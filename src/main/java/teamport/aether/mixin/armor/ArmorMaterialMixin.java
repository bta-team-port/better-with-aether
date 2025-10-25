package teamport.aether.mixin.armor;

import net.minecraft.core.item.material.ArmorMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.AetherMod;

@Mixin(value = ArmorMaterial.class, remap = false)
public class ArmorMaterialMixin {

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void customizeVanillaProtections(CallbackInfo ci) {

        ArmorMaterial.LEATHER
                .withProtectionPercentage(AetherMod.HOLY, 25.0f)
                .withProtectionPercentage(AetherMod.LIGHTNING, 10.0f);

        ArmorMaterial.CHAINMAIL
                .withProtectionPercentage(AetherMod.HOLY, 120.0f)
                .withProtectionPercentage(AetherMod.LIGHTNING, -25.0f);

        ArmorMaterial.IRON
                .withProtectionPercentage(AetherMod.HOLY, 80.0f)
                .withProtectionPercentage(AetherMod.LIGHTNING, -40.0f);

        ArmorMaterial.GOLD
                .withProtectionPercentage(AetherMod.HOLY, 70.0f)
                .withProtectionPercentage(AetherMod.LIGHTNING, -70.0f);

        ArmorMaterial.DIAMOND
                .withProtectionPercentage(AetherMod.HOLY, -66.0f)
                .withProtectionPercentage(AetherMod.LIGHTNING, 66.0f);

        ArmorMaterial.STEEL
                .withProtectionPercentage(AetherMod.HOLY, 55.0f)
                .withProtectionPercentage(AetherMod.LIGHTNING, -55.0f);
    }
}
