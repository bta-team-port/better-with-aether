package bta.aether.entity;

import bta.aether.block.AetherBlocks;
import bta.aether.block.BlockDungeon;
import bta.aether.item.tool.base.ItemToolAetherPickaxe;
import com.mojang.nbt.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.LiquidMaterial;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityBossSlider extends EntityAetherBossBase {
    public final int angerThreshold = 50;
    public final float baseDamage = 10F;
    public final int maxAttackCoolDown = 60;
    public final float baseSpeed = 1.375F;

    public boolean awake = false;
    private final ArrayList<EntityPlayer> creativeAttackersList = new ArrayList<>();

    public int attackCoolDown = 0;
    private boolean midSlam = false;
    private double slamY = 0;

    private boolean allowedToMove = true;
    private float momentumX = 0;
    private float momentumY = 0;
    private float momentumZ = 0;

    public int pedestalX;
    public int pedestalY;
    public int pedestalZ;

    public EntityBossSlider(World world) {
        super(world, 500, "aether.slider.name");
        this.setSize(2f,2f);
        this.health = 500;
        this.scoreValue = 10000;
        this.viewScale = 2f;
    }

    @Override
    protected void roamRandomPath() {
    }

    @Override
    public boolean collidesWith(Entity entity) {
        if (Math.abs(this.momentumZ) > 0.05F || Math.abs(this.momentumX) > 0.05F || Math.abs(this.momentumY) > 0.05F) {
            entity.hurt(this, (int) (baseDamage * getAngerModifier()), DamageType.FALL);
            entity.hurt(this, (int) ((baseDamage * .50F) * getAngerModifier()), DamageType.GENERIC);
            if (entity instanceof EntityPlayer && ((EntityPlayer) entity).gamemode.isPlayerInvulnerable()) {
                return super.collidesWith(entity);
            }
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
        if (Arrays.stream(new Integer[] {this.pedestalX, this.pedestalY, this.pedestalZ}).noneMatch(integer -> integer == 0)) {
            this.x = this.pedestalX;
            this.y = this.pedestalY;
            this.z = this.pedestalZ;
        }

        this.allowedToMove = true;
        this.attackCoolDown = 0;
        this.health = maxHealth;
    }

    @Override
    public void tick() {
        super.baseTick();

        int blocksBroken = 0;
        if (Math.abs(momentumX) > 1.0F || Math.abs(momentumZ) > 1.0F) {
            for (int x = -2; x <= 1; x++) {
                for (int z = -2; z <= 1; z++) {
                    for (int y = -1; y <= 2; y++) {
                        if (doBlockSmash(world, (int) (this.x + x), (int) (this.y + y), (int) (this.z + z))) {
                            this.momentumX *= 0.85F;
                            this.momentumY *= 0.85F;
                            this.momentumZ *= 0.85F;

                            blocksBroken++;
                            if (blocksBroken >= 9) {
                                if (this.momentumY <= 0) this.momentumY = 0.4125F;
                                this.allowedToMove = false;
                                this.attackCoolDown = maxAttackCoolDown;
                                return;
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

            if (this.slamY == this.y && attackCoolDown <= 0) {
                int slamRadius = 5;
                float launchSpeed = 0.75F;
                List<Entity> list = world.getEntitiesWithinAABB(Entity.class, AABB.getBoundingBox(this.x - slamRadius, this.y, this.z  - slamRadius, this.x + slamRadius, this.y + slamRadius, this.z + slamRadius));

                for (Entity entity : list) {
                    entity.hurt(this, (int) ((baseDamage * 0.50F) * getAngerModifier()), DamageType.FALL);
                    entity.hurt(this, (int) ((baseDamage * 0.75F) * getAngerModifier()), DamageType.GENERIC);

                    switch (calculateDirection(entity)) {
                        case NORTH:
                            entity.push(0, launchSpeed /2, -launchSpeed);
                            break;

                        case SOUTH:
                            entity.push(0, launchSpeed /2, launchSpeed);
                            break;

                        case EAST:
                            entity.push(launchSpeed, launchSpeed /2, 0);
                            break;

                        case WEST:
                            entity.push(-launchSpeed, launchSpeed /2, 0);
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

                if (this.momentumY <= 0) this.momentumY = 0.4125F;
                this.midSlam = false;
                this.awake = true;
                this.attackCoolDown = maxAttackCoolDown;
            }

            this.slamY = this.y;
            attackCoolDown--;
            return;
        }

        if (awake) {
            if (this.world.players.stream().noneMatch(entityPlayer -> distanceToSqr(entityPlayer) < 7225)) {
                this.awake = false;
                returnToPedestal();
                return;
            }

            EntityPlayer target = (EntityPlayer) findPlayerToAttack();
            if (target == null && !this.creativeAttackersList.isEmpty()) {
                target = creativeAttackersList.get(0);
                for (EntityPlayer entityPlayer : this.creativeAttackersList) {
                    if (this.distanceToSqr(entityPlayer) < this.distanceToSqr(target)) target = entityPlayer;
                }

                if (this.distanceToSqr(target) > 7225) target = null;
            }

            if (allowedToMove && target != null && (Math.abs(this.momentumX) <= 0.05F && Math.abs(this.momentumY) <= 0.05F && Math.abs(this.momentumZ) <= 0.05F)) {
                this.speed = this.baseSpeed * getSpeedModifier(target);
                this.attackCoolDown = this.maxAttackCoolDown * this.health/this.maxHealth;

                if (this.distanceToSqr(target) <= 25 && health < (maxHealth * 0.50F) && !midSlam && random.nextInt(6) == 0) {
                    this.midSlam = true;
                    this.awake = false;
                    this.attackCoolDown = (int) (this.maxAttackCoolDown * 0.50F);
                    this.momentumY += this.baseSpeed * getAngerModifier();
                    return;
                }

                Direction direction = calculateDirection(target);
                 switch (direction){
                    case UP:
                        this.allowedToMove = false;
                        this.momentumY += baseSpeed * getSpeedModifier(target);
                        break;

                    case DOWN:
                        this.allowedToMove = false;
                        this.momentumY -= baseSpeed * getSpeedModifier(target);
                        break;

                    case NORTH:
                        this.allowedToMove = false;
                        this.momentumZ -= speed;
                        break;

                    case SOUTH:
                        this.allowedToMove = false;
                        this.momentumZ += speed;
                        break;

                    case EAST:
                        this.allowedToMove = false;
                        this.momentumX += speed;
                        break;

                    case WEST:
                        this.allowedToMove = false;
                        this.momentumX -= speed;
                        break;
                }
            }

            this.momentumX *= 0.75F;
            this.momentumY *= 0.75F;
            this.momentumZ *= 0.75F;
            move(this.momentumX, this.momentumY, this.momentumZ);

            this.attackCoolDown--;
            if (attackCoolDown <= 0) allowedToMove = true;
        }
    }

    public float getSpeedModifier(Entity target){
        double distance = this.distanceTo(target);
        if (distance > 3) {
            return getAngerModifier();
        }

        return (float) distance / 3;
    }

    public float getAngerModifier() {
        return 1.0F + ( (float) (this.maxHealth - this.health) / this.maxHealth );
    }

    public boolean isAngry() {
        return (this.health * 100) / this.maxHealth < angerThreshold;
    }

    public Direction calculateDirection(Entity entity) {
        double deltaX =  this.x - entity.x;
        double deltaY =  this.y - entity.y;
        double deltaZ =  this.z - entity.z;

        if (Math.abs(deltaY) >= 1.65) {
            if (deltaY < 0) {
                return Direction.UP;
            } else {
                return Direction.DOWN;
            }
        }

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

    public boolean doBlockSmash(World world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        if (block != null && !(block instanceof BlockDungeon) && !(block.blockMaterial instanceof LiquidMaterial)) {
            block.dropBlockWithCause(world, EnumDropCause.EXPLOSION, x, y, z, world.getBlockMetadata(x, y,z), world.getBlockTileEntity(x, y, z));
            doExplosionEffect(world, x, y, z);
            world.setBlockWithNotify(x, y, z, 0);

            return true;
        }

        return false;
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

        if (attacker instanceof EntityPlayer) {
            ItemStack item = ((EntityPlayer)attacker).inventory.mainInventory[((EntityPlayer)attacker).inventory.currentItem];

            if (item != null && item.getItem() instanceof ItemToolPickaxe) {
                awake = true;
                if (!((EntityPlayer)attacker).gamemode.areMobsHostile()) creativeAttackersList.add((EntityPlayer) attacker);
                return super.hurt(attacker, (int) item.getStrVsBlock(AetherBlocks.holystone), type);
            }

            if (item != null && item.getItem() instanceof ItemToolAetherPickaxe) {
                awake = true;
                if (!((EntityPlayer)attacker).gamemode.areMobsHostile()) creativeAttackersList.add((EntityPlayer) attacker);
                return super.hurt(attacker, (int) item.getStrVsBlock(AetherBlocks.holystone), type);
            }

            if (!this.awake) {
                String message = "<"+((EntityPlayer)attacker).getDisplayName()+"> "+ I18n.getInstance().translateKey("aether.slider.hit.fail");
                ((EntityPlayer)attacker).addChatMessage(message);
            }
        }

        return false;
    }

    @Override
    public void onEntityDeath() {
        world.setBlockWithNotify(this.pedestalX, this.pedestalY, this.pedestalZ, 0);
        world.setBlockWithNotify(this.pedestalX, this.pedestalY, this.pedestalZ + 1, 0);
        world.setBlockWithNotify(this.pedestalX + 1, this.pedestalY, this.pedestalZ, 0);
        world.setBlockWithNotify(this.pedestalX + 1, this.pedestalY, this.pedestalZ + 1, 0);

        super.onEntityDeath();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putBoolean("awake", this.awake);
        super.addAdditionalSaveData(tag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        this.awake = tag.getBoolean("awake");
        super.readAdditionalSaveData(tag);
    }

    @Override
    protected void dropFewItems() {
        int lootAmount = this.random.nextInt(4);

        for(int loot = 0; loot < lootAmount; ++loot) {
            int id = AetherBlocks.stoneCarved.id;
            if (world.rand.nextInt(2) == 0) {
                id = AetherBlocks.stoneCarvedLight.id;
            }
            this.spawnAtLocation(id, 1);
        }
    }

}
