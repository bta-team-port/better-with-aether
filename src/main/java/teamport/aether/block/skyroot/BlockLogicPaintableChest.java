package teamport.aether.block.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;

public class BlockLogicPaintableChest extends BlockLogicChest {
    protected final Block<? extends BlockLogicPaintedChest> paintedBlock;

    public BlockLogicPaintableChest(Block<?> block, Material material, Block<? extends BlockLogicPaintedChest> paintedBlock) {
        super(block, material);
        this.paintedBlock = paintedBlock;
    }

    @Override
    public void setColor(@NonNull World world, @NonNull TilePosc pos, @NonNull DyeColor color) {
        int meta = world.getBlockData(pos);
        world.setBlockTypeDataRaw(pos, paintedBlock, meta);
        world.setBlockData(pos, meta);
        paintedBlock.getLogic().setColor(world, pos, color);
    }
}
