package teamport.aether.entity.animal.moa;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.entity.animal.MobAetherAnimalRideable;
import teamport.aether.items.AetherItemTags;
import teamport.aether.items.AetherItems;

public class MobMoaBlue extends MobAetherAnimalRideable {
    public float flap = 0.0F;
    public float flapSpeed = 0.0F;
    public float oFlapSpeed;
    public float oFlap;
    public float flapping = 1.0F;
    public int eggTimer;
    public Item eggColor;
    public boolean tamed;

    public MobMoaBlue(@Nullable World world) {
        super(world);
        this.setSize(1.0F, 2.0F);
        this.eggTimer = this.random.nextInt(6000) + 6000;
        this.textureIdentifier = NamespaceID.getPermanent("aether", "moa_blue");
        this.rideFootSize = 1.5f;
        this.eggColor = AetherItems.EGG_MOA_BLUE;
        this.mobDrops.add(new WeightedRandomLootObject(Items.FEATHER_CHICKEN.getDefaultStack(), 0, 2));
    }

    public MobMoaBlue(@Nullable World world, boolean tamed) {
        super(world);
        this.setSize(1.0F, 2.0F);
        this.eggTimer = this.random.nextInt(6000) + 6000;
        this.textureIdentifier = NamespaceID.getPermanent("aether", "moa_blue");
        this.eggColor = AetherItems.EGG_MOA_BLUE;
        this.mobDrops.add(new WeightedRandomLootObject(Items.FEATHER_CHICKEN.getDefaultStack(), 0, 2));
        this.tamed = tamed;
    }

    public boolean hurt(Entity attacker, int damage, DamageType type) {
        if (attacker == this.passenger) {
            return false;
        }
        return super.hurt(attacker, damage, type);
    }

    public void setTamed(boolean tamed) {
        this.tamed = tamed;
    }

    public int getMaxHealth() {
        return this.tamed ? 40 : 16;
    }

    public float getSoundVolume() {
        return 0.5F;
    }

    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(16, (byte) 0, Byte.class);
    }

    @Override
    public void addAdditionalSaveData(@NonNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Saddle", this.getSaddled());
        tag.putBoolean("Tamed", this.tamed);
    }

    @Override
    public void readAdditionalSaveData(@NonNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setSaddled(tag.getBoolean("Saddle"));
        this.tamed = tag.getBoolean("Tamed");
    }

    public void tick() {
        super.tick();
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed = (float) ((double) this.flapSpeed + (double) (this.onGround ? -1 : 4) * 0.3);

        if (this.flapSpeed < 0.0F) {
            this.flapSpeed = 0.0F;
        }

        if (this.flapSpeed > 1.0F) {
            this.flapSpeed = 1.0F;
        }

        if (!this.onGround && this.flapping < 1.0F) {
            this.flapping = 1.0F;
        }

        this.flapping = (float) ((double) this.flapping * 0.9);
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

    public double getRideHeight() {
        return this.bbHeight - 0.6f;
    }

    @Override
    protected void jump() {
        this.yd = 0.6;
    }

    @Override
    protected void causeFallDamage(float distance) {
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

    public boolean interact(@NonNull Player player) {
        if (super.interact(player)) return true;

        if (player.getHeldItem() != null && player.getHeldItem().itemID == AetherItems.PETAL_AECHOR.id && tamed && player.isSneaking() && this.getHealth() < this.getMaxHealth()) {
            if (player.getGamemode().consumeBlocks()) {
                player.swingItem();
                player.getHeldItem().stackSize--;
            }
            this.heal(6);
        }

        if (player.isSneaking()) return false;
        if (!this.getSaddled() || this.world.isClientSide) return false;
        if (this.passenger != null && this.passenger != player) return false;

        player.startRiding(this);
        return true;
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
            this.entityData.set(16, (byte) 1);
        } else {
            this.entityData.set(16, (byte) 0);
        }

    }

    public boolean isFavouriteItem(ItemStack itemStack) {
        return itemStack != null && itemStack.getItem().hasTag(AetherItemTags.MOAS_FAVOURITE_ITEM);
    }
}
