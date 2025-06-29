package teamport.aether.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicDoor;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class BlockLogicDoorGlassQuicksoil extends BlockLogicDoor {
    public static final int MASK_ROTATION = 3;
    public static final int MASK_OPENED = 4;
    public static final int MASK_HINGE = 8;
    public final boolean isTop;
    public final boolean requireTool;
    public final @Nullable Supplier<Item> droppedItem;
    public BlockLogicDoorGlassQuicksoil(Block<?> block, Material material, boolean isTop, boolean requireTool, @Nullable Supplier<Item> droppedItem) {
        super(block, material, isTop, requireTool, droppedItem);
        this.isTop = isTop;
        this.requireTool = requireTool;
        this.droppedItem = droppedItem;
        float f = 0.5F;
        if (isTop) {
            this.setBlockBounds(0.5F - f, -1.0, 0.5F - f, 0.5F + f, 1.0, 0.5F + f);
        } else {
            this.setBlockBounds(0.5F - f, 0.0, 0.5F - f, 0.5F + f, 2.0, 0.5F + f);
        }
        block.friction = 1.05f;
    }



}
