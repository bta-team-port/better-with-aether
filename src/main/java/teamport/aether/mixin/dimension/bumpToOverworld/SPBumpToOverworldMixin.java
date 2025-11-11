package teamport.aether.mixin.dimension.bumpToOverworld;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.WorldClient;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.AetherMod;
import teamport.aether.world.AetherDimension;
import turniplabs.halplibe.helper.EnvironmentHelper;

import static teamport.aether.world.AetherDimension.OVERWORLD_RETURN_HEIGHT;

@Mixin(value = Player.class, remap = false)
public abstract class SPBumpToOverworldMixin extends Mob {

    @Shadow
    public int dimension;

    public SPBumpToOverworldMixin(@Nullable World world) {
        super(world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {

        assert world != null;
        if (dimension == AetherDimension.AETHER.id && this.y < world.worldType.getMinY() - 10) {
            if (EnvironmentHelper.isSinglePlayer()) {
                Minecraft mc = Minecraft.getMinecraft();

                AetherMod.LOGGER.info(String.format("Sending %s to overworld", getDisplayName()));

                CompoundTag passengerNBT = null;
                CompoundTag vehicleNBT = null;

                if (getPassenger() != null) {
                    passengerNBT = new CompoundTag();
                    Entity p = getPassenger();
                    this.ejectRider();

                    p.save(passengerNBT);
                    p.remove();
                }

                if (isPassenger() && vehicle != null) {
                    vehicleNBT = new CompoundTag();
                    ((Entity) vehicle).save(vehicleNBT);

                    vehicle.ejectRider();
                }

                mc.currentWorld.setEntityDead(this);
                mc.thePlayer.removed = false;

                float scale = Dimension.getCoordScale(AetherDimension.AETHER, Dimension.OVERWORLD);
                moveTo(x *= scale, OVERWORLD_RETURN_HEIGHT, z *= scale, yRot, xRot);

                if (isAlive()) {
                    mc.currentWorld.updateEntityWithOptionalForce(this, false);
                }

                WorldClient newWorld = new WorldClient(mc.currentWorld, Dimension.OVERWORLD);
                mc.changeWorld(newWorld, "Leaving " + AetherDimension.AETHER.getTranslatedName(), Player.class.cast(this));

                world = newWorld;
                dimension = Dimension.OVERWORLD.id;
                if (isAlive()) {
                    mc.currentWorld.updateEntityWithOptionalForce(this, false);
                }

                if (passengerNBT != null) {
                    Entity p = EntityDispatcher.createEntityFromNBT(passengerNBT, mc.currentWorld);
                    p.load(passengerNBT);
                    p.moveTo(x, y, z, 0f, 0f);
                    mc.currentWorld.entityJoinedWorld(p);

                    p.startRiding(this);
                }

                if (vehicleNBT != null) {
                    Entity v = EntityDispatcher.createEntityFromNBT(vehicleNBT, mc.currentWorld);
                    v.load(vehicleNBT);
                    v.moveTo(x, y, z, 0f, 0f);
                    mc.currentWorld.entityJoinedWorld(v);

                    this.startRiding(v);
                }
            }

        }
    }
}
