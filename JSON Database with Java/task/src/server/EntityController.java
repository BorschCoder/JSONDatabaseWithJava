package server;

import static server.ResponseType.OK;

public class EntityController {

    private EntityService service;

    public EntityController() {
        this.service = new EntityService();
    }

    public Response handle(Entity entity) {

        String method = entity.getType();
//        String key = entity.getKey();

        Response response = new Response();
        switch (method) {
            case "set" -> {
                service.set(entity, response);
                response.setResponse(OK);
            }
            case "get" -> {
                service.get(entity, response);
            }
            case "delete" -> {
                service.delete(entity, response);
            }
            default -> {
                response.setResponse(OK);
            }
        }

        return response;
    }
}
