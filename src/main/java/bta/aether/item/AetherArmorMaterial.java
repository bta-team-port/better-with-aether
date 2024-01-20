package bta.aether.item;

import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.DamageType;

public class AetherArmorMaterial {

    // TODO: Replace them with the real stats, currently everything is set to Steel stats
    public static final ArmorMaterial ZANITE = ArmorMaterial.register(new ArmorMaterial("zanite", 5, 1200).withProtectionPercentage(DamageType.COMBAT, 55.0f).withProtectionPercentage(DamageType.BLAST, 150.0f).withProtectionPercentage(DamageType.FIRE, 55.0f).withProtectionPercentage(DamageType.FALL, 55.0f));
    public static final ArmorMaterial GRAVITITE = ArmorMaterial.register(new ArmorMaterial("gravitite", 5, 1200).withProtectionPercentage(DamageType.COMBAT, 55.0f).withProtectionPercentage(DamageType.BLAST, 150.0f).withProtectionPercentage(DamageType.FIRE, 55.0f).withProtectionPercentage(DamageType.FALL, 55.0f));
    public static final ArmorMaterial PHOENIX = ArmorMaterial.register(new ArmorMaterial("phoenix", 5, 1200).withProtectionPercentage(DamageType.COMBAT, 55.0f).withProtectionPercentage(DamageType.BLAST, 150.0f).withProtectionPercentage(DamageType.FIRE, 55.0f).withProtectionPercentage(DamageType.FALL, 55.0f));
    public static final ArmorMaterial OBSIDIAN = ArmorMaterial.register(new ArmorMaterial("obsidian", 5, 1200).withProtectionPercentage(DamageType.COMBAT, 55.0f).withProtectionPercentage(DamageType.BLAST, 150.0f).withProtectionPercentage(DamageType.FIRE, 55.0f).withProtectionPercentage(DamageType.FALL, 55.0f));
    public static final ArmorMaterial NEPTUNE = ArmorMaterial.register(new ArmorMaterial("neptune", 5, 1200).withProtectionPercentage(DamageType.COMBAT, 55.0f).withProtectionPercentage(DamageType.BLAST, 150.0f).withProtectionPercentage(DamageType.FIRE, 55.0f).withProtectionPercentage(DamageType.FALL, 55.0f));

    // Used by pendant and ring
    public static final ArmorMaterial ICE = ArmorMaterial.register(new ArmorMaterial("ice", 5, 1200).withProtectionPercentage(DamageType.COMBAT, 55.0f).withProtectionPercentage(DamageType.BLAST, 150.0f).withProtectionPercentage(DamageType.FIRE, 55.0f).withProtectionPercentage(DamageType.FALL, 55.0f));

}