package teamport.aether.entity;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.MathHelper;
import teamport.aether.entity.player.PlayerUtil;
import teamport.aether.item.AetherArmorMaterial;

public class MobUtil {

    private MobUtil() {
    }

    public static void knockback(
        Entity target, Entity attacker,
        float knockBackStrength, float lift
    ) {
        if (knockBackStrength < 0.001) {
            throw new RuntimeException("Cannot multiply speed by zero!");
        }
        double distX = attacker.x - target.x;
        double distZ = attacker.z - target.z;

        if(target instanceof Player){
            int count = PlayerUtil.countArmorPiecesOfMaterial(((Player)target).inventory, AetherArmorMaterial.OBSIDIAN);
            if (count >= 5) {
                return;
            }
            if (count >= 3) {
                distX /= 2;
                distZ /= 2;
            }
        }

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


    public static boolean multiHit(Entity attacker, Entity victim, DamageInstance... instances) {
        if (instances == null) {
            return false;
        }
        if (instances.length < 2) {
            DamageInstance instance = instances[0];
            return victim.hurt(attacker, instance.getDamage(), instance.getType());
        }
        boolean cumulativeAccept = true;
        int cumulativeDamage = 0;
        for (DamageInstance instance : instances) {
            cumulativeDamage += instance.damage;
            cumulativeAccept = victim.hurt(attacker, cumulativeDamage, instance.getType());
        }
        return cumulativeAccept;
    }

    public static boolean killMob(Mob mob) {
        return MobUtil.killMob(mob, null);
    }

    public static boolean killMob(Mob mob, Entity attack) {
        mob.setHealthRaw(0);
        mob.playDeathSound();
        mob.onDeath(attack);
        return true;
    }

    public static void convertMob(Entity mob, String mobId) {
        Class<? extends Entity> tempestClazz = EntityDispatcher.classForId(mobId);
        if (tempestClazz == null) {
            return;
        }
        Entity entity = EntityDispatcher.createEntityInWorld(tempestClazz, mob.world);
        if (entity == null) {
            return;
        }
        entity.moveTo(mob.x, mob.y, mob.z, mob.yRot, mob.xRot);
        mob.world.entityJoinedWorld(entity);
        mob.remove();
    }
}
