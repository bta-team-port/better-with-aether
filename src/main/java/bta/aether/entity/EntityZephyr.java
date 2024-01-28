package bta.aether.entity;

import bta.aether.AetherBlockTags;
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
    public EntityZephyr(World world) {
        super(world);
        this.skinName = "zephyr";
        this.fireImmune = false;
        this.scoreValue = 1000;
        this.bb.expand(2.0, 3.0, 2.0);
    }

    public int courseChangeCooldown = 0;
    public double waypointX;
    public double waypointY;
    public double waypointZ;
    private Entity targetedEntity = null;
    private int aggroCooldown = 0;
    public int prevAttackCounter = 0;
    public int attackCounter = 0;

    @Override
    protected void init() {
        super.init();
        this.entityData.define(16, (byte) 1);
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
    public void tick() {
        if (this.world.isClientSide) {
            byte i = this.entityData.getByte(16);
            if (i > 0 && this.attackCounter == 0) {
                this.world.playSoundAtEntity(this, "aether.sound.mobs.zephyr.zephyrCall", this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
            }
            this.attackCounter += i;
            if (this.attackCounter < 0) {
                this.attackCounter = 0;
            }
            if (this.attackCounter >= 20) {
                this.attackCounter = 20;
            }
            if (this.attackCounter >= 20 && i == 0) {
                this.world.playSoundAtEntity(this, "aether.sound.mobs.zephyr.zephyrShoot", this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
                this.attackCounter = -40;
            }
        }
        super.tick();
    }

    @Override
    protected void updatePlayerActionState() {
        byte byte1;
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
            this.waypointX = this.x + (double)((this.random.nextFloat() * 2.0f - 1.0f) * 16.0f);
            this.waypointY = this.y + (double)((this.random.nextFloat() * 2.0f - 1.0f) * 16.0f);
            this.waypointZ = this.z + (double)((this.random.nextFloat() * 2.0f - 1.0f) * 16.0f);
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
            double d5 = this.targetedEntity.x - this.x;
            double d6 = this.targetedEntity.bb.minY + (double)(this.targetedEntity.bbHeight / 2.5f) - (this.y + (double)(this.bbHeight / 2.5f));
            double d7 = this.targetedEntity.z - this.z;
            this.renderYawOffset = this.yRot = -((float)Math.atan2(d5, d7)) * 180.0f / 3.141593f;
            if (this.canEntityBeSeen(this.targetedEntity)) {
                if (this.attackCounter == 10) {
                    this.world.playSoundAtEntity(this, "aether.sound.mobs.zephyr.zephyrCall", this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
                }
                ++this.attackCounter;
                if (this.attackCounter == 20) {

                    // no clue what this does, it does not fire snowballs correctly.
                    this.world.playSoundAtEntity(this, "aether.sound.mobs.zephyr.zephyrShoot", this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
                    EntityZephyrSnowball zephyrSnowball = new EntityZephyrSnowball(this.world, d5 += this.targetedEntity.x * (this.targetedEntity.xd * 0.5), d6 += this.targetedEntity.y * (this.targetedEntity.yd * 0.5), d7 += this.targetedEntity.z * (this.targetedEntity.zd * 0.5), 0);
                    double d8 = 4.0;
                    Vec3d vec3d = this.getViewVector(1.0f);
                    zephyrSnowball.x = this.x + vec3d.xCoord * d8;
                    zephyrSnowball.y = this.y + (double)(this.bbHeight / 2.0f) + 0.5;
                    zephyrSnowball.z = this.z + vec3d.zCoord * d8;
                    this.world.entityJoinedWorld(zephyrSnowball);
                    this.attackCounter = -40;
                }
            } else if (this.attackCounter > 0) {
                --this.attackCounter;
            }
        } else {
            this.renderYawOffset = this.yRot = -((float)Math.atan2(this.xd, this.zd)) * 180.0f / 3.141593f;
            if (this.attackCounter > 0) {
                --this.attackCounter;
            }
        }
        if (!this.world.isClientSide && this.entityData.getByte(16) != (byte1 = (byte)(this.attackCounter > 10 ? 1 : 0))) {
            this.entityData.set(16, byte1);
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
            if (this.world.getCubes(this, axisalignedbb).size() > 0) {
                return false;
            }
            ++i;
        }
        return true;
    }

    @Override
    public boolean hurt(Entity attacker, int i, DamageType type) {
        if (super.hurt(attacker, i, type)) {
            if (this.passenger == attacker || this.vehicle == attacker) {
                return true;
            }
            if (attacker != this) {
                this.targetedEntity = attacker;
                this.aggroCooldown = 60;
            }
            return true;
        }
        return false;
    }

    public String getEntityTexture() {
        return "/assets/aether/mobs/Zephyr.png";
    }

    public String getDefaultEntityTexture() {
        return "/assets/aether/mobs/Zephyr.png";
    }

    protected int getDropItemId() {
        return AetherBlocks.aercloudWhite.id;
    }

}
