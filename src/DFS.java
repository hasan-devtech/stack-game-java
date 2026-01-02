import java.util.*;

public class DFS {
    private Grid initGrid;
    private Set<String> visited;
    private Map<Grid,GridDir> parent;
    private int generatedStates;
    private int visitedStates;

    private long time;

    public DFS(Grid grid) {
        this.initGrid = grid;
        this.visited = new HashSet<>();
        this.parent = new HashMap<>();
        this.time = 0;
    }
    public boolean solve() {
        System.out.println("Starting DFS..........................................................................................");
        long startTime = System.nanoTime();
        Stack<Grid> s = new Stack<>();
        s.push(initGrid);
        parent.put(initGrid,null);
        visited.add(initGrid.getHash());
        visitedStates++;
        while (!s.isEmpty()) {
            Grid thisGrid = s.pop();
//            System.out.println("State: ");
//            thisGrid.printGrid();
//            states++;
            if (thisGrid.isComplete()) {
                long endTime = System.nanoTime();
                time = endTime - startTime;
                System.out.println("Solution Found: ");
                thisGrid.winPath(parent,thisGrid);
                System.out.println("Number of states visited in DFS >>>>: "+visitedStates);
                System.out.println("Number of states generated in DFS >>>>: "+generatedStates);
                System.out.println("Execution Time (micro second) in DFS >>>>: " + time / 1_000);
                return true;
            }
            for (Direction dir : Direction.values()) {
                Grid copy = new Grid(thisGrid);
                Mover mover = new Mover(copy);
                mover.moveAllBlocks(dir);
                String gridHash = copy.getHash();
                if (!visited.contains(gridHash)) {
                    visited.add(gridHash);
                    parent.put(copy,new GridDir (thisGrid,dir));
                    visitedStates++;
                    s.push(copy);
                }
                generatedStates++;
            }
        }
        long endTime = System.nanoTime();
        time = endTime - startTime;
        System.out.println("No Solution Found");
        System.out.println("Number of states visited in DFS >>> : ");
        System.out.println("Execution Time (ms): "+time / 1_000_000);
        return false;
    }
}
