package teamport.aether.mixin.entity;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.accessory.api.ContainerHelper;
import teamport.aether.accessory.api.IVariableHealthPlayer;
import teamport.aether.items.AetherArmorMaterial;
import teamport.aether.mixin.accessors.EntityAccessor;

@Mixin(value = Player.class, remap = false)
public abstract class PlayerMixin
        extends Mob
        implements IVariableHealthPlayer{

    @Shadow public ContainerInventory inventory;

    @Shadow public abstract boolean hurt(Entity attacker, int damage, DamageType type);

    @Shadow public abstract void fireHurt();

    public PlayerMixin(@Nullable World world) {
        super(world);
    }

    //###############################  Phoenix Armour  ###############################

    @Inject(method = "lavaHurt", at = @At("HEAD"), cancellable = true)
    public void aether$lavaImmunity(CallbackInfo ci){
        if(ContainerHelper.countArmorPiecesOfMaterial(this.inventory, AetherArmorMaterial.phoenix) == 4){
            aether$damagePhoenixArmourWithEffect(4);
            ci.cancel();
        }
    }

    @Inject(method = "fireHurt", at = @At("HEAD"), cancellable = true)
    public void aether$fireImmunity(CallbackInfo ci){
        if(ContainerHelper.countArmorPiecesOfMaterial(this.inventory, AetherArmorMaterial.phoenix) == 4){
            aether$damagePhoenixArmourWithEffect(1);
            ci.cancel();
        }
    }

    @Override
    public void burn(int damage) {
        if (ContainerHelper.countArmorPiecesOfMaterial(this.inventory, AetherArmorMaterial.phoenix) == 4) {
            aether$damagePhoenixArmourWithEffect(1);
            return;
        }
        super.burn(damage);
    }

    @Override
    public void thunderHit(EntityLightning bolt) {
        if (ContainerHelper.countArmorPiecesOfMaterial(this.inventory, AetherArmorMaterial.phoenix) == 4) {
            // only the burn is negated
            this.hurt((Entity) null, 5, DamageType.FIRE);
            aether$damagePhoenixArmourWithEffect(5);
            return;
        }
        super.thunderHit(bolt);
    }

    @Unique
    private void aether$damagePhoenixArmourWithEffect(int damage) {
        Player player = (Player) (Object) this;
        if(((EntityAccessor)player).getRandom().nextFloat() < (double) 0.2F){
            player.inventory.damageArmor(damage);
        }
        aether$spawnFlameParticles();
    }

    @Unique
    private void aether$spawnFlameParticles() {
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

    //##############################  Gravitite Armour  ##############################

    @Inject(method = "causeFallDamage", at= @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Mob;causeFallDamage(F)V"), cancellable = true)
    public void causeFallDamage(float distance, CallbackInfo ci) {
        if(ContainerHelper.countArmorPiecesOfMaterial(this.inventory, AetherArmorMaterial.gravitite) == 4){
            int damage = (int)Math.ceil(distance - 3.0F);
            if(damage  > 0) aether$damageArmourGravitite(damage);
            ci.cancel();
        }
    }

    private void aether$damageArmourGravitite(int damage) {
        ((Player) (Object) this).inventory.damageArmor((int) Math.ceil((double) damage / (double) 4.0F));
    }

    //###############################  ItemLifeShard  ###############################

    // new field in playerData for extra hp
    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    public void aether$initExtraHealth(CallbackInfo ci) {
        this.entityData.define(31, 0, Integer.class);
    }

    @Inject(method = "getMaxHealth", at = @At("HEAD"), cancellable = true)
    public void aether$getMaxHealth(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(20 + aether$getExtraHealth());
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void aether$writeExtraHealth(CompoundTag tag, CallbackInfo ci) {
        tag.putInt("ExtraHP", aether$getExtraHealth());
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void aether$readExtraHealth(CompoundTag tag, CallbackInfo ci) {
        if (tag.containsKey("ExtraHP")) {
            aether$setExtraHealth(tag.getInteger("ExtraHP"));
        } else {
            aether$setExtraHealth(0);
        }
    }

    public int aether$getExtraHealth() {
        return this.entityData.getInt(31);
    }

    public void aether$setExtraHealth(int extraHP) {
        this.entityData.set(31, Math.min(extraHP, 20));
    }

    public void aether$addExtraHealth(int extraHP) {
        aether$setExtraHealth(aether$getExtraHealth() + extraHP);
    }
}
