package teamport.aether.entity;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.net.command.TextFormatting;
import teamport.aether.helper.StringHelper;

import static teamport.aether.AetherMod.TRANSLATOR;

public interface AetherTranslatableDeathMessage {
    default String deathMessage(Player player){
        String deathMessage = TRANSLATOR.translateKey(StringHelper.formatTranslationKey(((Entity) this).getClass()))
            .replace("[PLAYER]", player.getDisplayName() + TextFormatting.RESET + TextFormatting.RED);

        return TextFormatting.RED + deathMessage;
    }
}
