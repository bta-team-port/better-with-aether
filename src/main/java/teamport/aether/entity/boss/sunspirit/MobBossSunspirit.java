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
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sunsetsatellite.catalyst.core.util.vector.Vec2f;
import teamport.aether.AetherAchievements;
import teamport.aether.entity.boss.AetherBossList;
import teamport.aether.entity.boss.EnemyBoss;
import teamport.aether.entity.monster.fireminion.MobFireMinion;
import teamport.aether.entity.projectile.ProjectileElementFire;
import teamport.aether.entity.projectile.ProjectileElementIce;
import teamport.aether.world.AetherDimension;
import turniplabs.halplibe.helper.EnvironmentHelper;

import static net.minecraft.core.net.command.TextFormatting.*;
import static teamport.aether.AetherMod.TRANSLATOR;

public class MobBossSunspirit extends MobBossFlying {
    public int timesShot = 0;
    public int chatLog;
    public int chatCooldown;
    public int direction;
    public double rotary;
    public int motionTimer;

    public static final int START_FIGHT = 9;


    @Nullable
    public Entity target;
    public boolean isAgro;

    public boolean hasAttacked;

    public MobBossSunspirit(@Nullable World world) {
        super(world);
        this.setSize(2.25F, 3.0F);

        this.textureIdentifier = NamespaceID.getPermanent("aether", "boss_sunspirit");
        this.fireImmune = true;
        this.maxHurtTime = 40;
        this.scoreValue = 100000;

        this.chatColor = (byte) (TextFormatting.YELLOW.id & 255);
    }

    public void updateAI() {
        super.updateAI();

        if (this.isAgro) {
            DVDMove();
            this.target = this.findPlayerToAttack();

            if (target != null){
                this.lookAt(this.target, 20.0F, 20.0F);
                this.attackEntity(this.target, 32);
            }
            else if (world.getClosestPlayerToEntity(this, AetherDimension.bossDetectionRange) == null) {
                returnToHome();
                runWithDungeon(d -> d.unlock(this, world));
                this.target = null;
                this.isAgro = false;
            }
        }
    }

    public double speedness() {
        return 0.5 - (double) this.getHealth() / 70.0 * 0.2;
    }

    public Vec2f DVDMoveAmount = new Vec2f(0.25f, 0.25f);

    protected void DVDMove() {
        HitResult hitResult =  world.checkBlockCollisionBetweenPoints(
            Vec3.getPermanentVec3(x, y + bbHeight/2, z),
            Vec3.getPermanentVec3(
                x + xd + DVDMoveAmount.x + (DVDMoveAmount.x > 0 ?  bbWidth/2 : -bbWidth/2),
                y + bbHeight/2,
                z + zd + DVDMoveAmount.y + (DVDMoveAmount.y > 0 ?  bbWidth/2 : -bbWidth/2)
            ),
            false
        );

        if (hitResult != null) {
            double speed = 0.25f * speedness();

            switch (hitResult.side) {
                case NORTH: DVDMoveAmount.y -= speed;
                    break;

                case SOUTH: DVDMoveAmount.y += speed;
                    break;

                case WEST: DVDMoveAmount.x -= speed;
                    break;

                case EAST: DVDMoveAmount.x += speed;
                    break;
            }

            DVDMoveAmount.x += random.nextFloat() * 0.1 - random.nextFloat() * 0.1;
            DVDMoveAmount.y += random.nextFloat() * 0.1 - random.nextFloat() * 0.1;
        }

        double maxSpeed = 0.035D;
        double currSpeed = Math.hypot(DVDMoveAmount.x, DVDMoveAmount.y);
        if (currSpeed > maxSpeed) DVDMoveAmount.multiply(maxSpeed / currSpeed);

        xd += DVDMoveAmount.x;
        zd += DVDMoveAmount.y;
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

            double flameX = this.x + a * b;
            double flameY = this.bb.minY + b - 0.5;
            double flameZ = this.z + c * b;

            this.world.spawnParticle("flame", flameX, flameY, flameZ, 0.0, -0.075F, 0.0, 0);
            this.evaporateWater();
        }

        this.maxFireTicks = 0;
        this.fireImmune = true;
        this.remainingFireTicks = 0;

