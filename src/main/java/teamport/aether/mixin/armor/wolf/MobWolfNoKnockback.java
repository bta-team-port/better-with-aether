package teamport.aether.mixin.armor.wolf;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.MobAnimal;
import net.minecraft.core.entity.animal.MobWolf;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.items.AetherArmorMaterial;

@Mixin(value = MobWolf.class, remap = false)
public abstract class MobWolfNoKnockback extends MobAnimal {


    public MobWolfNoKnockback(World world) {
        super(world);
    }

    // prevent any type of knockback
    @Override
    public void fling(double xd, double yd, double zd, float pushTime) {
        ArmorMaterial material = ((MobWolf) (Object) this).getArmorMaterial();
        if (material != null && material.equals(AetherArmorMaterial.OBSIDIAN)) {
            return;
        }
        super.fling(xd, yd, zd, pushTime);
    }

    @Override
    public void knockBack(Entity entity, int damage, double xd, double yd) {
        ArmorMaterial material = ((MobWolf) (Object) this).getArmorMaterial();
        if (material != null && material.equals(AetherArmorMaterial.OBSIDIAN)) {
            return;
        }
        super.knockBack(entity, damage, xd, yd);
    }

}
