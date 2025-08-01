package teamport.aether.entity;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;

public class MobBoss extends Mob implements EnemyBoss {
    protected static String translationKey = "MISSING";
    public MobBoss(@Nullable World world) {
        super(world);
    }

    @Override
    public String getBossTitle() {
        return bossName + ", The " +  I18n.getInstance().translateKey(translationKey);
    }

    @Override
    public void returnToHome() {
        if (returnPoint == null) return;
        moveTo(returnPoint.x, returnPoint.y, returnPoint.z, 0, 0);
    }
}
