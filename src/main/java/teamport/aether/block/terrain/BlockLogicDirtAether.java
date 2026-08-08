package teamport.aether.block.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.IBonemealable;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.block.AetherBlocks;
import teamport.aether.item.AetherItems;

public class BlockLogicDirtAether extends BlockLogic implements IBonemealable {

    public BlockLogicDirtAether(Block<?> block) {
        super(block, Materials.DIRT);
        block.setTicking(true);
    }

    @Override
    public int getPlacedData(@Nullable Player player, @NonNull ItemStack itemStack, @NonNull World world, @NonNull TilePosc tilePos, @NonNull Side side, double xHit, double yHit) {
        return 1;
    }

    @Override
    public void onDestroyedByPlayer(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Side side, int data, @NonNull Player player, @Nullable Item item) {
        ItemStack heldItem = player.getHeldItem();
        if (heldItem != null && heldItem.getItem().equals(AetherItems.TOOL_SHOVEL_SKYROOT) && data == 0 && player.getGamemode().hasBlockConsumption()) {
            this.onHarvest(world, player, tilePos, 1, world.getTileEntity(tilePos));
        }
    }

    @Override
    public boolean onBonemealUsed(@NonNull ItemStack itemStack, @Nullable Player player, World world, TilePosc pos, @NonNull Side side, double d, double e) {
        int i = pos.x();
        int j = pos.y();
        int k = pos.z();
        int j1;
        if (!world.isClientSide && Blocks.lightBlock[world.getBlockId(i, j + 1, k)] <= 2) {
            j1 = AetherBlocks.GRASS_AETHER.id();

            world.setBlockWithNotify(i, j, k, j1);
            if (player == null || player.getGamemode().hasBlockConsumption()) {
                --itemStack.stackSize;
                if (player != null) player.swingItem();
            }
            return true;
        }
        return false;
    }
}
