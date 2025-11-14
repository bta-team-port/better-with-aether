package teamport.aether.world.feature;

import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.world.feature.util.WorldFeatureBlock;
import teamport.aether.world.feature.util.WorldFeatureComponent;

import java.util.Random;

import static teamport.aether.helper.MetadataHelper.getTorchMetadataFromDirection;
import static teamport.aether.world.feature.util.WorldFeatureBlock.wfb;
import static teamport.aether.world.feature.util.WorldFeaturePoint.wfp;

public class RotationBlockTest extends WorldFeature {
    @Override
    public boolean place(World world, Random random, int x, int y, int z) {
        Direction[] horizontalDirections = Direction.horizontalDirections;
        for (int i = 0; i < horizontalDirections.length; i++) {
            Direction dir = horizontalDirections[i];
            make(world, x - 4 * i, y, z, dir);
        }
        return true;
    }

    private void make(World world, int x, int y, int z, Direction direction) {
        WorldFeatureComponent room = new WorldFeatureComponent();
        room.add(wfb(x, y, z, AetherBlocks.PLANKS_SKYROOT_PAINTED.id(), DyeColor.BLUE.blockMeta));
        room.add(wfb(x, y, z - 1, AetherBlocks.TORCH_AMBROSIUM.id(), getTorchMetadataFromDirection(Direction.NORTH), true));
        room.add(wfb(x + 1, y, z, AetherBlocks.TORCH_AMBROSIUM.id(), getTorchMetadataFromDirection(Direction.EAST), true));
        room.add(wfb(x, y, z + 1, AetherBlocks.TORCH_AMBROSIUM.id(), getTorchMetadataFromDirection(Direction.SOUTH), true));
        room.add(wfb(x - 1, y, z, AetherBlocks.TORCH_AMBROSIUM.id(), getTorchMetadataFromDirection(Direction.WEST), true));

        for (WorldFeatureBlock blocks : room.getBlockList()) {
            blocks.rotateYAroundPivot(wfp(x, y, z), direction);
            blocks.place(world);
        }
    }
}
