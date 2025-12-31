package teamport.aether.block.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicLeavesBase;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.world.World;
import teamport.aether.block.AetherBlocks;

import java.util.Random;

public class BlockLogicLeavesOakGolden extends BlockLogicLeavesBase {
    public BlockLogicLeavesOakGolden(Block<?> block) {
        super(block, Material.leaves, AetherBlocks.SAPLING_OAK_GOLDEN);
    }

    @Override
    public void animationTick(World world, int x, int y, int z, Random rand) {
        if (rand.nextInt(5) == 0) {
            world.spawnParticle("goldendust", x, y - 0.10000000149011612, z, 0.0, 0.0, 0.0, 0);
        }
    }

    @Override
    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
        if (dropCause != EnumDropCause.PICK_BLOCK && dropCause != EnumDropCause.SILK_TOUCH) {
            if (world.rand.nextInt(20) == 0) {
                return new ItemStack[]{new ItemStack(AetherBlocks.SAPLING_OAK_GOLDEN, 1)};
            }
            if (world.rand.nextInt(1000) == 0) {
                return new ItemStack[]{new ItemStack(Items.FOOD_APPLE_GOLD, 1)};
            }
        } else {
            return new ItemStack[]{new ItemStack(this)};
        }
        return null;
    }

}
