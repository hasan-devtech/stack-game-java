import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;

public class RecDFS {
    private Grid initGrid;
    private Set<String> visited;
    private Map<Grid,GridDir> parent;
    private int generatedStates;
    private int visitedStates;
    private long time;

    public RecDFS(Grid grid) {
        this.initGrid = grid;
        this.visited = new HashSet<>();
        this.parent = new HashMap<>();
        this.visitedStates = 0;
        this.generatedStates = 0;
    }

    public boolean solve() {
        System.out.println("Starting Recursive DFS ..........................................................................................");
        long startTime = System.nanoTime();

        parent.put(initGrid, null);
        if (dfs(initGrid)) {
            long endTime = System.nanoTime();
            time = endTime - startTime;
            Grid finalGrid = getFinalGrid();
            if (finalGrid != null) {
                finalGrid.winPath(parent,finalGrid);
                System.out.println("Solution Found");
                System.out.println("Number of States Visited in Recursive DFS >>>>: "+visitedStates);
                System.out.println("Number of States Generates in Recursive DFS >>>>: "+generatedStates);
                System.out.println("Execution Time in Recursive DFS >>>> (micro second):  " + time / 1_000);
            }
            return true;
        } else {
            long endTime = System.nanoTime();
            time = endTime - startTime;
            System.out.println("No Solution");
            System.out.println("Number of States Visited in Recursive DFS >>>>: " );
            System.out.println("Execution Time (ms) in Recursive DFS >>>>: " + time / 1_000_000);
            return false;
        }
    }

    private boolean dfs(Grid thisGrid) {
        String gridHash = thisGrid.getHash();
        if (visited.contains(gridHash)) {
            return false;
        }
        visited.add(gridHash);
        visitedStates++;
        if (thisGrid.isComplete()) {
            return true;
        }

        for (Direction dir : Direction.values()) {
            Grid copy = new Grid(thisGrid);
            Mover m = new Mover(copy);
            m.moveAllBlocks(dir);
            String childHash = copy.getHash();
            if (!visited.contains(childHash)) {
                parent.put(copy,new GridDir(thisGrid,dir));
                generatedStates++;
                if (dfs(copy)) {
                    return true;
                }
            }
        }
        return false;
    }

    private Grid getFinalGrid() {
        for (Grid grid : parent.keySet()) {
            if (grid.isComplete()) {
                return grid;
            }
        }
        return null;
    }
}
