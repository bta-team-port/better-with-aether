package teamport.aether.entity.boss.sunspirit;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import teamport.aether.AetherAchievements;
import teamport.aether.entity.boss.AetherBossList;
import teamport.aether.entity.boss.EnemyBoss;
import teamport.aether.entity.boss.MobBoss;
import teamport.aether.entity.projectile.ProjectileElementFire;
import teamport.aether.entity.projectile.ProjectileElementIce;

public class MobBossSunspirit extends MobBoss implements EnemyBoss {
    public int timesShot = 0;
    public MobBossSunspirit(@Nullable World world) {
        super(world);
        this.setSize(2.25F, 2.5F);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "boss_sunspirit");
        this.fireImmune = true;
        this.maxHurtTime = 40;
        this.scoreValue = 100000;
    }

    protected void causeFallDamage(float distance) {
    }

    public void onDeath(Entity entityKilledBy) {
        Player entityplayer = this.world.getClosestPlayerToEntity(this, 32.0);
        entityplayer.triggerAchievement(AetherAchievements.GOLD);
        this.world.playSoundEffect(entityKilledBy, SoundCategory.ENTITY_SOUNDS, entityplayer.x, entityplayer.y, entityplayer.z, "aether:achievement.gold", 0.5f, 1.0f);
        super.onDeath(entityplayer);
    }

    public int getMaxHealth() {
        return 1000;
    }

    public void moveEntityWithHeading(float moveStrafing, float moveForward) {
        if (this.isInWater()) {
            this.moveRelative(moveStrafing, moveForward, 0.02F);
            this.move(this.xd, this.yd, this.zd);
            this.xd *= 0.8;
            this.yd *= 0.8;
            this.zd *= 0.8;
        } else if (this.isInLava()) {
            this.moveRelative(moveStrafing, moveForward, 0.02F);
            this.move(this.xd, this.yd, this.zd);
            this.xd *= 0.5;
            this.yd *= 0.5;
            this.zd *= 0.5;
        } else {
            float f2 = 0.91F;
            if (this.onGround) {
                f2 = 0.5460001F;
                int i = this.world.getBlockId(MathHelper.floor(this.x), MathHelper.floor(this.bb.minY) - 1, MathHelper.floor(this.z));
                if (i > 0) {
                    f2 = Blocks.blocksList[i].friction * 0.91F;
                }
            }

            float f3 = 0.1627714F / (f2 * f2 * f2);
            this.moveRelative(moveStrafing, moveForward, this.onGround ? 0.1F * f3 : 0.02F);
            f2 = 0.91F;
            if (this.onGround) {
                f2 = 0.5460001F;
                int j = this.world.getBlockId(MathHelper.floor(this.x), MathHelper.floor(this.bb.minY) - 1, MathHelper.floor(this.z));
                if (j > 0) {
                    f2 = Blocks.blocksList[j].friction * 0.91F;
                }
            }

            this.move(this.xd, this.yd, this.zd);
            this.xd *= f2;
            this.yd *= f2;
            this.zd *= f2;
        }

        this.walkAnimSpeedO = this.walkAnimSpeed;
        double d = this.x - this.xo;
        double d1 = this.z - this.zo;
        float f4 = MathHelper.sqrt(d * d + d1 * d1) * 4.0F;
        if (f4 > 1.0F) {
            f4 = 1.0F;
        }

        this.walkAnimSpeed += (f4 - this.walkAnimSpeed) * 0.4F;
        this.walkAnimPos += this.walkAnimSpeed;
    }

    public boolean canClimb() {
        return false;
    }

    public Entity findPlayerToAttack() {
        Player player = this.world.getClosestPlayerToEntity(this, 16.0);
        if (player != null && canEntityBeSeen(player) && player.getGamemode().consumeBlocks()) {
            ((AetherBossList) player).aether$TryAddBossList(this);
            return player;
        }
        return null;
    }

    public float getBrightness(float partialTick) {
        return 1.0F;
    }

    public int getLightmapCoord(float partialTick) {
        return this.world.getLightmapCoord(15, 15);
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
                        elementFire.setHeading(world.rand.nextDouble(), this.getLookAngle().y, world.rand.nextDouble(), 1.0f, 50.0F);
                        this.world.playSoundAtEntity(null, this, "mob.ghast.fireball", this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                        this.world.entityJoinedWorld(elementFire);
                        this.timesShot++;
                    } else {
                        ProjectileElementIce elementIce = new ProjectileElementIce(this.world, this);
                        elementIce.setHeading(this.getLookAngle().x, this.getLookAngle().y, this.getLookAngle().z, 0.5f, 50.0F);
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

    public boolean hurt(Entity attacker, int damage, DamageType type) {
        if (attacker instanceof ProjectileElementIce) {
            super.hurt(attacker, 100, type);
            return true;
        }
        return false;
    }

    public String getHurtSound() {
        return "aether:mob.sunspirit.hurt";
    }

    public String getDeathSound() {
        return "aether:mob.sunspirit.death";
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
