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
        cell.putEntity(entity, this);
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

    public boolean[] getMoves(int x, int y) {
        return getCell(x,y).getMoves();
    }

    private static class Cell {
        static int mapSize = SimulationMap.mapSize;
        static int mapWidth = SimulationMap.mapWidth;
        static int mapHigh = SimulationMap.mapHigh;
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
            this.id = x + y * mapWidth;
            if(y != 0){
                up = true;
            }
            if (y + 1 != mapHigh) {
                down = true;
            }
            if(x != 0){
                left = true;
            }
            if (x + 1 != mapWidth) {
                right = true;
            }
        }

        public boolean isContainsEntity() {
            return entity != null;
        }

        public void putEntity(Entity entity, SimulationMap map) {
            fillMovement();
            entity.setMap(map);
            this.entity = entity;
        }

        void fillMovement(){
            if (up) {
                Cell cell = getCell(x, y - 1);
                cell.setDown(false);
            }
            if (down) {
                Cell cell = getCell(x, y + 1);
                cell.setUp(false);
            }
            if (left) {
                Cell cell = getCell(x - 1, y);
                cell.setRight(false);
            }
            if (right) {
                Cell cell = getCell(x + 1, y);
                cell.setLeft(false);
            }
        }

        public Entity getEntity() {
            return entity;
        }

        public boolean[] getMoves() {
            return new boolean[]{up,right,down,left};
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


