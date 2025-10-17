package teamport.aether.mixin.dimension.bumpToOverworld;

import net.minecraft.core.Global;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.world.AetherDimension;

@Mixin(value = WorldServer.class, remap = false)
public class MPWorldMixin extends World {

    @Unique
    public final int COOLDOWN_MAX = Global.TICKS_PER_SECOND;

    @Unique
    public int cooldown = COOLDOWN_MAX;

    @Inject(method = "tick", at = @At("RETURN"))
    public void tick(CallbackInfo ci) {
        cooldown--;

        if (cooldown < 0 && dimension.id == Dimension.OVERWORLD.id) {
            cooldown = COOLDOWN_MAX;

            for (
                Player player : MinecraftServer.getInstance().playerList.playerEntities) {
                AetherDimension.loadEntitiesNearPlayer(player);
            }
        }
    }

}