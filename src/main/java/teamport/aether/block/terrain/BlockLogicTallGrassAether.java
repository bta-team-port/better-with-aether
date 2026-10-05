package teamport.aether.block.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFlower;
import net.minecraft.core.block.BlockLogicMoss;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.MaterialColor;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlockTags;

public class BlockLogicTallGrassAether extends BlockLogicFlower {
    public BlockLogicTallGrassAether(@NonNull Block<?> block) {
        super(block);
        float f = 0.4F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.8F, 0.5F + f);
        block.withOverrideColor(MaterialColor.grass);
    }

    @Override
    public boolean mayPlaceOn(@NonNull Block<?> block) {
        return block.getLogic() instanceof BlockLogicMoss || block.hasTag(BlockTags.GROWS_FLOWERS) || block.hasTag(AetherBlockTags.GROWS_AETHER_FLOWERS) || super.mayPlaceOn(block);
    }

    @Override
    public @NonNull ItemStack @Nullable [] getBreakResult(@NonNull World world, @NonNull EnumDropCause dropCause, int data, @Nullable TileEntity tileEntity) {
        return switch (dropCause) {
            case PICK_BLOCK, SILK_TOUCH -> new ItemStack[]{new ItemStack(this)};
            default -> null;
        };
    }

}
