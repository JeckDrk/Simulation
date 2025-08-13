package Core;

import java.util.HashMap;
import java.util.Map;

public class SimulationMap {
    protected static int mapSize;
    protected static int mapWidth;
    protected static int mapHigh;
    protected static final Map<Integer, Cell> cells = new HashMap<>(mapSize);

    public SimulationMap(int mapSize, int mapWidth) {
        SimulationMap.mapSize = mapSize;
        SimulationMap.mapWidth = mapWidth;
        SimulationMap.mapHigh = mapSize / mapWidth;
        fillMap();
    }

    public void showMap(){
        for (int y = 0; y < mapHigh; y++){
            for (int x = 0; x < mapWidth; x++){
                Cell cell = getCell(x, y);
                if (cell.isContainsEntity()) {
                    System.out.print(cell.getEntity());
                } else {
                    System.out.print(" .");
                }
            }
            System.out.println();
        }
    }

    public void fillMap() {
        for (int y = 0; y < mapHigh; y++){
            for (int x = 0; x < mapWidth; x++){
                Cell cell = new Cell(x, y);
                putCell(cell);
            }
        }
    }

    public Entity getEntity(int x, int y) {
        int id = x + y * mapWidth;
        Cell cell = cells.get(id);
        if (cells.containsKey(id)) {
            return cell.getEntity();
        }
        return null;
    }

    public void putEntity(int x, int y, Entity entity) {
        Cell cell = getCell(x, y);
        cell.putEntity(entity);
        cells.put(cell.getId(), cell);
    }

    public static Cell getCell(int x, int y) {
        int id = x + y * mapWidth;
        return cells.get(id);
    }

    public static void putCell(int x, int y, Cell cell) {
        int id = x + y * mapWidth;
        cells.put(id, cell);
    }

    public static void putCell(Cell cell) {
        int id = cell.getId();
        cells.put(id, cell);
    }

    public int getSize() {
        return mapSize;
    }

    public int getWidth() {
        return mapWidth;
    }

    public int getHigh(){
        return mapHigh;
    }

    private static class Cell {
        static int sizeMap = SimulationMap.mapSize;
        static int widthMap = SimulationMap.mapWidth;
        static int highMap = SimulationMap.mapHigh;
        private boolean up = false;
        private boolean down = false;
        private boolean left = false;
        private boolean right = false;
        private final int x;
        private final int y;
        private final int id;
        private Entity entity;

        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
            this.id = x + y * widthMap;
            if(y != 0){
                up = true;
            }
            if (y + 1 != SimulationMap.mapHigh) {
                down = true;
            }
            if(x != 0){
                left = true;
            }
            if (x + 1 != SimulationMap.mapWidth) {
                right = true;
            }
        }

        public boolean isContainsEntity() {
            return entity != null;
        }

        public void putEntity(Entity entity) {
            fillMovement();
            this.entity = entity;
        }

        void fillMovement(){
            if (up) {
                Cell cell = getCell(x, y - 1);
                cell.setDown(false);
            }
            if (down) {
                Cell cell = getCell(x, y + 1);
                cell.setDown(false);
            }
            if (left) {
                Cell cell = getCell(x - 1, y);
                cell.setLeft(false);
            }
            if (right) {
                Cell cell = getCell(x + 1, y);
                cell.setRight(false);
            }
        }

        public Entity getEntity() {
            return entity;
        }

        public void setUp(boolean up) {
            this.up = up;
        }

        public void setDown(boolean down) {
            this.down = down;
        }

        public void setLeft(boolean left) {
            this.left = left;
        }

        public void setRight(boolean right) {
            this.right = right;
        }

        public int getId(){
            return id;
        }
    }
}


