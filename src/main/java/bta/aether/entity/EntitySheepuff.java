package bta.aether.entity;

import bta.aether.block.AetherBlocks;
import com.mojang.nbt.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;

import java.util.Random;

public class EntitySheepuff extends EntityAetherAnimal {
    public static final float[][] fleeceColorTable = new float[][]{{1.0F, 1.0F, 1.0F}, {0.95F, 0.7F, 0.2F}, {0.9F, 0.5F, 0.85F}, {0.6F, 0.7F, 0.95F}, {0.9F, 0.9F, 0.2F}, {0.5F, 0.8F, 0.1F}, {0.95F, 0.7F, 0.8F}, {0.3F, 0.3F, 0.3F}, {0.6F, 0.6F, 0.6F}, {0.3F, 0.6F, 0.7F}, {0.7F, 0.4F, 0.9F}, {0.2F, 0.4F, 0.8F}, {0.5F, 0.4F, 0.3F}, {0.4F, 0.5F, 0.2F}, {0.8F, 0.3F, 0.3F}, {0.1F, 0.1F, 0.1F}};
    private int growthTimer;
    public int timeSheepEating;
    public int prevTimeSheepEating;

    public EntitySheepuff(World world) {
        super(world);
        this.skinName = "sheepuff";
        this.setSize(0.9F, 1.3F);
    }

    protected void init() {
        super.init();
        this.entityData.define(16, (byte)0);
        this.entityData.define(17, (byte)0);
    }

    public void spawnInit() {
        super.init();
        this.setFleeceColor(getRandomFleeceColor(this.random));
    }

    public boolean hurt(Entity attacker, int i, DamageType type) {
        return super.hurt(attacker, i, type);
    }

    protected void dropFewItems() {
        if (!this.getSheared()) {
            this.spawnAtLocation(new ItemStack(Block.wool.id, 1, this.getFleeceColor()), 0.0F);
        }

    }

    protected int getDropItemId() {
        return Block.wool.id;
    }

