package teamport.aether.lookup;

import teamport.aether.blocks.AetherBlocks;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("java:S6548")
public class LookupFuelIncubator {
    public static final LookupFuelIncubator INSTANCE = new LookupFuelIncubator();
    private final Map<Integer, Integer> fuelList = new HashMap<>();

    private LookupFuelIncubator() {
        this.register();
    }

    public void register() {
        addFuelEntry(AetherBlocks.TORCH_AMBROSIUM.id(), 500);
    }

    public void addFuelEntry(int id, int fuelYield) {
        this.fuelList.put(id, fuelYield);
    }

    public int getFuelYield(int id) {
        return this.fuelList.get(id) == null ? 0 : this.fuelList.get(id);
    }
}
