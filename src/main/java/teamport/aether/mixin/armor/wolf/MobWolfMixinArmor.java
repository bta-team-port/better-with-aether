package teamport.aether.mixin.armor.wolf;

import net.minecraft.core.entity.animal.MobWolf;
import net.minecraft.core.item.IArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.item.AetherArmorMaterial;
import teamport.aether.item.AetherItems;

import static net.minecraft.core.entity.animal.MobWolf.ARMOR_MATERIALS;

@Mixin(MobWolf.class)
public abstract class MobWolfMixinArmor {
    static {
        /// TODO Replace the chestplates with wolf armor items
        ARMOR_MATERIALS.put(AetherArmorMaterial.PHOENIX, (IArmorItem<?>) AetherItems.ARMOR_CHESTPLATE_PHOENIX);
        ARMOR_MATERIALS.put(AetherArmorMaterial.NEPTUNE, (IArmorItem<?>) AetherItems.ARMOR_CHESTPLATE_NEPTUNE);
        ARMOR_MATERIALS.put(AetherArmorMaterial.OBSIDIAN, (IArmorItem<?>) AetherItems.ARMOR_CHESTPLATE_OBSIDIAN);
        ARMOR_MATERIALS.put(AetherArmorMaterial.GRAVITITE, (IArmorItem<?>) AetherItems.ARMOR_CHESTPLATE_GRAVITITE);
        ARMOR_MATERIALS.put(AetherArmorMaterial.ZANITE, (IArmorItem<?>) AetherItems.ARMOR_CHESTPLATE_ZANITE);
    }
}
