package teamport.aether.items;

import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.DamageType;
import turniplabs.halplibe.helper.ArmorHelper;

import static teamport.aether.AetherMod.MOD_ID;

public class AetherArmorMaterial {
    public static final ArmorMaterial ZANITE = ArmorHelper.createArmorMaterial(MOD_ID,
            "zanite",
            200,
            45.0f,
            45.0f,
            45.0f,
            45.0f);

    public static final ArmorMaterial GRAVITITE = ArmorHelper.createArmorMaterial(MOD_ID,
            "gravitite",
            800,
            46.0f,
            46.0f,
            46.0f,
            144.0f);

    public static final ArmorMaterial PHOENIX = ArmorHelper.createArmorMaterial(MOD_ID,
            "phoenix",
            1000,
            20.0f,
            75.0f,
            150.0f,
            75.0f)
            .withProtectionPercentage(DamageType.DROWN, -50.0f);

    public static final ArmorMaterial OBSIDIAN = ArmorHelper.createArmorMaterial(MOD_ID,
            "obsidian",
            1000,
            85.0f,
            150.0f,
            85.0f,
            0.0f);

    public static final ArmorMaterial NEPTUNE = ArmorHelper.createArmorMaterial(MOD_ID,
            "neptune",
            1000,
            150.0f,
            70.0f,
            20.0f,
            70.0f)
            .withProtectionPercentage(DamageType.DROWN, 50.0f);
}
