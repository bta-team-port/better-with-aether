package teamport.aether.entity.sunspirit;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import teamport.aether.entity.EnemyBoss;
import teamport.aether.entity.MobBoss;
import teamport.aether.entity.projectile.ProjectileElementFire;
import teamport.aether.entity.projectile.ProjectileElementIce;

public class MobBossSunspirit extends MobBoss implements EnemyBoss {
    public int timesShot = 0;
    public MobBossSunspirit(@Nullable World world) {
        super(world);
        this.setSize(2.25F, 2.5F);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "boss_sunspirit");
        this.fireImmune = true;
    }

    public void attackEntity(@NotNull Entity entity, float distance) {
        int totalShots = 4;
        if (distance < 10.0F) {
            double d = entity.x - this.x;
            double d1 = entity.z - this.z;
            if (this.attackTime == 0) {
                if (!this.world.isClientSide) {
                    if (this.timesShot < totalShots) {
                        ProjectileElementFire elementFire = new ProjectileElementFire(this.world, this);
                        elementFire.setHeading(world.rand.nextDouble(), this.getLookAngle().y, world.rand.nextDouble(), 0.5f, 15.0F);
                        this.world.playSoundAtEntity(null, this, "mob.ghast.fireball", this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                        this.world.entityJoinedWorld(elementFire);
                        this.timesShot++;
                    } else {
                        ProjectileElementIce elementIce = new ProjectileElementIce(this.world, this);
                        elementIce.setHeading(this.getLookAngle().x, this.getLookAngle().y, this.getLookAngle().z, 0.5f, 15.0F);
                        this.world.playSoundAtEntity(null, this, "mob.ghast.fireball", this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 2.0F);
                        this.world.entityJoinedWorld(elementIce);
                        this.timesShot = 0;
                    }
                }
                this.attackTime = 50;
            }
            this.yRot = (float)(Math.atan2(d1, d) * 180.0 / Math.PI) - 90.0F;
            this.hasAttacked = true;
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.timesShot = tag.getInteger("timesShot");
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("timesShot", this.timesShot);
    }


    public String getEntityTexture() {
        if (this.hurtTime > 0) {
            return "/assets/aether/textures/entity/boss_sunspirit/sunspirit_hurt.png";
        }
        return "/assets/aether/textures/entity/boss_sunspirit/sunspirit.png";
    }


    public @NotNull String getDefaultEntityTexture() {
        if (this.hurtTime > 0) {
            return "/assets/aether/textures/entity/boss_sunspirit/sunspirit_hurt.png";
        }
        return "/assets/aether/textures/entity/boss_sunspirit/sunspirit.png";
    }

}
