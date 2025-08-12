package Objects;

import Core.Entities;
import Core.Entity;

public class Grass extends Entity {
    
    private static final String SYMBOL = "\uD83C\uDF3F";
    private static final Entities TYPE = Entities.GRASS;

    public Grass(int id) {
        super(SYMBOL, TYPE, id);
    }

    public Entities getType(){
        return TYPE;
    }
}
