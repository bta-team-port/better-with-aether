// TODO: restore btwaila compat once upstream mod lands on BTA 8.0
/*
package teamport.aether.compat.waila;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import org.spongepowered.asm.mixin.Mixins;
import turniplabs.halplibe.helper.EnvironmentHelper;

public class AetherWailaLoadMixins implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        if (!EnvironmentHelper.isServerEnvironment()) {
            FabricLoader loader = FabricLoader.getInstance();
            if (loader.isModLoaded("btwaila")) {
                Mixins.addConfiguration("compat/aether/waila/waila.mixins.json");
            }
        }
    }
}
*/
