package Animals;

import Core.Entities;
import Core.Entity;
import Core.SimulationMap;

import static java.lang.Math.abs;

public abstract class Creature extends Entity {

    protected static final int ERROR = 90909090;

    protected static final int RADIUS = 1;
    protected static final int MOVE = 1;


    public Creature(String symbol, Entities type, int id) {
        super(symbol, type, id);
    }

    public int[] searchPath(SimulationMap map, int xMy, int yMy, Entities lookingType) {
        int sizeMap = map.getSize();
        int widthMap = map.getWidth();
        int highMap = map.getHigh();

        final int myObjectMark = 0;
        final int wrongObjectMark = sizeMap + 1;
        final int zeroObjectMark = sizeMap +2;
        final int lookingObjectMark = sizeMap +3;


        int mapCount = 1;

        int[] results = new int[6];

        int[][] initMap = new int[highMap][widthMap];

        for(int y = 0; y < highMap; y++){
            for(int x = 0; x < widthMap; x++){
                initMap[y][x] = zeroObjectMark;
            }
        }

        initMap[yMy][xMy] = myObjectMark;

        for(int y = 0; y < highMap; y++) {
            for(int x = 0; x < widthMap; x++) {
                Entity currentObject = map.getEntity(x, y);
                if (currentObject != null) {
                    Entities typeCurrentObject = currentObject.getType();
                    if (lookingType.equals(typeCurrentObject)) {
                        initMap[y][x] = lookingObjectMark;
                    } else {
                        initMap[y][x] = wrongObjectMark;
                    }
                    mapCount++;
                }
            }
        }

        int[] lookingCoordinate = new int[2];
        int moveCount = myObjectMark; // 0

        boolean isFound = false;
//        while ((mapCount < sizeMap) && !isFound) {
        while (true) {
            for (int y = 0; y < highMap; y++) {
                for (int x = 0; x < widthMap; x++) {
                    if (initMap[y][x] == moveCount) {
                        for (int yInv = y - MOVE; yInv <= y + MOVE; yInv += MOVE) {
                            for (int xInv = x - MOVE; xInv <= x + MOVE; xInv += MOVE) {
                                if(yInv >= 0 && yInv < highMap && xInv >= 0 && xInv < widthMap) {
                                    if (initMap[yInv][xInv] == zeroObjectMark) {
                                        initMap[yInv][xInv] = moveCount + 1;
                                    } else if (initMap[yInv][xInv] == lookingObjectMark) {
                                        lookingCoordinate = new int[]{x,y};
                                        isFound = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if (isFound){
                            break;
                        }
                    }
                }
                if (isFound){
                    break;
                }
            }
            if (isFound){
                break;
            }
            moveCount++;
        }

        results[0] = moveCount;
        results[1] = lookingCoordinate[0];
        results[2] = lookingCoordinate[1];

//        int lookingCountMove = initMap.get(lookingCoordinate);
//        while (moveCount > myObjectMark + MOVE){
//            currentInvestigatedCoordinates = new int[]{lookingCoordinate - widthMap, lookingCoordinate + widthMap, lookingCoordinate - MOVE, lookingCoordinate + MOVE};
//            for (int currentInvestigatedCoordinate : currentInvestigatedCoordinates) {
//                if ((currentInvestigatedCoordinate >= 0) && (currentInvestigatedCoordinate < sizeMap)
//                        && (initMap.get(currentInvestigatedCoordinate) < lookingCountMove)) {
//                    lookingCountMove = initMap.get(currentInvestigatedCoordinate);
//                    lookingCoordinate = currentInvestigatedCoordinate;
//                }
//            }
//            moveCount = lookingCountMove;
//        }
//
//        results[4] = lookingCoordinate[0];
//        results[5] = lookingCoordinate[1];

        for (int y = 0; y < highMap; y++) {
            for (int x = 0; x < widthMap; x++) {
                System.out.print(initMap[y][x] + " ");
            }
            System.out.println();
        }
        System.out.println();

        return results;
    }

//
//    public abstract int getMove(Map<Integer, Entity> map, int widthMap, int location);
//    public abstract void eat();
}
//
//class initMap() {
//
//}