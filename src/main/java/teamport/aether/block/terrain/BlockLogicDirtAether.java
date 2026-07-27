package teamport.aether.block.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.IBonemealable;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
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
    public void onBlockPlacedByMob(World world, int x, int y, int z, @NonNull Side side, Mob mob, double xPlaced, double yPlaced) {
        world.setBlockMetadataWithNotify(x, y, z, 1);
    }

    @Override
    public int getPlacedBlockMetadata(@Nullable Player player, ItemStack stack, World world, int x, int y, int z, Side side, double xPlaced, double yPlaced) {
        return 1;
    }

    @Override
    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, Side side, int meta, Player player, Item item) {
        ItemStack heldItem = player.getHeldItem();
        if (heldItem != null && heldItem.getItem().equals(AetherItems.TOOL_SHOVEL_SKYROOT) && meta == 0 && player.getGamemode().hasBlockConsumption()) {
            this.onHarvest(world, player, new TilePos(x, y, z), 1, world.getTileEntity(x, y, z));
        }
    }

    @Override
    public boolean onBonemealUsed(ItemStack itemStack, @Nullable Player player, World world, TilePosc pos, Side side, double d, double e) {
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
