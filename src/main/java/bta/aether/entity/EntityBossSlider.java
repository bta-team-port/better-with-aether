package bta.aether.entity;

import bta.aether.block.BlockDungeon;
import com.mojang.nbt.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolPickaxe;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.sound.SoundType;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;

public class EntityBossSlider extends EntityAetherBossBase{

    public boolean awake = false;
    public int angerThreshold = 50;
    public float baseDamage = 10F;
    public int attackCoolDown = 0;
    private boolean moving = false;
    public int maxAttackCoolDown = 45;
    public float baseSpeed = 1.5F;

    private float momentumX = 0;
    private float momentumZ = 0;

    public EntityBossSlider(World world) {
        super(world, 100, "aether.slider.name");
        this.setSize(2f,2f);
        this.viewScale = 2f;
        this.setSize(2.5F, 2.5F);
    }

    @Override
    protected void roamRandomPath() {
    }

    @Override
    public boolean collidesWith(Entity entity) {
        if (Math.abs(this.momentumZ) > 0F || Math.abs(this.momentumX) > 0F) {
            entity.hurt(this, (int) (baseDamage * getAngerModifier()), DamageType.FALL);
            entity.hurt(this, (int) ((baseDamage * .50F) * getAngerModifier()), DamageType.COMBAT);
            doExplosionEffect(entity.world, entity.x, entity.y, entity.z);
        }

        return super.collidesWith(entity);
    }

    public String getEntityTexture() {
        if (this.awake) {
            if ((this.health * 100) / this.maxHealth < angerThreshold) return "/assets/aether/mobs/sliderAwake_red.png";
            return "/assets/aether/mobs/sliderAwake.png";
        }

        if ((this.health * 100) / this.maxHealth < angerThreshold) return "/assets/aether/mobs/sliderSleep_red.png";
        return "/assets/aether/mobs/sliderSleep.png";
    }

    @Override
    protected String getDeathSound() {
        return "aether.sound.bosses.slider.sliderDeath";
    }

    @Override
    protected String getHurtSound() {
        return "step.stone";
    }

    // TODO: add return to pedestal method.
    public void returnToPedestal(World world, double x, double y, double z) {
    }

    @Override
    public void tick() {
        super.baseTick();

        if (awake) {
            if (this.world.loadedEntityList.stream().noneMatch(entity -> entity instanceof EntityPlayer && getDistanceFrom(this.x, this.y, this.z, entity.x, entity.y, entity.z) < 100)) {
                this.awake = false;
                returnToPedestal(this.world, this.x, this.y, this.z);
                return;
            }

            EntityPlayer target = (EntityPlayer) findPlayerToAttack();
            if (target != null && !moving && (Math.abs(this.momentumZ) <= 0.05F && Math.abs(this.momentumX) <= 0.05F)) {
                this.speed = this.baseSpeed * getSpeedModifier(target);
                this.attackCoolDown = this.maxAttackCoolDown * this.health/this.maxHealth;

                switch (calculateDirection((int) target.x, (int) target.z)){
                    case NORTH:
                        this.moving = true;
                        this.momentumZ -= speed;
                        break;

                    case SOUTH:
                        this.moving = true;
                        this.momentumZ += speed;
                        break;

                    case EAST:
                        this.moving = true;
                        this.momentumX += speed;
                        break;

                    case WEST:
                        this.moving = true;
                        this.momentumX -= speed;
                        break;
                }
            }

            this.momentumZ *= 0.75F;
            this.momentumX *= 0.75F;
            move(momentumX, 0, momentumZ);

            attackCoolDown--;
            if (attackCoolDown <= 0) moving = false;
        }
    }

    public float getSpeedModifier(Entity target){
        int distance = (int) getDistanceFrom(this.x, this.y, this.z, target.x, target.y, target.z);
        if (distance > 3) {
            return getAngerModifier();
        }

        return (float) distance / 10;
    }

    public float getAngerModifier() {
        return 1.0F + ( (float) (this.maxHealth - this.health) / this.maxHealth );
    }

    public Direction calculateDirection(int entityX, int entityZ) {
        int deltaX = (int) (this.x - entityX);
        int deltaZ = (int) (this.z - entityZ);

        if (Math.abs(deltaX) > Math.abs(deltaZ)) {
            if (deltaX < 0) {
                return Direction.EAST;
            } else {
                return Direction.WEST;
            }
        } else {
            if (deltaZ < 0) {
                return Direction.SOUTH;
            } else {
                return Direction.NORTH;
            }
        }
    }

    public void doBlockSmash(World world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        if (block != null && !(block instanceof BlockDungeon)) {
            this.moving = false;
            block.dropBlockWithCause(world, EnumDropCause.EXPLOSION, x, y, z, world.getBlockMetadata(x, y,z), world.getBlockTileEntity(x, y, z));
            doExplosionEffect(world, x, y, z);
        }
    }

    public void doExplosionEffect(World world, double x, double y, double z){
        for (int particle = 0; particle < 16; particle++) {
            double XParticle = x + 0.5F + ((double) world.rand.nextFloat()) - ((double) world.rand.nextFloat() * 0.375F);
            double YParticle = y + 0.5F + ((double) world.rand.nextFloat()) - ((double) world.rand.nextFloat() * 0.375F);
            double ZParticle = z + 0.5F + ((double) world.rand.nextFloat()) - ((double) world.rand.nextFloat() * 0.375F);

            world.spawnParticle("explode", XParticle, YParticle, ZParticle, 0,0,0);
        }

        world.playSoundEffect(SoundType.WORLD_SOUNDS, x, y, z, "random.explode", 1.5F, (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
    }

    @Override
    protected boolean isMovementBlocked() {
        return !this.awake;
    }

    @Override
    public boolean hurt(Entity attacker, int damage, DamageType type) {
        if(this.awake && type == DamageType.BLAST) return super.hurt(attacker, damage/4, type);

        if (attacker instanceof EntityPlayer){
            ItemStack item = ((EntityPlayer)attacker).inventory.mainInventory[((EntityPlayer)attacker).inventory.currentItem];
            if (item != null && item.getItem() instanceof ItemToolPickaxe) {
                awake = true;
                return super.hurt(attacker, damage, type);
            } else if (!this.awake) {
                String message = "<"+((EntityPlayer)attacker).getDisplayName()+"> "+ I18n.getInstance().translateKey("aether.slider.hit_fail");
                ((EntityPlayer)attacker).addChatMessage(message);
            }
        }

        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putBoolean("awake", awake);
        super.addAdditionalSaveData(tag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        this.awake = tag.getBoolean("awake");
        super.readAdditionalSaveData(tag);
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        for (int boom = 0; boom < 16; boom++) doExplosionEffect(this.world, this.x, this.y, this.z);
        this.awake = !this.awake;
        return super.interact(entityplayer);
    }

    private double getDistanceFrom(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Math.abs(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2)));
    }

}
