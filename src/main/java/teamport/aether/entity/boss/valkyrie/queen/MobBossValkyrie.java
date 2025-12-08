package teamport.aether.entity.boss.valkyrie.queen;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.Global;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.AetherMod;
import teamport.aether.achievements.AetherAchievements;
import teamport.aether.entity.MobUtil;
import teamport.aether.entity.boss.AetherBossList;
import teamport.aether.entity.boss.MobBoss;
import teamport.aether.entity.player.MessageMaker;
import teamport.aether.entity.projectile.ProjectileElementLightning;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.item.AetherItems;
import teamport.aether.world.AetherDimension;
import teamport.aether.world.feature.util.WorldFeaturePoint;
import teamport.aether.world.feature.util.map.DungeonMap;
import turniplabs.halplibe.helper.EnvironmentHelper;

import java.util.Comparator;
import java.util.Objects;

import static net.minecraft.core.net.command.TextFormatting.LIGHT_GRAY;
import static teamport.aether.AetherMod.TRANSLATOR;

public class MobBossValkyrie extends MobBoss {
    private boolean isSwinging;
    private boolean isReadyToDuel;
    private boolean isAgro;

    private int teleportTimer;
    private int chatTime;
    protected float wingSpeed;

    private static final int ATTACK_STRENGTH = 10;

    public MobBossValkyrie(@Nullable World world) {
        super(world);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "boss_valkyrie");
        this.setSize(0.8F, 2.0F);
        this.scoreValue = 50000;
        this.mobDrops.add(new WeightedRandomLootObject(AetherItems.TOOL_SWORD_HOLY.getDefaultStack(), 1));
        this.moveSpeed = 0.5F;
        this.footSize = 1.5f;
        this.chatColor = (byte) (LIGHT_GRAY.id & 255);
        this.canBreatheUnderwater();
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public void jump() {
        this.yd = 0.72;
    }

    @Override
    public void tick() {
        if (!isAgro) {
            this.moveSpeed = 0.0F;
        }

        this.yo = this.yd;
        super.tick();
        if (!this.onGround && this.target != null && this.yo >= 0.0 && this.yd < 0.0 && this.distanceTo(this.target) <= 16.0F && this.canEntityBeSeen(this.target)) {
            double a = this.target.x - this.x;
            double b = this.target.z - this.z;
            double angle = Math.atan2(a, b);
            this.xd = Math.sin(angle) * 0.25;
            this.zd = Math.cos(angle) * 0.25;
        }

        if (!this.onGround && this.yd < 0.0) {
            this.yd += 0.054999999701976776;
            if (this.yd < -0.2750000059604645) {
                this.yd = -0.2750000059604645;
            }
        }

        this.moveSpeed = this.target == null ? 0.5F : 1.0F;

        if (this.world != null && !this.world.getDifficulty().canHostileMobsSpawn() && (this.target != null || this.isAgro)) {
            this.target = null;
        }

        if (this.isSwinging) {
            this.prevSwingProgress += 0.15F;
            this.swingProgress += 0.15F;
            if (this.prevSwingProgress > 1.0F || this.swingProgress > 1.0F) {
                this.isSwinging = false;
                this.prevSwingProgress = 0.0F;
                this.swingProgress = 0.0F;
            }
        }

        if (!this.onGround) {
            this.wingSpeed += 0.75F;
        } else {
            this.wingSpeed += 0.15F;
        }

        if (this.wingSpeed > 6.283186F) {
            this.wingSpeed -= 6.283186F;
        }
    }

    @Override
    public void updateAI() {
        super.updateAI();
        ++this.teleportTimer;

        if (this.target == null || !this.target.isAlive()) {
            this.target = findPlayerToAttack();
        }

        boolean anyPlayerInArena = this.world.players.stream()
            .anyMatch(p -> p.distanceTo(this) <= AetherDimension.BOSS_DETECTION_RADIUS);

        if (!anyPlayerInArena && this.isAgro) {
            this.isAgro = false;
            this.returnToOriginalState();
            this.isReadyToDuel = false;
            return;
        }

        if (this.isReadyToDuel && this.target != null) {
            if (this.teleportTimer >= 125) {
                this.teleport(this.target.x, this.target.y, this.target.z, 8);
                this.remainingFireTicks = 0;
            } else if (this.teleportTimer % 5 == 0 && !this.canEntityBeSeen(this.target)) {
                this.teleportTimer += 50;
            }
        } else {
            this.teleportTimer = this.random.nextInt(40);
        }

        if (this.onGround && this.teleportTimer % 10 == 0) {
            this.xo = this.x;
            this.yo = this.y;
            this.zo = this.z;
        }

        if (this.chatTime > 0) {
            --this.chatTime;
        }
    }

