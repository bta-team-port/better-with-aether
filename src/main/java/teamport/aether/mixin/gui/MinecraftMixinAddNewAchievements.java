package teamport.aether.mixin.gui;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.AetherClient;

@Mixin(value = Minecraft.class, remap = false)
public abstract class MinecraftMixinAddNewAchievements {

    @Inject(method = "startGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/achievement/stat/StatList;init()V", shift = At.Shift.AFTER))
    public void initStats(CallbackInfo ci) {
        AetherClient.initAchievementsPage();
    }
}
