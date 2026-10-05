package teamport.aether.block.dungeon;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.IPaintable;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;

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
    public void setColor(@NonNull World world, @NonNull TilePosc pos, @NonNull DyeColor dyeColor) {
        int meta = world.getBlockData(pos);
        world.setBlockTypeDataRaw(pos, paintedBlock, meta);
        world.setBlockData(pos, meta);
        paintedBlock.getLogic().setColor(world, pos, dyeColor);
    }
}