    @Override
    public boolean interact(@NonNull Player entityplayer) {
        if (this.world == null || this.chatTime > 0 || (this.isReadyToDuel && this.target == entityplayer))
            return false;

        this.lookAt(entityplayer, 180.0F, 180.0F);
        world.playSoundAtEntity(null, this, "aether:mob.valkyrie.talk", 1.0f, 0.75F);

        if (!this.world.getDifficulty().canHostileMobsSpawn()) {
            MessageMaker.sendMessage(entityplayer, TRANSLATOR.translateKey("boss_valkyrie.peaceful"));
            world.playSoundAtEntity(null, this, "aether:mob.valkyrie.laugh", 1.0f, 0.75F);
            this.chatTime = 40;
            return true;
        }

        if (this.isReadyToDuel) {
            MessageMaker.sendMessage(entityplayer, TRANSLATOR.translateKey("boss_valkyrie.duel"));
            this.chatTime = 40;
            return true;
        }

        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if (itemstack != null && itemstack.itemID == AetherItems.MEDAL_VICTORY.id && itemstack.stackSize >= 10) {
            itemstack.stackSize -= 10;
            if (itemstack.stackSize <= 0) {
                entityplayer.destroyCurrentEquippedItem();
            }
            MessageMaker.sendMessage(entityplayer, TRANSLATOR.translateKey("boss_valkyrie.duel_start"));
            this.isReadyToDuel = true;
        } else {
            MessageMaker.sendMessage(entityplayer, TRANSLATOR.translateKey("boss_valkyrie.condition"));
        }
        this.chatTime = 40;
        return true;
    }

    @Override
    public void causeFallDamage(float distance) {
    }

    @Override
    public void spawnInit() {
        this.teleportTimer = this.random.nextInt(125);
    }

    @Override
    public Entity findPlayerToAttack() {
        if (!this.isReadyToDuel || !this.isAgro || this.world == null || !this.world.getDifficulty().canHostileMobsSpawn())
            return null;

        Entity newTarget = this.world.players.stream()
            .filter(Objects::nonNull)
            .filter(player -> player.getGamemode().areMobsHostile())
            .filter(player -> player.distanceTo(this) <= AetherDimension.BOSS_DETECTION_RADIUS)
            .filter(this::canEntityBeSeen)
            .min(Comparator.comparingDouble(this::distanceTo))
            .orElse(null);

        boolean currTargetIsBetter = (
            this.target != null
                && this.target.isAlive()
                && this.target.distanceToSqr(this) <= AetherDimension.BOSS_DETECTION_RANGE_SQR
                && (
                newTarget == null
                    || newTarget.distanceTo(this) < this.target.distanceTo(this)
            )
        );

        newTarget = currTargetIsBetter ? this.target : newTarget;

        if (newTarget instanceof AetherBossList) {
            ((AetherBossList) newTarget).aether$TryAddBossList(this);
        }

        return newTarget;
    }

    @Override
    public void onDeath(Entity entityKilledBy) {
        if (this.world == null) {
            super.onDeath(entityKilledBy);
            return;
        }
        this.world.players.stream()
            .filter(player -> player.distanceTo(this) < 32)
            .forEach(player -> {
                this.world.playSoundEffect(player, SoundCategory.WORLD_SOUNDS, player.x, player.y, player.z, "aether:achievement.silver", 0.5f, 1.0f);
                player.triggerAchievement(AetherAchievements.SILVER);

                MessageMaker.sendMessage(player, TRANSLATOR.translateKey("boss_valkyrie.dies"));
            });

        super.onDeath(entityKilledBy);
    }

