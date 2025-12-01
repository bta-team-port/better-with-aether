package teamport.aether.entity.monster.aechorplant;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.LightLayer;
import net.minecraft.core.item.ItemBucketEmpty;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.entity.AetherDeathMessage;
import teamport.aether.entity.MobUtil;
import teamport.aether.entity.monster.MobMonsterAether;
import teamport.aether.entity.projectile.ProjectileNeedle;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.items.AetherItems;

public class MobAechorPlant extends MobMonsterAether implements Enemy, AetherDeathMessage {
    private int attackCooldown;
    private int smokeTime;
    private boolean hasTarget;
    private float sinage;

    public MobAechorPlant(World world1) {
        super(world1);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "aechorplant");
        this.sinage = this.random.nextFloat() * 6.0F;
        this.smokeTime = this.attackCooldown = 0;
        this.hasTarget = false;
        this.setSize(1.0F, 1.0F);
        this.scoreValue = 200;
        this.mobDrops.add(new WeightedRandomLootObject(AetherItems.PETAL_AECHOR.getDefaultStack(), 1, 4));
    }

    @Override
    public boolean hurt(Entity attacker, int damage, DamageType type) {
        if (type == DamageType.FIRE) {
            super.hurt(attacker, damage * 2, type);
        }

        return super.hurt(attacker, damage, type);
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
        if (this.world == null) return false;
        int x = MathHelper.floor(this.x);
        int y = MathHelper.floor(this.bb.minY);
        int z = MathHelper.floor(this.z);

        if (this.world.getBlockId(x, y - 1, z) != AetherBlocks.GRASS_AETHER.id()) {
            return false;
        }

        if (this.world.getSavedLightValue(LightLayer.Block, x, y, z) > 7) {
            return false;
        }

        int[] adjacentOffsets = {-1, 0, 1, 0, 0, -1, 0, 1, -1, -1, -1, 1, 1, -1, 1, 1};

        for (int i = 0; i < 8; i++) {
            int offsetX = adjacentOffsets[i * 2];
            int offsetZ = adjacentOffsets[i * 2 + 1];
            int blockId = this.world.getBlockId(x + offsetX, y, z + offsetZ);
            Block<?> block = Blocks.blocksList[blockId];
            if (block != null && block.isCubeShaped()) {
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
    public void push(Entity entity) {
    }

    @Override
    protected void updateAI() {
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.world == null) return;
        int belowX = MathHelper.floor(this.x);
        int belowY = MathHelper.floor(this.bb.minY) - 1;
        int belowZ = MathHelper.floor(this.z);
        int belowId = this.world.getBlockId(belowX, belowY, belowZ);
        Block<?> belowBlock = Blocks.blocksList[belowId];
        double blockTopY = (belowBlock != null) ? (belowY + belowBlock.getBlockBoundsFromState(this.world, belowX, belowY, belowZ).maxY) : (belowY + 1.0);
        double gap = this.bb.minY - blockTopY;
        this.onGround = (belowId != 0) && (gap <= 0.001D);

        if (!this.isAlive()) {
            this.target = null;
            this.hasTarget = false;
            return;
        }

        ++this.entityAge;
        this.tryToDespawn();

        if (this.hurtTime > 0) {
            this.sinage += 0.9F;
        } else if (this.hasTarget) {
            this.sinage += 0.3F;
        } else {
            this.sinage += 0.1F;
        }

        if (this.sinage > 6.283186F) {
            this.sinage -= 6.283186F;
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

        ++this.smokeTime;
        if (this.smokeTime >= (this.hasTarget ? 3 : 8)) {
            this.smokeTime = 0;
            int i = MathHelper.floor(this.x);
            int j = MathHelper.floor(this.bb.minY);
            int k = MathHelper.floor(this.z);

            if (!this.onGround || this.world.getBlockId(i, j - 1, k) != AetherBlocks.GRASS_AETHER.id()) {
                MobUtil.killMob(this);
            }
        }

        this.hasTarget = this.target != null;

        this.xd = 0.0D;
        this.yd = 0.0D;
        this.zd = 0.0D;
    }

    @Override
    public boolean canEntityBeSeen(Entity entity) {
        return this.world != null && this.world.checkBlockCollisionBetweenPoints(Vec3.getTempVec3(this.x, this.y + this.getHeadHeight(), this.z), Vec3.getTempVec3(entity.x, entity.y + entity.getHeadHeight(), entity.z),
            false, true, false) == null;
    }

    public void shootTarget(Entity target) {
        if (!this.isAlive() || this.world == null || !this.world.getDifficulty().canHostileMobsSpawn() || this.world.isClientSide) {
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
            ItemBucketEmpty.useBucket(player, new ItemStack(AetherItems.BUCKET_SKYROOT_POISON));
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
}
