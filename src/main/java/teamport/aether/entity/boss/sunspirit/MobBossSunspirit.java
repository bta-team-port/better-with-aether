package teamport.aether.entity.boss.sunspirit;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLightning;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.LightIndexHelper;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import sunsetsatellite.catalyst.core.util.vector.Vec2f;
import teamport.aether.achievements.AetherAchievements;
import teamport.aether.entity.boss.AetherBossList;
import teamport.aether.entity.boss.MobBoss;
import teamport.aether.entity.monster.fireminion.MobFireMinion;
import teamport.aether.entity.player.MessageMaker;
import teamport.aether.entity.projectile.ProjectileElementFire;
import teamport.aether.entity.projectile.ProjectileElementIce;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.world.AetherDimension;
import teamport.aether.world.feature.util.map.DungeonMap;
import turniplabs.halplibe.helper.EnvironmentHelper;

import static net.minecraft.core.net.command.TextFormatting.*;
import static teamport.aether.AetherMod.TRANSLATOR;

public class MobBossSunspirit extends MobBossFlying {
    @Nullable
    private Entity target;
    private boolean isAgro;
    private int timesShot = 0;

    private int chatLog;
    private int chatCooldown;
    private static final int START_FIGHT = 9;
    private static final int DATA_AGGRO = 17;

    private static final double DEFAULT_SPEED = 0.85;
    private static final double ADDED_MAX_SPEED = 0.45;
    private final Vec2f defaultVector = new Vec2f(0.25f, 0.25f);

