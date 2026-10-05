package teamport.aether.mixin.achievement;

import net.minecraft.core.achievement.stat.Stat;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.achievements.AetherAchievements;
import teamport.aether.world.AetherDimension;

@Mixin(Player.class)
public abstract class HostileParadiseMixin extends Mob {

    @Shadow
    public abstract void addStat(@Nullable Stat stat, int i);

    protected HostileParadiseMixin(@NonNull World world) {
        super(world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void grantHostileParadise(CallbackInfo ci) {
        if (this.world.dimension.id == AetherDimension.getAether().id) {
            this.addStat(AetherAchievements.HOSTILE_PARADISE, 1);
        }
    }
}
