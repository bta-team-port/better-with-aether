package bta.aether.mixin;

import bta.aether.Aether;
import bta.aether.item.AetherItems;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityLiving.class, remap = false)
abstract public class EntityLivingMixinNeptune extends Entity {
    public EntityLivingMixinNeptune(World world) {
        super(world);
    }

    @ModifyConstant(
            method = "moveEntityWithHeading",
            constant = @Constant(floatValue = 0.02f),
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/core/entity/EntityLiving;isInWater()Z"
                    ),
                    to = @At(
                            value = "FIELD",
                            target = "Lnet/minecraft/core/entity/EntityLiving;horizontalCollision:Z"
                    )
            )
    )
    private float changeMoveRelative(float constant) {
        EntityLiving entityLiving = (EntityLiving) (Object) this;
        if (!(entityLiving instanceof EntityPlayer)) {
            return constant;
        }

        EntityPlayer player = (EntityPlayer) entityLiving;
        ItemStack bootsSlot = player.inventory.armorItemInSlot(0);
        ItemStack leggingsSlot = player.inventory.armorItemInSlot(1);
        ItemStack chestplateSlot = player.inventory.armorItemInSlot(2);
        ItemStack helmetSlot = player.inventory.armorItemInSlot(3);
        ItemStack glovesSlot = player.inventory.armorItemInSlot(10);

        if (
                helmetSlot == null ||
                helmetSlot.itemID != AetherItems.armorHelmetNeptune.id ||
                chestplateSlot == null ||
                chestplateSlot.itemID != AetherItems.armorChestplateNeptune.id ||
                leggingsSlot == null ||
                leggingsSlot.itemID != AetherItems.armorLeggingsNeptune.id ||
                bootsSlot == null ||
                bootsSlot.itemID != AetherItems.armorBootsNeptune.id ||
                glovesSlot == null ||
                glovesSlot.itemID != AetherItems.armorGlovesNeptune.id
        ) {
            return constant;
        }


        return speed * 0.4f;
    }

    @Inject(
            method = "moveEntityWithHeading",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/core/entity/EntityLiving;horizontalCollision:Z",
                    ordinal = 0
            )
    )
    private void changeGravity(float moveStrafing, float moveForward, CallbackInfo ci) {
        EntityLiving entityLiving = (EntityLiving) (Object) this;
        if (!(entityLiving instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer player = (EntityPlayer) entityLiving;
        ItemStack bootsSlot = player.inventory.armorItemInSlot(0);
        ItemStack leggingsSlot = player.inventory.armorItemInSlot(1);
        ItemStack chestplateSlot = player.inventory.armorItemInSlot(2);
        ItemStack helmetSlot = player.inventory.armorItemInSlot(3);
        ItemStack glovesSlot = player.inventory.armorItemInSlot(10);

        if (
                helmetSlot == null ||
                helmetSlot.itemID != AetherItems.armorHelmetNeptune.id ||
                chestplateSlot == null ||
                chestplateSlot.itemID != AetherItems.armorChestplateNeptune.id ||
                leggingsSlot == null ||
                leggingsSlot.itemID != AetherItems.armorLeggingsNeptune.id ||
                bootsSlot == null ||
                bootsSlot.itemID != AetherItems.armorBootsNeptune.id ||
                glovesSlot == null ||
                glovesSlot.itemID != AetherItems.armorGlovesNeptune.id
        ) {
            return;
        }

        yd += 0.02;
        yd -= 0.08;
        yd *= 0.98;
    }

    @ModifyConstant(method = "onLivingUpdate", constant = @Constant(doubleValue = 0.04, ordinal = 0))
    private double changeRisingSpeed(double constant) {
        EntityLiving entityLiving = (EntityLiving) (Object) this;
        if (!(entityLiving instanceof EntityPlayer)) {
            return constant;
        }

        EntityPlayer player = (EntityPlayer) entityLiving;
        ItemStack bootsSlot = player.inventory.armorItemInSlot(0);
        ItemStack leggingsSlot = player.inventory.armorItemInSlot(1);
        ItemStack chestplateSlot = player.inventory.armorItemInSlot(2);
        ItemStack helmetSlot = player.inventory.armorItemInSlot(3);
        ItemStack glovesSlot = player.inventory.armorItemInSlot(10);

        if (
                helmetSlot == null ||
                helmetSlot.itemID != AetherItems.armorHelmetNeptune.id ||
                chestplateSlot == null ||
                chestplateSlot.itemID != AetherItems.armorChestplateNeptune.id ||
                leggingsSlot == null ||
                leggingsSlot.itemID != AetherItems.armorLeggingsNeptune.id ||
                bootsSlot == null ||
                bootsSlot.itemID != AetherItems.armorBootsNeptune.id ||
                glovesSlot == null ||
                glovesSlot.itemID != AetherItems.armorGlovesNeptune.id
        ) {
            return constant;
        }

        return 0.16;
    }


}

