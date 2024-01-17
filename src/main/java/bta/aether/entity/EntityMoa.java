package bta.aether.entity;

import bta.aether.item.AetherItems;
import com.mojang.nbt.CompoundTag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.option.GameSettings;
import net.minecraft.core.entity.animal.EntityChicken;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.IVehicle;
import net.minecraft.core.world.World;

public class EntityMoa extends EntityChicken implements IVehicle {

    public final int jumpMaxAmount = 3;
    public int jumpCoolDownMax = 25;
    private int jumpAmount = jumpMaxAmount;
    private boolean jumpLastState = false;
    public int jumpCoolDown;

    public boolean isTamed = false;
    public boolean isBeingRidden = false;
    public EntityPlayer rider = null;
    public String owner = "null";

    public EntityMoa(World world) {
        super(world);
        this.setSize(1.5f, 1.5f);
    }

    public String getEntityTexture() {
            return "/assets/aether/mobs/BlueMoa.png";
        }

    public String getDefaultEntityTexture() {
        return "/assets/aether/mobs/BlueMoa.png";
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
    protected int getDropItemId() {
        return AetherItems.eggMoaBlue.id;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (rider != null){
            ((IPlayerJumpAmount)rider).aether$setJumpAmount(jumpAmount);
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
                    ((IPlayerJumpAmount)this.rider).aether$setJumpAmount(this.jumpAmount);
                }

                if (this.onGround){
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

        if (this.jumpCoolDown > 0) this.jumpCoolDown--;
        if (this.onGround) this.jumpAmount = this.jumpMaxAmount;
    }

    @Override
    protected boolean isMovementCeased() {
        return this.isBeingRidden;
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        entityplayer.startRiding(this);
        ((IPlayerJumpAmount)entityplayer).aether$setJumpMaxAmount(this.jumpMaxAmount);
        ((IPlayerJumpAmount)entityplayer).aether$setJumpAmount(this.jumpAmount);

        this.rider = entityplayer;
        this.isBeingRidden = true;

        return super.interact(entityplayer);
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
        tag.putString("owner", this.owner);
        super.addAdditionalSaveData(tag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        this.isTamed = tag.getBoolean("isTamed");
        this.owner = tag.getString("owner");
        super.readAdditionalSaveData(tag);
    }

}
