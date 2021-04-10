package server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import server.exceptions.NoSuchKeyException;
import server.utils.GsonUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public enum Database {

    INSTANCE;

    private static final String FILENAME = "db.json";
    private static final Path DB_PATH = Server.DATA_DIR_PATH.resolve(FILENAME);
    private final Lock readLock;
    private final Lock writeLock;
    private JsonObject database;

    {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        writeLock = lock.writeLock();
        readLock = lock.readLock();
    }

    Database() {}

    public void init() throws IOException {
        if (Files.exists(DB_PATH)) {
            String content = new String(Files.readAllBytes(DB_PATH));
            database = new Gson().fromJson(content, JsonObject.class);
        } else {
            Files.createFile(DB_PATH);
            database = new JsonObject();
            writeToDatabase();
        }
    }

    public void set(JsonElement key, JsonElement value) {
        try {
            writeLock.lock();
            if (key.isJsonPrimitive()) {
                database.add(key.getAsString(), value);
            } else if (key.isJsonArray()) {
                JsonArray keys = key.getAsJsonArray();
                String toAdd = keys.remove(keys.size() - 1).getAsString();
                findElement(keys, true).getAsJsonObject().add(toAdd, value);
            } else {
                throw new NoSuchKeyException();
            }
            writeToDatabase();
        } finally {
            writeLock.unlock();
        }
    }

    public JsonElement get(JsonElement key) {
        try {
            readLock.lock();
            if (key.isJsonPrimitive() && database.has(key.getAsString())) {
                return database.get(key.getAsString());
            } else if (key.isJsonArray()) {
                return findElement(key.getAsJsonArray(), false);
            }
            throw new NoSuchKeyException();
        } finally {
            readLock.unlock();
        }
    }

    public void delete(JsonElement key) {
        try {
            writeLock.lock();
            if (key.isJsonPrimitive() && database.has(key.getAsString())) {
                database.remove(key.getAsString());
            } else if (key.isJsonArray()) {
                JsonArray keys = key.getAsJsonArray();
                String toRemove = keys.remove(keys.size() - 1).getAsString();
                findElement(keys, false).getAsJsonObject().remove(toRemove);
                writeToDatabase();
            } else {
                throw new NoSuchKeyException();
            }
        } finally {
            writeLock.unlock();
        }
    }

    private JsonElement findElement(JsonArray keys, boolean createIfAbsent) {
        JsonElement tmp = database;
        if (createIfAbsent) {
            for (JsonElement key: keys) {
                if (!tmp.getAsJsonObject().has(key.getAsString())) {
                    tmp.getAsJsonObject().add(key.getAsString(), new JsonObject());
                }
                tmp = tmp.getAsJsonObject().get(key.getAsString());
            }
        } else {
            for (JsonElement key: keys) {
                if (!key.isJsonPrimitive() || !tmp.getAsJsonObject().has(key.getAsString())) {
                    throw new NoSuchKeyException();
                }
                tmp = tmp.getAsJsonObject().get(key.getAsString());
            }
        }
        return tmp;
    }

    private void writeToDatabase() {
        try (FileWriter writer = new FileWriter(DB_PATH.toString())) {
            writer.write(GsonUtils.prettyPrint(database));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
