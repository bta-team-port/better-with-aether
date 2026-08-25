package teamport.aether.entity.projectile;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.projectile.Projectile;
import org.jspecify.annotations.NonNull;
import teamport.aether.entity.boss.EnemyBoss;

import static net.minecraft.core.net.command.TextFormatting.RED;
import static net.minecraft.core.net.command.TextFormatting.RESET;
import static teamport.aether.AetherMod.TRANSLATOR;

public interface AetherProjectileDeathMessages extends AetherDeathMessage {
    @Override
    default String deathMessage(@NonNull Player player) {
        Projectile proj = (Projectile) this;
        Entity owner = proj.owner;
        EntityDispatcher.EntityDispatcherEntry<?> entry = EntityDispatcher.getInstance().entryForClass(proj.getClass());
        if (owner instanceof Player killer) {
            if(entry == null){
                return TRANSLATOR.translateKey("messages.death.player.generic");
            }
            String keys = entry.nameKey + ".death_message";
            if ((killer).uuid.equals(player.uuid)) {
                return RED + TRANSLATOR.translateKey(keys + ".suicide")
                    .replace("[PLAYER]", RESET + player.getDisplayName() + RESET + RED);
            } else {
                return RED + TRANSLATOR.translateKey(keys + ".player")
                    .replace("[PLAYER]", RESET + player.getDisplayName() + RESET + RED)
                    .replace("[KILLER]", RESET + killer.getDisplayName() + RESET + RED);
            }
        }
        if (owner instanceof EnemyBoss boss) {
            EntityDispatcher.EntityDispatcherEntry<?> ownerEntry = EntityDispatcher.getInstance().entryForClass(owner.getClass());
            if(entry == null || ownerEntry == null){
                return TRANSLATOR.translateKey("messages.death.player.generic");
            }
            String bossName = ownerEntry.nameKey + ".death_message";
            String projectileName = TRANSLATOR.translateKey(entry.nameKey);
            return RED + TRANSLATOR.translateKey(bossName + "." + projectileName)
                .replace("[PLAYER]", RESET + player.getDisplayName() + RESET + RED)
                .replace("[BOSS]", boss.getTranslatedBossTitle());
        }
        return owner instanceof AetherDeathMessage deathMessage
            ? deathMessage.deathMessage(player)
            : AetherDeathMessage.super.deathMessage(player);
    }

}
