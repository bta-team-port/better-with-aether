package teamport.aether.helper;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.MathHelper;

public class MobUtil {

    public static void knockback(
        Entity target, Entity attacker,
        float knockBackStrength, float lift
    ) {
        if (knockBackStrength < 0.001) {
            throw new RuntimeException("Cannot multiply speed by zero!");
        }

        double distX = attacker.x - target.x;
        double distZ = attacker.z - target.z;
        float horizonalDistance = Math.max(0.001F, MathHelper.sqrt(distX * distX + distZ * distZ));

        // half momentum, to slow the player down
        target.xd /= 2.0F; // velocity x
        target.yd /= 2.0F; // velocity y
        target.zd /= 2.0F; // velocity z

        // set velocity, apply knockback
        target.xd = target.xd - (distX / horizonalDistance * knockBackStrength);
        target.yd = target.yd + lift;
        target.zd = target.zd - (distZ / horizonalDistance * knockBackStrength);

        // update velocity, so it works on the server
        target.xo = target.x;
        target.yo = target.y;
        target.zo = target.z;
    }
}
