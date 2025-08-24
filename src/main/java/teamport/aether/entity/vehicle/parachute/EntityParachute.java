package teamport.aether.entity.vehicle.parachute;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import teamport.aether.entity.AetherRideable;
import teamport.aether.mixin.accessors.EntityAccessor;
import teamport.aether.net.message.AetherRideableNetworkMessage;
import turniplabs.halplibe.helper.EnvironmentHelper;
import turniplabs.halplibe.helper.network.NetworkHandler;

public class EntityParachute extends Mob implements AetherRideable {

    public EntityParachute(@Nullable World world) {
        super(world);
        setSize(1.0f, 1.0f);
    }

    static float maxSpeed = 0.10F;

    public boolean makeStepSound() {
        return false;
    }

    protected double xdChange = 0;
    protected double zdChange = 0;

    public String getPathParticle() {
        return "explode";
    }

    public void tick() {
        super.tick();

        double x = this.x + ((EntityAccessor) this).getRandom().nextDouble() * 0.75 * 2.0 - 0.75;
        double y = this.bb.minY - 0.5 + ((EntityAccessor) this).getRandom().nextDouble() * 0.75 * 2.0 - 0.75;
        double z = this.z + ((EntityAccessor) this).getRandom().nextDouble() * 0.75 * 2.0 - 0.75;

        if (!EnvironmentHelper.isServerEnvironment()) {
            world.spawnParticle(getPathParticle(), x, y, z, 0.0, 0.0, 0.0, 0);
        }

        if (this.passenger == null) {
            this.remove();
        } else {
            this.passenger.handleSpecialVehicleControl();
        }

        handleParachuteMovement();

        if (this.onGround) {
            this.ejectRider();
            this.remove();
        }
    }

    protected void handleParachuteMovement() {
        this.move(this.xd, this.yd, this.zd);
        if (this.yd < -0.2) {
            this.yd *= 0.5F;
        }

        this.xd *= 0.9F;
        this.zd *= 0.9F;
    }

    public void vehicleMovement() {
        this.moveSpeed = 0.0F;
        this.moveStrafing = 0.0F;

        if (this.passenger != null) {
            this.passenger.fallDistance = 0.0F;
        }

        xd += xdChange;
        zd += zdChange;
        xdChange = 0.0;
        zdChange = 0.0;

        double speed = Math.sqrt(this.xd * this.xd + this.zd * this.zd);
        if (speed > maxSpeed) {
            double factor = maxSpeed / speed;
            this.xd *= factor;
            this.zd *= factor;
        }
    }

    public void updateAI() {
        if (this.passenger != null) {
            vehicleMovement();
        }
    }

    public double getRideHeight() {
        return this.bbHeight + 0.2f;
    }

    public void causeFallDamage(float distance) {
    }

    public boolean hurt(Entity attacker, int damage, DamageType type) {
        return false;
    }

    @Override
    public void controlEntity(float moveForward, float moveStrafe, boolean isJumping, float xRot, float yRot) {
        if (EnvironmentHelper.isClientWorld()) {
            NetworkHandler.sendToServer(
                new AetherRideableNetworkMessage(moveForward, moveStrafe, isJumping, xRot, yRot)
            );
        }

        float yawDeg = (float) (yRot * (Math.PI/180));
        float step = 0.175F;

        if (moveForward > 0.1F) {
            xdChange += (double) moveForward * -Math.sin(yawDeg) * step;
            zdChange += (double) moveForward * Math.cos(yawDeg) * step;

        } else if (moveForward < -0.1F) {
            xdChange += (double) moveForward * -Math.sin(yawDeg) * step;
            zdChange += (double) moveForward * Math.cos(yawDeg) * step;
        }

        if (moveStrafe > 0.1F) {
            xdChange += (double) moveStrafe * Math.cos(yawDeg) * step;
            zdChange += (double) moveStrafe * Math.sin(yawDeg) * step;

        } else if (moveStrafe < -0.1F) {
            xdChange += (double) moveStrafe * Math.cos(yawDeg) * step;
            zdChange += (double) moveStrafe * Math.sin(yawDeg) * step;
        }

        this.yRotO = this.yRot = yRot;
    }
}