package teamport.aether.entity.monster.aechorplant;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.monster.MobCreeper;
import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemBucketEmpty;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.entity.projectile.ProjectileDart;
import teamport.aether.items.AetherItems;

import java.util.List;

public class MobAechorPlant extends MobMonster implements Enemy {
    public Mob target;
    public int size;
    public int attTime;
    public int smokeTime;
    public boolean seeprey;
    public boolean grounded;
    public boolean noDespawn;
    public float sinage;
    public int poisonLeft;

    public MobAechorPlant(World world1) {
        super(world1);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "aechorplant");
        this.size = this.random.nextInt(3) + 1;
        this.sinage = this.random.nextFloat() * 6.0F;
        this.smokeTime = this.attTime = 0;
        this.seeprey = false;
        this.setSize(0.75F + (float) this.size * 0.125F, 0.5F + (float) this.size * 0.075F);
        this.setPos(this.x, this.y, this.z);
        this.poisonLeft = 2;
        this.mobDrops.add(new WeightedRandomLootObject(AetherItems.PETAL_AECHOR.getDefaultStack(), 1, 4));

    }

    @Override
    public boolean isPushable() {
        return false;
    }


    public int getMaxHealth() {
        return 10 + this.size * 2;
    }

    public int getMaxSpawnedInChunk() {
        return 3;
    }

    public boolean canSpawnHere() {
        int i = MathHelper.floor(this.x);
        int j = MathHelper.floor(this.bb.minY);
        int k = MathHelper.floor(this.z);
        return this.world.getBlockId(i, j - 1, k) == AetherBlocks.GRASS_AETHER.id() && this.world.getBlockLightValue(i, j, k) > 8 && super.canSpawnHere();
    }

    public void onLivingUpdate() {
        if (this.getHealth() > 0 && this.grounded) {
            ++this.entityAge;
            this.tryToDespawn();
        } else {
            super.onLivingUpdate();
            if (this.getHealth() <= 0) {
                return;
            }
        }

        if (this.onGround) {
            this.grounded = true;
        }

        if (this.hurtTime > 0) {
            this.sinage += 0.9F;
        } else if (this.seeprey) {
            this.sinage += 0.3F;
        } else {
            this.sinage += 0.1F;
        }

        if (this.sinage > 6.283186F) {
            this.sinage -= 6.283186F;
        }

        int j;
        if (this.target == null) {
            label107:
            {
                List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.bb.expand(10.0, 10.0, 10.0));
                j = 0;

                Entity entity1;
                while (true) {
                    if (j >= list.size()) {
                        break label107;
                    }

                    entity1 = list.get(j);
                    if (entity1 instanceof Mob && !(entity1 instanceof MobAechorPlant) && !(entity1 instanceof MobCreeper)) {
                        if (!(entity1 instanceof Player)) {
                            break;
                        }

                        boolean flag = false;
                        if (!flag) {
                            break;
                        }
                    }

                    ++j;
                }

                this.target = (Mob) entity1;
            }
        }

        if (this.target != null) {
            if (this.target.isAlive() && !((double) this.target.distanceTo(this) > 12.0)) {
                this.target = null;
                this.attTime = 0;
            }

            if (this.target != null && this.attTime >= 20 && this.canEntityBeSeen(this.target) && (double) this.target.distanceTo(this) < 5.5 + (double) this.size / 2.0) {
                this.shootTarget();
                this.attTime = -10;
            }

            if (this.attTime < 20) {
                ++this.attTime;
            }
        }

        ++this.smokeTime;
        if (this.smokeTime >= (this.seeprey ? 3 : 8)) {
            this.smokeTime = 0;
            int i = MathHelper.floor(this.x);
            j = MathHelper.floor(this.bb.minY);
            int k = MathHelper.floor(this.z);
            if (this.world.getBlockId(i, j - 1, k) != AetherBlocks.GRASS_AETHER.id() && this.grounded) {
                this.removed = true;
                this.dropDeathItems();
            }
        }

        this.seeprey = this.target != null;
    }

    public void remove() {
        if (!this.noDespawn || this.getHealth() <= 0) {
            super.remove();
        }

    }

    public void shootTarget() {
        if (this.world.getDifficulty().canHostileMobsSpawn() && !this.world.isClientSide) {
            double d1 = this.target.x - this.x;
            double d2 = this.target.z - this.z;
            double sqrt = Math.sqrt(d1 * d1 + d2 * d2 + 0.1);
            double d3 = 1.5 / sqrt;
            double d4 = 0.1 + sqrt * 0.5 + (this.y - this.target.y) * 0.25;
            d1 *= d3;
            d2 *= d3;
            ProjectileDart dart = new ProjectileDart(this.world, 1);
            dart.y = this.y + 0.5;
            this.world.playSoundAtEntity(null, this, "random.bow", 0.3F, 2.0F / (this.random.nextFloat() * 0.4F + 0.8F));
            dart.setHeading(d1, d4, d2, 0.285F + (float) d4 * 0.05F, 1.0F);
            this.world.entityJoinedWorld(dart);
        }
    }

    public void attackEntity(@NotNull Entity entity, float distance) {
        if (distance < 10.0F) {
            double d = entity.x - this.x;
            double d1 = entity.z - this.z;
                if (!this.world.isClientSide) {
                    ProjectileDart dart = new ProjectileDart(this.world, this, false, 1);
                    double d2 = entity.y + (double)entity.getHeadHeight() - 0.8 - dart.y;
                    float f1 = MathHelper.sqrt(d * d + d1 * d1) * 0.2F;
                    world.playSoundAtEntity(null, this, "random.bow", 0.3F, 2.0F / (random.nextFloat() * 0.4F + 0.8F));
                    dart.setHeading(d, d2 + (double)f1, d1, 0.6F, 12.0F);
                    this.world.entityJoinedWorld(dart);
                }

            this.yRot = (float)(Math.atan2(d1, d) * 180.0 / Math.PI) - 90.0F;
            this.hasAttacked = true;
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

    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Grounded", this.grounded);
        tag.putBoolean("NoDespawn", this.noDespawn);
        tag.putShort("AttTime", (short) this.attTime);
        tag.putShort("Size", (short) this.size);
    }

    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.grounded = tag.getBoolean("Grounded");
        this.noDespawn = tag.getBoolean("NoDespawn");
        this.attTime = tag.getShort("AttTime");
        this.size = tag.getShort("Size");
        this.setSize(0.75F + (float) this.size * 0.125F, 0.5F + (float) this.size * 0.075F);
        this.setPos(this.x, this.y, this.z);
    }

}
