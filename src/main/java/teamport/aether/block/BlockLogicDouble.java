package teamport.aether.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicCobble;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.item.AetherItems;

import java.util.function.Supplier;

public class BlockLogicDouble extends BlockLogicCobble {
    public BlockLogicDouble(Block<?> block, Material material, @Nullable Supplier<? extends IItemConvertible> crushResult) {
        super(block, material, crushResult);
    }

    @Override
    public int getPlacedData(@Nullable Player player, @NonNull ItemStack itemStack, @NonNull World world, @NonNull TilePosc tilePos, @NonNull Side side, double xHit, double yHit) {
        return 1;
    }

    @Override
    public void onDestroyedByPlayer(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Side side, int data, @NonNull Player player, @Nullable Item item) {
        ItemStack heldItem = player.getHeldItem();
        if (heldItem != null && heldItem.getItem().equals(AetherItems.TOOL_PICKAXE_SKYROOT) && data == 0 && player.getGamemode().hasBlockConsumption()) {
            this.onHarvest(world, player, tilePos, 1, world.getTileEntity(tilePos));
        }
    }

}
