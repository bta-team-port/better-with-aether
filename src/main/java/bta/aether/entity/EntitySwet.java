package bta.aether.entity;

import bta.aether.Aether;
import bta.aether.block.AetherBlocks;
import bta.aether.item.AetherItems;
import com.mojang.nbt.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pathfinder.Path;
import org.lwjgl.Sys;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

public class EntitySwet extends EntityAetherAnimal {
    private final Random rand = new Random();

    private int jumpDelay;
    private int cooldownInactive;
    private int jumpsCounter = 0;
    public boolean isGold;
    private boolean isfriendly;
    private boolean targetPlayer = false;

    public EntitySwet(World world) {
        super(world);
        this.cooldownInactive = 0;
        this.isGold = rand.nextBoolean();
        this.heightOffset = 0.0F;
        this.scoreValue = 100;
        this.setHealthRaw(this.getMaxHealth());
        this.setSize(1f, 1f);

        if (this.isGold) this.speed = 3.0F;
        else this.speed = 1.5f;
    }

    public String getEntityTexture() {
        if (isGold) return "/assets/aether/mobs/swet/swet_blue.png";
        else return "/assets/aether/mobs/swet/swet_gold.png";
    }

    @Override
    public void tick() {
        boolean onGround_last = this.onGround;

        if (this.isUnderLiquid(Material.water)) this.damageEntity(999999999, DamageType.DROWN);
        if (!this.onGround && this.yd < 0.0) this.yd *= 0.9;

        if (this.yd > 0.05F) {
            for (int i = 0; i < 3; ++i) {
                double d = (float) this.x + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.3F;
                double d1 = (float) this.y + this.bbHeight;
                double d2 = (float) this.z + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.3F;
                this.world.spawnParticle("splash", d, d1 - 0.25, d2, 0.0, 0.0, 0.0);
            }
        }

        super.tick();
        if (this.onGround && !onGround_last) this.world.playSoundAtEntity(null, this, "mob.slime", this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f) / 0.8f);
        if (!this.world.isClientSide && this.world.difficultySetting == 0) this.remove();


//        if (this.passenger != null && this.onGround && rand.nextInt(60) == 0) {
//            this.yd += 2.2;
//        }

