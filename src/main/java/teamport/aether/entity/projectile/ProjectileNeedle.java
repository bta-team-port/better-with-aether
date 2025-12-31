package teamport.aether.entity.projectile;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import sunsetsatellite.catalyst.effects.api.effect.IHasEffects;
import teamport.aether.effect.AetherEffects;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.item.AetherItems;

public class ProjectileNeedle extends Projectile implements ProjectileAether, AetherProjectileDeathMessages {
    private int xTile;
    private int yTile;
    private int zTile;
    private int inTile;
    private int shake;
    private int inData;
    private boolean inGround;

    @SuppressWarnings("unused")
    public ProjectileNeedle(World world) {
        super(world);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = 0;
        this.shake = 0;
        this.inData = 0;
        this.inGround = false;
    }

    public ProjectileNeedle(World world, double d, double d1, double d2) {
        super(world, d, d1, d2);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = 0;
        this.shake = 0;
        this.inData = 0;
        this.inGround = false;
    }

    public ProjectileNeedle(World world, Mob mob) {
        super(world, mob);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = 0;
        this.shake = 0;
        this.inData = 0;
        this.inGround = false;
    }

    @Override
    public void initProjectile() {
        super.initProjectile();
        this.damage = 4;
        this.defaultGravity = 0.005F;
    }

    @Override
    public void setHeading(double newMotionX, double newMotionY, double newMotionZ, float speed, float randomness) {
        float velocity = MathHelper.sqrt(newMotionX * newMotionX + newMotionY * newMotionY + newMotionZ * newMotionZ);
        newMotionX /= velocity;
        newMotionY /= velocity;
        newMotionZ /= velocity;
        newMotionX += (this.random.nextGaussian() * 0.0075 * randomness) / 2;
        newMotionY += (this.random.nextGaussian() * 0.0075 * randomness) / 2;
        newMotionZ += (this.random.nextGaussian() * 0.0075 * randomness) / 2;
        newMotionX *= speed;
        newMotionY *= speed;
        newMotionZ *= speed;
        this.xd = newMotionX;
        this.yd = newMotionY;
        this.zd = newMotionZ;
        float f3 = MathHelper.sqrt(newMotionX * newMotionX + newMotionZ * newMotionZ);
        this.yRotO = this.yRot = (float) (Math.atan2(newMotionX, newMotionZ) * 180.0 / Math.PI);
        this.xRotO = this.xRot = (float) (Math.atan2(newMotionY, f3) * 180.0 / Math.PI);
        this.ticksInGround = 0;
    }

