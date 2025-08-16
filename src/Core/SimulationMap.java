package Core;

import java.util.HashMap;
import java.util.Map;

public class SimulationMap {
    protected static int mapSize;
    protected static int mapWidth;
    protected static int mapHigh;
    private static final Map<Integer, Cell> cells = new HashMap<>(mapSize);

    public SimulationMap(int mapWidth, int mapHigh) {
        if (mapSize % mapWidth != 0) {
            while (mapSize % mapWidth != 0){
                mapSize++;
            }
        }
        SimulationMap.mapWidth = mapWidth;
        SimulationMap.mapHigh = mapHigh;
        SimulationMap.mapSize = mapHigh * mapWidth;
        fillMap();
    }

    public void printMap(){
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

    public boolean isContainsEntity(int x, int y) {
        Cell cell = getCell(x, y);
        return cell.isContainsEntity();
    }

    public boolean putEntity(int x, int y, Entity entity) {
        Cell cell = getCell(x, y);
        if(cell.isContainsEntity()) {
            return false;
        }
        entity.setX(x);
        entity.setY(y);
        cell.putEntity(entity, this);
        cells.put(cell.getId(), cell);
        return true;
    }

    public void removeEntity(int x, int y) {
        Cell cell = getCell(x, y);
        cell.removeEntity();
    }

    public void moveEntity(int xFrom, int yFrom, Sides side) {
        Cell cellFrom = getCell(xFrom, yFrom);
        Entity entity = cellFrom.getEntity();
        Cell cellTarget;
        if (side == Sides.UP){
            cellTarget = getCell(xFrom, yFrom - 1);
        } else if (side == Sides.RIGHT){
            cellTarget = getCell(xFrom + 1, yFrom);
        } else if (side == Sides.DOWN){
            cellTarget = getCell(xFrom, yFrom + 1);
        } else {
            cellTarget = getCell(xFrom - 1, yFrom);
        }
        if (cellTarget.getEntity() == null && cellFrom.removeEntity()) {
            cellTarget.putEntity(entity, this);
        }
    }

    private static Cell getCell(int x, int y) {
        int id = x + y * mapWidth;
        if (!cells.containsKey(id)) {
            throw new IllegalStateException("Cell not found at [" + x + "," + y + "]");
        }
        return cells.get(id);
    }

    private static void putCell(Cell cell) {
        int id = cell.getId();
        cells.put(id, cell);
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
        private static final int mapWidth = SimulationMap.mapWidth;
        private static final int mapHigh = SimulationMap.mapHigh;
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
            fillCollision();
            entity.setX(x);
            entity.setY(y);
            entity.setMap(map);
            this.entity = entity;
        }

        public Entity getEntity() {
            return entity;
        }

        private void fillCollision(){
            if (up || y - 1 >= 0) {
                Cell cell = getCell(x, y - 1);
                cell.setDown(false);
            }
            if (down || y + 1 < mapHigh) {
                Cell cell = getCell(x, y + 1);
                cell.setUp(false);
            }
            if (left || x - 1 >= 0) {
                Cell cell = getCell(x - 1, y);
                cell.setRight(false);
            }
            if (right || x + 1 < mapWidth) {
                Cell cell = getCell(x + 1, y);
                cell.setLeft(false);
            }
        }

        public boolean removeEntity() {
            if (entity != null) {
                if (up || y - 1 >= 0) {
                    Cell cell = getCell(x, y - 1);
                    cell.setDown(true);
                }
                if (down || y + 1 < mapHigh) {
                    Cell cell = getCell(x, y + 1);
                    cell.setUp(true);
                }
                if (left || x - 1 >= 0) {
                    Cell cell = getCell(x - 1, y);
                    cell.setRight(true);
                }
                if (right || x + 1 < mapWidth) {
                    Cell cell = getCell(x + 1, y);
                    cell.setLeft(true);
                }
                entity = null;
                return true;
            }
            return false;
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


