package teamport.aether.helper;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.MathHelper;

public class MobUtil {

    public static void customKnockback(Entity target, Entity attacker) {
        customKnockback(target, attacker, 0.4f, 0.4f);
    }

    public static void customKnockback(
            Entity target, Entity attacker,
            float knockBackStrength, float lift
    ) {
        double distX = attacker.x - target.x;
        double distY = attacker.z - target.z;
        float horizonalDistance = MathHelper.sqrt(distX * distX + distY * distY);

        // half momentum
        target.xd /= 2.0F; // velocity x
        target.yd /= 2.0F; // velocity y
        target.zd /= 2.0F; // velocity z
        target.xd = target.xd - (distX / (double) horizonalDistance * (double) knockBackStrength);
        target.yd = target.yd + lift;
        target.zd = target.zd - (distY / (double) horizonalDistance * (double) knockBackStrength);
        updateVelocity(target);
    }

    private static void updateVelocity(Entity target) {
        target.xo = target.x;
        target.yo = target.y;
        target.zo = target.z;
    }


}
