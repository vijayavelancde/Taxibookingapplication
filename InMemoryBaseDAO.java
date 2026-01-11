package cabbooking;

import java.util.HashMap;

public class InMemoryBaseDAO<T extends Identifiable> {

    public HashMap<String, T> map = new HashMap<>();

    public void add(T obj) {
        if (obj == null) {
            return;
        }

        String id = obj.getID();

        if (map.containsKey(id)) {
            return;
        }

        map.put(id, obj);
        
    }

    public T getById(String locationid) {
        if (!map.containsKey(locationid)) {
            return null;
        }
        return map.get(locationid);
    }

    public void delete(String id) {
        if (!map.containsKey(id)) {
            return;
        }

        T obj = map.get(id);
        map.remove(id);
    }
}
