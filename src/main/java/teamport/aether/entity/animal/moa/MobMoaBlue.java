package teamport.aether.entity.animal.moa;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.client.Minecraft;
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
import teamport.aether.entity.AetherRideable;
import teamport.aether.entity.AetherJumpAmount;
import teamport.aether.entity.animal.MobAetherAnimal;
import teamport.aether.items.AetherItemTags;
import teamport.aether.items.AetherItems;

import teamport.aether.mixin.accessors.EntityAccessor;
import teamport.aether.net.message.AetherRideableNetworkMessage;
import turniplabs.halplibe.helper.EnvironmentHelper;
import turniplabs.halplibe.helper.network.NetworkHandler;


public class MobMoaBlue extends MobAetherAnimal implements Creature, AetherJumpAmount, AetherRideable {
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


    double xdChange = 0;
    double zdChange = 0;

    boolean playerUsedJump = false;


    public MobMoaBlue(@Nullable World world) {
        super(world);
        this.setSize(1.0F, 2.0F);
        this.eggTimer = this.random.nextInt(6000) + 6000;
        this.textureIdentifier = NamespaceID.getPermanent("aether", "moa_blue");
        this.jumpsRemaining = getJumpMaxAmount();
        this.eggColor = AetherItems.EGG_MOA_BLUE;
        this.mobDrops.add(new WeightedRandomLootObject(Items.FEATHER_CHICKEN.getDefaultStack(), 0, 2));
    }

    public MobMoaBlue(@Nullable World world, boolean tamed) {
        super(world);
        this.setSize(1.0F, 2.0F);
        this.eggTimer = this.random.nextInt(6000) + 6000;
        this.textureIdentifier = NamespaceID.getPermanent("aether", "moa_blue");
        this.jumpsRemaining = getJumpMaxAmount();
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

        vehicleMovement();
    }

    @Override
    public void controlEntity(float moveForward, float moveStrafe, boolean isJumping, float xRot, float yRot) {
        if (EnvironmentHelper.isClientWorld()) {
            NetworkHandler.sendToServer(
                new AetherRideableNetworkMessage(moveForward, moveStrafe, isJumping, xRot, yRot)
            );
        }

        float yawDeg = (float) (yRot * (Math.PI/180));
        float step = 0.175F;

        if (moveForward > 0.1F) {
            xdChange += (double) moveForward * -Math.sin(yawDeg) * step;
            zdChange += (double) moveForward * Math.cos(yawDeg) * step;

        } else if (moveForward < -0.1F) {
            xdChange += (double) moveForward * -Math.sin(yawDeg) * step;
            zdChange += (double) moveForward * Math.cos(yawDeg) * step;
        }

        if (moveStrafe > 0.1F) {
            xdChange += (double) moveStrafe * Math.cos(yawDeg) * step;
            zdChange += (double) moveStrafe * Math.sin(yawDeg) * step;

        } else if (moveStrafe < -0.1F) {
            xdChange += (double) moveStrafe * Math.cos(yawDeg) * step;
            zdChange += (double) moveStrafe * Math.sin(yawDeg) * step;
        }

        if (isJumping && !jumpPressed) {
            playerUsedJump = true;
            jumpPressed = true;
        }

        if (this.jumpPressed && !isJumping) {
            this.jumpPressed = false;
        }
    }

    public void vehicleMovement() {
        if (passenger == null || !(this.passenger instanceof Player)) return;
        Player player = (Player) this.passenger;

        player.handleSpecialVehicleControl();

        xd += xdChange;
        zd += zdChange;
        xdChange = 0.0;
        zdChange = 0.0;

        if (playerUsedJump) {
            boolean canJump = this.jumpsRemaining > 0;

            if (this.onGround) {
                yd = 1.4;
                this.onGround = false;
            }
            else {
                if (isInWater()) yd = 0.5;
                else if (canJump) yd = 1.2;
                --this.jumpsRemaining;
            }

            if (isInWater() || canJump) {
                world.playSoundAtEntity(null, this, "aether:mob.wingflap", 2.0f, 1.0f);
            }
        }

        playerUsedJump = false;

        player.sendSpecialVehiclePacket();

        double horizontalSpeed = Math.abs(Math.sqrt(this.xd * this.xd + this.zd * this.zd));
        if (horizontalSpeed > 0.375) {
            double normal = 0.375 / horizontalSpeed;
            this.xd *= normal;
            this.zd *= normal;
        }
    }

    public void updateAI() {
        if (this.passenger != null && this.passenger instanceof Player) {
            this.moveSpeed = 0.0F;
            this.moveStrafing = 0.0F;
            this.isJumping = false;
            this.footSize = 1.5f;

            this.yRotO = this.yRot = this.passenger.yRot;
            this.xRotO = this.xRot = this.passenger.xRot;

            Player player = (Player) this.passenger;
            ((EntityAccessor) player).setFallDistance(0.0F);
        } else {
            this.footSize = 1.0f;
            super.updateAI();
        }
    }

    public void onGround() {
        if (this.onGround ) {
            this.jumpsRemaining = getJumpMaxAmount();
        }
    }

    public double getRideHeight() {
        return this.bbHeight - 0.6f;
    }

    @Override
    protected void jump() { this.yd = 0.6; }

    @Override
    protected void causeFallDamage(float distance) {}

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
        if (super.interact(player)) return true;

        //if (!this.getSaddled() || this.world.isClientSide) return false;
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
            this.entityData.set(16, (byte)1);
        } else {
            this.entityData.set(16, (byte)0);
        }

    }

    public boolean isFavouriteItem(ItemStack itemStack) {
        return itemStack != null && itemStack.getItem().hasTag(AetherItemTags.MOAS_FAVOURITE_ITEM);
    }

    @Override
    public int getJumpMaxAmount() {
        return 3;
    }

    @Override
    public int getJumpAmount() {
        return jumpsRemaining;
    }
}
