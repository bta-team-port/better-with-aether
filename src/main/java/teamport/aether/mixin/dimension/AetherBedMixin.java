package teamport.aether.mixin.dimension;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.achievement.stat.Stat;
import net.minecraft.core.block.BlockLogicBed;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.Explosion;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.world.AetherDimension;

@Mixin(value = BlockLogicBed.class)
public abstract class AetherBedMixin {
    @WrapOperation(method = "onInteracted(Lnet/minecraft/core/world/World;Lnet/minecraft/core/world/pos/TilePosc;Lnet/minecraft/core/entity/player/Player;Lnet/minecraft/core/util/helper/Side;DD)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/player/Player;addStat(Lnet/minecraft/core/achievement/stat/Stat;I)V"))
    private void onInteractedAddStat(Player instance, Stat statbase, int i, Operation<Void> original, World world, TilePosc pos, Player player, Side side, double xPlaced, double yPlaced) {
        if (world.dimension.id != AetherDimension.getAether().id) original.call(instance, statbase, i);
    }
    @WrapOperation(method = "onInteracted(Lnet/minecraft/core/world/World;Lnet/minecraft/core/world/pos/TilePosc;Lnet/minecraft/core/entity/player/Player;Lnet/minecraft/core/util/helper/Side;DD)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;createExplosion(Lnet/minecraft/core/entity/Entity;DDDFZZ)Lnet/minecraft/core/world/Explosion;"))
    private Explosion onInteractedCreateExplosion(World instance, Entity entity, double x, double y, double z, float explosionSize, boolean flaming, boolean isCannonBall, Operation<Explosion> original) {
        if (instance.dimension.id != AetherDimension.getAether().id) return original.call(instance, entity, x, y, z, explosionSize, flaming, isCannonBall);
        return original.call(instance, entity, x, y, z, explosionSize, false, isCannonBall);
    }
}
