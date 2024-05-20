package server;

import com.google.gson.JsonElement;

import java.io.Serializable;

public class Response implements Serializable {

    private ResponseType response;
    private String reason;
    private JsonElement value;

    public void setValue(JsonElement value) {
        this.value = value;
    }

    public Response(ResponseType response, String reason) {
        this.response = response;
        this.reason = reason;
    }

    public Response(ResponseType response) {
        this.response = response;
    }
    public Response( ) {

    }

    public Response(ResponseType response, String reason, JsonElement value) {
        this.response = response;
        this.reason = reason;
        this.value = value;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setResponse(ResponseType response) {
        this.response = response;
    }
}
