package bta.aether.item;

import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.DamageType;
import turniplabs.halplibe.helper.ArmorHelper;

public class AetherArmorMaterial {

    public static final ArmorMaterial ZANITE    = ArmorHelper.createArmorMaterial("zanite", 200, 0f, 0f, 0f, 0f); // all zeros are intended, uses custom protection values;
    public static final ArmorMaterial GRAVITITE = ArmorHelper.createArmorMaterial("gravitite", 800, 66f, 66f, 66f, 150f);
    public static final ArmorMaterial PHOENIX   = ArmorHelper.createArmorMaterial("phoenix", 800, 150f, 0f, 150f, 0f);
    public static final ArmorMaterial OBSIDIAN  = ArmorHelper.createArmorMaterial("obsidian", 1200, 0f, 150f, 150f, 0f);
    public static final ArmorMaterial NEPTUNE   = ArmorHelper.createArmorMaterial("neptune", 800, 150f, 150f, 0f, 0f).withProtectionPercentage(DamageType.DROWN, 50f);

}