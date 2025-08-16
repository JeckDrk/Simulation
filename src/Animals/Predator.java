package Animals;

import Core.Entities;
import Core.Sides;

public class Predator extends Creature {

    private static final String SYMBOL = "\uD83D\uDC3A";
    private static final Entities TYPE = Entities.PREDATOR;

    private static final Entities FOOD = Entities.HERBIVORE;

    private static final int HP = 30;

    public Predator() {
        super(SYMBOL, TYPE, HP);
        hp = 70;
        food = 30;
        range = 8;
    }

    @Override
    public void makeMove() {
        if(food <= 0){
            getDamage(10);
        } else {
            food--;
        }
        int lengthToFood = howLong(FOOD);
        Sides sideFood = getTargetSide(lengthToFood);
        int[] coordsFood = getTargetObjectCords();
        if (lengthToFood != 1 && sideFood != Sides.NONE) {
            makeMoveToFood(sideFood);
        } else if (lengthToFood == 1 && sideFood == Sides.NONE) {
            makeEat(coordsFood[0], coordsFood[1]);
            food += 5;
        } else {
            makeMoveRandom();
        }
    }

    private void makeEat(int x, int y){
        map.getEntity(x,y).getDamage(909090);
        food += 10;
    }
}
