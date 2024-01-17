package bta.aether.entity;

import bta.aether.block.AetherBlocks;
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
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;

import java.util.List;

public class EntityBossSlider extends EntityAetherBossBase{

    public boolean awake = false;
    public int angerThreshold = 50;
    public float baseDamage = 10F;
    public int attackCoolDown = 0;

    private boolean moving = false;

    private boolean midSlam = false;
    private double slamY = 0;

    public int maxAttackCoolDown = 45;
    public float baseSpeed = 1.5F;

    private float momentumX = 0;
    private float momentumY = 0;
    private float momentumZ = 0;

    public int pedestalX = (int) this.x;
    public int pedestalY = (int) this.y;
    public int pedestalZ = (int) this.z;


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
            entity.hurt(this, (int) ((baseDamage * .50F) * getAngerModifier()), DamageType.GENERIC);
            doExplosionEffect(entity.world, entity.x, entity.y, entity.z);
        }

        return super.collidesWith(entity);
    }

    public String getEntityTexture() {
        if (this.awake) {
            if (isAngry()) return "/assets/aether/mobs/sliderAwake_red.png";
            return "/assets/aether/mobs/sliderAwake.png";
        }

        if (isAngry()) return "/assets/aether/mobs/sliderSleep_red.png";
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

    public void returnToPedestal() {
        this.x = pedestalX;
        this.y = pedestalY;
        this.z = pedestalZ;

        this.moving = false;
        this.attackCoolDown = 0;
        this.health = maxHealth;
    }

    @Override
    public void tick() {
        super.baseTick();

        boolean flag = false;
        int blocksBroken = 0;
        if (Math.abs(momentumX) > 1.0F || Math.abs(momentumZ) > 1.0F) {
            for (int x = -2; x <= 3; x++) {
                if (flag) break;
                for (int z = -2; z <= 3; z++) {
                    if (flag) break;
                    for (int y = -1; y <= 3; y++) {
                        if (world.getBlockId((int) (this.x + x), (int) (this.y + y), (int) (this.z + z)) != 0) {

                            doBlockSmash(world, (int) (this.x + x), (int) (this.y + y), (int) (this.z + z));
                            this.momentumX *= 0.65F;
                            this.momentumZ *= 0.65F;

                            blocksBroken++;
                            if (blocksBroken >= 5){
                                flag = true;
                                break;
                            }
                        }
                    }
                }
            }
        }

        if (midSlam) {

            if (attackCoolDown <= 0) {
                this.momentumY -= this.baseSpeed;
            }

            this.momentumX *= 0.75F;
            this.momentumY *= 0.75F;
            this.momentumZ *= 0.75F;
            move(this.momentumX, this.momentumY, this.momentumZ);

            if (this.slamY == this.y) {
                int slamRadius = 4;
                float launchSpeed = 0.75F;
                List<Entity> list = world.getEntitiesWithinAABB(Entity.class, AABB.getBoundingBox(this.x - slamRadius, this.y, this.z  - slamRadius, this.x + slamRadius, this.y + slamRadius, this.z + slamRadius));

                for (Entity entity : list) {
                    entity.hurt(this, (int) ((baseDamage * 0.50F) * getAngerModifier()), DamageType.FALL);
                    entity.hurt(this, (int) ((baseDamage * 0.75F) * getAngerModifier()), DamageType.GENERIC);

                    switch (calculateDirection((int) entity.x, (int) entity.z)) {
                        case NORTH:
                            entity.push(0, launchSpeed /2, -launchSpeed);
                            break;

                        case SOUTH:
                            entity.push(0, launchSpeed /2, launchSpeed);
                            break;

                        case EAST:
                            entity.push(launchSpeed /2, launchSpeed, 0);
                            break;

                        case WEST:
                            entity.push(-launchSpeed /2, launchSpeed, 0);
                            break;
                    }

                    doExplosionEffect(entity.world, entity.x, entity.y, entity.z);
                }

                for (int particle = 0; particle < 16; particle++) {
                    double explosionX = this.x - slamRadius + world.rand.nextInt(slamRadius * 2);
                    double explosionY = this.y - slamRadius + world.rand.nextInt(slamRadius * 2);
                    double explosionZ = this.z - slamRadius + world.rand.nextInt(slamRadius * 2);
                    doExplosionEffect(world, explosionX, explosionY, explosionZ);
                }

                this.midSlam = false;
                this.attackCoolDown = maxAttackCoolDown;
            }

            this.slamY = this.y;
            attackCoolDown--;
            return;
        }

        if (awake) {
            if (this.world.loadedEntityList.stream().noneMatch(entity -> entity instanceof EntityPlayer && getDistanceFrom(this.x, this.y, this.z, entity.x, entity.y, entity.z) < 100)) {
                this.awake = false;
                returnToPedestal();
                return;
            }

            EntityPlayer target = (EntityPlayer) findPlayerToAttack();
            if (target != null && !moving && (Math.abs(this.momentumZ) <= 0.05F && Math.abs(this.momentumX) <= 0.05F)) {
                this.speed = this.baseSpeed * getSpeedModifier(target);
                this.attackCoolDown = this.maxAttackCoolDown * this.health/this.maxHealth;

                if (getDistanceFrom(this.x, this.y, this.z, target.x, target.y, target.z) <= 5 && health < (maxHealth * 0.50F) && !midSlam && random.nextBoolean()) {
                    this.midSlam = true;
                    this.attackCoolDown = this.maxAttackCoolDown;
                    this.momentumY += this.baseSpeed * getAngerModifier();
                    return;
                }

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
            this.momentumX *= 0.75F;
            this.momentumY *= 0.75F;
            this.momentumZ *= 0.75F;
            move(this.momentumX, this.momentumY, this.momentumZ);

            this.attackCoolDown--;
            if (attackCoolDown <= 0) moving = false;
        }
    }

    public float getSpeedModifier(Entity target){
        int distance = (int) getDistanceFrom(this.x, this.y, this.z, target.x, target.y, target.z);
        if (distance > 3) {
            return getAngerModifier();
        }
        return (float) distance / 5;
    }

    public float getAngerModifier() {
        return 1.0F + ( (float) (this.maxHealth - this.health) / this.maxHealth );
    }

    public boolean isAngry() {
        return (this.health * 100) / this.maxHealth < angerThreshold;
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
            world.setBlockWithNotify(x, y, z, 0);
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
    public void onEntityDeath() {
        world.setBlockWithNotify(pedestalX, pedestalY, pedestalZ, 0);
        world.setBlockWithNotify(pedestalX, pedestalY, pedestalZ + 1, 0);
        world.setBlockWithNotify(pedestalX + 1, pedestalY, pedestalZ, 0);
        world.setBlockWithNotify(pedestalX + 1, pedestalY, pedestalZ + 1, 0);

        super.onEntityDeath();
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

    boolean doSlam = false;
    @Override
    public boolean interact(EntityPlayer entityplayer) {
        doSlam = !doSlam;
        return true;
    }

    private double getDistanceFrom(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Math.abs(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(z2 - z1, 2)));
    }

    @Override
    protected void dropFewItems() {
        int lootAmount = this.random.nextInt(10);
        for(int loot = 0; loot < lootAmount; ++loot) {
            int id = AetherBlocks.stoneCarved.id;
            if (world.rand.nextInt(2) == 0) {
                id = AetherBlocks.stoneCarvedLight.id;
            }
            this.spawnAtLocation(id, 1);
        }
    }

}
