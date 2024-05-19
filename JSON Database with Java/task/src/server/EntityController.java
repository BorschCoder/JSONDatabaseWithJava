package server;

import client.Request;

import static server.ResponseType.OK;

public class EntityController {

    private EntityService service;

    public EntityController() {
        this.service = new EntityService();
    }

    public Response handle(Request request) {

        String method = request.getType();
        String key = request.getKey();
        String value = request.getValue();

        Response response = new Response();
        switch (method) {
            case "set" -> {
                service.set(key, value, response);
                response.setResponse(OK);
            }
            case "get" -> {
                service.get(key, response);
            }
            case "delete" -> {
                service.delete(key, response);
            }
            default -> {
                response.setResponse(OK);
            }
        }

        return response;
    }
}
