import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Heuristic {
    public int calculateHeuristic(Grid grid) {
        int total = 0;
        Map<Character,List<Block>> colorGroups = new HashMap<>();
        for (Block block : grid.getAllBlocks()) {
            char color = block.getColor();
            colorGroups.putIfAbsent(color,new ArrayList<>());
            colorGroups.get(color).add(block);
        }
        for (List<Block> sameColorBlocks : colorGroups.values()) {
            int distances = 0;
            if (sameColorBlocks.size() > 1) {
                for (int i = 0; i < sameColorBlocks.size(); i++) {
                    Block block1 = sameColorBlocks.get(i);
                    for (int j = i+1; j < sameColorBlocks.size(); j++) {
                        Block block2 = sameColorBlocks.get(j);
                        distances += Math.abs(block1.getX()-block2.getX())+Math.abs(block1.getY()-block2.getY());
                    }
                }
            }
            total += distances;
            for (Block block : sameColorBlocks) {
                if (grid.isInCorner(block) || grid.isNearWall(block)) {
                    total += 3;
                }
            }
        }
        return total    ;
    }

}


