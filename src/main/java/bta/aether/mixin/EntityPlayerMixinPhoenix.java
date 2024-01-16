package bta.aether.mixin;

import bta.aether.item.AetherItems;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityPlayer.class, remap = false)
abstract public class EntityPlayerMixinPhoenix extends EntityLiving {
    @Shadow public InventoryPlayer inventory;

    public EntityPlayerMixinPhoenix(World world) {
        super(world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        ItemStack bootsSlot = inventory.armorItemInSlot(0);
        ItemStack leggingsSlot = inventory.armorItemInSlot(1);
        ItemStack chestplateSlot = inventory.armorItemInSlot(2);
        ItemStack helmetSlot = inventory.armorItemInSlot(3);

        fireImmune = helmetSlot != null &&
                helmetSlot.itemID == AetherItems.armorHelmetPhoenix.id &&
                chestplateSlot != null &&
                chestplateSlot.itemID == AetherItems.armorChestplatePhoenix.id &&
                leggingsSlot != null &&
                leggingsSlot.itemID == AetherItems.armorLeggingsPhoenix.id &&
                bootsSlot != null &&
                bootsSlot.itemID == AetherItems.armorBootsPhoenix.id;

        if (fireImmune && random.nextInt(3) == 0) {
            spawnFlameParticles();
        }
    }

    @Unique
    private void spawnFlameParticles() {
        double dx = random.nextGaussian() * 0.02;
        double dy = random.nextGaussian() * 0.02;
        double dz = random.nextGaussian() * 0.02;
        world.spawnParticle(
                "flame",
                x + (double) (random.nextFloat() * bbWidth * 2.0F) - (double) bbWidth,
                y + (double) (random.nextFloat() * bbHeight) - (double) bbHeight,
                z + (double) (random.nextFloat() * bbWidth * 2.0F) - (double) bbWidth,
                dx, dy, dz
        );
    }
}
