package teamport.aether.entity.aerbunny;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pathfinder.Path;
import org.jetbrains.annotations.NotNull;
import teamport.aether.entity.MobAetherAnimal;
import teamport.aether.items.AetherItemTags;
import teamport.aether.mixin.accessors.EntityAccessor;
import teamport.aether.mixin.accessors.MobAccessor;

import java.util.List;

public class MobAerbunny extends MobAetherAnimal {
    public boolean grab;
    public boolean fear;
    public boolean gotRider;
    public Entity runFrom;
    public float puffiness;
    public MobAerbunny(World world) {
        super(world);
        this.setSize(0.4F, 0.4F);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "aerbunny");
        this.mobDrops.add(new WeightedRandomLootObject(Items.STRING.getDefaultStack(), 1));
    }

    public boolean isFavouriteItem(ItemStack itemStack) {
        return itemStack != null && itemStack.itemID < Blocks.blocksList.length && Blocks.blocksList[itemStack.itemID].hasTag(BlockTags.SHEEPS_FAVOURITE_BLOCK) || itemStack != null && itemStack.getItem().hasTag(AetherItemTags.NATURE_STAFF_FOLLOW);
    }

    public int getMaxHealth() {
        return 4;
    }

    public double getRidingHeight() {
        return this.heightOffset - 1.1f;
    }

    public void tick() {
        if (this.gotRider) {
            this.gotRider = false;
            if (this.vehicle == null) {
                Player entityplayer = (Player) this.findPlayerToRunFrom();
                if (entityplayer != null && this.distanceTo(entityplayer) < 2.0F && entityplayer.passenger == null) {
                    this.startRiding(entityplayer);
                }
            }
        }

        if (this.puffiness > 0.0F) {
            this.puffiness -= 0.1F;
        } else {
            this.puffiness = 0.0F;
        }

        super.tick();
    }

    public void causeFallDamage(float distance) {
    }

    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Fear", this.fear);
        if (this.passenger != null) {
            this.gotRider = true;
        }
        tag.putBoolean("GotRider", this.gotRider);
    }

    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.fear = tag.getBoolean("Fear");
        this.gotRider = tag.getBoolean("GotRider");
    }

    public void onLivingUpdate() {
        int i;
        if (this.onGround) {
            if (this.moveForward != 0.0F) {
                this.jump();
            }
        } else if (this.vehicle != null) {
            if (this.vehicle.isRemoved()) {
                this.startRiding(this.vehicle);
            } else if (!this.vehicle.getPassenger().onGround && !this.vehicle.getPassenger().isInWaterOrRain()) {
                ((EntityAccessor) this.vehicle).setFallDistance(0.0F);
                Entity var10000 = (Entity) this.vehicle;
                var10000.yd += 0.05000000074505806;
                if (((Entity) this.vehicle).yd < -0.22499999403953552 && this.vehicle instanceof Mob && ((MobAccessor) this.vehicle).getJumping()) {
                    ((Mob) this.vehicle).yd = 0.125F;
                    this.cloudPoop();
                    this.puffiness = 1.15F;
                }
            }
        } else if (!this.grab) {
            if (this.moveForward != 0.0F) {
                int j = MathHelper.floor(this.x);
                i = MathHelper.floor(this.bb.minY);
                int k1 = MathHelper.floor(this.bb.minY - 0.5);
                int l1 = MathHelper.floor(this.z);
                if ((this.world.getBlockId(j, i - 1, l1) != 0 || this.world.getBlockId(j, k1 - 1, l1) != 0) && this.world.getBlockId(j, i + 2, l1) == 0 && this.world.getBlockId(j, i + 1, l1) == 0) {
                    if (this.yd < 0.0) {
                        this.cloudPoop();
                        this.puffiness = 0.9F;
                    }

                    this.yd = 0.2;
                }
            }

            if (this.yd < -0.1) {
                this.yd = -0.1;
            }
        }

        if (!this.grab) {
            super.onLivingUpdate();
            if (this.fear && this.random.nextInt(4) == 0) {
                if (this.runFrom != null) {
                    this.runLikeHell();
                    this.world.spawnParticle("splash", this.x, this.y, this.z, 0.0, 0.0, 0.0, 0);
                    if (!this.hasPath()) {
                        this.lookAt(this.runFrom, 30.0F, 30.0F);
                    }

                    if (this.runFrom.removed || this.distanceTo(this.runFrom) > 16.0F) {
                        this.runFrom = null;
                    }
                } else {
                    this.runFrom = this.findPlayerToRunFrom();
                }
            }
        } else if (this.onGround) {
            this.grab = false;
            this.world.playSoundAtEntity(null, this, "aether:mob.aerbunny.land", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.bb.expand(12.0, 12.0, 12.0));

            for (i = 0; i < list.size(); ++i) {
                Entity entity = list.get(i);
                if (entity instanceof MobMonster) {
                    MobMonster entitymobs = (MobMonster) entity;
                    entitymobs.setTarget(this);
                }
            }
        }

        if (this.isInWater()) {
            this.jump();
        }

    }

    public void cloudPoop() {
        double a = this.random.nextFloat() - 0.5F;
        double d = this.x + a * 0.4000000059604645;
        double e = this.bb.minY;
        double f = this.z + a * 0.4000000059604645;
        this.world.spawnParticle("explode", d, e, f, 0.0, -0.07500000298023224, 0.0, 0);
    }

    public boolean hurt(Entity entity, int i, DamageType type) {
        if (this.vehicle == entity) {
            return false;
        } else {
            return super.hurt(entity, i, type);
        }
    }

    public Entity findPlayerToRunFrom() {
        Player entityplayer = this.world.getClosestPlayerToEntity(this, 12.0);
        return entityplayer != null && this.canEntityBeSeen(entityplayer) ? entityplayer : null;
    }

    public void runLikeHell() {
        double a = this.x - this.runFrom.x;
        double b = this.z - this.runFrom.z;
        double crazy = Math.atan2(a, b);
        crazy += (double) (this.random.nextFloat() - this.random.nextFloat()) * 0.75;
        double c = this.x + Math.sin(crazy) * 8.0;
        double d = this.z + Math.cos(crazy) * 8.0;
        int x = MathHelper.floor(c);
        int y = MathHelper.floor(this.bb.minY);
        int z = MathHelper.floor(d);

        for (int q = 0; q < 16; ++q) {
            int i = x + this.random.nextInt(4) - this.random.nextInt(4);
            int j = y + this.random.nextInt(4) - this.random.nextInt(4) - 1;
            int k = z + this.random.nextInt(4) - this.random.nextInt(4);
            if (j > 4 && (this.world.getBlockId(i, j, k) == 0 || this.world.getBlockId(i, j, k) == Blocks.BLOCK_SNOW.id()) && this.world.getBlockId(i, j - 1, k) != 0) {
                Path dogs = this.world.getEntityPathToXYZ(this, i, j, k, 16.0F);
                this.setPathToEntity(dogs);
                break;
            }
        }

    }

    public boolean interact(@NotNull Player entityplayer) {
        if (!this.world.isClientSide) {
            if (!entityplayer.isSneaking()) {
                if (this.vehicle == entityplayer) {
                    this.grab = false;
                    this.ejectRider();
                    if (this.vehicle != null) {
                        this.vehicle = null;
                    }
                    return true;
                }

                if (entityplayer.getPassenger() instanceof MobAerbunny) {
                    return false;
                }

                this.startRiding(entityplayer);
                this.grab = true;
                this.world.playSoundAtEntity(null, this, "aether:mob.aerbunny.lift", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                this.xd = entityplayer.xd * 5.0;
                this.yd = entityplayer.yd / 2.0 + 0.5;
                this.zd = entityplayer.zd * 5.0;
                this.isJumping = false;
                this.moveForward = 0.0F;
                this.moveStrafing = 0.0F;
                this.setPathToEntity(null);
                return true;
            }
            super.interact(entityplayer);
        }
        return false;
    }

    public String getLivingSound() {
        return "aether:mob.aerbunny.lift";
    }

    public String getHurtSound() {
        return "aether:mob.aerbunny.hurt";
    }

    public String getDeathSound() {
        return "aether:mob.aerbunny.death";
    }
    
}
