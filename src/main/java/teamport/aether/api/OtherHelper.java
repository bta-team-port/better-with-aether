package teamport.aether.api;

import net.minecraft.core.entity.player.Player;

public class OtherHelper {

    // for later, for both poison and hd preview
    public static int[] calcNextColor(Player player, int barCount, int[] startColor, int[] endColor){
        int steps = (int) Math.ceil(HealthHelper.getExtraHealth(player) / 20.0f);
        float precent = (float) barCount / (float) steps;
        int r = startColor[0] + Math.round(precent * (endColor[0] - startColor[0]));
        int g = startColor[1] + Math.round(precent * (endColor[1] - startColor[1]));
        int b = startColor[2] + Math.round(precent * (endColor[2] - startColor[2]));;
        return new int[]{r, g, b};
    }
}
