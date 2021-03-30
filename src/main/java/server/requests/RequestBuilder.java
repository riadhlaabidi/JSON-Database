package server.requests;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class RequestBuilder {

    private final Request request = new Request();

    private RequestBuilder() {}

    public static RequestBuilder newBuilder() {
        return new RequestBuilder();
    }

    public RequestBuilder fromJson(String json) {
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
        request.setType(jsonObject.get("type").getAsString());
        request.setKey(jsonObject.get("key"));
        request.setValue(jsonObject.get("value"));
        return this;
    }

    public Request build() {
        return request;
    }
}
