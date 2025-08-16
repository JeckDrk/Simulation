package Animals;

import Core.*;

public class Herbivore extends Creature {

    private static final String SYMBOL = "\uD83D\uDC07";
    private static final Entities TYPE = Entities.HERBIVORE;

    private static final Entities FOOD = Entities.GRASS;
    private static final Entities DANGER = Entities.PREDATOR;

    private static final int HP = 30;

    public Herbivore() {
        super(SYMBOL, TYPE, HP);
        food = 20;
        range = 6;
    }

    @Override
    public void makeMove(){
        if(food <= 0){
            getDamage(10);
        } else {
            food--;
        }
        int lengthToDanger = howLong(DANGER);
        Sides sideDanger = getTargetSide(lengthToDanger);
        int lengthToFood = howLong(FOOD);
        Sides sideFood = getTargetSide(lengthToFood);
        int[] coordsFood = getTargetObjectCords();
        if (DANGER == Entities.PREDATOR && lengthToDanger < lengthToFood) {
            makeMoveFromDanger(sideDanger);
        } else if (lengthToFood != 1 && sideFood != Sides.NONE) {
            makeMoveToFood(sideFood);
        } else if (lengthToFood == 1 && sideFood == Sides.NONE) {
            makeEat(coordsFood[0], coordsFood[1]);
            food += 5;
        } else {
            makeMoveRandom();
        }
    }

    private void makeEat(int x, int y){
        map.getEntity(x,y).getDamage(1);
        food += 10;
    }

    private void makeMoveFromDanger(Sides side){
        boolean[] myMoves = map.getMoves(x,y);
        if (myMoves[2] && side != Sides.UP){
            map.moveEntity(x,y,Sides.DOWN);
        } else if (myMoves[1] && side != Sides.LEFT) {
            map.moveEntity(x,y,Sides.RIGHT);
        } else if (myMoves[3] && side != Sides.RIGHT){
            map.moveEntity(x,y,Sides.LEFT);
        } else if (myMoves[0] && side != Sides.DOWN){
            map.moveEntity(x,y,Sides.UP);
        }
    }

    @Override
    public boolean isCreature() {
        return true;
    }
}
