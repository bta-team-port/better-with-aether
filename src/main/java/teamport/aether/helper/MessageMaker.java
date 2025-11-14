package teamport.aether.helper;

import net.minecraft.core.entity.player.Player;
import turniplabs.halplibe.helper.EnvironmentHelper;

public class MessageMaker {
    public static void sendMessage(Player player, String message) {
        if (EnvironmentHelper.isClientWorld()) {
            player.sendMessage(message);
        }
    }
}
