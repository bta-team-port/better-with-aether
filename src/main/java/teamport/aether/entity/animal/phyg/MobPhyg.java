package teamport.aether.entity.animal.phyg;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import teamport.aether.AetherAchievements;
import teamport.aether.entity.EntityJumpAmount;
import teamport.aether.entity.animal.MobAetherAnimal;
import teamport.aether.items.AetherItemTags;
import teamport.aether.mixin.accessors.EntityAccessor;
import teamport.aether.mixin.accessors.MobAccessor;

import java.util.ArrayList;
import java.util.List;

public class MobPhyg extends MobAetherAnimal implements EntityJumpAmount {
    public float wingFold;
    public float wingFoldO;
    public float wingAngle;
    public float wingAngleO;
    public float aimingForFold;
    public int jumpsRemaining;
    public boolean jumpPressed;
    public int ticks;
    public List<WeightedRandomLootObject> burningMobDrops = new ArrayList<>();

    public MobPhyg(World world) {
        super(world);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "phyg");
        this.setSize(0.9F, 0.9F);
        this.jumpsRemaining = 1;
        this.mobDrops.add(new WeightedRandomLootObject(Items.FOOD_PORKCHOP_RAW.getDefaultStack(), 1, 2));
        this.mobDrops.add(new WeightedRandomLootObject(Items.FEATHER_CHICKEN.getDefaultStack(), 0, 2));
        this.burningMobDrops.add(new WeightedRandomLootObject(Items.FOOD_PORKCHOP_COOKED.getDefaultStack(), 1, 2));
    }

    public void tick() {
        super.tick();
        if (this.onGround) {
            this.aimingForFold = 0.1F;
            this.jumpPressed = false;
            this.jumpsRemaining = getJumpMaxAmount();
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

    public void onGround() {
        if (this.onGround ) {
            this.jumpsRemaining = getJumpMaxAmount();
        }
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
                    world.playSoundAtEntity(null, this, "aether:mob.wingflap", 2.0f, 1.0f);
                    this.onGround = false;
                    this.yd = 1.4;
                    this.jumpPressed = true;
                } else if (this.isInWater() && ((MobAccessor) mob).getJumping()) {
                    world.playSoundAtEntity(null, this, "aether:mob.wingflap", 2.0f, 1.0f);
                    this.yd = 0.5;
                    this.jumpPressed = true;
                    --this.jumpsRemaining;
                } else if (this.jumpsRemaining > 0 && !this.jumpPressed && ((MobAccessor) mob).getJumping()) {
                    world.playSoundAtEntity(null, this, "aether:mob.wingflap", 2.0f, 1.0f);
                    this.yd = 1.2;
                    this.jumpPressed = true;
                    --this.jumpsRemaining;
                }

                if (this.jumpPressed && !((MobAccessor) mob).getJumping()) {
                    this.jumpPressed = false;
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

    public void defineSynchedData() {
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
        return "mob.pig";
    }

    public String getHurtSound() {
        return "mob.pig";
    }

    public String getDeathSound() {
        return "mob.pigdeath";
    }

    public boolean interact(@NotNull Player player) {
        if (super.interact(player)) {
            return true;
        } else if (!this.getSaddled() || this.world.isClientSide || this.passenger != null && this.passenger != player) {
            return false;
        } else {
            player.startRiding(this);
            player.triggerAchievement(AetherAchievements.PHYG);
            return true;
        }
    }

    public void dropDeathItems() {
        if (this.getSaddled()) {
            this.dropItem(Items.SADDLE.id, 1);
        }

        super.dropDeathItems();
    }

    public List<WeightedRandomLootObject> getMobDrops() {
        return this.remainingFireTicks > 0 ? this.burningMobDrops : this.mobDrops;
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

    public boolean isFavouriteItem(ItemStack itemStack) {
        return itemStack != null && itemStack.itemID < Blocks.blocksList.length && Blocks.blocksList[itemStack.itemID].hasTag(BlockTags.PIGS_FAVOURITE_BLOCK) || itemStack != null && itemStack.getItem().hasTag(AetherItemTags.NATURE_STAFF_FOLLOW);
    }

    @Override
    public int getJumpMaxAmount() {
        return 1;
    }

    @Override
    public int getJumpAmount() {
        return jumpsRemaining;
    }
}
