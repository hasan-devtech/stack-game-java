import java.util.*;

public class UCS {
    private Grid initGrid;
    private Set<String> visited;
    private Map<Grid,GridDir> parent;
    private int generatedStates;
    private int visitedStates;
    private long time;

    public UCS(Grid grid) {
        this.initGrid = grid;
        this.visited = new HashSet<>();
        this.parent = new HashMap<>();
        this.generatedStates = 0;
        this.visitedStates = 0;
        this.time = 0;
    }

    public boolean solve() {
        System.out.println("Starting Uniform Cost Search ..........................................................................................");
        long startTime = System.nanoTime();
        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(State::getCost));
        pq.add(new State(initGrid,0));
        parent.put(initGrid, null);
        visited.add(initGrid.getHash());
        visitedStates++;
        while (!pq.isEmpty()) {
            State currentState = pq.poll();
            Grid thisGrid = currentState.getGrid();
            int currentCost = currentState.getCost();
            if (thisGrid.isComplete()) {
                long endTime = System.nanoTime();
                time = endTime - startTime;
                System.out.println("Solution Found");
                thisGrid.winPath(parent,thisGrid);
                System.out.println("Number of states visited in UCS >>>>: "+visitedStates);
                System.out.println("Number of states generated in UCS >>>: "+generatedStates);
                System.out.println("Execution Time (ms) in UCS >>>>: "+time / 1_000_000);
                return true;
            }
            for (Direction dir : Direction.values()) {
                Grid copy = new Grid(thisGrid);
                Mover m = new Mover(copy);
                m.moveAllBlocks(dir);
                String gridHashed = copy.getHash();
                if (!visited.contains(gridHashed)) {
                    int newCost = currentCost + copy.getMoveCost(dir);
                    pq.add(new State(copy,newCost));
                    parent.put(copy,new GridDir(thisGrid,dir));
                    visited.add(gridHashed);
                    visitedStates++;
                }
                generatedStates++;
            }
        }
        long endTime = System.nanoTime();
        time = endTime - startTime;
        System.out.println("No Solution Found");
        System.out.println("Number of states visited: "+visitedStates);
        System.out.println("Number of states generated: "+generatedStates);
        System.out.println("Execution Time (ms): "+time / 1_000_000);
        return false;
    }

}
