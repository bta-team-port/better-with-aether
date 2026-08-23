package teamport.aether.mixin.item;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.HumanArmorShape;
import net.minecraft.core.item.ItemQuiver;
import net.minecraft.core.item.ItemQuiverEndless;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.item.accessory.HumanAccessoryShape;
import teamport.aether.item.accessory.IAccessoryEffects;
import teamport.aether.item.accessory.IAccessoryWearing;

@Mixin({ItemQuiver.class, ItemQuiverEndless.class})
public abstract class ItemQuiverCapeMixin {

    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    private void equipQuiverToCapeOrChest(ItemStack selfStack, World world, Player player, CallbackInfoReturnable<ItemStack> cir) {
        if (!(player instanceof IAccessoryWearing<?> accessoryPlayer)) {
            return;
        }

        ItemStack currentChest = player.getItemInArmorSlot(HumanArmorShape.CHEST);
        ItemStack currentCape = accessoryPlayer.getAccessoryInSlot(HumanAccessoryShape.CAPE.getSlotIndex());

        if (player.isSneaking() || (currentChest != null && currentCape == null)) {
            if (currentCape != null && currentCape.getItem() instanceof IAccessoryEffects oldEffects) {
                oldEffects.removeEffect(player, currentCape);
            }

            ItemStack equippedStack = selfStack.splitStack(1);
            accessoryPlayer.setAccessoryInSlot(HumanAccessoryShape.CAPE.getSlotIndex(), equippedStack);

            if (equippedStack.getItem() instanceof IAccessoryEffects newEffects) {
                newEffects.addEffect(player, equippedStack);
            }

            player.world.playSoundAtEntity(player, player, "random.equip", 1.0F, 1.0F);
            cir.setReturnValue(currentCape != null ? currentCape : cleanEmptyStack(selfStack));
        } else {
            player.setItemInArmorSlot(HumanArmorShape.CHEST, selfStack.splitStack(1));
            cir.setReturnValue(currentChest != null ? currentChest : cleanEmptyStack(selfStack));
        }
    }

    @Unique
    private static ItemStack cleanEmptyStack(ItemStack stack) {
        return (stack != null && stack.stackSize <= 0) ? null : stack;
    }
}
