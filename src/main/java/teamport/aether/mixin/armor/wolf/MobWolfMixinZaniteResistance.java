package teamport.aether.mixin.armor.wolf;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.entity.animal.MobAnimal;
import net.minecraft.core.entity.animal.MobWolf;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.items.AetherArmorMaterial;

import java.util.Random;

@Mixin(value = MobWolf.class, remap = false)
public abstract class MobWolfMixinZaniteResistance extends MobAnimal{

    @Shadow public abstract int getMaxHealth();

    public MobWolfMixinZaniteResistance(World world) {
        super(world);
    }

    // TODO rewritte it to be not an Injection, maybe WrapOperations
    @Inject(method = "damageEntity", at = @At("HEAD"), cancellable = true)
    public void damageEntity(int damage, DamageType damageType, CallbackInfo ci) {
        MobWolf wolf = (MobWolf) (Object) this;
        if (wolf.getArmorMaterial() == null || !wolf.getArmorMaterial().equals(AetherArmorMaterial.ZANITE)) {
            super.damageEntity(damage, damageType);
            return;
        }
        float healthPercentage  = (float) this.getHealth() /this.getMaxHealth();
        float baseProtection = wolf.getArmorMaterial().getProtection(damageType);
        float endProtection = ArmorMaterial.GOLD.getProtection(damageType);
        float protection = 1.0F - ((baseProtection * healthPercentage) + (endProtection * (1 - healthPercentage)));
        protection = Math.max(protection, 0.01F);
        double d = (float) damage * protection;
        Random random = this.random;
        int newDamage = (int) ((double) random.nextFloat() > (double) 0.5F ? Math.floor(d) : Math.ceil(d));
        super.damageEntity(newDamage, damageType);
        ci.cancel();
    }


    @WrapOperation(method = "damageEntity", at = )
}
