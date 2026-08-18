package teamport.aether.item.accessory.gloves;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.HumanArmorShape;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.DamageType;
import org.jspecify.annotations.NonNull;
import teamport.aether.entity.player.PlayerUtil;
import teamport.aether.item.AetherHasCustomDamageType;
import teamport.aether.item.accessory.HumanAccessoryShape;
import teamport.aether.item.accessory.IAccessoryItem;
import teamport.aether.item.accessory.ItemAccessory;

import static teamport.aether.item.accessory.SlotAccessory.GLOVES_SLOT;

public class ItemGloves extends ItemAccessory<HumanAccessoryShape> implements IAccessoryItem<HumanAccessoryShape>, AetherHasCustomDamageType {
    private int damage;
    private DamageType damageType;

    public ItemGloves(String translationKey, String namespaceId, int id, @NonNull ArmorMaterial material, HumanAccessoryShape humanAccessoryShape) {
        super(translationKey, namespaceId, id, material, humanAccessoryShape);
        float maxDurability = HumanArmorShape.BOOTS.getDurabilityModifier() * material.durability;
        this.setMaxDamage((int) Math.ceil(maxDurability));
        this.damageType = DamageType.COMBAT;
    }

    public ItemGloves setDamageType(DamageType damageType) {
        this.damageType = damageType;
        return this;
    }

    public ItemGloves setDamage(int damage) {
        this.damage = damage;
        return this;
    }

    @Override
    public DamageType getDamageType() {
        return this.damageType;
    }

    @Override
    public ArmorMaterial getArmorMaterial() {
        return this.material;
    }

    @Override
    public int armorPieceProtection() {
        return HumanArmorShape.BOOTS.getProtectionValue();
    }

    @Override
    public float getArmorPieceProtectionPercentage() {
        return this.armorPieceProtection() / 40.0f;
    }

    @Override
    public @NonNull HumanAccessoryShape getArmorShape() {
        return HumanAccessoryShape.GLOVES;
    }

    @Override
    public boolean hitEntity(@NonNull ItemStack gloves, @NonNull Mob target, @NonNull Mob attacker) {
        if (!(attacker instanceof Player player)) {
            return super.hitEntity(gloves, target, attacker);
        }
        ItemStack hold = player.getHeldItem();
        if (hold == null && gloves.getItem() instanceof ItemGloves) {
            PlayerUtil.damageItemArmor(player, gloves, GLOVES_SLOT);
            return true;
        }
        return false;
    }

    public int getDamage() {
        return damage;
    }
}

