package server;

import java.io.Serializable;

public class Response implements Serializable {

    private ResponseType response;
    private String reason;
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ResponseType getResponse() {
        return response;
    }

    public Response() {
    }

    public Response(ResponseType response, String reason) {
        this.response = response;
        this.reason = reason;
    }

    public Response(ResponseType response, String reason, String value) {
        this.response = response;
        this.reason = reason;
        this.value = value;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setResponse(ResponseType response) {
        this.response = response;
    }
}
