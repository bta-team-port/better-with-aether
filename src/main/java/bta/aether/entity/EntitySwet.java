package bta.aether.entity;

import bta.aether.block.AetherBlocks;
import bta.aether.item.AetherItems;
import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;

public class EntitySwet extends EntityMonster {
    private int jumpDelay;
    private int cooldownInactive;
    public boolean activated;

    public EntitySwet(World world) {
        super(world);
        this.attackStrength = 5;
        this.cooldownInactive = 0;
        this.activated = false;
        this.heightOffset = 0.0F;
        this.scoreValue = 100;
        this.setSize(1f, 1f);
    }

    public String getEntityTexture() {
        return "/assets/aether/mobs/swets.png";
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
        boolean targetPlayer = entityplayer != null &&
                entityplayer.getGamemode().areMobsHostile() &&
                canEntityBeSeen(entityplayer) &&
                (entityplayer.inventory.armorItemInSlot(5) == null || entityplayer.inventory.armorItemInSlot(5).itemID != AetherItems.armorCapeSwet.id);

        if (cooldownInactive > 0) {
            cooldownInactive--;
        }

        if (targetPlayer) {
            entityToAttack = entityplayer;
            faceEntity(entityToAttack, 10.0f, 20.0f);
            activated = true;
            cooldownInactive = 100;
        } else {
            entityToAttack = null;
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

    public String getDefaultEntityTexture() {
        return "/assets/aether/mobs/swets.png";
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
        return AetherBlocks.aercloudBlue.id;
    }

    protected float getSoundVolume() {
        return 0.6F;
    }

    @Override
    public boolean getCanSpawnHere() {
        if (this.world.difficultySetting == 0) {
            return false;
        }
        return super.getCanSpawnHere();
    }
}
