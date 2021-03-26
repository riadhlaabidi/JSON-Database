package server.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import server.exceptions.NoSuchKeyException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public enum Database {
    INSTANCE;

    private final Path dbFilePath;
    private final Lock readLock;
    private final Lock writeLock;
    private final JsonObject database;
    {
        String filename = "db.json";
        dbFilePath = Paths.get("src" + File.separator +
                "main" + File.separator +
                "java" + File.separator +
                "server" + File.separator +
                "data" + File.separator +
                filename).toAbsolutePath();
        ReadWriteLock lock = new ReentrantReadWriteLock();
        writeLock = lock.writeLock();
        readLock = lock.readLock();
        database = initializeDatabase();
    }


    Database() {}

    protected final JsonObject initializeDatabase() {
        String content = "";
        try {
            if (Files.exists(dbFilePath)) {
                content = new String(Files.readAllBytes(dbFilePath));
            } else {
                Files.createFile(dbFilePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.isBlank()
                ? new JsonObject()
                : new Gson().fromJson(content, JsonObject.class);
    }

    public void set(String key, String value) {
        try {
            writeLock.lock();
            database.addProperty(key, value);
            write();
        } finally {
            writeLock.unlock();
        }
    }

    public String get(String key) {
        try {
            readLock.lock();
            if (database.has(key)) {
                return database.get(key).getAsString();
            }
            throw new NoSuchKeyException();
        } finally {
            readLock.unlock();
        }
    }

    public void delete(String key) {
        try {
            writeLock.lock();
            if (!database.has(key)) {
                throw new NoSuchKeyException();
            }
            database.remove(key);
            write();
        } finally {
            writeLock.unlock();
        }
    }

    private void write() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        try (FileWriter writer = new FileWriter(dbFilePath.toString())) {
            writer.write(gson.toJson(database));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
