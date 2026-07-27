package teamport.aether.lookup;

import net.minecraft.core.block.Blocks;
import teamport.aether.block.AetherBlocks;

import java.util.HashMap;
import java.util.Map;

public class LookupFuelFreezer {
    public static final LookupFuelFreezer INSTANCE = new LookupFuelFreezer();
    private final Map<Integer, Integer> fuelList = new HashMap<>();

    private LookupFuelFreezer() {
        this.register();
    }

    public void register() {
        this.addFuelEntry(Blocks.ICE.id(), 100);
        this.addFuelEntry(Blocks.PERMAFROST.id(), 200);
        // cobbled permafrost
        this.addFuelEntry(Blocks.COBBLE_PERMAFROST.id(), 200);
        this.addFuelEntry(Blocks.STAIRS_COBBLE_PERMAFROST.id(), 200);
        this.addFuelEntry(Blocks.SLAB_COBBLE_PERMAFROST.id(), 200);
        // brick permafrost
        this.addFuelEntry(Blocks.BRICK_PERMAFROST.id(), 200);
        this.addFuelEntry(Blocks.STAIRS_BRICK_PERMAFROST.id(), 200);
        this.addFuelEntry(Blocks.SLAB_BRICK_PERMAFROST.id(), 200);
        // polished permafrost
        this.addFuelEntry(Blocks.PERMAFROST_POLISHED.id(), 200);
        this.addFuelEntry(Blocks.SLAB_PERMAFROST_POLISHED.id(), 200);

        this.addFuelEntry(Blocks.PERMAICE.id(), 400);
        this.addFuelEntry(AetherBlocks.ICESTONE.id(), 500);
    }

    public void addFuelEntry(int id, int fuelYield) {
        this.fuelList.put(id, fuelYield);
    }

    public int getFuelYield(int id) {
        return this.fuelList.get(id) == null ? 0 : this.fuelList.get(id);
    }
}
