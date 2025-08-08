package teamport.aether.entity.phow;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemBucketEmpty;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.item.tag.ItemTags;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import teamport.aether.entity.MobAetherAnimal;
import teamport.aether.items.AetherItems;
import teamport.aether.mixin.accessors.EntityAccessor;
import teamport.aether.mixin.accessors.MobAccessor;

public class MobPhow extends MobAetherAnimal {
    public float wingFold;
    public float wingFoldO;
    public float wingAngle;
    public float wingAngleO;
    public float aimingForFold;
    public int jumps;
    public int jrem;
    public boolean jpress;
    public int ticks;
    public MobPhow(World world) {
        super(world);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "phow");
        this.setSize(0.9F, 1.3F);
        this.mobDrops.add(new WeightedRandomLootObject(Items.LEATHER.getDefaultStack(), 1, 5));
        this.mobDrops.add(new WeightedRandomLootObject(Items.FEATHER_CHICKEN.getDefaultStack(), 0, 2));
    }

    public void tick() {
        super.tick();
        if (this.onGround) {
            this.aimingForFold = 0.1F;
            this.jpress = false;
            this.jrem = this.jumps;
        } else {
            this.aimingForFold = 1.0F;
        }

        this.wingAngleO = this.wingAngle;
        this.wingFoldO = this.wingFold;

        ++this.ticks;
        this.wingAngle = this.wingFold * (float) Math.sin((float) this.ticks / 31.830988F);
        this.wingFold += (this.aimingForFold - this.wingFold) / 5.0F;
        this.fallDistance = 0.0F;
        if (this.yd < -0.2) {
            this.yd = -0.2;
        }

    }

    public double getRideHeight() {
        return this.bbHeight;
    }

    public void updateAI() {
        if (!this.world.isClientSide) {
            if (this.passenger != null && this.passenger instanceof Player) {
                this.moveSpeed = 0.0F;
                this.moveStrafing = 0.0F;
                this.isJumping = false;
                this.footSize = 1.0f;
                ((EntityAccessor) this.passenger).setFallDistance(0.0F);
                this.yRotO = this.yRot = this.passenger.yRot;
                this.xRotO = this.xRot = this.passenger.xRot;
                Player mob = (Player) this.passenger;
                float f = 3.141593F;
                float f1 = f / 180.0F;
                float f5;
                if (((MobAccessor) mob).getForwardVelocity() > 0.1F) {
                    f5 = mob.yRot * f1;
                    this.xd += (double) ((MobAccessor) mob).getForwardVelocity() * -Math.sin(f5) * 0.17499999701976776;
                    this.zd += (double) ((MobAccessor) mob).getForwardVelocity() * Math.cos(f5) * 0.17499999701976776;
                } else if (((MobAccessor) mob).getForwardVelocity() < -0.1F) {
                    f5 = mob.yRot * f1;
                    this.xd += (double) ((MobAccessor) mob).getForwardVelocity() * -Math.sin(f5) * 0.17499999701976776;
                    this.zd += (double) ((MobAccessor) mob).getForwardVelocity() * Math.cos(f5) * 0.17499999701976776;
                }

                if (((MobAccessor) mob).getHorizontalVelocity() > 0.1F) {
                    f5 = mob.yRot * f1;
                    this.xd += (double) ((MobAccessor) mob).getHorizontalVelocity() * Math.cos(f5) * 0.17499999701976776;
                    this.zd += (double) ((MobAccessor) mob).getHorizontalVelocity() * Math.sin(f5) * 0.17499999701976776;
                } else if (((MobAccessor) mob).getHorizontalVelocity() < -0.1F) {
                    f5 = mob.yRot * f1;
                    this.xd += (double) ((MobAccessor) mob).getHorizontalVelocity() * Math.cos(f5) * 0.17499999701976776;
                    this.zd += (double) ((MobAccessor) mob).getHorizontalVelocity() * Math.sin(f5) * 0.17499999701976776;
                }

                if (this.onGround && ((MobAccessor) mob).getJumping()) {
                    this.onGround = false;
                    this.yd = 1.4;
                    this.jpress = true;
                    --this.jrem;
                } else if (this.isInWater() && ((MobAccessor) mob).getJumping()) {
                    this.yd = 0.5;
                    this.jpress = true;
                    --this.jrem;
                } else if (this.jrem > 0 && !this.jpress && ((MobAccessor) mob).getJumping()) {
                    this.yd = 1.2;
                    this.jpress = true;
                    --this.jrem;
                }

                if (this.jpress && !((MobAccessor) mob).getJumping()) {
                    this.jpress = false;
                }

                double d = Math.abs(Math.sqrt(this.xd * this.xd + this.zd * this.zd));
                if (d > 0.375) {
                    double d1 = 0.375 / d;
                    this.xd *= d1;
                    this.zd *= d1;
                }

            } else {
                this.footSize = 0.5f;
                super.updateAI();
            }
        }
    }

    public void jump() {
        this.yd = 0.6;
    }

    public void dropDeathItems() {
        if (this.getSaddled()) {
            this.dropItem(Items.SADDLE.id, 1);
        }

        super.dropDeathItems();
    }

    public boolean getSaddled() {
        return (this.entityData.getByte(16) & 1) != 0;
    }

    public void setSaddled(boolean flag) {
        if (flag) {
            this.entityData.set(16, (byte)1);
        } else {
            this.entityData.set(16, (byte)0);
        }

    }

    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(16, (byte)0, Byte.class);
    }

    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Saddle", this.getSaddled());
    }

    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setSaddled(tag.getBoolean("Saddle"));
    }

    public String getLivingSound() {
        return "mob.cow";
    }

    public String getHurtSound() {
        return "mob.cowhurt";
    }

    public String getDeathSound() {
        return "mob.cowhurt";
    }

    public float getSoundVolume() {
        return 0.4F;
    }

    public boolean interact(@NotNull Player player) {
        ItemStack itemstack = player.inventory.getCurrentItem();
        if (itemstack != null && itemstack.itemID == Items.BUCKET.id) {
            ItemBucketEmpty.useBucket(player, new ItemStack(Items.BUCKET_MILK));
            return true;
        } else if (itemstack != null && itemstack.itemID == AetherItems.BUCKET_SKYROOT.id) {
            ItemBucketEmpty.useBucket(player, new ItemStack(AetherItems.BUCKET_SKYROOT_MILK));
            return true;
        } else if (!this.getSaddled() || this.world.isClientSide || this.passenger != null && this.passenger != player) {
            return false;
        } else {
            player.startRiding(this);
            return true;
        }
    }

    public boolean isFavouriteItem(ItemStack itemStack) {
        return itemStack != null && itemStack.getItem().hasTag(ItemTags.COWS_FAVOURITE_ITEM);
    }
}
