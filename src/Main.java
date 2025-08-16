import Core.Simulation;
import Core.SimulationMap;

public class Main {
    public static void main(String[] args) {
        SimulationMap simulationMap = new SimulationMap(40, 15);
        Simulation simulation = new Simulation(simulationMap);
        simulation.startSimulation();
    }
}
