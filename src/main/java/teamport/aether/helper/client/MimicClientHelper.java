package teamport.aether.helper.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.entity.ClientSkinVariantList;
import net.minecraft.core.Global;
import org.jspecify.annotations.NonNull;
import teamport.aether.entity.monster.mimic.MimicRegistry;
import teamport.aether.entity.monster.mimic.MobMimic;

@Environment(EnvType.CLIENT)
public final class MimicClientHelper {
    private MimicClientHelper() {
    }

    public static boolean cycleVariant(@NonNull MobMimic mimic) {
        ClientSkinVariantList variants = (ClientSkinVariantList) Global.accessor.getSkinVariantList();
        String variantsPath = mimic.getMimicTextureBasePath() + "variants.json";
        int skinVariant = mimic.getSkinVariant();

        if (skinVariant >= variants.getSkinTextureLength(variantsPath) - 1) {
            mimic.setVariant(MimicRegistry.getNextValue(mimic.getMimicVariant()));
            skinVariant = 0;
            variantsPath = mimic.getMimicTextureBasePath() + "variants.json";
        }

        mimic.setSkinVariant(variants.nextSkinVariant(variantsPath, skinVariant));
        return MimicRegistry.getLength() > 1;
    }
}
