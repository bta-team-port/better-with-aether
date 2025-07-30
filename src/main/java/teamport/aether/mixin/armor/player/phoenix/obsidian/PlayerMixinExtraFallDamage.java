package teamport.aether.mixin.armor.player.phoenix.obsidian;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
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
import teamport.aether.helper.ContainerHelper;
import teamport.aether.items.AetherArmorMaterial;

@Mixin(value = Player.class, remap = false)
public class PlayerMixinExtraFallDamage extends Mob {

    @Shadow
    public ContainerInventory inventory;

    public PlayerMixinExtraFallDamage(@Nullable World world) {
        super(world);
    }

    @WrapOperation(method = "causeFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Mob;causeFallDamage(F)V"))
    public void aether$causeFallDamage(Player instance, float distance, Operation<Void> original) {
        float totalProtectionOfMaterial = ContainerHelper.getTotalEquippedArmorProtection(inventory, AetherArmorMaterial.OBSIDIAN);
        original.call(instance, (1 + totalProtectionOfMaterial) * distance);
    }
}