    public void teleport(double x, double y, double z, int rad) {
        int ax = this.random.nextInt(rad + 1) * (this.random.nextInt(2) * 2 - 1);
        int ay = this.random.nextInt(rad / 2) * (this.random.nextInt(2) * 2 - 1);
        int az = (rad - Math.abs(ax)) * (this.random.nextInt(2) * 2 - 1);
        x += ax;
        y += ay;
        z += az;

        int newX = (int) Math.floor(x);
        int newY = (int) Math.floor(y);
        int newZ = (int) Math.floor(z);
        boolean flag = false;

        if (returnPoint == null) {
            AetherMod.LOGGER.info("Queen Valkyrie at {}, {}, {} has no return point!", x, y, z);
            return;
        }
        WorldFeaturePoint p1 = new WorldFeaturePoint(this.returnPoint.getX() - 9, this.returnPoint.getY() - 3, this.returnPoint.getZ() - 16);
        for (int q = 0; q < 128 && !flag; ++q) {
            int ix = newX + (this.random.nextInt(6) - this.random.nextInt(6));
            int iy = newY + this.random.nextInt(rad / 2);
            int iz = newZ + (this.random.nextInt(6) - this.random.nextInt(6));

            for (int searchY = iy; searchY >= p1.getY(); --searchY) {
                if (searchY < 0 || searchY + 1 >= Objects.requireNonNull(this.world).getHeightBlocks()) continue;

                boolean isAirAbove = this.isAirySpace(ix, searchY, iz);
                boolean isAirHead = this.isAirySpace(ix, searchY + 1, iz);
                boolean isGroundBelow = !this.isAirySpace(ix, searchY - 1, iz);

                if (isAirAbove && isAirHead && isGroundBelow
                    && ix >= p1.getX() && ix <= p1.getX() + 16
                    && searchY >= p1.getY() && searchY <= p1.getY() + 16
                    && iz >= p1.getZ() && iz <= p1.getZ() + 16
                ) {
                    newX = ix;
                    newY = searchY;
                    newZ = iz;
                    flag = true;
                    break;
                }
            }
        }

        if (!flag) {
            this.teleportFailed();
        } else {
            if (!EnvironmentHelper.isServerEnvironment()) {
                ParticleMaker.spawnParticle(world, "explode", this.x, this.y + 1, this.z, 0.0, 0.0, 0.0, 0);
                ParticleMaker.spawnParticle(world, "smoke", this.x, this.y + 1, this.z, 0.0, 0.0, 0.0, 0);
                ParticleMaker.spawnParticle(world, "largesmoke", this.x, this.y + 1, this.z, 0.0, 0.0, 0.0, 0);
            }
            this.setPos(newX + 0.5, newY, newZ + 0.5);
            world.playSoundAtEntity(null, this, "mob.ghast.fireball", 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F));
            this.xd = 0.0;
            this.yd = 0.0;
            this.zd = 0.0;
            this.moveForward = 0.0F;
            this.moveStrafing = 0.0F;
            this.isJumping = false;
            this.xRot = 0.0F;
            this.yRot = 0.0F;
            this.setPathToEntity(null);
            this.yBodyRot = this.random.nextFloat() * 360.0F;
            this.teleportTimer = this.random.nextInt(Global.TICKS_PER_SECOND);
        }
    }

    public boolean isAirySpace(int x, int y, int z) {
        if (this.world == null) return true;
        int p = this.world.getBlockId(x, y, z);
        Block<?> block = this.world.getBlock(x, y, z);
        Block<?> blockTwo = Blocks.blocksList[p];

        return p == 0 || blockTwo == null || blockTwo.getCollisionBoundingBoxFromPool(this.world, x, y, z) == null || block != null && block.getMaterial() == Material.water;
    }

    public void swingArm() {
        if (!this.isSwinging) {
            this.isSwinging = true;
            this.prevSwingProgress = 0.0F;
            this.swingProgress = 0.0F;
        }
    }

    public void teleportFailed() {
        this.teleportTimer -= this.random.nextInt(40) + 40;
        if (this.y <= 0.0) {
            this.teleportTimer = 5 * Global.TICKS_PER_SECOND;
        }
    }

    @Override
    public boolean canSpawnHere() {
        int i = MathHelper.floor(this.x);
        int j = MathHelper.floor(this.bb.minY);
        int k = MathHelper.floor(this.z);

        return this.world != null && this.world.getFullBlockLightValue(i, j, k) > 8
            && this.world.getIsAnySolidGround(this.bb)
            && this.world.getCollidingSolidBlockBoundingBoxes(this, this.bb).isEmpty()
            && !this.world.getIsAnyLiquid(this.bb);
    }

    @Override
    public void addAdditionalSaveData(@NonNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putShort("teleportTimer", (short) this.teleportTimer);
        tag.putBoolean("isReadyToDuel", this.isReadyToDuel);
        tag.putBoolean("isAgro", this.isAgro);
    }

    @Override
    public void readAdditionalSaveData(@NonNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.teleportTimer = tag.getShort("teleportTimer");
        this.isReadyToDuel = tag.getBoolean("isReadyToDuel");
        this.isAgro = tag.getBoolean("isAgro");
    }

    @Override
    public boolean canFight() {
        return isAlive() && isReadyToDuel;
    }

    @Override
    public boolean hurt(Entity attacker, int damage, DamageType type) {
        /// if /kill (jank!)
        if (attacker == null && type == null && damage == 100) {
            return MobUtil.killMob(this);
        }
        if (this.world == null) return false;
        /// need to acquire more medals
        if (!this.isReadyToDuel) {
            if (!(attacker instanceof Player) || this.chatTime > 0) {
                return false;
            }
            if (!world.getDifficulty().canHostileMobsSpawn()) {
                MessageMaker.sendMessage((Player) attacker, TRANSLATOR.translateKey("boss_valkyrie.weakling"));
                world.playSoundAtEntity(null, this, "aether:mob.valkyrie.laugh", 1.0f, 0.75F);
            } else {
                String message = this.random.nextInt(2) == 0
                    ? "boss_valkyrie.fight_weaklings"
                    : "boss_valkyrie.collect_medals";
                world.playSoundAtEntity(null, this, "aether:mob.valkyrie.talk", 1.0f, 0.75F);
                MessageMaker.sendMessage((Player) attacker, TRANSLATOR.translateKey(message));
            }
            this.chatTime = 40;
            return false;
        }

        /// can fight valk
        if (this.target == null && attacker instanceof Player) {
            if (!world.getDifficulty().canHostileMobsSpawn()) {
                if (this.chatTime <= 0) {
                    MessageMaker.sendMessage((Player) attacker, TRANSLATOR.translateKey("boss_valkyrie.weakling"));
                    world.playSoundAtEntity(null, this, "aether:mob.valkyrie.laugh", 1.0f, 0.75F);
                    this.chatTime = 40;
                }
                return false;
            }

            // Lock dungeon and set boss target
            DungeonMap.runWithDungeon(dungeonID, d -> d.lock(world));
            MessageMaker.sendMessage((Player) attacker, TRANSLATOR.translateKey("boss_valkyrie.target"));
            ((AetherBossList) attacker).aether$TryAddBossList(this);

            this.chatTime = 2 * Global.TICKS_PER_SECOND;
            this.target = attacker;
            this.isAgro = true;

            this.world.players.stream()
                .filter(player -> player.distanceTo(this) < 32)
                .forEach(player -> ((AetherBossList) player).aether$TryAddBossList(this));
        }

        if (attacker instanceof Player) {
            ((AetherBossList) attacker).aether$TryAddBossList(this);
        }

        this.teleportTimer += 2 * Global.TICKS_PER_SECOND;
        if (type == AetherMod.HOLY) {
            return super.hurt(attacker, damage / 2, type);
        }
        return super.hurt(attacker, damage, type);
    }


    @Override
    public void attackEntity(@NonNull Entity entity, float distance) {
        if (this.getHealth() < this.getMaxHealth() / 2 && distance > 5.0F) {
            double d = entity.x - this.x;
            double d1 = entity.z - this.z;
            if (this.attackTime == 0) {
                if (this.world != null && !this.world.isClientSide) {
                    ProjectileElementLightning elementLightning = new ProjectileElementLightning(this.world, this);
                    elementLightning.setHeading(world.rand.nextDouble(), this.getLookAngle().y + 5, world.rand.nextDouble(), 0.5f, 0.0f);
                    this.world.playSoundAtEntity(null, this, "mob.ghast.fireball", this.getSoundVolume(), (this.random.nextFloat() + this.random.nextFloat()) * 1.2F + 1.0F);
                    this.world.entityJoinedWorld(elementLightning);
                }
                this.attackTime = 50;
            }
            this.yRot = (float) (Math.atan2(d1, d) * 180.0 / Math.PI) - 90.0F;
            this.hasAttacked = true;
        }


        if (this.attackTime <= 0 && distance < 2.75F && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY) {
            this.attackTime = 20;
            this.swingArm();
            entity.hurt(this, ATTACK_STRENGTH, AetherMod.HOLY);

            if (this.target != null && entity == this.target && entity instanceof Player) {
                Player target = (Player) entity;

                if (this.world != null && !target.isAlive() && this.chatTime <= 0) {
                    this.world.playSoundAtEntity(null, this, "aether:mob.valkyrie.laugh", 1.0f, 0.75F);
                }
            }
        }
    }

    @Override
    public String getLivingSound() {
        return null;
    }

    @Override
    public String getHurtSound() {
        return "aether:mob.valkyrie.hurt";
    }

    @Override
    public String getDeathSound() {
        return "aether:mob.valkyrie.death";
    }

    @Override
    public void playHurtSound() {
        if (this.world == null) return;
        this.world.playSoundAtEntity(null, this, this.getHurtSound(), 0.75f, 0.75F);
    }

    @Override
    public void playDeathSound() {
        if (this.world == null) return;
        this.world.playSoundAtEntity(null, this, this.getDeathSound(), 1.0f, 0.75F);
    }

    @Override
    public int getMaxHealth() {
        return 750;
    }

    @Override
    public ItemStack getHeldItem() {
        return new ItemStack(AetherItems.TOOL_SWORD_HOLY, 1);
    }
}
