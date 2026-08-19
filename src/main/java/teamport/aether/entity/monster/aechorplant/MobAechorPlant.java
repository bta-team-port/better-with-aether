package teamport.aether.entity.monster.aechorplant;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.LightLayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import org.joml.Vector3d;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlocks;
import teamport.aether.entity.AetherDeathMessage;
import teamport.aether.entity.MobUtil;
import teamport.aether.entity.monster.MobMonsterAether;
import teamport.aether.entity.projectile.ProjectileNeedle;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.item.AetherItems;
import teamport.aether.item.ItemBucketSkyrootEmpty;

public class MobAechorPlant extends MobMonsterAether implements Enemy, AetherDeathMessage {
    private static final int DATA_HAS_TARGET = 16;

    private int attackCooldown;
    private int smokeTime;
    private boolean hasTarget;
    private float sinage;
    private float sinageO;

    public MobAechorPlant(World world1) {
        super(world1);
        this.setTextureIdentifier("aether", "aechorplant");
        this.sinage = this.random.nextFloat() * 6.0F;
        this.sinageO = this.sinage;
        this.smokeTime = this.attackCooldown = 0;
        this.hasTarget = false;
        this.setSize(0.9F, 0.9F);
        this.scoreValue = 200;
        this.mobDrops.add(new WeightedRandomLootObject(AetherItems.PETAL_AECHOR.getDefaultStack(), 1, 4));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_HAS_TARGET, (byte) 0, Byte.class);
    }

    @Override
    public boolean hurt(Entity attacker, int damage, DamageType type) {
        if (type == DamageType.FIRE) {
            super.hurt(attacker, damage * 2, type);
        }

        return super.hurt(attacker, damage, type);
    }

    @Override
    public boolean collidesWith(Entity entity) {
        return false;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }

    @Override
    public int getMaxHealth() {
        return 14;
    }

    @Override
    public boolean canSpawnHere() {
        TilePos blockPos = new TilePos(MathHelper.floor(this.x), MathHelper.floor(this.bb.minY), MathHelper.floor(this.z));

        if (this.world.getBlockType(blockPos.down(new TilePos())) != AetherBlocks.GRASS_AETHER) {
            return false;
        }

        if (this.world.canBlockSeeSky(blockPos.down(new TilePos()))) {
            return false;
        }

        if (this.world.getSavedLightValue(LightLayer.Block, blockPos) > 7) {
            return false;
        }

        int[] adjacentOffsets = {-1, 0, 1, 0, 0, -1, 0, 1, -1, -1, -1, 1, 1, -1, 1, 1};

        for (int i = 0; i < 8; i++) {
            int offsetX = adjacentOffsets[i * 2];
            int offsetZ = adjacentOffsets[i * 2 + 1];

            TilePos offset = new TilePos(blockPos.x() + offsetX, blockPos.y(), blockPos.z() + offsetZ);
            Block<?> block = this.world.getBlockType(offset);
            if (block.isCubeShaped()) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected boolean isMovementCeased() {
        return true;
    }

    @Override
    protected boolean isMovementBlocked() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void push(@NonNull Entity entity) {
        /* should not be fling*/
        for (int i = 0; i < 8; ++i) {
            double d1 = this.x + (this.random.nextFloat() - this.random.nextFloat()) * 0.5;
            double d2 = this.y + 0.25 + (this.random.nextFloat() - this.random.nextFloat()) * 0.5;
            double d3 = this.z + (this.random.nextFloat() - this.random.nextFloat()) * 0.5;
            double d4 = (this.random.nextFloat() - this.random.nextFloat()) * 0.5;
            double d5 = (this.random.nextFloat() - this.random.nextFloat()) * 0.5;
            ParticleMaker.spawnParticle(world, "portal", d1, d2, d3, d4, 0.25, d5, 0);
        }
    }

    @Override
    protected void updateAI() {
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        int belowY = MathHelper.floor(this.bb.minY) - 1;

        TilePos below = new TilePos(this.x, this.bb.minY - 1, this.z);

        Block<?> belowId = this.world.getBlockType(below);
        Block<?> belowBlock = Blocks.blocksList[belowId.id()];
        double blockTopY = (belowBlock != null) ? (belowY + belowBlock.getBoundsFromState(this.world, new TilePos(below)).maxY()) : (belowY + 1.0);
        double gap = this.bb.minY - blockTopY;
        this.onGround = (belowId != Blocks.AIR) && (gap <= 0.001D);

        if (!this.isAlive()) {
            this.target = null;
            this.hasTarget = false;
            return;
        }

        ++this.entityAge;
        this.tryToDespawn();

        this.sinageO = this.sinage;

        if (!this.world.isClientSide) {
            this.hasTarget = this.target != null;
            this.entityData.set(DATA_HAS_TARGET, (byte) (this.hasTarget ? 1 : 0));
        } else {
            this.hasTarget = this.entityData.getByte(DATA_HAS_TARGET) != 0;
        }

        if (this.hurtTime > 0) {
            this.sinage += 0.9F;
        } else if (this.hasTarget) {
            this.sinage += 0.3F;
        } else {
            this.sinage += 0.1F;
        }

        if (this.target == null) {
            this.target = this.findPlayerToAttack();
        }

        if (this.target != null) {
            if (!this.canEntityBeSeen(this.target)) {
                this.attackCooldown = 0;
            }
            if (!this.target.isAlive() || this.target.distanceTo(this) > 12.0) {
                this.target = null;
                this.attackCooldown = 0;
            } else if (this.attackCooldown >= 20 && this.canEntityBeSeen(this.target) && this.target.distanceTo(this) < 6.5 && this.getHealth() > 0) {
                this.shootTarget(this.target);
                this.attackCooldown = -20;
            }

            if (this.attackCooldown < 20) {
                ++this.attackCooldown;
            }
        }

        if (!this.world.isClientSide) {
            ++this.smokeTime;
            if (this.smokeTime >= (this.hasTarget ? 3 : 8)) {
                this.smokeTime = 0;
                if (this.world.getBlockType(below) != AetherBlocks.GRASS_AETHER) {
                    MobUtil.killMob(this);
                }
            }
        }

        this.xd = 0.0D;
        this.yd = 0.0D;
        this.zd = 0.0D;
    }

    @Override
    public boolean canEntityBeSeen(@NonNull Entity entity) {
        return this.world.checkBlockCollisionBetweenPoints(new Vector3d(this.x, this.y + this.getHeadHeight(), this.z), new Vector3d(entity.x, entity.y + entity.getHeadHeight(), entity.z),
            false, true, false) == null;
    }

    public void shootTarget(Entity target) {
        if (!this.isAlive() || !this.world.getDifficulty().canHostileMobsSpawn() || this.world.isClientSide) {
            return;
        }

        double dX = target.x - this.x;
        double dZ = target.z - this.z;
        double sqrt = Math.sqrt(dX * dX + dZ * dZ + 0.1);
        double d3 = 1.5 / sqrt;
        dX *= d3;
        dZ *= d3;

        ProjectileNeedle needle = new ProjectileNeedle(this.world, this);
        needle.y = this.y + 0.5;

        double h = target.y + target.getHeadHeight() - 0.8 - needle.y;
        float f1 = MathHelper.sqrt(dX * dX + dZ * dZ) * 0.2F;

        needle.setHeading(dX, h + f1, dZ, 0.6F, 12.0F);

        this.world.playSoundAtEntity(null, this, "random.bow", 0.3F, 2.0F / (this.random.nextFloat() * 0.4F + 0.8F));
        this.world.entityJoinedWorld(needle);
    }

    @Override
    public String getHurtSound() {
        return "damage.hurtflesh";
    }

    @Override
    public String getDeathSound() {
        return "damage.fallbig";
    }

    @Override
    public void fling(double xd, double yd, double zd, float pushTime) {
        /* should not be fling*/
        for (int i = 0; i < 8; ++i) {
            double d1 = this.x + (this.random.nextFloat() - this.random.nextFloat()) * 0.5;
            double d2 = this.y + 0.25 + (this.random.nextFloat() - this.random.nextFloat()) * 0.5;
            double d3 = this.z + (this.random.nextFloat() - this.random.nextFloat()) * 0.5;
            double d4 = (this.random.nextFloat() - this.random.nextFloat()) * 0.5;
            double d5 = (this.random.nextFloat() - this.random.nextFloat()) * 0.5;
            ParticleMaker.spawnParticle(world, "portal", d1, d2, d3, d4, 0.25, d5, 0);
        }
    }

    @Override
    public void knockBack(Entity entity, int damage, double xd, double yd) {
        for (int i = 0; i < 8; ++i) {
            double d1 = this.x + (this.random.nextFloat() - this.random.nextFloat()) * 0.5;
            double d2 = this.y + 0.25 + (this.random.nextFloat() - this.random.nextFloat()) * 0.5;
            double d3 = this.z + (this.random.nextFloat() - this.random.nextFloat()) * 0.5;
            double d4 = (this.random.nextFloat() - this.random.nextFloat()) * 0.5;
            double d5 = (this.random.nextFloat() - this.random.nextFloat()) * 0.5;
            ParticleMaker.spawnParticle(world, "portal", d1, d2, d3, d4, 0.25, d5, 0);
        }
    }

    @Override
    public boolean interact(@NonNull Player player) {
        ItemStack itemstack = player.inventory.getCurrentItem();
        if (itemstack != null && itemstack.itemID == AetherItems.BUCKET_SKYROOT.id) {
            ItemBucketSkyrootEmpty.useBucket(player, new ItemStack(AetherItems.BUCKET_SKYROOT_POISON));
            return true;
        } else {
            return super.interact(player);
        }
    }

    @Override
    public void addAdditionalSaveData(@NonNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putShort("AttTime", (short) this.attackCooldown);
    }

    @Override
    public void readAdditionalSaveData(@NonNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.attackCooldown = tag.getShort("AttTime");
    }

    public boolean hasTarget() {
        return hasTarget;
    }

    public float getSinage() {
        return sinage;
    }

    public float getSinageO() {
        return sinageO;
    }
}
