package server.requests;

import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;

public class Response {

    public static final String STATUS_OK = "OK";
    public static final String STATUS_ERROR = "ERROR";

    @Expose private String response;
    @Expose private String reason;
    @Expose private JsonElement value;


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

    public JsonElement getValue() {
        return value;
    }

    public void setValue(JsonElement value) {
        this.value = value;
    }
}
