package teamport.aether.mixin.player;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.entity.animal.MobAetherAnimalRideable;

@Mixin(Player.class)
public abstract class SaveRidingMobPlayerMixin extends Entity {

    @Shadow
    private @Nullable CompoundTag pendingVehicleTag;

    protected SaveRidingMobPlayerMixin(@NonNull World world) {
        super(world);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void saveAetherVehicle(CompoundTag tag, CallbackInfo ci) {
        if (this.vehicle instanceof MobAetherAnimalRideable rideable) {
            NamespaceID vehicleId = rideable.getDispatcherId();
            if (vehicleId != null) {
                CompoundTag vehicleTag = new CompoundTag();
                vehicleTag.putString("id", vehicleId.toString());
                rideable.saveWithoutId(vehicleTag);
                tag.put("Vehicle", vehicleTag);
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void loadAetherVehicle(CallbackInfo ci) {
        if (this.pendingVehicleTag != null && !this.world.isClientSide) {
            CompoundTag vehicleTag = this.pendingVehicleTag;
            Entity vehicle = EntityDispatcher.getInstance().createEntityFromNBT(vehicleTag, this.world);

            if (vehicle instanceof MobAetherAnimalRideable rideable) {
                this.pendingVehicleTag = null;
                this.world.entityJoinedWorld(rideable);
                this.startRiding(rideable);
            }
        }
    }
}
