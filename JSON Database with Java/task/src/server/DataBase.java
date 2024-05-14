package server;

import client.Request;

import java.util.Map;
import java.util.LinkedHashMap;

import static server.ResponseType.ERROR;
import static server.ResponseType.OK;

public class DataBase {
    private Map<String, String> database;

    public DataBase() {
        this.database = new LinkedHashMap<>();
    }

    public Response requestHandler(Request request) {

        String method = request.getType();
        String key = request.getKeyValue();
        String value = request.getSetValue();

        Response response = new Response();

        switch (method) {
            case "set" -> {
                set(key, value, response);
                response.setResponse(OK);
            }
            case "get" -> {
                get(key, response);
            }
            case "delete" -> {
                delete(key, response);
            }
            default -> {
                response.setResponse(OK);
            }
        }

        return response;
    }

    public void delete(String key, Response response) {
        if (database.remove(key) == null) {
            response.setReason("No such key");
            response.setResponse(ERROR);
        } else {
            response.setResponse(OK);
        }
    }

    public void get(String key, Response response) {
        String value = database.get(key);
        if (value == null) {
            response.setReason("No such key");
            response.setResponse(ERROR);
        } else {
            response.setValue(value);
            response.setResponse(OK);
        }
    }

    public void set(String key, String text, Response response) {

        database.put(key, text);
        response.setResponse(OK);

    }
}
