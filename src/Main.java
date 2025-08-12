import Animals.Creature;
import Animals.Herbivore;
import Core.Entities;
import Core.Entity;
import Core.Simulation;
import Core.SimulationMap;
import Objects.Grass;
import Objects.Rock;


public class Main {
    public static void main(String[] args) {
        Creature bunny = new Herbivore(1);
        Entity grass = new Grass(1);
        Entity rock = new Rock(1);
        SimulationMap map = new SimulationMap(36,6);
        map.putEntity(1, rock);
        map.putEntity(11, rock);
        map.putEntity(21, rock);
        map.putEntity(5, grass);
        int[] result = bunny.searchPath(map, 22, Entities.GRASS);
        System.out.println(result[0] + " " + result[1] + " " + result[2]);
    }
}
