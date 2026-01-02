import java.util.*;

public class AStar  {
    private Grid initGrid;
    private Set<String> visited;
    private Map<Grid,GridDir> parent;
    private int visitedStates;
    private int generatedStates;
    private long time;
    private Heuristic heuristic;

    public AStar(Grid grid) {
        this.initGrid = grid;
        this.visited = new HashSet<>();
        this.parent = new HashMap<>();
        this.time = 0;
        this.heuristic = new Heuristic();
    }

    public boolean solve() {
        System.out.println("Starting A* Search ....................................................................................................");
        long startTime = System.nanoTime();
        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(State::getCost));
        pq.add(new State(initGrid,0));
        parent.put(initGrid, null);
        visited.add(initGrid.getHash());

        while (!pq.isEmpty()) {
            State currentState = pq.poll();
            Grid thisGrid = currentState.getGrid();
            int currentCost = currentState.getCost();
//            System.out.println("State: " + states);
//            thisGrid.printGrid();
            if (thisGrid.isComplete()) {
                long endTime = System.nanoTime();
                time = endTime - startTime;
                System.out.println("Solution Found");
                thisGrid.winPath(parent, thisGrid);
                System.out.println("Number of states visited in A* >>>>: "+visitedStates);
                System.out.println("Number of states generated in A* >>>>: "+generatedStates);
                System.out.println("Execution Time (ms) in A* >>>>: " + time / 1_000_000);
                return true;
            }

            for (Direction dir : Direction.values()) {
                Grid copy = new Grid(thisGrid);
                Mover m = new Mover(copy);
                m.moveAllBlocks(dir);
                String gridHashed = copy.getHash();
                if (!visited.contains(gridHashed)) {
                    int newCost = currentCost+copy.getMoveCost(dir);
                    int heuristicValue = heuristic.calculateHeuristic(copy);
                    int fCost = newCost + heuristicValue;
                    pq.add(new State(copy,fCost));
                    parent.put(copy,new GridDir(thisGrid, dir));
                    visited.add(gridHashed);
                    visitedStates++;
                }
                generatedStates++;
            }
        }

        long endTime = System.nanoTime();
        time = endTime - startTime;
        System.out.println("No Solution Found");
        System.out.println("Number of states visited: " );
        System.out.println("Execution Time (ms): " + time / 1_000_000);
        return false;
    }

}
