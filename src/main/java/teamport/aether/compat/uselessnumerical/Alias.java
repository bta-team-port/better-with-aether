package teamport.aether.compat.uselessnumerical;

import gungun974.uselessnumerical.UselessNumericalEntrypoint;
import net.minecraft.core.util.collection.NamespaceID;
import org.jspecify.annotations.NonNull;
import teamport.aether.AetherMod;

import java.util.function.BiConsumer;

public class Alias implements UselessNumericalEntrypoint {
    @Override
    public void defineAlias(@NonNull BiConsumer<NamespaceID, NamespaceID> alias) {
        alias.accept(
            NamespaceID.fromPool(AetherMod.MOD_ID, "block/planks_skyroot.painted"),
            NamespaceID.fromPool(AetherMod.MOD_ID, "block/planks_skyroot_painted")
        );
        alias.accept(
            NamespaceID.fromPool(AetherMod.MOD_ID, "item/sign.skyroot.painted"),
            NamespaceID.fromPool(AetherMod.MOD_ID, "item/sign_skyroot_painted")
        );
        alias.accept(
            NamespaceID.fromPool(AetherMod.MOD_ID, "block/flower_purple"),
            NamespaceID.fromPool(AetherMod.MOD_ID, "block/flower_aether_purple")
        );
        alias.accept(
            NamespaceID.fromPool(AetherMod.MOD_ID, "block/flower_white"),
            NamespaceID.fromPool(AetherMod.MOD_ID, "block/flower_aether_white")
        );
    }
}
