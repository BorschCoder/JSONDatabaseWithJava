package server;

import java.util.List;

import static server.ResponseType.ERROR;
import static server.ResponseType.OK;

public class EntityService {
    private final EntityDAO repository;


    public EntityService() {
        this.repository = new EntityDAO();
    }


    public synchronized void get(String key, Response response) {

        List<Entity> list = repository.findAll();
        Entity entity = list.stream().filter(t -> t.getKey().equals(key)).findFirst().orElse(new Entity("", ""));

        if (isInvalid(entity)) {
            response.setReason("No such key");
            response.setResponse(ERROR);
        } else {
            response.setValue(entity.getValue());
            response.setResponse(OK);
        }
    }

    public synchronized void set(String key, String value, Response response) {
        List<Entity> list = repository.findAll();

        Entity entity = list.stream().filter(t -> t.getKey().equals(key)).findFirst().orElse(new Entity("", ""));

        if (isInvalid(entity)) {
            list.add(new Entity(key, value));
        } else {
            entity.setValue(value);
        }

        repository.saveAll(list);

        response.setResponse(OK);

    }

    public synchronized void delete(String key, Response response) {
        List<Entity> list = repository.findAll();

        Entity entity = list.stream().filter(t -> t.getKey().equals(key)).findFirst().orElse(new Entity("", ""));

        if (isInvalid(entity)) {
            response.setReason("No such key");
            response.setResponse(ERROR);
        } else {
            list.remove(entity);
            response.setResponse(OK);
        }
        repository.saveAll(list);
    }

    private boolean isInvalid(Entity entity) {
        return entity == null || entity.getKey().isBlank() && entity.getValue().isBlank();
    }
}
