package server;

import com.google.gson.JsonElement;

import java.util.List;

import static server.ResponseType.ERROR;
import static server.ResponseType.OK;

public class EntityService {
    private final EntityDAO repository;


    public EntityService() {
        this.repository = new EntityDAO();
    }


    public synchronized void get(Entity entity, Response response) {

        List<String> keys = entity.getKeys();

        List<Entry> list = repository.findAll();

        Entry entry = list.stream().filter(e -> e.key.equals(keys.get(0)))
                .findFirst()
                .orElse(null);

        if (isInvalid(entity)) {
            response.setReason("No such key");
            response.setResponse(ERROR);
        } else {
            JsonElement elem = entry.value;
            for (int i = 1; i < keys.size(); i++) {
                if (elem.isJsonObject()) {
                    elem = elem.getAsJsonObject().get(keys.get(i));
                } else if (elem.isJsonPrimitive()) {
                    elem = elem.getAsJsonPrimitive();
                }
            }
            response.setValue(elem);
            response.setResponse(OK);
        }
    }

    public synchronized void set(Entity entity, Response response) {
        List<String> keys = entity.getKeys();
        List<Entry> list = repository.findAll();

        Entry entry = list.stream().filter(e -> e.key.equals(keys.get(0)))
                .findFirst()
                .orElse(null);

        if (entry == null) {
            entry = new Entry();
            entry.key = keys.get(0);
            entry.value = entity.value;
            list.add(entry);
        } else {

            if (keys.size() >= 2) {
                JsonElement value = entry.value;
                for (int i = 1; i < keys.size() - 1; i++) {
                    System.out.println(value);
                    System.out.println(value.isJsonObject());
                    if (value.isJsonObject()) {
                        value = value.getAsJsonObject().get(keys.get(i));
                    }
                    System.out.println("i=" + i);
                }

                if (value.isJsonObject()) {
                    if (entity.value.isJsonPrimitive()) {
                        value.getAsJsonObject().addProperty(keys.get(keys.size() - 1), entity.value.getAsString());
                    }
                }

            } else {
                entry.value = entity.value;
            }

        }

        repository.saveAll(list);

        response.setResponse(OK);

    }

    public synchronized void delete(Entity entity, Response response) {
        List<String> keys = entity.getKeys();
        List<Entry> list = repository.findAll();

        Entry entry = list.stream().filter(e -> e.key.equals(keys.get(0)))
                .findFirst()
                .orElse(null);
        if (isInvalid(entity)) {
            response.setReason("No such key");
            response.setResponse(ERROR);
        } else {
            if (keys.size() >= 2) {
                JsonElement elem = entry.value;
                for (int i = 1; i < keys.size() - 1; i++) {
                    if (elem.isJsonObject()) {
                        elem = elem.getAsJsonObject().get(keys.get(i));
                    }
                }
                elem.getAsJsonObject().remove(keys.get(keys.size() - 1));
            } else {
                list.remove(entry);
            }

        }
        response.setResponse(OK);
        repository.saveAll(list);
    }

    private boolean isInvalid(Entity entity) {
        return entity == null || entity.getKeys().isEmpty();
    }
}
