package Core;

import Animals.Herbivore;
import Animals.Predator;
import Objects.Grass;
import Objects.Rock;
import Objects.Tree;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Simulation {
    private static SimulationMap map;
    private final int width;
    private final int height;
    private int stepCounter;
    private final List<Entity> entities;

    public Simulation(SimulationMap map) {
        Simulation.map = map;
        width = map.getWidth();
        height = map.getHigh();
        entities = new LinkedList<>();
        InitActions initActions = new InitActions(this);
        initActions.doActions();
    }

    public void startSimulation() {
        TurnActions actions = new TurnActions(this);
        Scanner scanner;
        for(;true;){
            System.out.println("1 - следующий ход, 2 - сделать несколько ходов, 3 - добавить объектов, 0 - выход");
            scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            switch(input){
                case "1" -> {
                    actions.nextTurn();
                }
                case "2" -> {
                    System.out.println("Сколько: ");
                    scanner = new Scanner(System.in);
                    int intInput;
                    try {
                        intInput = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка ввода! Что-то введено не правильно!");
                        intInput = 0;
                    }
                    for(int i = 0; i < intInput; i++){
                        actions.nextTurn();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                case "3" -> {
                    System.out.println("1 - камень, 2 - трава, 3 - дерево, 4 - кролик, 5 - волк: ");
                    String type = new Scanner(System.in).nextLine();
                    System.out.println("Введите число: ");
                    int intInput;
                    try {
                        intInput = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка ввода! Что-то введено не правильно!");
                        intInput = 0;
                    }
                    switch(type) {
                        case "1" -> {
                            addEntitiesInList(Entities.ROCK, intInput);
                        }
                        case "2" -> {
                            addEntitiesInList(Entities.GRASS, intInput);
                        }
                        case "3" -> {
                            addEntitiesInList(Entities.TREE, intInput);
                        }
                        case "4" -> {
                            addEntitiesInList(Entities.HERBIVORE, intInput);
                        }
                        case "5" -> {
                            addEntitiesInList(Entities.PREDATOR, intInput);
                        }
                    }
                    addEntitiesOnMap(intInput);
                    printMap();
                }
                case "9" -> {
                    return;
                }
            }
        }
    }

    public void increaseStepCounter(){
        stepCounter++;
    }

    public void addEntitiesInList(Entities type, int count) {
        for (int i = 0; i < count; i++) {
            switch (type) {
                case Entities.TREE -> entities.add(new Tree());
                case Entities.ROCK -> entities.add(new Rock());
                case Entities.HERBIVORE -> entities.add(new Herbivore());
                case Entities.PREDATOR -> entities.add(new Predator());
                default -> entities.add(new Grass());
            }
        }
    }

    public void addEntitiesOnMap(int count) {
        int x, y;
        for (int i = entities.size() - count; i < entities.size();) {
            x = (int) (Math.random() * width);
            y = (int) (Math.random() * height);
            if (map.putEntity(x,y,entities.get(i))){
                i++;
            }
        }
    }

    public void addEntitiesOnMap() {
        int x, y;
        if(entities.size() != width * height){
            for (int i = 0; i < entities.size(); i++) {
                x = (int) (Math.random() * width);
                y = (int) (Math.random() * height);
                if (map.putEntity(x,y,entities.get(i))){
                    i++;
                }
            }
        }
    }

    public void nextTurn() {
        for (Entity entity : entities) {
            if (entity instanceof Predator predator) {
                predator.makeMove();
            }
        }
        for (Entity entity : entities) {
            if (entity instanceof Herbivore herbivore) {
                herbivore.makeMove();
            }
        }
    }

    public void checkDeadEntities() {
        while (true){
            for (int i = 0; i < entities.size(); i++) {
                if (entities.get(i).isDead()){
                    Entity entity = entities.get(i);
                    System.out.println("Entity die: " + entity.getSymbol() + " " + entity.getId());
                    map.removeEntity(entity.getX(), entity.getY());
                    entities.remove(i);
                    break;
                }
                if(i >= entities.size()-1){
                    return;
                }
            }
        }

    }

    public void printMap() {
        System.out.println("Ход: " + stepCounter);
        map.printMap();
    }

    private static class InitActions {
        Simulation simulation;

        InitActions(Simulation simulation){
            this.simulation = simulation;
        }

        private void doActions(){
            int size = simulation.width * simulation.height;
            simulation.addEntitiesInList(Entities.GRASS, (int)(size * 0.1) + 1);
            simulation.addEntitiesInList(Entities.TREE, (int)(size * 0.05));
            simulation.addEntitiesInList(Entities.ROCK, (int)(size * 0.05));
            simulation.addEntitiesInList(Entities.HERBIVORE, (int)(size * 0.05) + 1);
            simulation.addEntitiesInList(Entities.PREDATOR, (int)(size * 0.01) + 1);
            simulation.addEntitiesOnMap();
            simulation.printMap();
        }

    }
    private static class TurnActions {
        Simulation simulation;

        TurnActions(Simulation simulation){
            this.simulation = simulation;
        }

        public void nextTurn(){
            simulation.checkDeadEntities();
            simulation.nextTurn();
            simulation.increaseStepCounter();
            simulation.printMap();
        }
    }
}
