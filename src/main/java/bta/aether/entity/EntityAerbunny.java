package bta.aether.entity;

import bta.aether.AetherBlockTags;
import bta.aether.block.AetherBlocks;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.EntityAnimal;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;

public class EntityAerbunny extends EntityAnimal {
    private static final int MAXIMUM_PUFFS = 11;
    // run away when attacked
    // puff up on jump and slowly fart the puffiness away
    public Entity runningFrom;

    private int puffSubtract;

    public int aerMax = 15;
    public int aer = 0;


    public EntityAerbunny(World world) {
        super(world);
        this.setSize(0.6F, 0.6F);
    }

    @Override
    protected void init() {
        super.init();
        this.entityData.define(16, 0);
    }

    @Override
    public String getEntityTexture() {
        return "/Jar/aether/mobs/aerbunny.png";
    }

    @Override
    public boolean hurt(Entity attacker, int damage, DamageType type) {
        if (isPassenger()) return false;
        runningFrom = attacker;
        return super.hurt(attacker, damage, type);
    }

    @Override
    public void tick() {

        this.fallDistance = 0.0F;
        if (!this.onGround && !this.isInWater() && this.yd < 0.0 && !this.collision) {
            this.yd *= 0.75;
        }

        if (this.onGround && this.random.nextInt(100) == 0) {
            jump();
            this.aer = this.aerMax;
        }

        if (aer > 0 && this.random.nextInt(8) == 0) {
            this.jump();
            double lookAngleRadians = Math.atan2(this.getLookAngle().zCoord, this.getLookAngle().xCoord);
            this.push(0.125 * Math.cos(lookAngleRadians), 0, 0.125 * Math.sin(lookAngleRadians));
            this.aer--;
        }

        if (this.pathToEntity == null && this.runningFrom != null) {
            if (Math.pow(this.runningFrom.x - this.x, 2) + Math.pow(this.runningFrom.y - this.y, 2) + Math.pow(this.runningFrom.z - this.z, 2) < 3600) {
                runAway();
                this.speed = 0.85F;
            } else {
                runningFrom = null;
                this.speed = 0.35F;
            }
        }

        this.setPuffiness(this.getPuffiness() - this.puffSubtract);
        if (this.getPuffiness() > 0) {
            this.puffSubtract = 1;
        } else {
            this.puffSubtract = 0;
        }
        super.tick();
    }

    @Override
    protected void updatePlayerActionState() {
        if (this.onGround) {
            this.moveForward = 0.0f;
            this.moveStrafing = 0.0f;
        }
        this.hasAttacked = this.isMovementCeased();
        if (!this.hasAttacked && this.closestFireflyEntity == null && (this.pathToEntity == null && this.random.nextInt(80) == 0 || this.random.nextInt(80) == 0)) {
            this.roamRandomPath();
        }
        int i = MathHelper.floor_double(this.bb.minY + 0.5);
        boolean inWater = this.isInWater();
        boolean inLava = this.isInLava();
        this.xRot = 0.0f;
        if (this.pathToEntity == null || this.random.nextInt(100) == 0) {
            super.updatePlayerActionState();
            this.pathToEntity = null;
            return;
        }
        Vec3d coordsForNextPath = this.pathToEntity.getPos(this);
        double d = this.bbWidth * 2.0f;
        while (coordsForNextPath != null && coordsForNextPath.squareDistanceTo(this.x, coordsForNextPath.yCoord, this.z) < d * d) {
            this.pathToEntity.next();
            if (this.pathToEntity.isDone()) {
                this.closestFireflyEntity = null;
                coordsForNextPath = null;
                this.pathToEntity = null;
                continue;
            }
            coordsForNextPath = this.pathToEntity.getPos(this);
        }
        this.isJumping = false;
        if (coordsForNextPath != null) {
            if (this.onGround) {
                float f3;
                double x1 = coordsForNextPath.xCoord - this.x;
                double z1 = coordsForNextPath.zCoord - this.z;
                double y1 = coordsForNextPath.yCoord - (double) i;
                float f2 = (float) (Math.atan2(z1, x1) * 180.0 / 3.1415927410125732) - 90.0f;
                this.moveForward = this.moveSpeed;
                for (f3 = f2 - this.yRot; f3 < -180.0f; f3 += 360.0f) {
                }
                while (f3 >= 180.0f) {
                    f3 -= 360.0f;
                }
                if (f3 > 30.0f) {
                    f3 = 30.0f;
                }
                if (f3 < -30.0f) {
                    f3 = -30.0f;
                }
                this.yRot += f3;
                //This makes it not do a weird dash.
                this.jump();
            }
        }
        if (this.horizontalCollision && !this.hasPath()) {
            this.isJumping = true;
        }
        if (this.random.nextFloat() < 0.8f && (inWater || inLava)) {
            this.isJumping = true;
        }
    }

    public int getPuffiness() {
        return this.entityData.getInt(16);
    }

    public void setPuffiness(int puffiness) {
        this.entityData.set(16, puffiness);
    }

    public int getPuffSubtract() {
        return this.puffSubtract;
    }

    public void puff() {
        this.world.spawnParticle("explode", this.x, this.y + 0.5, this.z, 0.0, -0.075, 0.0);

        this.setPuffiness(MAXIMUM_PUFFS);
    }

    public void runAway() {
        double angle = Math.toRadians(this.random.nextInt(60) * 6);
        this.pathToEntity = this.world.getEntityPathToXYZ(this, (int) (this.x + (-Math.cos(angle) * 20.0)), (int) this.y, (int) (this.z + (-Math.sin(angle) * 20.0)), 10.0F);
        this.aer = this.aerMax;
    }

    @Override
    protected void jump() {
        if (!this.noPhysics) {
            this.yd = 0.80;
            if (aer > 0) this.yd = 0.20;

            if (this.isSprinting()) {
                float f = this.yRot * 0.01745329F;
                this.xd -= MathHelper.sin(f) * 0.2F;
                this.zd += MathHelper.cos(f) * 0.2F;
            }
        }
        this.puff();
    }

    @Override
    public boolean getCanSpawnHere() {
        int x = MathHelper.floor_double(this.x);
        int y = MathHelper.floor_double(this.bb.minY);
        int z = MathHelper.floor_double(this.z);

        if (world.getBlock(x, y-1, z) == null) return false;
        return this.world.getBlock(x, y - 1, z).hasTag(AetherBlockTags.PASSIVE_MOBS_SPAWN);
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        if (isPassenger()) {
            entityplayer.ejectRider();
            this.setPos(entityplayer.x, entityplayer.y - 1, entityplayer.z);
            this.jump();
            this.puff();
        } else {
            this.startRiding(entityplayer);
        }
        return super.interact(entityplayer);
    }
}
