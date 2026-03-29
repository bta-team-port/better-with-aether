package teamport.aether.mixin.dimension.bump_to_overworld;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Global;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
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
@Mixin(value = World.class)
public abstract class SPWorldMixin {
    @Shadow
    public Dimension dimension;
    @Shadow
    public Random rand;
    @Unique
    private int cooldown = Global.TICKS_PER_SECOND;
    @Inject(method = "tick", at = @At("RETURN"))
    private void tick(CallbackInfo ci) {
        cooldown--;
        if (cooldown < 0 && dimension.id == Dimension.OVERWORLD.id) {
            cooldown = Global.TICKS_PER_SECOND / 2 + rand.nextInt(Global.TICKS_PER_SECOND / 2);
            if (EnvironmentHelper.isSinglePlayer()) {
                AetherDimension.loadEntitiesNearPlayer(Minecraft.getMinecraft().thePlayer, (World) (Object) this);
            }
        }
    }
}
