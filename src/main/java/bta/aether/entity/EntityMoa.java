package bta.aether.entity;

import bta.aether.AetherBlockTags;
import bta.aether.block.AetherBlocks;
import bta.aether.item.AetherItems;
import com.mojang.nbt.CompoundTag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.option.GameSettings;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.EntityChicken;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.IVehicle;
import net.minecraft.core.world.World;

public class EntityMoa extends EntityChicken implements IVehicle {

    public final int jumpMaxAmount = 3;
    private int jumpAmount = jumpMaxAmount;
    private boolean jumpLastState = false;

    public int jumpCoolDownMax = 25;
    public int jumpCoolDown;

    public int tameCoolDownMax = 4500;
    public int tameCoolDown = 0;
    public int tameAttempts = 0;
    public boolean isTamed = false;
    public boolean hasSaddle = false;

    public boolean isBeingRidden = false;
    public boolean isChild = false;

    public EntityPlayer rider = null;

    public EntityMoa(World world) {
        super(world);
        this.setSize(1.5f, 1.5f);
    }

    public String getEntityTexture() {
        if (hasSaddle) return "/Jar/aether/mobs/BlueMoaSaddle.png";
        return "/Jar/aether/mobs/BlueMoa.png";
    }
    public String getDefaultEntityTexture() {
        if (hasSaddle) return "/Jar/aether/mobs/BlueMoaSaddle.png";
        return "/Jar/aether/mobs/BlueMoa.png";
    }

    @Override
    public double getRideHeight() {
        return 1.6;
    }

    @Override
    protected void causeFallDamage(float f) {
    }

    @Override
    protected boolean canDespawn() {
        return isTamed;
    }

    public float getYRotDelta() {
        return isBeingRidden ? 0 : super.getYRotDelta();
    }

    public float getXRotDelta(){
        return isBeingRidden ? 0 : super.getXRotDelta();
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (rider != null) {
            ((IPlayerJumpAmount) rider).aether$setJumpAmount(jumpAmount);
            this.yRot = this.rider.yRot;
            this.xRot = this.rider.xRot;

            GameSettings gameSettings = Minecraft.getMinecraft(rider.getClass()).gameSettings;

            if (gameSettings.keyForward.isPressed()) {
                float modifier = this.onGround ? 1 : 0.5f;
                this.move(rider.getLookAngle().xCoord * modifier, 0, rider.getLookAngle().zCoord * modifier);
            }

            if (gameSettings.keyJump.isPressed()) {
                if (!this.onGround && this.jumpAmount > 0 && this.jumpLastState != gameSettings.keyJump.isPressed()) {
                    this.yd += 1;
                    this.spawnCloudParticles();
                    this.jumpAmount--;
                    this.jumpCoolDown = this.jumpCoolDownMax;
                    ((IPlayerJumpAmount) this.rider).aether$setJumpAmount(this.jumpAmount);
                }

                if (this.onGround) {
                    jump();
                    this.jumpCoolDown = this.jumpCoolDownMax;
                }

            }
            this.jumpLastState = gameSettings.keyJump.isPressed();

            if (rider.vehicle == null) {
                ((IPlayerJumpAmount) rider).aether$setJumpMaxAmount(0);
                this.rider = null;
            }
        }

        if (this.tameCoolDown > 0) this.tameCoolDown--;
        if (this.jumpCoolDown > 0) this.jumpCoolDown--;
        if (this.onGround) this.jumpAmount = this.jumpMaxAmount;
    }

    @Override
    protected boolean isMovementCeased() {
        return this.isBeingRidden;
    }

    public boolean getCanSpawnHere() {
        int x = MathHelper.floor_double(this.x);
        int y = MathHelper.floor_double(this.bb.minY);
        int z = MathHelper.floor_double(this.z);

        return this.world.getBlock(x, y - 1, z).hasTag(AetherBlockTags.PASSIVE_MOBS_SPAWN);
    }


    @Override
    public boolean hurt(Entity attacker, int damage, DamageType type) {
        if (attacker == this.rider) return false;
        return super.hurt(attacker, damage, type);
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {

        if (this.isTamed && this.hasSaddle) {
            entityplayer.startRiding(this);
            ((IPlayerJumpAmount) entityplayer).aether$setJumpMaxAmount(this.jumpMaxAmount);
            ((IPlayerJumpAmount) entityplayer).aether$setJumpAmount(this.jumpAmount);

            this.rider = entityplayer;
            this.isBeingRidden = true;
        }

        ItemStack item = entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem];

        if (item != null) {
            if (!this.isTamed && this.isChild && this.tameCoolDown <= 0 && item.itemID == AetherItems.petalAechor.id) {
                entityplayer.inventory.consumeInventoryItem(AetherItems.petalAechor.id);

                if (this.random.nextInt(20) < 3 + this.tameAttempts) {
                    this.isTamed = true;
                }

                spawnTameParticles();
                this.tameCoolDown = this.tameCoolDownMax;
                this.tameAttempts++;
            }

            if (item.itemID == Item.saddle.id && !this.hasSaddle && !this.isChild && this.isTamed) {
                entityplayer.inventory.consumeInventoryItem(Item.saddle.id);
                this.hasSaddle = true;
            }
        }

        return super.interact(entityplayer);
    }

    void spawnTameParticles() {
        String particle = "smoke";
        if (isTamed) {
            particle = "heart";
        }

        for(int i = 0; i < 7; ++i) {
            double dx = this.random.nextGaussian() * 0.02;
            double dy = this.random.nextGaussian() * 0.02;
            double dz = this.random.nextGaussian() * 0.02;
            this.world.spawnParticle(
                particle,
                this.x + (double)(this.random.nextFloat() * this.bbWidth * 2.0F) - (double)this.bbWidth,
                this.y + 0.5 + (double)(this.random.nextFloat() * this.bbHeight),
                this.z + (double)(this.random.nextFloat() * this.bbWidth * 2.0F) - (double)this.bbWidth,
                dx, dy, dz);
        }
    }

    private void spawnCloudParticles() {

        float width = 1.0f;
        for (int i = 0; i < 20; ++i) {
            double dx = random.nextGaussian() * 0.02;
            double dy = random.nextGaussian() * 0.02;
            double dz = random.nextGaussian() * 0.02;
            world.spawnParticle(
                "snowshovel",
                x + (double) (random.nextFloat() * width * 2.0F) - (double) width,
                y - bbHeight + (double) (random.nextFloat() * width),
                z + (double) (random.nextFloat() * width * 2.0F) - (double) width,
                dx, dy, dz
            );
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putBoolean("isTamed", this.isTamed);
        tag.putBoolean("hasSaddle", this.hasSaddle);
        super.addAdditionalSaveData(tag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        this.isTamed = tag.getBoolean("isTamed");
        this.hasSaddle = tag.getBoolean("hasSaddle");
        super.readAdditionalSaveData(tag);
    }

}
