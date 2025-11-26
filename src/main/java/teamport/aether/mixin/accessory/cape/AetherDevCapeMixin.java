package teamport.aether.mixin.accessory.cape;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRendererPlayer;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.AetherGlobals;

@Environment(EnvType.CLIENT)
@Mixin(MobRendererPlayer.class)
public abstract class AetherDevCapeMixin {
    @SuppressWarnings("java:S131")
    @Inject(method = "renderSpecials*", at = @At("HEAD"), remap = false)
    private void injectCapeOverride(Player player, float partialTick, CallbackInfo ci) {
        switch (player.uuid.toString()) {
            case AetherGlobals.UUID_LUKEISSTUFF: // LukeisStuff
            case AetherGlobals.UUID_OLYPOLYU: // Olypolyu / Kheprep
            case AetherGlobals.UUID_TOCININ: // Tocinin
            case AetherGlobals.UUID_REDART15: // Redart15
            case AetherGlobals.UUID_SMUSHYTACO: // SmushyTaco
            case AetherGlobals.UUID_TOUFOUMASTER: //ToufouMaster
            case AetherGlobals.UUID_RIN: //Rin
                player.capeURL = "https://raw.githubusercontent.com/bta-team-port/better-with-aether/refs/heads/7.3/src/main/resources/assets/aether/textures/armor/cape/aether.png";
                break;
        }
    }
}
