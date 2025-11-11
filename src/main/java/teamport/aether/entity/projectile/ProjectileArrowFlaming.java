package teamport.aether.entity.projectile;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.monster.MobCreeper;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.projectile.ProjectileArrow;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.items.AetherItems;

public class ProjectileArrowFlaming extends ProjectileArrow implements ProjectileAether, AetherProjectileDeathMessages<ProjectileArrowFlaming> {

    public ProjectileArrowFlaming(World world) {
        super(world);
    }

    public ProjectileArrowFlaming(World world, Mob entityliving, boolean doesArrowBelongToPlayer, int arrowType) {
        super(world, entityliving, doesArrowBelongToPlayer, arrowType);
    }

    public ProjectileArrowFlaming(World world, double x, double y, double z, int arrowType) {
        super(world, x, y, z, arrowType);
        this.mobsHit = 0;
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = 0;
        this.shake = 0;
        this.inData = 0;
        this.stack = new ItemStack(AetherItems.AMMO_ARROW_FLAMING);
        this.inGround = false;
        this.doesArrowBelongToPlayer = false;
    }

    protected void initProjectile() {
        super.initProjectile();
        this.damage = 6;
    }

    public float getBrightness(float partialTick) {
        return 1.0F;
    }

    public int getLightmapCoord(float partialTick) {
        return this.world.getLightmapCoord(15, 15);
    }

    public void tick() {
        if (this.shake > 0) {
            --this.shake;
        }

        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            float f = MathHelper.sqrt(this.xd * this.xd + this.zd * this.zd);
            this.yRotO = this.yRot = (float) (Math.atan2(this.xd, this.zd) * 180.0 / Math.PI);
            this.xRotO = this.xRot = (float) (Math.atan2(this.yd, f) * 180.0 / Math.PI);
        }

        Block<?> block = this.world.getBlock(this.xTile, this.yTile, this.zTile);
        if (block != null) {
            AABB aabb = block.getCollisionBoundingBoxFromPool(this.world, this.xTile, this.yTile, this.zTile);
            if (aabb != null && aabb.contains(Vec3.getTempVec3(this.x, this.y, this.z))) {
                this.setGrounded(true);
            }
        }

        if (this.isGrounded()) {
            int id = this.world.getBlockId(this.xTile, this.yTile, this.zTile);
            int meta = this.world.getBlockMetadata(this.xTile, this.yTile, this.zTile);
            if (id == this.inTile && meta == this.inData) {
                ++this.ticksInGround;
                if (this.ticksInGround == 1200) {
                    this.remove();
                }

            } else {
                this.setGrounded(false);
                this.xd *= (double) this.random.nextFloat() * 0.2;
                this.yd *= (double) this.random.nextFloat() * 0.2;
                this.zd *= (double) this.random.nextFloat() * 0.2;
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
        } else {
            ParticleMaker.spawnParticle(this.world, "flame", this.x, this.y, this.z, this.xd * 0.05, this.yd * 0.05 - 0.1, this.zd * 0.05, 0);
            ParticleMaker.spawnParticle(this.world, "flame", this.x + this.xd * 0.5, this.y + this.yd * 0.5, this.z + this.zd * 0.5, this.xd * 0.05, this.yd * 0.05 - 0.1, this.zd * 0.05, 0);
            ParticleMaker.spawnParticle(this.world, "smoke", this.x, this.y, this.z, this.xd * 0.05, this.yd * 0.05 - 0.1, this.zd * 0.05, 0);
            ParticleMaker.spawnParticle(this.world, "smoke", this.x + this.xd * 0.5, this.y + this.yd * 0.5, this.z + this.zd * 0.5, this.xd * 0.05, this.yd * 0.05 - 0.1, this.zd * 0.05, 0);

            super.tick();
        }
    }

    public void onHit(HitResult hitResult) {
        if (hitResult.entity != null) {
            if (hitResult.entity.hurt(this.owner, this.damage, DamageType.FIRE)) {
                if (hitResult.entity instanceof MobCreeper) {
                    MobCreeper entityCreeper = (MobCreeper) hitResult.entity;
                    entityCreeper.setTarget(entityCreeper);
                }

                if (!this.world.isClientSide) {
                    this.world.playSoundAtEntity(null, this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
                }

                hitResult.entity.fireHurt();
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
            this.x -= this.xd / (double) f1 * 0.05;
            this.y -= this.yd / (double) f1 * 0.05;
            this.z -= this.zd / (double) f1 * 0.05;
            this.inGroundAction(hitResult.side, xTile, yTile, zTile);
        }
    }

    public void inGroundAction(Side side, int blockX, int blockY, int blockZ) {
        this.world.playSoundAtEntity(null, this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        for (int j = 0; j < 4; ++j) {
            ParticleMaker.spawnParticle(this.world, "item", this.x, this.y, this.z, 0.0, 0.0, 0.0, Items.AMMO_FIREBALL.id);
            ParticleMaker.spawnParticle(this.world, "item", this.x, this.y, this.z, 0.0, 0.0, 0.0, AetherItems.AMMO_ARROW_FLAMING.id);
        }
        blockX += side.getOffsetX();
        blockY += side.getOffsetY();
        blockZ += side.getOffsetZ();
        int blockID = world.getBlockId(blockX, blockY, blockZ);
        if (blockID == 0) {
            world.setBlockWithNotify(blockX, blockY, blockZ, Blocks.FIRE.id());
        }
        this.remove();
    }

    public static Entity getEntity(World world, double x, double y, double z, int meta, boolean hasVelocity, double xd, double yd, double zd, Entity owner, @Nullable CompoundTag compoundTag) {
        ProjectileArrowFlaming projectile = new ProjectileArrowFlaming(world, x, y, z, meta);
        if (hasVelocity) projectile.setHeading(xd, yd, zd, 1, 0);
        if (owner instanceof Mob) projectile.owner = (Mob) owner;
        if (owner instanceof Player) projectile.doesArrowBelongToPlayer = true;
        return projectile;
    }
}
