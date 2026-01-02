import java.util.List;

public class Mover {
    private Grid grid;
    public Mover(Grid grid) {
        this.grid = grid;
    }
    public void moveAllBlocks(Direction direction) {
        List<Block> blocks = grid.getAllBlocks();
        blocks.sort((a,b)->compare(a,b,direction));
        for (Block block : blocks) {
            moveBlock(block,direction);
        }
    }

    private void moveBlock(Block block,Direction direction) {
        while (true) {
            int newX = block.getX()+direction.dx;
            int newY = block.getY()+direction.dy;
            if (!grid.withinGrid(newX,newY) || grid.isWall(newX,newY)) {
                break;
            }
            Block otherBlock = grid.getBlockAt(newX,newY);
            if (otherBlock!=null) {
                if (otherBlock.getColor() == block.getColor()) {
                    grid.removeBlock(block);
                }
                break;
            }
            grid.updatePosition(block,newX,newY);
        }
    }

    private int compare(Block a,Block b,Direction direction) {
        switch (direction) {
            case UP:
                return a.getX()-b.getX();
            case DOWN:
                return b.getX()-a.getX();
            case LEFT:
                return a.getY()-b.getY();
            case RIGHT:
                return b.getY()-a.getY();
            default:
                return 0;
        }
    }
}
