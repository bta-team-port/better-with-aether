package teamport.aether.entity.monster.zephyr;

import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItemHoming;
import net.minecraft.core.entity.MobFlying;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.gamemode.Gamemodes;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.LightIndexHelper;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import org.joml.primitives.AABBd;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.block.AetherBlocks;
import teamport.aether.entity.player.PlayerUtil;
import teamport.aether.entity.projectile.windball.ProjectileWindball;

import java.util.ArrayList;
import java.util.List;

public class MobZephyr extends MobFlying implements Enemy {
    private static final int DATA_CHARGING = 16;
    private int courseChangeCooldown = 0;
    private double waypointX;
    private double waypointY;
    private double waypointZ;
    private Entity targetedEntity = null;
    private int aggroCooldown = 0;
    private int attackChargeO = 0;
    private int attackCharge = 0;

    public MobZephyr(World world) {
        super(world);
        this.setTextureIdentifier("aether", "zephyr");
        this.setSize(5.0F, 4.0F);
        this.scoreValue = 500;
        this.mobDrops.add(new WeightedRandomLootObject(AetherBlocks.AERCLOUD_WHITE.getDefaultStack(), 0, 6));
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

    @Override
    public int getMaxHealth() {
        return 10;
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_CHARGING, (byte) 0, Byte.class);
    }

    @Override
    public @NonNull String getEntityTexture() {
        return this.entityData.getByte(DATA_CHARGING) != 1 ? super.getEntityTexture() : "/assets/aether/textures/entity/zephyr_fire/" + this.getTextureReference() + ".png";
    }

    @Override
    public @NonNull String getDefaultEntityTexture() {
        return this.entityData.getByte(DATA_CHARGING) != 1 ? super.getEntityTexture() : "/assets/aether/textures/entity/zephyr_fire/" + this.getTextureReference() + ".png";
    }

