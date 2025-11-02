package teamport.aether.items.accessory;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.DamageType;
import org.jetbrains.annotations.Nullable;
import teamport.aether.items.AetherHasCustomDamageType;

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
    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
        itemstack.damageItem(1, attacker);
        return true;
    }
}

