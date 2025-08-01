package teamport.aether.entity.cockatrice;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import teamport.aether.entity.projectile.ProjectileDart;

public class MobCockatrice extends MobMonster implements Enemy {
    public float flap = 0.0F;
    public float flapSpeed = 0.0F;
    public float oFlapSpeed;
    public float oFlap;
    public float flapping = 1.0F;
    public MobCockatrice(@Nullable World world) {
        super(world);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "cockatrice");
        this.setSize(1.0F, 2.0F);
        this.mobDrops.add(new WeightedRandomLootObject(Items.FEATHER_CHICKEN.getDefaultStack(), 0, 2));

    }

    public void tick() {
        super.tick();
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed = (float)((double)this.flapSpeed + (double)(this.onGround ? -1 : 4) * 0.3);

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
    }

    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(15, this.attackTime, Integer.class);
    }

    public void onLivingUpdate() {
        if (this.world.isClientSide) {
            this.attackTime = this.entityData.getInt(15);
        } else {
            this.entityData.set(15, this.attackTime);
        }
        super.onLivingUpdate();
    }

    public void attackEntity(@NotNull Entity entity, float distance) {
        if (distance < 10.0F) {
            double d = entity.x - this.x;
            double d1 = entity.z - this.z;
            if (this.attackTime == 0) {
                if (!this.world.isClientSide) {
                    ProjectileDart dart = new ProjectileDart(this.world, this, false, 1);
                    double d2 = entity.y + (double)entity.getHeadHeight() - 0.8 - dart.y;
                    float f1 = MathHelper.sqrt(d * d + d1 * d1) * 0.2F;
                    world.playSoundAtEntity(null, this, "random.bow", 0.3F, 2.0F / (random.nextFloat() * 0.4F + 0.8F));
                    dart.setHeading(d, d2 + (double)f1, d1, 0.6F, 12.0F);
                    this.world.entityJoinedWorld(dart);
                }

                this.attackTime = 30;
            }

            this.yRot = (float)(Math.atan2(d1, d) * 180.0 / Math.PI) - 90.0F;
            this.hasAttacked = true;
        }

    }

    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
    }

    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
    }

    public void jump() {
        this.yd = 0.6;
    }

    public void causeFallDamage(float distance) {
    }

    public int getAmbientSoundInterval() {
        return 240;
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

    public float getSoundVolume() {
        return 0.5F;
    }

    public void playLivingSound() {
        this.world.playSoundAtEntity(null, this, this.getLivingSound(), this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.5F + 0.25F);
    }

    public void playHurtSound() {
        this.world.playSoundAtEntity(null, this, this.getHurtSound(), this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.5F + 0.25F);
    }

    public void playDeathSound() {
        this.world.playSoundAtEntity(null, this, this.getDeathSound(), this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.5F + 0.25F);
    }

}
