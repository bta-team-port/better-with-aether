package teamport.aether.mixin.dimension.bump_to_overworld;

import com.mojang.nbt.tags.CompoundTag;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.WorldClient;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.AetherGlobals;
import teamport.aether.world.AetherDimension;
import turniplabs.halplibe.helper.EnvironmentHelper;

import static teamport.aether.world.AetherDimension.OVERWORLD_RETURN_HEIGHT;

@Environment(EnvType.CLIENT)
@Mixin(Player.class)
public abstract class SPBumpToOverworldMixin extends Mob {

    @Shadow
    public int dimension;

    protected SPBumpToOverworldMixin(@NonNull World world) {
        super(world);
    }

    @Inject(method = "tick()V", at = @At("HEAD"))
    private void bumpPlayerToOverworld(CallbackInfo ci) {
        if (dimension == AetherDimension.getAether().id && this.y < world.getWorldType().getMinY(world) - 10 && EnvironmentHelper.isSingleplayerClient()) {
            Minecraft mc = Minecraft.getMinecraft();

            AetherGlobals.LOGGER.info("Sending {} to overworld", getDisplayName());

            CompoundTag passengerNBT = null;
            CompoundTag vehicleNBT = null;

            if (getPassenger() != null) {
                Entity p = getPassenger();
                this.ejectRider();

                NamespaceID passengerId = p.getDispatcherId();
                if (passengerId != null) {
                    passengerNBT = new CompoundTag();
                    passengerNBT.putString("id", passengerId.toString());
                    p.saveWithoutId(passengerNBT);
                }
                p.remove();
            }

            if (isPassenger() && vehicle != null) {
                Entity v = (Entity) vehicle;
                this.ejectRider();

                NamespaceID vehicleId = v.getDispatcherId();
                if (vehicleId != null) {
                    vehicleNBT = new CompoundTag();
                    vehicleNBT.putString("id", vehicleId.toString());
                    v.saveWithoutId(vehicleNBT);
                }
            }

            mc.currentWorld.setEntityDead(this);
            mc.thePlayer.removed = false;

            float scale = Dimension.getCoordScale(AetherDimension.getAether(), Dimension.OVERWORLD);
            x *= scale;
            z *= scale;
            moveTo(x, OVERWORLD_RETURN_HEIGHT, z, yRot, xRot);

            if (isAlive()) {
                mc.currentWorld.updateEntityWithOptionalForce(this, false);
            }

            WorldClient newWorld = new WorldClient(mc.currentWorld, Dimension.OVERWORLD);
            mc.changeWorld(newWorld, "Leaving " + AetherDimension.getAether().getTranslatedName(), (Player) (Object) this);

            world = newWorld;
            dimension = Dimension.OVERWORLD.id;
            if (isAlive()) {
                mc.currentWorld.updateEntityWithOptionalForce(this, false);
            }

            if (passengerNBT != null) {
                Entity p = EntityDispatcher.getInstance().createEntityFromNBT(passengerNBT, mc.currentWorld);
                if (p != null) {
                    p.moveTo(x, y, z, yRot, xRot);
                    mc.currentWorld.entityJoinedWorld(p);

                    p.startRiding(this);
                }
            }

            if (vehicleNBT != null) {
                Entity v = EntityDispatcher.getInstance().createEntityFromNBT(vehicleNBT, mc.currentWorld);
                if (v != null) {
                    v.moveTo(x, y, z, yRot, xRot);
                    mc.currentWorld.entityJoinedWorld(v);

                    this.startRiding(v);
                }
            }

        }
    }
}
