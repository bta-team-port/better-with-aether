package bta.aether.entity.projectiles;

import net.minecraft.core.HitResult;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import org.lwjgl.Sys;

public class EntityHammerHead extends EntityProjectileModular{

    {
        this.arrowDamage = 8;
        this.viewScale = 2;
    }

    public EntityHammerHead(World world) {
        super(world);

    }

    public EntityHammerHead(World world, EntityLiving entityliving, boolean doesArrowBelongToPlayer) {
        super(world, entityliving, doesArrowBelongToPlayer, 11);
    }

    @Override
    public void playerTouch(EntityPlayer player) {
    }

    @Override
    protected void playHitSound() {
        world.playSoundAtEntity(this, "mob.ghast.fireball", 0.3F, 1.0F / (this.world.rand.nextFloat() * 0.4F + 0.8F));
    }

    @Override
    public void tick() {
        if (inGround) remove();
        super.tick();
    }

    @Override
    protected void onHit(HitResult movingobjectposition) {
        if (movingobjectposition.entity != null) this.remove();
    }

    @Override
    public void remove() {
        for(int j = 0; j < 8; ++j) {
            this.world.spawnParticle("explode", this.x, this.y, this.z, 0.0, 0.0, 0.0);
            this.world.spawnParticle("explode", this.x, this.y, this.z, 0.0, 0.0, 0.0);
            this.world.spawnParticle("smoke", this.x, this.y, this.z, 0.0, 0.0, 0.0);
            this.world.spawnParticle("largesmoke", this.x, this.y, this.z, 0.0, 0.0, 0.0);
            this.world.spawnParticle("flame", this.x, this.y, this.z, 0.0, 0.0, 0.0);
        }

        super.remove();
    }
}
