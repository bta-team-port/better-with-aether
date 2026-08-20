package teamport.aether.mixins.mixin.armor.wolf;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLightning;
import net.minecraft.core.entity.animal.MobWolf;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import teamport.aether.helper.MixinHelper;
import teamport.aether.helper.ParticleMaker;

@Mixin(Entity.class)
public abstract class MobWolfMixinFireImmunityBurn {
    @Shadow
    @Nullable
    public World world;
    @Shadow
    public double x;
    @Shadow
    public double y;
    @Shadow
    public double z;
    @Shadow
    public float bbHeight;
    @Shadow
    public float bbWidth;
    @WrapMethod(method = "burn(ILnet/minecraft/core/block/Block;)V")
    private void burn(int damage, Block<?> fireSource, Operation<Void> original) {
        if (!((Entity) (Object) this instanceof MobWolf)) {
            original.call(damage, fireSource);
            return;
        }
        if (MixinHelper.isImmuneToFire((MobWolf) (Object) this)) {
            if (world == null) return;
            ParticleMaker.spawnSmokeParticles(world, x, y, z, bbHeight, bbWidth);
            return;
        }
        original.call(damage, fireSource);
    }
    @WrapMethod(method = "thunderHit")
    private void thunderHit(EntityLightning bolt, Operation<Void> original) {
        if (!((Entity) (Object) this instanceof MobWolf)) {
            original.call(bolt);
            return;
        }
        if (MixinHelper.isImmuneToFire((MobWolf) (Object) this)) {
            if (world == null) return;
            ParticleMaker.spawnSmokeParticles(world, x, y, z, bbHeight, bbWidth);
            return;
        }
        original.call(bolt);
    }
}
