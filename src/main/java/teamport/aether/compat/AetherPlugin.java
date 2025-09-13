package teamport.aether.compat;

import teamport.aether.world.generate.feature.dungeon.WorldFeatureAetherBronzeDungeon;

public interface AetherPlugin {
    void registerBronzeDungeonRoom(WorldFeatureAetherBronzeDungeon.RoomManager roomManager);
}