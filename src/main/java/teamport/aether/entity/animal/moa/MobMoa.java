package teamport.aether.entity.animal.moa;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.entity.animal.MobAetherAnimalRideable;
import teamport.aether.item.AetherItemTags;
import teamport.aether.item.AetherItems;

public abstract class MobMoa extends MobAetherAnimalRideable {
    public static final int DATA_TAMED_ID = 18;
    protected float flap = 0.0F;
    protected float flapSpeed = 0.0F;
    protected float oFlapSpeed;
    protected float oFlap;
    protected float flapping = 1.0F;
    protected int eggTimer;
    protected Item eggColor;

    protected MobMoa(@Nullable World world) {
        super(world);
        this.setSize(1.0F, 2.0F);
        this.eggTimer = this.random.nextInt(6000) + 6000;
        this.jumpHeight = 1.2f;
        this.stepDownSize = 1.5F;
        this.footSize = 1.5F;
        this.accelerationRate = 100.0F;
        this.mobDrops.add(new WeightedRandomLootObject(Items.FEATHER_CHICKEN.getDefaultStack(), 0, 2));
    }

    @Override
    public int getMaxHealth() {
        return this.getTamed() ? 40 : 16;
    }

    @Override
    public void onGround() {
        if (this.onGround) this.jumpsRemaining = getJumpMaxAmount();
    }

    @Override
    public float getSoundVolume() {
        return 0.5F;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_TAMED_ID, (byte) 0, Byte.class);
    }

    @Override
    public void addAdditionalSaveData(@NonNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Tamed", this.getTamed());
    }

    @Override
    public void readAdditionalSaveData(@NonNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setTamed(tag.getBoolean("Tamed"));
    }

    public boolean getTamed() {
        return (this.entityData.getByte(DATA_TAMED_ID) & 1) != 0;
    }

    public void setTamed(boolean flag) {
        this.entityData.set(DATA_TAMED_ID, flag ? (byte) 1 : (byte) 0);
    }

    @Override
    public void tick() {
        super.tick();
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed += (float) ((this.onGround ? -1 : 4) * 0.3);

        this.flapSpeed = Math.max(0.0F, Math.min(this.flapSpeed, 1.0F));
        if (!this.onGround && this.flapping < 1.0F) this.flapping = 1.0F;
        this.flapping *= 0.9F;
        if (!this.onGround && this.yd < 0.0) this.yd *= 0.6;

        this.flap += this.flapping * 2.0F;

        if (!this.world.isClientSide && --this.eggTimer <= 0) {
            this.world.playSoundAtEntity(null, this, "mob.chickenplop", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.dropItem(this.eggColor.id, 1);
            this.eggTimer = this.random.nextInt(6000) + 6000;
        }
    }

    @Override
    public double getRideHeight() {
        return this.bbHeight - 0.6f;
    }

    @Override
    protected void causeFallDamage(float distance) {
    }

    @Override
    public String getLivingSound() {
        return "aether:mob.moa";
    }

    @Override
    public String getHurtSound() {
        return "aether:mob.moa";
    }

    @Override
    public String getDeathSound() {
        return "aether:mob.moa";
    }

    @Override
    public boolean interact(@NonNull Player player) {
        if (super.interact(player)) {
            return true;
        } else {
            ItemStack heldItem = player.getHeldItem();
            if (heldItem != null && (isFeedableItem(heldItem)) && this.getHealth() < this.getMaxHealth() && heldItem.consumeItem(player) && this.getTamed()) {
                if (heldItem.stackSize <= 0) {
                    player.setHeldItem(null);
                }

                this.heal(4);
                this.world.playSoundAtEntity(player, this, "random.bite", 0.2F + (this.random.nextFloat() - this.random.nextFloat()) * 0.1F, 0.8F + (this.random.nextFloat() - this.random.nextFloat()) * 0.1F);
                return true;
            } else if (this.passenger == player) {
                return false;
            } else if (!this.getSaddled()) {
                return false;
            } else if (this.passenger != null) {
                return false;
            } else if (this.world.isClientSide && this.getTamed()) {
                return true;
            } else {
                if (player.isSneaking()) {
                    this.setSaddled(false);
                    this.setSitting(false);
                    ItemStack toInsert = new ItemStack(Items.SADDLE);
                    player.inventory.insertItem(toInsert, true);
                    if (toInsert.stackSize > 0) {
                        this.dropItem(toInsert, 0.0F);
                    }
                } else {
                    player.startRiding(this);
                }

                return true;
            }
        }
    }

    @Override
    public boolean isFavouriteItem(ItemStack itemStack) {
        return itemStack != null && itemStack.getItem().hasTag(AetherItemTags.MOAS_FAVOURITE_ITEM);
    }

    @Override
    public boolean isFeedableItem(ItemStack itemStack) {
        return itemStack != null && (itemStack.itemID == AetherItems.PETAL_AECHOR.id);
    }

    public float getFlap() {
        return flap;
    }

    public float getFlapSpeed() {
        return flapSpeed;
    }

    public float getOFlapSpeed() {
        return oFlapSpeed;
    }

    public float getOFlap() {
        return oFlap;
    }

    public String getSaddleTexturePath() {
        return String.format("/assets/%s/textures/entity/%s/saddle.png", this.textureIdentifier.namespace(), this.textureIdentifier.value());
    };

    @Override
    public abstract int getJumpMaxAmount();
}
