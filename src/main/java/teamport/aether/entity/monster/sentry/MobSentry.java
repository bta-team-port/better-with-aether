package teamport.aether.entity.monster.sentry;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolPickaxe;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.entity.AetherDeathMessage;
import teamport.aether.entity.AetherMobImmuneToSpikes;
import teamport.aether.entity.monster.MobMonsterAether;
import teamport.aether.items.itemtool.ItemToolPickaxeAether;

public class MobSentry extends MobMonsterAether implements Enemy, AetherDeathMessage, AetherMobImmuneToSpikes {
    public int jumpDelay;
    public int cooldownInactive;
    public boolean activated;

    public MobSentry(World world) {
        super(world);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "sentry");
        this.scoreValue = 200;
        this.setSize(1f, 1f);
        this.activated = false;
        this.cooldownInactive = 0;
        this.canBreatheUnderwater();
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public int getMaxHealth() {
        return 10;
    }

    public boolean hurt(Entity attacker, int damage, DamageType type) {

        if (attacker instanceof Player) {
            ItemStack item = ((Player) attacker).inventory.getCurrentItem();

            if (item != null && (item.getItem() instanceof ItemToolPickaxe || item.getItem() instanceof ItemToolPickaxeAether)) {
                return super.hurt(attacker, damage * 2, type);
            }
        }
        return super.hurt(attacker, damage, type);
    }

    @Override
    public void tick() {
        boolean flag = this.onGround;
        super.tick();
        if (this.onGround && !flag) {
            this.world.playSoundAtEntity(null, this, "step.stone", this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f) / 0.8f);
        }
        if (!this.world.isClientSide && !this.world.getDifficulty().canHostileMobsSpawn()) {
            this.remove();
        }
    }

    public void updateAI() {
        this.tryToDespawn();
        Player entityplayer = this.world.getClosestPlayerToEntity(this, 16.0);
        boolean targetPlayer = entityplayer != null && entityplayer.getGamemode().areMobsHostile() && canEntityBeSeen(entityplayer);
        if (entityplayer != null && targetPlayer) {
            this.target = entityplayer;
        }
        if (this.target != null && !this.canEntityBeSeen(target)) {
            this.target = null;
            targetPlayer = false;
            this.activated = false;
        }
        if (cooldownInactive > 0) {
            cooldownInactive--;
        }

        if (targetPlayer) {
            target = entityplayer;
            lookAt(entityplayer, 10.0f, 20.0f);
            activated = true;
            cooldownInactive = 100;
        } else {
            activated = false;
        }

        if (this.onGround && this.jumpDelay-- <= 0 && cooldownInactive > 0) {
            this.jumpDelay = this.random.nextInt(20) + 10;
            if (targetPlayer) {
                this.jumpDelay /= 3;
            } else {
                float rotation = (this.world.rand.nextFloat() - 0.5f) * 90.0f;
                this.yRot += rotation;
            }
            this.isJumping = true;
            this.world.playSoundAtEntity(null, this, "step.stone", this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f) * 0.8f);
            this.moveStrafing = 1.0f - this.random.nextFloat() * 2.0f;
            this.moveForward = 2;

        } else {
            this.isJumping = false;
            if (this.onGround) {
                this.moveStrafing = this.moveForward = 0.0F;
            }
        }

    }


    @Override
    public void playerTouch(Player player) {
        if (findPlayerToAttack() == player && this.canEntityBeSeen(player) && (double) this.distanceTo(player) < 1.5) {
            findPlayerToAttack().hurt(this, this.attackStrength, DamageType.COMBAT);
            this.world.createExplosion(this, this.x, this.y - 0.5, this.z, 1f, false, true);
        }
    }

    public String getHurtSound() {
        return "step.stone";
    }

    public String getDeathSound() {
        return "step.stone";
    }


    public void dropDeathItems() {
        if (this.random.nextInt(5) == 0) {
            this.dropItem(AetherBlocks.CARVED_STONE_LIGHT.id(), 1);

        } else this.dropItem(AetherBlocks.CARVED_STONE.id(), 1);

        super.dropDeathItems();
    }


    public boolean canSpawnHere() {
        return this.world.getDifficulty().canHostileMobsSpawn();
    }

    @Override
    public void addAdditionalSaveData(@NonNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
    }

    @Override
    public void readAdditionalSaveData(@NonNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
    }

}
