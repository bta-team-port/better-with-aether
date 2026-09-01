package teamport.aether.compat;

import teamport.aether.world.feature.dungeon.bronze.WorldFeatureAetherBronzeDungeon;
@Deprecated(since = "1.1.0+8.0.1", forRemoval = true)
public interface AetherPlugin {
    void registerBronzeDungeonRoom(WorldFeatureAetherBronzeDungeon.RoomManager roomManager);
    void registerDungeonType();
    void initializeDimensionBlacklist();
}
