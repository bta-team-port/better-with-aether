package teamport.aether.compat.waila;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import turniplabs.halplibe.helper.EnvironmentHelper;

import java.util.List;
import java.util.Set;

// TODO: load it differently cause this wont work anymore
public class AetherWailaLoadMixins implements IMixinConfigPlugin {

    public void onPreLaunch() {
        if (!EnvironmentHelper.isMultiplayerServer()) {
            FabricLoader loader = FabricLoader.getInstance();
            if (loader.isModLoaded("btwaila")) {
//                Mixins.addConfiguration("compat/aether/waila/waila.mixins.json");
            }
        }
    }

    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return "";
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return false;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return List.of();
    }

    @Override
    public void preApply(
        String targetClassName, ClassNode targetClass,
        String mixinClassName, IMixinInfo mixinInfo
    ) {/* no need */}

    @Override
    public void postApply(
        String targetClassName, ClassNode targetClass,
        String mixinClassName, IMixinInfo mixinInfo
    ) {/* no need */}
}

