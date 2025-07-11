package teamport.aether.mixin.armor.wolf;


import net.minecraft.core.entity.animal.MobAnimal;
import net.minecraft.core.entity.animal.MobWolf;
import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import teamport.aether.items.AetherArmorMaterial;
import teamport.aether.items.AetherItems;

import static net.minecraft.core.entity.animal.MobWolf.ARMOR_MATERIALS;

// TODO missing textures for wolfs with armour
@Mixin(value = MobWolf.class)
public abstract class MobWolfMixinArmor extends MobAnimal {

    @Shadow
    private ItemStack armor;

    @Shadow
    public abstract @Nullable ArmorMaterial getArmorMaterial();

    public MobWolfMixinArmor(World world) {
        super(world);
    }

    // TODO once the mod is done, make the effects of phoenix and gravitite work for dogs too
    static {
        ARMOR_MATERIALS.put(AetherArmorMaterial.PHOENIX, (IArmorItem) AetherItems.ARMOR_CHESTPLATE_PHOENIX);
        ARMOR_MATERIALS.put(AetherArmorMaterial.NEPTUNE, (IArmorItem) AetherItems.ARMOR_CHESTPLATE_NEPTUNE);
        ARMOR_MATERIALS.put(AetherArmorMaterial.OBSIDIAN, (IArmorItem) AetherItems.ARMOR_CHESTPLATE_OBSIDIAN);
        ARMOR_MATERIALS.put(AetherArmorMaterial.GRAVITITE, (IArmorItem) AetherItems.ARMOR_CHESTPLATE_GRAVITITE);
        ARMOR_MATERIALS.put(AetherArmorMaterial.ZANITE, (IArmorItem) AetherItems.ARMOR_CHESTPLATE_ZANITE);
    }


}
