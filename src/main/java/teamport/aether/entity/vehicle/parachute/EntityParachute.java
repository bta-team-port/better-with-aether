package teamport.aether.entity.vehicle.parachute;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.ICollidable;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.joml.primitives.AABBdc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.block.AetherBlocks;
import teamport.aether.helper.ParticleMaker;

public class EntityParachute extends Entity implements ICollidable {
    public double maxSpeed;
    public double acceleration;
    public float MAX_PACKET_SPEED_CHANGE;
    public float MAX_PACKET_ROTATION_CHANGE = 120.0F;

    private int lerpTicks;
    private double lerpX;
    private double lerpY;
    private double lerpZ;
    private float lerpYRot;
    private float lerpXRot;
    private double velocityX;
    private double velocityY;
    private double velocityZ;

    private float pendingYRot;
    private double pendingXDChange;
    private double pendingZDChange;

    protected String pathParticle = "explode";
    protected Block<?> particleBlock = AetherBlocks.AERCLOUD_WHITE;

    public EntityParachute(World world) {
        super(world);
        this.blocksBuilding = true;
        this.setSize(1.0F, 1.0F);
        this.sendAdditionalData = true;

        this.maxSpeed = 0.25;
        this.acceleration = 0.025;
        this.MAX_PACKET_SPEED_CHANGE = 0.052F;
    }

