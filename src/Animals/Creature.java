package Animals;

import Core.*;

import static java.lang.Math.*;

public abstract class Creature extends Entity {

    protected int food;
    protected static int range = 6;
    InitMap initMap;
    int[] targetObjectCords;

    public Creature(String symbol, Entities type, int hp) {
        super(symbol, type);
        this.hp = hp;
    }

    protected int howLong(Entities type){
        initMap = new InitMap(x,y);
        targetObjectCords = new int[2];
        int stepCounter = 0;

        for (; stepCounter < range + 1; stepCounter++) {
            targetObjectCords = initMap.foundPath(stepCounter, type);
            if (targetObjectCords != null) {
                break;
            }
        }
        return stepCounter;
    }

    protected Sides getTargetSide(int step){
        Sides side = Sides.NONE;
        if(targetObjectCords != null){
            side = initMap.getSide(x, y, targetObjectCords[0], targetObjectCords[1], step);
        }
        return side;
    }

    protected int[] getTargetObjectCords() {
        return targetObjectCords;
    }

    private static class InitMap {
        private final int high = map.getHigh();
        private final int width = map.getWidth();
        private final int upRange;
        private final int rightRange;
        private final int downRange;
        private final int leftRange;
        private final int markZeroObject = range + 2;
        private final int[][] movesMap = new int[high][width];

        InitMap(int x, int y) {
            upRange = max(0, y - range);
            rightRange = min(width, x + range);
            downRange = min(high, y + range);
            leftRange = max(0, x - range);

            for(int i = upRange; i < downRange; i++){
                for(int j = leftRange; j < rightRange; j++){
                    movesMap[i][j] = markZeroObject;
                }
            }

            movesMap[y][x] = 0;
        }

        public int[] foundPath(int step, Entities type){
            Entity entity;
            for(int y = upRange; y < downRange; y++){
                for(int x = leftRange; x < rightRange; x++){
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
                        } else if (y > upRange){
                            entity = map.getEntity(x, y - 1);
                            if (entity != null && entity.getType() == type){
                                movesMap[y - 1][x] = step+1;
                            }
                        }
                        if (myMoves[2]){
                            if (movesMap[y + 1][x] == markZeroObject){
                                movesMap[y + 1][x] = step+1;
                            }
                        } else if (y + 1 < downRange){
                            entity = map.getEntity(x, y + 1);
                            if (entity != null && entity.getType() == type){
                                movesMap[y + 1][x] = step+1;
                            }
                        }
                        if (myMoves[1]){
                            if (movesMap[y][x + 1] == markZeroObject){
                                movesMap[y][x + 1] = step+1;
                            }
                        } else if (x + 1 < rightRange){
                            entity = map.getEntity(x + 1, y);
                            if (entity != null && entity.getType() == type){
                                movesMap[y][x + 1] = step+1;
                            }
                        }
                        if (myMoves[3]){
                            if (movesMap[y][x - 1] == markZeroObject){
                                movesMap[y][x - 1] = step+1;
                            }
                        } else if (x > leftRange){
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
            } else {
                return Sides.LEFT;
            }
        }
    }

    public abstract void makeMove();

    protected void makeMoveToFood(Sides side){
        map.moveEntity(x,y,side);
    }

    protected void makeMoveRandom(){
        Sides side = Sides.NONE;
        boolean[] myMoves = map.getMoves(x,y);
        for (int i = 0; i < myMoves.length; i++){
            if (myMoves[i]){
                break;
            }
            if (i == myMoves.length - 1){
                return;
            }
        }
        Sides[] allMoves = new Sides[]{Sides.UP,Sides.RIGHT,Sides.DOWN,Sides.LEFT};
        while (true){
            int rand = (int)(random() * 4);
            side = allMoves[rand];
            if (myMoves[rand]){
                break;
            }
        }
        map.moveEntity(x,y,side);
    }
}