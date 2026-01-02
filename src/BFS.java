import java.util.*;

public class BFS {
    private Grid initGrid;
    private Set<String> visited;
    private Map<Grid,GridDir> parent;
    private int generatedStates;
    private int visitedStates;
    private long time;

    public BFS(Grid grid) {
        this.initGrid = grid;
        this.visited = new HashSet<>();
        this.parent = new HashMap<>();
        this.generatedStates = 0;
        this.visitedStates = 0;
        this.time = 0;
    }

    public boolean solve() {
        System.out.println("Starting BFS ..........................................................................................");
        long startTime = System.nanoTime();
        Queue<Grid> q = new LinkedList<>();
        q.add(initGrid);
        parent.put(initGrid, null);
        visited.add(initGrid.getHash());
        visitedStates++;
        while (!q.isEmpty()) {
            Grid thisGrid = q.poll();
            if (thisGrid.isComplete()) {
                long endTime = System.nanoTime();
                time = endTime - startTime;
                System.out.println("Solution Found");
                thisGrid.winPath(parent,thisGrid);
                System.out.println("Number of states visited in BFS >>>>: "+visitedStates);
                System.out.println("Number of states generated in BFS >>>>: "+generatedStates);
                System.out.println("Execution Time (ms) in BFS >>>>: " + time / 1_000_000);
                return true;
            }
            for (Direction dir : Direction.values()) {
                Grid copy = new Grid(thisGrid);
                Mover m = new Mover(copy);
                m.moveAllBlocks(dir);
                String gridHashed = copy.getHash();
                if (!visited.contains(gridHashed)) {
                    q.add(copy);
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
        System.out.println("Number of states visited: " + visitedStates);
        System.out.println("Number of states generated: " + generatedStates);
        System.out.println("Execution Time (ms): " + time / 1_000_000);
        return false;
    }


}
