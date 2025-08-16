package teamport.aether.world.generate.feature.chests;

import teamport.aether.blocks.AetherBlocks;
import teamport.aether.world.generate.feature.dungeon.WorldFeatureAetherSilverDungeon;

public class WorldFeatureAetherSilverChest extends WorldFeatureAetherTreasureChest {
    public WorldFeatureAetherSilverChest(){
        super(AetherBlocks.SILVER_CHEST_DUNGEON_LOCKED.id(), WorldFeatureAetherSilverDungeon.LOOT_NORMAL,  WorldFeatureAetherSilverDungeon.LOOT_RARE);
    }

    public static WorldFeatureAetherSilverChest silverChest(){
        return new WorldFeatureAetherSilverChest();
    }
}
