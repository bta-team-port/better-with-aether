package teamport.aether.world.feature.chest;

import teamport.aether.block.AetherBlocks;
import teamport.aether.world.feature.dungeon.gold.WorldFeatureAetherGoldDungeon;

public class WorldFeatureAetherGoldChest extends WorldFeatureAetherTreasureChest {
    public WorldFeatureAetherGoldChest() {
        super(AetherBlocks.CHEST_DUNGEON_GOLD_LOCKED, 4, WorldFeatureAetherGoldDungeon::generateLoot, WorldFeatureAetherGoldDungeon.TREASURE);
        this.guaranteedRare = 6;
    }
}
