package bta.aether.mixin;

import bta.aether.Aether;
import bta.aether.item.AetherItems;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(value = EntityLiving.class, remap = false)
abstract public class EntityLivingMixinGravitite extends Entity {
    @Shadow protected boolean isJumping;

    @Unique
    private boolean usedDoubleJump = false;

    @Unique
    private boolean isJumpingPrev = false;

    public EntityLivingMixinGravitite(World world) {
        super(world);
    }

    @Inject(
            method = "jump",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/core/entity/EntityLiving;isSprinting()Z"
            )
    )
    private void jump(CallbackInfo ci) {
        EntityLiving entity = (EntityLiving) (Object) this;
        if (!(entity instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer player = (EntityPlayer) entity;
        ItemStack bootsSlot = player.inventory.armorItemInSlot(0);
        ItemStack leggingsSlot = player.inventory.armorItemInSlot(1);
        ItemStack chestplateSlot = player.inventory.armorItemInSlot(2);
        ItemStack helmetSlot = player.inventory.armorItemInSlot(3);

        if (
                helmetSlot != null &&
                helmetSlot.itemID == AetherItems.armorHelmetGravitite.id &&
                chestplateSlot != null &&
                chestplateSlot.itemID == AetherItems.armorChestplateGravitite.id &&
                leggingsSlot != null &&
                leggingsSlot.itemID == AetherItems.armorLeggingsGravitite.id &&
                bootsSlot != null &&
                bootsSlot.itemID == AetherItems.armorBootsGravitite.id
        ) {
            yd = 1.05;
        }
    }

    @Inject(
            method = "onLivingUpdate",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/core/entity/EntityLiving;moveStrafing:F",
                    opcode = Opcodes.PUTFIELD,
                    ordinal = 1
            )
    )
    private void onLivingUpdate(CallbackInfo ci) {
        EntityLiving entity = (EntityLiving) (Object) this;
        if (!(entity instanceof EntityPlayer)) {
            return;
        }

        if (noPhysics) {
            usedDoubleJump = true;
            return;
        }

        EntityPlayer player = (EntityPlayer) entity;
        ItemStack bootsSlot = player.inventory.armorItemInSlot(0);
        ItemStack leggingsSlot = player.inventory.armorItemInSlot(1);
        ItemStack chestplateSlot = player.inventory.armorItemInSlot(2);
        ItemStack helmetSlot = player.inventory.armorItemInSlot(3);

        if (
                helmetSlot == null ||
                helmetSlot.itemID != AetherItems.armorHelmetGravitite.id ||
                chestplateSlot == null ||
                chestplateSlot.itemID != AetherItems.armorChestplateGravitite.id ||
                leggingsSlot == null ||
                leggingsSlot.itemID != AetherItems.armorLeggingsGravitite.id ||
                bootsSlot == null ||
                bootsSlot.itemID != AetherItems.armorBootsGravitite.id
        ) {
            return;
        }

        if (!onGround && !isJumpingPrev && isJumping && !usedDoubleJump) {
            Aether.LOGGER.info("double jump!");
            yd = 1.05;

            spawnCloudParticles();
            usedDoubleJump = true;
        }

        if (onGround) {
            usedDoubleJump = false;
        }

        isJumpingPrev = isJumping;
    }

    @Unique
    private void spawnCloudParticles() {
        float width = 1.0f;

        for (int i = 0; i < 20; ++i) {
            double dx = random.nextGaussian() * 0.02;
            double dy = random.nextGaussian() * 0.02;
            double dz = random.nextGaussian() * 0.02;
            world.spawnParticle(
                    "snowshovel",
                    x + (double) (random.nextFloat() * width * 2.0F) - (double) width,
                    y - bbHeight + (double) (random.nextFloat() * width),
                    z + (double) (random.nextFloat() * width * 2.0F) - (double) width,
                    dx, dy, dz
            );
        }
    }
}
