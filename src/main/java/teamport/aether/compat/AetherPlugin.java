package teamport.aether.compat;

import teamport.aether.world.feature.dungeon.bronze.WorldFeatureAetherBronzeDungeon;

public interface AetherPlugin {
    void registerBronzeDungeonRoom(WorldFeatureAetherBronzeDungeon.RoomManager roomManager);
    void registerDungeonType();
}
