import java.util.*;

public class HillClimbing {
    private Grid initGrid;
    private Set<String> visited;
    private Map<Grid,GridDir> parent;
    private int states;
    private long executionTime;
    private Heuristic heuristicCalculator;

    public HillClimbing(Grid grid) {
        this.initGrid = grid;
        this.visited = new HashSet<>();
        this.parent = new HashMap<>();
        this.states = 0;
        this.executionTime = 0;
        this.heuristicCalculator = new Heuristic();
    }

    public boolean solve() {
        System.out.println("Starting Hill Climbing ...");
        long startTime = System.nanoTime();
        Grid currentGrid = initGrid;
        visited.add(currentGrid.getHash());
        parent.put(currentGrid, null);
        states++;
        int iterationsWithoutImprovement = 0;
        while (true) {
            System.out.println("State: " + states);
            currentGrid.printGrid();
            if (currentGrid.isComplete()) {
                long endTime = System.nanoTime();
                executionTime = endTime - startTime;
                System.out.println("Solution Found");
                currentGrid.winPath(parent, currentGrid);
                System.out.println("Number of states: " + states);
                System.out.println("Execution Time (ms): " + executionTime / 1_000_000);
                return true;
            }

            Grid bestNeighbor = null;
            int bestHeuristic = Integer.MAX_VALUE;
            boolean betterNeighborFound = false;

            for (Direction dir : Direction.values()) {
                Grid copy = new Grid(currentGrid);
                Mover m = new Mover(copy);
                m.moveAllBlocks(dir);
                String gridHashed = copy.getHash();

                if (!visited.contains(gridHashed)) {
                    visited.add(gridHashed);
                    states++;
                    int heuristicValue = heuristicCalculator.calculateHeuristic(copy);
                    if (heuristicValue < bestHeuristic) {
                        bestHeuristic = heuristicValue;
                        bestNeighbor = copy;
                        parent.put(copy, new GridDir(currentGrid, dir));
                        betterNeighborFound = true;
                    }
                }
            }

            if (!betterNeighborFound) {
                iterationsWithoutImprovement++;
                System.out.println("No better neighbor found. Iteration: " + iterationsWithoutImprovement);
                if (iterationsWithoutImprovement > 100) {
                    System.out.println("Maximum iterations without improvement reached. Stopping.");
                    break;
                }
            }

            if (bestNeighbor == null || heuristicCalculator.calculateHeuristic(bestNeighbor) >= heuristicCalculator.calculateHeuristic(currentGrid)) {
                if (iterationsWithoutImprovement > 50) {
                    System.out.println("Solution not found after some time, but continuing.");
                    break;
                }
            }
            if (bestNeighbor != null) {
                currentGrid = bestNeighbor;
                visited.add(currentGrid.getHash());
                states++;
                iterationsWithoutImprovement = 0;
            }
        }
        long endTime = System.nanoTime();
        executionTime = endTime - startTime;
        System.out.println("No Solution Found");
        System.out.println("Number of states visited: " + states);
        System.out.println("Execution Time (ms): " + executionTime / 1_000_000);
        return false;
    }
}
