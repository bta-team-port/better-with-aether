package teamport.aether.world.feature.util;

import it.unimi.dsi.fastutil.ints.IntIntMutablePair;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import org.jspecify.annotations.NonNull;

import java.util.*;

/// Implements a random maze using kruskal
public class MazeHelper {
    private MazeHelper(){}

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

    public static @NonNull List<IntIntPair> randomMazeKruskal(Map<Integer, List<Integer>> graph, int size) {
        List<IntIntPair> edges = makeEdgeList(graph);
        Collections.shuffle(edges);
        return randomMazeKruskal(edges, size);
    }

    public static @NonNull List<IntIntPair> randomMazeKruskal(@NonNull List<IntIntPair> edges, int size) {
        List<IntIntPair> mst = new ArrayList<>();
        Dsu uf = new Dsu(size);

        for (IntIntPair edge : edges) {
            if (uf.union(edge.firstInt(), edge.secondInt())) {
                mst.add(edge);
            }
            if (mst.size() == size - 1) {
                break;
            }
        }
        return mst;
    }

    public static List<IntIntPair> makeEdgeList(@NonNull Map<Integer, List<Integer>> graph) {
        Set<IntIntPair> edgeSet = new HashSet<>();

        for (Map.Entry<Integer, List<Integer>> node : graph.entrySet()) {
            int currentNode = node.getKey();
            for (Integer next : node.getValue()) {
                int to = Math.min(next, currentNode);
                int from = Math.max(next, currentNode);
                edgeSet.add(new IntIntMutablePair(to, from));
            }
        }
        return new ArrayList<>(edgeSet);
    }

    public static @NonNull Map<Integer, List<Integer>> makeGraph(@NonNull List<IntIntPair> edgeList) {
        Map<Integer, List<Integer>> graph = new HashMap<>();

        for (IntIntPair edge : edgeList) {
            Integer u = edge.firstInt();
            Integer v = edge.secondInt();
            graph.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
            graph.computeIfAbsent(v, k -> new ArrayList<>()).add(u);
        }
        return graph;
    }
}
