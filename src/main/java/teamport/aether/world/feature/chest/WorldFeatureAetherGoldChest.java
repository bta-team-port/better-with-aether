package teamport.aether.world.feature.chest;

import net.minecraft.core.block.BlockLogicRotatable;
import net.minecraft.core.util.helper.Direction;
import teamport.aether.block.AetherBlocks;
import teamport.aether.world.feature.dungeon.gold.WorldFeatureAetherGoldDungeon;

public class WorldFeatureAetherGoldChest extends WorldFeatureAetherTreasureChest {
    public WorldFeatureAetherGoldChest(Direction rotation) {
        super(AetherBlocks.CHEST_DUNGEON_GOLD_LOCKED.id(), BlockLogicRotatable.setDirection(0, rotation), WorldFeatureAetherGoldDungeon::generateLoot, WorldFeatureAetherGoldDungeon.TREASURE);
        this.guaranteedRare = 6;
    }
}
