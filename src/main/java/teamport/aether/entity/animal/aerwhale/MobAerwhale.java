package teamport.aether.entity.animal.aerwhale;

import net.minecraft.core.block.material.Materials;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.MobFlying;
import net.minecraft.core.entity.animal.AmbientCreature;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import org.joml.Vector3d;
import teamport.aether.entity.MobUtil;

public class MobAerwhale extends MobFlying implements AmbientCreature {
    private static final double CRUISE_SPEED = 0.22;
    private static final double MAX_SPEED = 0.26;
    private static final double VELOCITY_RESPONSE = 0.1;
    private static final float MAX_YAW_STEP = 1.25F;
    private static final float MAX_PITCH_STEP = 0.6F;
    private static final float MAX_PITCH = 30.0F;
    private static final int PROBE_INTERVAL = 5;
    private static final int PROGRESS_INTERVAL = 80;

    private double waypointX;
    private double waypointY;
    private double waypointZ;
    private int waypointTicks;
    private int probeCooldown;
    private float avoidanceYaw;
    private float avoidancePitch;
    private double progressX;
    private double progressY;
    private double progressZ;
    private int progressTicks;

    private float renderYaw;
    private float renderYawO;
    private float renderPitch;
    private float renderPitchO;
    private boolean renderRotationInitialized;

