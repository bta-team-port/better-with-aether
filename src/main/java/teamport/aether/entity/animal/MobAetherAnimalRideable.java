package teamport.aether.entity.animal;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import org.jspecify.annotations.NonNull;
import teamport.aether.entity.interfaces.AetherJumpAmount;

public class MobAetherAnimalRideable extends MobAetherAnimal implements AetherJumpAmount {
    public static final int DATA_SADDLE_ID = 16;
    public static final int DATA_SIT_ID = 17;
    protected float speedProgress = 0.0F;
    private final @NonNull TilePos queryPos = new TilePos();
    private double controlTargetX;
    private double controlTargetY;
    private double controlTargetZ;
    private boolean hasControlTarget;
    private float pendingFallDistance;
    private boolean wasGrounded;
    public boolean airborneFromJump;
    private static final float WALL_HIT_SPEED_LOSS = 0.12F;
    protected float accelerationRate;

    private boolean sitWhenBackOnLand = false;
    public float lookFadeAlpha = 1.0F;
    public long lookFadeLastRenderNanos = 0L;

    protected int jumpsRemaining;
    protected int maxJumps = 3;
    protected boolean jumpPressed;

    public MobAetherAnimalRideable(World world) {
        super(world);
        this.jumpsRemaining = getJumpMaxAmount();
    }

