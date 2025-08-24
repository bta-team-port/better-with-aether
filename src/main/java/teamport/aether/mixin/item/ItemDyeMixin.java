package teamport.aether.mixin.item;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemDye;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.blocks.BlockLogicSignSkyroot;
import teamport.aether.entity.tile.TileEntitySignSkyroot;

@Mixin(value = ItemDye.class, remap = false)
public abstract class ItemDyeMixin extends Item {

    public ItemDyeMixin(String name, String namespaceId, int id) {
        super(name, namespaceId, id);
    }

    @Inject(method = "onUseItemOnBlock", at = @At("HEAD"), cancellable = true)
    public void callOnItemUse(ItemStack itemstack, Player entityplayer, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced, CallbackInfoReturnable<Boolean> info) {
        Block<?> block = world.getBlock(blockX, blockY, blockZ);
        if (Block.hasLogicClass(block, BlockLogicSignSkyroot.class)) {
            TileEntitySignSkyroot sign = (TileEntitySignSkyroot)world.getTileEntity(blockX, blockY, blockZ);
            if (DyeColor.WHITE.itemMeta - itemstack.getMetadata() == sign.getColor().id) {
                info.setReturnValue(false);
            } else {
                sign.setColor(TextFormatting.get(DyeColor.WHITE.itemMeta - itemstack.getMetadata()));
                if (entityplayer == null || entityplayer.getGamemode().consumeBlocks()) {
                    --itemstack.stackSize;
                }

                info.setReturnValue(true);
            }
        }
    }
}
