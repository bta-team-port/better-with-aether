package teamport.aether.helper.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;

@Environment(EnvType.CLIENT)
public final class BossMusicClientHelper {
    private BossMusicClientHelper() {
    }

    public static void stop() {
        Minecraft.getMinecraft().sndManager.stopMusic();
    }

    public static void play(String sound, double x, double y, double z) {
        Minecraft minecraft = Minecraft.getMinecraft();
        minecraft.sndManager.stopMusic();
        minecraft.sndManager.playMusic(sound, (float) x, (float) y, (float) z, 1.0F, 1.0F);
    }
}
