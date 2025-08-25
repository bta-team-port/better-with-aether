package teamport.aether.entity.boss.sunspirit;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.net.command.TextFormatting;
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
import teamport.aether.entity.monster.fireminion.MobFireMinion;
import teamport.aether.entity.projectile.ProjectileElementFire;
import teamport.aether.entity.projectile.ProjectileElementIce;
import teamport.aether.world.AetherDimension;

import static net.minecraft.core.net.command.TextFormatting.*;
import static teamport.aether.AetherMod.TRANSLATOR;

public class MobBossSunspirit extends MobBossFlying implements EnemyBoss {
    public int timesShot = 0;
    public int chatLog;
    public int chatTime;
    public int direction;
    public double rotary;
    public int motionTimer;
    public static final int START_FIGHT = 9;

    @Nullable
    public Entity target;
    public boolean gotTarget;
    public boolean hasAttacked;
    public int wideness;
    public double speedness;

    public MobBossSunspirit(@Nullable World world) {
        super(world);
        this.setSize(2.25F, 3.0F);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "boss_sunspirit");
        this.fireImmune = true;
        this.maxHurtTime = 40;
        this.scoreValue = 100000;
        this.wideness = 10;
        this.speedness = 0.5 - (double) this.getHealth() / 70.0 * 0.2;
        this.chatColor = (byte) (TextFormatting.YELLOW.id & 255);
        this.canBreatheUnderwater();
    }

    public void updateAI() {
        super.updateAI();
        if (this.gotTarget && this.target == null) {
            this.target = this.findPlayerToAttack();
            this.gotTarget = false;
        }

        if (this.target != null) {
            this.lookAt(this.target, 20.0F, 20.0F);
            this.attackEntity(this.target, 32);
        }

        if (world.players
                .stream()
                .noneMatch(p -> distanceToSqr(p) < AetherDimension.bossDetectionRangeSQR && p.isAlive())
        ) {
            returnToHome();
            this.target = null;
            this.gotTarget = false;
        }
    }


    @Override
    public void returnToHome() {
        if (returnPoint == null || !hasHadReturnPointSet) return;

        moveTo(returnPoint.x + 0.5, returnPoint.y, returnPoint.z + 0.5, 0, 0);
        this.xd = 0.0;
        this.yd = 0.0;
        this.zd = 0.0;
    }

    public void knockBack(Entity entity, int damage, double xd, double yd) {}

    public void tick() {
        super.tick();
        if (this.getHealth() > 0) {
            double a = this.random.nextFloat() - 0.5F;
            double b = this.random.nextFloat();
            double c = this.random.nextFloat() - 0.5F;
            double d = this.x + a * b;
            double e = this.bb.minY + b - 0.5;
            double f = this.z + c * b;
            this.world.spawnParticle("flame", d, e, f, 0.0, -0.07500000298023224, 0.0, 0);
            this.evaporateWater();
        }

        this.maxFireTicks = 0;
        this.fireImmune = true;
        this.remainingFireTicks = 0;

        if (this.chatTime > 0) {
            --this.chatTime;
        }
    }

    public boolean collidesWith(Entity entity) {
        if (!(entity instanceof MobBossSunspirit || entity instanceof MobFireMinion)) {
            entity.hurt(this, 20, DamageType.FIRE);
            entity.maxFireTicks = 300;
            entity.remainingFireTicks = 300;
            return false;
        }
        return false;
    }

    public void evaporateWater() {
        int x = MathHelper.floor(this.x);
        int z = MathHelper.floor(this.z);

        for (int i = 0; i < 8; ++i) {
            int b = (int) (this.yo - 2 + i);
            if (this.world.getBlockMaterial(x, b, z) == Material.water) {
                this.world.setBlock(x, b, z, 0);
                this.world.playSoundEffect(this, SoundCategory.ENTITY_SOUNDS, (float) x + 0.5F, (float) b + 0.5F, (float) z + 0.5F, "random.fizz", 0.5F, 2.6F + (this.random.nextFloat() - this.random.nextFloat()) * 0.8F);

                for (int l = 0; l < 8; ++l) {
                    this.world.spawnParticle("largesmoke", (double) x + Math.random(), (double) b + 0.75, (double) z + Math.random(), 0.0, 0.0, 0.0, 0);
                }
            }
        }
    }

    public boolean isPushable() {
        return false;
    }

    public boolean chatWithMe(Player player) {
        if (this.chatTime <= 0) {
            if (this.chatLog < START_FIGHT) {
                player.sendMessage(ORANGE + TRANSLATOR.translateKey("aether.entity.boss_sunspirit.chat_" + chatLog));
                if (this.chatLog >= 5 && this.chatLog < 8) {
                    player.sendMessage(ORANGE + TRANSLATOR.translateKey("aether.entity.boss_sunspirit.chat_" + chatLog + "_1"));
                }
                world.playSoundAtEntity(null, this, "aether:mob.sunspirit.talk", 1.0f, 1.0f);
                this.chatLog++;
                this.chatTime = 40;
                return false;
            }
            if (this.chatLog == START_FIGHT) {
                player.sendMessage(RED + TRANSLATOR.translateKey("aether.entity.boss_sunspirit.fight.start"));
                world.playSoundAtEntity(null, this, "aether:mob.sunspirit.talk", 1.0f, 0.5f);
                this.chatLog++;
                return true;
            }
            if (this.target == null) {
                player.sendMessage(RED + TRANSLATOR.translateKey("aether.entity.boss_sunspirit.fight.repeat"));
                world.playSoundAtEntity(null, this, "aether:mob.sunspirit.talk", 1.0f, 1.0f);
                this.chatLog = START_FIGHT;
                this.chatTime = 40;
                return false;
            }
        }
        return false;
    }

    public boolean interact(@NotNull Player player) {
        if (this.chatWithMe(player)) {
            this.rotary = 57.295772552490234 * Math.atan2(this.x - player.x, this.z - player.z);
            this.target = player;
            this.gotTarget = true;
        }
        return false;
    }

    public void onDeath(Entity entityKilledBy) {
        if (!world.isClientSide && world.dimension == AetherDimension.AETHER) {
            AetherDimension.unlockDaylightCycle(world);
        }

        world.players.stream()
                .filter(player -> player.distanceTo(this) < 32)
                .forEach(players -> {
                    players.sendMessage(LIGHT_BLUE + TRANSLATOR.translateKey("aether.entity.boss_sunspirit.dies"));
                    players.triggerAchievement(AetherAchievements.GOLD);
                    this.world.playSoundEffect(players, SoundCategory.WORLD_SOUNDS, players.x, players.y, players.z, "aether:achievement.gold", 0.5f, 1.0f);
                });

        super.onDeath(entityKilledBy);
    }

    public int getMaxHealth() {
        return 50;
    }

    public Entity findPlayerToAttack() {
        Player player = this.world.getClosestPlayerToEntity(this, 16.0);
        if (player != null && canEntityBeSeen(player) && player.getGamemode().consumeBlocks() && gotTarget) {
            ((AetherBossList) player).aether$TryAddBossList(this);
            return player;
        }
        return null;
    }

    public boolean canFight() {
        return isAlive() && gotTarget;
    }

    public float getBrightness(float partialTick) {
        return 1.0F;
    }

    public int getLightmapCoord(float partialTick) {
        return this.world.getLightmapCoord(15, 15);
    }

    public void attackEntity(@NotNull Entity entity, float distance) {
        if (gotTarget) {
            int totalShots = 4;
            if (this.attackTime == 0) {
                if (!this.world.isClientSide) {
                    if (this.timesShot < totalShots) {
                        ProjectileElementFire elementFire = new ProjectileElementFire(this.world, this);
                        elementFire.setHeading(world.rand.nextDouble(), this.getLookAngle().y, world.rand.nextDouble(), 2.0f, 0.0F);
                        this.world.playSoundAtEntity(null, this, "mob.ghast.fireball", this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                        this.world.entityJoinedWorld(elementFire);
                        this.timesShot++;
                    } else {
                        ProjectileElementIce elementIce = new ProjectileElementIce(this.world, this);
                        elementIce.setHeading(this.getLookAngle().x, this.getLookAngle().y, this.getLookAngle().z, 0.5f, 0.0F);
                        this.world.playSoundAtEntity(null, this, "mob.ghast.fireball", this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 2.0F);
                        this.world.entityJoinedWorld(elementIce);
                        this.timesShot = 0;
                    }
                }
                if (this.getHealth() <= (this.getMaxHealth() / 2)) {
                    this.attackTime = 25;
                } else {
                    this.attackTime = 50;
                }
            }
            this.hasAttacked = true;
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.timesShot = tag.getInteger("timesShot");
        this.chatLog = tag.getByte("chatLog");
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("timesShot", this.timesShot);
        tag.putByte("chatLog", (byte)this.chatLog);
    }

    public boolean hurt(Entity attacker, int damage, DamageType type) {
        if (attacker instanceof ProjectileElementIce) {
            super.hurt(attacker, 5, type);

            if (target instanceof Player) {
                ((Player) target).triggerAchievement(AetherAchievements.ICE_DEFLECT);
            }

            MobFireMinion minion1 = new MobFireMinion(this.world);
            MobFireMinion minion2 = new MobFireMinion(this.world);
            MobFireMinion minion3 = new MobFireMinion(this.world);
            if (this.getHealth() <= (this.getMaxHealth() / 2)) {
                minion1.setPos(this.x + 1, this.y + 1, this.z);
                this.world.entityJoinedWorld(minion1);
                minion2.setPos(this.x, this.y + 1, this.z);
                this.world.entityJoinedWorld(minion2);
                minion3.setPos(this.x - 1, this.y + 1, this.z);
                this.world.entityJoinedWorld(minion3);
            } else {
                minion1.setPos(this.x, this.y, this.z);
                this.world.entityJoinedWorld(minion1);
            }
            return true;
        }
        if (!this.gotTarget) {
            this.chatLog = 9;
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
