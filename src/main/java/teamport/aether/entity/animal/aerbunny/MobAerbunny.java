package teamport.aether.entity.animal.aerbunny;

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
import org.jetbrains.annotations.NotNull;
import teamport.aether.entity.animal.MobAetherAnimal;
import teamport.aether.items.AetherItemTags;
import teamport.aether.mixin.accessors.EntityAccessor;
import teamport.aether.mixin.accessors.MobAccessor;

public class MobAerbunny extends MobAetherAnimal {
    public boolean grab;
    public boolean gotRider;
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
        if (this.passenger != null) {
            gotRider = true;
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
        tag.putBoolean("GotRider", gotRider);
    }

    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        gotRider = tag.getBoolean("GotRider");
    }

    public void onLivingUpdate() {
        if (onGround && this.moveForward != 0.0F) {
            this.jump();
        }

        if (this.vehicle != null) {
            if (this.vehicle.isRemoved()) {
                this.startRiding(this.vehicle);
            } else {
                Entity passenger = this.vehicle.getPassenger();
                if (!passenger.onGround && !passenger.isInWaterOrRain()) {
                    ((EntityAccessor) this.vehicle).setFallDistance(0.0F);
                    ((Mob) this.vehicle).yd += 0.05F;
                    if (this.vehicle instanceof Mob && ((Mob) this.vehicle).yd < -0.225F && ((MobAccessor) this.vehicle).getJumping()) {
                        ((Mob) this.vehicle).yd = 0.125F;
                        this.cloudPoop();
                        this.puffiness = 1.15F;
                    }
                }
            }
        } else if (!grab) {
            if (this.moveForward != 0.0F) {
                int x = MathHelper.floor(this.x);
                int y = MathHelper.floor(this.bb.minY);
                int z = MathHelper.floor(this.z);
                if ((this.world.getBlockId(x, y - 1, z) != 0 || this.world.getBlockId(x, y - 2, z) != 0) &&
                        this.world.getBlockId(x, y + 1, z) == 0 && this.world.getBlockId(x, y + 2, z) == 0) {
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

        if (grab && onGround) {
            grab = false;
            this.world.playSoundAtEntity(null, this, "aether:mob.aerbunny.land", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            for (Entity entity : this.world.getEntitiesWithinAABBExcludingEntity(this, this.bb.expand(12.0, 12.0, 12.0))) {
                if (entity instanceof MobMonster) {
                    ((MobMonster) entity).setTarget(this);
                }
            }
        }

        if (this.isInWater()) {
            this.jump();
        }

        super.onLivingUpdate();
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
            return super.hurt(entity, i ,type);
        }
    }

    public boolean interact(@NotNull Player entityplayer) {
        if (!this.world.isClientSide) {
            if (!entityplayer.isSneaking()) {
                if (this.vehicle == entityplayer) {
                    grab = false;
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
                grab = true;
                this.world.playSoundAtEntity(null, this, "aether:mob.aerbunny.lift", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                this.isJumping = false;
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
