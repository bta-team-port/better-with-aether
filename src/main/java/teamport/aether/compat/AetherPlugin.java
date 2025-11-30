package teamport.aether.compat;

import teamport.aether.lookup.LookupFuelEnchanter;
import teamport.aether.lookup.LookupFuelFreezer;
import teamport.aether.lookup.LookupFuelIncubator;
import teamport.aether.lookup.LookupTrinketIcons;
import teamport.aether.world.feature.dungeon.bronze.WorldFeatureAetherBronzeDungeon;

public interface AetherPlugin {
    void registerBronzeDungeonRoom(WorldFeatureAetherBronzeDungeon.RoomManager roomManager);
    void registerDungeonType();

    void addEnchanterFuel(LookupFuelEnchanter instance);
    void addFreezerFuel(LookupFuelFreezer instance);
    void addIncubatorFuel(LookupFuelIncubator instance);
    void makeTrinket(LookupTrinketIcons instance);
}
