package teamport.aether.entity.projectile;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.projectile.Projectile;
import teamport.aether.entity.AetherDeathMessage;
import teamport.aether.entity.boss.EnemyBoss;

import static net.minecraft.core.net.command.TextFormatting.RED;
import static net.minecraft.core.net.command.TextFormatting.RESET;
import static teamport.aether.AetherMod.TRANSLATOR;

public interface AetherProjectileDeathMessages extends AetherDeathMessage {
    @Override
    default String deathMessage(Player player) {
        Projectile proj = (Projectile) this;
        Entity owner = proj.owner;
        if (owner instanceof Player) {
            Player killer = (Player) owner;
            String keys = EntityDispatcher.getInstance().entryForClass(proj.getClass()).nameKey + ".death_message";
            if ((killer).uuid.equals(player.uuid)) {
                return RED + TRANSLATOR.translateKey(keys + ".suicide")
                    .replace("[PLAYER]", RESET + player.getDisplayName() + RESET + RED);
            } else {
                return RED + TRANSLATOR.translateKey(keys + ".player")
                    .replace("[PLAYER]", RESET + player.getDisplayName() + RESET + RED)
                    .replace("[KILLER]", RESET + killer.getDisplayName() + RESET + RED);
            }
        }
        if (owner instanceof EnemyBoss) {
            EnemyBoss boss = (EnemyBoss) owner;
            String bossName = EntityDispatcher.getInstance().entryForClass(owner.getClass()).nameKey + ".death_message";
            String projectileName = TRANSLATOR.translateKey(EntityDispatcher.getInstance().entryForClass(proj.getClass()).nameKey);
            return RED + TRANSLATOR.translateKey(bossName + "." + projectileName)
                .replace("[PLAYER]", RESET + player.getDisplayName() + RESET + RED)
                .replace("[BOSS]", boss.getBossTitle());
        }
        return owner instanceof AetherDeathMessage
            ? ((AetherDeathMessage) owner).deathMessage(player)
            : AetherDeathMessage.super.deathMessage(player);
    }

}
