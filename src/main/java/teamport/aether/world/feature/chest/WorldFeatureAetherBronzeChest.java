package teamport.aether.world.feature.chest;

import teamport.aether.block.AetherBlocks;
import teamport.aether.world.feature.dungeon.bronze.WorldFeatureAetherBronzeDungeon;

public class WorldFeatureAetherBronzeChest extends WorldFeatureAetherTreasureChest {
    public WorldFeatureAetherBronzeChest() {
        super(AetherBlocks.CHEST_DUNGEON_BRONZE_LOCKED, 4, WorldFeatureAetherBronzeDungeon::generateLoot, WorldFeatureAetherBronzeDungeon.TREASURE);
        this.guaranteedRare = 2;
    }
}
