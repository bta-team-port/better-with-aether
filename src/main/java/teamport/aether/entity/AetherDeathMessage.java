package teamport.aether.entity;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.player.Player;

import static net.minecraft.core.net.command.TextFormatting.RED;
import static net.minecraft.core.net.command.TextFormatting.RESET;
import static teamport.aether.AetherMod.TRANSLATOR;

public interface AetherDeathMessage {
    default String deathMessage(Player player) {
        String key = EntityDispatcher.nameKeyForClass(((Entity) this).getClass()) + ".death_message";
        String deathMessage = TRANSLATOR
            .translateKey(key)
            .replace("[PLAYER]", RESET + player.getDisplayName() + RESET + RED);
        return RED + deathMessage;
    }
}
