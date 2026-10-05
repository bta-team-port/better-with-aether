package teamport.aether.world.feature.util;

import it.unimi.dsi.fastutil.ints.IntIntMutablePair;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import net.minecraft.core.WeightedRandomBag;

import java.util.Random;

public class BlockPallet {
    private final WeightedRandomBag<IntIntPair> pallet = new WeightedRandomBag<>();

    public void addEntry(int id, double weight) {
        this.addEntry(id, 0, weight);
    }

    public void addEntry(int id, int meta, double weight) {
        IntIntPair entry = new IntIntMutablePair(id, meta);
        this.pallet.addEntry(entry, weight);
    }

    public IntIntPair getRandom(Random random) {
        return this.pallet.getRandom(random);
    }
    public WeightedRandomBag<IntIntPair> getPallet() {
        return pallet;
    }
}
