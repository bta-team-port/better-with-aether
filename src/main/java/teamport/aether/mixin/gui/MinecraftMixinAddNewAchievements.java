package teamport.aether.mixin.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.AetherClient;

@Environment(EnvType.CLIENT)
@Mixin(value = Minecraft.class)
public abstract class MinecraftMixinAddNewAchievements {
    @Inject(method = "startGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/achievement/stat/StatList;init()V", shift = At.Shift.AFTER))
    private void initStats(CallbackInfo ci) {
        AetherClient.initAchievementsPage();
    }
}
