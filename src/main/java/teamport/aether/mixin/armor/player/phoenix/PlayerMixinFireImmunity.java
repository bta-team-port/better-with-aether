package teamport.aether.mixin.armor.player.phoenix;

import net.minecraft.core.entity.EntityLightning;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.helper.ContainerHelper;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.items.AetherArmorMaterial;
import teamport.aether.mixin.accessors.EntityAccessor;

@Mixin(value = Player.class, remap = false)
public class PlayerMixinFireImmunity extends Mob {

    @Shadow
    public ContainerInventory inventory;

    public PlayerMixinFireImmunity(@Nullable World world) {
        super(world);
    }


    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if (this.isInLava() || this.isInWater()) {
            return;
        }
        if (fireResistanceCount() >= 3 && random.nextInt(6) == 0) {
            ParticleMaker.spawnFlameParticles(world, x, y, z, bbHeight, bbWidth);
        }

        if (fireResistanceCount() >= 5 && random.nextInt(3) == 0) {
            ParticleMaker.spawnFlameParticles(world, x, y, z, bbHeight, bbWidth);
        }
    }

    @Inject(method = "lavaHurt", at = @At("HEAD"), cancellable = true)
    public void aether$lavaImmunity(CallbackInfo ci) {
        if (fireResistanceCount() >= 5) {
            // lava damage is 4 points
            aether$damageArmourWithEffect(4);
            ci.cancel();
        }
    }

    @Inject(method = "fireHurt", at = @At("HEAD"), cancellable = true)
    public void aether$fireImmunity(CallbackInfo ci) {
        if (fireResistanceCount() >= 3) {
            // fire damage is 1 points
            aether$damageArmourWithEffect(1);
            ci.cancel();
        }
    }

    @Override
    public void burn(int damage) {
        if (fireResistanceCount() >= 3) {
            // burn damage is 1 points
            aether$damageArmourWithEffect(1);
            return;
        }
        super.burn(damage);
    }

    @Override
    public void thunderHit(EntityLightning bolt) {
        if (fireResistanceCount() >= 5) {
            // we only negate the burn but the player takes the lightning damage
            this.hurt(null, 5, DamageType.FIRE);
            aether$damageArmourWithEffect(5);
            return;
        }
        super.thunderHit(bolt);
    }

    @Unique
    public int fireResistanceCount() {
        return ContainerHelper.countArmorPiecesOfMaterial(inventory, AetherArmorMaterial.PHOENIX);
    }

    @Unique
    public void aether$damageArmourWithEffect(int damage) {
        Player player = (Player) (Object) this;
        if (((EntityAccessor) player).getRandom().nextFloat() < (double) 0.05F) {
            player.inventory.damageArmor(damage);
            if (random.nextInt(6) == 0) {
                this.world.playSoundAtEntity(null, this, "random.fizz", 0.5F, 0.8F / (this.random.nextFloat() * 0.2F + 0.9F));
            }
        }
        ParticleMaker.spawnSmokeParticles(world, x, y, z, bbHeight, bbWidth);
    }
}
