package teamport.aether.mixin.dimension.bump_to_overworld;

import com.mojang.nbt.tags.CompoundTag;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.net.packet.PacketSetRiding;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.PlayerServer;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.AetherGlobals;
import teamport.aether.world.AetherDimension;

import static teamport.aether.world.AetherDimension.OVERWORLD_RETURN_HEIGHT;

@Environment(EnvType.SERVER)
@Mixin(PlayerServer.class)
public abstract class MPBumpToOverworldMixin extends Player {
    protected MPBumpToOverworldMixin(World world) {
        super(world);
    }

    @Shadow
    @NonNull
    @SuppressWarnings("java:S1161")
    public abstract String getDisplayName();

    @Inject(method = "onUpdateEntity()V", at = @At("HEAD"))
    private void bumpPlayerToOverworld(CallbackInfo ci) {
        if (dimension == AetherDimension.getAether().id && this.y < this.world.getWorldType().getMinY(world) - 10) {
            AetherGlobals.LOGGER.debug("Sending {} to overworld", getDisplayName());
            MinecraftServer server = MinecraftServer.getInstance();

            CompoundTag passengerNBT = null;
            CompoundTag vehicleNBT = null;

            if (getPassenger() != null) {
                Entity p = getPassenger();
                this.ejectRider();

                passengerNBT = new CompoundTag();

                p.save(passengerNBT);
                p.remove();
            }

            if (isPassenger() && vehicle != null) {
                vehicleNBT = new CompoundTag();
                ((Entity) vehicle).save(vehicleNBT);

                vehicle.ejectRider();
            }

            moveTo(x, OVERWORLD_RETURN_HEIGHT, z, yRot, xRot);

            PlayerServer player = (PlayerServer) (Object) this;
            World targetWorld = server.getDimensionWorld(Dimension.OVERWORLD.id);

            server.playerList.sendPlayerToOtherDimension(player, Dimension.OVERWORLD.id, DyeColor.BLUE, false);

            if (passengerNBT != null) {
                Entity p = EntityDispatcher.getInstance().createEntityFromNBT(passengerNBT, targetWorld);
                p.load(passengerNBT);
                p.moveTo(x, y, z, 0f, 0f);
                targetWorld.entityJoinedWorld(p);
                // start riding only sends the packet if it's a player who started riding something
                // so if something attempts to ride a player: (lol) it doesn't notify the vehicle(player)
                p.startRiding(this);
                player.playerNetServerHandler.sendPacket(new PacketSetRiding(p, player));
            }
            if (vehicleNBT != null) {
                Entity v = EntityDispatcher.getInstance().createEntityFromNBT(vehicleNBT, targetWorld);
                v.load(vehicleNBT);
                v.moveTo(x, y, z, 0f, 0f);
                targetWorld.entityJoinedWorld(v);
                this.startRiding(v);
            }
        }
    }
}
