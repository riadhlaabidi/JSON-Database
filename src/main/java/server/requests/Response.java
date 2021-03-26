package server.requests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

public class Response implements JsonConverter {
    public static final String STATUS_OK = "OK";
    public static final String STATUS_ERROR = "ERROR";

    private String response;
    private String reason;
    private String value;


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toJson() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        Map<String, String> map = new LinkedHashMap<>();
        map.put("response", response);
        map.put("value", value);
        map.put("reason", reason);
        return gson.toJson(map);
    }
}
