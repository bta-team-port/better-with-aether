package bta.aether.entity;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.EntityAnimal;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;

public class EntityAerbunny extends EntityAnimal {

    // run away when attacked
    // puff up on jump and slowly fart the puffiness away
    public Entity runningFrom;

    public EntityAerbunny(World world) {
        super(world);
        this.setSize(1, 1);
    }

    @Override
    public String getEntityTexture() {
        return "/assets/aether/mobs/aerbunny.png";
    }

    @Override
    public boolean hurt(Entity attacker, int damage, DamageType type) {
        if (isPassenger()) return false;
        runningFrom = attacker;
        return super.hurt(attacker, damage, type);
    }

    @Override
    public void tick() {
        super.tick();

        this.fallDistance = 0.0F;
        if (!this.onGround && !this.isInWater() && this.yd < 0.0 && !this.collision) {
            this.yd *= 0.75;
        }

        if (this.onGround && this.random.nextInt(100) == 0) jump();
        if (!this.onGround && this.random.nextInt(50) == 0) jump();

        if (this.pathToEntity == null && this.runningFrom != null) {
            if (Math.pow(this.runningFrom.x - this.x, 2) + Math.pow(this.runningFrom.y - this.y, 2) + Math.pow(this.runningFrom.z - this.z, 2) < 3600) {
                runAway();
                this.speed = 0.85F;
            } else {
                runningFrom = null;
                this.speed = 0.35F;
            }
        }
    }

    public void runAway() {
        double angle = Math.toRadians(this.random.nextInt(60) * 6);
        this.pathToEntity = this.world.getEntityPathToXYZ(this, (int) (this.x + (-Math.cos(angle) * 20.0)), (int) this.y, (int) (this.z + (-Math.sin(angle) * 20.0)), 10.0F);
    }

    @Override
    protected void jump() {
        this.world.spawnParticle("explode", this.x, this.y + 0.5, this.z, 0.0, -0.075, 0.0);
        if (!this.noPhysics) {
            this.yd = 0.60;
            if (this.isSprinting()) {
                float f = this.yRot * 0.01745329F;
                this.xd -= MathHelper.sin(f) * 0.2F;
                this.zd += MathHelper.cos(f) * 0.2F;
            }
        }
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        if (isPassenger()) {
            entityplayer.ejectRider();
            this.y = entityplayer.y + entityplayer.getRideHeight();
        } else {
            this.startRiding(entityplayer);
        }
        return super.interact(entityplayer);
    }
}
