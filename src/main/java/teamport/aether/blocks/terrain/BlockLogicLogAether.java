package teamport.aether.blocks.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicAxisAligned;
import net.minecraft.core.block.BlockLogicLog;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.PlacementMode;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Axis;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import teamport.aether.items.AetherItems;
import teamport.aether.mixin.accessors.ItemAccessor;

import java.util.Random;

public class BlockLogicLogAether extends BlockLogicLog {

    public BlockLogicLogAether(Block<?> block) {
        super(block);
    }

    @Override
    public void onBlockPlacedByMob(World world, int x, int y, int z, @NonNull Side side, Mob mob, double xPlaced, double yPlaced) {
        Axis axis = mob.getPlacementDirection(side, PlacementMode.SIDE).getAxis();
        world.setBlockMetadataWithNotify(x, y, z, BlockLogicAxisAligned.axisToMeta(axis) + 4);
    }

    @Override
    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, Side side, int meta, Player player, Item item) {
        ItemStack heldItem = player.getHeldItem();
        if (heldItem != null && heldItem.getItem().equals(AetherItems.TOOL_AXE_SKYROOT) && meta == 0 && player.getGamemode().consumeBlocks()) {
            this.harvestBlock(world, player, x, y, z, 1, world.getTileEntity(x, y, z));
        }
    }

    /**
     * @implNote This is a modifies BreakResult for TreecapitatorHelper, to allow AetherTrees to work nicely with the gamerule
     */
    @SuppressWarnings("java:S1172")
    public ItemStack[] getAdditionalBreakResult(World world, Item tool, ItemStack[] results, int meta) {
        if (results == null) return new ItemStack[0];
        if (tool != null && tool.equals(AetherItems.TOOL_AXE_SKYROOT) && meta == 0) {
            ItemStack[] doubleStack = new ItemStack[results.length << 1];
            System.arraycopy(results, 0, doubleStack, 0, results.length);
            System.arraycopy(results, 0, doubleStack, results.length, results.length);
            return doubleStack;
        }
        if (tool != null && tool.equals(AetherItems.TOOL_AXE_HOLYSTONE)) {
            if (results.length > 64) throw new IllegalStateException("Expected results.length <= 64 but got " + results.length);
            Random random = ((ItemAccessor) tool).getItemRand();
            int count = 0;
            for (int i = 0; i < results.length; i++) {
                if (random.nextInt(16) == 0) {
                    count++;
                }
            }
            ItemStack[] addedAmbrosium = new ItemStack[results.length + 1];
            System.arraycopy(results, 0, addedAmbrosium, 0, results.length);
            addedAmbrosium[results.length] = new ItemStack(AetherItems.AMBROSIUM, count);
            return addedAmbrosium;
        }
        return results;
    }
}
