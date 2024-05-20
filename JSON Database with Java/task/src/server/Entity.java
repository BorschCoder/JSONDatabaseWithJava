package server;


import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class Entity {


    String type;

    JsonElement key;

    public String getType() {
        return type;
    }

    JsonElement value;


    @Override
    public String toString() {
        return "Entity{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }


    public List<String> getKeys() {
        List<String> list = new ArrayList<>();
        if (key.isJsonArray()) {
            for (JsonElement e : key.getAsJsonArray()) {
                if (e.isJsonPrimitive()) {
                    list.add(e.getAsString());
                }
            }
        } else if (key.isJsonPrimitive()) {
            list.add(key.getAsString());
        }
        return list;
    }
}
