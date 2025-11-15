package teamport.aether.mixin.dimension.bump_to_overworld;

import net.minecraft.core.Global;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import net.minecraft.server.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.world.AetherDimension;

@Mixin(value = WorldServer.class, remap = false)
public abstract class MPWorldMixin extends World {
    @Unique
    private int cooldown = Global.TICKS_PER_SECOND;
    @Inject(method = "tick", at = @At("RETURN"))
    private void tick(CallbackInfo ci) {
        cooldown--;
        if (cooldown < 0 && dimension.id == Dimension.OVERWORLD.id) {
            cooldown = Global.TICKS_PER_SECOND / 2 + rand.nextInt(Global.TICKS_PER_SECOND / 2);
            for (Player player : players) {
                AetherDimension.loadEntitiesNearPlayer(player, this);
            }
        }
    }
}
