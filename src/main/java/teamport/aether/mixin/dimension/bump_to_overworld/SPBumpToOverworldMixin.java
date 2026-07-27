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

@Environment(EnvType.CLIENT)
@Mixin(value = Player.class)
public abstract class SPBumpToOverworldMixin extends Mob {
    protected SPBumpToOverworldMixin(@Nullable World world) {
        super(world);
    }
    @Shadow
    public int dimension;
    @Inject(method = "tick()V", at = @At("HEAD"))
    private void bumpPlayerToOverworld(CallbackInfo ci) {
        if (world != null && dimension == AetherDimension.getAether().id && this.y < world.getWorldType().getMinY(world) - 10) {
            if (EnvironmentHelper.isSinglePlayer()) {
                Minecraft mc = Minecraft.getMinecraft();

                AetherMod.LOGGER.debug("Sending {} to overworld", getDisplayName());

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
                    p.load(passengerNBT);
                    p.moveTo(x, y, z, 0f, 0f);
                    mc.currentWorld.entityJoinedWorld(p);

                    p.startRiding(this);
                }

                if (vehicleNBT != null) {
                    Entity v = EntityDispatcher.getInstance().createEntityFromNBT(vehicleNBT, mc.currentWorld);
                    v.load(vehicleNBT);
                    v.moveTo(x, y, z, 0f, 0f);
                    mc.currentWorld.entityJoinedWorld(v);

                    this.startRiding(v);
                }
            }

        }
    }
}
