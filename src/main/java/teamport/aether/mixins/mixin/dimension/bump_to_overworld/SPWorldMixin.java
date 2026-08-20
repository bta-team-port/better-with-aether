package teamport.aether.mixins.mixin.dimension.bump_to_overworld;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Global;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.world.AetherDimension;
import turniplabs.halplibe.helper.EnvironmentHelper;

import java.util.Random;

@Environment(EnvType.CLIENT)
@Mixin(World.class)
public abstract class SPWorldMixin {
    @Shadow
    @Final
    @NonNull
    public Dimension dimension;
    @Shadow
    @Final
    @NonNull
    public Random rand;
    @Unique
    private int cooldown = Global.TICKS_PER_SECOND;

    @Inject(method = "tick()V", at = @At("RETURN"))
    private void loadFallenEntities(CallbackInfo ci) {
        cooldown--;
        if (cooldown < 0 && dimension.id == Dimension.OVERWORLD.id) {
            cooldown = Global.TICKS_PER_SECOND / 2 + rand.nextInt(Global.TICKS_PER_SECOND / 2);
            if (EnvironmentHelper.isSingleplayerClient()) {
                AetherDimension.loadEntitiesNearPlayer(Minecraft.getMinecraft().thePlayer, (World) (Object) this);
            }
        }
    }
}
