package teamport.aether.entity;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.net.command.TextFormatting;
import org.jspecify.annotations.NonNull;

import static net.minecraft.core.net.command.TextFormatting.RED;
import static net.minecraft.core.net.command.TextFormatting.RESET;
import static teamport.aether.AetherMod.TRANSLATOR;

public interface AetherDeathMessage {
    default String deathMessage(@NonNull Player player) {
        String victim = TextFormatting.scoped(Entity.getNameFromEntity(player, true));


        EntityDispatcher.EntityDispatcherEntry<?> entry = EntityDispatcher.getInstance().entryForClass(((Entity) this).getClass());
        String key = (entry == null ? "" : entry.nameKey) + ".death_message";
        String deathMessage = TRANSLATOR
            .translateKey(key)
            .replace("[PLAYER]", RESET + player.getDisplayName() + RESET + RED);
        return RED + deathMessage;
    }




    /**
     * TextFormats::scope
     * push string pop
     *
     *
     * isStatus false
     * format red
     * translatable message
     * scoped victim, scoped attacker
     *
     *
     * PlayerLocal
     * translate key
     * fill in args
     * prepands format
     * adds it to the gui
     */

}
