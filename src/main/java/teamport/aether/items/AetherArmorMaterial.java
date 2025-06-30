package teamport.aether.items;

import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.DamageType;
import turniplabs.halplibe.helper.ArmorHelper;

import static teamport.aether.AetherMod.MOD_ID;

public class AetherArmorMaterial {
    public static final ArmorMaterial zanite = ArmorHelper.createArmorMaterial(MOD_ID,
            "zanite",
            200,
            0f,
            0f,
            0f,
            0f);

    public static final ArmorMaterial gravitite = ArmorHelper.createArmorMaterial(MOD_ID,
            "gravitite",
            800,
            66f,
            66f,
            66f,
            150f);

    public static final ArmorMaterial phoenix = ArmorHelper.createArmorMaterial(MOD_ID,
            "phoenix",
            800,
            150f,
            0f,
            150f,
            0f);

    public static final ArmorMaterial obsidian = ArmorHelper.createArmorMaterial(MOD_ID,
            "obsidian",
            1200,
            0f,
            150f,
            150f,
            0f);

    public static final ArmorMaterial neptune = ArmorHelper.createArmorMaterial(MOD_ID,
            "neptune",
            800,
            150f,
            150f,
            0f,
            0f)
            .withProtectionPercentage(DamageType.DROWN, 50f);
}
