package teamport.aether.entity.projectile;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.Global;
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
        final double hw = this.bbWidth / 2.0;   // half width (x/z radius)
        final double h  = this.bbHeight;
        // full height; use your actual field names

        int xi = MathHelper.floor(this.x);
        int zi = MathHelper.floor(this.z);
        int yi = MathHelper.floor(this.y + h / 2.0); // cell at the box's vertical center

        final double EPS = 1e-4;
        if (this.xd != 0) {
            int ex = MathHelper.floor(this.x + Math.signum(this.xd) * (hw - EPS));
            if (this.world.getBlockId(ex, yi, zi) != 0) {
                this.x = this.xd > 0 ? ex - hw : ex + 1 + hw;
                this.xd = -this.xd;
                bounceSound();
                bounceCount++;
            }
        }

        if (this.zd != 0) {
            int ez = MathHelper.floor(this.z + Math.signum(this.zd) * (hw - EPS));
            if (this.world.getBlockId(xi, yi, ez) != 0) {
                this.z = this.zd > 0 ? ez - hw : ez + 1 + hw;
                this.zd = -this.zd;
                bounceSound();
                bounceCount++;
            }
        }

        if (this.yd != 0) {
            // going up: the top of the box leads, going down: the feet lead
            double edgeY = this.yd > 0 ? this.y + h - EPS : this.y + EPS;
            int ey = MathHelper.floor(edgeY);
            if (this.world.getBlockId(xi, ey, zi) != 0) {
                this.y = this.yd > 0 ? ey - h : ey + 1; // top flush under the block / feet flush on top
                this.yd = -this.yd;
                bounceSound();
                bounceCount++;
            }
        }

        if ((!this.world.isClientSide && bounceCount >= MAX_BOUNCES) || ticksInAir > Global.TICKS_PER_SECOND * 60) {
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
