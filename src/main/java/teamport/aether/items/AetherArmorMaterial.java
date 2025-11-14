package teamport.aether.items;

import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.DamageType;
import teamport.aether.AetherMod;
import turniplabs.halplibe.helper.ArmorHelper;

import static teamport.aether.AetherMod.MOD_ID;

public class AetherArmorMaterial {
    public static final ArmorMaterial ZANITE = ArmorHelper.createArmorMaterial(MOD_ID,
            "zanite",
            200,
            45.0f,
            45.0f,
            45.0f,
            45.0f)
        .withProtectionPercentage(AetherMod.HOLY, -45.0f)
        .withProtectionPercentage(AetherMod.LIGHTNING, 45.0f);


    public static final ArmorMaterial GRAVITITE = ArmorHelper.createArmorMaterial(MOD_ID,
            "gravitite",
            800,
            56.0f,
            56.0f,
            56.0f,
            154.0f)
        .withProtectionPercentage(AetherMod.HOLY, 56.0f)
        .withProtectionPercentage(AetherMod.LIGHTNING, -56.0f);


    public static final ArmorMaterial PHOENIX = ArmorHelper.createArmorMaterial(MOD_ID,
            "phoenix",
            1000,
            25.0f,
            65.0f,
            150.0f,
            65.0f)
        .withProtectionPercentage(AetherMod.HOLY, 65.0f)
        .withProtectionPercentage(AetherMod.LIGHTNING, -65.0f)
        .withProtectionPercentage(DamageType.DROWN, -50.0f);


    public static final ArmorMaterial OBSIDIAN = ArmorHelper.createArmorMaterial(MOD_ID,
            "obsidian",
            1000,
            65.0f,
            150.0f,
            65.0f,
            25.0f)
        .withProtectionPercentage(AetherMod.HOLY, -65.0f)
        .withProtectionPercentage(AetherMod.LIGHTNING, 65.0f);


    public static final ArmorMaterial NEPTUNE = ArmorHelper.createArmorMaterial(MOD_ID,
            "neptune",
            1000,
            65.0f,
            65.0f,
            25.0f,
            65.0f)
        .withProtectionPercentage(AetherMod.HOLY, 150.0f)
        .withProtectionPercentage(AetherMod.LIGHTNING, -65.0f)
        .withProtectionPercentage(DamageType.DROWN, 50.0f);
}
