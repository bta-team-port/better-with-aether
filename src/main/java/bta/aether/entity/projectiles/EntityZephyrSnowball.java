package bta.aether.entity.projectiles;

import net.minecraft.core.HitResult;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.projectile.EntityArrow;
import net.minecraft.core.world.World;

public class EntityZephyrSnowball extends EntityArrow {

    public EntityZephyrSnowball(World world, double d, double d1, double d2, int arrowType) {
        super(world, d, d1, d2, arrowType);
    }

    public EntityZephyrSnowball(World world, EntityLiving entityliving, boolean doesArrowBelongToPlayer) {
        super(world, entityliving, doesArrowBelongToPlayer, 11);
    }

    @Override
    protected void init() {
        super.init();
        this.damage = 0;
    }

    @Override
    public void onHit(HitResult hitResult) {
        if (hitResult.entity != null) {
            hitResult.entity.push(this.xd * 1.75, 0, this.zd * 1.75);
            this.remove();
        }
    }

    @Override
    protected void inGroundAction() {
        this.remove();
    }

    @Override
    public void remove() {
        for (int particle = 0; particle < 8; particle++) {
            this.world.spawnParticle("snowballpoof", this.x, this.y, this.z, 0.0, 0.0, 0.0);
        }

        super.remove();
    }
}
