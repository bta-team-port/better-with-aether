package bta.aether.entity;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.world.World;

public class EntityPhow extends EntityAetherAnimal {
    public float wingFold;
    public float wingAngle;
    private float aimingForFold;
    public int jumps;
    public int jrem;
    private boolean jpress;
    private int ticks;
    public EntityPhow(World world) {
        super(world);
        this.skinName = "phow";
        this.setSize(0.9F, 1.3F);
        this.jrem = 0;
        this.jumps = 1;
        this.ticks = 0;
        this.aimingForFold = 1.0F;
        this.jpress = true;
    }

    public String getEntityTexture() {
        return "/assets/aether/mobs/" + this.skinName + "/" + this.getSkinVariant() + ".png";
    }
    @Override
    public int getSkinVariant() {
        int skinVariantCount = 1;
        return this.entityData.getByte(1) % skinVariantCount;
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

    protected void jump() {
        this.yd = 0.6;
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

        ++this.ticks;
        this.wingAngle = this.wingFold * (float) Math.sin((float) this.ticks / 31.830988F);
        this.wingFold += (this.aimingForFold - this.wingFold) / 5.0F;
        this.fallDistance = 0.0F;
        if (this.yd < -0.2) {
            this.yd = -0.2;
        }

    }

    public String getLivingSound() {
        return "mob.cow";
    }

    protected String getHurtSound() {
        return "mob.cowhurt";
    }

    protected String getDeathSound() {
        return "mob.cowhurt";
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    protected int getDropItemId() {
        return Item.leather.id;
    }

    protected void dropFewItems() {
        if (this.getSaddled()) {
            this.spawnAtLocation(Item.saddle.id, 1);
        }
        int i = this.random.nextInt(3);

        int k;
        for(k = 0; k < i; ++k) {
            this.spawnAtLocation(Item.leather.id, 1);
        }

        i = this.random.nextInt(3);

        for(k = 0; k < i; ++k) {
            this.spawnAtLocation(Item.featherChicken.id, 1);
        }

    }
}
