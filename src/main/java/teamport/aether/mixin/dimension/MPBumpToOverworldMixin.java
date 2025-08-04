package teamport.aether.mixin.dimension;

import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.net.packet.PacketGameRule;
import net.minecraft.core.net.packet.PacketPlayerGamemode;
import net.minecraft.core.net.packet.PacketRespawn;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.PlayerServer;
import net.minecraft.server.net.handler.PacketHandlerServer;
import net.minecraft.server.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.world.AetherDimension;

@Mixin(value = PlayerServer.class, remap = false)
public abstract class MPBumpToOverworldMixin extends Player {
    public MPBumpToOverworldMixin(World world) {
        super(world);
    }

    @Unique
    public int teleportDelay = 0;

    @Inject(method = "onUpdateEntity", at = @At("HEAD"))
    public void bumpPlayerToOverworld(CallbackInfo ci) {
        teleportDelay--;

        if (teleportDelay < 0 && dimension == AetherDimension.AetherDimensionID && this.y < world.worldType.getMinY() - 10) {
            teleportDelay = 20;

            MinecraftServer server = MinecraftServer.getInstance();

            float scale = Dimension.getCoordScale(AetherDimension.AETHER, Dimension.OVERWORLD);
            moveTo(x * scale, 600, z * scale, yRot, xRot);
            server.playerList.sendPlayerToOtherDimension((PlayerServer) (Object) this, Dimension.OVERWORLD.id, DyeColor.BLUE, false);
        }
    }
}
