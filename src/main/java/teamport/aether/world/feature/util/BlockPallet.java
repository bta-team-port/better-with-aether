package teamport.aether.world.feature.util;

import net.minecraft.core.WeightedRandomBag;
import teamport.aether.helper.unboxed.IntPair;

import java.util.Random;

public class BlockPallet {
    private final WeightedRandomBag<IntPair> pallet = new WeightedRandomBag<>();

    public void addEntry(int id, double weight) {
        this.addEntry(id, 0, weight);
    }

    public void addEntry(int id, int meta, double weight) {
        IntPair entry = new IntPair(id, meta);
        this.pallet.addEntry(entry, weight);
    }

    public IntPair getRandom(Random random) {
        return this.pallet.getRandom(random);
    }
    public WeightedRandomBag<IntPair> getPallet() {
        return pallet;
    }
}
