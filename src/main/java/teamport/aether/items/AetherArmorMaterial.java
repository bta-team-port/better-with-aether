package teamport.aether.items;

import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.DamageType;
import turniplabs.halplibe.helper.ArmorHelper;

import static teamport.aether.AetherMod.MOD_ID;

public class AetherArmorMaterial {
    public static final ArmorMaterial ZANITE = ArmorHelper.createArmorMaterial(MOD_ID,
            "zanite",
            200,
            45f,
            45f,
            45f,
            45f);

    public static final ArmorMaterial GRAVITITE = ArmorHelper.createArmorMaterial(MOD_ID,
            "gravitite",
            800,
            56f,
            56f,
            56f,
            134f);

    public static final ArmorMaterial DIAMOND = ArmorHelper.createArmorMaterial(MOD_ID,
            "diamond",
            800,
            66.0F,
            66.0F,
            124.0F,
            66.0F);

    public static final ArmorMaterial STEEL = ArmorHelper.createArmorMaterial(MOD_ID,
            "steel",
            1200,
            55.0F,
            150.0F,
            55.0F,
            55.0F);

    public static final ArmorMaterial PHOENIX = ArmorHelper.createArmorMaterial(MOD_ID,
            "phoenix",
            800,
            45f,
            45f,
            134f,
            45f);

    public static final ArmorMaterial OBSIDIAN = ArmorHelper.createArmorMaterial(MOD_ID,
            "obsidian",
            1200,
            45f,
            134f,
            70f,
            25f);

    public static final ArmorMaterial NEPTUNE = ArmorHelper.createArmorMaterial(MOD_ID,
            "neptune",
            800,
            75f,
            75f,
            20f,
            75f)
            .withProtectionPercentage(DamageType.DROWN, 50f);
}
