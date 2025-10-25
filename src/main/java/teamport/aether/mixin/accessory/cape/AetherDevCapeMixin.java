package teamport.aether.mixin.accessory.cape;

import net.minecraft.client.render.entity.MobRendererPlayer;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.AetherGlobals;

@Mixin(MobRendererPlayer.class)
public class AetherDevCapeMixin {

    @Inject(method = "renderSpecials*", at = @At("HEAD"), remap = false)
    private void injectCapeOverride(Player player, float partialTick, CallbackInfo ci) {
        String uuid = player.uuid.toString();
        switch (uuid) {
            case AetherGlobals.UUID_LUKEISSTUFF: // LukeisStuff
            case AetherGlobals.UUID_OLYPOLYU: // Olypolyu / Kheprep
            case AetherGlobals.UUID_TOCININ: // Tocinin
            case AetherGlobals.UUID_REDART15: // Redart15
                player.capeURL = "https://raw.githubusercontent.com/bta-team-port/better-with-aether/refs/heads/7.3/src/main/resources/assets/aether/textures/armor/cape/aether.png";
                break;
        }
    }
}