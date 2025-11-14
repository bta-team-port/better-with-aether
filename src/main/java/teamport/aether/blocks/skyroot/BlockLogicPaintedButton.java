package teamport.aether.blocks.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicButtonPainted;
import net.minecraft.core.world.World;

public class BlockLogicPaintedButton extends BlockLogicButtonPainted {
    protected final int unpaintedBlockID;

    public BlockLogicPaintedButton(Block<?> block, int unpaintedBlockID) {
        super(block);
        this.unpaintedBlockID = unpaintedBlockID;
    }

    @Override
    public int tickDelay() {
        return 5;
    }

    @Override
    public void removeDye(World world, int x, int y, int z) {
        world.setBlockWithNotify(x, y, z, unpaintedBlockID);
    }
}
