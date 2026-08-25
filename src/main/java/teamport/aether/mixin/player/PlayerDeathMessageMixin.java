package teamport.aether.mixin.player;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.monster.MobSlime;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.world.pos.TilePos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sunsetsatellite.catalyst.effects.api.effect.EffectContainer;
import sunsetsatellite.catalyst.effects.api.effect.IHasEffects;
import teamport.aether.ducks.IContainerInventoryAether;
import teamport.aether.effect.AetherEffects;
import teamport.aether.effect.DeathCauseEffects;
import teamport.aether.entity.DeathCauseEnvironment;
import teamport.aether.entity.boss.DeathCauseBoss;
import teamport.aether.entity.boss.EnemyBoss;
import teamport.aether.entity.monster.mimic.DeathCauseMimic;
import teamport.aether.entity.monster.mimic.MobMimic;
import teamport.aether.entity.monster.swet.DeathCauseKilledSecondary;
import teamport.aether.entity.monster.swet.MobSwet;
import teamport.aether.entity.monster.swet.MobSwetGold;
import teamport.aether.item.accessory.pendant.ItemIcePendant;
import teamport.aether.mixin.entity.MobMessageMixin;
import turniplabs.halplibe.util.deathcause.DeathCause;
import turniplabs.halplibe.util.deathcause.vanilla.DeathCauseKilledBy;
import turniplabs.halplibe.util.deathcause.vanilla.DeathCauseProjectile;

import static teamport.aether.item.accessory.SlotAccessory.*;

@Mixin(Player.class)
public abstract class PlayerDeathMessageMixin extends MobMessageMixin {


    @Shadow
    @Final
    @NotNull
    public ContainerInventory inventory;

    @Inject(method = "getDeathMessageKey", at = @At("HEAD"))
    private void getAetherDeathCause(Entity entityKilledBy, CallbackInfoReturnable<String> cir) {
        Player victim = (Player) (Object) this;
        DeathCause deathCause = this.resolvePlayerDeathCause(entityKilledBy);
        this.prevVehicle = null;
        if(deathCause != null){
            deathCause.bind(victim);
        }

    }

    @Unique
    private @Nullable DeathCause resolvePlayerDeathCause(Entity entityKilledBy) {
        Player victim = (Player) (Object) this;
        EffectContainer<?> victimsEffects = ((IHasEffects<?>) victim).getContainer();
        if (entityKilledBy instanceof Mob mob) {
            if (victimsEffects.hasEffect(AetherEffects.poisonEffect) && this.prevVehicle != null) {
                return new DeathCauseEffects(victim, AetherEffects.poisonEffect).setSecondary("driving");
            }
            if (entityKilledBy instanceof EnemyBoss enemyBoss) {
                return new DeathCauseBoss(victim, mob, enemyBoss);
            }
            if (entityKilledBy instanceof MobSlime
                || entityKilledBy instanceof MobSwet
                || entityKilledBy instanceof MobSwetGold
            ) {
                DeathCauseKilledSecondary deathCauseSwet = new DeathCauseKilledSecondary(victim, entityKilledBy);
                if (victimsEffects.hasEffect(AetherEffects.swetty)) {
                    return deathCauseSwet.setSecondary("friendly");
                }
                return deathCauseSwet;
            }
            if(entityKilledBy instanceof MobMimic){
                return new DeathCauseMimic(victim, mob);
            }
            return new DeathCauseKilledBy(victim, mob);
        }
        if (entityKilledBy instanceof Projectile projectile) {
            return new DeathCauseProjectile(victim, projectile);
        }
        if (victimsEffects.hasEffect(AetherEffects.poisonEffect)) {
            DeathCauseEffects deathCausePoison = new DeathCauseEffects(victim, AetherEffects.poisonEffect);
            if (this.prevVehicle != null) {
                return deathCausePoison.setSecondary("driving");
            }
            return deathCausePoison;
        }
        if(victim.fallDistance > 0){
            ItemStack[] accessories = ((IContainerInventoryAether) victim.inventory).aether$getAccessoryInventory();
            ItemStack pendant1 = accessories[TRINKET_1_SLOT - GLOVES_SLOT];
            ItemStack pendant2 = accessories[TRINKET_2_SLOT - GLOVES_SLOT];
            if (   (pendant1 == null || !(pendant1.getItem() instanceof ItemIcePendant))
                && (pendant2 == null || !(pendant2.getItem() instanceof ItemIcePendant))
            ) {
                TilePos tilePos = new TilePos(victim);
                Block<?> block = victim.world.getBlockType(tilePos);
                if (block == Blocks.OBSIDIAN || block == Blocks.ICE) {
                    return new DeathCauseEnvironment(victim, "ice_pendant");
                }
            }
        }
        return null;
    }

}
