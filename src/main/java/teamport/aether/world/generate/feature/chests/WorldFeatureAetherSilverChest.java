package teamport.aether.world.generate.feature.chests;

import teamport.aether.blocks.AetherBlocks;
import teamport.aether.world.generate.feature.WorldFeatureAetherDungeonSilver;

public class WorldFeatureAetherSilverChest extends WorldFeatureAetherTreasureChest {
    public WorldFeatureAetherSilverChest(){
        super(AetherBlocks.SILVER_CHEST_DUNGEON_LOCKED.id(),WorldFeatureAetherDungeonSilver.LOOT_NORMAL,  WorldFeatureAetherDungeonSilver.LOOT_RARE);
    }
}
