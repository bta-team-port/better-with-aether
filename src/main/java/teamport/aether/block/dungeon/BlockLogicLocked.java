package teamport.aether.block.dungeon;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;

public class BlockLogicLocked extends BlockLogicDungeon {
    private final Block<?> replacement;

    public BlockLogicLocked(Block<?> block, Material material, Block<?> replacement) {
        super(block, material);
        this.replacement = replacement;
    }

    @Override
    public @Nullable ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
        if (dropCause != EnumDropCause.IMPROPER_TOOL) {
            return new ItemStack[]{new ItemStack(replacement, 1)};
        }
        return null;
    }

    public Block<?> getReplacement() {
        return replacement;
    }
}
