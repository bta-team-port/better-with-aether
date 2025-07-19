package teamport.aether.lookup;

import net.minecraft.core.block.Blocks;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.items.AetherItems;

import java.util.HashMap;
import java.util.Map;

public class LookupFuelFreezer {
    public static final LookupFuelFreezer instance = new LookupFuelFreezer();
    public final Map<Integer, Integer> fuelList = new HashMap<>();

    public LookupFuelFreezer() {
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
        this.addFuelEntry(AetherItems.ARMOR_TALISMAN_ICE.id, 8000);
    }

    public void addFuelEntry(int id, int fuelYield) {
        this.fuelList.put(id, fuelYield);
    }

    public int getFuelYield(int id) {
        return this.fuelList.get(id) == null ? 0 : (Integer)this.fuelList.get(id);
    }

    public Map<Integer, Integer> getFuelList() {
        return this.fuelList;
    }
}
