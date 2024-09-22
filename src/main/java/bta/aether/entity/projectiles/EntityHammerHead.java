package bta.aether.entity.projectiles;

import net.minecraft.core.HitResult;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.projectile.EntityArrow;
import net.minecraft.core.world.World;

public class EntityHammerHead extends EntityArrow {

    public EntityHammerHead(World world) {
        super(world);

    }

    public EntityHammerHead(World world, EntityLiving entityliving, boolean doesArrowBelongToPlayer) {
        super(world, entityliving, doesArrowBelongToPlayer, 11);
    }

    @Override
    protected void init() {
        super.init();
        this.damage = 8;
        this.viewScale = 2;
    }

    @Override
    public void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (hitResult.entity != null) doEffect();
    }

    @Override
    protected void inGroundAction() {
        doEffect();
        this.remove();
    }

    private void doEffect() {
        world.playSoundAtEntity(null, this, "mob.ghast.fireball", 0.3F, 1.0F / (this.world.rand.nextFloat() * 0.4F + 0.8F));
        for (int j = 0; j < 8; ++j) {
            this.world.spawnParticle("explode", this.x, this.y, this.z, 0.0, 0.0, 0.0, 0);
            this.world.spawnParticle("explode", this.x, this.y, this.z, 0.0, 0.0, 0.0, 0);
            this.world.spawnParticle("smoke", this.x, this.y, this.z, 0.0, 0.0, 0.0, 0);
            this.world.spawnParticle("largesmoke", this.x, this.y, this.z, 0.0, 0.0, 0.0, 0);
            this.world.spawnParticle("flame", this.x, this.y, this.z, 0.0, 0.0, 0.0, 0);
        }
    }
}
