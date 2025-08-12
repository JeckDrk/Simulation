package Animals;

import Core.Entities;
import Core.Entity;
import Core.SimulationMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import static java.lang.Math.abs;

public abstract class Creature extends Entity {

    protected static final int ERROR = 90909090;

    protected static final int RADIUS = 1;
    protected static final int MOVE = 1;


    public Creature(String symbol, Entities type, int id) {
        super(symbol, type, id);
    }

    public int cordToLength(int keyMy, int keyObject, int widthMap, int radius, int move) {
        int length = 0;

        int xMy = keyMy % widthMap;
        int yMy = keyMy / widthMap;

        int xObject = keyObject % widthMap;
        int yObject = keyObject / widthMap;

        int x = abs(xMy - xObject);
        int y = abs(yMy - yObject);
        while ((x-move) >= radius || (y-move) >= radius) {
            length += move;
            x -= move;
            y -= move;
        }
        return length;
    }

    int getNearestObjectTypeOf (Map<Integer, Entity> map, int widthMap, int myKey, Entities lookingType) {

        int minLength = myKey*myKey;

        int keyNearestObject = ERROR;

        for (Map.Entry<Integer, Entity> object : map.entrySet()) {

            Entities objectType = object.getValue().getType();

            if (objectType.equals(lookingType)) {
                Integer keyCurrentObject = object.getKey();

                int lengthToCurrentObject = cordToLength(myKey, keyCurrentObject, widthMap, RADIUS, MOVE);

                if (lengthToCurrentObject <= minLength) {
                    minLength = lengthToCurrentObject;
                    keyNearestObject = keyCurrentObject;
                }
            }
        }
        return keyNearestObject;
    }

    public int[] searchPath(SimulationMap map, int myLocation, Entities lookingType) {
        int sizeMap = map.getSize();
        int widthMap = map.getWidth();

        final int myObjectMark = 0;
        final int wrongObjectMark = sizeMap + 1;
        final int zeroObjectMark = sizeMap +2;
        final int lookingObjectMark = sizeMap +3;


        int mapCount = 1;

        int[] results = new int[3];

        ArrayList<Integer> pathMap = new ArrayList<>(Collections.nCopies(sizeMap, zeroObjectMark));
        pathMap.set(myLocation, myObjectMark);

        for(int i = 0; i < sizeMap; i++) {
            Entity currentObject = map.get(i);
            if (currentObject != null) {
                Entities typeCurrentObject = currentObject.getType();
                if (lookingType.equals(typeCurrentObject)) {
                    pathMap.set(i, lookingObjectMark);
                } else {
                    pathMap.set(i, wrongObjectMark);
                }
                mapCount++;
            }
        }

        int lookingCoordinate = 0;
        int moveCount = 0;
        int[] currentInvestigatedCoordinates;

        boolean isFound = true;
        while ((mapCount < sizeMap) && isFound) {
//        while (true) {
            for (int i = 0; i < sizeMap; i++) {
                if (pathMap.get(i) == moveCount) {
                    currentInvestigatedCoordinates = new int[]{i - widthMap, i + widthMap, i - MOVE, i + MOVE};
                    for (int currentInvestigatedCoordinate : currentInvestigatedCoordinates) {
                        if((currentInvestigatedCoordinate >= 0 && currentInvestigatedCoordinate < sizeMap)){
                            if (pathMap.get(currentInvestigatedCoordinate) == zeroObjectMark) {
                                pathMap.set(currentInvestigatedCoordinate, moveCount + 1);
                                mapCount++;
                            } else if (pathMap.get(currentInvestigatedCoordinate) == lookingObjectMark) {
                                lookingCoordinate = i;
                                break;
                            }
                        }
                    }
                    if (lookingCoordinate != 0){
                        break;
                    }
                }
            }
            if (lookingCoordinate != 0){
                break;
            }
            moveCount++;
        }

        results[0] = moveCount;
        results[1] = lookingCoordinate;

        int lookingCountMove = pathMap.get(lookingCoordinate);
        while (moveCount > myObjectMark + MOVE){
            currentInvestigatedCoordinates = new int[]{lookingCoordinate - widthMap, lookingCoordinate + widthMap, lookingCoordinate - MOVE, lookingCoordinate + MOVE};
            for (int currentInvestigatedCoordinate : currentInvestigatedCoordinates) {
                if ((currentInvestigatedCoordinate >= 0) && (currentInvestigatedCoordinate < sizeMap)
                        && (pathMap.get(currentInvestigatedCoordinate) < lookingCountMove)) {
                    lookingCountMove = pathMap.get(currentInvestigatedCoordinate);
                    lookingCoordinate = currentInvestigatedCoordinate;
                }
            }
            moveCount = lookingCountMove;
        }

        results[2] = lookingCoordinate;

        for (int i = 0; i < sizeMap; i++) {
            System.out.print(pathMap.get(i) + " ");
            if (i % widthMap == widthMap - 1) {
                System.out.println();
            }
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