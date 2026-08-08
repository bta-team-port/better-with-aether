package teamport.aether.block.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicDoor;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.item.Item;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
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
    public void setColor(World world, TilePosc pos, DyeColor color) {
        int meta = world.getBlockData(pos);
        TilePos otherPos = this.isTop ? pos.down(new TilePos()) : pos.up(new TilePos());
        if (this.isTop) {
            world.setBlockTypeDataRaw(pos, paintedDoorBlockTop, meta);
            paintedDoorBlockTop.getLogic().setColor(world, pos, color);
            world.setBlockTypeDataRaw(otherPos, paintedDoorBlockBottom, meta);
            paintedDoorBlockBottom.getLogic().setColor(world, otherPos, color);
        } else {
            world.setBlockTypeDataRaw(pos, paintedDoorBlockBottom, meta);
            paintedDoorBlockBottom.getLogic().setColor(world, pos, color);
            world.setBlockTypeDataRaw(otherPos, paintedDoorBlockTop, meta);
            paintedDoorBlockTop.getLogic().setColor(world, otherPos, color);
        }
    }
}