    public EntityParachute(@NonNull World world, double x, double y, double z) {
        this(world);
        this.setPos(x, y + (double) this.heightOffset, z);
        this.xd = 0.0;
        this.yd = 0.0;
        this.zd = 0.0;
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    public @NonNull String getEntityTexture() {
        return "/assets/aether/textures/entity/parachute.png";
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected boolean makeStepSound() {
        return false;
    }

    @Override
    public @Nullable AABBdc getCollisionAABB() {
        return this.bb;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public boolean isPickable() {
        return !this.removed;
    }

    @Override
    public double getRideHeight() {
        return this.bbHeight;
    }

    @Override
    public float getShadowHeightOffs() {
        return 0.0F;
    }

    @Override
    protected void causeFallDamage(float distance) {
    }

    @Override
    public void readAdditionalSaveData(@NonNull CompoundTag compoundTag) {
    }

    @Override
    public void addAdditionalSaveData(@NonNull CompoundTag compoundTag) {
    }

    @Override
    public boolean hurt(Entity entity, int damage, DamageType type) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();

        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        double pX = this.x + (this.random.nextDouble() * 0.75 * 2.0 - 0.75);
        double pY = this.bb.minY - 0.5 + (this.random.nextDouble() * 0.75 * 2.0 - 0.75);
        double pZ = this.z + (this.random.nextDouble() * 0.75 * 2.0 - 0.75);
        ParticleMaker.spawnParticle(this.world, this.pathParticle, pX, pY, pZ, 0.0, 0.0, 0.0, 0);

        if (!this.world.isClientSide) {
            if (this.passenger == null) {
                this.breakParachute();
                this.remove();
                return;
            }

            if (this.onGround || this.isInWater()) {
                this.breakParachute();
                this.remove();
                return;
            }
        }

        if (this.world.isClientSide) {
            if (this.lerpTicks > 0) {
                double lx = this.x + (this.lerpX - this.x) / (double) this.lerpTicks;
                double ly = this.y + (this.lerpY - this.y) / (double) this.lerpTicks;
                double lz = this.z + (this.lerpZ - this.z) / (double) this.lerpTicks;
                this.xRot += (this.lerpXRot - this.xRot) / (float) this.lerpTicks;
                if (this.passenger == null || this.passenger.lerpVehicleMotion()) {
                    float lyRot = MathHelper.normalizeRotation(this.lerpYRot - this.yRot);
                    this.yRot += lyRot / (float) this.lerpTicks;
                }
                --this.lerpTicks;
                this.setPos(lx, ly, lz);
            }

            this.parachuteMovement();
            this.move(this.xd, this.yd, this.zd);
            this.setRot(this.yRot, this.xRot);
        } else {
            if (this.passenger != null) {
                this.passenger.handleSpecialVehicleControl();
            }

            this.parachuteMovement();
            this.move(this.xd, this.yd, this.zd);

            if (this.passenger != null) {
                this.passenger.sendSpecialVehiclePacket();
            }
        }
    }

    private void parachuteMovement() {
        this.yd -= 0.008;

        if (this.yd < -0.2) {
            this.yd = -0.2;
        }

        if (this.passenger != null) {
            this.passenger.handleSpecialVehicleControl();
        }

        this.yRot += this.pendingYRot;
        this.pendingYRot = 0.0F;

        this.xd += this.pendingXDChange;
        this.zd += this.pendingZDChange;
        this.pendingXDChange = 0.0;
        this.pendingZDChange = 0.0;

        this.xd *= 0.9;
        this.zd *= 0.9;

        double currentSpeed = Math.hypot(this.xd, this.zd);
        if (currentSpeed > this.maxSpeed) {
            double scale = this.maxSpeed / currentSpeed;
            this.xd *= scale;
            this.zd *= scale;
        }

        if (this.passenger != null) {
            this.passenger.sendSpecialVehiclePacket();
        }
    }

    public void controlParachute(float forward, float strafe) {
        if (this.passenger != null) {
            this.yRot = this.passenger.yRot;
        }

        double yawRad = Math.toRadians(this.yRot);

        double forwardX = Math.sin(yawRad) * forward;
        double forwardZ = -Math.cos(yawRad) * forward;

        double strafeX = -Math.cos(yawRad) * strafe;
        double strafeZ = -Math.sin(yawRad) * strafe;

        this.pendingXDChange = (forwardX + strafeX) * acceleration;
        this.pendingZDChange = (forwardZ + strafeZ) * acceleration;
    }

    @Override
    public boolean relaysVehicleControl() {
        return true;
    }

    @Override
    public void handleControlDirect(double xd, double yd, double zd, float yRot) {
        this.yRot = (float) MathHelper.clamp(yRot, (double) this.yRot - MAX_PACKET_ROTATION_CHANGE, (double) this.yRot + MAX_PACKET_ROTATION_CHANGE);
        this.xd = MathHelper.clamp(xd, this.xd - MAX_PACKET_SPEED_CHANGE, this.xd + MAX_PACKET_SPEED_CHANGE);
        this.zd = MathHelper.clamp(zd, this.zd - MAX_PACKET_SPEED_CHANGE, this.zd + MAX_PACKET_SPEED_CHANGE);
    }

    @Override
    public void lerpTo(double x, double y, double z, float yRot, float xRot, int i) {
        this.lerpX = x;
        this.lerpY = y;
        this.lerpZ = z;
        this.lerpYRot = yRot;
        this.lerpXRot = xRot;
        this.lerpTicks = i;
        if (this.passenger == null || this.passenger.lerpVehicleMotion()) {
            this.xd = this.velocityX;
            this.yd = this.velocityY;
            this.zd = this.velocityZ;
        }
    }

    @Override
    public void lerpMotion(double xd, double yd, double zd) {
        if (this.passenger == null || this.passenger.lerpVehicleMotion()) {
            this.velocityX = this.xd = xd;
            this.velocityY = this.yd = yd;
            this.velocityZ = this.zd = zd;
        }
    }

    @Override
    public void positionRider() {
        if (this.passenger != null) {
            this.passenger.setPos(this.x, this.y + this.getRideHeight() + this.passenger.getRidingHeight(), this.z);
        }
    }

    @Override
    public boolean interact(@NonNull Player player) {
        if ((!(this.passenger instanceof Player) || this.passenger == player) && !this.world.isClientSide) {
            player.startRiding(this);
        }
        return false;
    }

    @Override
    public Entity ejectRider() {
        Entity entity = this.passenger;
        if (entity == null) {
            return null;
        }
        this.passenger = null;
        entity.vehicle = null;
        return entity;
    }

    public void breakParachute() {
        for (int i = 0; i < 16 + this.random.nextInt(10); i++) {
            float faceX = this.bbWidth * this.random.nextFloat();
            float faceY = this.bbWidth * this.random.nextFloat();

            float posX;
            float posY;
            float posZ;
            Direction dir = Direction.all[this.random.nextInt(Direction.all.length)];
            switch (dir) {
                case WEST:
                    posX = (float) this.x;
                    posY = (float) (this.y + faceY);
                    posZ = (float) (this.z + faceX);
                    break;
                case EAST:
                    posX = (float) (this.x + 1);
                    posY = (float) (this.y + faceY);
                    posZ = (float) (this.z + faceX);
                    break;
                case SOUTH:
                    posX = (float) (this.x + faceX);
                    posY = (float) (this.y + faceY);
                    posZ = (float) (this.z + 1);
                    break;
                case NORTH:
                    posX = (float) (this.x + faceX);
                    posY = (float) (this.y + faceY);
                    posZ = (float) this.z;
                    break;
                case DOWN:
                    posX = (float) (this.x + faceX);
                    posY = (float) (this.y - 1);
                    posZ = (float) (this.z + faceY);
                    break;
                default:
                    posX = (float) (this.x + faceX);
                    posY = (float) (this.y + 1);
                    posZ = (float) (this.z + faceY);
                    break;
            }

            ParticleMaker.spawnParticle(this.world, "block", posX - 0.5F, posY + 0.25F, posZ - 0.5F, 0, 0.005, 0, this.particleBlock.id());
        }

        this.world.playBlockSoundEffect(null, this.x, this.y, this.z, this.particleBlock, EnumBlockSoundEffectType.MINE);
    }

    public String getPathParticle() {
        return this.pathParticle;
    }
}
