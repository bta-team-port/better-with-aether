package teamport.aether.blocks.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicDoor;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.item.Item;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;

public class BlockLogicPaintableDoor extends BlockLogicDoor {
    protected final Block<? extends BlockLogicPaintedDoor> paintedDoorBlockBottom;
    protected final Block<? extends BlockLogicPaintedDoor> paintedDoorBlockTop;

    public BlockLogicPaintableDoor(
        Block<?> block, Material material,
        boolean isTop, boolean requireTool,
        Block<? extends BlockLogicPaintedDoor> paintedDoorBlockTop, Block<? extends BlockLogicPaintedDoor> paintedDoorBlockBottom,
        @Nullable Supplier<Item> droppedItem
    ) {
        super(block, material, isTop, requireTool, droppedItem);
        this.paintedDoorBlockBottom = paintedDoorBlockBottom;
        this.paintedDoorBlockTop = paintedDoorBlockTop;
    }

    @Override
    public boolean canBePainted() {
        return true;
    }

    @Override
    public void setColor(World world, int x, int y, int z, DyeColor color) {
        int meta = world.getBlockMetadata(x, y, z);
        if (this.isTop) {
            world.setBlockAndMetadataRaw(x, y, z, paintedDoorBlockTop.id(), meta);
            paintedDoorBlockTop.getLogic().setColor(world, x, y, z, color);
            world.setBlockAndMetadataRaw(x, y - 1, z, paintedDoorBlockBottom.id(), meta);
            paintedDoorBlockBottom.getLogic().setColor(world, x, y - 1, z, color);
        } else {
            world.setBlockAndMetadataRaw(x, y, z, paintedDoorBlockBottom.id(), meta);
            paintedDoorBlockBottom.getLogic().setColor(world, x, y, z, color);
            world.setBlockAndMetadataRaw(x, y + 1, z, paintedDoorBlockTop.id(), meta);
            paintedDoorBlockTop.getLogic().setColor(world, x, y + 1, z, color);
        }
    }
}
