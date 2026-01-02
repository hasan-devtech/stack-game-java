import java.util.*;

public class Grid {
    private int rows, cols;
    private char[][] grid;
    private List<Block> blocks;
    private char[][] startState;


    public Grid(int rows,int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new char[rows][cols];
        blocks = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = '.';
            }
        }
        startState = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                startState[i][j] = grid[i][j];
            }
        }
    }
    public Grid(Grid other) {
        this.rows = other.rows;
        this.cols = other.cols;
        this.grid = new char[rows][cols];
        this.blocks = new ArrayList<>();
        for (int i = 0;i < rows;i++) {
            System.arraycopy(other.grid[i], 0, this.grid[i], 0, cols);
        }
        for (Block block : other.blocks) {
            this.blocks.add(new Block(block.getX(), block.getY(), block.getColor()));
        }
    }

    public boolean addBlock(Block block) {
        if (withinGrid(block.getX(),block.getY()) && grid[block.getX()][block.getY()] == '.') {
            blocks.add(block);
            grid[block.getX()][block.getY()] = block.getColor();
            startState[block.getX()][block.getY()] = block.getColor();
            return true;
        } else {
            return false;
        }
    }

    public void printGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean setWall(int x, int y) {
        if (withinGrid(x, y) && grid[x][y] == '.') {
            grid[x][y] = '#';
            startState[x][y] = '#';
            return true;
        } else {
            return false;
        }
    }

    public boolean withinGrid(int x,int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
    }

    public boolean isWall(int x,int y) {
        return withinGrid(x, y) && grid[x][y] == '#';
    }

    public Block getBlockAt(int x,int y) {
        for (Block block : blocks) {
            if (block.getX() == x && block.getY() == y) {
                return block;
            }
        }
        return null;
    }

    public void removeBlock(Block block) {
        grid[block.getX()][block.getY()] = '.';
        blocks.remove(block);
    }

    public void updatePosition(Block block,int newX,int newY) {
        grid[block.getX()][block.getY()] = '.';
        block.setX(newX);
        block.setY(newY);
        grid[newX][newY] = block.getColor();
    }

    public List<Block> getAllBlocks() {
        return new ArrayList<>(blocks);
    }

    public boolean isComplete() {
        Map<Character,Integer> colorCount = new HashMap<>();
        for (Block block : blocks) {
            char color = block.getColor();
            int count = colorCount.getOrDefault(color,0);
            colorCount.put(color,count+ 1);
        }
        List<Integer> counts = new ArrayList<>(colorCount.values());
        for (int count : counts) {
            if (count > 1) {
                return false;
            }
        }
        return true;
    }
    public void reset() {
        blocks.clear();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = startState[i][j];
                char c = startState[i][j];
                if (c !='.' && c !='#') {
                    blocks.add(new Block(i,j,c));
                }
            }
        }
    }
    public String getHash() {
        StringBuilder hash = new StringBuilder();
        for (char[] row : grid) {
            hash.append(new String(row));
        }
        return hash.toString();
    }
    public void winPath(Map<Grid,GridDir> parent, Grid solution){
        LinkedList<Grid> path = new LinkedList<>();
        LinkedList<Direction> dir = new LinkedList<>();
        Grid current = solution;
        int statesToWin = 0;
        while(current != null){
            GridDir value = parent.get(current);
            if(value!=null) {
                path.addFirst(current); 
                dir.addFirst(value.dir);
                current = value.parent;
                statesToWin++;
            }
            else {
                path.addFirst(current);
                current =null;
            }
        }
        System.out.println("Path to win : #######################");
        for (int i=0 ; i<path.size() ; i++){
            System.out.println("State :"+i);
            path.get(i).printGrid();
            if(i< dir.size()){
                System.out.println("Direction: "+dir.get(i));
            }
        }
        System.out.println("Number of state for win: "+statesToWin);
    }

    public int getMoveCost(Direction direction) {
        //MAYBE I WILL USE DIRECTION IN FUTURE
        Map<Character,Integer> colorCount = new HashMap<>();
        for (Block block : this.getAllBlocks()) {
            char color = block.getColor();
            colorCount.put(color, colorCount.getOrDefault(color, 0) + 1);
        }
        int totalCost = 0;
        for (int count : colorCount.values()) {
            if (count > 1) {
                totalCost += (count - 1);
            }
        }
        return totalCost;
    }
    public boolean isInCorner(Block block) {
        int x = block.getX();
        int y = block.getY();
        return (x == 0 && y == 0) || (x == 0 && y == rows - 1)
                || (x == cols - 1 && y == 0)
                || (x == cols- 1 && y == rows - 1);
    }
    public boolean isNearWall(Block block) {
        int x = block.getX();
        int y = block.getY();
        return x == 0 || y == 0 || x == cols - 1 || y == rows - 1;
    }


}
