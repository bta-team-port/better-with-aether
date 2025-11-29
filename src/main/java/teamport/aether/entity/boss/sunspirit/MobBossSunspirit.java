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
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import sunsetsatellite.catalyst.core.util.vector.Vec2f;
import teamport.aether.achievements.AetherAchievements;
import teamport.aether.entity.boss.AetherBossList;
import teamport.aether.entity.monster.fireminion.MobFireMinion;
import teamport.aether.entity.projectile.ProjectileElementFire;
import teamport.aether.entity.projectile.ProjectileElementIce;
import teamport.aether.entity.player.MessageMaker;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.world.AetherDimension;
import teamport.aether.world.feature.util.map.DungeonMap;

import static net.minecraft.core.net.command.TextFormatting.*;
import static teamport.aether.AetherMod.TRANSLATOR;

public class MobBossSunspirit extends MobBossFlying {
    private int timesShot = 0;
    private int chatLog;
    private int chatCooldown;

    private static final int START_FIGHT = 9;


    @Nullable
    private Entity target;
    private boolean isAgro;

    public MobBossSunspirit(@Nullable World world) {
        super(world);
        this.setSize(2.25F, 3.0F);

        this.textureIdentifier = NamespaceID.getPermanent("aether", "boss_sunspirit");
        this.fireImmune = true;
        this.maxHurtTime = 40;
        this.scoreValue = 100000;

        this.chatColor = (byte) (TextFormatting.YELLOW.id & 255);
        this.canBreatheUnderwater();
    }

    public void returnToOriginalState() {
        this.isAgro = false;
        this.target = null;
        returnToHome();
        DungeonMap.runWithDungeon(dungeonID, d -> d.unlock(world));
        this.setHealthRaw(this.getMaxHealth());
    }

    @Override
    public void updateAI() {
        super.updateAI();

        if (this.isAgro) {
            DVDMove();
            this.target = this.findPlayerToAttack();

            if (target != null) {
                this.lookAt(this.target, 20.0F, 20.0F);
                this.attackEntity();
            } else if (this.world != null && this.world.getClosestPlayerToEntity(this, AetherDimension.BOSS_DETECTION_RADIUS) == null) {
                returnToOriginalState();
            }
        }
    }

    public double speedness() {
        return 0.5 - this.getHealth() / 70.0 * 0.2;
    }

    private final Vec2f dvdMoveAmount = new Vec2f(0.25f, 0.25f);

    @SuppressWarnings("java:S131")
    protected void DVDMove() {
        if (this.world == null) return;
        HitResult hitResult = this.world.checkBlockCollisionBetweenPoints(
            Vec3.getPermanentVec3(x, y + bbHeight / 2, z),
            Vec3.getPermanentVec3(
                x + xd + dvdMoveAmount.x + (dvdMoveAmount.x > 0 ? bbWidth / 2 : -bbWidth / 2),
                y + bbHeight / 2,
                z + zd + dvdMoveAmount.y + (dvdMoveAmount.y > 0 ? bbWidth / 2 : -bbWidth / 2)
            ),
            false
        );

        if (hitResult != null) {
            double speed = 0.25f * speedness();

            switch (hitResult.side) {
                case NORTH:
                    dvdMoveAmount.y -= speed;
                    break;

                case SOUTH:
                    dvdMoveAmount.y += speed;
                    break;

                case WEST:
                    dvdMoveAmount.x -= speed;
                    break;

                case EAST:
                    dvdMoveAmount.x += speed;
                    break;
            }

            dvdMoveAmount.x += random.nextDouble() * 0.1 - random.nextDouble() * 0.1;
            dvdMoveAmount.y += random.nextDouble() * 0.1 - random.nextDouble() * 0.1;
        }

        double maxSpeed = 0.035D;
        double currSpeed = Math.hypot(dvdMoveAmount.x, dvdMoveAmount.y);
        if (currSpeed > maxSpeed) dvdMoveAmount.multiply(maxSpeed / currSpeed);

        xd += dvdMoveAmount.x;
        zd += dvdMoveAmount.y;
    }

    @Override
    public void returnToHome() {
        if (returnPoint == null || !hasHadReturnPointSet) return;
        moveTo(returnPoint.getX() + 0.5, returnPoint.getY(), returnPoint.getZ() + 0.5, 0, 0);
        this.xd = 0.0;
        this.yd = 0.0;
        this.zd = 0.0;
    }

    @Override
    public void knockBack(Entity entity, int damage, double xd, double yd) {}

