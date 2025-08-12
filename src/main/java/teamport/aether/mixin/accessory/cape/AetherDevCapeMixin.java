package teamport.aether.mixin.accessory.cape;

import net.minecraft.client.render.entity.MobRendererPlayer;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobRendererPlayer.class)
public class AetherDevCapeMixin {

    @Inject(method = "renderSpecials*", at = @At("HEAD"), remap = false)
    private void injectCapeOverride(Player player, float partialTick, CallbackInfo ci) {
        String uuid = player.uuid.toString();
        switch (uuid) {
            case "db7db941-6923-4855-a879-1ae655c16122": // LukeisStuff
            case "d561a5ee-57df-491d-80ea-784251df4bef": // Olypolyu / Kheprep
            case "4f419f3d-c2b0-41de-92bb-9740e43b640d": // Tocinin
            case "3da8c87f-1845-455c-b91f-7e9ee8f4c0ec": // Redart15
                player.capeURL = "https://raw.githubusercontent.com/bta-team-port/better-with-aether/refs/heads/7.3/src/main/resources/assets/aether/textures/armor/cape/aether.png";
                break;
        }
    }
}