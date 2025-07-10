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
            130f);

    public static final ArmorMaterial PHOENIX = ArmorHelper.createArmorMaterial(MOD_ID,
            "phoenix",
            800,
            130f,
            0f,
            130f,
            0f);

    public static final ArmorMaterial OBSIDIAN = ArmorHelper.createArmorMaterial(MOD_ID,
            "obsidian",
            1200,
            0f,
            130f,
            130f,
            0f);

    public static final ArmorMaterial NEPTUNE = ArmorHelper.createArmorMaterial(MOD_ID,
            "neptune",
            800,
            130f,
            130f,
            0f,
            0f)
            .withProtectionPercentage(DamageType.DROWN, 50f);
}
