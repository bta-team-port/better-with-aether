package teamport.aether.mixin.dimension.bumpToOverworld;

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

@Mixin(value = World.class, remap = false)
public class SPWorldMixin {

    @Shadow
    public Dimension dimension;

    @Unique
    public final int COOLDOWN_MAX = Global.TICKS_PER_SECOND;

    @Unique
    public int cooldown = COOLDOWN_MAX;

    @Inject(method = "tick", at = @At("RETURN"))
    public void tick(CallbackInfo ci) {
        cooldown--;

        if (cooldown < 0 && dimension.id == Dimension.OVERWORLD.id) {
            cooldown = COOLDOWN_MAX;

            if (EnvironmentHelper.isSinglePlayer()) {
                AetherDimension.loadEntitiesNearPlayer(Minecraft.getMinecraft().thePlayer);
            }
        }
    }

}
