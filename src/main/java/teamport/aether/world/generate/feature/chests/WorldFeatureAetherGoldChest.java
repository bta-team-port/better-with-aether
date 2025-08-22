package teamport.aether.world.generate.feature.chests;

import teamport.aether.blocks.AetherBlocks;
import teamport.aether.world.generate.feature.dungeon.WorldFeatureAetherGoldDungeon;

public class WorldFeatureAetherGoldChest extends WorldFeatureAetherTreasureChest {
    public WorldFeatureAetherGoldChest() {
        super(AetherBlocks.GOLD_CHEST_DUNGEON_LOCKED.id(), WorldFeatureAetherGoldDungeon.LOOT_NORMAL, WorldFeatureAetherGoldDungeon.LOOT_RARE);
        this.guaranteedRare = 6;
    }

    public static WorldFeatureAetherGoldChest goldChest() {
        return new WorldFeatureAetherGoldChest();
    }
}
