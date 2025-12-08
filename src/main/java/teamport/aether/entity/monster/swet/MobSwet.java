package teamport.aether.entity.monster.swet;

import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.LightLayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlockTags;
import teamport.aether.block.AetherBlocks;
import teamport.aether.entity.AetherDeathMessage;
import teamport.aether.entity.monster.MobMonsterAether;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.item.AetherItems;
import teamport.aether.item.accessory.AetherInvisibility;

import java.util.ArrayList;
import java.util.List;

public class MobSwet extends MobMonsterAether implements Enemy, AetherDeathMessage {
    private boolean friendly;
    private double ydO;
    protected int jumpDelay;
    protected int grabDelay;

    public MobSwet(World world) {
        super(world);
        this.heightOffset = 0.0F;
        this.scoreValue = 200;
        this.setSize(1.4F, 1.2F);
        this.setPos(this.x, this.y, this.z);
        this.jumpDelay = 20;
        this.textureIdentifier = NamespaceID.getPermanent("aether", "swet");
        this.moveSpeed = 1.5F;
        this.mobDrops.add(new WeightedRandomLootObject(AetherBlocks.AERCLOUD_BLUE.getDefaultStack(), 0));
    }

    @Override
    public List<WeightedRandomLootObject> getMobDrops() {
        List<WeightedRandomLootObject> drops = new ArrayList<>();
        drops.add(new WeightedRandomLootObject(AetherBlocks.AERCLOUD_BLUE.getDefaultStack(), 1, 2));
        return drops;
    }

    @Override
    public void causeFallDamage(float distance) {
        super.causeFallDamage(distance / 2);
    }

    @Override
    public int getMaxHealth() {
        return 16;
    }

    @Override
    public void jump() {
        if (this.passenger != null) {
            this.yd = 1.6;
        } else {
            this.yd = 0.6;
        }
    }

    @Override
    public double getRideHeight() {
        return 0.1;
    }

    public void doTickEffect() {
        if (this.world != null && random.nextInt(2) == 0) {
            ParticleMaker.spawnParticle(world, "splash", this.x, this.y, this.z, world.rand.nextDouble(), world.rand.nextDouble(), world.rand.nextDouble(), 0);
        }
    }

    public Item getBounceParticle() {
        return AetherItems.FOOD_GUMMY_BLUE;
    }

    @Override
    public void tick() {
        if (this.world == null) return;
        this.doTickEffect();

        if (this.passenger != null && (!this.passenger.isAlive() || this.passenger.removed)) {
            this.ejectRider();
        }

        if (this.getHealth() <= 0) {
            this.ejectRider();
        }

        if (this.grabDelay > 0) {
            this.grabDelay--;
        }

        this.ydO = this.yd;

        boolean flag = this.onGround;
        super.tick();
        if (this.onGround && !flag) {
            int i = 2;

            for (int j = 0; j < i * 8; ++j) {
                float f = this.random.nextFloat() * 3.1415927F * 2.0F;
                double f1 = this.random.nextDouble() * 0.5 + 0.5;
                double f2 = (MathHelper.sin(f) * i) * 0.5 * f1;
                double f3 = (MathHelper.cos(f) * i) * 0.5 * f1;
                ParticleMaker.spawnParticle(world, "item", this.x + f2, this.bb.minY, this.z + f3, 0.0, 0.0, 0.0, getBounceParticle().id);
            }

            this.world.playSoundAtEntity(null, this, "mob.slime", this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
        }

        if (!this.world.isClientSide && !this.world.getDifficulty().canHostileMobsSpawn()) {
            this.remove();
        }
    }

    @Override
    public void knockBack(Entity entity, int i, double d, double d1) {
        if (this.passenger == null || entity != this.passenger) {
            super.knockBack(entity, i, d, d1);
        }
    }

    @Override
    public void updateAI() {
        this.tryToDespawn();
        if (this.world == null) return;
        Player entityplayer = this.world.getClosestPlayerToEntity(this, 16.0);

        if (entityplayer instanceof AetherInvisibility) {
            AetherInvisibility invPlayer = (AetherInvisibility) entityplayer;
            if (invPlayer.aether$isInvisible()) {
                entityplayer = this.world.getClosestPlayerToEntity(this, 2.0);
            }
        }

        boolean targetPlayer = entityplayer != null && entityplayer.getGamemode().areMobsHostile() && this.canEntityBeSeen(entityplayer);
        if (!this.friendly && entityplayer != null && targetPlayer && entityplayer != this.passenger) {
            this.lookAt(entityplayer, 10.0F, 20.0F);
        }

        if (this.onGround && this.jumpDelay-- <= 0) {
            if (!targetPlayer) {
                float rotation = (this.world.rand.nextFloat() - 0.5F) * 90.0F;
                this.yRot += rotation;
                this.jumpDelay = this.random.nextInt(80) + 40;
            } else {
                this.jumpDelay = this.random.nextInt(20) + 10;
                this.jumpDelay /= 3;
            }

            this.isJumping = true;
            this.world.playSoundAtEntity(null, this, "mob.slime", this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 0.8F);
            this.world.playSoundAtEntity(null, this, "mob.slimeattack", 0.5F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.moveStrafing = 1.0F - this.random.nextFloat() * 2.0F;
            this.moveForward = 8.0f;
        } else {
            this.isJumping = false;
            if (this.onGround) {
                this.moveStrafing = this.moveForward = 0.0F;
            }
        }
    }

    @Override
    public void attackEntity(@NonNull Entity entity, float distance) {
        if (this.isAlive() && !this.friendly && this.attackTime <= 0 && distance < 2.0F && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY && getHealth() > 0 && !dead) {
            this.attackTime = 200;
            if (this.world != null)
                this.world.playSoundAtEntity(null, this, "mob.slimeattack", 0.5F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            entity.hurt(this, 2, DamageType.COMBAT);
        }
    }

    @Override
    public void playerTouch(Player player) {
        if (this.isAlive() && !this.friendly && this.canEntityBeSeen(player) && (double) this.distanceTo(player) < 2.0F && player.hurt(this, 2, DamageType.COMBAT) && getHealth() > 0 && !dead && player.isAlive() && grabDelay == 0) {
            player.startRiding(this);
            grabDelay = 100;
        }
    }

    @Override
    public String getHurtSound() {
        return "mob.slime";
    }

    @Override
    public String getDeathSound() {
        return "mob.slime";
    }

    @Override
    public float getSoundVolume() {
        return 0.3F;
    }

    @Override
    public boolean canSpawnHere() {
        if (this.world == null) return false;
        int x = MathHelper.floor(this.x);
        int y = MathHelper.floor(this.bb.minY);
        int z = MathHelper.floor(this.z);
        int id = this.world.getBlockId(x, y - 1, z);

        if (this.world.getSavedLightValue(LightLayer.Block, x, y, z) > 7) {
            return false;
        }

        Block<?> block = Blocks.blocksList[id];
        if (block == null) return false;
        if (world.rand.nextInt(5) == 0) return block.hasTag(AetherBlockTags.PASSIVE_MOBS_SPAWN);
        return false;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isFriendly() {
        return friendly;
    }

    public void setFriendly(boolean friendly) {
        this.friendly = friendly;
    }

    public double getYdO() {
        return ydO;
    }
}
