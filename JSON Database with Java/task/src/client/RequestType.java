package client;

public enum RequestType {
    SET("set"),
    GET("get"),
    DELETE("delete"),
    IN("in"),
    EXIT("exit");

    private final String type;

    RequestType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
