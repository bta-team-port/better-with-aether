package teamport.aether.block.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicLeavesBase;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import net.minecraft.core.world.wind.WindProvider;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.block.AetherBlocks;

import java.util.Random;

public class BlockLogicLeavesSkyroot extends BlockLogicLeavesBase {

    public BlockLogicLeavesSkyroot(Block<?> block) {
        super(block, Materials.LEAVES, AetherBlocks.SAPLING_SKYROOT);
    }

    @Override
    public ItemStack[] getBreakResult(@NonNull World world, @NonNull EnumDropCause dropCause, int data, @Nullable TileEntity tileEntity) {
        if (dropCause != EnumDropCause.PICK_BLOCK && dropCause != EnumDropCause.SILK_TOUCH) {
            int numDropped = 1;
            return world.rand.nextInt(MathHelper.floor(20.0F)) != 0 ? null : new ItemStack[]{new ItemStack(this.getSapling(), numDropped)};
        } else {
            return new ItemStack[]{new ItemStack(this.block)};
        }
    }

    @Override
    public void animationTick(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Random rand) {
        WindProvider wind = world.getWorldType().getWindManager();
        float windIntensity = wind.getWindIntensity(world, (float) tilePos.x(), (float) tilePos.y(), (float) tilePos.z());
        if (rand.nextInt((int) (40.0F + 200.0F * (1.0F - windIntensity))) == 0) {
            world.spawnParticle("fallingAetherLeaf", tilePos.x(), (double) tilePos.y() - (double) 0.1F, tilePos.z(), 0.0F, 0.0F, 0.0F, 0, false);
        }

    }
}
