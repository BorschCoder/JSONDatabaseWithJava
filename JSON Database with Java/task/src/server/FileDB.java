package server;

import client.Request;

import java.util.LinkedList;

import static server.ResponseType.ERROR;
import static server.ResponseType.OK;

public class FileDB {
    private final FileHandler file;


    public FileDB() {
        this.file = new FileHandler();
    }

    public Response requestHandler(Request request) {

        String method = request.getType();
        String key = request.getKeyValue();
        String value = request.getSetValue();
        Response response = null;
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
                response = new Response(OK);
//                response.setResponse(OK);
            }
        }

        return response;
    }

    public synchronized void get(String key, Response response) {
        LinkedList<TaskFile> list = file.read();
        TaskFile taskFile = list.stream().filter(t -> t.getKey().equals(key)).findFirst().orElse(null);
        if (taskFile == null) {
            response.setReason("No such key");
            response.setResponse(ERROR);
        } else {
            response.setValue(taskFile.getValue());
            response.setResponse(OK);
        }
    }

    public synchronized void set(String key, String value, Response response) {
        LinkedList<TaskFile> list = file.read();
        TaskFile taskFile = list.stream().filter(t -> t.getKey().equals(key)).findFirst().orElse(null);

        if (taskFile == null) {
            list.add(new TaskFile(key, value));
            response.setResponse(OK);
        } else {
            response.setReason("base has already such key");
            response.setResponse(ERROR);
        }

        file.save(list);
    }

    public synchronized void delete(String key, Response response) {
        LinkedList<TaskFile> list = file.read();
        TaskFile taskFile = list.stream().filter(t -> t.getKey().equals(key)).findFirst().orElse(null);

        if (taskFile == null) {
            response.setReason("No such key");
            response.setResponse(ERROR);
        } else {
            list.remove(taskFile);
            response.setResponse(OK);
        }
        file.save(list);
    }
}
