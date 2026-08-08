package teamport.aether.world.feature.util;

import org.jspecify.annotations.NonNull;
import teamport.aether.helper.unboxed.IntPair;

import java.util.*;

/// Implements a random maze using kruskal
public class MazeHelper {
    public static class Dsu {
        int[] parent;
        int[] rank;

        public Dsu(int size) {
            this.parent = new int[size];
            this.rank = new int[size];

            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        public int find(int a) {
            while (parent[a] != a) {
                parent[a] = parent[parent[a]];
                a = parent[a];
            }
            return a;
        }

        @SuppressWarnings("SuspiciousNameCombination")
        public boolean union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX == rootY) {
                return false;
            }
            if (rank[rootX] < rank[rootY]) {
                int temp = rootX;
                rootX = rootY;
                rootY = temp;
            }
            parent[rootY] = rootX;
            if (rank[rootX] == rank[rootY]) {
                rank[rootX]++;
            }
            return true;
        }
    }

    public static @NonNull List<IntPair> randomMazeKruskal(Map<Integer, List<Integer>> graph, int size) {
        List<IntPair> edges = makeEdgeList(graph);
        Collections.shuffle(edges);
        return randomMazeKruskal(edges, size);
    }

    public static @NonNull List<IntPair> randomMazeKruskal(@NonNull List<IntPair> edges, int size) {
        List<IntPair> mst = new ArrayList<>();
        Dsu uf = new Dsu(size);

        for (IntPair edge : edges) {
            if (uf.union(edge.first(), edge.second())) {
                mst.add(edge);
            }
            if (mst.size() == size - 1) {
                break;
            }
        }
        return mst;
    }

    public static List<IntPair> makeEdgeList(@NonNull Map<Integer, List<Integer>> graph) {
        Set<IntPair> edgeSet = new HashSet<>();

        for (Map.Entry<Integer, List<Integer>> node : graph.entrySet()) {
            int currentNode = node.getKey();
            for (Integer next : node.getValue()) {
                int to = Math.min(next, currentNode);
                int from = Math.max(next, currentNode);
                edgeSet.add(new IntPair(to, from));
            }
        }
        return new ArrayList<>(edgeSet);
    }

    public static @NonNull Map<Integer, List<Integer>> makeGraph(@NonNull List<IntPair> edgeList) {
        Map<Integer, List<Integer>> graph = new HashMap<>();

        for (IntPair edge : edgeList) {
            Integer u = edge.first();
            Integer v = edge.second();
            graph.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
            graph.computeIfAbsent(v, k -> new ArrayList<>()).add(u);
        }
        return graph;
    }
}
