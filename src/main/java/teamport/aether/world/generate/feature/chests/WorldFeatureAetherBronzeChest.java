package teamport.aether.world.generate.feature.chests;

import teamport.aether.blocks.AetherBlocks;
import teamport.aether.world.generate.feature.WorldFeatureAetherDungeonBronze;

public class WorldFeatureAetherBronzeChest extends WorldFeatureAetherTreasureChest{
    public WorldFeatureAetherBronzeChest(){
        super(AetherBlocks.BRONZE_CHEST_DUNGEON_LOCKED.id(), WorldFeatureAetherDungeonBronze.LOOT_NORMAL, WorldFeatureAetherDungeonBronze.LOOT_RARE);
    }
}