    @Override
    protected void dropDeathItems() {
        if (this.getSaddled()) {
            this.dropItem(Items.SADDLE.id, 1);
            this.setSaddled(false);
        }

        super.dropDeathItems();
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_SADDLE_ID, (byte) 0, Byte.class);
        this.entityData.define(DATA_SIT_ID, (byte) 0, Byte.class);
    }

    @Override
    public void addAdditionalSaveData(@NonNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Saddle", this.getSaddled());
        tag.putBoolean("Sit", this.getSitting());
    }

    @Override
    public void readAdditionalSaveData(@NonNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setSaddled(tag.getBoolean("Saddle"));
        this.setSitting(tag.getBoolean("Sit"));
    }

    @Override
    public boolean save(@NonNull CompoundTag tag) {
        return !(this.passenger instanceof Player) && super.save(tag);
    }

    @Override
    public void setPassenger(Entity passenger) {
        super.setPassenger(passenger);
        this.hasControlTarget = false;
        this.airborneFromJump = false;
        if (passenger != null) {
            this.setSitting(false);
            this.sitWhenBackOnLand = false;
        }

        if (passenger == null) {
            this.xd = 0.0F;
            this.yd = 0.0F;
            this.zd = 0.0F;
            this.speedProgress = 0.0F;
            this.fallDistance = 0.0F;
            if (this.getSaddled()) {
                this.setSitting(true);
            }
        }

    }

    @Override
    public void onLivingUpdate() {
        Entity var2 = this.passenger;
        if (var2 instanceof Player driver) {
            if (!driver.lerpVehicleMotion()) {
                this.newPosRotationIncrements = 0;
                boolean wasMultiplayerEntity = this.isMultiplayerEntity;
                boolean wasLocallySimulated = this.locallySimulated;
                this.isMultiplayerEntity = false;
                this.locallySimulated = true;
                super.onLivingUpdate();
                this.isMultiplayerEntity = wasMultiplayerEntity;
                this.locallySimulated = wasLocallySimulated;
                driver.sendSpecialVehiclePacket();
                return;
            }

            if (!this.world.isClientSide) {
                this.target = null;
                this.pathToEntity = null;
                this.yBodyRot = this.yRot;
                this.xRot = driver.xRot;
                this.fallDistance = 0.0F;
                if (this.hasControlTarget) {
                    double trueY = this.bb.minY + (double) this.heightOffset;
                    double dx = MathHelper.clamp(this.controlTargetX - this.x, -1.0F, 1.0F);
                    double dy = MathHelper.clamp(this.controlTargetY - trueY, -2.0F, 2.0F);
                    double dz = MathHelper.clamp(this.controlTargetZ - this.z, -1.0F, 1.0F);
                    this.move(dx, dy, dz);
                }

                this.xd = 0.0F;
                this.yd = 0.0F;
                this.zd = 0.0F;
                return;
            }
        }

        super.onLivingUpdate();
    }

    @Override
    public void handleControlDirect(double x, double y, double z, float yRot) {
        this.yRot = yRot;
        this.yBodyRot = yRot;
        this.controlTargetX = x;
        this.controlTargetY = y;
        this.controlTargetZ = z;
        this.hasControlTarget = true;
    }

    @Override
    public boolean isInWall() {
        return (this.world.isClientSide || !(this.passenger instanceof Player)) && super.isInWall();
    }

    @Override
    public void move(double dx, double dy, double dz) {
        if (!this.hasNoPhysics()) {
            Entity var8 = this.passenger;
            if (var8 instanceof Player rider && rider.bb.maxY > this.bb.maxY) {
                double pigHeight = this.bb.maxY - this.bb.minY;
                this.bb.maxY = rider.bb.maxY;
                super.move(dx, dy, dz);
                this.bb.maxY = this.bb.minY + pigHeight;
                return;
            }
        }

        super.move(dx, dy, dz);
    }

    @Override
    protected boolean canDespawn() {
        return super.canDespawn() && !this.getSaddled();
    }


    @Override
    public boolean passesThroughRiderTargeting(ItemStack heldItem) {
        boolean canFeed = heldItem != null && isFeedableItem(heldItem) && this.getHealth() < this.getMaxHealth();
        return !canFeed;
    }

    public boolean isFeedableItem(ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean hurt(Entity attacker, int damage, DamageType type) {
        boolean hurt = super.hurt(attacker, damage, type);
        if (hurt) {
            this.speedProgress = 0.0F;
            if (!this.world.isClientSide && this.passenger == null && (this.getSitting())) {
                this.setSitting(false);
            }
        }

        return hurt;
    }

    @Override
    public void handleEntityEvent(byte event, float attackedAtYaw) {
        super.handleEntityEvent(event, attackedAtYaw);
        if (event == 2) {
            this.speedProgress = 0.0F;
        }
    }

    @Override
    protected void jump() {
        super.jump();
        if (this.passenger instanceof Player) {
            this.airborneFromJump = true;
        }
    }

    @Override
    public void fling(double xd, double yd, double zd, float pushTime) {
        super.fling(xd, yd, zd, pushTime);
        if (!this.world.isClientSide) {
            Entity var9 = this.passenger;
            if (var9 instanceof Player rider) {
                rider.onVehicleFlung(this);
            }
        }
    }

    @Override
    public Entity ejectRider() {
        Entity rider = super.ejectRider();
        this.xd = 0.0F;
        this.yd = 0.0F;
        this.zd = 0.0F;
        this.speedProgress = 0.0F;
        this.fallDistance = 0.0F;
        this.airborneFromJump = false;
        if (this.getSaddled()) {
            this.setSitting(true);
        }

        return rider;
    }

    public boolean getSaddled() {
        return (this.entityData.getByte(DATA_SADDLE_ID) & 1) != 0;
    }

    public void setSaddled(boolean flag) {
        if (flag) {
            this.entityData.set(DATA_SADDLE_ID, (byte) 1);
        } else {
            this.entityData.set(DATA_SADDLE_ID, (byte) 0);
        }
    }

    public boolean getSitting() {
        return (this.entityData.getByte(DATA_SIT_ID) & 1) != 0;
    }

    public void setSitting(boolean flag) {
        this.entityData.set(DATA_SIT_ID, (byte) (flag ? 1 : 0));
        if (flag) {
            this.xd = 0.0F;
            this.zd = 0.0F;
            this.moveForward = 0.0F;
            this.moveStrafing = 0.0F;
            this.pathToEntity = null;
        }
    }

    @Override
    public boolean interact(@NonNull Player player) {
        if (super.interact(player)) {
            return true;
        } else {
            ItemStack heldItem = player.getHeldItem();
            if (heldItem != null && (isFeedableItem(heldItem)) && this.getHealth() < this.getMaxHealth() && heldItem.consumeItem(player)) {
                if (heldItem.stackSize <= 0) {
                    player.setHeldItem(null);
                }

                this.heal(4);
                this.world.playSoundAtEntity(player, this, "random.bite", 0.2F + (this.random.nextFloat() - this.random.nextFloat()) * 0.1F, 0.8F + (this.random.nextFloat() - this.random.nextFloat()) * 0.1F);
                return true;
            } else if (this.passenger == player) {
                return false;
            } else if (!this.getSaddled()) {
                return false;
            } else if (this.passenger != null) {
                return false;
            } else if (this.world.isClientSide) {
                return true;
            } else {
                if (player.isSneaking()) {
                    this.setSaddled(false);
                    this.setSitting(false);
                    ItemStack toInsert = new ItemStack(Items.SADDLE);
                    player.inventory.insertItem(toInsert, true);
                    if (toInsert.stackSize > 0) {
                        this.dropItem(toInsert, 0.0F);
                    }
                } else {
                    player.startRiding(this);
                }

                return true;
            }
        }
    }

    @Override
    protected boolean isMovementCeased() {
        return this.getSitting() || super.isMovementCeased();
    }

    @Override
    protected void causeFallDamage(float distance) {
        Entity var3 = this.passenger;
        if (var3 instanceof Player driver) {
            if (this.world.isClientSide) {
                this.pendingFallDistance = distance;
            } else if (!driver.lerpVehicleMotion()) {
                this.applyRiderFallDamage(distance);
            }

        } else {
            super.causeFallDamage(distance);
        }
    }

    public void applyRiderFallDamage(float distance) {
        if (!this.world.isClientSide) {
            int damage = (int) Math.ceil(distance - 3.0F);
            if (damage > 0) {
                this.hurt(null, damage, DamageType.FALL);
            }
        }
    }

    @Override
    public int getJumpMaxAmount() {
        return maxJumps;
    }

    @Override
    public int getJumpAmount() {
        return jumpsRemaining;
    }

    @Override
    protected void updateAI() {
        if (this.passenger instanceof Player player) {
            this.target = null;
            this.pathToEntity = null;
            this.moveForward = 0.0F;
            this.moveStrafing = 0.0F;

            if (this.onGround) {
                this.jumpsRemaining = getJumpMaxAmount();
                if (!this.wasGrounded) {
                    this.airborneFromJump = false;
                }
            }

            this.wasGrounded = this.onGround;

            if (this.horizontalCollision) {
                this.speedProgress = Math.max(0.0F, this.speedProgress - WALL_HIT_SPEED_LOSS);
            }

            if (player.isJumping() && !this.jumpPressed) {
                this.jumpPressed = true;
                if (this.onGround) {
                    this.yd = jumpHeight;
                    this.airborneFromJump = true;
                    this.onGround = false;
                    if (!this.world.isClientSide) {
                        this.world.playSoundAtEntity(null, this, "aether:mob.wingflap", 2.0F, 1.0F);
                    }
                } else if (this.isInWater()) {
                    this.yd = jumpHeight / 2;
                    if (!this.world.isClientSide) {
                        this.world.playSoundAtEntity(null, this, "aether:mob.wingflap", 2.0F, 1.0F);
                    }
                } else if (this.jumpsRemaining > 0) {
                    this.yd = jumpHeight * 1.5F;
                    this.jumpsRemaining--;
                    this.airborneFromJump = true;
                    if (!this.world.isClientSide) {
                        this.world.playSoundAtEntity(null, this, "aether:mob.wingflap", 2.0F, 1.0F);
                    }
                }
            } else if (!player.isJumping()) {
                this.jumpPressed = false;
            }

            float friction = this.world.getBlockType(this.queryPos.set(MathHelper.floor(this.x), MathHelper.floor(this.y - 1.0F), MathHelper.floor(this.z))).friction;
            float accel = MathHelper.lerp(3.0F, 10.0F * (1.0F + (0.6F - friction)), this.speedProgress) * (!this.onGround ? 0.25F : 1.0F) * (this.isInWater() ? 0.5F : 1.0F);
            float maxSpeed = MathHelper.lerp(0.15F, 0.5F * (1.0F - (0.6F - friction) / 2.5F), this.speedProgress) * (this.isInWater() ? 0.5F : 1.0F);

            double pXd = this.passenger.xd;
            double pZd = this.passenger.zd;
            double vel = Math.sqrt(this.passenger.xd * this.passenger.xd + this.passenger.zd * this.passenger.zd);
            if (vel != 0.0F && Double.isFinite(vel)) {
                pXd /= vel;
                pZd /= vel;
                vel = MathHelper.clamp(vel, 0.0F, 0.02 * (double) (1.0F + (0.6F - friction)));
                pXd *= vel;
                pZd *= vel;
            }

            double externalSpeed = Math.sqrt(this.xd * this.xd + this.zd * this.zd);
            double effectiveMax = Math.max(maxSpeed, externalSpeed);
            this.xd += pXd * (double) accel;
            this.zd += pZd * (double) accel;
            double spdsqrd = this.xd * this.xd + this.zd * this.zd;
            if (spdsqrd > effectiveMax * effectiveMax) {
                double speed = Math.sqrt(spdsqrd);
                this.xd = this.xd / speed * effectiveMax;
                this.zd = this.zd / speed * effectiveMax;
            }

            this.xRot = this.passenger.xRot;
            this.yRot = this.passenger.yRot;
            this.yBodyRot = this.passenger.yRot;

            if (this.isInWater()) {
                this.speedProgress = 0.0F;
            } else {
                if (pXd * pXd + pZd * pZd > 5.0E-6F) {
                    this.speedProgress += 0.005F * this.accelerationRate * (1.0F + (0.6F - friction) * 1.5F);
                    this.speedProgress *= 1.0F + (0.025F * this.accelerationRate) * (1.0F + (0.6F - friction) * 1.5F);
                } else {
                    this.speedProgress *= 0.9F;
                }

                this.speedProgress = MathHelper.clamp(this.speedProgress, 0.0F, 1.0F);
            }
        } else {
            this.moveSpeed = 0.7F;
            if (this.getSitting() && this.isInWater()) {
                this.setSitting(false);
                this.sitWhenBackOnLand = true;
            }

            super.updateAI();
            if (this.sitWhenBackOnLand && this.getSaddled() && this.onGround && !this.isInWater()) {
                this.sitWhenBackOnLand = false;
                this.setSitting(true);
            }
        }
    }

    public float consumePendingFallDistance() {
        float distance = this.pendingFallDistance;
        this.pendingFallDistance = 0.0F;
        return distance;
    }

    public void onGround() {
        if (this.onGround) {
            this.jumpsRemaining = getJumpMaxAmount();
        }
    }
}
