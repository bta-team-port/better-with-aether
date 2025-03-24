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
        entityData.define(16, (byte) 1);
        y += 1;
    }

    @Override
    public void tick() {
        if (world.isClientSide) {
            byte i = entityData.getByte(16);
            if (i > 0 && attackCounter == 0) {
                world.playSoundAtEntity(null,
                        this,
                        "aether.sound.mobs.zephyr.zephyrCall",
                        getSoundVolume(),
                        (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f);
            }

            attackCounter += i;
            if (attackCounter < 0) {
                attackCounter = 0;
            }

            if (attackCounter >= 20) {
                attackCounter = 20;
            }

            if (attackCounter == 20 && i == 0) {
                world.playSoundAtEntity(null,
                        this,
                        "aether.sound.mobs.zephyr.zephyrShoot",
                        getSoundVolume(),
                        (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f);
                attackCounter = -40;
            }
        }

        super.tick();
    }

    protected void updatePlayerActionState() {
        if (!world.isClientSide && world.difficultySetting == 0) {
            remove();
        }

        tryToDespawn();
        prevAttackCounter = attackCounter;
        double nextX = waypointX - x;
        double nextY = waypointY - y;
        double nextZ = waypointZ - z;
        double nextWaypoint = MathHelper.sqrt_double(nextX * nextX + nextY * nextY + nextZ * nextZ);

        if ((nextWaypoint < 1.0 || nextWaypoint > 60.0)) {
            waypointX = x + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            waypointY = y + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            waypointZ = z + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        }

        if (courseChangeCooldown-- <= 0) {
            courseChangeCooldown += random.nextInt(10) + 2;

            if (isCourseTraversable(nextWaypoint)) {
                xd += nextX / nextWaypoint * 0.1;
                yd += nextY / nextWaypoint * 0.1;
                zd += nextZ / nextWaypoint * 0.1;
            } else {
                waypointX = x;
                waypointY = y;
                waypointZ = z;
            }
        }

        if (targetedEntity != null && targetedEntity.removed) {
            targetedEntity = null;
        }

        if (targetedEntity == null || aggroCooldown-- <= 0) {
            targetedEntity = world.getClosestPlayerToEntity(this, 100.0);
            if (targetedEntity != null && !((EntityPlayer)targetedEntity).getGamemode().areMobsHostile()) {
                targetedEntity = null;
            }

            if (targetedEntity != null) {
                aggroCooldown = 20;
            }
        }

        double radius = 64.0;
        if (targetedEntity != null && targetedEntity.distanceToSqr(this) < radius * radius) {
            double modifier = 4.0;
            Vec3d vec3d = getViewVector(1.0F);
            double dX = targetedEntity.x - x;
            double dY = targetedEntity.y - y;
            double dZ = targetedEntity.z - z;
            double dist = MathHelper.sqrt_double(dX * dX + dY * dY + dZ * dZ);
            double vX = dX + targetedEntity.xd * dist / 7.5 - vec3d.xCoord * modifier;
            double vY = dY + targetedEntity.yd * dist / 7.5 - ((double)(bbHeight / 2.0F) + 0.5);
            double vZ = dZ + targetedEntity.zd * dist / 7.5 - vec3d.zCoord * modifier;
            renderYawOffset = yRot = -((float)Math.atan2(vX, vZ)) * 180.0F / (float)Math.PI;

            if (canEntityBeSeen(targetedEntity)) {
                if (attackCounter == 10) {
                    world.playSoundAtEntity(null,
                            this,
                            "aether.sound.mobs.zephyr.zephyrCall",
                            getSoundVolume(),
                            (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
                }

                ++attackCounter;
                if (attackCounter == 20) {
                    this.world.playSoundAtEntity(null,
                            this,
                            "aether.sound.mobs.zephyr.zephyrShoot",
                            getSoundVolume(),
                            (random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);

                    EntityZephyrSnowball zephyrSnowball = new EntityZephyrSnowball(world, this, vX, vY, vZ);
                    zephyrSnowball.x = x + vec3d.xCoord * modifier;
                    zephyrSnowball.y = y + (double)(bbHeight / 2.0F) + 0.5;
                    zephyrSnowball.z = z + vec3d.zCoord * modifier;
                    world.entityJoinedWorld(zephyrSnowball);
                    attackCounter = -40;
                }
            } else if (attackCounter > 0) {
                --attackCounter;
            }
        } else {
            renderYawOffset = yRot = -((float)Math.atan2(xd, zd)) * 180.0F / (float) Math.PI;
            if (attackCounter > 0) {
                --attackCounter;
            }
        }

        if (!world.isClientSide) {
            byte byte0 = this.entityData.getByte(16);
            byte byte1 = (byte)(this.attackCounter <= 10 ? 0 : 1);
            if (byte0 != byte1) {
                this.entityData.set(16, byte1);
            }
        }
    }

    private boolean isCourseTraversable(double modifier) {
        double offX = (waypointX - this.x) / modifier;
        double offY = (waypointY - this.y) / modifier;
        double offZ = (waypointZ - this.z) / modifier;
        AABB aabb = bb.copy();

        for(int i = 1; (double)i < modifier; ++i) {
            aabb.offset(offX, offY, offZ);
            if (!world.getCubes(this, aabb).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public boolean hurt(Entity attacker, int i, DamageType type) {
        if (super.hurt(attacker, i, type)) {
            if (passenger != attacker && vehicle != attacker) {
                if (attacker != this) {
                    targetedEntity = attacker;
                    aggroCooldown = 60;
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
        return "/assets/aether/mobs/" + skinName + "/" + getSkinVariant() + ".png";
    }
    @Override
    public int getSkinVariant() {
        int skinVariantCount = 1;
        return entityData.getByte(1) % skinVariantCount;
    }

    public int getDropItemId() {
        return AetherBlocks.aercloudWhite.id;
    }

    public float getSoundVolume() {
        return 3.0F;
    }

    public boolean getCanSpawnHere() {
        return random.nextInt(20) == 0 && super.getCanSpawnHere() && world.difficultySetting > 0;
    }

    public int getMaxSpawnedInChunk() {
        return 1;
    }
}