    @Override
    public void tick() {
        if (this.world.isClientSide) {
            byte i = this.entityData.getByte(DATA_CHARGING);
            if (i > 0 && this.attackCharge == 0) {
                this.world.playSoundAtEntity(null, this, "aether:mob.zephyr.shoot", this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            }

            this.attackCharge += i;
            if (this.attackCharge < 0) {
                this.attackCharge = 0;
            }

            if (this.attackCharge >= 20) {
                this.attackCharge = 20;
            }

            if (this.attackCharge == 20 && i == 0) {
                this.world.playSoundAtEntity(null, this, "aether:mob.zephyr.shoot", this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                this.attackCharge = -40;
            }
        }

        super.tick();
    }

    @Override
    public boolean collidesWith(Entity entity) {
        return !(entity instanceof ProjectileWindball);
    }

    @Override
    @SuppressWarnings({"java:S6541", "java:S3776", "java:S1192"})
    public void updateAI() {
        if (!this.world.isClientSide && !this.world.getDifficulty().canHostileMobsSpawn()) {
            this.remove();
        } else {

            if (this.y < -2.0 || this.y > world.getHeightBlocks()) {
                this.remove();
            }

            this.tryToDespawn();
            double d = this.waypointX - this.x;
            double d1 = this.waypointY - this.y;
            double d2 = this.waypointZ - this.z;
            double d3 = d * d + d1 * d1 + d2 * d2;
            if (d3 < (double) 1.0F || d3 > (double) 3600.0F) {
                this.waypointX = this.x + (double) ((this.random.nextFloat() * 2.0F - 1.0F) * 16.0F);
                this.waypointY = this.y + (double) ((this.random.nextFloat() * 2.0F - 1.0F) * 16.0F);
                this.waypointZ = this.z + (double) ((this.random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            }

            if (this.courseChangeCooldown-- <= 0) {
                this.courseChangeCooldown += this.random.nextInt(5) + 2;
                d3 = MathHelper.sqrt(d3);
                if (this.isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, d3)) {
                    this.xd += d / d3 * 0.1;
                    this.yd += d1 / d3 * 0.1;
                    this.zd += d2 / d3 * 0.1;
                } else {
                    this.waypointX = this.x;
                    this.waypointY = this.y;
                    this.waypointZ = this.z;
                }
            }

            if (this.targetedEntity == null || this.aggroCooldown-- <= 0) {
                Entity potentialTarget = this.world.getClosestPlayerToEntity(this, 100.0);
                if (potentialTarget instanceof Player p && p.gamemode != Gamemodes.CREATIVE && p.gamemode != Gamemodes.SPECTATOR) {
                    this.targetedEntity = potentialTarget;
                }

                if (this.targetedEntity != null) {
                    this.aggroCooldown = 20;
                }
            }

            double maxAttackDist = 64.0F;
            if (this.targetedEntity != null && this.targetedEntity.distanceToSqr(this) < maxAttackDist * maxAttackDist) {
                double tx = this.targetedEntity.x - this.x;
                double ty = this.targetedEntity.y + (double) (this.targetedEntity.bbHeight / 2.0F) - (this.y + (double) (this.bbHeight / 2.0F));
                double tz = this.targetedEntity.z - this.z;
                float targetYaw = -((float) Math.atan2(tx, tz)) * 180.0F / (float) Math.PI;
                float deltaYaw = targetYaw - this.yRot;

                while (deltaYaw < -180.0F) {
                    deltaYaw += 360.0F;
                }

                while (deltaYaw >= 180.0F) {
                    deltaYaw -= 360.0F;
                }

                this.yRot += deltaYaw * 0.1F;
                this.yBodyRot = this.yRot;
                if (this.canEntityBeSeen(this.targetedEntity)) {
                    if (this.attackCharge == 10) {
                        this.world.playSoundAtEntity(null, this, "aether:mob.zephyr.call", this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                    }

                    this.attackCharge++;
                    if (this.attackCharge == 20) {
                        this.world.playSoundAtEntity(null, this, "aether:mob.zephyr.shoot", this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                        ProjectileWindball windball = new ProjectileWindball(this.world, this, tx, ty, tz);
                        windball.setPos(this.x, this.y + this.bbHeight / 2.0F, this.z);
                        this.world.entityJoinedWorld(windball);
                        this.attackCharge = -40;
                    }
                } else if (this.attackCharge > 0) {
                    this.attackCharge--;
                }
            } else {
                if (this.xd * this.xd + this.zd * this.zd > 0.001) {
                    float targetYaw = -((float) Math.atan2(this.xd, this.zd)) * 180.0F / (float) Math.PI;
                    float deltaYaw = targetYaw - this.yRot;

                    while (deltaYaw < -180.0F) {
                        deltaYaw += 360.0F;
                    }

                    while (deltaYaw >= 180.0F) {
                        deltaYaw -= 360.0F;
                    }

                    this.yRot += deltaYaw * 0.1F;
                    this.yBodyRot = this.yRot;
                }

                if (this.attackCharge > 0) {
                    this.attackCharge--;
                }
            }

            if (!this.world.isClientSide) {
                byte isCharging = (byte) (this.attackCharge > 10 ? 1 : 0);
                if (this.entityData.getByte(16) != isCharging) {
                    this.entityData.set(16, isCharging);
                }
            }
        }
    }

    private @Nullable Entity findPlayerToAttack() {
        Player player = PlayerUtil.getClosestNonInvisPlayerToEntity(this.world, this, (float) 100.0);
        if (player == null || !this.canEntityBeSeen(player) || !player.getGamemode().hasHostileMobs()) {
            return null;
        }
        return player;
    }

    private boolean isCourseTraversable(double x, double y, double z, double d3) {
        double d4 = (x - this.x) / d3;
        double d5 = (y - this.y) / d3;
        double d6 = (z - this.z) / d3;
        AABBd aabb = new AABBd(this.bb);

        for (int i = 1; (double) i < d3; ++i) {
            aabb.translate(d4, d5, d6);
            if (!this.world.areBlocksLoaded(aabb) || !this.world.getCubes(this, aabb).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean hurt(Entity attacker, int i, DamageType type) {
        if (super.hurt(attacker, i, type)) {
            if (this.passenger != attacker && this.vehicle != attacker && attacker != this) {
                this.targetedEntity = attacker;
                this.aggroCooldown = 60;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getLivingSound() {
        return "aether:mob.zephyr.call";
    }

    @Override
    public String getHurtSound() {
        return "aether:mob.zephyr.call";
    }

    @Override
    public String getDeathSound() {
        return "aether:mob.zephyr.call";
    }

    @Override
    public float getSoundVolume() {
        return 3.0F;
    }

    @Override
    public boolean canSpawnHere() {
        TilePos blockPos = new TilePos(this.x, this.bb.minY, this.z);

        return this.world.getDifficulty().canHostileMobsSpawn()
            && this.world.areBlocksLoaded(this.bb)
            && this.world.getCubes(this, this.bb).isEmpty()
            && this.world.canBlockSeeSky(blockPos)
            && super.canSpawnHere();
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }

    @Override
    public int getMaxPerPlayer() {
        return 3;
    }

    @Override
    public void onDeath(Entity killer) {
        List<WeightedRandomLootObject> storedDrops = new ArrayList<>(this.mobDrops);
        this.mobDrops.clear();
        super.onDeath(killer);
        this.mobDrops.addAll(storedDrops);
        if (!this.world.isClientSide) {
            for (WeightedRandomLootObject lootObject : storedDrops) {
                ItemStack dropStack = lootObject.getItemStack(this.random);
                if (dropStack != null && dropStack.stackSize > 0) {
                    if (killer instanceof Player) {
                        for (int i = 0; i < dropStack.stackSize; i++) {
                            ItemStack singleDrop = new ItemStack(dropStack.itemID, 1, dropStack.getMetadata());
                            EntityItemHoming homingItem = new EntityItemHoming(this.world, this.x, this.y, this.z, singleDrop, killer);
                            homingItem.xd = (this.random.nextFloat() - 0.5F) * 0.8F;
                            homingItem.yd = this.random.nextFloat() * 0.8F;
                            homingItem.zd = (this.random.nextFloat() - 0.5F) * 0.8F;
                            this.world.entityJoinedWorld(homingItem);
                        }
                    } else {
                        this.world.dropItem(new TilePos(this.x, this.y, this.z), dropStack);
                    }
                }
            }
        }
    }

    public int getAttackChargeO() {
        return attackChargeO;
    }

    public int getAttackCharge() {
        return attackCharge;
    }
}