    public MobBossSunspirit(@Nullable World world) {
        super(world);
        this.setSize(2.25F, 3.0F);
        this.setTextureIdentifier("aether", "boss_sunspirit");
        this.fireImmune = true;
        this.maxHurtTime = 40;
        this.scoreValue = 100000;
        this.maxFireTicks = 0;
        this.remainingFireTicks = 0;
        this.chatColor = (byte) (TextFormatting.YELLOW.id & 255);
        this.footSize = 2;
        this.canBreatheUnderwater();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_AGGRO, 0, Integer.class);
    }

    private void syncAggroState() {
        if (this.world.isClientSide) {
            this.isAgro = this.entityData.getInt(DATA_AGGRO) != 0;
        } else {
            this.entityData.set(DATA_AGGRO, this.isAgro ? 1 : 0);
        }
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
            this.moveSunspirit();
            this.setFloorOnFire();
            this.target = this.findPlayerToAttack();
            if (target != null) {
                this.lookAt(this.target, 20.0F, 20.0F);
                this.attackEntity();
            } else if (this.world.getClosestPlayerToEntity(this, AetherDimension.BOSS_DETECTION_RADIUS) == null) {
                this.returnToOriginalState();
            }
        }
    }

    private void setFloorOnFire() {
        int ix = (int) Math.floor(this.x);
        int iy = (int) Math.floor(this.y - 1);
        int iz = (int) Math.floor(this.z);
        Block<?> block = this.world.getBlock(ix, iy, iz);
        if (block.id() == Blocks.AIR.id()) {
            this.world.setBlockWithNotify(ix, iy, iz, Blocks.FIRE.id());
        }
    }


    @SuppressWarnings("java:S131")
    protected void moveSunspirit() {
        double speed = DEFAULT_SPEED + MathHelper.lerp(0.0f, ADDED_MAX_SPEED, 1.0f - this.getHealth() / (double) this.getMaxHealth());
        Vector3d currentPos = new Vector3d(x, y, z);
        Vector3d nextPos = new Vector3d(
            x + xd + defaultVector.x * speed + (defaultVector.x > 0 ? bbWidth / 2 : -bbWidth / 2),
            y,
            z + zd + defaultVector.y * speed + (defaultVector.y > 0 ? bbWidth / 2 : -bbWidth / 2)
        );
        HitResult hitResult = this.world.checkBlockCollisionBetweenPoints(currentPos, nextPos, false);
        if (hitResult != null || this.isInWall()) {
            rotateSunspirit(MathHelper.toRadians((float) (90.0f + (this.random.nextFloat() + 0.5) * 45.0f)));
        }
        this.xd = this.defaultVector.x * speed;
        this.zd = this.defaultVector.y * speed;
    }

    private void rotateSunspirit(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double theX = this.defaultVector.x * cos - this.defaultVector.y * sin;
        double theZ = this.defaultVector.x * sin + this.defaultVector.y * cos;
        this.defaultVector.x = theX;
        this.defaultVector.y = theZ;
    }

    @Override
    public void returnToHome() {
        if (returnPoint == null || !hasHadReturnPointSet) {
            return;
        }
        moveTo(returnPoint.getX() + 0.5, returnPoint.getY(), returnPoint.getZ() + 0.5, 0, 0);
        this.xd = 0.0;
        this.yd = 0.0;
        this.zd = 0.0;
    }

    @Override
    public void knockBack(Entity entity, int damage, double xd, double yd) {/* cannot be knockbacked */ }

    @Override
    public void tick() {
        if (!this.world.getDifficulty().canHostileMobsSpawn() && this.isAgro) {
            if (!EnvironmentHelper.isMultiplayerServer()) {
                MobBoss.stop();
            }
            this.isAgro = false;
            this.chatLog = 0;
            this.returnToOriginalState();
            this.evaporateMaterialWithEffect(Materials.FIRE);
        }
        super.tick();
        this.syncAggroState();
        this.evaporateMaterialWithEffect(Materials.WATER);
        if (this.chatCooldown > 0) {
            --this.chatCooldown;
            this.maxFireTicks = this.remainingFireTicks = 0;
        }
    }

    private void evaporateMaterialWithEffect(Material material) {
        if (this.getHealth() > 0) {
            double a = this.random.nextDouble() - 0.5;
            double b = this.random.nextDouble();
            double c = this.random.nextDouble() - 0.5;

            double flameX = this.x + a * b;
            double flameY = this.bb.minY + b - 0.5;
            double flameZ = this.z + c * b;

            ParticleMaker.spawnParticle(world, "flame", flameX, flameY, flameZ, 0.0, -0.075F, 0.0, 0);
            this.evaporateWater(material);
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

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public void fireHurt() {/*cannot be set on fire*/}

    @Override
    public void lavaHurt() {/*cannot be take damage from lava*/}

    @Override
    public void thunderHit(EntityLightning bolt) {/* cannot be take light */}

    public void evaporateWater(Material material) {
        int centerX = MathHelper.floor(this.x);
        int centerZ = MathHelper.floor(this.z);
        int radius = 9;

        for (int dx = -radius; dx < radius; dx++) {
            for (int dz = -radius; dz < radius; dz++) {
                int x = centerX + dx;
                int z = centerZ + dz;

                for (int i = 0; i < 9; ++i) {
                    int y = (int) (this.yo - 2 + i);
                    TilePos tilePos = new TilePos(x, y, z);
                    if (this.world.getBlockMaterial(tilePos) == material) {
                        this.world.setBlockTypeNotify(tilePos, Blocks.AIR);
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

    @SuppressWarnings({"java:S3776"})
    public boolean chatWithMe(Player player) {
        if (isAgro && target != null) {
            return false;
        }

        if (this.chatCooldown <= 0) {
            if (!this.world.getDifficulty().canHostileMobsSpawn()) {
                MessageMaker.sendMessage(player, ORANGE + TRANSLATOR.translateKey("boss_sunspirit.peaceful_" + this.random.nextInt(4)));
                this.world.playSoundAtEntity(null, this, "aether:mob.sunspirit.talk", 1.0f, 1.0f);
                this.chatCooldown = 40;
                return false;
            }
            if (this.chatLog < START_FIGHT) {
                MessageMaker.sendMessage(player, ORANGE + TRANSLATOR.translateKey("boss_sunspirit.chat_" + this.chatLog));
                if (this.chatLog >= 5 && this.chatLog < 8) {
                    MessageMaker.sendMessage(player, ORANGE + TRANSLATOR.translateKey("boss_sunspirit.chat_" + this.chatLog + "_1"));
                }
                this.world.playSoundAtEntity(null, this, "aether:mob.sunspirit.talk", 1.0f, 1.0f);
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
                this.rotateSunspirit(this.random.nextInt(360));
                DungeonMap.runWithDungeon(dungeonID, d -> d.lock(this.world));

                if (!EnvironmentHelper.isMultiplayerServer()) {
                    MobBoss.play("aether:aether_music_boss.fireboss", this.x, this.y, this.z);
                }

                return true;
            }
            if (this.target == null && !this.isAgro) {
                MessageMaker.sendMessage(player, RED + TRANSLATOR.translateKey("boss_sunspirit.fight.repeat"));
                this.world.playSoundAtEntity(null, this, "aether:mob.sunspirit.talk", 1.0f, 1.0f);
                this.chatLog = START_FIGHT;
                this.chatCooldown = 40;
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
        DungeonMap.runWithDungeon(dungeonID, d -> d.unlock(world));

        if (!this.world.isClientSide && world.dimension == AetherDimension.getAether()) {
            AetherDimension.unlockDaylightCycle(world);
        }

        world.players.stream()
            .filter(player -> player.distanceTo(this) < 32)
            .forEach(players -> {
                MessageMaker.sendMessage(players, LIGHT_BLUE + TRANSLATOR.translateKey("boss_sunspirit.dies"));
                players.triggerAchievement(AetherAchievements.GOLD);
            });

        this.world.playSoundAtEntity(null, this, "aether:achievement.gold", 0.5f, 1.0f);

        if (!EnvironmentHelper.isMultiplayerServer()) {
            MobBoss.stop();
        }

        super.onDeath(entityKilledBy);
    }

    @Override
    public int getMaxHealth() {
        return 1000;
    }

    public Entity findPlayerToAttack() {
        Player player = this.world.getClosestPlayerToEntity(this, 32.0);
        if (player != null && canEntityBeSeen(player) && player.gamemode.hasHostileMobs()) {
            ((AetherBossList) player).aether$TryAddBossList(this);
            return player;
        }
        return null;
    }

    @Override
    public boolean canFight() {
        boolean active = this.world.isClientSide
            ? this.entityData.getInt(DATA_AGGRO) != 0
            : this.isAgro;
        return isAlive() && active;
    }

    @Override
    public float getBrightness(float partialTick) {
        return 1.0F;
    }

    @Override
    public byte getLightIndex(float partialTick) {
        byte light = super.getLightIndex(partialTick);
        light = LightIndexHelper.setSkyLight(light, 15);
        return LightIndexHelper.setBlockLight(light, 15);
    }

    private void attackEntity() {
        int totalShots = 4;
        float healthPercentage = (float) this.getHealth() / this.getMaxHealth();
        float fireballSpeed = 0.5f + (1.0f - healthPercentage) * 0.5f;
        float iceballSpeed = 0.1f + (1.0f - healthPercentage) * 0.2f;

        if (this.attackTime != 0) {
            return;
        }
        if (!this.world.isClientSide) {
            @NotNull Vector3dc viewVector = this.getViewVector(1.0F); // I dont know how this would be null
            assert viewVector != null;
            if (this.timesShot < totalShots) {
                ProjectileElementFire elementFire = new ProjectileElementFire(this.world, this);
                elementFire.setHeading(world.rand.nextDouble(), viewVector.y(), world.rand.nextDouble(), fireballSpeed, 0.0F);
                this.world.playSoundAtEntity(null, this, "mob.ghast.fireball", this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                this.world.entityJoinedWorld(elementFire);
                this.timesShot++;

            } else {
                ProjectileElementIce elementIce = new ProjectileElementIce(this.world, this);
                elementIce.setHeading(viewVector.x(), viewVector.y(), viewVector.z(), iceballSpeed, world.rand.nextFloat());
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
        if (!this.world.getDifficulty().canHostileMobsSpawn()) {
            return false;
        }
        if (attacker instanceof ProjectileElementIce) {
            return hurt(attacker, type);
        }
        if (attacker instanceof Player) {
            ((AetherBossList) attacker).aether$TryAddBossList(this);
        }

        if (!this.isAgro && this.chatLog < START_FIGHT) {
            this.chatLog = START_FIGHT;
            this.chatCooldown = 40;
            this.world.players.stream()
                .filter(p -> p.distanceToSqr(this) < 32 * 32)
                .forEach(p -> MessageMaker.sendMessage(p, RED + TRANSLATOR.translateKey("boss_sunspirit.fight.start")));
            this.world.playSoundAtEntity(null, this, "aether:mob.sunspirit.talk", 1.0f, 0.5f);
            this.isAgro = true;
            this.target = attacker instanceof Player ? attacker : this.findPlayerToAttack();
            this.rotateSunspirit(this.random.nextInt(360));
            DungeonMap.runWithDungeon(dungeonID, d -> d.lock(this.world));

            if (!EnvironmentHelper.isMultiplayerServer()) {
                MobBoss.play("aether:aether_music_boss.fireboss", this.x, this.y, this.z);
            }
        }

        return false;
    }

    private boolean hurt(Entity attacker, DamageType type) {
        super.hurt(attacker, 100, type);
        triggerAchievement();
        spawnMinions();
        return true;
    }

    private void spawnMinions() {
        if (this.getHealth() <= 0) {
            return;
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
    }

    private void triggerAchievement() {
        if (target instanceof Player player) {
            player.triggerAchievement(AetherAchievements.ICE_DEFLECT);
        }
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
    public @NonNull String getEntityTexture() {
        return this.getDefaultEntityTexture();
    }

    @Override
    public @NonNull String getDefaultEntityTexture() {
        if (this.hurtTime > 0) {
            return "/assets/aether/textures/entity/boss_sunspirit/sunspirit_hurt.png";
        }
        return "/assets/aether/textures/entity/boss_sunspirit/sunspirit.png";
    }

}
