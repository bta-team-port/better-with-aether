package teamport.aether.entity.monster.aechorplant;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemBucketEmpty;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.entity.projectile.ProjectileDart;
import teamport.aether.items.AetherItems;

public class MobAechorPlant extends MobMonster implements Enemy {
    public Mob target;
    public int size;
    public int attackCooldown;
    public int smokeTime;
    public boolean hasTarget;
    public float sinage;

    public MobAechorPlant(World world1) {
        super(world1);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "aechorplant");
        this.size = this.random.nextInt(3) + 1;
        this.sinage = this.random.nextFloat() * 6.0F;
        this.smokeTime = this.attackCooldown = 0;
        this.hasTarget = false;
        this.setSize(0.75F + (float) this.size * 0.125F, 0.5F + (float) this.size * 0.075F);
        this.setPos(this.x, this.y, this.z);
        this.mobDrops.add(new WeightedRandomLootObject(AetherItems.PETAL_AECHOR.getDefaultStack(), 1, 4));

    }

    public int getMaxHealth() {
        return 10 + this.size * 2;
    }

    public int getMaxSpawnedInChunk() {
        return 16;
    }

    public boolean canSpawnHere() {
        int i = MathHelper.floor(this.x);
        int j = MathHelper.floor(this.bb.minY);
        int k = MathHelper.floor(this.z);
        return this.world.getBlockId(i, j - 1, k) == AetherBlocks.GRASS_AETHER.id() && this.world.getBlockLightValue(i, j, k) > 8 && super.canSpawnHere();
    }

    public void onLivingUpdate() {
        if (this.getHealth() > 0 && this.onGround) {
            ++this.entityAge;
            this.tryToDespawn();
        } else {
            super.onLivingUpdate();
        }

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
            Player player = world.getClosestPlayer(x, y, z, 16);
            if (player != null && player.getGamemode().areMobsHostile()) {
                target = player;
            }
        }

        if (this.target != null) {
            if (!this.target.isAlive() || target.distanceTo(this) > 12.0) {
                this.target = null;
                this.attackCooldown = 0;
            } else if (this.attackCooldown >= 20 && canEntityBeSeen(target) && target.distanceTo(this) < 5.5 + size / 2.0) {
                this.shootTarget(target);
                this.attackCooldown = -10;
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

            if (this.world.getBlockId(i, j - 1, k) != AetherBlocks.GRASS_AETHER.id() && this.onGround) {
                this.hurt(this, 999999, DamageType.FALL);
            }
        }

        this.hasTarget = this.target != null;
    }

    protected Entity findPlayerToAttack() {
        Player entityplayer = this.world.getClosestPlayerToEntity(this, 16.0);
        return entityplayer != null && this.canEntityBeSeen(entityplayer) && entityplayer.getGamemode().areMobsHostile() ? entityplayer : null;
    }

    public void shootTarget(Entity target) {
        if (this.world.getDifficulty().canHostileMobsSpawn() && !this.world.isClientSide) {

            double d1 = this.target.x - this.x;
            double d2 = this.target.z - this.z;
            double sqrt = Math.sqrt(d1 * d1 + d2 * d2 + 0.1);
            double d3 = 1.5 / sqrt;
            d1 *= d3;
            d2 *= d3;

            double dX = target.x - this.x;
            double dZ = target.z - this.z;

            ProjectileDart dart = new ProjectileDart(this.world, this, false, 1);
            dart.y = this.y + 0.5;

            double h = target.y + (double)target.getHeadHeight() - 0.8 - dart.y;
            float f1 = MathHelper.sqrt(d1 * d1 + d2 * d2) * 0.2F;

            dart.setHeading(dX, h + (double)f1, dZ, 0.6F, 12.0F);

            this.world.playSoundAtEntity(null, this, "random.bow", 0.3F, 2.0F / (this.random.nextFloat() * 0.4F + 0.8F));
            this.world.entityJoinedWorld(dart);
        }
    }

    public String getHurtSound() {
        return "damage.hurtflesh";
    }

    public String getDeathSound() {
        return "damage.fallbig";
    }

    public void knockBack(Entity entity, int damage, double xd, double yd) {
        for (int i = 0; i < 8; ++i) {
            double d1 = this.x + (double) (this.random.nextFloat() - this.random.nextFloat()) * 0.5;
            double d2 = this.y + 0.25 + (double) (this.random.nextFloat() - this.random.nextFloat()) * 0.5;
            double d3 = this.z + (double) (this.random.nextFloat() - this.random.nextFloat()) * 0.5;
            double d4 = (double) (this.random.nextFloat() - this.random.nextFloat()) * 0.5;
            double d5 = (double) (this.random.nextFloat() - this.random.nextFloat()) * 0.5;
            this.world.spawnParticle("portal", d1, d2, d3, d4, 0.25, d5, 0);
        }

        if (this.getHealth() <= 0) {
            super.knockBack(entity, damage, xd, yd);
        }
    }

    public boolean interact(@NotNull Player player) {
        ItemStack itemstack = player.inventory.getCurrentItem();
        if (itemstack != null && itemstack.itemID == AetherItems.BUCKET_SKYROOT.id) {
            ItemBucketEmpty.useBucket(player, new ItemStack(AetherItems.BUCKET_SKYROOT_POISON));
            return true;
        } else {
            return super.interact(player);
        }
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putShort("AttTime", (short) this.attackCooldown);
        tag.putShort("Size", (short) this.size);
    }

    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.attackCooldown = tag.getShort("AttTime");
        this.size = tag.getShort("Size");
        this.setSize(0.75F + (float) this.size * 0.125F, 0.5F + (float) this.size * 0.075F);
        this.setPos(this.x, this.y, this.z);
    }

}