        if (this.passenger == null) {
            List<Entity> list = this.world.getEntitiesWithinAABB(EntityLiving.class, this.bb.copy().expand(0.5, 0.75, 0.5));
            if (!list.isEmpty()) {
                Entity entity = list.get(0);
                if (!(entity instanceof EntitySwet) && rand.nextBoolean()) {
                    if (!(entity instanceof EntityPlayer)) this.capturePrey(entity);
                    else if (((EntityPlayer) entity).gamemode.areMobsHostile()) this.capturePrey(entity);
                }
            }
        }
    }

    @Override
    public double getRideHeight() {
        return this.bbHeight/4;
    }

    public void capturePrey(Entity entity) {
        this.splorch();
        this.setPos(this.x, this.y, this.z);
        this.setSize(entity.bbWidth + rand.nextFloat(), entity.bbHeight + rand.nextFloat());
        entity.startRiding(this);
        this.roamRandomPath();
    }

    public void dissolve() {
        for (int i = 0; i < 50; ++i) {
            float f = this.rand.nextFloat() * 3.141593F * 2.0F;
            float f1 = this.rand.nextFloat() * 0.5F + 0.25F;
            float f2 = MathHelper.sin(f) * f1;
            float f3 = MathHelper.cos(f) * f1;
            this.world.spawnParticle("splash", this.x + (double) f2, this.bb.minY + 1.25, this.z + (double) f3, (double) f2 * 1.5 + this.xd, 4.0, (double) f3 * 1.5 + this.zd);
        }

        if (this.passenger != null) {
            this.passenger.y += (double) (this.passenger.bbHeight - 0.3F);
            this.passenger.startRiding(this);
        }

        this.remove();
    }

    private Path goSomewhere() {
        final int distance = 8;
        double angleRad = Math.toRadians(world.rand.nextInt(360));

        Path result = null;

        int tries = 60;
        while (tries --> 0) {
            int x, y, z;
            x = (int) (distance * Math.cos(angleRad) + this.x);
            z = (int) (distance * Math.sin(angleRad) + this.z);
            y = world.getHeightValue(x, z);

            if (world.getBlockId(x, y, z) != 0) continue;
            Block block = world.getBlock(x, y - 1, z);
            if (block==null) continue;
            if (!block.blockMaterial.isSolid()) continue;
            if (block.blockMaterial == Material.lava) continue;
            if (block.blockMaterial == Material.water) continue;

            double d3 = x - this.x; double d4 = y - this.y; double d5 = z - this.z;
            if ((d3 * d3 + d4 * d4 + d5 * d5) >= (distance * distance * 1.25F)) continue;
            result = world.getEntityPathToXYZ(this, x, y, z, 10.0F);

            world.setBlockWithNotify(x, y-1, z, Block.blockDiamond.id);
            world.setBlockWithNotify((int) this.x, (int) (this.y -1), (int) this.z, Block.blockGold.id);
            break;
        }

        return result;
    }

    @Override
    protected void updatePlayerActionState() {
        this.tryToDespawn();

        if (this.passenger == null) {
            if (this.entityToAttack != null && this.distanceTo(this.entityToAttack) > 32 ) {
                EntityPlayer entityplayer = this.world.getClosestPlayerToEntity(this, 16.0);
                targetPlayer =
                    entityplayer != null &&
                    entityplayer.getGamemode().areMobsHostile() &&
                    canEntityBeSeen(entityplayer) &&
                    (entityplayer.inventory.armorItemInSlot(5) == null || entityplayer.inventory.armorItemInSlot(5).itemID != AetherItems.armorCapeSwet.id);

                if (targetPlayer) this.entityToAttack = entityplayer;
            }
        }

        if (this.pathToEntity == null) this.pathToEntity = goSomewhere();

        if (cooldownInactive > 0) cooldownInactive--;
        if (this.pathToEntity != null || (targetPlayer && this.entityToAttack != null)) {
            double zNext = 0;
            double xNext = 0;

            if (targetPlayer) {
                xNext = this.entityToAttack.x;
                zNext = this.entityToAttack.z;
            }

            else {
                if (!this.pathToEntity.isDone()) {
                    Vec3d pos = this.pathToEntity.getPos(this);
                    xNext = pos.xCoord;
                    zNext = pos.yCoord;
                    this.pathToEntity.next();
                }
                else {
                    this.pathToEntity = goSomewhere();
                }
            }

            if (zNext != 0 && xNext != 0) {
                this.yRot = (float) (180 - Math.atan2(this.x - xNext, this.z - zNext) * 180 / Math.PI);
            }

            cooldownInactive = 100;
        }

        if (this.onGround && this.jumpDelay-- <= 0 && cooldownInactive > 0) {
            this.jumpDelay = this.random.nextInt(20) + 10;
            if (this.passenger != null) {
                this.jumpDelay /= 3;
            } else {
                float rotation = (this.world.rand.nextFloat() - 0.5f) * 90.0f;
                this.yRot += rotation;
            }
            this.isJumping = true;
            this.world.playSoundAtEntity(null, this, "mob.slime", this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f) * 0.8f);
            this.moveStrafing = 1.0f - this.random.nextFloat() * 2.0f;
            this.moveForward = 2;
            //if (this.pathToEntity != null) this.moveForward *= ((float) this.pathToEntity.length / 100);

        } else {
            this.isJumping = false;
            if (this.onGround) {
                this.moveForward = 0.0f;
                this.moveStrafing = 0.0f;
            }
        }
    }

    @Override
    protected Entity findPlayerToAttack() {
        return super.findPlayerToAttack();
    }

    @Override
    protected void causeFallDamage(float f) {
        int i = (int)Math.ceil((double)(f - 3.0F));
        if (i > 0) {
            this.hurt(null, i, DamageType.FALL);
            if (this.passenger != null) {
                this.passenger.hurt(this, i, DamageType.FALL);
                this.passenger.hurt(this, (int)(i*0.25F), DamageType.COMBAT);
            }

            int j = this.world.getBlockId((int)(this.x), (int)(this.y - 0.2 - this.heightOffset), (int)(this.z));
            if (j > 0) this.world.playBlockSoundEffect(this, this.x, this.y - (double)this.heightOffset, this.z, Block.blocksList[j], EnumBlockSoundEffectType.ENTITY_LAND);
        }
    }

    @Override
    protected void checkFallDamage(double d, boolean flag) {
        if (!this.isfriendly) super.checkFallDamage(d, flag);
        if (this.jumpsCounter >= 3 && this.getHealth() > 0) this.dissolve();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putBoolean("isGold", this.isGold);
        super.addAdditionalSaveData(tag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        this.isGold = tag.getBoolean("isGold");
        super.readAdditionalSaveData(tag);
    }

    public void splorch() {
        this.world.playSoundAtEntity(null, this, "mob.slimeattack", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
    }

    public void splotch() {
        this.world.playSoundAtEntity(null, this, "mob.slime", 0.5F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
    }

    @Override
    public int getMaxHealth() {
        if (this.isGold) return 25;
        else return 20;
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