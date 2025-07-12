package teamport.aether.mixin.armor.player.phoenix.obsidian;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.api.ContainerHelper;
import teamport.aether.items.AetherArmorMaterial;

@Mixin(value = Player.class, remap = false)
public class PlayerMixinExtraFallDamage extends Mob {

    @Shadow
    public ContainerInventory inventory;

    public PlayerMixinExtraFallDamage(@Nullable World world) {
        super(world);
    }

    @Inject(method = "causeFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Mob;causeFallDamage(F)V"), cancellable = true)
    public void aether$causeFallDamage(float distance, CallbackInfo ci) {
        float totalProtectionOfMaterial = ContainerHelper.getTotalEquippedArmorProtection(inventory, AetherArmorMaterial.OBSIDIAN);
        super.causeFallDamage((1 + totalProtectionOfMaterial) * distance);
    }
}
