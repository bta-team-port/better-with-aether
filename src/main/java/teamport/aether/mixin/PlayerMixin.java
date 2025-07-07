package teamport.aether.mixin;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.DamageType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.items.AetherArmorMaterial;

import java.util.Random;

@Mixin(value = Player.class, remap = false)
public abstract class PlayerMixin {

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    public void isImmuneToDamageType(Entity attacker, int damage, DamageType type, CallbackInfoReturnable<Boolean> cir) {
        // armor still needs to take damage
        Player player = (Player) (Object) this;
        if (immunityArmour(type, DamageType.FIRE, AetherArmorMaterial.phoenix)) {
            // could not implement Accessor for radom, not sure what it is good for
            Random rand = new Random();
            float take_damage = rand.nextFloat() > (double) 0.01F ? 0 : damage;
            player.inventory.damageArmor((int) Math.ceil((double) take_damage / (double) 4.0F));
            cir.setReturnValue(false);
        }
        if (immunityArmour(type, DamageType.FALL, AetherArmorMaterial.gravitite)) {
            player.inventory.damageArmor((int) Math.ceil((double) damage / (double) 4.0F));
            cir.setReturnValue(false);
        }
    }

    private boolean immunityArmour(DamageType type, DamageType immunity, ArmorMaterial material) {
        if (!type.equals(immunity)) return false;
        Player player = (Player) (Object) this;
        for (int i = 0; i < player.inventory.armorInventory.length; ++i) {
            ItemStack itemStack = player.inventory.armorInventory[i];
            if (itemStack == null || !(itemStack.getItem() instanceof IArmorItem)) {
                return false;
            }
            IArmorItem armor = (IArmorItem) itemStack.getItem();
            if (armor.getArmorPiece() != i) {
                return false;
            }
            ArmorMaterial armorMaterial = armor.getArmorMaterial();
            if (!armorMaterial.equals(material)) {
                return false;
            }
        }
        return true;
    }
}
