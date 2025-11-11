package teamport.aether.mixin.armor.wolf;

import net.minecraft.core.entity.animal.MobAnimal;
import net.minecraft.core.entity.animal.MobWolf;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import teamport.aether.items.AetherArmorMaterial;

@Mixin(value = MobWolf.class, remap = false)
public abstract class MobWolfMixinDrowningImmunity extends MobAnimal {


    @Shadow
    public abstract @Nullable ArmorMaterial getArmorMaterial();

    public MobWolfMixinDrowningImmunity(World world) {
        super(world);
    }

    @Override
    public boolean canBreatheUnderwater() {
        if (this.getArmorMaterial() != null) {
            ArmorMaterial material = this.getArmorMaterial();
            if (material.equals(AetherArmorMaterial.NEPTUNE)) {
                return true;
            }
        }
        return super.canBreatheUnderwater();

    }
}
