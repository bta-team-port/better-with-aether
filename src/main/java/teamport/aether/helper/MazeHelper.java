package teamport.aether.helper;

import java.util.*;

public class MazeHelper {
    public static class Edge {
        public int u, v;

        Edge(int u, int v) {
            this.u = Math.min(u, v); // keep edges undirected
            this.v = Math.max(u, v);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Edge)) return false;
            Edge e = (Edge) o;
            return u == e.u && v == e.v;
        }

        @Override
        public int hashCode() {
            return Objects.hash(u, v);
        }

        @Override
        public String toString() {
            return "(" + u + ", " + v + ")";
        }
    }

    // Wilson Algorithms to generate maze
    public static Set<Edge> generateMaze(Map<Integer, List<Integer>> GRAPH) {
        Random random = new Random();
        Set<Edge> tree = new HashSet<>();
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
                tree.add(new Edge(u, v));
                inTree.add(u);
                inTree.add(v);
            }
        }

        return tree;
    }
}
