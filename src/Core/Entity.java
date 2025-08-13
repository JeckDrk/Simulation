package Core;

public class Entity {
    private final String symbol;
    private final Entities type;
    protected int id;

    public Entity(String symbol, Entities type, int id) {
        this.symbol = symbol;
        this.type = type;
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getId() {
        return id;
    }

    public Entities getType() {
        return type;
    }

    @Override
    public String toString() {
//        return symbol + " " + type + " " + id;
        return symbol;
    }
}
