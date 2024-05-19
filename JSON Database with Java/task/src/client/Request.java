package client;

public class Request {
    private final String type;
    private final String key;
    private final String value;

    public Request(RequestType type, String keyValue) {
        this(type, keyValue, null);
    }

    public Request(RequestType type, String keyValue, String setValue) {
        this.type = type.getType();
        this.key = keyValue;
        this.value = setValue;
    }

    public Request(RequestType type) {
        this(type, null);
    }

    public String getType() {
        return type;
    }


    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Request{");
        sb.append("type='").append(type).append('\'');
        sb.append(", key='").append(key).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
