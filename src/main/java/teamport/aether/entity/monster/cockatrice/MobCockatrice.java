package teamport.aether.entity.monster.cockatrice;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.Global;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.enums.LightLayer;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.blocks.AetherBlockTags;
import teamport.aether.entity.AetherDeathMessage;
import teamport.aether.entity.monster.MobMonsterAether;
import teamport.aether.entity.projectile.ProjectileNeedle;

public class MobCockatrice extends MobMonsterAether implements Enemy, AetherDeathMessage {
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
        this.scoreValue = 500;
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
                    ProjectileNeedle needle = new ProjectileNeedle(this.world, this);
                    double d2 = entity.y + (double) entity.getHeadHeight() - 0.8 - needle.y;
                    float f1 = MathHelper.sqrt(d * d + d1 * d1) * 0.2F;
                    world.playSoundAtEntity(null, this, "random.bow", 0.3F, 2.0F / (random.nextFloat() * 0.4F + 0.8F));
                    needle.setHeading(d, d2 + (double) f1, d1, 0.6F, 12.0F);
                    this.world.entityJoinedWorld(needle);
                }

                this.attackTime = 30;
            }

            this.yRot = (float) (Math.atan2(d1, d) * 180.0 / Math.PI) - 90.0F;
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
        return 12 * Global.TICKS_PER_SECOND;
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


    public void playLivingSound() {
        this.world.playSoundAtEntity(null, this, this.getLivingSound(), 0.5f, (this.random.nextFloat() - this.random.nextFloat()) * 0.5F + 0.25F);
    }

    public void playHurtSound() {
        this.world.playSoundAtEntity(null, this, this.getHurtSound(), 0.5f, (this.random.nextFloat() - this.random.nextFloat()) * 0.5F + 0.25F);
    }

    public void playDeathSound() {
        this.world.playSoundAtEntity(null, this, this.getDeathSound(), 0.5f, (this.random.nextFloat() - this.random.nextFloat()) * 0.5F + 0.25F);
    }

    public boolean canSpawnHere() {
        int blockX = MathHelper.floor(this.x);
        int blockY = MathHelper.floor(this.bb.minY);
        int blockZ = MathHelper.floor(this.z);
        if (this.world.getSavedLightValue(LightLayer.Block, blockX, blockY, blockZ) > 7) {
            return false;
        } else if (this.world.getSavedLightValue(LightLayer.Sky, blockX, blockY, blockZ) > this.random.nextInt(32)) {
            return false;
        } else {
            int blockLight = this.world.getBlockLightValue(blockX, blockY, blockZ);
            if (this.world.getCurrentWeather() != null && this.world.getCurrentWeather().doMobsSpawnInDaylight) {
                blockLight /= 2;
            }

            return blockLight <= 4 && AetherBlockTags.PASSIVE_MOBS_SPAWN.appliesTo(this.world.getBlock(MathHelper.floor(this.x), MathHelper.floor(this.y - (double) this.heightOffset) - 1, MathHelper.floor(this.z))) && super.canSpawnHere();
        }
    }

}
