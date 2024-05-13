package bta.aether.entity;

import bta.aether.block.AetherBlocks;
import bta.aether.entity.projectiles.EntityZephyrSnowball;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityFlying;
import net.minecraft.core.entity.monster.IEnemy;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;

public class EntityZephyr extends EntityFlying implements IEnemy {
    public int courseChangeCooldown;
    public double waypointX;
    public double waypointY;
    public double waypointZ;
    private Entity targetedEntity;
    private int aggroCooldown;
    public int prevAttackCounter;
    public int attackCounter;

    public EntityZephyr(World world) {
        super(world);
        this.skinName = "zephyr";
        this.fireImmune = false;
        this.scoreValue = 1000;
        this.bb.expand(2.0, 3.0, 2.0);
        this.setSize(4.0f, 4.0f);
        this.courseChangeCooldown = 0;
        this.targetedEntity = null;
        this.aggroCooldown = 0;
        this.prevAttackCounter = 0;
        this.attackCounter = 0;
    }

    @Override
    protected void init() {
        super.init();
        this.entityData.define(16, (byte) 1);
    }

    @Override
    public void tick() {
        if (this.world.isClientSide) {
            byte i = this.entityData.getByte(16);
            if (i > 0 && this.attackCounter == 0) {
                this.world.playSoundAtEntity(null, this, "aether.sound.mobs.zephyr.zephyrCall", this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
            }
            this.attackCounter += i;
            if (this.attackCounter < 0) {
                this.attackCounter = 0;
            }
            if (this.attackCounter >= 20) {
                this.attackCounter = 20;
            }
            if (this.attackCounter == 20 && i == 0) {
                this.world.playSoundAtEntity(null, this, "aether.sound.mobs.zephyr.zephyrShoot", this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
                this.attackCounter = -40;
            }
        }
        super.tick();
    }

    protected void updatePlayerActionState() {
        if (!this.world.isClientSide && this.world.difficultySetting == 0) {
            this.remove();
        }

        this.tryToDespawn();
        this.prevAttackCounter = this.attackCounter;
        double d = this.waypointX - this.x;
        double d1 = this.waypointY - this.y;
        double d2 = this.waypointZ - this.z;
        double d3 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
        if (d3 < 1.0 || d3 > 60.0) {
            this.waypointX = this.x + (double)((this.random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.waypointY = this.y + (double)((this.random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.waypointZ = this.z + (double)((this.random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        }

        if (this.courseChangeCooldown-- <= 0) {
            this.courseChangeCooldown += this.random.nextInt(5) + 2;
            if (this.isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, d3)) {
                this.xd += d / d3 * 0.1;
                this.yd += d1 / d3 * 0.1;
                this.zd += d2 / d3 * 0.1;
            } else {
                this.waypointX = this.x;
                this.waypointY = this.y;
                this.waypointZ = this.z;
            }
        }

        if (this.targetedEntity != null && this.targetedEntity.removed) {
            this.targetedEntity = null;
        }

        if (this.targetedEntity == null || this.aggroCooldown-- <= 0) {
            this.targetedEntity = this.world.getClosestPlayerToEntity(this, 100.0);
            if (this.targetedEntity != null && !((EntityPlayer)this.targetedEntity).getGamemode().areMobsHostile()) {
                this.targetedEntity = null;
            }

            if (this.targetedEntity != null) {
                this.aggroCooldown = 20;
            }
        }

        double d4 = 64.0;
        if (this.targetedEntity != null && this.targetedEntity.distanceToSqr(this) < d4 * d4) {
            double d8 = 4.0;
            Vec3d vec3d = this.getViewVector(1.0F);
            double dX = this.targetedEntity.x - this.x;
            double dY = this.targetedEntity.y - this.y;
            double dZ = this.targetedEntity.z - this.z;
            double dist = MathHelper.sqrt_double(dX * dX + dY * dY + dZ * dZ);
            double vX = dX + this.targetedEntity.xd * dist / 7.5 - vec3d.xCoord * d8;
            double vY = dY + this.targetedEntity.yd * dist / 7.5 - ((double)(this.bbHeight / 2.0F) + 0.5);
            double vZ = dZ + this.targetedEntity.zd * dist / 7.5 - vec3d.zCoord * d8;
            this.renderYawOffset = this.yRot = -((float)Math.atan2(vX, vZ)) * 180.0F / 3.1415927F;
            if (this.canEntityBeSeen(this.targetedEntity)) {
                if (this.attackCounter == 10) {
                    this.world.playSoundAtEntity(null, this, "aether.sound.mobs.zephyr.zephyrCall", this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                }

                ++this.attackCounter;
                if (this.attackCounter == 20) {
                    this.world.playSoundAtEntity(null, this, "aether.sound.mobs.zephyr.zephyrShoot", this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                    EntityZephyrSnowball zephyrSnowball = new EntityZephyrSnowball(this.world, this, vX, vY, vZ);
                    zephyrSnowball.x = this.x + vec3d.xCoord * d8;
                    zephyrSnowball.y = this.y + (double)(this.bbHeight / 2.0F) + 0.5;
                    zephyrSnowball.z = this.z + vec3d.zCoord * d8;
                    this.world.entityJoinedWorld(zephyrSnowball);
                    this.attackCounter = -40;
                }
            } else if (this.attackCounter > 0) {
                --this.attackCounter;
            }
        } else {
            this.renderYawOffset = this.yRot = -((float)Math.atan2(this.xd, this.zd)) * 180.0F / 3.141593F;
            if (this.attackCounter > 0) {
                --this.attackCounter;
            }
        }

        if (!this.world.isClientSide) {
            byte byte0 = this.entityData.getByte(16);
            byte byte1 = (byte)(this.attackCounter <= 10 ? 0 : 1);
            if (byte0 != byte1) {
                this.entityData.set(16, byte1);
            }
        }

    }

    private boolean isCourseTraversable(double d, double d1, double d2, double d3) {
        double d4 = (this.waypointX - this.x) / d3;
        double d5 = (this.waypointY - this.y) / d3;
        double d6 = (this.waypointZ - this.z) / d3;
        AABB axisalignedbb = this.bb.copy();
        int i = 1;
        while ((double)i < d3) {
            axisalignedbb.offset(d4, d5, d6);
            if (!this.world.getCubes(this, axisalignedbb).isEmpty()) {
                return false;
            }
            ++i;
        }
        return true;
    }

    public boolean hurt(Entity attacker, int i, DamageType type) {
        if (super.hurt(attacker, i, type)) {
            if (this.passenger != attacker && this.vehicle != attacker) {
                if (attacker != this) {
                    this.targetedEntity = attacker;
                    this.aggroCooldown = 60;
                }

            }
            return true;
        } else {
            return false;
        }
    }

    public String getLivingSound() {
        return "aether:mobs.zephyr.zephyrcall";
    }

    @Override
    public String getHurtSound() {
        return "aether:mobs.zephyr.zephyrcall";
    }

    @Override
    public String getDeathSound() {
        return "aether:mobs.zephyr.zephyrcall";
    }

    public String getEntityTexture() {
        return "/assets/aether/mobs/" + this.skinName + "/" + this.getSkinVariant() + ".png";
    }
    @Override
    public int getSkinVariant() {
        int skinVariantCount = 1;
        return this.entityData.getByte(1) % skinVariantCount;
    }

    public int getDropItemId() {
        return AetherBlocks.aercloudWhite.id;
    }

    public float getSoundVolume() {
        return 3.0F;
    }

    public boolean getCanSpawnHere() {
        return this.random.nextInt(20) == 0 && super.getCanSpawnHere() && this.world.difficultySetting > 0;
    }

    public int getMaxSpawnedInChunk() {
        return 1;
    }
}
