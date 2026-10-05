package teamport.aether.entity.projectile;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.helper.ParticleMaker;


public abstract class ProjectileElementBase extends Projectile implements ProjectileAether {
    protected int bounceCount = 0;
    private static final int MAX_BOUNCES = 20;

    private static final String[] DEFAULT_PARTICLES = {"explode"};

    protected ProjectileElementBase(World world) {
        super(world);
        this.initProjectile();
    }

    protected ProjectileElementBase(World world, Mob owner) {
        super(world, owner);
        this.initProjectile();
    }

    @SuppressWarnings("unused")
    protected ProjectileElementBase(World world, double x, double y, double z) {
        super(world, x, y, z);
        this.initProjectile();
    }

    @Override
    public void initProjectile() {
        this.damage = 2;
        this.defaultGravity = 0.0F;
        this.setSize(1.0F, 1.0F);
        this.setNoPhysics(true);
    }

    @Override
    public void tick() {
        super.tick();
        int xFloor = MathHelper.floor(this.x);
        int yFloor = MathHelper.floor(this.y);
        int zFloor = MathHelper.floor(this.z);

        if ((this.xd > 0 && this.world.getBlockId(xFloor + 1, yFloor, zFloor) != 0) ||
            (this.xd < 0 && this.world.getBlockId(xFloor - 1, yFloor, zFloor) != 0)) {
            this.xd = -this.xd;
            bounceSound();
            bounceCount++;
        }

        if ((this.yd > 0 && this.world.getBlockId(xFloor, yFloor + 1, zFloor) != 0) ||
            (this.yd < 0 && this.world.getBlockId(xFloor, yFloor - 1, zFloor) != 0)) {
            this.yd = -this.yd;
            bounceSound();
            bounceCount++;
        }

        if ((this.zd > 0 && this.world.getBlockId(xFloor, yFloor, zFloor + 1) != 0) ||
            (this.zd < 0 && this.world.getBlockId(xFloor, yFloor, zFloor - 1) != 0)) {
            this.zd = -this.zd;
            bounceSound();
            bounceCount++;
        }

        if (!this.world.isClientSide && bounceCount >= MAX_BOUNCES) {
            doExplosion();
            this.remove();
        }
    }

    public void bounceSound() {
        this.world.playSoundAtEntity(null, this, "random.explode", 0.1F, 2.0F);
    }

    public void doExplosion() {
        doExplosionHelper(world, this, DEFAULT_PARTICLES, "random.explode", null, SoundCategory.WORLD_SOUNDS, 0.25F);
    }

    protected static void doExplosionHelper(World world, Entity entity, String[] particles, @Nullable String soundPath, @Nullable Mob target, SoundCategory soundCategory, float volume) {
        if (world == null) return;
        Entity usedEntity = target == null ? entity : target;
        for (int particle = 0; particle < 16; particle++) {
            double xParticle = usedEntity.x + (world.rand.nextDouble()) - (world.rand.nextDouble() * 0.375);
            double yParticle = usedEntity.y + 0.5 + (world.rand.nextDouble()) - (world.rand.nextDouble() * 0.375);
            double zParticle = usedEntity.z + (world.rand.nextDouble()) - (world.rand.nextDouble() * 0.375);

            ParticleMaker.spawnParticle(world, particles[world.rand.nextInt(particles.length)], xParticle, yParticle, zParticle, 0, 0, 0, 0);
        }
        if (soundPath != null) {
            world.playSoundEffect(null, soundCategory, usedEntity.x, usedEntity.y, usedEntity.z, soundPath, volume, (1.3F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
        } else {
            world.playBlockSoundEffect(null, usedEntity.x, usedEntity.y, usedEntity.z, Blocks.ICE, EnumBlockSoundEffectType.MINE);
        }
    }

    @Override
    @SuppressWarnings("java:S131")
    public void onHit(@NonNull HitResult hitResult) {
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
    public void readAdditionalSaveData(@NonNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.bounceCount = tag.getInteger("bounceCount");
    }

    @Override
    public void addAdditionalSaveData(@NonNull CompoundTag tag) {
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

    @SuppressWarnings("unused")
    protected static @NonNull Entity getEntity(Class<? extends ProjectileElementBase> clazz, World world, double x, double y, double z, int meta, boolean hasVelocity, double xd, double yd, double zd, Entity owner) {
        ProjectileElementBase element;
        try {
            element = clazz.getDeclaredConstructor(World.class).newInstance(world);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        element.setPos(x, y, z);
        if (hasVelocity) element.setHeading(xd, yd, zd, 1, 0);
        if (owner instanceof Mob mob) element.owner = mob;
        return element;
    }
}
