package Objects;

import Core.Entities;
import Core.Entity;

public class Grass extends Entity {
    
    private static final String SYMBOL = "\uD83C\uDF3F";
    private static final Entities TYPE = Entities.GRASS;

    public Grass() {
        super(SYMBOL, TYPE);
    }

    public Entities getType(){
        return TYPE;
    }

    @Override
    public boolean isCreature() {
        return false;
    }
}
