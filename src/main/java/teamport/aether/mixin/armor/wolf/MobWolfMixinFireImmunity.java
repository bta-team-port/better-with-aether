package teamport.aether.mixin.armor.wolf;

import net.minecraft.core.entity.EntityLightning;
import net.minecraft.core.entity.animal.MobAnimal;
import net.minecraft.core.entity.animal.MobWolf;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.particle.ParticalHelper;
import teamport.aether.items.AetherArmorMaterial;

@Mixin(value = MobWolf.class, remap = false)
public abstract class MobWolfMixinFireImmunity extends MobAnimal {

    public MobWolfMixinFireImmunity(World world) {
        super(world);
    }

    @Inject(method = "lavaHurt", at = @At("HEAD"), cancellable = true)
    public void aether$lavaImmunity(CallbackInfo ci) {
        if(isImmuneToFire()) {
            if(world == null) return;
            ParticalHelper.spawnFlameParticles(world,  x, y, z, bbHeight, bbWidth);
            ci.cancel();
        }
    }

    @Inject(method = "fireHurt", at = @At("HEAD"), cancellable = true)
    public void aether$fireImmunity(CallbackInfo ci) {
        if (isImmuneToFire()) {
            if(world == null) return;
            ParticalHelper.spawnFlameParticles(world,  x, y, z, bbHeight, bbWidth);
            ci.cancel();
        }
    }

    @Override
    public void thunderHit(EntityLightning bolt){
        if(isImmuneToFire()) {
            if(world == null) return;
            ParticalHelper.spawnFlameParticles(world,  x, y, z, bbHeight, bbWidth);
            return;
        }
        super.thunderHit(bolt);
    }

    @Override
    public void burn(int damage) {
        if (isImmuneToFire()) {
            if(world == null) return;
            ParticalHelper.spawnFlameParticles(world, x, y, z, bbHeight, bbWidth);
            return;
        }
        super.burn(damage);
    }

    @Unique
    public boolean isImmuneToFire() {
        ArmorMaterial armorMaterial = ((MobWolf)(Object)this).getArmorMaterial();
        if(armorMaterial == null){
            return false;
        }
        return     armorMaterial.equals(AetherArmorMaterial.PHOENIX)
                || armorMaterial.equals(AetherArmorMaterial.OBSIDIAN);
    }

}
