package Core;

import java.util.HashMap;
import java.util.Map;

public class SimulationMap {
    private final Map<Integer, Entity> mapObjects;
    int mapSize;
    int mapWidth;

    public SimulationMap(int mapSize, int mapWidth) {
        this.mapSize = mapSize;
        this.mapWidth = mapWidth;
        this.mapObjects = new HashMap<>();
    }

    public Entity get(int id) {
        if (mapObjects.containsKey(id)) {
            return mapObjects.get(id);
        }
        return null;
    }

    public Entity get(int x, int y) {
        int id = x * y;
        if (mapObjects.containsKey(id)) {
            return mapObjects.get(id);
        }
        return null;
    }

    public void putEntity(int id, Entity entity) {
        mapObjects.put(id, entity);
    }

    public void putEntity(int x, int y, Entity entity) {
        int id = x * y;
        mapObjects.put(id, entity);
    }

    public int getSize() {
        return mapSize;
    }

    public int getWidth() {
        return mapWidth;
    }
}
