package teamport.aether.blocks.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicLeavesBase;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import teamport.aether.AetherMod;
import teamport.aether.helper.ParticleMaker;
import turniplabs.halplibe.helper.EnvironmentHelper;

import java.util.Random;

public class BlockLogicLeavesSkyroot extends BlockLogicLeavesBase {

    public BlockLogicLeavesSkyroot(Block<?> block, Material material, Block<?> sapling) {
        super(block, material, sapling);
    }

    @Override
    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
        if (dropCause != EnumDropCause.PICK_BLOCK && dropCause != EnumDropCause.SILK_TOUCH) {
            int numDropped = 1;
            return world.rand.nextInt(MathHelper.floor(20.0F)) != 0 ? null : new ItemStack[]{new ItemStack(this.getSapling(), numDropped)};
        } else {
            return new ItemStack[]{new ItemStack(this.block)};
        }
    }

    @Override
    public void animationTick(World world, int x, int y, int z, Random rand) {
        if (rand.nextInt(20) == 0 && !EnvironmentHelper.isServerEnvironment()) {
            ParticleMaker.spawnParticle(world, AetherMod.MOD_ID + "$fallingleaf", x, y - 0.10000000149011612, z, 0.0, 0.0, 0.0, 0);
        }
    }
}
