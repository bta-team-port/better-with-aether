package teamport.aether.mixin;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.DamageType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.items.AetherArmorMaterial;

import java.util.Random;

@Mixin(value = Player.class, remap = false)
public abstract class PlayerMixin {

    // TODO fire damage still causes fire to be rendered
    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    public void isImmuneToDamageType(Entity attacker, int damage, DamageType type, CallbackInfoReturnable<Boolean> cir) {
        Player player = (Player) (Object) this;
        if (type.equals(DamageType.FIRE) && countArmorPiecesOfMaterial(AetherArmorMaterial.phoenix) == 4) {
            // could not implement Accessor for radom, not sure what it is good for
            Random rand = new Random();
            float take_damage = rand.nextFloat() > (double) 0.05F ? 0 : 4;

            // armor takes damage
            player.inventory.damageArmor((int) Math.ceil((double) take_damage / (double) 4.0F));
            cir.setReturnValue(false);
            return;
        }
        if (type.equals(DamageType.FALL) && countArmorPiecesOfMaterial(AetherArmorMaterial.gravitite) == 4) {
            // armor takes damage
            player.inventory.damageArmor((int) Math.ceil((double) damage / (double) 4.0F));
            cir.setReturnValue(false);
        }
    }

    @Unique
    private int countArmorPiecesOfMaterial(ArmorMaterial material) {
        int count = 0;
        Player player = (Player) (Object) this;
        for (int i = 0; i < player.inventory.armorInventory.length; ++i) {
            ItemStack itemStack = player.inventory.armorInventory[i];
            if (itemStack == null || !(itemStack.getItem() instanceof IArmorItem)) {
                continue;
            }
            IArmorItem armor = (IArmorItem) itemStack.getItem();
            if (armor.getArmorPiece() != i) {
                continue;
            }
            ArmorMaterial armorMaterial = armor.getArmorMaterial();
            if (armorMaterial != null && !armorMaterial.equals(material)) {
                continue;
            }
            count++;
        }
        return count;
    }
}