    @Override
    public void tick() {
        super.tick();

        if (this.getHealth() > 0) {
            double a = this.random.nextDouble() - 0.5;
            double b = this.random.nextDouble();
            double c = this.random.nextDouble() - 0.5;

            double flameX = this.x + a * b;
            double flameY = this.bb.minY + b - 0.5;
            double flameZ = this.z + c * b;

            ParticleMaker.spawnParticle(world, "flame", flameX, flameY, flameZ, 0.0, -0.075F, 0.0, 0);
            this.evaporateWater();
        }

        this.maxFireTicks = 0;
        this.fireImmune = true;
        this.remainingFireTicks = 0;

        if (this.chatCooldown > 0) {
            --this.chatCooldown;
        }
    }

    @Override
    public boolean collidesWith(Entity entity) {
        if (!(entity instanceof MobBossSunspirit || entity instanceof MobFireMinion)) {
            entity.hurt(this, 20, DamageType.FIRE);
            entity.hurt(this, 10, DamageType.COMBAT);
            entity.maxFireTicks = 1000;
            entity.remainingFireTicks = 1000;
        }
        return false;
    }

    public void evaporateWater() {
        int centerX = MathHelper.floor(this.x);
        int centerZ = MathHelper.floor(this.z);
        int radius = 9;

        for (int dx = -radius; dx < radius; dx++) {
            for (int dz = -radius; dz < radius; dz++) {
                int x = centerX + dx;
                int z = centerZ + dz;

                for (int i = 0; i < 9; ++i) {
                    int y = (int) (this.yo - 2 + i);
                    if (this.world != null && this.world.getBlockMaterial(x, y, z) == Material.water) {
                        this.world.setBlockWithNotify(x, y, z, 0);
                        this.world.playSoundEffect(this, SoundCategory.ENTITY_SOUNDS, x + 0.5, y + 0.5, z + 0.5F, "random.fizz", 0.125F, 2.6F + (this.random.nextFloat() - this.random.nextFloat()) * 0.8F);

                        for (int l = 0; l < 8; ++l) {
                            ParticleMaker.spawnParticle(world, "largesmoke", x - 1.0 + (2.0 * Math.random()), y + 0.75, z - 1.0 + (2.0 * Math.random()), 0.0, 0.025, 0.0, 0);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    public boolean chatWithMe(Player player) {
        if (this.world == null || isAgro && target != null) return false;

        if (this.chatCooldown <= 0) {

            if (this.chatLog < START_FIGHT) {
                MessageMaker.sendMessage(player, ORANGE + TRANSLATOR.translateKey("boss_sunspirit.chat_" + chatLog));
                if (this.chatLog >= 5 && this.chatLog < 8) {
                    MessageMaker.sendMessage(player, ORANGE + TRANSLATOR.translateKey("boss_sunspirit.chat_" + chatLog + "_1"));
                }

                world.playSoundAtEntity(null, this, "aether:mob.sunspirit.talk", 1.0f, 1.0f);

                this.chatLog++;
                this.chatCooldown = 10;
                return false;
            }

            if (this.chatLog == START_FIGHT) {
                MessageMaker.sendMessage(player, RED + TRANSLATOR.translateKey("boss_sunspirit.fight.start"));
                world.playSoundAtEntity(null, this, "aether:mob.sunspirit.talk", 1.0f, 0.5f);
                ((AetherBossList) player).aether$TryAddBossList(this);

                this.world.players.stream()
                    .filter(p -> p.distanceTo(this) < 32 && p != player)
                    .forEach(p -> ((AetherBossList) p).aether$TryAddBossList(this));

                this.chatLog++;
                this.isAgro = true;

                DungeonMap.runWithDungeon(dungeonID, d -> d.lock(world));
                return true;
            }

            if (this.target == null) {
                MessageMaker.sendMessage(player, RED + TRANSLATOR.translateKey("boss_sunspirit.fight.repeat"));
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

    @Override
    public boolean interact(@NonNull Player player) {
        if (this.chatWithMe(player)) {
            this.target = player;
        }
        return false;
    }

    @Override
    public void onDeath(Entity entityKilledBy) {
        if (this.world == null) return;
        DungeonMap.runWithDungeon(dungeonID, d -> d.unlock(world));

        if (!this.world.isClientSide && world.dimension == AetherDimension.getAether()) {
            AetherDimension.unlockDaylightCycle(world);
        }

        world.players.stream()
            .filter(player -> player.distanceTo(this) < 32)
            .forEach(players -> {
                MessageMaker.sendMessage(players, LIGHT_BLUE + TRANSLATOR.translateKey("boss_sunspirit.dies"));
                players.triggerAchievement(AetherAchievements.GOLD);
                this.world.playSoundEffect(players, SoundCategory.WORLD_SOUNDS, players.x, players.y, players.z, "aether:achievement.gold", 0.5f, 1.0f);
            });

        super.onDeath(entityKilledBy);
    }

    @Override
    public int getMaxHealth() {
        return 1000;
    }

    public Entity findPlayerToAttack() {
        if (this.world == null) return null;
        Player player = this.world.getClosestPlayerToEntity(this, 16.0);

        if (player != null && canEntityBeSeen(player) && player.gamemode.areMobsHostile()) {
            ((AetherBossList) player).aether$TryAddBossList(this);
            return player;
        }

        return null;
    }

    @Override
    public boolean canFight() {
        return isAlive() && isAgro;
    }

    @Override
    public float getBrightness(float partialTick) {
        return 1.0F;
    }

    @Override
    public int getLightmapCoord(float partialTick) {
        if (this.world == null) return super.getLightmapCoord(partialTick);
        return this.world.getLightmapCoord(15, 15);
    }

    private void attackEntity() {
        int totalShots = 4;
        float healthPercentage = (float) this.getHealth() / this.getMaxHealth();

        float fireballSpeed = 0.5f + (1.0f - healthPercentage) * 0.5f;

        float iceballSpeed = 0.1f + (1.0f - healthPercentage) * 0.2f;

        if (this.attackTime == 0 && this.world != null) {
            if (!this.world.isClientSide) {
                if (this.timesShot < totalShots) {
                    ProjectileElementFire elementFire = new ProjectileElementFire(this.world, this);
                    elementFire.setHeading(world.rand.nextDouble(), this.getLookAngle().y, world.rand.nextDouble(), fireballSpeed, 0.0F);
                    this.world.playSoundAtEntity(null, this, "mob.ghast.fireball", this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                    this.world.entityJoinedWorld(elementFire);
                    this.timesShot++;

                } else {
                    ProjectileElementIce elementIce = new ProjectileElementIce(this.world, this);
                    elementIce.setHeading(this.getLookAngle().x, this.getLookAngle().y, this.getLookAngle().z, iceballSpeed, world.rand.nextFloat());
                    this.world.playSoundAtEntity(null, this, "mob.ghast.fireball", this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 2.0F);
                    this.world.entityJoinedWorld(elementIce);
                    this.timesShot = 0;
                }
            }

            if (this.getHealth() <= (this.getMaxHealth() / 2)) {
                this.attackTime = 25;
            } else {
                this.attackTime = 35;
            }
        }

    }

    @Override
    public void readAdditionalSaveData(@NonNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.timesShot = tag.getInteger("timesShot");
        this.chatLog = tag.getByte("chatLog");
        this.isAgro = tag.getBoolean("isAgro");
    }

    @Override
    public void addAdditionalSaveData(@NonNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("timesShot", this.timesShot);
        tag.putByte("chatLog", (byte) this.chatLog);
        tag.putBoolean("isAgro", isAgro);
    }

    @Override
    public boolean hurt(Entity attacker, int damage, DamageType type) {
        if (attacker == null && type == null && damage == 100) {
            this.setHealthRaw(0);
            this.playDeathSound();
            this.onDeath(null);
            return true;
        }
        if (attacker instanceof ProjectileElementIce) {
            super.hurt(attacker, 100, type);

            if (target instanceof Player) {
                ((Player) target).triggerAchievement(AetherAchievements.ICE_DEFLECT);
            }

            if (this.getHealth() > 0 && this.world != null) {
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
            }

            return true;
        }

        if (attacker instanceof Player) {
            ((AetherBossList) attacker).aether$TryAddBossList(this);
        }

        if (!this.isAgro) {
            this.chatLog = 9;
        }
        return false;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public String getHurtSound() {
        return "aether:mob.sunspirit.hurt";
    }

    @Override
    public String getDeathSound() {
        return "aether:mob.sunspirit.death";
    }


    @Override
    public String getEntityTexture() {
        if (this.hurtTime > 0) {
            return "/assets/aether/textures/entity/boss_sunspirit/sunspirit_hurt.png";
        }
        return "/assets/aether/textures/entity/boss_sunspirit/sunspirit.png";
    }


    @Override
    public @NonNull String getDefaultEntityTexture() {
        if (this.hurtTime > 0) {
            return "/assets/aether/textures/entity/boss_sunspirit/sunspirit_hurt.png";
        }
        return "/assets/aether/textures/entity/boss_sunspirit/sunspirit.png";
    }

}
