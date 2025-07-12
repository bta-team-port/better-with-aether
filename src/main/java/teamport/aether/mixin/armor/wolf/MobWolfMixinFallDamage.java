package teamport.aether.mixin.armor.wolf;

import net.minecraft.core.entity.animal.MobAnimal;
import net.minecraft.core.entity.animal.MobWolf;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.items.AetherArmorMaterial;

@Mixin(value = MobWolf.class, remap = false)
public abstract class MobWolfMixinFallDamage extends MobAnimal {

    public MobWolfMixinFallDamage(World world) {
        super(world);
    }

    @Override
    public void causeFallDamage(float distance) {
        ArmorMaterial material = ((MobWolf) (Object) this).getArmorMaterial();
        if (material == null) {
            super.causeFallDamage(distance);
            return;
        }
        if (material.equals(AetherArmorMaterial.GRAVITITE)) {
            return;
        }
        if(material.equals(AetherArmorMaterial.OBSIDIAN)){
            distance = 1.4f * distance;
        }
        super.causeFallDamage(distance);
    }
}