    public boolean interact(EntityPlayer entityplayer) {
        if (super.interact(entityplayer)) {
            return true;
        } else {
            ItemStack itemstack = entityplayer.inventory.getCurrentItem();
            if (itemstack != null && (itemstack.itemID == Item.toolShears.id || itemstack.itemID == Item.toolShearsSteel.id) && !this.getSheared()) {
                if (!this.world.isClientSide) {
                    this.setSheared(true);
                    int count = 2 + this.random.nextInt(3);

                    for(int j = 0; j < count; ++j) {
                        EntityItem entityitem = this.spawnAtLocation(new ItemStack(Block.wool.id, 1, this.getFleeceColor()), 1.0F);
                        entityitem.yd += (double)(this.random.nextFloat() * 0.05F);
                        entityitem.xd += (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.1F);
                        entityitem.zd += (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.1F);
                    }
                }

                itemstack.damageItem(1, entityplayer);
                return true;
            } else {
                return false;
            }
        }
    }

    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Sheared", this.getSheared());
        tag.putByte("Color", (byte)this.getFleeceColor());
        tag.putShort("GrowthTimer", (short)this.growthTimer);
        tag.putBoolean("Puffed", this.getPuffed());

    }

    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setSheared(tag.getBoolean("Sheared"));
        this.setFleeceColor(tag.getByte("Color"));
        this.setGrowthTimer(tag.getShort("GrowthTimer"));
        this.setPuffed(tag.getBoolean("Puffed"));
    }

    protected boolean isMovementBlocked() {
        return super.isMovementBlocked() || this.getIsSheepEating();
    }

    public boolean getIsSheepEating() {
        return this.entityData.getByte(17) != 0;
    }

    protected void setIsSheepEating(boolean value) {
        this.entityData.set(17, (byte)(value ? 1 : 0));
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.getSheared()) {
            ++this.growthTimer;
        }

        int blockX;
        int blockY;
        int blockZ;
        Block blockBelow;
        if (this.growthTimer > 400) {
            blockX = MathHelper.floor_double(this.x);
            blockY = MathHelper.floor_double(this.y);
            blockZ = MathHelper.floor_double(this.z);
            blockBelow = this.world.getBlock(blockX, blockY - 1, blockZ);
            this.growthTimer = 0;
            if ((blockBelow == Block.grass || blockBelow == Block.grassRetro) && !this.world.isClientSide) {
                this.setIsSheepEating(true);
            }

            this.timeSheepEating = 0;
            this.prevTimeSheepEating = 0;
        }

        if (this.getIsSheepEating()) {
            blockX = MathHelper.floor_double(this.x);
            blockY = MathHelper.floor_double(this.y);
            blockZ = MathHelper.floor_double(this.z);
            blockBelow = this.world.getBlock(blockX, blockY - 1, blockZ);
            if (this.timeSheepEating >= 5 && this.timeSheepEating <= 35 && this.timeSheepEating % 5 == 0 && !this.world.isClientSide) {
                this.world.playBlockSoundEffect((Entity)null, this.x + 0.5, this.y + 0.5, this.z + 0.5, Block.grass, EnumBlockSoundEffectType.DIG);
            }

            this.prevTimeSheepEating = this.timeSheepEating++;
            if (this.prevTimeSheepEating == 35 && (blockBelow == Block.grass || blockBelow == Block.grassRetro) && !this.world.isClientSide) {
                this.world.playSoundEffect((EntityPlayer)null, 2001, (int)this.x, (int)this.y - 1, (int)this.z, this.world.getBlockId((int)this.x, (int)this.y - 1, (int)this.z));
                this.world.setBlockWithNotify(blockX, blockY - 1, blockZ, Block.dirt.id);
                this.setSheared(false);
            }

            if (this.prevTimeSheepEating >= 40) {
                this.prevTimeSheepEating = 0;
                this.timeSheepEating = 0;
                if (!this.world.isClientSide) {
                    this.setIsSheepEating(false);
                }
            }
        }

    }

    public String getLivingSound() {
        return "mob.sheep";
    }

    protected String getHurtSound() {
        return "mob.sheep";
    }

    protected String getDeathSound() {
        return "mob.sheep";
    }

    public int getFleeceColor() {
        return this.entityData.getByte(16) & 15;
    }

    public void setFleeceColor(int i) {
        byte byte0 = this.entityData.getByte(16);
        this.entityData.set(16, (byte)(byte0 & 240 | i & 15));
    }

    public boolean getSheared() {
        return (this.entityData.getByte(16) & 16) != 0;
    }

    public void setSheared(boolean flag) {
        byte byte0 = this.entityData.getByte(16);
        if (flag) {
            this.entityData.set(16, (byte)(byte0 | 16));
        } else {
            this.entityData.set(16, (byte)(byte0 & -17));
        }

    }

    protected void jump() {
        if (this.getPuffed()) {
            this.yd = 1.8;
            this.xd += this.random.nextGaussian() * 0.5;
            this.zd += this.random.nextGaussian() * 0.5;
        } else {
            this.yd = 0.41999998688697815;
        }

    }

    public String getEntityTexture() {
        return "/assets/aether/mobs/sheepuff.png";
    }

    public void tick() {
        super.tick();
        if (this.getPuffed()) {
            this.fallDistance = 0.0F;
            if (this.yd < -0.05) {
                this.yd = -0.05;
            }
        }

        if (this.random.nextInt(100) == 0) {
            int x = MathHelper.floor_double(this.x);
            int y = MathHelper.floor_double(this.y);
            int z = MathHelper.floor_double(this.z);
            if (this.world.getBlockId(x, y - 1, z) == AetherBlocks.grassAether.id) {
                this.world.setBlock(x, y - 1, z, AetherBlocks.dirtAether.id);
                ++this.timeSheepEating;
            }
        }

        if (this.timeSheepEating == 5 && !this.getSheared() && !this.getPuffed()) {
            this.setPuffed(true);
            this.timeSheepEating = 0;
        }

        if (this.timeSheepEating == 10 && this.getSheared() && !this.getPuffed()) {
            this.setSheared(false);
            this.setFleeceColor(0);
            this.timeSheepEating = 0;
        }

    }

    public static int getRandomFleeceColor(Random random) {
        int i = random.nextInt(100);
        if (i < 5) {
            return 15;
        } else if (i < 10) {
            return 7;
        } else if (i < 15) {
            return 8;
        } else if (i < 18) {
            return 12;
        } else {
            return random.nextInt(500) != 0 ? 0 : 6;
        }
    }

    public void setGrowthTimer(int growthTimer) {
        this.growthTimer = growthTimer;
    }

    public boolean getPuffed() {
        return (this.entityData.getByte(16) & 32) != 0;
    }

    public void setPuffed(boolean flag) {
        byte byte0 = this.entityData.getByte(16);
        if (flag) {
            this.entityData.set(16, (byte) (byte0 | 32));
        } else {
            this.entityData.set(16, (byte) (byte0 & -33));
        }

    }


}
