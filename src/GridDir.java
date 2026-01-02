public class GridDir {
    public Grid parent;
    public Direction dir;
    public GridDir(Grid grid,Direction dir){
        this.parent = grid;
        this.dir =dir;
    }
}
