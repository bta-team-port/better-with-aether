package teamport.aether.entity.monster.cockatrice;

import net.minecraft.core.Global;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.enums.LightLayer;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlockTags;
import teamport.aether.entity.AetherDeathMessage;
import teamport.aether.entity.monster.MobMonsterAether;
import teamport.aether.entity.projectile.ProjectileNeedle;

public class MobCockatrice extends MobMonsterAether implements Enemy, AetherDeathMessage {
    private float flap = 0.0F;
    private float flapSpeed = 0.0F;
    private float oFlapSpeed;
    private float oFlap;
    private float flapping = 1.0F;

    public MobCockatrice(@NonNull World world) {
        super(world);
        this.setTextureIdentifier("aether", "cockatrice");
        this.setSize(1.0F, 2.0F);
        this.mobDrops.add(new WeightedRandomLootObject(Items.FEATHER_CHICKEN.getDefaultStack(), 0, 2));
        this.scoreValue = 500;
    }

    @Override
    public void tick() {
        super.tick();
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed = (float) (this.flapSpeed + (this.onGround ? -1 : 4) * 0.3);

        if (this.flapSpeed < 0.0F) {
            this.flapSpeed = 0.0F;
        }

        if (this.flapSpeed > 1.0F) {
            this.flapSpeed = 1.0F;
        }

        if (!this.onGround && this.flapping < 1.0F) {
            this.flapping = 1.0F;
        }

        this.flapping = (float) (this.flapping * 0.9);
        if (!this.onGround && this.yd < 0.0) {
            this.yd *= 0.6;
        }

        this.flap += this.flapping * 2.0F;
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(15, this.attackTime, Integer.class);
    }

    @Override
    public void onLivingUpdate() {
        if (this.world.isClientSide) {
            this.attackTime = this.entityData.getInt(15);
        } else {
            this.entityData.set(15, this.attackTime);
        }
        super.onLivingUpdate();
    }

    @Override
    public void attackEntity(@NonNull Entity entity, float distance) {
        if (distance < 10.0F) {
            double d = entity.x - this.x;
            double d1 = entity.z - this.z;
            if (this.attackTime == 0) {
                if (!this.world.isClientSide) {
                    ProjectileNeedle needle = new ProjectileNeedle(this.world, this);
                    double d2 = entity.y + entity.getHeadHeight() - 0.8 - needle.y;
                    float f1 = MathHelper.sqrt(d * d + d1 * d1) * 0.2F;
                    world.playSoundAtEntity(null, this, "random.bow", 0.3F, 2.0F / (random.nextFloat() * 0.4F + 0.8F));
                    needle.setHeading(d, d2 + f1, d1, 0.6F, 12.0F);
                    this.world.entityJoinedWorld(needle);
                }

                this.attackTime = 30;
            }

            this.yRot = (float) (Math.atan2(d1, d) * 180.0 / Math.PI) - 90.0F;
            this.hasAttacked = true;
        }

    }

    @Override
    public void jump() {
        this.yd = 0.6;
    }

    @Override
    public void causeFallDamage(float distance) {
    }

    @Override
    public int getAmbientSoundInterval() {
        return 12 * Global.TICKS_PER_SECOND;
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
    public void playLivingSound() {
        this.world.playSoundAtEntity(null, this, this.getLivingSound(), 0.5f, (this.random.nextFloat() - this.random.nextFloat()) * 0.5F + 0.25F);
    }

    @Override
    public void playHurtSound() {
        this.world.playSoundAtEntity(null, this, this.getHurtSound(), 0.5f, (this.random.nextFloat() - this.random.nextFloat()) * 0.5F + 0.25F);
    }

    @Override
    public void playDeathSound() {
        this.world.playSoundAtEntity(null, this, this.getDeathSound(), 0.5f, (this.random.nextFloat() - this.random.nextFloat()) * 0.5F + 0.25F);
    }

    @Override
    public boolean canSpawnHere() {
        TilePos blockPos = new TilePos(this.x, this.bb.minY, this.z);
        if (this.world.getSavedLightValue(LightLayer.Block, blockPos) > 7) {
            return false;
        } else if (this.world.getSavedLightValue(LightLayer.Sky, blockPos) > this.random.nextInt(32)) {
            return false;
        } else {
            int blockLight = this.world.getBlockLightValue(blockPos);
            if (this.world.getCurrentWeather() != null && this.world.getCurrentWeather().isMobDaylightSpawnAllowed()) {
                blockLight /= 2;
            }

            return blockLight <= 4 && AetherBlockTags.PASSIVE_MOBS_SPAWN.appliesTo(this.world.getBlock(MathHelper.floor(this.x), MathHelper.floor(this.y - this.heightOffset) - 1, MathHelper.floor(this.z))) && super.canSpawnHere();
        }
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
}
