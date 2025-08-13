package teamport.aether.helper;

import java.util.*;

public class MazeHelper {
    // Wilson Algorithms to generate maze
    public static Map<Integer, Integer> generateMaze(Map<Integer, List<Integer>> GRAPH) {
        Random random = new Random();
        Map<Integer, Integer> tree = new HashMap<>();
        Set<Integer> inTree = new HashSet<>();

        List<Integer> vertices = new ArrayList<>(GRAPH.keySet());
        int start = random.nextInt(27);
        inTree.add(start);

        while (inTree.size() < GRAPH.size()) {

            // Pick a room that is not part of the maze
            int current = -1;
            for (int v : vertices) {
                if (!inTree.contains(v)) {
                    current = v;
                    break;
                }
            }

            Map<Integer, Integer> path = new HashMap<>();
            while (!inTree.contains(current)) {
                List<Integer> neighbors = GRAPH.get(current);
                int next = neighbors.get(random.nextInt(neighbors.size()));
                path.put(current, next);
                current = next;
            }

            for (Map.Entry<Integer, Integer> step : path.entrySet()) {
                int u = step.getKey();
                int v = step.getValue();

                // so it easier to place the rooms later
                int from = Math.min(u, v);
                int to = Math.max(u, v);

                tree.put(to, from);
                inTree.add(u);
                inTree.add(v);
            }
        }
        return tree;
    }
}
