package teamport.aether.world.generate.feature.chests;

import teamport.aether.blocks.AetherBlocks;
import teamport.aether.world.generate.feature.dungeon.WorldFeatureAetherSilverDungeon;

public class WorldFeatureAetherSilverChest extends WorldFeatureAetherTreasureChest {
    public WorldFeatureAetherSilverChest() {
        super(AetherBlocks.CHEST_DUNGEON_SILVER_LOCKED.id(), WorldFeatureAetherSilverDungeon::generateLoot, WorldFeatureAetherSilverDungeon.TREASURE);
        this.guaranteedRare = 4;
    }

    public static WorldFeatureAetherSilverChest silverChest() {
        return new WorldFeatureAetherSilverChest();
    }
}
