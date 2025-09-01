package teamport.aether.world.generate.feature.chests;

import teamport.aether.blocks.AetherBlocks;
import teamport.aether.world.generate.feature.dungeon.WorldFeatureAetherGoldDungeon;

public class WorldFeatureAetherGoldChest extends WorldFeatureAetherTreasureChest {
    public WorldFeatureAetherGoldChest() {
        super(AetherBlocks.CHEST_DUNGEON_GOLD_LOCKED.id(), WorldFeatureAetherGoldDungeon::generateLoot, WorldFeatureAetherGoldDungeon.TREASURE);
        this.guaranteedRare = 6;
    }

    public static WorldFeatureAetherGoldChest goldChest() {
        return new WorldFeatureAetherGoldChest();
    }
}