    @Override
    public void lerpMotion(double xd, double yd, double zd) {
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            float f = MathHelper.sqrt(xd * xd + zd * zd);
            this.yRot = (float) (Math.atan2(xd, zd) * 30.0 / Math.PI);
            this.xRot = (float) (Math.atan2(yd, f) * 30.0 / Math.PI);
            this.xRotO = this.xRot;
            this.yRotO = this.yRot;
            this.moveTo(this.x, this.y, this.z, this.yRot, this.xRot);
            this.ticksInGround = 0;
        }

    }

    @Override
    public void tick() {
        if (this.world == null) return;
        if (this.shake > 0) {
            --this.shake;
        }

        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            float f = MathHelper.sqrt(this.xd * this.xd + this.zd * this.zd);
            this.yRotO = this.yRot = (float) (Math.atan2(this.xd, this.zd) * 30.0 / Math.PI);
            this.xRotO = this.xRot = (float) (Math.atan2(this.yd, f) * 30.0 / Math.PI);
        }

        Block<?> block = this.world.getBlock(this.xTile, this.yTile, this.zTile);
        if (block != null) {
            AABB aabb = block.getCollisionBoundingBoxFromPool(this.world, this.xTile, this.yTile, this.zTile);
            if (aabb != null && aabb.contains(Vec3.getTempVec3(this.x, this.y, this.z))) {
                this.inGround = true;
            }
        }

        if (this.inGround) {
            int id = this.world.getBlockId(this.xTile, this.yTile, this.zTile);
            int meta = this.world.getBlockMetadata(this.xTile, this.yTile, this.zTile);
            if (id == this.inTile && meta == this.inData) {
                ++this.ticksInGround;
                if (this.ticksInGround == 1200) {
                    this.remove();
                }

            } else {
                this.inGround = false;
                this.xd *= this.random.nextFloat() * 0.02;
                this.yd *= this.random.nextFloat() * 0.02;
                this.zd *= this.random.nextFloat() * 0.02;
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
        } else {

            super.tick();
        }
    }

    @Override
    public HitResult getHitResult() {
        if (this.world == null) return super.getHitResult();
        Vec3 oldPosition = Vec3.getTempVec3(this.x, this.y, this.z);
        Vec3 newPosition = Vec3.getTempVec3(this.x + this.xd, this.y + this.yd, this.z + this.zd);
        return this.world.checkBlockCollisionBetweenPoints(oldPosition, newPosition, false, true, false);
    }

    @Override
    public void onHit(HitResult hitResult) {
        if (this.world == null) return;
        if (hitResult.entity != null) {
            if (hitResult.entity.hurt(this.owner, this.damage, DamageType.COMBAT)) {
                IHasEffects<?> target = (IHasEffects<?>) hitResult.entity;
                AetherEffects.add((Entity) target, AetherEffects.poisonEffect, random.nextInt(1) + 1);

                if (this.isOnFire()) {
                    hitResult.entity.fireHurt();
                }

                if (!this.world.isClientSide) {
                    this.world.playSoundAtEntity(null, this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
                }
                this.remove();
            }

        } else {
            this.xTile = hitResult.x;
            this.yTile = hitResult.y;
            this.zTile = hitResult.z;
            this.inTile = this.world.getBlockId(this.xTile, this.yTile, this.zTile);
            this.inData = this.world.getBlockMetadata(this.xTile, this.yTile, this.zTile);
            this.xd = (float) (hitResult.location.x - this.x);
            this.yd = (float) (hitResult.location.y - this.y);
            this.zd = (float) (hitResult.location.z - this.z);
            float f1 = MathHelper.sqrt(this.xd * this.xd + this.yd * this.yd + this.zd * this.zd);
            this.x -= this.xd / f1 * 0.05;
            this.y -= this.yd / f1 * 0.05;
            this.z -= this.zd / f1 * 0.05;
            this.inGroundAction();
        }
    }

    public void inGroundAction() {
        if (this.world == null) return;
        this.world.playSoundAtEntity(null, this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));

        for (int j = 0; j < 4; ++j) {
            ParticleMaker.spawnParticle(this.world, "item", this.x, this.y, this.z, 0.0, 0.0, 0.0, AetherItems.AMMO_DART_GOLDEN.id);
        }

        this.remove();
    }

    @Override
    public void waterTick() {
        for (int k = 0; k < 4; ++k) {
            double particleDistance = 0.25;
            ParticleMaker.spawnParticle(this.world, "bubble", this.x - this.xd * particleDistance, this.y - this.yd * particleDistance, this.z - this.zd * particleDistance, this.xd, this.yd, this.zd, 0);
        }

        this.projectileSpeed = 0.95F;
    }

    @Override
    public void addAdditionalSaveData(@NonNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putShort("xTile", (short) this.xTile);
        tag.putShort("yTile", (short) this.yTile);
        tag.putShort("zTile", (short) this.zTile);
        tag.putShort("inTile", (short) this.inTile);
        tag.putByte("shake", (byte) this.shake);
        tag.putByte("inData", (byte) this.inData);
        tag.putByte("inGround", (byte) (this.inGround ? 1 : 0));
    }

    @Override
    public void readAdditionalSaveData(@NonNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.xTile = tag.getShort("xTile");
        this.yTile = tag.getShort("yTile");
        this.zTile = tag.getShort("zTile");
        this.inTile = tag.getShort("inTile") & 16383;
        this.shake = tag.getByte("shake") & 255;
        this.inData = tag.getByte("inData") & 255;
        this.inGround = tag.getByte("inGround") == 1;
    }

    @SuppressWarnings("unused")
    public static Entity getEntity(World world, double x, double y, double z, int meta, boolean hasVelocity, double xd, double yd, double zd, Entity owner) {
        ProjectileNeedle projectile = new ProjectileNeedle(world, x, y, z);
        if (hasVelocity) projectile.setHeading(xd, yd, zd, 1, 0);
        if (owner instanceof Mob) projectile.owner = (Mob) owner;
        return projectile;
    }
    public int getShake() {
        return shake;
    }
}
