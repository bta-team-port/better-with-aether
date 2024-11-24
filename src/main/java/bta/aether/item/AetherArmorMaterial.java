package bta.aether.item;

import bta.aether.Aether;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.DamageType;
import turniplabs.halplibe.helper.ArmorHelper;

public class AetherArmorMaterial {

    // All zeroes are intentional as Zanite uses custom armor protection values. -Cookie
    public static final ArmorMaterial ZANITE    = ArmorHelper.createArmorMaterial(Aether.MOD_ID,
            "zanite",
            200,
            0f,
            0f,
            0f,
            0f);

    public static final ArmorMaterial GRAVITITE = ArmorHelper.createArmorMaterial(Aether.MOD_ID,
            "gravitite",
            800,
            66f,
            66f,
            66f,
            150f);

    public static final ArmorMaterial PHOENIX   = ArmorHelper.createArmorMaterial(Aether.MOD_ID,
            "phoenix",
            800,
            150f,
            0f,
            150f,
            0f);

    public static final ArmorMaterial OBSIDIAN  = ArmorHelper.createArmorMaterial(Aether.MOD_ID,
            "obsidian",
            1200,
            0f,
            150f,
            150f,
            0f);

    public static final ArmorMaterial NEPTUNE   = ArmorHelper.createArmorMaterial(Aether.MOD_ID,
            "neptune",
            800,
            150f,
            150f,
            0f,
            0f)
            .withProtectionPercentage(DamageType.DROWN, 50f);
}