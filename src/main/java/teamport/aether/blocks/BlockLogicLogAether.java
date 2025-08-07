package teamport.aether.blocks;

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
import org.jetbrains.annotations.NotNull;
import teamport.aether.items.AetherItems;

public class BlockLogicLogAether extends BlockLogicLog {
    public boolean golden;

    public BlockLogicLogAether(Block<?> block, boolean golden) {
        super(block);
        this.golden = golden;
    }

    @Override
    public void onBlockPlacedByMob(World world, int x, int y, int z, @NotNull Side side, Mob mob, double xPlaced, double yPlaced) {
        Axis axis = mob.getPlacementDirection(side, PlacementMode.SIDE).getAxis();
        world.setBlockMetadataWithNotify(x, y, z, BlockLogicAxisAligned.axisToMeta(axis) + 4);
    }

    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, Side side, int meta, Player player, Item item) {
        ItemStack heldItem = player.getHeldItem();
        if (heldItem != null && heldItem.getItem().equals(AetherItems.TOOL_AXE_SKYROOT) && meta == 0 && player.getGamemode().consumeBlocks()) {
            this.harvestBlock(world, player, x, y, z, 1, world.getTileEntity(x, y, z));
        } else if (heldItem != null && meta == 0 && this.golden && player.getGamemode().consumeBlocks()) {
            if (heldItem.getItem().equals(AetherItems.TOOL_AXE_HOLYSTONE)) {
                world.dropItem(x, y, z, new ItemStack(AetherItems.AMBER, world.rand.nextInt(3) + 1));
            } else if (heldItem.getItem().equals(AetherItems.TOOL_AXE_ZANITE)) {
                world.dropItem(x, y, z, new ItemStack(AetherItems.AMBER, world.rand.nextInt(3) + 1));
            } else if (heldItem.getItem().equals(AetherItems.TOOL_AXE_GRAVITITE)) {
                world.dropItem(x, y, z, new ItemStack(AetherItems.AMBER, world.rand.nextInt(3) + 1));
            } else if (heldItem.getItem().equals(AetherItems.TOOL_AXE_VALKYRIE)) {
                world.dropItem(x, y, z, new ItemStack(AetherItems.AMBER, world.rand.nextInt(3) + 1));
            }
        }
    }
}
