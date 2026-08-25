package teamport.aether.entity.animal;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import teamport.aether.entity.interfaces.AetherJumpAmount;
import teamport.aether.entity.interfaces.AetherRideable;
import teamport.aether.mixin.accessors.EntityAccessor;
import teamport.aether.net.message.AetherRideableNetworkMessage;
import turniplabs.halplibe.helper.EnvironmentHelper;
import turniplabs.halplibe.helper.network.NetworkHandler;

public class MobAetherAnimalRideable extends MobAetherAnimal implements AetherRideable, AetherJumpAmount {
    protected int jumpsRemaining;
    protected int maxJumps = 3;
    protected boolean jumpPressed;

    protected float rideFootSize;

    protected double xdChange = 0;
    protected double zdChange = 0;
    protected boolean playerUsedJump = false;

    public float lookFadeAlpha = 1.0F;
    public long lookFadeLastRenderNanos = 0L;

    public MobAetherAnimalRideable(World world) {
        super(world);
        this.jumpsRemaining = getJumpMaxAmount();
    }

    @Override
    public void tick() {
        super.tick();
        this.onGround();
        vehicleMovement();
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
    public void controlEntity(float moveForward, float moveStrafe, boolean isJumping, float xRot, float yRot) {
        if (EnvironmentHelper.isMultiplayerClient()) {
            NetworkHandler.sendToServer(
                new AetherRideableNetworkMessage(moveForward, moveStrafe, isJumping, xRot, yRot)
            );
        }

        float yawDeg = (float) (yRot * (Math.PI / 180));
        float step = 0.175F;

        if (moveForward > 0.1F || moveForward < -0.1F) {
            xdChange += moveForward * -Math.sin(yawDeg) * step;
            zdChange += moveForward * Math.cos(yawDeg) * step;

        }

        if (moveStrafe > 0.1F || moveStrafe < -0.1F) {
            xdChange += moveStrafe * Math.cos(yawDeg) * step;
            zdChange += moveStrafe * Math.sin(yawDeg) * step;

        }

        if (isJumping && !jumpPressed) {
            playerUsedJump = true;
            jumpPressed = true;
        }

        if (this.jumpPressed && !isJumping) {
            this.jumpPressed = false;
        }
    }

    public void vehicleMovement() {
        if (passenger == null || !(this.passenger instanceof Player)) return;
        Player player = (Player) this.passenger;

        player.handleSpecialVehicleControl();

        xd += xdChange;
        zd += zdChange;
        xdChange = 0.0;
        zdChange = 0.0;

        if (playerUsedJump) {
            boolean canJump = this.jumpsRemaining > 0;

            if (this.onGround) {
                yd = 1.4;
                this.onGround = false;
            } else {
                if (isInWater()) yd = 0.5;
                else if (canJump) yd = 1.2;
                --this.jumpsRemaining;
            }

            if ((isInWater() || canJump) && this.world != null && !this.world.isClientSide) {
                world.playSoundAtEntity(null, this, "aether:mob.wingflap", 2.0f, 1.0f);
            }
        }

        playerUsedJump = false;

        player.sendSpecialVehiclePacket();

        double horizontalSpeed = Math.abs(Math.sqrt(this.xd * this.xd + this.zd * this.zd));
        if (horizontalSpeed > 0.375) {
            double normal = 0.375 / horizontalSpeed;
            this.xd *= normal;
            this.zd *= normal;
        }
    }

    @Override
    public void updateAI() {
        if (this.passenger instanceof Player) {
            this.moveSpeed = 0.0F;
            this.moveStrafing = 0.0F;
            this.isJumping = false;
            this.footSize = rideFootSize;

            this.yRotO = this.yRot = this.passenger.yRot;
            this.xRotO = this.xRot = this.passenger.xRot;

            Player player = (Player) this.passenger;
            ((EntityAccessor) player).setFallDistance(0.0F);
        } else {
            this.footSize = rideFootSize - 0.5f;
            super.updateAI();
        }
    }

    public void onGround() {
        if (this.onGround) {
            this.jumpsRemaining = getJumpMaxAmount();
        }
    }
}
