package teamport.aether.block.dungeon;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class BlockLogicLocked extends BlockLogicDungeon {
    private final Block<?> replacement;

    public BlockLogicLocked(Block<?> block, Material material, Block<?> replacement) {
        super(block, material);
        this.replacement = replacement;
    }

    @Override
    public int getPistonPushReaction(@NonNull World world, @NonNull TilePosc pos) {
        return Material.PISTON_CANT_PUSH;
    }


    public @NonNull ItemStack @Nullable [] getBreakResult(@NonNull World world, @NonNull EnumDropCause dropCause, int data, @Nullable TileEntity tileEntity) {
        return dropCause != EnumDropCause.IMPROPER_TOOL ? new ItemStack[]{new ItemStack(this.replacement)} : null;
    }

    public Block<?> getReplacement() {
        return replacement;
    }
}
