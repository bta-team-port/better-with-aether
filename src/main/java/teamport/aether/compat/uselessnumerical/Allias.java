package teamport.aether.compat.uselessnumerical;

import gungun974.uselessnumerical.UselessNumericalEntrypoint;
import net.minecraft.core.util.collection.NamespaceID;
import teamport.aether.AetherMod;

import java.util.function.BiConsumer;

public class Allias implements UselessNumericalEntrypoint {
    @Override
    public void defineAlias(BiConsumer<NamespaceID, NamespaceID> alias) {
        alias.accept(
            NamespaceID.getPermanent(AetherMod.MOD_ID, "block/planks_skyroot.painted"),
            NamespaceID.getPermanent(AetherMod.MOD_ID, "block/planks_skyroot_painted")
        );
        alias.accept(
            NamespaceID.getPermanent(AetherMod.MOD_ID, "item/sign.skyroot.painted"),
            NamespaceID.getPermanent(AetherMod.MOD_ID, "item/sign_skyroot_painted")
        );
    }
}
