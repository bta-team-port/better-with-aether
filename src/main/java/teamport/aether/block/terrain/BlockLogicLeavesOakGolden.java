package teamport.aether.block.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicLeavesBase;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.block.AetherBlocks;

import java.util.Random;

public class BlockLogicLeavesOakGolden extends BlockLogicLeavesBase {
    public BlockLogicLeavesOakGolden(Block<?> block) {
        super(block, Materials.LEAVES, AetherBlocks.SAPLING_OAK_GOLDEN);
    }

    @Override
    public void animationTick(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Random rand) {
        if (rand.nextInt(5) == 0) {
            world.spawnParticle("goldendust", tilePos.x(), (double) tilePos.y() - (double) 0.1F, tilePos.z(), 0.0F, 0.0F, 0.0F, 0, false);
        }

    }

    @Override
    public ItemStack[] getBreakResult(@NonNull World world, @NonNull EnumDropCause dropCause, int data, @Nullable TileEntity tileEntity) {
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
