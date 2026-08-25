package teamport.aether.entity.monster.mimic;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.net.command.TextFormatting;
import turniplabs.halplibe.util.deathcause.vanilla.DeathCauseKilledBy;

public class DeathCauseMimic extends DeathCauseKilledBy {

    public DeathCauseMimic(){
        super();
    }

    public DeathCauseMimic(Mob victim, Mob attacker){
        super(victim, attacker);
    }

    @Override
    public TextFormatting.Base getTextFormattingBase() {
        return TextFormatting.Base.WHITE;
    }
}
