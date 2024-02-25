package bta.aether.world.generate;

import bta.aether.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BlockPallet {
    private final List<Entry> entries = new ArrayList<>();
    private double accumulatedWeight;
    public void addEntry(int id, int meta, double weight) {
        this.accumulatedWeight += weight;
        Entry e = new Entry();
        e.object = new Pair<>(id, meta);
        e.weight = weight;
        e.accumulatedWeight = this.accumulatedWeight;
        this.entries.add(e);
    }

    public Pair<Integer, Integer> getRandom(Random random) {
        double r = random.nextDouble() * this.accumulatedWeight;
        for (Entry entry : this.entries) {
            if (!(entry.accumulatedWeight >= r)) continue;
            return entry.object;
        }
        return null;
    }

    public List<Pair<Integer, Integer>> getEntries() {
        ArrayList<Pair<Integer, Integer>> list = new ArrayList<>();
        for (Entry entry : this.entries) {
            list.add(entry.object);
        }
        return Collections.unmodifiableList(list);
    }

    public List<Entry> getEntriesWithWeights() {
        return Collections.unmodifiableList(this.entries);
    }

    public class Entry {
        private double accumulatedWeight;
        private Pair<Integer, Integer> object;
        private double weight;

        public double getWeight() {
            return this.weight;
        }

        public Pair<Integer, Integer> getObject() {
            return this.object;
        }
    }
}
