package teamport.aether.block.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;

public class BlockLogicPaintableChest extends BlockLogicChest {
    protected final Block<? extends BlockLogicPaintedChest> paintedBlock;

    public BlockLogicPaintableChest(Block<?> block, Material material, Block<? extends BlockLogicPaintedChest> paintedBlock) {
        super(block, material);
        this.paintedBlock = paintedBlock;
    }

    @Override
    public void setColor(World world, int x, int y, int z, DyeColor color) {
        int meta = world.getBlockMetadata(x, y, z);
        world.setBlockAndMetadataRaw(x, y, z, paintedBlock.id(), meta);
        world.setBlockMetadata(x, y, z, meta);
        paintedBlock.getLogic().setColor(world, x, y, z, color);
    }
}
