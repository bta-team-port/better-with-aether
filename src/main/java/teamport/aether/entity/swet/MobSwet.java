package teamport.aether.entity.swet;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import teamport.aether.items.AetherItems;

public class MobSwet extends Mob implements Enemy {
    public static final int DATA_SLIME_SIZE = 16;
    public float squish;
    public float oSquish;
    public int jumpDelay = 0;
    public boolean sizeSet = false;

    public MobSwet(World world) {
        super(world);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "swet");
        this.heightOffset = 0.0F;
        this.jumpDelay = 20;
        this.scoreValue = 100;
        this.mobDrops.add(new WeightedRandomLootObject(AetherItems.FOOD_GUMMY_BLUE.getDefaultStack(), 0, 2));
    }

    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(16, (byte)1, Byte.class);
    }

    public void spawnInit() {
        super.defineSynchedData();
        this.setSlimeSize(4);
        }

    public void setSlimeSize(int i) {
        this.setSize(0.5F * (float)i, 0.5F * (float)i);
        this.setHealthRaw(this.getMaxHealth());
        this.setPos(this.x, this.y, this.z);
    }

    public int getMaxHealth() {
        return this.getSlimeSize() * this.getSlimeSize();
    }

    public int getSlimeSize() {
        return this.entityData.getByte(16);
    }

    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Size", this.getSlimeSize() - 1);
    }

    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        this.setSlimeSize(tag.getInteger("Size") + 1);
        this.sizeSet = true;
        super.readAdditionalSaveData(tag);
    }

    public void tick() {
        if (!this.sizeSet) {
            this.setSlimeSize(this.getSlimeSize());
            this.sizeSet = true;
        }

        this.oSquish = this.squish;
        boolean flag = this.onGround;
        super.tick();
        if (this.onGround && !flag) {
            int i = this.getSlimeSize();

            for(int j = 0; j < i * 8; ++j) {
                float f = this.random.nextFloat() * 3.1415927F * 2.0F;
                double f1 = (double)this.random.nextFloat() * 0.5 + 0.5;
                double f2 = (double)(MathHelper.sin(f) * (float)i) * 0.5 * f1;
                double f3 = (double)(MathHelper.cos(f) * (float)i) * 0.5 * f1;
                this.world.spawnParticle("item", this.x + f2, this.bb.minY, this.z + f3, 0.0, 0.0, 0.0, AetherItems.FOOD_GUMMY_BLUE.id);
            }

            if (i > 2) {
                this.world.playSoundAtEntity(null, this, "mob.slime", this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
            }

            this.squish = -0.5F;
        }

        if (!this.world.isClientSide && !this.world.getDifficulty().canHostileMobsSpawn()) {
            this.remove();
        }

        this.squish *= 0.6F;
    }

    public void updateAI() {
        this.tryToDespawn();
        Player entityplayer = this.world.getClosestPlayerToEntity(this, 16.0);
        boolean targetPlayer = entityplayer != null && entityplayer.getGamemode().areMobsHostile();
        if (entityplayer != null && targetPlayer) {
            this.lookAt(entityplayer, 10.0F, 20.0F);
        }

        if (this.onGround && this.jumpDelay-- <= 0) {
            if (!targetPlayer) {
                float rotation = (this.world.rand.nextFloat() - 0.5F) * 90.0F;
                this.yRot += rotation;
            }

            this.jumpDelay = this.random.nextInt(20) + 10;
            if (entityplayer != null) {
                this.jumpDelay /= 6;
            }

            this.isJumping = true;
            if (this.getSlimeSize() > 1) {
                this.world.playSoundAtEntity(null, this, "mob.slime", this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 0.8F);
            }

            this.squish = 1.0F;
            this.moveStrafing = 1.0F - this.random.nextFloat() * 2.0F;
            this.moveForward = (float)this.getSlimeSize();
        } else {
            this.isJumping = false;
            if (this.onGround) {
                this.moveStrafing = this.moveForward = 0.0F;
            }
        }

    }

    public void playerTouch(Player player) {
        int i = this.getSlimeSize();
        if (i > 1 && this.canEntityBeSeen(player) && (double)this.distanceTo(player) < 0.6 * (double)i && player.hurt(this, i, DamageType.COMBAT)) {
            this.world.playSoundAtEntity(null, this, "mob.slimeattack", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            player.startRiding(this);
        }

    }

    public String getHurtSound() {
        return "mob.slime";
    }

    public String getDeathSound() {
        return "mob.slime";
    }

    public float getSoundVolume() {
        return 0.6F;
    }
}

