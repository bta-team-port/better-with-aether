package teamport.aether.entity.monster.zephyr;

import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.MobFlying;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlocks;
import teamport.aether.entity.AetherDeathMessage;
import teamport.aether.entity.player.PlayerUtil;
import teamport.aether.entity.projectile.ProjectileWindball;

public class MobZephyr extends MobFlying implements Enemy, AetherDeathMessage {
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
        this.textureIdentifier = NamespaceID.getPermanent("aether", "zephyr");
        this.setSize(5.0F, 4.0F);
        this.scoreValue = 500;
        this.mobDrops.add(new WeightedRandomLootObject(AetherBlocks.AERCLOUD_WHITE.getDefaultStack(), 0, 6));
    }

    @Override
    public float getBrightness(float partialTick) {
        return 1.0F;
    }

    @Override
    public int getLightmapCoord(float partialTick) {
        return this.world.getLightmapCoord(15, 15);
    }

    @Override
    public int getMaxHealth() {
        return 10;
    }

    @Override
    public void defineSynchedData() {
        this.entityData.define(16, (byte) 0, Byte.class);
    }

    @Override
    public String getEntityTexture() {
        return this.entityData.getByte(16) != 1 ? super.getEntityTexture() : "/assets/aether/textures/entity/zephyr_fire/" + this.getTextureReference() + ".png";
    }

    @Override
    public @NonNull String getDefaultEntityTexture() {
        if (this.entityData.getByte(16) != 1) {
            String entityTexture = super.getEntityTexture();
            if (entityTexture != null) return entityTexture;
        }
        return "/assets/aether/textures/entity/zephyr_fire/" + this.getTextureReference() + ".png";
    }

    @SuppressWarnings("java:S1192")
    @Override
    public void tick() {
        if (this.world != null && this.world.isClientSide) {
            byte i = this.entityData.getByte(16);
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

    @SuppressWarnings({"java:S6541", "java:S3776", "java:S1192"})
    @Override
    public void updateAI() {
        if (this.world != null && !this.world.isClientSide && !this.world.getDifficulty().canHostileMobsSpawn()) {
            this.remove();
        }

        if (this.y < -2.0 || this.y > 256.0) {
            this.remove();
        }

        this.tryToDespawn();
        this.attackChargeO = this.attackCharge;
        double d = this.waypointX - this.x;
        double d1 = this.waypointY - this.y;
        double d2 = this.waypointZ - this.z;
        double d3 = Math.max(0.001F, MathHelper.sqrt(d * d + d1 * d1 + d2 * d2));
        if (d3 < 1.0 || d3 > 60.0) {
            this.waypointX = this.x + ((this.random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.waypointY = this.y + ((this.random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.waypointZ = this.z + ((this.random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        }

        if (this.courseChangeCooldown-- <= 0) {
            this.courseChangeCooldown += this.random.nextInt(5) + 2;
            if (this.isCourseTraversable(d3)) {
                this.xd += d / d3 * 0.1;
                this.yd += d1 / d3 * 0.1;
                this.zd += d2 / d3 * 0.1;
            } else {
                this.waypointX = this.x;
                this.waypointY = this.y;
                this.waypointZ = this.z;
            }
        }

        if (this.targetedEntity != null && this.targetedEntity.removed) {
            this.targetedEntity = null;
        }

        if (this.targetedEntity == null || this.aggroCooldown-- <= 0) {
            this.targetedEntity = this.findPlayerToAttack();
            if (this.targetedEntity != null) {
                this.aggroCooldown = 20;
            }

            if (this.targetedEntity != null && !((Player) this.targetedEntity).getGamemode().areMobsHostile()) {
                this.targetedEntity = null;
            }
        }

        double d4 = 64.0;
        if (this.targetedEntity != null && this.targetedEntity.distanceToSqr(this) < d4 * d4) {
            double d8 = 4.0;
            Vec3 vec3 = this.getViewVector(1.0F);
            double dX = this.targetedEntity.x - this.x;
            double dY = this.targetedEntity.y - this.y;
            double dZ = this.targetedEntity.z - this.z;
            double dist = MathHelper.sqrt(dX * dX + dY * dY + dZ * dZ);
            double vX = dX + this.targetedEntity.xd * dist / 7.5 - vec3.x * d8;
            double vY = dY + this.targetedEntity.yd * dist / 7.5 - ((this.bbHeight / 2.0F) + 0.5);
            double vZ = dZ + this.targetedEntity.zd * dist / 7.5 - vec3.z * d8;
            this.yBodyRot = this.yRot = -((float) Math.atan2(vX, vZ)) * 180.0F / 3.1415927F;
            if (this.canEntityBeSeen(this.targetedEntity)) {
                if (this.attackCharge == 10 && this.world != null) {
                    this.world.playSoundAtEntity(null, this, "aether:mob.zephyr.call", this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                }

                ++this.attackCharge;
                if (this.attackCharge == 20 && this.world != null) {
                    this.world.playSoundAtEntity(null, this, "aether:mob.zephyr.shoot", this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                    ProjectileWindball windball = new ProjectileWindball(this.world, this, vX, vY, vZ);
                    windball.setPos(this.x + vec3.x * d8, this.y + (this.bbHeight / 2.0F) - 0.5, this.z + vec3.z * d8);
                    this.world.entityJoinedWorld(windball);
                    this.attackCharge = -40;
                }
            } else if (this.attackCharge > 0) {
                --this.attackCharge;
            } else {
                this.targetedEntity = null;
            }
        } else {
            this.yBodyRot = this.yRot = -((float) Math.atan2(this.xd, this.zd)) * 180.0F / 3.1415927F;
            if (this.attackCharge > 0) {
                --this.attackCharge;
            }
        }

        if (this.world != null && !this.world.isClientSide) {
            byte chargeData = this.entityData.getByte(16);
            byte chargeState = (byte) (this.attackCharge <= 10 ? 0 : 1);
            if (chargeData != chargeState) {
                this.entityData.set(16, chargeState);
            }
        }
    }

    private Entity findPlayerToAttack() {
        if (this.world == null) return null;
        Player player = PlayerUtil.getClosestNonInvisPlayerToEntity(this.world, this, (float) 100.0);
        if (player == null || !this.canEntityBeSeen(player) || !player.getGamemode().areMobsHostile()) {
            return null;
        }
        return player;
    }

    private boolean isCourseTraversable(double d3) {
        double d4 = (this.waypointX - this.x) / d3;
        double d5 = (this.waypointY - this.y) / d3;
        double d6 = (this.waypointZ - this.z) / d3;
        AABB axisalignedbb = this.bb.copy();

        for (int i = 1; i < d3; ++i) {
            axisalignedbb.move(d4, d5, d6);
            if (this.world == null || !this.world.getCubes(this, axisalignedbb).isEmpty()) {
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
        if (this.world == null) return false;

        boolean tooManyZephyrs = world.loadedEntityList.stream()
            .filter(MobZephyr.class::isInstance)
            .filter(e -> e.distanceTo(this) <= 64)
            .count() > 5;

        if (tooManyZephyrs) return false;

        int x = MathHelper.floor(this.x);
        int y = MathHelper.floor(this.bb.minY);
        int z = MathHelper.floor(this.z);

        return this.world.getDifficulty().canHostileMobsSpawn()
            && this.world.checkIfAABBIsClear(this.bb)
            && this.random.nextInt(10) == 0
            && this.world.getCubes(this, this.bb).isEmpty()
            && this.world.canBlockSeeTheSky(x, y, z);
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }

    public int getAttackChargeO() {
        return attackChargeO;
    }

    public int getAttackCharge() {
        return attackCharge;
    }
}
