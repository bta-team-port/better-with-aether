package teamport.aether.block.terrain;

import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.block.BlockLogicFloatingBlock;
import teamport.aether.item.AetherItems;

public class BlockLogicOreGravitite extends BlockLogicFloatingBlock {
    public static Int2IntArrayMap variantMap = new Int2IntArrayMap();

    public BlockLogicOreGravitite(@NonNull Block<?> block, @NonNull Block<?> parentBlock, @NonNull Material material) {
        super(block, material);
        variantMap.put(parentBlock.id(), block.id());
    }

    @Override
    public ItemStack[] getBreakResult(@NonNull World world, @NonNull EnumDropCause dropCause, int data, @Nullable TileEntity tileEntity) {
        return switch (dropCause) {
            case SILK_TOUCH, PICK_BLOCK -> new ItemStack[]{new ItemStack(this)};
            case EXPLOSION, PROPER_TOOL, PISTON_CRUSH -> new ItemStack[]{new ItemStack(AetherItems.ORE_RAW_GRAVITITE)};
            default -> null;
        };
    }
}
