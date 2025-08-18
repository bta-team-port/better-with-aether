package teamport.aether.mixin.dimension;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.PlayerLocal;
import net.minecraft.client.world.WorldClient;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import teamport.aether.AetherMod;
import teamport.aether.world.AetherDimension;
import turniplabs.halplibe.helper.EnvironmentHelper;

import static teamport.aether.world.AetherDimension.OVERWORLD_RETURN_HEIGHT;

@Mixin(value = PlayerLocal.class, remap = false)
public abstract class SPBumpToOverworldMixin extends Player {

    @Shadow protected Minecraft mc;

    @Shadow public abstract void sendMessage(String message);

    @Shadow public abstract void sendChatMessage(String s);

    public SPBumpToOverworldMixin(@Nullable World world) {
        super(world);
    }

    @Unique
    public int teleportDelay = 0;

    @Override
    public void tick() {
        teleportDelay--;

        assert world != null;
        if (teleportDelay < 0 && dimension == AetherDimension.AetherDimensionID && this.y < world.worldType.getMinY() - 10) {
            if (EnvironmentHelper.isSinglePlayer()) {
                teleportDelay = 20;

                AetherMod.LOGGER.info(String.format("Sending %s to overworld", getDisplayName()));

                CompoundTag passengerNBT = null;
                if (getPassenger() != null) {
                    passengerNBT = new CompoundTag();
                    Entity p = getPassenger();
                    this.ejectRider();

                    p.save(passengerNBT);
                    p.remove();
                } else if (isPassenger() && vehicle != null) { vehicle.ejectRider(); }

                mc.currentWorld.setEntityDead(this);
                mc.thePlayer.removed = false;

                float scale = Dimension.getCoordScale(AetherDimension.AETHER, Dimension.OVERWORLD);
                moveTo(x *= scale, OVERWORLD_RETURN_HEIGHT, z *= scale, yRot, xRot);

                if (isAlive()) { mc.currentWorld.updateEntityWithOptionalForce(this, false); }

                WorldClient newWorld = new WorldClient(this.mc.currentWorld, Dimension.OVERWORLD);
                this.mc.changeWorld(newWorld, "Leaving " + AetherDimension.AETHER.getTranslatedName(), this);

                world = newWorld;
                dimension = Dimension.OVERWORLD.id;
                if (isAlive()) { mc.currentWorld.updateEntityWithOptionalForce(this, false); }

                if (passengerNBT != null) {
                    Entity p = EntityDispatcher.createEntityFromNBT(passengerNBT, world);
                    p.moveTo(x, y, z, 0f, 0f);
                    world.entityJoinedWorld(p);

                    p.startRiding(this);
                }
            }

        }
        super.tick();
    }
}
