package teamport.aether.mixin.entity;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.accessory.api.VariableHealthPlayer;
import teamport.aether.items.AetherArmorMaterial;
import teamport.aether.mixin.accessors.EntityAccessor;

@Mixin(value = Player.class, remap = false)
public abstract class PlayerMixin extends Mob implements VariableHealthPlayer {

    public PlayerMixin(@Nullable World world) {
        super(world);
    }

    //########################  Phoenix/Gravitite immunities  ########################

    // TODO fire damage still causes fire to be rendered
    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    public void aether$isImmuneToDamageType(Entity attacker, int damage, DamageType type, CallbackInfoReturnable<Boolean> cir) {
        if (type == null) return;
        Player player = (Player) (Object) this;
        if (type.equals(DamageType.FIRE) && aether$countArmorPiecesOfMaterial(AetherArmorMaterial.phoenix) == 4) {
            float take_damage = ((EntityAccessor)player).getRandom().nextFloat() > (double) 0.05F ? 0 : 4;
            // armor takes damage
            player.inventory.damageArmor((int) Math.ceil((double) take_damage / (double) 4.0F));
            cir.setReturnValue(false);
            return;
        }
        if (type.equals(DamageType.FALL) && aether$countArmorPiecesOfMaterial(AetherArmorMaterial.gravitite) == 4) {
            // armor takes damage
            player.inventory.damageArmor((int) Math.ceil((double) damage / (double) 4.0F));
            cir.setReturnValue(false);
        }
    }

    @Unique
    private int aether$countArmorPiecesOfMaterial(ArmorMaterial material) {
        int count = 0;
        Player player = (Player) (Object) this;
        for (int i = 0; i < player.inventory.armorInventory.length; ++i) {
            ItemStack itemStack = player.inventory.armorInventory[i];
            if (itemStack == null || !(itemStack.getItem() instanceof IArmorItem)) {
                continue;
            }
            IArmorItem armor = (IArmorItem) itemStack.getItem();
            if (armor.getArmorPiece() != i) {
                continue;
            }
            ArmorMaterial armorMaterial = armor.getArmorMaterial();
            if (armorMaterial != null && !armorMaterial.equals(material)) {
                continue;
            }
            count++;
        }
        return count;
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
