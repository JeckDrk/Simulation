import Animals.Creature;
import Animals.Herbivore;
import Core.Entities;
import Core.Entity;
import Core.Simulation;
import Core.SimulationMap;
import Objects.Grass;
import Objects.Rock;
import Objects.Tree;


public class Main {
    public static void main(String[] args) {
        Entity grass = new Grass(1);
        Entity rock = new Rock(1);
        Entity tree = new Tree(1);
        SimulationMap map = new SimulationMap(16,4);
        Creature bunny = new Herbivore(1, map);

        map.putEntity(0, 1, rock);
        map.putEntity(2, 3, rock);
        map.putEntity(3, 1, rock);
        map.putEntity(0, 0, grass);
        map.putEntity(1, 1, tree);
        map.putEntity(0, 2, bunny);

        map.showMap();

        bunny.showMove(0, 2, Entities.GRASS);
//        int[] result = bunny.searchPath(map, 0, 5, Entities.GRASS);
//        System.out.println(result[0] + " " + result[1] + " " + result[2]);
    }
}
