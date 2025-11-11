package teamport.aether.mixin.item;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemPaintBrush;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.blocks.skyroot.BlockLogicPaintableSignSkyroot;
import teamport.aether.entity.tile.TileEntitySignSkyroot;

import static net.minecraft.core.item.ItemPaintBrush.getColor;

@Mixin(value = ItemPaintBrush.class, remap = false)
public abstract class ItemPaintBrushMixin extends Item {

    @Shadow
    public abstract void consumePaint(@NonNull ItemStack itemstack, @Nullable Player player);

    public ItemPaintBrushMixin(String name, String namespaceId, int id) {
        super(name, namespaceId, id);
    }

    @Inject(method = "onUseItemOnBlock", at = @At("HEAD"), cancellable = true)
    public void callOnItemUse(ItemStack itemstack, Player entityplayer, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced, CallbackInfoReturnable<Boolean> info) {
        int id = world.getBlockId(blockX, blockY, blockZ);
        Block<?> block = Blocks.getBlock(id);
        DyeColor color;
        if (Block.hasLogicClass(block, BlockLogicPaintableSignSkyroot.class) && entityplayer != null) {
            TileEntitySignSkyroot sign = (TileEntitySignSkyroot) world.getTileEntity(blockX, blockY, blockZ);
            color = getColor(itemstack);
            if (color != null && color.blockMeta != sign.getColor().id) {
                sign.setColor(TextFormatting.get(color.blockMeta));
                this.consumePaint(itemstack, entityplayer);
                info.setReturnValue(true);
            }
        }
    }

}
