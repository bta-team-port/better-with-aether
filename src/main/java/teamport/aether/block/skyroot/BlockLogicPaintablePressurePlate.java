package teamport.aether.block.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicPressurePlate;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;

public class BlockLogicPaintablePressurePlate<T extends Entity> extends BlockLogicPressurePlate<T> {
    protected final Block<? extends BlockLogicPaintedPressurePlate<T>> paintedBlock;

    public BlockLogicPaintablePressurePlate(Block<?> block, Class<T> mobType, Material material, Block<? extends BlockLogicPaintedPressurePlate<T>> paintedBlock) {
        super(block, mobType, material);
        this.paintedBlock = paintedBlock;
    }

    @Override
    public int tickDelay() {
        return 10;
    }

    @Override
    public boolean canBePainted() {
        return true;
    }

    @Override
    public void setColor(@NonNull World world, @NonNull TilePosc pos, @NonNull DyeColor color) {
        int meta = world.getBlockData(pos);
        world.setBlockTypeDataRaw(pos, paintedBlock, meta);
        world.setBlockData(pos, meta);
        paintedBlock.getLogic().setColor(world, pos, color);
        if (isPressed(meta)) {
            world.scheduleBlockUpdate(pos, paintedBlock, this.tickDelay());
        }
    }
}
