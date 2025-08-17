package teamport.aether;

import net.minecraft.core.entity.player.Player;

import static teamport.aether.AetherMod.TRANSLATOR;

public interface ITranslatableDeathMessage {
    default String deathMessage(Player player){
        return TRANSLATOR.translateKey(String.valueOf(this.getClass())).replace("[PLAYER]", player.getDisplayName());
    }
}
