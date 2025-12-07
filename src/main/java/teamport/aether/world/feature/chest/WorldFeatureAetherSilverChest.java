package teamport.aether.world.feature.chest;

import teamport.aether.block.AetherBlocks;
import teamport.aether.world.feature.dungeon.silver.WorldFeatureAetherSilverDungeon;

public class WorldFeatureAetherSilverChest extends WorldFeatureAetherTreasureChest {
    public WorldFeatureAetherSilverChest() {
        super(AetherBlocks.CHEST_DUNGEON_SILVER_LOCKED.id(), 4, WorldFeatureAetherSilverDungeon::generateLoot, WorldFeatureAetherSilverDungeon.TREASURE);
        this.guaranteedRare = 4;
    }
}
