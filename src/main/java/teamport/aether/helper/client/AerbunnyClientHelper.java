package teamport.aether.helper.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import teamport.aether.entity.animal.aerbunny.MobAerbunny;

@Environment(EnvType.CLIENT)
public final class AerbunnyClientHelper {
    private AerbunnyClientHelper() { }

    public static double getRidingHeight(MobAerbunny bunny) {
        return bunny.heightOffset + 1.0F;
    }
}
