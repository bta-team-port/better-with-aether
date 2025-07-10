package teamport.aether.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.PlayerLocal;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.AetherClient;
import teamport.aether.accessory.api.HealthHelper;
import teamport.aether.mixin.accessors.ItemsAccessor;

@Mixin(value = Minecraft.class, remap = false)

public abstract class MinecraftMixin {
    @Shadow
    public PlayerLocal thePlayer;

    @Inject(method = "startGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/achievement/stat/StatList;init()V"))
    public void initStats(CallbackInfo ci) {
        ItemsAccessor.invokeInitStats();
        AetherClient.initAchievementsPage();
    }

    // preserves the extra Health on death
    @Inject(method = "respawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/player/PlayerLocal;setGamemode(Lnet/minecraft/core/player/gamemode/Gamemode;)V"), remap = false)
    public void keepExtraHealthClient(boolean multiplayer, int targetDimension, CallbackInfo ci, @Local Player previousPlayer) {
        HealthHelper.setExtraHealth(thePlayer, HealthHelper.getExtraHealth(previousPlayer));
    }
}
