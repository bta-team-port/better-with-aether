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

public class MobAerwhale extends MobFlying implements AmbientCreature {
    public double motionYaw;
    public double motionPitch;

    public long checkTime = 0L;

    public double checkX = 0.0;
    public double checkY = 0.0;
    public double checkZ = 0.0;

    public boolean isStuckWarning = false;

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
        return false;
    }

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

    @Override
    public void tick() {
        if (this.newPosRotationIncrements > 0) {
            double lerpXD = this.x + (this.newPosX - this.x) / (double) this.newPosRotationIncrements;
            double lerpYD = this.y + (this.newPosY - this.y) / (double) this.newPosRotationIncrements;
            double lerpZD = this.z + (this.newPosZ - this.z) / (double) this.newPosRotationIncrements;

            double lerpYRot = this.newRotationYaw - (double) this.yRot;
            double lerpXRot = this.newRotationPitch - (double) this.xRot;

            while (lerpYRot < (double) -180.0F) {
                lerpYRot += 360.0F;
            }
            while (lerpYRot >= (double) 180.0F) {
                lerpYRot -= 360.0F;
            }

            this.yRot = (float) ((double) this.yRot + lerpYRot / (double) this.newPosRotationIncrements);
            this.xRot = (float) ((double) this.xRot + lerpXRot / (double) this.newPosRotationIncrements);

            --this.newPosRotationIncrements;
            this.setPos(lerpXD, lerpYD, lerpZD);
            this.setRot(this.yRot, this.xRot);
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

        int floorX = MathHelper.floor(this.x);
        int floorY = MathHelper.floor(this.bb.minY);
        int floorZ = MathHelper.floor(this.z);

        assert world != null; // shutupintelij

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

        this.move(this.xd, this.yd, this.zd);
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
        if (world.getBlockId((int) (this.x + 0.5), (int) (this.y + 25), (int) (this.z + 0.5)) == 0) {
            this.moveTo(this.x, this.y + 25, this.z, this.yRot, 0.0F);
        }
    }

    @Override
    public boolean canSpawnHere() {
        int x = MathHelper.floor(this.x);
        int y = MathHelper.floor(this.bb.minY);
        int z = MathHelper.floor(this.z);
        return this.world.checkIfAABBIsClear(this.bb) && this.world.getCollidingSolidBlockBoundingBoxes(this, this.bb).isEmpty() && !this.world.isAABBInMaterial(this.bb, Material.water) && this.world.getFullBlockLightValue(x, y, z) > 8;
    }

    public double openSpace(float rotationyRotOffset, float rotationPitchOffset) {
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
}
