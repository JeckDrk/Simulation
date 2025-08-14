package Animals;

import Core.Entities;
import Core.Entity;
import Core.Sides;
import Core.SimulationMap;

import static java.lang.Math.min;
import static java.lang.Math.max;

public abstract class Creature extends Entity {

    protected static final int RANGE = 6;

    InitMap initMap;

    public Creature(String symbol, Entities type, int id, SimulationMap map) {
        super(symbol, type, id);
        Creature.map = map;
    }

    public void myPriority(){

    }

    public void showMove(int x, int y, Entities type){
        initMap = new InitMap(x,y);
        int[] lookingObjectCords = new int[2];
        int stepCounter = 0;
        Sides side = Sides.NONE;

        for (; stepCounter < RANGE + 1; stepCounter++) {
            lookingObjectCords = initMap.tryFoundPath(stepCounter, type);
            if (lookingObjectCords != null) {
                break;
            }
        }

        System.out.print("Steps: " + stepCounter);

        if(lookingObjectCords != null){
            side = initMap.getSide(x, y, lookingObjectCords[0], lookingObjectCords[1], stepCounter);
            System.out.print(" Coords: " + lookingObjectCords[0] + ", " + lookingObjectCords[1]);
        }

        System.out.print(" Side: " + side + "\n");

        initMap.printMap();
    }

    static class InitMap {
        int high = map.getHigh();
        int width = map.getWidth();

        int xMy;
        int yMy;

        int upRange;
        int rightRange;
        int downRange;
        int leftRange;

        int markZeroObject = RANGE + 2;

        int[][] movesMap = new int[high][width];

        InitMap(int x, int y) {
            this.xMy = x;
            this.yMy = y;

            upRange = max(0, yMy - RANGE);
            rightRange = min(width, xMy + RANGE);
            downRange = min(high, yMy + RANGE);
            leftRange = max(0, xMy - RANGE);

            for(int i = upRange; i < downRange; i++){
                for(int j = leftRange; j < rightRange; j++){
                    movesMap[i][j] = markZeroObject;
                }
            }

            movesMap[y][x] = 0;
        }

        public int[] tryFoundPath(int step, Entities type){
            Entity entity;
            for(int y = upRange; y < downRange; y++){
                for(int x = leftRange; x < downRange; x++){
                    entity = map.getEntity(x, y);
                    if(movesMap[y][x] == step){
                        if(entity != null && entity.getType() == type){
                            return new int[]{x, y};
                        }
                        boolean[] myMoves = map.getMoves(x,y);
                        if (myMoves[0]){
                            if (movesMap[y - 1][x] == markZeroObject){
                                movesMap[y - 1][x] = step+1;
                            }
                        } else if (y >= upRange){
                            entity = map.getEntity(x, y - 1);
                            if (entity != null && entity.getType() == type){
                                movesMap[y - 1][x] = step+1;
                            }
                        }
                        if (myMoves[1]){
                            if (movesMap[y][x + 1] == markZeroObject){
                                movesMap[y][x + 1] = step+1;
                            }
                        } else if (x < rightRange){
                            entity = map.getEntity(x + 1, y);
                            if (entity != null && entity.getType() == type){
                                movesMap[y][x + 1] = step+1;
                            }
                        }
                        if (myMoves[2]){
                            if (movesMap[y + 1][x] == markZeroObject){
                                movesMap[y + 1][x] = step+1;
                            }
                        } else if (y < downRange){
                            entity = map.getEntity(x, y + 1);
                            if (entity != null && entity.getType() == type){
                                movesMap[y + 1][x] = step+1;
                            }
                        }
                        if (myMoves[3]){
                            if (movesMap[y][x - 1] == markZeroObject){
                                movesMap[y][x - 1] = step+1;
                            }
                        } else if (x >= leftRange){
                            entity = map.getEntity(x - 1, y);
                            if (entity != null && entity.getType() == type){
                                movesMap[y][x - 1] = step+1;
                            }
                        }
                    }
                }
            }
            return null;
        }

        public int getStep(int x, int y){
            return movesMap[y][x];
        }

        public Sides getSide(int x, int y, int xLooking, int yLooking, int step){
            if (step == 1){
                return Sides.NONE;
            }
            for(; step > 1; step--){
                if(yLooking - 1 >= upRange && movesMap[yLooking - 1][xLooking] < step){
                    yLooking--;
                } else if (yLooking + 1 < downRange && movesMap[yLooking + 1][xLooking] < step) {
                    yLooking++;
                } else if (xLooking + 1 < rightRange && movesMap[yLooking][xLooking + 1] < step) {
                    xLooking++;
                } else if (xLooking - 1 >= leftRange && movesMap[yLooking][xLooking - 1] < step) {
                    xLooking--;
                }
            }
            if(y - yLooking > 0){
                return Sides.UP;
            } else if (x - xLooking < 0){
                return Sides.RIGHT;
            } else if (y - yLooking < 0){
                return Sides.DOWN;
            } else if(x - xLooking > 0){
                return Sides.LEFT;
            } else {
                return Sides.NONE;
            }
        }

        public void printMap(){
            for(int y = 0; y < high; y++){
                for(int x = 0; x < width; x++){
                    System.out.print(movesMap[y][x] + " ");
                }
                System.out.println();
            }
        }
    }
//    public abstract int getMove(Map<Integer, Entity> map, int widthMap, int location);
//    public abstract void eat();
}