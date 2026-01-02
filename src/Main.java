import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
         Scanner scanner = new Scanner(System.in);
         System.out.print("Enter number of rows: ");
         int rows = scanner.nextInt();
         scanner.nextLine();
         System.out.print("Enter number of columns: ");
         int cols = scanner.nextInt();
         scanner.nextLine();
         Grid grid = new Grid(rows,cols);
         int wallCount;
         while (true) {
             System.out.print("Enter number of walls: ");
             wallCount = scanner.nextInt();
             scanner.nextLine();
             if (wallCount > rows*cols || wallCount < 0) {
                 System.out.println("Invalid Count: The number of walls cannot exceed "+(rows * cols)+"and greater or equal to 0");
             }
             else {
                 break;
             }
         }
         for (int i = 0; i < wallCount; i++) {
             while (true) {
                 System.out.print("Enter wall "+(i + 1)+" position (row and column): ");
                 int wallX = scanner.nextInt();
                 int wallY = scanner.nextInt();
                 if (grid.setWall(wallX,wallY)) {
                     break;
                 }
                 else {
                     System.out.println("Invalid position: Cannot place wall at ("+ wallX +", " +wallY+ ")");
                 }
             }
         }
         int blockCount;
         while (true){
             System.out.print("Enter number of blocks: ");
             blockCount = scanner.nextInt();
             scanner.nextLine();
             if(blockCount < 0 || blockCount > (rows*cols) - wallCount) {
                 System.out.println("Invalid Count: The number of blocks cannot exceed "+(rows * cols - wallCount)+"and greater than 0");
             }
             else {
                 break;
             }

         }
         for (int i = 0; i < blockCount; i++) {
             while (true){
                 System.out.print("Enter block "+(i + 1)+" position (row and column): ");
                 int blockX = scanner.nextInt();
                 int blockY = scanner.nextInt();
                 System.out.print("Enter block color (single character): ");
                 char color = scanner.next().charAt(0);
                 Block block = new Block(blockX,blockY,color);
                 if(grid.addBlock(block)){
                     break;
                 }
                 System.out.println("Invalid position: Cannot place block at ("+blockX+ ", "+blockY+")");
             }
         }
         grid.printGrid();
         Mover mover = new Mover(grid);
         scanner.nextLine();
         while (true) {
             System.out.println("Enter a move (up(w),down(s),left(a),right(d)) or 'q' to quit:");
             System.out.println("Enter a move (bfs(b),dfs(dfs),recDfs(rdfs),ucs(ucs)),hillclimbing (h) ,astar(star) or 'q' to quit:");
             System.out.println("Enter a (r) to restart the game:");
             String input = scanner.nextLine().trim().toLowerCase();
             if (input.equals("q")) {
                 System.out.println("Ended by user.");
                 break;
             }
             if (input.equals("b")) {
                 BFS bfs = new BFS(grid);
                 bfs.solve();
             }
             if (input.equals("dfs")) {
                 DFS dfs = new DFS(grid);
                 dfs.solve();
             }
             if (input.equals("rdfs")) {
                 RecDFS recDFS = new RecDFS(grid);
                 recDFS.solve();
             }
             if (input.equals("ucs")) {
                 UCS ucs = new UCS(grid);
                 ucs.solve();
             }
             if (input.equals("h")) {
                 HillClimbingV2 hill2 = new HillClimbingV2(grid);
                 hill2.solve();
             }
             if (input.equals("star")) {
                 AStar a = new AStar(grid);
                 a.solve();
             }

             if (input.equals("r")) {
                 grid.reset();
                 grid.printGrid();
                 continue;
             }
             Direction direction;
             switch (input) {
                 case "w":direction = Direction.UP;
                 break;
                 case "s":direction = Direction.DOWN;
                 break;
                 case "a":direction = Direction.LEFT;
                 break;
                 case "d":direction = Direction.RIGHT;
                 break;
                 default:
                     System.out.println("Please enter up,down,left,right");
                     continue;
             }
             mover.moveAllBlocks(direction);
             grid.printGrid();
             if (grid.isComplete()) {
                 System.out.println("Congratulations");
                 break;
             }
         }
        scanner.close();

    }

}
