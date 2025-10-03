package teamport.aether.blocks.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicStairsPainted;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;

public class BlockLogicPaintedStairs extends BlockLogicStairsPainted {
    protected final int unpaintedBlockID;

    public BlockLogicPaintedStairs(Block<?> block, Block<?> modelBlock, int unpaintedBlockID) {
        super(block, modelBlock);
        this.unpaintedBlockID = unpaintedBlockID;
    }

    @Override
    public void removeDye(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        world.setBlockAndMetadataWithNotify(x, y, z, unpaintedBlockID, this.stripColorFromMetadata(meta));
    }

    @Override
    public String getLanguageKey(int meta) {
        return super.getLanguageKey(meta) + "." + this.fromMetadata(meta).colorID;
    }

    @Override
    public int getPlacedBlockMetadata(@Nullable Player player, ItemStack stack, World world, int x, int y, int z, Side side, double xPlaced, double yPlaced) {
        return stack.getMetadata();
    }
}
