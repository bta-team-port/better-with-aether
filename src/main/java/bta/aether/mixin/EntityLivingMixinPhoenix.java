package bta.aether.mixin;

import bta.aether.item.AetherItems;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = EntityLiving.class, remap = false)
abstract public class EntityLivingMixinPhoenix extends Entity {
    public EntityLivingMixinPhoenix(World world) {
        super(world);
    }

    @ModifyConstant(method = "moveEntityWithHeading", constant = @Constant(doubleValue = 0.5))
    private double changeDrag(double constant) {
        EntityLiving entityLiving = (EntityLiving) (Object) this;
        if (!(entityLiving instanceof EntityPlayer)) {
            return constant;
        }

        EntityPlayer player = (EntityPlayer) entityLiving;
        ItemStack bootsSlot = player.inventory.armorItemInSlot(0);
        ItemStack leggingsSlot = player.inventory.armorItemInSlot(1);
        ItemStack chestplateSlot = player.inventory.armorItemInSlot(2);
        ItemStack helmetSlot = player.inventory.armorItemInSlot(3);

        if (
                helmetSlot == null ||
                        helmetSlot.itemID != AetherItems.armorHelmetPhoenix.id ||
                        chestplateSlot == null ||
                        chestplateSlot.itemID != AetherItems.armorChestplatePhoenix.id ||
                        leggingsSlot == null ||
                        leggingsSlot.itemID != AetherItems.armorLeggingsPhoenix.id ||
                        bootsSlot == null ||
                        bootsSlot.itemID != AetherItems.armorBootsPhoenix.id
        ) {
            return constant;
        }

        return 0.8;
    }
}
