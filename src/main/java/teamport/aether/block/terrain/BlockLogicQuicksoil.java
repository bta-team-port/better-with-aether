package teamport.aether.block.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.block.AetherBlocks;
import teamport.aether.block.BlockLogicDouble;
import teamport.aether.item.AetherItems;

import static teamport.aether.AetherConfig.QUICK_SOIL_SPEED_CAP;

public class BlockLogicQuicksoil extends BlockLogicDouble {
    public BlockLogicQuicksoil(Block<?> block) {
        super(block, Materials.DIRT, () -> AetherBlocks.QUICKSOIL);
        block.friction = 1.1f;
    }

    @Override
    public void onEntityWalkedOn(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Entity walker) {
        walker.xd = Math.max(Math.min(walker.xd, QUICK_SOIL_SPEED_CAP), -QUICK_SOIL_SPEED_CAP);
        walker.zd = Math.max(Math.min(walker.zd, QUICK_SOIL_SPEED_CAP), -QUICK_SOIL_SPEED_CAP);
        super.onEntityWalkedOn(world, tilePos, walker);
    }

    @Override
    public void onDestroyedByPlayer(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Side side, int data, @NonNull Player player, @Nullable Item item) {
        ItemStack heldItem = player.getHeldItem();
        if (heldItem != null && heldItem.getItem().equals(AetherItems.TOOL_SHOVEL_SKYROOT) && data == 0 && player.getGamemode().hasBlockConsumption()) {
            this.onHarvest(world, player, tilePos, 1, world.getTileEntity(tilePos));
        }
    }

}
