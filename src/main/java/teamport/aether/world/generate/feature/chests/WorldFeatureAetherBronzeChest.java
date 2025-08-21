package teamport.aether.world.generate.feature.chests;

import teamport.aether.blocks.AetherBlocks;
import teamport.aether.world.generate.feature.dungeon.WorldFeatureAetherBronzeDungeon;

public class WorldFeatureAetherBronzeChest extends WorldFeatureAetherTreasureChest {
    public WorldFeatureAetherBronzeChest() {
        super(AetherBlocks.BRONZE_CHEST_DUNGEON_LOCKED.id(), WorldFeatureAetherBronzeDungeon.LOOT_NORMAL, WorldFeatureAetherBronzeDungeon.LOOT_RARE);
    }

    public static WorldFeatureAetherBronzeChest bronzeChest() {
        return new WorldFeatureAetherBronzeChest();
    }
}
