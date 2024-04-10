package bta.aether.entity;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.achievement.AchievementList;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.world.World;

public class EntityPhyg extends EntityAetherAnimal {
    public float wingFold;
    public float wingAngle;
    private float aimingForFold;
    public int jumps;
    public int jrem;
    private boolean jpress;
    private int ticks;
    public EntityPhyg(World world) {
        super(world);
        this.skinName = "phyg";
        this.setSize(0.9F, 0.9F);
        this.jrem = 0;
        this.jumps = 1;
        this.ticks = 0;
        this.aimingForFold = 1.0F;
        this.jpress = true;
    }

    protected void init() {
        this.entityData.define(16, (byte)0);
    }

    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Saddle", this.getSaddled());
    }

    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setSaddled(tag.getBoolean("Saddle"));
    }

    public boolean interact(EntityPlayer entityplayer) {
        if (super.interact(entityplayer)) {
            return true;
        } else if (!this.getSaddled() || this.world.isClientSide || this.passenger != null && this.passenger != entityplayer) {
            return false;
        } else {
            entityplayer.startRiding(this);
            return true;
        }
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

    protected void causeFallDamage(float f) {
        super.causeFallDamage(f);
        if (f > 5.0F && this.passenger instanceof EntityPlayer) {
            ((EntityPlayer)this.passenger).triggerAchievement(AchievementList.FLY_PIG);
        }

    }

    public String getEntityTexture() {
        return "/assets/aether/mobs/" + this.skinName + "/" + this.getSkinVariant() + ".png";
    }
    @Override
    public int getSkinVariant() {
        int skinVariantCount = 1;
        return this.entityData.getByte(1) % skinVariantCount;
    }

    protected void jump() {
        this.yd = 0.6;
    }

    public void tick() {
        super.tick();
        if (this.onGround) {
            this.wingAngle *= 0.8F;
            this.aimingForFold = 0.1F;
            this.jpress = false;
            this.jrem = this.jumps;
        } else {
            this.aimingForFold = 1.0F;
        }

        ++this.ticks;
        this.wingAngle = this.wingFold * (float) Math.sin((double) ((float) this.ticks / 31.830988F));
        this.wingFold += (this.aimingForFold - this.wingFold) / 5.0F;
        this.fallDistance = 0.0F;
        if (this.yd < -0.2) {
            this.yd = -0.2;
        }

    }

    public String getLivingSound() {
        return "mob.pig";
    }

    protected String getHurtSound() {
        return "mob.pig";
    }

    protected String getDeathSound() {
        return "mob.pigdeath";
    }

    protected int getDropItemId() {
        return Item.foodPorkchopRaw.id;
    }

    protected void dropFewItems() {
        if (this.getSaddled()) {
            this.spawnAtLocation(Item.saddle.id, 1);
        }
        int i = this.random.nextInt(3);

        int k;
        for(k = 0; k < i; ++k) {
            this.spawnAtLocation(Item.foodPorkchopRaw.id, 1);
        }

        i = this.random.nextInt(3);

        for(k = 0; k < i; ++k) {
            this.spawnAtLocation(Item.featherChicken.id, 1);
        }
    }

}
