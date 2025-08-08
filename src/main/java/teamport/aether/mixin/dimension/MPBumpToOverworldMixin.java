package teamport.aether.mixin.dimension;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.PlayerServer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.AetherMod;
import teamport.aether.world.AetherDimension;

import static teamport.aether.world.AetherDimension.OVERWORLD_RETURN_HEIGHT;

@Mixin(value = PlayerServer.class, remap = false)
public abstract class MPBumpToOverworldMixin extends Player {
    @Shadow public abstract @NotNull String getDisplayName();

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

            AetherMod.LOGGER.info(String.format("Sending %s to overworld", getDisplayName()));

            MinecraftServer server = MinecraftServer.getInstance();

            CompoundTag passengerNBT = null;
            if (getPassenger() != null) {
                Entity p = getPassenger();
                this.ejectRider();

                passengerNBT = new CompoundTag();

                p.save(passengerNBT);
                p.remove();
            }

            float scale = Dimension.getCoordScale(AetherDimension.AETHER, Dimension.OVERWORLD);
            moveTo(x * scale, OVERWORLD_RETURN_HEIGHT, z * scale, yRot, xRot);
            server.playerList.sendPlayerToOtherDimension((PlayerServer) (Object) this, Dimension.OVERWORLD.id, DyeColor.BLUE, false);

            if (passengerNBT != null) {
                Entity p = EntityDispatcher.createEntityFromNBT(passengerNBT, world);
                p.moveTo(x, y, z, 0f, 0f);
                world.entityJoinedWorld(p);

                p.startRiding(this);
            }
        }
    }
}
