package Objects;

import Core.Entities;
import Core.Entity;

public class Tree extends Entity {

    private static final String SYMBOL = "\uD83C\uDF32";
    private static final Entities TYPE = Entities.TREE;


    public Tree() {
        super(SYMBOL, TYPE);
    }

    @Override
    public boolean isCreature() {
        return false;
    }
}
