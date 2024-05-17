package client;

import java.io.Serializable;

public class Request implements Serializable {
    private final String type;
    private  String key;
    private  String value;

    public Request(RequestType type, String keyValue) {
        this.type = type.getType();
        this.key = keyValue;
    }

    public Request(RequestType type, String keyValue, String setValue) {
        this.type = type.getType();
        this.key = keyValue;
        this.value = setValue;
    }

    public Request(RequestType type) {
        this.type = type.getType();
    }

    public String getType() {
        return type;
    }


    public String getKeyValue() {
        return key;
    }

    public String getSetValue() {
        return value;
    }

}
