package teamport.aether.entity.animal.aerwhale;

import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.MobFlying;
import net.minecraft.core.entity.animal.AmbientCreature;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import teamport.aether.entity.MobUtil;
import turniplabs.halplibe.helper.EnvironmentHelper;

public class MobAerwhale extends MobFlying implements AmbientCreature {
    private double motionYaw;
    private double motionPitch;

    private long checkTime = 0L;

    private double checkX = 0.0;
    private double checkY = 0.0;
    private double checkZ = 0.0;

    private boolean isStuckWarning = false;

    public MobAerwhale(World world) {
        super(world);
        this.setSize(0.1F, 0.1F);
        this.viewScale = 100.0f;
        this.textureIdentifier = NamespaceID.getPermanent("aether", "aerwhale");
        this.fireImmune = true;
        this.moveSpeed = 0.5F;
        this.yRot = 360.0F * this.random.nextFloat();
        this.xRot = 90.0F * this.random.nextFloat() - 45.0F;
        this.ignoreFrustumCheck = true;
    }

    @Override
    public boolean hurt(Entity attacker, int damage, DamageType type) {
        if (attacker == null && type == null && damage == 100) {
            return MobUtil.killMob(this);
        }
        return false;
    }

    @SuppressWarnings("java:S131")
    @Override
    public void updateAI() {
        double[] distances = new double[]{
            this.openSpace(0.0F, 0.0F),
            this.openSpace(45.0F, 0.0F),
            this.openSpace(0.0F, 45.0F),
            this.openSpace(-45.0F, 0.0F),
            this.openSpace(0.0F, -45.0F)
        };

        int longest = 0;
        for (int distance = 1; distance < 5; ++distance) {
            if (distances[distance] > distances[longest]) {
                longest = distance;
            }
        }

        switch (longest) {
            case 0:
                if (distances[0] == 50.0) {
                    this.motionYaw *= 0.90F;
                    this.motionPitch *= 0.90F;

                    if (this.y > 225.0) {
                        this.motionPitch -= 2.0;
                    } else if (this.y < 180.0) {
                        this.motionPitch += 2.0;
                    }
                } else {
                    this.xRot = -this.xRot;
                    this.yRot = -this.yRot;
                }

                break;

            case 1:
                this.motionYaw += 5.0;
                break;

            case 2:
                this.motionPitch -= 5.0;
                break;

            case 3:
                this.motionYaw -= 5.0;
                break;

            case 4:
                this.motionPitch += 5.0;
        }
    }

