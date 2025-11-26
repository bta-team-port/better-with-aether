package teamport.aether.mixin.armor;

import net.minecraft.core.item.material.ArmorMaterial;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.AetherMod;

@Mixin(value = ArmorMaterial.class, remap = false)
public abstract class ArmorMaterialMixin {
    static {
        ArmorMaterial.LEATHER
            .withProtectionPercentage(AetherMod.HOLY, 20.0f)
            .withProtectionPercentage(AetherMod.LIGHTNING, 120.0f);

        ArmorMaterial.CHAINMAIL
            .withProtectionPercentage(AetherMod.HOLY, 35.0f)
            .withProtectionPercentage(AetherMod.LIGHTNING, -18.0f);

        ArmorMaterial.IRON
            .withProtectionPercentage(AetherMod.HOLY, 45.0f)
            .withProtectionPercentage(AetherMod.LIGHTNING, -23.0f);

        ArmorMaterial.GOLD
            .withProtectionPercentage(AetherMod.HOLY, 70.0f)
            .withProtectionPercentage(AetherMod.LIGHTNING, -35.0f);

        ArmorMaterial.DIAMOND
            .withProtectionPercentage(AetherMod.HOLY, -33.0f)
            .withProtectionPercentage(AetherMod.LIGHTNING, 66.0f);

        ArmorMaterial.STEEL
            .withProtectionPercentage(AetherMod.HOLY, 55.0f)
            .withProtectionPercentage(AetherMod.LIGHTNING, -28.0f);
    }
}
