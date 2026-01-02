public class State {
    private Grid grid;
    private int cost;

    public State(Grid grid, int cost) {
        this.grid = grid;
        this.cost = cost;
    }

    public Grid getGrid() {
        return grid;
    }

    public int getCost() {
        return cost;
    }
}
