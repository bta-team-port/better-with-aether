package teamport.aether.entity.vehicle.parachute;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import teamport.aether.block.AetherBlocks;
import teamport.aether.entity.AetherRideable;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.mixin.accessors.EntityAccessor;
import teamport.aether.net.message.AetherRideableNetworkMessage;
import turniplabs.halplibe.helper.EnvironmentHelper;
import turniplabs.halplibe.helper.network.NetworkHandler;

public class EntityParachute extends Mob implements AetherRideable {
    protected float maxSpeed = 0.10F;
    protected String pathParticle = "explode";
    protected Block<?> particleBlock = AetherBlocks.AERCLOUD_WHITE;
    protected double xdChange = 0;
    protected double zdChange = 0;

    public EntityParachute(@Nullable World world) {
        super(world);
        setSize(1.0f, 1.0f);
    }
    @Override
    public boolean makeStepSound() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();

        double x = this.x + ((EntityAccessor) this).getRandom().nextDouble() * 0.75 * 2.0 - 0.75;
        double y = this.bb.minY - 0.5 + ((EntityAccessor) this).getRandom().nextDouble() * 0.75 * 2.0 - 0.75;
        double z = this.z + ((EntityAccessor) this).getRandom().nextDouble() * 0.75 * 2.0 - 0.75;

        ParticleMaker.spawnParticle(world, pathParticle, x, y, z, 0.0, 0.0, 0.0, 0);

        if (this.passenger == null) {
            breakParachute();
            this.remove();
        } else {
            this.passenger.handleSpecialVehicleControl();
        }

        handleParachuteMovement();

        if (this.onGround || isInWater()) {
            Entity rider = this.ejectRider();
            if (rider != null) rider.fling(0, 0.30F, 0, 0.0F);

            breakParachute();
            this.remove();
        }
    }

    public void breakParachute() {
        for (int i = 0; i < 16 + random.nextInt(10); i++) {
            float faceX = bbWidth * random.nextFloat();
            float faceY = bbWidth * random.nextFloat();

            float posX;
            float posY;
            float posZ;
            Direction dir = Direction.all[random.nextInt(Direction.all.length)];
            switch (dir) {
                case WEST:
                    posX = (float) (x);
                    posY = (float) (y + faceY);
                    posZ = (float) (z + faceX);
                    break;

                case EAST:
                    posX = (float) (x + 1);
                    posY = (float) (y + faceY);
                    posZ = (float) (z + faceX);
                    break;

                case SOUTH:
                    posX = (float) (x + faceX);
                    posY = (float) (y + faceY);
                    posZ = (float) (z + 1);
                    break;

                case NORTH:
                    posX = (float) (x + faceX);
                    posY = (float) (y + faceY);
                    posZ = (float) (z);
                    break;

                case DOWN:
                    posX = (float) (x + faceX);
                    posY = (float) (y - 1);
                    posZ = (float) (z + faceY);
                    break;

                default:
                    posX = (float) (x + faceX);
                    posY = (float) (y + 1);
                    posZ = (float) (z + faceY);
                    break;
            }

            ParticleMaker.spawnParticle(this.world, "block", posX - 0.5F, posY + 0.25F, posZ - 0.5F, 0, 0.005, 0, particleBlock.id());
        }

        if (this.world != null) this.world.playBlockSoundEffect(null, x, y, z, particleBlock, EnumBlockSoundEffectType.MINE);
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

    @Override
    public void updateAI() {
        if (this.passenger != null) {
            vehicleMovement();
        }
    }

    @Override
    public double getRideHeight() {
        return this.bbHeight + 0.2f;
    }

    @Override
    public void causeFallDamage(float distance) {
    }

    @Override
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

        this.yRotO = this.yRot = yRot;
    }
    public String getPathParticle() {
        return pathParticle;
    }
}
