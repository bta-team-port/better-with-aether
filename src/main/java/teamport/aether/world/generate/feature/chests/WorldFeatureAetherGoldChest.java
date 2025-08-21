package teamport.aether.world.generate.feature.chests;

import teamport.aether.blocks.AetherBlocks;
import teamport.aether.world.generate.feature.dungeon.WorldFeatureAetherGoldDungeon;
import teamport.aether.world.generate.feature.dungeon.WorldFeatureAetherSilverDungeon;

public class WorldFeatureAetherGoldChest extends WorldFeatureAetherTreasureChest {
    public WorldFeatureAetherGoldChest() {
        super(AetherBlocks.GOLD_CHEST_DUNGEON_LOCKED.id(), WorldFeatureAetherSilverDungeon.LOOT_NORMAL, WorldFeatureAetherGoldDungeon.LOOT_RARE);
        this.guaranteedRare = 2;
    }

    public static WorldFeatureAetherGoldChest goldChest() {
        return new WorldFeatureAetherGoldChest();
    }
}
