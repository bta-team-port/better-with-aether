package teamport.aether.mixin.entity.player;

import net.minecraft.core.entity.EntityLightning;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.api.ContainerHelper;
import teamport.aether.items.AetherArmorMaterial;
import teamport.aether.mixin.accessors.EntityAccessor;

import java.util.Random;

@Mixin(value = Player.class, remap = false)
public class ArmorFireImmunity extends Mob {

    @Shadow
    public ContainerInventory inventory;

    public ArmorFireImmunity(@Nullable World world) {
        super(world);
    }

    @Inject(method = "lavaHurt", at = @At("HEAD"), cancellable = true)
    public void aether$lavaImmunity(CallbackInfo ci){
        if (fireResistanceCount() >= 4) {
            // lava damage is 4 points
            aether$damageArmourWithEffect(4);
            ci.cancel();
        }
    }

    @Inject(method = "fireHurt", at = @At("HEAD"), cancellable = true)
    public void aether$fireImmunity(CallbackInfo ci){
        if (fireResistanceCount() >= 4) {
            // fire damage is 1 points
            aether$damageArmourWithEffect(1);
            ci.cancel();
        }
    }

    @Override
    public void burn(int damage) {
        if (fireResistanceCount() >= 4) {
            // burn damage is 4 points
            aether$damageArmourWithEffect(1);
            return;
        }
        super.burn(damage);
    }

    @Override
    public void thunderHit(EntityLightning bolt) {
        if (fireResistanceCount() >= 4) {
            // we only negate the burn but the player takes the lightning damage
            this.hurt(null, 5, DamageType.FIRE);
            aether$damageArmourWithEffect(5);
            return;
        }
        super.thunderHit(bolt);
    }

    @Unique
    private int  fireResistanceCount(){
        return ContainerHelper.countArmorPiecesOfMaterial(inventory, AetherArmorMaterial.PHOENIX)
                + ContainerHelper.countArmorPiecesOfMaterial(inventory, AetherArmorMaterial.OBSIDIAN);
    }

    @Unique
    private void aether$damageArmourWithEffect(int damage) {
        Player player = (Player) (Object) this;
        if(((EntityAccessor)player).getRandom().nextFloat() < (double) 0.05F){
            player.inventory.damageArmor(damage);
        }
        aether$spawnFlameParticles();
    }

    @Unique
    private void aether$spawnFlameParticles() {
        Random random = ((EntityAccessor)this).getRandom();
        double dx = random.nextGaussian() * 0.02;
        double dy = random.nextGaussian() * 0.02;
        double dz = random.nextGaussian() * 0.02;
        // TODO figure out what data is
        world.spawnParticle(
                "flame",
                x + (double) (random.nextFloat() * bbWidth * 2.0F) - (double) bbWidth,
                y + (double) (random.nextFloat() * bbHeight) - (double) bbHeight,
                z + (double) (random.nextFloat() * bbWidth * 2.0F) - (double) bbWidth,
                dx, dy, dz, 2
        );
    }
}
