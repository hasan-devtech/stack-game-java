import java.util.*;

public class HillClimbingV2 {
    private Grid initGrid;
    private Set<String> visited;
    private Map<Grid,GridDir> parent;
    private int generatedStates;
    private int visitedStates;
    private long time;

    public HillClimbingV2(Grid grid) {
        this.initGrid = grid;
        this.visited = new HashSet<>();
        this.parent = new HashMap<>();
        this.time = 0;
    }

    public boolean solve() {
        System.out.println("Starting Enhanced Hill Climbing  ..........................................................................................");
        long startTime = System.nanoTime();
        Heuristic heuristic = new Heuristic();
        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(State::getCost));
        int initialHeuristic = heuristic.calculateHeuristic(initGrid);
        pq.add(new State(initGrid, initialHeuristic));
        parent.put(initGrid, null);
        visited.add(initGrid.getHash());

        while (!pq.isEmpty()) {
            State currentState = pq.poll();
            Grid thisGrid = currentState.getGrid();
//            int currentHeuristic = currentState.getHeuristic();
            if (thisGrid.isComplete()) {
                long endTime = System.nanoTime();
                time = endTime - startTime;
                System.out.println("Solution Found");
                thisGrid.winPath(parent, thisGrid);
                System.out.println("Number of states visited in HillClimbing >>>>: "+visitedStates);
                System.out.println("Number of states generated in HillClimbing >>>>: "+generatedStates);
                System.out.println("Execution Time (ms) in HillClimbing >>>>: " +time / 1_000_000);
                return true;
            }
            for (Direction dir : Direction.values()) {
                Grid copy = new Grid(thisGrid);
                Mover m = new Mover(copy);
                m.moveAllBlocks(dir);
                String gridHash = copy.getHash();
                if (!visited.contains(gridHash)) {
                    int newHeuristic = heuristic.calculateHeuristic(copy);
                    pq.add(new State(copy,newHeuristic));
                    parent.put(copy,new GridDir(thisGrid,dir));
                    visited.add(gridHash);
                    visitedStates++;
                }
                generatedStates++;
            }
        }

        long endTime = System.nanoTime();
        time = endTime - startTime;
        System.out.println("No Solution Found");
        System.out.println("Number of states visited in HillClimbing >>>>: "+visitedStates );
        System.out.println("Number of states generated in HillClimbing >>>>: "+generatedStates );
        System.out.println("Execution Time (ms): " + time / 1_000_000);
        return false;
    }

}
