package teamport.aether.world.generate.feature.chests;

import net.minecraft.core.block.BlockLogicRotatable;
import net.minecraft.core.util.helper.Direction;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.world.generate.feature.dungeon.WorldFeatureAetherGoldDungeon;

public class WorldFeatureAetherGoldChest extends WorldFeatureAetherTreasureChest {
    public WorldFeatureAetherGoldChest() {
        this(Direction.NORTH);
    }

    public WorldFeatureAetherGoldChest(Direction rotation) {
        super(AetherBlocks.CHEST_DUNGEON_GOLD_LOCKED.id(), BlockLogicRotatable.setDirection(0, rotation), WorldFeatureAetherGoldDungeon::generateLoot, WorldFeatureAetherGoldDungeon.TREASURE);
        this.guaranteedRare = 6;
    }

    public static WorldFeatureAetherGoldChest goldChest() {
        return new WorldFeatureAetherGoldChest();
    }

    public static WorldFeatureAetherGoldChest goldChest(Direction rotation) {
        return new WorldFeatureAetherGoldChest(rotation);
    }
}
