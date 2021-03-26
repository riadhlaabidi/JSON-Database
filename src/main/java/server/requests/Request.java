package server.requests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import server.cli.CommandLineArgs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

public class Request implements JsonConverter {

    private static final Path DATA_DIR_PATH = Paths.get("src" + File.separator +
            "main" + File.separator +
            "java" + File.separator +
            "client" + File.separator +
            "data" ).toAbsolutePath();

    private String type;
    private String key;
    private String value;
    private String filename;

    public Request(CommandLineArgs commandLineArgs) {
        if (commandLineArgs.filename == null) {
            this.type = commandLineArgs.type;
            this.key = commandLineArgs.key;
            this.value = commandLineArgs.value;
        } else {
            this.filename = commandLineArgs.filename;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    private String readFromFile(String filename) throws IOException {
        return new String(Files.readAllBytes(DATA_DIR_PATH.resolve(filename)));
    }

    @Override
    public String toJson() {
        if (filename != null) {
            try {
                return readFromFile(filename);
            } catch (IOException e) {
                System.out.println("Cannot read file: " + e.getMessage());
                System.exit(1);
            }
        }
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        Map<String, String> map = new LinkedHashMap<>();
        map.put("type", type);
        map.put("key",key);
        map.put("value",value);
        return gson.toJson(map);
    }
}
