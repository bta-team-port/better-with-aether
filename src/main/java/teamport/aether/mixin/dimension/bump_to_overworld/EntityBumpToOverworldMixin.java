package teamport.aether.mixin.dimension.bump_to_overworld;

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

@Mixin(Entity.class)
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
    @Nullable
    public abstract Entity getPassenger();
    @Shadow
    @Nullable
    public Entity passenger;
    @Inject(method = "tick", at = @At("HEAD"))
    private void fallToOverWorld(CallbackInfo ci) {
        if (world == null || EnvironmentHelper.isMultiplayerClient()) return;
        Dimension dimension = world.dimension;
        if (dimension.id == AetherDimension.getAether().id && y < world.getWorldType().getMinY(world) - 10) {
            if (getPassenger() != null) {
                if (getPassenger() instanceof Player) return;
                ejectRider();
            }
            Entity entity = (Entity) (Object) this;
            if (entity instanceof AetherMobFallingToOverworld) {
                ((AetherMobFallingToOverworld) entity).onLeavingAether();
                if (!((AetherMobFallingToOverworld) entity).canFallToOverworld()) {
                    this.remove();
                    return;
                }
            }
            addEntityToFallen(entity);
        }
    }
}
