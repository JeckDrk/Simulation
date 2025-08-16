package Core;

public abstract class Entity {
    private final String symbol;
    private final Entities type;
    protected int idMy = 0;
    static protected int id = 0;
    protected static SimulationMap map;
    protected int x;
    protected int y;
    protected int hp = 1;

    public Entity(String symbol, Entities type) {
        this.symbol = symbol;
        this.type = type;
        this.idMy = id++;
    }

    public boolean isDead(){
        return hp <= 0;
    }

    public void getDamage(int damage) {
        hp -= damage;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setMap(SimulationMap map) {
        Entity.map = map;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getId() {
        return idMy;
    }

    public Entities getType() {
        return type;
    }

    @Override
    public String toString() {
//        return symbol + " " + type + " " + id;
        return symbol;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract boolean isCreature();
}
