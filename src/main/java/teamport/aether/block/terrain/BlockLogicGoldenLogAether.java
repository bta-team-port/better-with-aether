package teamport.aether.block.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.item.AetherItems;
import teamport.aether.item.item_tool.ItemToolAxeAether;
import turniplabs.halplibe.helper.EnvironmentHelper;

public class BlockLogicGoldenLogAether extends BlockLogicLogAether {

    public BlockLogicGoldenLogAether(Block<?> block) {
        super(block);
    }

    @Override
    public void onDestroyedByPlayer(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Side side, int data, @NonNull Player player, @Nullable Item item) {
        super.onDestroyedByPlayer(world, tilePos, side, data, player, item);
        ItemStack heldItem = player.getHeldItem();
        if (heldItem != null && data <= 2 && player.getGamemode().hasBlockConsumption() && !EnvironmentHelper.isMultiplayerClient() && heldItem.getItem() instanceof ItemToolAxeAether) {
            world.dropItem(tilePos, new ItemStack(AetherItems.AMBER, world.rand.nextInt(3) + 1));
        }
    }

    @Override
    public ItemStack[] getAdditionalBreakResult(World world, Item tool, ItemStack[] results, int meta) {
        ItemStack[] additionalResults = super.getAdditionalBreakResult(world, tool, results, meta);
        ItemStack[] addedAmber = new ItemStack[additionalResults.length + results.length];
        System.arraycopy(additionalResults, 0, addedAmber, 0, additionalResults.length);
        for (int i = 0; i < results.length; i++) {
            addedAmber[additionalResults.length + i] = new ItemStack(AetherItems.AMBER, world.rand.nextInt(3) + 1);
        }
        return addedAmber;
    }
}
