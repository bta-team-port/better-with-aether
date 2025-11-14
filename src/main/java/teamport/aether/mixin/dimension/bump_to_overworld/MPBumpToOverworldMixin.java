package teamport.aether.mixin.dimension.bump_to_overworld;

import com.mojang.nbt.tags.CompoundTag;
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
import teamport.aether.AetherMod;
import teamport.aether.world.AetherDimension;

import static teamport.aether.world.AetherDimension.OVERWORLD_RETURN_HEIGHT;

@Mixin(value = PlayerServer.class, remap = false)
public abstract class MPBumpToOverworldMixin extends Player {
    protected MPBumpToOverworldMixin(World world) {
        super(world);
    }
    @SuppressWarnings("java:S1161")
    @Shadow
    @NonNull
    public abstract String getDisplayName();
    @Inject(method = "onUpdateEntity", at = @At("HEAD"))
    public void bumpPlayerToOverworld(CallbackInfo ci) {
        if (this.world != null && dimension == AetherDimension.getAether().id && this.y < this.world.worldType.getMinY() - 10) {
            AetherMod.LOGGER.info("Sending {} to overworld", getDisplayName());
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

            float scale = Dimension.getCoordScale(AetherDimension.getAether(), Dimension.OVERWORLD);
            moveTo(x * scale, OVERWORLD_RETURN_HEIGHT, z * scale, yRot, xRot);

            PlayerServer player = (PlayerServer) (Object) this;
            World targetWorld = server.getDimensionWorld(Dimension.OVERWORLD.id);

            server.playerList.sendPlayerToOtherDimension(player, Dimension.OVERWORLD.id, DyeColor.BLUE, false);

            if (passengerNBT != null) {
                Entity p = EntityDispatcher.createEntityFromNBT(passengerNBT, targetWorld);
                p.load(passengerNBT);
                p.moveTo(x, y, z, 0f, 0f);
                targetWorld.entityJoinedWorld(p);
                // start riding only sends the packet if it's a player who started riding something
                // so if something attempts to ride a player: (lol) it doesn't notify the vehicle(player)
                p.startRiding(this);
                player.playerNetServerHandler.sendPacket(new PacketSetRiding(p, player));
            }
            if (vehicleNBT != null) {
                Entity v = EntityDispatcher.createEntityFromNBT(vehicleNBT, targetWorld);
                v.moveTo(x, y, z, 0f, 0f);
                targetWorld.entityJoinedWorld(v);
                this.startRiding(v);
            }
        }
    }
}
