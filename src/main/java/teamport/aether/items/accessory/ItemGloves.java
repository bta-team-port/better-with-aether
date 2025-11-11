package teamport.aether.items.accessory;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.DamageType;
import org.jspecify.annotations.Nullable;
import teamport.aether.items.AetherHasCustomDamageType;

import static teamport.aether.items.accessory.SlotAccessory.GLOVES_SLOT;

public class ItemGloves extends ItemAccessoryArmor implements IArmorItem, AetherHasCustomDamageType {
    public final ArmorMaterial material;
    public int damage;
    public DamageType damageType;

    public ItemGloves(String translationKey, String namespaceId, int id, ArmorMaterial material, int accessoryPiece) {
        super(translationKey, namespaceId, id, material.identifier.value(), accessoryPiece);
        this.material = material;
        float maxDurability = ItemArmor.ARMOR_PIECE_DURABILITY_MODIFIERS[3] * material.durability;
        this.setMaxDamage((int) Math.ceil(maxDurability));
        this.damage = 1;
        this.damageType = DamageType.COMBAT;
    }

    public ItemGloves setDamageType(DamageType damageType){
        this.damageType = damageType;
        return this;
    }

    public ItemGloves setDamage(int damage) {
        this.damage = damage;
        return this;
    }

    public DamageType getDamageType() {
        return this.damageType;
    }

    @Override
    public @Nullable ArmorMaterial getArmorMaterial() {
        return this.material;
    }

    @Override
    public int armorPieceProtection() {
        return 3;
    }

    @Override
    public float getArmorPieceProtectionPercentage() {
        return (float) this.armorPieceProtection() / 40.0f;
    }

    @Override
    public int getArmorPiece() {
        return this.getSlotID();
    }

    @Override
    public boolean hitEntity(ItemStack gloves, Mob target, Mob attacker) {
        if(!(attacker instanceof Player)){
            return super.hitEntity(gloves, target, attacker);
        }
        Player player = (Player) attacker;
        ItemStack hold = player.getHeldItem();
        if (hold == null && gloves != null && gloves.getItem() instanceof ItemGloves) {
            gloves.damageItem(1, attacker);
            return true;
        }
        return false;
    }
}

