package teamport.aether.items.accessory;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.DamageType;
import teamport.aether.items.AetherHasCustomDamageType;

public class ItemGloves extends ItemAccessoryArmor implements IArmorItem, AetherHasCustomDamageType {
    private final ArmorMaterial material;
    private int damage;
    private DamageType damageType;

    public ItemGloves(String translationKey, String namespaceId, int id, ArmorMaterial material, int accessoryPiece) {
        super(translationKey, namespaceId, id, material.identifier.value(), accessoryPiece);
        this.material = material;
        float maxDurability = IArmorItem.ARMOR_PIECE_DURABILITY_MODIFIERS[3] * material.durability;
        this.setMaxDamage((int) Math.ceil(maxDurability));
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
    public ArmorMaterial getArmorMaterial() {
        return this.material;
    }

    @Override
    public int armorPieceProtection() {
        return 3;
    }

    @Override
    public float getArmorPieceProtectionPercentage() {
        return this.armorPieceProtection() / 40.0f;
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
    public int getDamage() {
        return damage;
    }
}

