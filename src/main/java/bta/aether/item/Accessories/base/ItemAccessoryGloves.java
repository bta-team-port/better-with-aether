package bta.aether.item.Accessories.base;

import bta.aether.entity.IAetherAccessories;
import bta.aether.item.ItemToolAccessory;
import bta.aether.item.TexturePath;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;

public class ItemAccessoryGloves extends ItemToolAccessory implements TexturePath {
    public static final int[] armorPieceProtectionValues = new int[]{3, 8, 6, 3};
    private static final float[] armorPieceDurabilityModifiers = new float[]{0.91f, 1.0f, 0.97f, 0.94f};
    private final String texturePath;
    public final ArmorMaterial material;
    public final int armorPiece;

    public ItemAccessoryGloves(String name, int id, String texturePath, ArmorMaterial material, int armorPiece) {
        super(name, id);
        this.texturePath = texturePath;
        this.material = material;
        this.armorPiece = armorPiece;
        this.setMaxDamage((int)(armorPieceDurabilityModifiers[armorPiece] * (float)material.durability));
    }

    public int armorPieceProtection() {
        return armorPieceProtectionValues[this.armorPiece];
    }

    public float getArmorPieceProtectionPercentage() {
        return (float)this.armorPieceProtection() / 20.0f;
    }

    @Override
    public String[] getAccessoryTypes(ItemStack itemStack) {
        return new String[]{"gloves"};
    }

    @Override
    public void onAccessoryRemoved(EntityPlayer player, ItemStack accessory) {
        ((IAetherAccessories)player).aether$setInvisible(false);
    }

    public String getTexturePath() {
        return texturePath;
    }
}
