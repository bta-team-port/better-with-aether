package teamport.aether.entity.animal.aerwhale;

import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.MobFlying;
import net.minecraft.core.entity.animal.AmbientCreature;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;

public class MobAerwhale extends MobFlying implements AmbientCreature {
    public int prevAttackCounter;
    public int attackCounter;
    public double motionYaw;
    public double motionPitch;
    public long checkTime = 0L;
    public double checkX = 0.0;
    public double checkY = 0.0;
    public double checkZ = 0.0;
    public boolean isStuckWarning = false;
    public int aggroCooldown;

    public MobAerwhale(World world) {
        super(world);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "aerwhale");
        this.viewScale = 4f;
        this.fireImmune = true;
        this.aggroCooldown = 0;
        this.prevAttackCounter = 0;
        this.attackCounter = 0;
        this.setSize(12.0F, 6.0F);
        this.speed = 0.5F;
        this.yRot = 360.0F * this.random.nextFloat();
        this.xRot = 90.0F * this.random.nextFloat() - 45.0F;
        this.ignoreFrustumCheck = true;
    }

    public void defineSynchedData() {
        this.entityData.define(16, (byte)0, Byte.class);
    }

    public void tick() {
        double[] distances = new double[]{this.openSpace(0.0F, 0.0F), this.openSpace(45.0F, 0.0F), this.openSpace(0.0F, 45.0F), this.openSpace(-45.0F, 0.0F), this.openSpace(0.0F, -45.0F)};
        int longest = 0;

        int i;
        for (i = 1; i < 5; ++i) {
            if (distances[i] > distances[longest]) {
                longest = i;
            }
        }

        if (random.nextInt(200) == 0) {
            playLivingSound();
        }

        switch (longest) {
            case 0:
                if (distances[0] == 50.0) {
                    this.motionYaw *= 0.8999999761581421;
                    this.motionPitch *= 0.8999999761581421;
                    if (this.y > 225.0) {
                        this.motionPitch -= 2.0;
                    }

                    if (this.y < 180.0) {
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

        this.motionYaw += 2.0F * this.random.nextFloat() - 1.0F;
        this.motionPitch += 2.0F * this.random.nextFloat() - 1.0F;
        this.xRot = (float) ((double) this.xRot + 0.1 * this.motionPitch);
        this.yRot = (float) ((double) this.yRot + 0.1 * this.motionYaw);
        if (this.xRot < -60.0F) {
            this.xRot = -60.0F;
        }

        if (this.xRot > 60.0F) {
            this.xRot = 60.0F;
        }

        this.xRot = (float) ((double) this.xRot * 0.99);
        this.xd += 0.005 * Math.cos((double) this.yRot / 180.0 * Math.PI) * Math.cos((double) this.xRot / 180.0 * Math.PI);
        this.yd += 0.005 * Math.sin((double) this.xRot / 180.0 * Math.PI);
        this.zd += 0.005 * Math.sin((double) this.yRot / 180.0 * Math.PI) * Math.cos((double) this.xRot / 180.0 * Math.PI);
        this.xd *= 0.98;
        this.yd *= 0.98;
        this.zd *= 0.98;
        i = MathHelper.floor(this.x);
        int j = MathHelper.floor(this.bb.minY);
        int k = MathHelper.floor(this.z);
        if (this.xd > 0.0 && this.world.getBlockId(i + 1, j, k) != 0) {
            this.xd = -this.xd;
            this.motionYaw -= 10.0;
        } else if (this.xd < 0.0 && this.world.getBlockId(i - 1, j, k) != 0) {
            this.xd = -this.xd;
            this.motionYaw += 10.0;
        }

        if (this.yd > 0.0 && this.world.getBlockId(i, j + 1, k) != 0) {
            this.yd = -this.yd;
            this.motionPitch -= 10.0;
        } else if (this.yd < 0.0 && this.world.getBlockId(i, j - 1, k) != 0) {
            this.yd = -this.yd;
            this.motionPitch += 10.0;
        }

        if (this.zd > 0.0 && this.world.getBlockId(i, j, k + 1) != 0) {
            this.zd = -this.zd;
            this.motionYaw -= 10.0;
        } else if (this.zd < 0.0 && this.world.getBlockId(i, j, k - 1) != 0) {
            this.zd = -this.zd;
            this.motionYaw += 10.0;
        }

        this.remainingFireTicks = 0;
        this.move(this.xd, this.yd, this.zd);
        this.checkForBeingStuck();
    }

    public double openSpace(float rotationyRotOffset, float rotationPitchOffset) {
        float yRot = this.yRot + rotationyRotOffset;
        float pitch = this.yRot + rotationyRotOffset;
        Vec3 vec3d = Vec3.getTempVec3(this.x, this.y, this.z);
        float f3 = MathHelper.cos(-yRot * 0.01745329F - 3.141593F);
        float f4 = MathHelper.sin(-yRot * 0.01745329F - 3.141593F);
        float f5 = MathHelper.cos(-pitch * 0.01745329F);
        float f6 = MathHelper.sin(-pitch * 0.01745329F);
        float f7 = f4 * f5;
        float f9 = f3 * f5;
        double d3 = 50.0;
        Vec3 vec3d1 = vec3d.add((double) f7 * d3, (double) f6 * d3, (double) f9 * d3);
        HitResult movingobjectposition = this.world.checkBlockCollisionBetweenPoints(vec3d, vec3d1, true);
        if (movingobjectposition == null) {
            return 50.0;
        } else if (movingobjectposition.hitType == HitResult.HitType.TILE) {
            double i = (double) movingobjectposition.x - this.x;
            double j = (double) movingobjectposition.y - this.y;
            double k = (double) movingobjectposition.z - this.z;
            return Math.sqrt(i * i + j * j + k * k);
        } else {
            return 50.0;
        }
    }

    public void updateAI() {
    }

    public void checkForBeingStuck() {
        long curtime = System.currentTimeMillis();
        if (curtime > this.checkTime + 3000L) {
            double diffx = this.x - this.checkX;
            double diffy = this.y - this.checkY;
            double diffz = this.z - this.checkZ;
            double distanceTravelled = Math.sqrt(diffx * diffx + diffy * diffy + diffz * diffz);
            if (distanceTravelled < 3.0) {
                if (!this.isStuckWarning) {
                    this.isStuckWarning = true;
                } else {
                    this.isRemoved();
                }
            }

            this.checkX = this.x;
            this.checkY = this.y;
            this.checkZ = this.z;
            this.checkTime = curtime;
        }

    }

    public String getLivingSound() {
        return "aether:mob.aerwhale.call";
    }

    public String getHurtSound() {
        return "aether:mob.aerwhale.call";
    }

    public String getDeathSound() {
        return "aether:mob.aerwhale.call";
    }

    public float getSoundVolume() {
        return 3.0F;
    }

    public int getMaxSpawnedInChunk() {
        return 1;
    }

    public void spawnInit() {
        this.moveTo(this.x, this.y + 25, this.z, this.yRot, 0.0F);
    }

    public boolean canSpawnHere() {
        int x = MathHelper.floor(this.x);
        int y = MathHelper.floor(this.bb.minY);
        int z = MathHelper.floor(this.z);
        return this.random.nextInt(65) == 0 && this.world.checkIfAABBIsClear(this.bb) && this.world.getCollidingSolidBlockBoundingBoxes(this, this.bb).isEmpty() && !this.world.isAABBInMaterial(this.bb, Material.water) && this.world.getFullBlockLightValue(x, y, z) > 8;
    }

}
