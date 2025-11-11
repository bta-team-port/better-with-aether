package teamport.aether.mixin.achievement;

import net.minecraft.client.Minecraft;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.achievements.AetherAchievements;
import teamport.aether.world.AetherDimension;

@Mixin(value = Player.class, remap = false)
public abstract class HostileParadiseMixin extends Mob {
    public HostileParadiseMixin(@Nullable World world) {
        super(world);
    }

    @Shadow
    public int dimension;

    @Inject(method = "tick", at = @At(value = "HEAD"))
    public void grantHostileParadise(CallbackInfo ci) {
        if (this.world.dimension.id == AetherDimension.AETHER.id) {
            Minecraft.getMinecraft().thePlayer.addStat(AetherAchievements.HOSTILE_PARADISE, 1);
        }
    }
}
