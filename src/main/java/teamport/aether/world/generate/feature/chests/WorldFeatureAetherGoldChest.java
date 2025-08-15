package teamport.aether.world.generate.feature.chests;

import teamport.aether.blocks.AetherBlocks;
import teamport.aether.world.generate.feature.WorldFeatureAetherDungeonGold;
import teamport.aether.world.generate.feature.WorldFeatureAetherDungeonSilver;

public class WorldFeatureAetherGoldChest extends WorldFeatureAetherTreasureChest{
    public WorldFeatureAetherGoldChest(){
        super(AetherBlocks.GOLD_CHEST_DUNGEON_LOCKED.id(),WorldFeatureAetherDungeonSilver.LOOT_NORMAL,  WorldFeatureAetherDungeonGold.LOOT_RARE);
    }
}
