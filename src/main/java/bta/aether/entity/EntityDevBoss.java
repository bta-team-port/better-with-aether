package bta.aether.entity;

import bta.aether.block.AetherBlocks;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;

public class EntityDevBoss extends EntityAetherBossBase {
    private int jumpDelay;
    private int cooldownInactive;
    public boolean activated;

    public EntityDevBoss(World world) {
        super(world, 100);
        this.attackStrength = 5;
        this.cooldownInactive = 0;
        this.activated = false;
        this.health = maxHealth;
    }

    public String getEntityTexture() {
        if (activated) {
            return "/assets/aether/mobs/SentryLit.png";
        }
        return "/assets/aether/mobs/Sentry.png";
    }

    public String getDefaultEntityTexture() {
        return "/assets/aether/mobs/Sentry.png";
    }

    @Override
    public void tick() {
        boolean flag = this.onGround;
        super.tick();
        if (this.onGround && !flag) {
            this.world.playSoundAtEntity(this, "mob.slime", this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f) / 0.8f);
        }
        if (!this.world.isClientSide && this.world.difficultySetting == 0) {
            this.remove();
        }
    }

    @Override
    protected void updatePlayerActionState() {
        this.tryToDespawn();
        EntityPlayer entityplayer = this.world.getClosestPlayerToEntity(this, 16.0);
        boolean targetPlayer = entityplayer != null && entityplayer.getGamemode().areMobsHostile();
        if (entityplayer != null && targetPlayer) {
            this.entityToAttack = entityplayer;
        }
        if (this.entityToAttack != null && !this.canEntityBeSeen(entityToAttack)) {
            this.entityToAttack = null;
            targetPlayer = false;
            this.activated = false;
        }
        if (cooldownInactive > 0) {
            cooldownInactive--;
        }
        if (targetPlayer) {
            this.faceEntity(entityToAttack, 10.0f, 20.0f);
            this.activated = true;
            cooldownInactive = 100;
        }
        if (this.onGround && this.jumpDelay-- <= 0 && cooldownInactive > 0) {
            if (!targetPlayer) {
                float rotation = (this.world.rand.nextFloat() - 0.5f) * 90.0f;
                this.yRot += rotation;
            }
            this.jumpDelay = this.random.nextInt(20) + 10;
            if (entityToAttack != null) {
                this.jumpDelay /= 3;
            }
            this.isJumping = true;
            this.world.playSoundAtEntity(this, "mob.slime", this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f) * 0.8f);
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
    public void playerTouch(EntityPlayer player) {
        if (entityToAttack == player && this.canEntityBeSeen(player) && (double)this.distanceTo(player) < 1.5) {
            entityToAttack.hurt(this, this.attackStrength, DamageType.COMBAT);
            this.world.newExplosion(this, this.x, this.y-0.5, this.z, 2f, false, true);
            this.health = 0;
        }
    }

    @Override
    protected String getHurtSound() {
        return "mob.slime";
    }

    @Override
    protected String getDeathSound() {
        return "mob.slime";
    }

    @Override
    protected int getDropItemId() {
        return this.random.nextInt(5) == 0 ? AetherBlocks.stoneCarvedLight.id : AetherBlocks.stoneCarved.id;
    }

    @Override
    public boolean getCanSpawnHere() {
        if (this.world.difficultySetting == 0) {
            return false;
        }
        return super.getCanSpawnHere();
    }
}
