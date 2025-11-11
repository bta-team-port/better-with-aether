package teamport.aether.entity.monster.whirly;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.client.entity.particle.Particle;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.monster.MobCreeper;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import teamport.aether.blocks.AetherBlockTags;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.entity.AetherDeathMessage;
import teamport.aether.entity.AetherMobFallingToOverworld;
import teamport.aether.entity.monster.MobMonsterAether;
import teamport.aether.items.AetherItems;
import teamport.aether.items.accessory.AetherInvisibility;

import java.util.ArrayList;
import java.util.List;

public class MobWhirly extends MobMonsterAether implements Enemy, AetherDeathMessage, AetherMobFallingToOverworld {
    public int entcount = 0;
    public int Life;
    public List<Particle> fluffies;
    public float Angle;
    public float Speed;
    public float Curve;

    public static final int DATA_EVIL = 20;

    @Override
    public boolean canFallToOverworld() {
        return false;
    }

    public boolean getEvil() {
        return this.entityData.getInt(DATA_EVIL) > 0;
    }

    public void setEvil(boolean isEvil) {
        this.entityData.set(DATA_EVIL, (isEvil ? 1 : 0));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_EVIL, 0, Integer.class);
    }

    public MobWhirly(World world) {
        super(world);
        this.setSize(1.0F, 1.5F);
        this.setPos(this.x, this.y, this.z);
        this.moveSpeed = 0.6F;
        this.Angle = this.random.nextFloat() * 360.0F;
        this.Speed = this.random.nextFloat() * 0.025F + 0.025F;
        this.Curve = (this.random.nextFloat() - this.random.nextFloat()) * 0.1F;
        this.Life = this.random.nextInt(512) + 512;
        this.scoreValue = 0;
        this.fluffies = new ArrayList<>();
    }

    public void spawnInit() {
        if (random.nextInt(5) == 0) {
            this.setEvil(true);
        }
    }

    public void tick() {
        if (!this.world.isClientSide && !this.world.getDifficulty().canHostileMobsSpawn() && getEvil()) {
            this.remove();
        }
        super.tick();

    }

    public boolean makeStepSound() {
        return false;
    }

    public boolean collidesWith(Entity entity) {
        float launchSpeed = 0.75F;
        double distanceTo = entity.distanceTo(x, y, z);

        if (!(entity instanceof MobCreeper) && !(entity instanceof MobWhirly)) {
            switch (Direction.values()[world.rand.nextInt(Direction.values().length)]) {
                case NORTH:
                    entity.push(0, launchSpeed / 4, -launchSpeed / distanceTo);
                    break;

                case SOUTH:
                    entity.push(0, launchSpeed / 4, launchSpeed / distanceTo);
                    break;

                case EAST:
                    entity.push(launchSpeed / distanceTo, launchSpeed / 4, 0);
                    break;

                case WEST:
                    entity.push(-launchSpeed / distanceTo, launchSpeed / 4, 0);
                    break;
            }
        }

        return false;
    }

    public void updateAI() {
        if (this.getEvil()) {
            Player entityplayer = (Player) this.getPlayer();
            if (entityplayer != null && entityplayer.onGround) {
                this.target = entityplayer;
            }
        }

        if (this.target == null) {
            this.xd = Math.cos(0.01745329F * this.Angle) * (double) this.Speed;
            this.zd = -Math.sin(0.01745329F * this.Angle) * (double) this.Speed;
            this.Angle += this.Curve;
        } else {
            super.updateAI();
        }

        if (this.Life-- <= 0 || this.isInWaterOrRain()) {
            this.remove();
        }

        if (this.getPlayer() != null) {
            ++this.entcount;
        }

        int i;
        if (this.entcount >= 128) {
            if (this.getEvil() && this.target != null) {
                MobCreeper entitycreeper = new MobCreeper(this.world);
                entitycreeper.setPos(this.x, this.y + 0.75, this.z);
                entitycreeper.xd = (double) (this.random.nextFloat() - this.random.nextFloat()) * 0.125;
                entitycreeper.zd = (double) (this.random.nextFloat() - this.random.nextFloat()) * 0.125;
                this.world.entityJoinedWorld(entitycreeper);
                this.entcount = 0;
            } else {
                i = this.loot();
                if (i != 0) {
                    this.dropItem(i, 1);
                    this.entcount = 0;
                }
            }
        }

        int j1 = MathHelper.floor(this.x);
        int k1 = MathHelper.floor(this.y);
        int l1 = MathHelper.floor(this.z);
        if (this.world.getBlockId(j1, k1 + 1, l1) != 0) {
            this.Life -= 50;
        }
    }

    public int loot() {
        int i = this.random.nextInt(100) + 1;
        if (i == 100) {
            return AetherBlocks.BLOCK_GRAVITITE.id();
        } else if (i >= 96) {
            return AetherItems.ZANITE.id;
        } else if (i >= 91) {
            return AetherItems.PETAL_AECHOR.id;
        } else if (i >= 82) {
            return AetherItems.AMBROSIUM.id;
        } else if (i >= 75) {
            return AetherBlocks.DIRT_AETHER.id();
        } else if (i >= 64) {
            return AetherBlocks.ICESTONE.id();
        } else if (i >= 52) {
            return AetherItems.STICK_SKYROOT.id;
        } else if (i >= 38) {
            return AetherItems.AMBER.id;
        } else {
            return i > 20 ? AetherBlocks.LOG_SKYROOT.id() : AetherBlocks.QUICKSOIL.id();
        }
    }

    public boolean canSpawnHere() {
        int x = MathHelper.floor(this.x);
        int y = MathHelper.floor(this.bb.minY);
        int z = MathHelper.floor(this.z);
        int id = this.world.getBlockId(x, y - 1, z);
        if (Blocks.blocksList[id] == null) {
            return false;
        } else {
            if (this.random.nextInt(10) == 0) {
                return Blocks.blocksList[id].hasTag(AetherBlockTags.PASSIVE_MOBS_SPAWN);
            }
        }
        return false;
    }

    public Entity getPlayer() {
        Player entityplayer = this.world.getClosestPlayerToEntity(this, 16.0);
        if (entityplayer instanceof AetherInvisibility) {
            AetherInvisibility invPlayer = (AetherInvisibility) entityplayer;
            if (invPlayer.aether$isInvisible()) {
                entityplayer = this.world.getClosestPlayerToEntity(this, 2.0);
            }
        }
        return entityplayer != null && this.canEntityBeSeen(entityplayer) ? entityplayer : null;
    }

    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("Angle", this.Angle);
        tag.putFloat("Speed", this.Speed);
        tag.putFloat("Curve", this.Curve);
        tag.putShort("Life", (short) this.Life);
        tag.putShort("Counter", (short) this.entcount);
        tag.putBoolean("Evil", this.getEvil());
    }

    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.Angle = tag.getFloat("Angle");
        this.Speed = tag.getFloat("Speed");
        this.Curve = tag.getFloat("Curve");
        this.Life = tag.getShort("Life");
        this.entcount = tag.getShort("Counter");
        this.setEvil(tag.getBoolean("Evil"));
    }

    public boolean hurt(Entity entity, int i, DamageType type) {
        return false;
    }

    public int getMaxSpawnedInChunk() {
        return 1;
    }

    public boolean canClimb() {
        return false;
    }

}
