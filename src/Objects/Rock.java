package Objects;

import Core.Entities;
import Core.Entity;

public class Rock extends Entity {

    private static final String SYMBOL = "\uD83D\uDDFF";
    private static final Entities TYPE = Entities.ROCK;

    public Rock(int id) {
        super(SYMBOL, TYPE, id);
    }
}
