package teamport.aether.mixin.entity.player;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.player.inventory.container.ContainerInventory;
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

@Mixin(value = Player.class, remap = false)
public abstract class GravititeArmorImmunity extends Mob {

    @Shadow
    public ContainerInventory inventory;

    public GravititeArmorImmunity(@Nullable World world) {
        super(world);
    }

    @Inject(method = "causeFallDamage", at= @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Mob;causeFallDamage(F)V"), cancellable = true)
    public void aether$causeFallDamage(float distance, CallbackInfo ci) {
        if(ContainerHelper.countArmorPiecesOfMaterial(this.inventory, AetherArmorMaterial.GRAVITITE) >= 4){
            int damage = (int)Math.ceil(distance - 13.0F);
            if(damage  > 0) aether$damageArmourGravitite(damage);
            ci.cancel();
        }
    }

    @Unique
    private void aether$damageArmourGravitite(int damage) {
        ((Player) (Object) this).inventory.damageArmor((int) Math.ceil((double) damage / (double) 4.0F));
    }
}
