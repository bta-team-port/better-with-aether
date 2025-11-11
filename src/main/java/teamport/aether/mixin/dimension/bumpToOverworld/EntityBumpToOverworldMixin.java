package teamport.aether.mixin.dimension.bumpToOverworld;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.entity.AetherMobFallingToOverworld;
import teamport.aether.world.AetherDimension;
import turniplabs.halplibe.helper.EnvironmentHelper;

import static teamport.aether.world.AetherDimension.addEntityToFallen;

@Mixin(value = Entity.class, remap = false)
public abstract class EntityBumpToOverworldMixin {

    @Shadow
    @Nullable
    public World world;

    @Shadow
    public double y;

    @Shadow
    public abstract void remove();

    @Shadow
    public abstract Entity ejectRider();

    @Shadow
    public abstract boolean isPassenger();

    @Shadow
    public abstract @Nullable Entity getPassenger();

    @Shadow
    @Nullable
    public Entity passenger;

    private final Entity thisAs = Entity.class.cast(this);

    @Inject(method = "tick", at = @At("HEAD"))
    public void fallToOverWorld(CallbackInfo ci) {
        if (EnvironmentHelper.isClientWorld()) return;

        Dimension dimension = world.dimension;

        if (dimension.id == AetherDimension.AETHER.id && y < world.worldType.getMinY() - 10) {

            if (getPassenger() != null) {
                if (getPassenger() instanceof Player) return;
                ejectRider();
            }

            if (thisAs instanceof AetherMobFallingToOverworld) {
                ((AetherMobFallingToOverworld) thisAs).onLeavingAether();

                if (!((AetherMobFallingToOverworld) thisAs).canFallToOverworld()) {
                    this.remove();
                    return;
                }
            }

            addEntityToFallen(Entity.class.cast(this));
        }
    }

}
