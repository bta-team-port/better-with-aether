package teamport.aether.entity.sentry;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import teamport.aether.blocks.AetherBlocks;

public class MobSentry extends MobMonster implements Enemy {
    public int jumpDelay;
    public int cooldownInactive;
    public boolean activated;
    public MobSentry(World world) {
        super(world);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "sentry");
        this.scoreValue = 100;
        this.setSize(1f, 1f);
        this.activated = false;
        this.cooldownInactive = 0;
        this.attackStrength = 5;
    }

    @Override
    public void tick() {
        boolean flag = this.onGround;
        super.tick();
        if (this.onGround && !flag) {
            this.world.playSoundAtEntity(null, this, "mob.slime", this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f) / 0.8f);
        }
        if (!this.world.isClientSide && !this.world.getDifficulty().canHostileMobsSpawn()) {
            this.remove();
        }
    }

    @Override
    public void updateAI() {
        this.tryToDespawn();
        Player entityplayer = (Player) findPlayerToAttack();
        boolean targetPlayer = entityplayer != null && entityplayer.getGamemode().areMobsHostile();
        if (entityplayer != null && targetPlayer) {
            this.findPlayerToAttack();
        }
        if (this.findPlayerToAttack() != null && !this.canEntityBeSeen(findPlayerToAttack())) {
            this.findPlayerToAttack();
            targetPlayer = false;
            this.activated = false;
        }
        if (cooldownInactive > 0) {
            cooldownInactive--;
        }
        if (targetPlayer) {
            this.faceEntity(findPlayerToAttack(), 10.0f, 20.0f);
            this.activated = true;
            cooldownInactive = 100;
        }
        if (this.onGround && this.jumpDelay-- <= 0 && cooldownInactive > 0) {
            if (!targetPlayer) {
                float rotation = (this.world.rand.nextFloat() - 0.5f) * 90.0f;
                this.yRot += rotation;
            }
            this.jumpDelay = this.random.nextInt(20) + 10;
            if (findPlayerToAttack() != null) {
                this.jumpDelay /= 3;
            }
            this.isJumping = true;
            this.world.playSoundAtEntity(entityplayer, this, "mob.slime", this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f) * 0.8f);
            this.moveStrafing = 1.0f - this.random.nextFloat() * 2.0f;
            this.moveForward = 2;
        } else {
            this.isJumping = false;
            if (this.onGround) {
                this.moveForward = 0.0f;
                this.moveStrafing = 0.0f;
            }
        }
    }

    @Override
    public void playerTouch(Player player) {
        if (findPlayerToAttack() == player && this.canEntityBeSeen(player) && (double)this.distanceTo(player) < 1.5) {
            findPlayerToAttack().hurt(this, this.attackStrength, DamageType.COMBAT);
            this.world.createExplosion(this, this.x, this.y-0.5, this.z, 2f, false, true);
        }
    }

    public String getHurtSound() {
        return "mob.slime";
    }

    public String getDeathSound() {
        return "mob.slime";
    }


    public void dropDeathItems() {
        if (this.random.nextInt(5) == 0) {
            this.dropItem(AetherBlocks.CARVED_STONE_LIGHT.id(), 1);

        } else this.dropItem(AetherBlocks.CARVED_STONE.id(), 1);

        super.dropDeathItems();
    }


    public boolean canSpawnHere() {
        return !this.world.getDifficulty().canHostileMobsSpawn() ? false : super.canSpawnHere();
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
    }

}
