package teamport.aether.world.generate.feature.components;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldFeatureSilverMaze {
    // Graph
    static Map<Integer, List<Integer>> GRAPH = new HashMap<>();
    static {
        // I am not going to generate this because it easier and safer to just write it down
        GRAPH.put(0, Arrays.asList(1, 3, 9));
        GRAPH.put(1, Arrays.asList(0, 2, 4));
        GRAPH.put(2, Arrays.asList(1, 5, 11));
        GRAPH.put(3, Arrays.asList(0, 4, 12));
        GRAPH.put(4, Arrays.asList(1, 3, 5, 7, 13));
        GRAPH.put(5, Arrays.asList(2, 4, 8, 14));
        GRAPH.put(6, Arrays.asList(15));
        GRAPH.put(7, Arrays.asList(4, 8, 16));
        GRAPH.put(8, Arrays.asList(5, 7, 17));
        GRAPH.put(9, Arrays.asList(0, 10, 12, 18));
        GRAPH.put(10, Arrays.asList(1, 9, 11, 13, 19));
        GRAPH.put(11, Arrays.asList(2, 10, 14, 20));
        GRAPH.put(12, Arrays.asList(2, 9, 13, 21));
        GRAPH.put(13, Arrays.asList(4, 10, 12, 14, 16, 22));
        GRAPH.put(14, Arrays.asList(5, 11, 13, 17, 23));
        GRAPH.put(15, Arrays.asList(6, 24));
        GRAPH.put(16, Arrays.asList(7, 13, 17, 25));
        GRAPH.put(17, Arrays.asList(8, 14, 16, 26));
        GRAPH.put(18, Arrays.asList(9, 19, 21));
        GRAPH.put(19, Arrays.asList(10, 18, 20, 22));
        GRAPH.put(20, Arrays.asList(11, 19, 23));
        GRAPH.put(21, Arrays.asList(12, 18, 22, 24));
        GRAPH.put(22, Arrays.asList(13, 19, 21, 23, 25));
        GRAPH.put(23, Arrays.asList(14, 20, 22, 26));
        GRAPH.put(24, Arrays.asList(15, 21, 25));
        GRAPH.put(25, Arrays.asList(16, 22, 24, 26));
        GRAPH.put(26, Arrays.asList(17, 23, 25));
    }
}
