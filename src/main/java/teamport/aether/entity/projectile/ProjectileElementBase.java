package teamport.aether.entity.projectile;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.helper.ParticleMaker;


abstract public class ProjectileElementBase extends Projectile implements ProjectileAether {
    public int bounceCount = 0;
    public int maxBounces = 20;

    public String[] particles = {"explode"};

    public ProjectileElementBase(World world) {
        super(world);
        this.initProjectile();
    }

    public ProjectileElementBase(World world, Mob owner) {
        super(world, owner);
        this.initProjectile();
    }

    public ProjectileElementBase(World world, double x, double y, double z) {
        super(world, x, y, z);
        this.initProjectile();
    }

    @Override
    public void initProjectile() {
        this.damage = 2;
        this.defaultGravity = 0.0F;
        this.setSize(1.0F, 1.0F);
    }

    @Override
    public void tick() {
        super.tick();

        int xFloor = MathHelper.floor(this.x);
        int yFloor = MathHelper.floor(this.y);
        int zFloor = MathHelper.floor(this.z);

        if (this.xd > 0 && this.world.getBlockId(xFloor + 1, yFloor, zFloor) != 0) {
            this.xd = -this.xd;
            bounceSound();
            bounceCount++;
        } else if (this.xd < 0 && this.world.getBlockId(xFloor - 1, yFloor, zFloor) != 0) {
            this.xd = -this.xd;
            bounceSound();
            bounceCount++;
        }

        if (this.yd > 0 && this.world.getBlockId(xFloor, yFloor + 1, zFloor) != 0) {
            this.yd = -this.yd;
            bounceSound();
            bounceCount++;
        } else if (this.yd < 0 && this.world.getBlockId(xFloor, yFloor - 1, zFloor) != 0) {
            this.yd = -this.yd;
            bounceSound();
            bounceCount++;
        }

        if (this.zd > 0 && this.world.getBlockId(xFloor, yFloor, zFloor + 1) != 0) {
            this.zd = -this.zd;
            bounceSound();
            bounceCount++;
        } else if (this.zd < 0 && this.world.getBlockId(xFloor, yFloor, zFloor - 1) != 0) {
            this.zd = -this.zd;
            bounceSound();
            bounceCount++;
        }

        if (!this.world.isClientSide && bounceCount >= maxBounces) {
            doExplosion();
            this.remove();
        }
    }

    public void bounceSound() {
        this.world.playSoundAtEntity(null, this, "random.explode", 0.1F, 2.0F);
    }

    public void doExplosion() {
        for (int particle = 0; particle < 16; particle++) {
            double XParticle = x + ((double) world.rand.nextFloat()) - ((double) world.rand.nextFloat() * 0.375F);
            double YParticle = y + 0.5F + ((double) world.rand.nextFloat()) - ((double) world.rand.nextFloat() * 0.375F);
            double ZParticle = z + ((double) world.rand.nextFloat()) - ((double) world.rand.nextFloat() * 0.375F);

            ParticleMaker.spawnParticle(world, particles[world.rand.nextInt(particles.length)], XParticle, YParticle, ZParticle, 0, 0, 0, 0);
        }

        world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, x, y, z, "random.explode", 0.25F, (1.3F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
    }

    @Override
    public void onHit(HitResult hitResult) {
        if (!this.world.isClientSide) {
            if (hitResult.side != null) {
                switch (hitResult.side) {
                    case BOTTOM:
                    case TOP:
                        this.yd = -this.yd * 1.0F;
                        break;
                    case NORTH:
                    case SOUTH:
                        this.zd = -this.zd * 1.0F;
                        break;
                    case WEST:
                    case EAST:
                        this.xd = -this.xd * 1.0F;
                        break;
                }
                bounceCount++;
            }
        }
    }

    @Override
    public void afterTick() {
        this.x += this.xd;
        this.y += this.yd;
        this.z += this.zd;
        this.yRot = (float) (Math.atan2(this.xd, this.zd) * 180.0 / Math.PI);

        if (this.isInWater()) {
            this.waterTick();
        }


        this.setPos(this.x, this.y, this.z);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.bounceCount = tag.getInteger("bounceCount");
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("bounceCount", this.bounceCount);
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public float getPickRadius() {
        return 1.0F;
    }

    @Override
    public boolean hurt(Entity entity, int damage, DamageType type) {
        return false;
    }

    protected static Entity getEntity(Class<? extends ProjectileElementBase> clazz, World world, double x, double y, double z, int meta, boolean hasVelocity, double xd, double yd, double zd, Entity owner, @Nullable CompoundTag compoundTag) {
        ProjectileElementBase element;
        try {
            element = clazz.getDeclaredConstructor(World.class).newInstance(world);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        element.setPos(x, y, z);
        if (hasVelocity) element.setHeading(xd, yd, zd, 1, 0);
        if (owner instanceof Mob) element.owner = (Mob) owner;
        return element;
    }
}
