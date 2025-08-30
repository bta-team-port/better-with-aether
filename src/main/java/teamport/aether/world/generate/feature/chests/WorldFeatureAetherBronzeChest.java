package teamport.aether.world.generate.feature.chests;

import teamport.aether.blocks.AetherBlocks;
import teamport.aether.world.generate.feature.dungeon.WorldFeatureAetherBronzeDungeon;

public class WorldFeatureAetherBronzeChest extends WorldFeatureAetherTreasureChest {
    public WorldFeatureAetherBronzeChest() {
        super(AetherBlocks.CHEST_DUNGEON_BRONZE_LOCKED.id(), WorldFeatureAetherBronzeDungeon.LOOT_NORMAL, WorldFeatureAetherBronzeDungeon.LOOT_RARE);
        this.guaranteedRare = 2;
    }

    public static WorldFeatureAetherBronzeChest bronzeChest() {
        return new WorldFeatureAetherBronzeChest();
    }
}
