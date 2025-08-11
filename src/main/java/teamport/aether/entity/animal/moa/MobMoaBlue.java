package teamport.aether.entity.animal.moa;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.entity.animal.Creature;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import teamport.aether.entity.animal.MobAetherAnimal;
import teamport.aether.items.AetherItemTags;
import teamport.aether.items.AetherItems;
import teamport.aether.mixin.accessors.EntityAccessor;
import teamport.aether.mixin.accessors.MobAccessor;

public class MobMoaBlue extends MobAetherAnimal implements Creature {
    public float flap = 0.0F;
    public float flapSpeed = 0.0F;
    public float oFlapSpeed;
    public float oFlap;
    public float flapping = 1.0F;
    public int eggTimer;
    public Item eggColor;
    public int jumpsRemaining;
    public boolean jumpPressed;
    public boolean tamed;

    public MobMoaBlue(@Nullable World world) {
        super(world);
        this.setSize(1.0F, 2.0F);
        this.eggTimer = this.random.nextInt(6000) + 6000;
        this.textureIdentifier = NamespaceID.getPermanent("aether", "moa_blue");
        this.jumpsRemaining = 2;
        this.eggColor = AetherItems.EGG_MOA_BLUE;
        this.mobDrops.add(new WeightedRandomLootObject(Items.FEATHER_CHICKEN.getDefaultStack(), 0, 2));
    }

    public MobMoaBlue(@Nullable World world, boolean tamed) {
        super(world);
        this.setSize(1.0F, 2.0F);
        this.eggTimer = this.random.nextInt(6000) + 6000;
        this.textureIdentifier = NamespaceID.getPermanent("aether", "moa_blue");
        this.jumpsRemaining = 2;
        this.eggColor = AetherItems.EGG_MOA_BLUE;
        this.mobDrops.add(new WeightedRandomLootObject(Items.FEATHER_CHICKEN.getDefaultStack(), 0, 2));
        this.tamed = tamed;
    }

    public int getMaxHealth() {
        return 40;
    }

    public int getAmbientSoundInterval() {
        return 240;
    }

    public float getSoundVolume() {
        return 0.5F;
    }

    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(16, (byte)0, Byte.class);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Saddle", this.getSaddled());
        tag.putBoolean("Tamed", this.tamed);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setSaddled(tag.getBoolean("Saddle"));
        this.tamed = tag.getBoolean("Tamed");
    }

    public void tick() {
        super.tick();
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed = (float)((double)this.flapSpeed + (double)(this.onGround ? -1 : 4) * 0.3);

        this.onGround();

        if (this.flapSpeed < 0.0F) {
            this.flapSpeed = 0.0F;
        }

        if (this.flapSpeed > 1.0F) {
            this.flapSpeed = 1.0F;
        }

        if (!this.onGround && this.flapping < 1.0F) {
            this.flapping = 1.0F;
        }

        this.flapping = (float)((double)this.flapping * 0.9);
        if (!this.onGround && this.yd < 0.0) {
            this.yd *= 0.6;
        }

        this.flap += this.flapping * 2.0F;

        if (!this.world.isClientSide && --this.eggTimer <= 0) {
            this.world.playSoundAtEntity(null, this, "mob.chickenplop", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.dropItem(this.eggColor.id, 1);
            this.eggTimer = this.random.nextInt(6000) + 6000;
        }

    }

    public void onGround() {
        if (this.onGround ) {
            this.jumpsRemaining = 2;
        }
    }

    public double getRideHeight() {
        return this.bbHeight - 0.6f;
    }

    public void updateAI() {
        if (!this.world.isClientSide) {
            if (this.passenger != null && this.passenger instanceof Player) {
                this.moveSpeed = 0.0F;
                this.moveStrafing = 0.0F;
                this.isJumping = false;
                this.footSize = 1.5f;
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
                    this.jumpPressed = true;
                } else if (this.isInWater() && ((MobAccessor) mob).getJumping()) {
                    this.yd = 0.5;
                    this.jumpPressed = true;
                    --this.jumpsRemaining;
                } else if (this.jumpsRemaining > 0 && !this.jumpPressed && ((MobAccessor) mob).getJumping()) {
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
                this.footSize = 1.0f;
                super.updateAI();
            }
        }
    }

    public void jump() {
        this.yd = 0.6;
    }

    public void causeFallDamage(float distance) {
    }

    public String getLivingSound() {
        return "aether:mob.moa";
    }

    public String getHurtSound() {
        return "aether:mob.moa";
    }

    public String getDeathSound() {
        return "aether:mob.moa";
    }

    public boolean interact(@NotNull Player player) {
        if (super.interact(player)) {
            return true;
        } else if (!this.getSaddled() || this.world.isClientSide || this.passenger != null && this.passenger != player) {
            return false;
        } else {
            player.startRiding(this);
            return true;
        }
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

    public boolean isFavouriteItem(ItemStack itemStack) {
        return itemStack != null && itemStack.getItem().hasTag(AetherItemTags.MOAS_FAVOURITE_ITEM);
    }

}
