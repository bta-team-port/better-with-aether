package teamport.aether.block.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.IPaintable;
import net.minecraft.core.block.IPainted;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;

import java.util.function.Supplier;

public class BlockLogicPaintableBlock extends BlockLogic implements IPaintable {

    public final Supplier<Block<? extends IPainted>> paintedVariant;

    public BlockLogicPaintableBlock(Block<?> block, Material material, Supplier<Block<? extends IPainted>> paintedVariant) {
        super(block, material);
        this.paintedVariant = paintedVariant;
    }

    @Override
    public void setColor(World world, TilePosc pos, DyeColor color) {
        Block<? extends IPainted> painted = paintedVariant.get();
        world.setBlockType(pos, painted);
        painted.getLogic().setColor(world, pos, color);
    }
}