    private static final int DATA_MOTION_YAW = 16;
    private static final int DATA_MOTION_PITCH = 17;

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_MOTION_YAW, 0, Integer.class);
        this.entityData.define(DATA_MOTION_PITCH, 0, Integer.class);
    }

    public void lerpPosAndRot() {
        if (this.newPosRotationIncrements > 0) {
            double lerpXD = this.x + (this.newPosX - this.x) / this.newPosRotationIncrements;
            double lerpYD = this.y + (this.newPosY - this.y) / this.newPosRotationIncrements;
            double lerpZD = this.z + (this.newPosZ - this.z) / this.newPosRotationIncrements;

            double lerpYRot = this.newRotationYaw - this.yRot;
            double lerpXRot = this.newRotationPitch - this.xRot;

            while (lerpYRot < -180.0) {
                lerpYRot += 360.0;
            }
            while (lerpYRot >= 180.0) {
                lerpYRot -= 360.0;
            }

            this.yRot = (float) (this.yRot + lerpYRot / this.newPosRotationIncrements);
            this.xRot = (float) (this.xRot + lerpXRot / this.newPosRotationIncrements);

            --this.newPosRotationIncrements;
            this.setPos(lerpXD, lerpYD, lerpZD);
            this.setRot(this.yRot, this.xRot);
        }
    }

    @Override
    public void tick() {
        if (!EnvironmentHelper.isClientWorld()) {
            this.updateAI();
            this.checkForBeingStuck();

            this.motionYaw += 2.0F * this.random.nextFloat() - 1.0F;
            this.motionPitch += 2.0F * this.random.nextFloat() - 1.0F;

            this.entityData.set(DATA_MOTION_YAW, Float.floatToIntBits((float) this.motionYaw));
            this.entityData.set(DATA_MOTION_PITCH, Float.floatToIntBits((float) this.motionPitch));
        } else {
            lerpPosAndRot();
            this.motionYaw = Float.intBitsToFloat(this.entityData.getInt(DATA_MOTION_YAW));
            this.motionPitch = Float.intBitsToFloat(this.entityData.getInt(DATA_MOTION_PITCH));
        }


        this.xRot = (float) (this.xRot + 0.1 * this.motionPitch);
        this.yRot = (float) (this.yRot + 0.1 * this.motionYaw);

        if (this.xRot < -60.0F) {
            this.xRot = -60.0F;
        }
        if (this.xRot > 60.0F) {
            this.xRot = 60.0F;
        }

        this.xRot = (float) (this.xRot * 0.99);

        this.xd += 0.005 * Math.cos(this.yRot / 180.0 * Math.PI) * Math.cos(this.xRot / 180.0 * Math.PI);
        this.yd += 0.005 * Math.sin(this.xRot / 180.0 * Math.PI);
        this.zd += 0.005 * Math.sin(this.yRot / 180.0 * Math.PI) * Math.cos(this.xRot / 180.0 * Math.PI);

        this.xd *= 0.98;
        this.yd *= 0.98;
        this.zd *= 0.98;

        int floorX = MathHelper.floor(this.x);
        int floorY = MathHelper.floor(this.bb.minY);
        int floorZ = MathHelper.floor(this.z);

        if (this.world != null) {
            if (this.xd > 0.0 && this.world.getBlockId(floorX + 1, floorY, floorZ) != 0) {
                this.xd = -this.xd;
                this.motionYaw -= 10.0;
            } else if (this.xd < 0.0 && this.world.getBlockId(floorX - 1, floorY, floorZ) != 0) {
                this.xd = -this.xd;
                this.motionYaw += 10.0;
            }

            if (this.yd > 0.0 && this.world.getBlockId(floorX, floorY + 1, floorZ) != 0) {
                this.yd = -this.yd;
                this.motionPitch -= 10.0;
            } else if (this.yd < 0.0 && this.world.getBlockId(floorX, floorY - 1, floorZ) != 0) {
                this.yd = -this.yd;
                this.motionPitch += 10.0;
            }

            if (this.zd > 0.0 && this.world.getBlockId(floorX, floorY, floorZ + 1) != 0) {
                this.zd = -this.zd;
                this.motionYaw -= 10.0;
            } else if (this.zd < 0.0 && this.world.getBlockId(floorX, floorY, floorZ - 1) != 0) {
                this.zd = -this.zd;
                this.motionYaw += 10.0;
            }
        }

        this.move(this.xd, this.yd, this.zd);
        super.baseTick();
    }

    @Override
    public String getLivingSound() {
        return "aether:mob.aerwhale.call";
    }

    @Override
    public String getHurtSound() {
        return "aether:mob.aerwhale.call";
    }

    @Override
    public String getDeathSound() {
        return "aether:mob.aerwhale.call";
    }

    @Override
    public float getSoundVolume() {
        return 3.0F;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }

    @Override
    public void spawnInit() {
        if (this.world != null && this.world.getBlockId((int) (this.x + 0.5), (int) (this.y + 25), (int) (this.z + 0.5)) == 0) {
            this.moveTo(this.x, this.y + 25, this.z, this.yRot, 0.0F);
        }
    }

    @Override
    public boolean canSpawnHere() {
        int x = MathHelper.floor(this.x);
        int y = MathHelper.floor(this.bb.minY);
        int z = MathHelper.floor(this.z);
        return this.world != null && this.world.checkIfAABBIsClear(this.bb) && this.world.getCollidingSolidBlockBoundingBoxes(this, this.bb).isEmpty() && !this.world.isAABBInMaterial(this.bb, Material.water) && this.world.getFullBlockLightValue(x, y, z) > 8;
    }

    public double openSpace(float rotationyRotOffset, float rotationPitchOffset) {
        if (this.world == null) return 50.0;
        float yRot = this.yRot + rotationyRotOffset;
        float pitch = this.xRot + rotationPitchOffset;
        Vec3 vec3d = Vec3.getTempVec3(this.x, this.y, this.z);
        float f3 = MathHelper.cos(-yRot * 0.01745329F - 3.141593F);
        float f4 = MathHelper.sin(-yRot * 0.01745329F - 3.141593F);
        float f5 = MathHelper.cos(-pitch * 0.01745329F);
        float f6 = MathHelper.sin(-pitch * 0.01745329F);
        float f7 = f4 * f5;
        float f9 = f3 * f5;
        double d3 = 50.0;
        Vec3 vec3d1 = vec3d.add(f7 * d3, f6 * d3, f9 * d3);
        HitResult movingobjectposition = this.world.checkBlockCollisionBetweenPoints(vec3d, vec3d1, true);
        if (movingobjectposition == null) {
            return 50.0;
        } else if (movingobjectposition.hitType == HitResult.HitType.TILE) {
            double i = movingobjectposition.x - this.x;
            double j = movingobjectposition.y - this.y;
            double k = movingobjectposition.z - this.z;
            return Math.sqrt(i * i + j * j + k * k);
        } else {
            return 50.0;
        }
    }

    public void checkForBeingStuck() {
        long currTime = System.currentTimeMillis();
        if (currTime > this.checkTime + 3000L) {
            double diffX = this.x - this.checkX;
            double diffY = this.y - this.checkY;
            double diffZ = this.z - this.checkZ;

            double distanceTravelled = Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ);

            if (distanceTravelled < 3.0) {
                if (!this.isStuckWarning) this.isStuckWarning = true;
                else this.remove();
            }

            this.checkX = this.x;
            this.checkY = this.y;
            this.checkZ = this.z;
            this.checkTime = currTime;
        }

    }
}
