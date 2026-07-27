package teamport.aether.entity.projectile;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.monster.MobCreeper;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.projectile.ProjectileArrow;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.LightIndexHelper;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.World;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.item.AetherItems;

public class ProjectileArrowFlaming extends ProjectileArrow implements ProjectileAether, AetherProjectileDeathMessages {

    @SuppressWarnings("unused")
    public ProjectileArrowFlaming(World world) {
        super(world);
        this.tilePos = new TilePos(-1, -1, -1);
    }

    public ProjectileArrowFlaming(World world, Mob entityliving, boolean doesArrowBelongToPlayer, int arrowType) {
        super(world, entityliving, doesArrowBelongToPlayer, arrowType);
        this.tilePos = new TilePos(-1, -1, -1);
    }

    public ProjectileArrowFlaming(World world, double x, double y, double z, int arrowType) {
        super(world, x, y, z, arrowType);
        this.mobsHit = 0;
        this.tilePos = new TilePos(-1, -1, -1);
        this.inTile = null;
        this.shake = 0;
        this.inData = 0;
        this.stack = new ItemStack(AetherItems.AMMO_ARROW_FLAMING);
        this.inGround = false;
        this.doesArrowBelongToPlayer = false;
    }

    @Override
    protected void initProjectile() {
        super.initProjectile();
        this.damage = 6;
    }

    @Override
    public float getBrightness(float partialTick) {
        return 1.0F;
    }

    @Override
    public byte getLightIndex(float partialTick) {
        byte light = super.getLightIndex(partialTick);
        light = LightIndexHelper.setSkyLight(light, 15);
        return LightIndexHelper.setBlockLight(light, 15);
    }

    @Override
    public void tick() {
        if (this.world == null) return;
        if (!this.isGrounded()) {
            ParticleMaker.spawnParticle(this.world, "flame", this.x, this.y, this.z, this.xd * 0.05, this.yd * 0.05 - 0.1, this.zd * 0.05, 0);
            ParticleMaker.spawnParticle(this.world, "flame", this.x + this.xd * 0.5, this.y + this.yd * 0.5, this.z + this.zd * 0.5, this.xd * 0.05, this.yd * 0.05 - 0.1, this.zd * 0.05, 0);
            ParticleMaker.spawnParticle(this.world, "smoke", this.x, this.y, this.z, this.xd * 0.05, this.yd * 0.05 - 0.1, this.zd * 0.05, 0);
            ParticleMaker.spawnParticle(this.world, "smoke", this.x + this.xd * 0.5, this.y + this.yd * 0.5, this.z + this.zd * 0.5, this.xd * 0.05, this.yd * 0.05 - 0.1, this.zd * 0.05, 0);
        }
        super.tick();
    }

    @Override
    public void onHit(HitResult hitResult) {
        if (this.world == null) return;
        if (hitResult instanceof HitResult.Entity) {
            Entity hitEntity = ((HitResult.Entity) hitResult).entity;
            if (hitEntity.hurt(this.owner, this.damage, DamageType.FIRE)) {
                if (hitEntity instanceof MobCreeper) {
                    MobCreeper entityCreeper = (MobCreeper) hitEntity;
                    entityCreeper.setTarget(entityCreeper);
                }

                if (!this.world.isClientSide) {
                    this.world.playSoundAtEntity(null, this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
                }

                hitEntity.fireHurt();
                this.remove();
            }
        } else if (hitResult instanceof HitResult.Tile) {
            HitResult.Tile tileHit = (HitResult.Tile) hitResult;
            this.tilePos = new TilePos(tileHit.tilePos.x(), tileHit.tilePos.y(), tileHit.tilePos.z());
            this.inTile = this.world.getBlock(this.tilePos.x, this.tilePos.y, this.tilePos.z);
            this.inData = this.world.getBlockMetadata(this.tilePos.x, this.tilePos.y, this.tilePos.z);
            this.xd = (float) (hitResult.location.x() - this.x);
            this.yd = (float) (hitResult.location.y() - this.y);
            this.zd = (float) (hitResult.location.z() - this.z);
            float f1 = MathHelper.sqrt(this.xd * this.xd + this.yd * this.yd + this.zd * this.zd);
            this.x -= this.xd / f1 * 0.05;
            this.y -= this.yd / f1 * 0.05;
            this.z -= this.zd / f1 * 0.05;
            this.inGroundAction(tileHit.side, this.tilePos.x, this.tilePos.y, this.tilePos.z);
        }
    }

    public void inGroundAction(Side side, int blockX, int blockY, int blockZ) {
        if (this.world == null) return;
        this.world.playSoundAtEntity(null, this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        for (int j = 0; j < 4; ++j) {
            ParticleMaker.spawnParticle(this.world, "item", this.x, this.y, this.z, 0.0, 0.0, 0.0, Items.AMMO_FIREBALL.id);
            ParticleMaker.spawnParticle(this.world, "item", this.x, this.y, this.z, 0.0, 0.0, 0.0, AetherItems.AMMO_ARROW_FLAMING.id);
        }
        blockX += side.offsetX();
        blockY += side.offsetY();
        blockZ += side.offsetZ();
        int blockID = world.getBlockId(blockX, blockY, blockZ);
        if (blockID == 0) {
            world.setBlockWithNotify(blockX, blockY, blockZ, Blocks.FIRE.id());
        }
        this.remove();
    }

    public static Entity getEntity(World world, double x, double y, double z, int meta, boolean hasVelocity, double xd, double yd, double zd, Entity owner) {
        ProjectileArrowFlaming projectile = new ProjectileArrowFlaming(world, x, y, z, meta);
        if (hasVelocity) projectile.setHeading(xd, yd, zd, 1, 0);
        if (owner instanceof Mob) projectile.owner = (Mob) owner;
        if (owner instanceof Player) projectile.doesArrowBelongToPlayer = true;
        return projectile;
    }
}
