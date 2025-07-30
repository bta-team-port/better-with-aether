package teamport.aether.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.AetherClient;
import teamport.aether.mixin.accessors.ItemsAccessor;

@Mixin(value = Minecraft.class, remap = false)
public class MinecraftMixinAddNewAchievements {

    @Inject(method = "startGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/achievement/stat/StatList;init()V"))
    public void initStats(CallbackInfo ci) {
        ItemsAccessor.invokeInitStats();
        AetherClient.initAchievementsPage();
    }
}
