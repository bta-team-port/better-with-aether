package teamport.aether.compat;

import gungun974.uselessnumerical.UselessNumericalEntrypoint;
import net.minecraft.core.util.collection.NamespaceID;
import teamport.aether.AetherMod;

import java.util.function.BiConsumer;

public class Allias implements UselessNumericalEntrypoint {
      @Override
     public void defineAlias(BiConsumer<NamespaceID, NamespaceID> alias) {
          alias.accept(NamespaceID.getPermanent(AetherMod.MOD_ID, "planks_skyroot.painted"), NamespaceID.getPermanent(AetherMod.MOD_ID, "planks_skyroot_painted"));
      }
}