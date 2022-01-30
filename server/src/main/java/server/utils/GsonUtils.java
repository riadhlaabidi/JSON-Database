package server.utils;

import com.google.gson.GsonBuilder;

public final class GsonUtils {

    private GsonUtils() {

    }

    public static String prettyPrint(Object obj) {
        return new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(obj);
    }
}
