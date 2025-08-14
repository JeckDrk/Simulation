package Animals;

import Core.Entities;
import Core.Entity;
import Core.SimulationMap;

import java.util.Map;

import static java.lang.Math.abs;

public class Herbivore extends Creature {

    private static final String SYMBOL = "\uD83D\uDC07";
    private static final Entities TYPE = Entities.HERBIVORE;

    private static final Entities FOOD = Entities.GRASS;
    private static final Entities DANGER = Entities.PREDATOR;


    public Herbivore(int id, SimulationMap map) {
        super(SYMBOL, TYPE, id, map);
    }

//    @Override
//    public int getMove(Map<Integer, Entity> map, int widthMap, int myKey) {
//
//        int keyToNearestFood = getNearestObjectTypeOf(map, widthMap, myKey, FOOD);
//        int keyToNearestDanger = getNearestObjectTypeOf(map, widthMap, myKey, DANGER);
//
//        int lengthToFood = cordToLength(myKey, keyToNearestFood, widthMap, RADIUS, MOVE);
//        int lengthToDanger = cordToLength(myKey, keyToNearestDanger, widthMap, RADIUS, MOVE);
//
//        if ((lengthToDanger < lengthToFood) && (keyToNearestDanger != ERROR)) {
//            return getMoveFromDanger();
//        } else if (keyToNearestFood != ERROR) {
//            return getMoveToFood();
//        } else {
//            return getMoveRandom();
//        }
//    }

//    int getMoveFromDanger(Map<Integer, Entity> map, int widthMap, int myIndex, ){
//
//    }
//
//    int getMoveToFood(){
//
//    }
//
//    int getMoveRandom(){
//
//    }

//    @Override
//    public void eat() {
//
//    }
}
