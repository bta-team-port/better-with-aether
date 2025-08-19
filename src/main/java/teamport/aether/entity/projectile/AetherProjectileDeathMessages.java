package teamport.aether.entity.projectile;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.projectile.Projectile;
import teamport.aether.entity.AetherDeathMessage;
import teamport.aether.entity.boss.MobBoss;
import teamport.aether.helper.StringHelper;

import static net.minecraft.core.net.command.TextFormatting.RED;
import static net.minecraft.core.net.command.TextFormatting.RESET;
import static teamport.aether.AetherMod.TRANSLATOR;

public interface AetherProjectileDeathMessages<T extends Projectile> extends AetherDeathMessage {
    @Override
    default String deathMessage(Player player) {
        Projectile proj = (Projectile) this;
        Entity owner = proj.owner;
        String keys = StringHelper.formatTranslationKey(proj.getClass());
        if(owner instanceof Player){
            Player killer = (Player) owner;
            if((killer).uuid.equals(player.uuid)){
                return RED + TRANSLATOR.translateKey(keys + ".suicide")
                        .replace("[PLAYER]", RESET + player.getDisplayName() + RESET + RED);
            }else{
                return RED + TRANSLATOR.translateKey(keys  + ".killer")
                        .replace("[PLAYER]", RESET + player.getDisplayName() + RESET + RED)
                        .replace("[KILLER]", RESET + killer.getDisplayName() + RESET + RED);
            }
        }
        if(owner instanceof MobBoss){

        }
        return ((AetherDeathMessage)owner).deathMessage(player);
    }

}
