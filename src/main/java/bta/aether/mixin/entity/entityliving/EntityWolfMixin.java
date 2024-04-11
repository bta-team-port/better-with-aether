package bta.aether.mixin.entity.entityliving;

import bta.aether.item.AetherArmorMaterial;
import bta.aether.item.AetherItems;
import net.minecraft.core.entity.animal.EntityAnimal;
import net.minecraft.core.entity.animal.EntityWolf;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(value = EntityWolf.class, remap = false)
public class EntityWolfMixin extends EntityAnimal {
    public EntityWolfMixin(World world) {
        super(world);
    }

    @Shadow private static Map<ArmorMaterial, ItemArmor> ARMOR_MATERIALS;

    static {
        ARMOR_MATERIALS.put(AetherArmorMaterial.PHOENIX, (ItemArmor) AetherItems.armorChestplatePhoenix);
    }



}
