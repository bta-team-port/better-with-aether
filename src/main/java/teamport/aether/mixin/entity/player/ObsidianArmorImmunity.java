package teamport.aether.mixin.entity.player;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.util.helper.DamageType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.accessory.api.ContainerHelper;
import teamport.aether.items.AetherArmorMaterial;

@Mixin(value = Player.class, remap = false)
public class ObsidianArmorImmunity {

    @Shadow public ContainerInventory inventory;

    @Inject(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Mob;hurt(Lnet/minecraft/core/entity/Entity;ILnet/minecraft/core/util/helper/DamageType;)Z"), cancellable = true)
    public void negateDamage(Entity attacker, int damage, DamageType type, CallbackInfoReturnable<Boolean> cir){
        if(   type == null
           || !type.equals(DamageType.BLAST) 
           || ContainerHelper.countArmorPiecesOfMaterial(this.inventory, AetherArmorMaterial.OBSIDIAN) < 4
        ){
            return;
        }
        this.inventory.damageArmor((int) Math.ceil((double) damage / (double) 4.0F));
        cir.cancel();
    }

}
