package teamport.aether.entity;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import teamport.aether.helper.StringHelper;

import static net.minecraft.core.net.command.TextFormatting.RED;
import static net.minecraft.core.net.command.TextFormatting.RESET;
import static teamport.aether.AetherMod.TRANSLATOR;

public interface AetherDeathMessage {
    default String deathMessage(Player player){
        String deathMessage = TRANSLATOR.translateKey(StringHelper.formatTranslationKey(((Entity) this).getClass()))
            .replace("[PLAYER]", RESET + player.getDisplayName() + RESET + RED);
        return RED + deathMessage;
    }
}
