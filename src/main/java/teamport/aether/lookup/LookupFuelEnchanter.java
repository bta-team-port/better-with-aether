package teamport.aether.lookup;

import teamport.aether.block.AetherBlocks;
import teamport.aether.item.AetherItems;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("java:S6548")
public class LookupFuelEnchanter {
    public static final LookupFuelEnchanter INSTANCE = new LookupFuelEnchanter();
    private final Map<Integer, Integer> fuelList = new HashMap<>();

    public static void init(){/* just to load this class*/}

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
