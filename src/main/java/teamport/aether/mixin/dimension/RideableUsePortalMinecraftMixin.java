package teamport.aether.mixin.dimension;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.PlayerLocal;
import net.minecraft.client.world.WorldClient;
import net.minecraft.core.util.helper.DyeColor;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.entity.animal.MobAetherAnimalRideable;

@Environment(EnvType.CLIENT)
@Mixin(Minecraft.class)
public abstract class RideableUsePortalMinecraftMixin {

    @Shadow
    public PlayerLocal thePlayer;
    @Shadow
    @Nullable
    public WorldClient currentWorld;
    @Unique
    private MobAetherAnimalRideable aetherMount;

    @Inject(method = "usePortal", at = @At("HEAD"))
    private void captureAndDespawnAetherMount(int dim, DyeColor portalColor, CallbackInfo ci) {
        if (this.thePlayer != null && this.thePlayer.vehicle instanceof MobAetherAnimalRideable mount) {
            this.aetherMount = mount;
            if (this.currentWorld != null) {
                this.currentWorld.setEntityDead(this.aetherMount);
            }
        } else {
            this.aetherMount = null;
        }
    }

    @Inject(method = "usePortal", at = @At("TAIL"))
    private void respawnAetherMountInNewWorld(int dim, DyeColor portalColor, CallbackInfo ci) {
        if (this.aetherMount != null) {
            if (this.thePlayer != null && this.thePlayer.isAlive() && this.currentWorld != null) {
                this.thePlayer.clearPendingVehicleTag();
                this.aetherMount.removed = false;
                this.aetherMount.world = this.currentWorld;
                this.aetherMount.moveTo(this.thePlayer.x, this.thePlayer.y, this.thePlayer.z, this.aetherMount.yRot, this.aetherMount.xRot);
                this.currentWorld.entityJoinedWorld(this.aetherMount);
                this.thePlayer.startRiding(this.aetherMount);
            }
            this.aetherMount = null;
        }
    }
}
