package server.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {
    public static final Gson prettyGson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
}
