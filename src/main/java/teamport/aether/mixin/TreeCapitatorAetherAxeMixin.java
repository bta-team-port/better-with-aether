package teamport.aether.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.data.gamerule.TreecapitatorHelper;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.chunk.ChunkPosition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.items.AetherItems;

import java.util.Random;

@Mixin(value = TreecapitatorHelper.class, remap = false)
public abstract class TreeCapitatorAetherAxeMixin {
    @Unique
    private ItemStack tool = null;

    @Inject(method = "chopTree", at = @At("HEAD"))
    private void skyrootAxe(CallbackInfoReturnable<Boolean> cir) {
        TreecapitatorHelper help = (TreecapitatorHelper) (Object) this;
        Player player = help.player;
        if (player.getHeldItem() != null) {
            tool = player.getHeldItem();
        }
    }

    @Inject(method = "dropItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;dropItem(IIILnet/minecraft/core/item/ItemStack;)Lnet/minecraft/core/entity/EntityItem;"))
    private void doubleDrop(ItemStack[] items, ChunkPosition pos, CallbackInfo ci, @Local ItemStack stack) {
        TreecapitatorHelper help = (TreecapitatorHelper) (Object) this;
        if (tool.getItem().id == AetherItems.TOOL_AXE_HOLYSTONE.id) {
            Random random = new Random();
            if (random.nextInt(16) == 0) {
                help.world.dropItem(pos.x, pos.y, pos.z, AetherItems.AMBROSIUM.getDefaultStack());
            }
        }
    }
}
