package teamport.aether.block.dungeon;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.IPaintable;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;

public class BlockLogicPaintableChestMimic extends BlockLogicChestMimic implements IPaintable {
    protected final Block<? extends BlockLogicPaintedChestMimic> paintedBlock;

    public BlockLogicPaintableChestMimic(Block<?> block, Material material, Block<? extends BlockLogicPaintedChestMimic> paintedBlock) {
        super(block, material);
        this.paintedBlock = paintedBlock;
    }

    @Override
    public boolean canBePainted() {
        return true;
    }

    @Override
    public void setColor(World world, int x, int y, int z, DyeColor dyeColor) {
        int meta = world.getBlockMetadata(x, y, z); // frank
        world.setBlockAndMetadataRaw(x, y, z, paintedBlock.id(), meta);
        world.setBlockAndMetadata(x, y, z, paintedBlock.id(), meta);
        paintedBlock.getLogic().setColor(world, x, y, z, dyeColor); // no frank

    }
}
