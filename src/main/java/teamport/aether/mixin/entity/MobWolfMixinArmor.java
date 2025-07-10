package teamport.aether.mixin.entity;


import net.minecraft.core.entity.animal.MobAnimal;
import net.minecraft.core.entity.animal.MobWolf;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.items.AetherArmorMaterial;
import teamport.aether.items.AetherItems;

import static net.minecraft.core.entity.animal.MobWolf.ARMOR_MATERIALS;

// TODO missing textures for wolfs with armour
@Mixin(value = MobWolf.class)
public abstract class MobWolfMixinArmor extends MobAnimal {

    public MobWolfMixinArmor(World world) {
        super(world);
    }

    static {
        ARMOR_MATERIALS.put(AetherArmorMaterial.PHOENIX, (ItemArmor) AetherItems.ARMOR_CHESTPLATE_PHOENIX);
        ARMOR_MATERIALS.put(AetherArmorMaterial.NEPTUNE, (ItemArmor) AetherItems.ARMOR_CHESTPLATE_NEPTUNE);
        ARMOR_MATERIALS.put(AetherArmorMaterial.OBSIDIAN, (ItemArmor) AetherItems.ARMOR_CHESTPLATE_OBSIDIAN);
        ARMOR_MATERIALS.put(AetherArmorMaterial.GRAVITITE, (ItemArmor) AetherItems.ARMOR_CHESTPLATE_GRAVITITE);
        ARMOR_MATERIALS.put(AetherArmorMaterial.ZANITE, (ItemArmor) AetherItems.ARMOR_CHESTPLATE_ZANITE);
    }

}
