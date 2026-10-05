package teamport.aether.world.feature.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFluid;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.MethodParametersAnnotation;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.generate.feature.WorldFeatureInterface;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlockTags;

import java.util.Random;

public class WorldFeatureAetherLiquid implements WorldFeatureInterface {
    private final Block<?> liquidBlock;

    @MethodParametersAnnotation(
        names = {"liquidId"}
    )
    public WorldFeatureAetherLiquid( Block<BlockLogicFluid> liquidBlock) {
        this.liquidBlock = liquidBlock;
    }

    @Override
    public boolean place(@NotNull World world, @NotNull Random random, @NotNull TilePosc tilePosc) {
        int x = tilePosc.x();
        int y = tilePosc.y();
        int z = tilePosc.z();
        TilePos temp = new TilePos();
        if (!world.getBlockType(temp.set(x, y + 1, z)).hasTag(AetherBlockTags.AETHER_TERRAIN_BLOCK)) {
            return false;
        }
        if (!world.getBlockType(temp.set(x, y - 1, z)).hasTag(AetherBlockTags.AETHER_TERRAIN_BLOCK)) {
            return false;
        }
        if ((world.getBlockType(tilePosc) != Blocks.AIR && !world.getBlockType(tilePosc).hasTag(AetherBlockTags.AETHER_TERRAIN_BLOCK))) {
            return false;
        }
        int l = 0;
        if (world.getBlockType(temp.set(x - 1, y, z)).hasTag(AetherBlockTags.AETHER_TERRAIN_BLOCK)) {++l;}
        if (world.getBlockType(temp.set(x + 1, y, z)).hasTag(AetherBlockTags.AETHER_TERRAIN_BLOCK)) {++l;}
        if (world.getBlockType(temp.set(x, y, z - 1)).hasTag(AetherBlockTags.AETHER_TERRAIN_BLOCK)) {++l;}
        if (world.getBlockType(temp.set(x, y, z + 1)).hasTag(AetherBlockTags.AETHER_TERRAIN_BLOCK)) {++l;}

        int i1 = 0;
        if (world.isAirBlock(temp.set(x - 1, y, z))) {++i1;}
        if (world.isAirBlock(temp.set(x + 1, y, z))) {++i1;}
        if (world.isAirBlock(temp.set(x, y, z - 1))) {++i1;}
        if (world.isAirBlock(temp.set(x, y, z + 1))) {++i1;}

        if (l == 3 && i1 == 1) {
            world.setBlockTypeNotify(tilePosc, this.liquidBlock);
            world.scheduledUpdatesAreImmediate = true;
            this.liquidBlock.updateTick(world, tilePosc, random, false);
            world.scheduledUpdatesAreImmediate = false;
        }
        return true;
    }
}
