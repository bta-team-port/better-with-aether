package teamport.aether.lookup;

import teamport.aether.blocks.AetherBlocks;
import teamport.aether.items.AetherItems;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("java:S6548")
public class LookupFuelEnchanter {
    public static final LookupFuelEnchanter INSTANCE = new LookupFuelEnchanter();
    private final Map<Integer, Integer> fuelList = new HashMap<>();

    private LookupFuelEnchanter() {
        this.register();
    }

    public void register() {
        this.addFuelEntry(AetherItems.AMBROSIUM.id, 500);
        this.addFuelEntry(AetherBlocks.BLOCK_AMBROSIUM.id(), 4000);
    }

    public void addFuelEntry(int id, int fuelYield) {
        this.fuelList.put(id, fuelYield);
    }

    public int getFuelYield(int id) {
        return this.fuelList.get(id) == null ? 0 : this.fuelList.get(id);
    }
}
