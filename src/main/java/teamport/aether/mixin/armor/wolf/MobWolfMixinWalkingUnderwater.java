package teamport.aether.mixin.armor.wolf;

import net.minecraft.core.entity.animal.MobAnimal;
import net.minecraft.core.entity.animal.MobWolf;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = MobWolf.class, remap = false)
public abstract class MobWolfMixinWalkingUnderwater extends MobAnimal {

    @Shadow
    @Nullable
    public abstract ArmorMaterial getArmorMaterial();

    public MobWolfMixinWalkingUnderwater(World world) {
        super(world);
    }


    /// Goal was is to make the wolf walk underwater however this does not seem enough, as the wolf wont path while its underwater
//    @Override
//    public boolean isInWater() {
//        if(this.getArmorMaterial() != null){
//            ArmorMaterial material = this.getArmorMaterial();
//            if(material.equals(AetherArmorMaterial.NEPTUNE)){
//                return false;
//            }
//        }
//        return this.wasInWater;
//    }


}
