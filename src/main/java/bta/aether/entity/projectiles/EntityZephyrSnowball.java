package bta.aether.entity.projectiles;

import bta.aether.entity.EntityZephyr;
import net.minecraft.core.HitResult;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;

public class EntityZephyrSnowball extends EntityProjectileModular{

    {
        this.arrowDamage = 1;
    }

    public EntityZephyrSnowball(World world, double d, double d1, double d2, int arrowType) {
        super(world, d, d1, d2, arrowType);
    }

    public EntityZephyrSnowball(World world, EntityLiving entityliving, boolean doesArrowBelongToPlayer) {
        super(world, entityliving, doesArrowBelongToPlayer, 11);
    }

    @Override
    public void playerTouch(EntityPlayer player) {
    }

    @Override
    public void tick() {
        if (inGround) remove();
        super.tick();
    }

    @Override
    protected void playHitSound() {
    }

    @Override
    protected void onHit(HitResult movingobjectposition) {
        if (movingobjectposition.entity != null) {
            movingobjectposition.entity.push(this.xd, this.yd, this.zd);
            this.remove();
        }
    }

    @Override
    public void remove() {
        for (int particle = 0; particle < 8; particle++) {
            this.world.spawnParticle("snowballpoof", this.x, this.y, this.z, 0.0, 0.0, 0.0);
        }

        super.remove();
    }
}