        if (this.chatCooldown > 0) {
            --this.chatCooldown;
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
                this.world.playSoundEffect(this, SoundCategory.ENTITY_SOUNDS, (float) x + 0.5F, (float) b + 0.5F, (float) z + 0.5F, "random.fizz", 0.25F, 2.6F + (this.random.nextFloat() - this.random.nextFloat()) * 0.8F);

                for (int l = 0; l < 8; ++l) {
                    this.world.spawnParticle("largesmoke", (double) x -1 + (2 *  Math.random()), (double) b + 0.75, (double) z -1 + (2 *  Math.random()), 0.0, 0.025F, 0.0, 0);
                }
            }
        }
    }

    public boolean isPushable() {
        return false;
    }

    public boolean chatWithMe(Player player) {
        if (isAgro && target != null) return false;

        if (this.chatCooldown <= 0) {

            if (this.chatLog < START_FIGHT) {
                player.sendMessage(ORANGE + TRANSLATOR.translateKey("aether.entity.boss_sunspirit.chat_" + chatLog));
                if (this.chatLog >= 5 && this.chatLog < 8) {
                    player.sendMessage(ORANGE + TRANSLATOR.translateKey("aether.entity.boss_sunspirit.chat_" + chatLog + "_1"));
                }

                world.playSoundAtEntity(null, this, "aether:mob.sunspirit.talk", 1.0f, 1.0f);

                this.chatLog++;
                this.chatCooldown = 10;
                return false;
            }

            if (this.chatLog == START_FIGHT) {
                player.sendMessage(RED + TRANSLATOR.translateKey("aether.entity.boss_sunspirit.fight.start"));
                world.playSoundAtEntity(null, this, "aether:mob.sunspirit.talk", 1.0f, 0.5f);
                ((AetherBossList) player).aether$TryAddBossList(this);

                this.chatLog++;
                this.isAgro = true;

                runWithDungeon(d -> d.lock(this, world));
                return true;
            }

            if (this.target == null) {
                player.sendMessage(RED + TRANSLATOR.translateKey("aether.entity.boss_sunspirit.fight.repeat"));
                world.playSoundAtEntity(null, this, "aether:mob.sunspirit.talk", 1.0f, 1.0f);
                ((AetherBossList) player).aether$TryAddBossList(this);

                this.chatLog = START_FIGHT;
                this.chatCooldown = 40;
                this.isAgro = true;
                return false;
            }
        }
        return false;
    }

    public boolean interact(@NotNull Player player) {
        if (this.chatWithMe(player)) {
            this.rotary = 57.295772552490234 * Math.atan2(this.x - player.x, this.z - player.z);
            this.target = player;
        }
        return false;
    }

    public void onDeath(Entity entityKilledBy) {
        runWithDungeon(d -> d.unlock(this, world));

        if (!world.isClientSide && world.dimension == AetherDimension.AETHER) {
            AetherDimension.unlockDaylightCycle(world);
        }

        world.players.stream()
            .filter(player -> player.distanceTo(this) < 32)
            .forEach(players -> {
                players.sendMessage(LIGHT_BLUE + TRANSLATOR.translateKey("aether.entity.boss_sunspirit.dies"));
                players.triggerAchievement(AetherAchievements.GOLD);
                this.world.playSoundEffect(players, SoundCategory.WORLD_SOUNDS, players.x, players.y, players.z, "", 0.5f, 1.0f);
            });

        super.onDeath(entityKilledBy);
    }

    public int getMaxHealth() {
        return 50;
    }

    public Entity findPlayerToAttack() {
        Player player = this.world.getClosestPlayerToEntity(this, 16.0);

        if (player != null && canEntityBeSeen(player) && player.gamemode.areMobsHostile()) {
            ((AetherBossList) player).aether$TryAddBossList(this);
            return player;
        }

        return null;
    }

    public boolean canFight() {
        return isAlive() && isAgro;
    }

    public float getBrightness(float partialTick) {
        return 1.0F;
    }

    public int getLightmapCoord(float partialTick) {
        return this.world.getLightmapCoord(15, 15);
    }

    public void attackEntity(@NotNull Entity entity, float distance) {
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

            if (this.getHealth() <= (this.getMaxHealth() / 2)) this.attackTime = 25;
            else this.attackTime = 35;
        }

        this.hasAttacked = true;

    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.timesShot = tag.getInteger("timesShot");
        this.chatLog = tag.getByte("chatLog");
        this.isAgro = tag.getBoolean("isAgro");
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("timesShot", this.timesShot);
        tag.putByte("chatLog", (byte)this.chatLog);
        tag.putBoolean("isAgro", isAgro);
    }

    public boolean hurt(Entity attacker, int damage, DamageType type) {
        if (attacker instanceof ProjectileElementIce) {
            super.hurt(attacker, 5, type);

            if (target instanceof Player) {
                ((Player) target).triggerAchievement(AetherAchievements.ICE_DEFLECT);
            }

            if (this.getHealth() <= (this.getMaxHealth() / 2)) {
                MobFireMinion minion1 = new MobFireMinion(this.world);
                minion1.setPos(this.x + 1, this.y + 1, this.z);

                MobFireMinion minion2 = new MobFireMinion(this.world);
                minion2.setPos(this.x, this.y + 1, this.z);

                MobFireMinion minion3 = new MobFireMinion(this.world);
                minion3.setPos(this.x - 1, this.y + 1, this.z);

                this.world.entityJoinedWorld(minion1);
                this.world.entityJoinedWorld(minion2);
                this.world.entityJoinedWorld(minion3);

            } else {
                MobFireMinion minion = new MobFireMinion(this.world);
                minion.setPos(this.x, this.y, this.z);

                this.world.entityJoinedWorld(minion);
            }

            return true;
        }

        if (!this.isAgro) {this.chatLog = 9;}
        return false;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
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
