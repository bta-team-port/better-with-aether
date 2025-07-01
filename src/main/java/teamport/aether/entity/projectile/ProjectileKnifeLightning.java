package teamport.aether.entity.projectile;

import net.minecraft.core.entity.EntityLightning;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import teamport.aether.items.AetherItems;

public class ProjectileKnifeLightning extends Projectile {

    public ProjectileKnifeLightning(World world, double x, double y, double z) {
        super(world, x, y, z);
        this.modelItem = AetherItems.TOOL_KNIFE_LIGHTNING;
    }

    public ProjectileKnifeLightning(World world, Player owner) {
        super(world, owner);
    }

    public void initProjectile() {
        this.damage = 6;
        this.defaultGravity = 0.03F;
        this.defaultProjectileSpeed = 0.99F;
    }



    public void onHit(HitResult hitResult) {
        if (hitResult.entity != null) {
            hitResult.entity.hurt(this.owner, this.damage, DamageType.COMBAT);
            hitResult.entity.world.entityJoinedWorld(new EntityLightning(hitResult.entity.world, hitResult.entity.x, hitResult.entity.y + 0.5, hitResult.entity.z));
            doEffect();
            this.remove();
        }

        if (hitResult.hitType == HitResult.HitType.TILE) {
            doEffect();
            this.remove();
        }

    }

    public void doEffect() {
        world.entityJoinedWorld(new EntityLightning(world, this.x, this.y + 0.5, this.z));
        for (int j = 0; j < 8; ++j) {
//            this.world.spawnParticle("explode", this.x, this.y, this.z, 0.0, 0.0, 0.0,0);
//            this.world.spawnParticle("smoke", this.x, this.y, this.z, 0.0, 0.0, 0.0,0);
//            this.world.spawnParticle("largesmoke", this.x, this.y, this.z, 0.0, 0.0, 0.0,0);
//            this.world.spawnParticle("flame", this.x, this.y, this.z, 0.0, 0.0, 0.0,0);
        }
    }
}
