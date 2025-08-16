package Objects;

import Core.Entities;
import Core.Entity;

public class Rock extends Entity {

    private static final String SYMBOL = "\uD83D\uDDFF";
    private static final Entities TYPE = Entities.ROCK;

    public Rock() {
        super(SYMBOL, TYPE);
    }

    @Override
    public boolean isCreature() {
        return false;
    }
}
