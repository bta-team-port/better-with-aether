package teamport.aether.mixins.mixin.armor.wolf;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.entity.animal.MobAnimal;
import net.minecraft.core.entity.animal.MobWolf;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.helper.MixinHelper;
import teamport.aether.helper.ParticleMaker;

@Mixin(MobWolf.class)
public abstract class MobWolfMixinFireImmunity extends MobAnimal {
    protected MobWolfMixinFireImmunity(World world) {
        super(world);
    }
    @Definition(id = "fireImmune", field = "Lnet/minecraft/core/entity/animal/MobWolf;fireImmune:Z")
    @Expression("this.fireImmune")
    @ModifyExpressionValue(method = "lavaHurt", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean lavaImmunity(boolean original) {
        if (!MixinHelper.isImmuneToFire((MobWolf) (Object) this)) return original;
        ParticleMaker.spawnSmokeParticles(world, x, y, z, bbHeight, bbWidth);
        return true;
    }
    @Definition(id = "fireImmune", field = "Lnet/minecraft/core/entity/animal/MobWolf;fireImmune:Z")
    @Expression("this.fireImmune")
    @ModifyExpressionValue(method = "fireHurt", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean fireImmunity(boolean original) {
        if (!MixinHelper.isImmuneToFire((MobWolf) (Object) this)) return original;
        ParticleMaker.spawnSmokeParticles(world, x, y, z, bbHeight, bbWidth);
        return true;
    }
}