    public MobAerwhale(World world) {
        super(world);
        this.setSize(0.1F, 0.1F);
        this.viewScale = 100.0f;
        this.setTextureIdentifier("aether", "aerwhale");
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

    @Override
    @SuppressWarnings("java:S131")
    public void updateAI() {
        if (this.world.isClientSide) return;

        double waypointDistance = this.distanceToSqr(this.waypointX, this.waypointY, this.waypointZ);
        if (--this.waypointTicks <= 0 || waypointDistance < 64.0) {
            this.chooseWaypoint();
        }

        if (--this.probeCooldown <= 0) {
            this.probeCooldown = PROBE_INTERVAL;
            this.updateAvoidance();
        }

        double dx = this.waypointX - this.x;
        double dy = this.waypointY - this.y;
        double dz = this.waypointZ - this.z;
        double horizontalDistance = Math.sqrt(dx * dx + dz * dz);
        float targetYaw = (float) Math.toDegrees(Math.atan2(-dx, dz)) + this.avoidanceYaw;
        float targetPitch = (float) -Math.toDegrees(Math.atan2(dy, horizontalDistance)) + this.avoidancePitch;
        targetPitch = MathHelper.clamp(targetPitch, -MAX_PITCH, MAX_PITCH);

        this.yRot = approachDegrees(this.yRot, targetYaw, MAX_YAW_STEP);
        this.xRot = approach(this.xRot, targetPitch, MAX_PITCH_STEP);
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
        super.baseTick();

        if (this.world.isClientSide) {
            this.lerpPosAndRot();
            this.updateRenderRotation();
            return;
        }

        this.updateAI();

        double yawRadians = Math.toRadians(this.yRot);
        double pitchRadians = Math.toRadians(this.xRot);
        double pitchCos = Math.cos(pitchRadians);
        double targetXd = -Math.sin(yawRadians) * pitchCos * CRUISE_SPEED;
        double targetYd = -Math.sin(pitchRadians) * CRUISE_SPEED;
        double targetZd = Math.cos(yawRadians) * pitchCos * CRUISE_SPEED;

        this.xd += (targetXd - this.xd) * VELOCITY_RESPONSE;
        this.yd += (targetYd - this.yd) * VELOCITY_RESPONSE;
        this.zd += (targetZd - this.zd) * VELOCITY_RESPONSE;
        this.clampVelocity();
        this.move(this.xd, this.yd, this.zd);

        if (this.horizontalCollision || this.verticalCollision) {
            this.xd *= 0.35;
            this.yd *= 0.35;
            this.zd *= 0.35;
            this.waypointTicks = 0;
            this.probeCooldown = 0;
        }

        this.checkForBeingStuck();
        if (this.tickCount % 3 == 0) {
            this.entityData.set(DATA_MOTION_YAW, Float.floatToIntBits(this.yRot));
            this.entityData.set(DATA_MOTION_PITCH, Float.floatToIntBits(this.xRot));
        }
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
    public boolean canSpawnHere() {
        return this.world.checkIfAABBIsClear(this.bb) && this.world.getCollidingSolidBlockBoundingBoxes(this, this.bb).isEmpty() && !this.world.isAABBInMaterial(this.bb, Materials.WATER);
    }

    public double openSpace(float rotationyRotOffset, float rotationPitchOffset) {
        float yRot = this.yRot + rotationyRotOffset;
        float pitch = this.xRot + rotationPitchOffset;
        Vector3d vec3d = new Vector3d(this.x, this.y, this.z);
        float f3 = MathHelper.cos(-yRot * 0.01745329F - 3.141593F);
        float f4 = MathHelper.sin(-yRot * 0.01745329F - 3.141593F);
        float f5 = MathHelper.cos(-pitch * 0.01745329F);
        float f6 = MathHelper.sin(-pitch * 0.01745329F);
        float f7 = f4 * f5;
        float f9 = f3 * f5;
        double d3 = 50.0;
        Vector3d vec3d1 = new Vector3d(vec3d.x() + f7 * d3, vec3d.y() + f6 * d3, vec3d.z() + f9 * d3);
        HitResult movingobjectposition = this.world.checkBlockCollisionBetweenPoints(vec3d, vec3d1, true);
        if (movingobjectposition == null) {
            return 50.0;
        } else if (movingobjectposition instanceof HitResult.Tile) {
            double i = movingobjectposition.location.x() - this.x;
            double j = movingobjectposition.location.y() - this.y;
            double k = movingobjectposition.location.z() - this.z;
            return Math.sqrt(i * i + j * j + k * k);
        } else {
            return 50.0;
        }
    }

    public void checkForBeingStuck() {
        if (++this.progressTicks < PROGRESS_INTERVAL) return;
        double dx = this.x - this.progressX;
        double dy = this.y - this.progressY;
        double dz = this.z - this.progressZ;
        if (dx * dx + dy * dy + dz * dz < 4.0) {
            this.waypointTicks = 0;
            this.avoidanceYaw = this.random.nextBoolean() ? 70.0F : -70.0F;
        }
        this.progressX = this.x;
        this.progressY = this.y;
        this.progressZ = this.z;
        this.progressTicks = 0;
    }

    private void chooseWaypoint() {
        float heading = this.yRot + (this.random.nextFloat() - 0.5F) * 120.0F;
        double distance = 32.0 + this.random.nextDouble() * 24.0;
        this.waypointX = this.x - Math.sin(Math.toRadians(heading)) * distance;
        this.waypointY = MathHelper.clamp(202.5 + (this.random.nextDouble() - 0.5) * 36.0, 180.0, 225.0);
        this.waypointZ = this.z + Math.cos(Math.toRadians(heading)) * distance;
        this.waypointTicks = 100 + this.random.nextInt(80);
    }

    private void updateAvoidance() {
        if (this.openSpace(0.0F, 0.0F) >= 12.0) {
            this.avoidanceYaw *= 0.6F;
            this.avoidancePitch *= 0.6F;
            return;
        }

        float[] yawOffsets = {35.0F, -35.0F, 70.0F, -70.0F, 0.0F, 0.0F};
        float[] pitchOffsets = {0.0F, 0.0F, 0.0F, 0.0F, 20.0F, -20.0F};
        double bestDistance = -1.0;
        int best = 0;
        for (int i = 0; i < yawOffsets.length; i++) {
            double distance = this.openSpace(yawOffsets[i], pitchOffsets[i]);
            if (distance > bestDistance) {
                bestDistance = distance;
                best = i;
            }
        }
        this.avoidanceYaw = yawOffsets[best];
        this.avoidancePitch = pitchOffsets[best];
    }

    private void clampVelocity() {
        double speed = Math.sqrt(this.xd * this.xd + this.yd * this.yd + this.zd * this.zd);
        if (speed <= MAX_SPEED) return;
        this.xd = this.xd / speed * MAX_SPEED;
        this.yd = this.yd / speed * MAX_SPEED;
        this.zd = this.zd / speed * MAX_SPEED;
    }

    private void updateRenderRotation() {
        float targetYaw = Float.intBitsToFloat(this.entityData.getInt(DATA_MOTION_YAW));
        float targetPitch = Float.intBitsToFloat(this.entityData.getInt(DATA_MOTION_PITCH));
        if (!this.renderRotationInitialized) {
            this.renderYaw = this.renderYawO = targetYaw;
            this.renderPitch = this.renderPitchO = targetPitch;
            this.renderRotationInitialized = true;
            return;
        }
        this.renderYawO = this.renderYaw;
        this.renderPitchO = this.renderPitch;
        this.renderYaw += MathHelper.wrapDegrees(targetYaw - this.renderYaw) * 0.35F;
        this.renderPitch += (targetPitch - this.renderPitch) * 0.35F;
    }

    public float getRenderYaw(float partialTick) {
        if (!this.renderRotationInitialized) return MathHelper.lerp(this.yRotO, this.yRot, partialTick);
        return this.renderYawO + MathHelper.wrapDegrees(this.renderYaw - this.renderYawO) * partialTick;
    }

    public float getRenderPitch(float partialTick) {
        if (!this.renderRotationInitialized) return MathHelper.lerp(this.xRotO, this.xRot, partialTick);
        return MathHelper.lerp(this.renderPitchO, this.renderPitch, partialTick);
    }

    private static float approachDegrees(float current, float target, float step) {
        return current + MathHelper.clamp(MathHelper.wrapDegrees(target - current), -step, step);
    }

    private static float approach(float current, float target, float step) {
        return current + MathHelper.clamp(target - current, -step, step);
    }
}
