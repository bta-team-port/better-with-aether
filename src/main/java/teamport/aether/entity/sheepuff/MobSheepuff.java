package teamport.aether.entity.sheepuff;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.animal.MobSheep;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import teamport.aether.blocks.AetherBlocks;

import java.util.Random;

public class MobSheepuff extends MobSheep {
    public int growthTimer;
    public MobSheepuff(World world) {
        super(world);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "sheepuff");
        this.setSize(0.9F, 1.3F);
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (this.getPuffed()) {
            this.fallDistance = 0.0F;
            if (this.yd < -0.05) {
                this.yd = -0.05;
            }
        }

        if (this.getSheared()) {
            ++this.growthTimer;
        }

        int blockX;
        int blockY;
        int blockZ;
        Block<?> blockBelow;
        if (this.growthTimer > 400) {
            blockX = MathHelper.floor(this.x);
            blockY = MathHelper.floor(this.y);
            blockZ = MathHelper.floor(this.z);
            blockBelow = this.world.getBlock(blockX, blockY - 1, blockZ);
            this.growthTimer = 0;
            if (blockBelow == AetherBlocks.GRASS_AETHER && !this.world.isClientSide) {
                this.setIsSheepEating(true);
            }

            this.timeSheepEating = 0;
            this.prevTimeSheepEating = 0;
        }

        if (this.getIsSheepEating()) {
            blockX = MathHelper.floor(this.x);
            blockY = MathHelper.floor(this.y);
            blockZ = MathHelper.floor(this.z);
            blockBelow = this.world.getBlock(blockX, blockY - 1, blockZ);
            if (this.timeSheepEating >= 5 && this.timeSheepEating <= 35 && this.timeSheepEating % 5 == 0 && !this.world.isClientSide) {
                this.world.playBlockSoundEffect(null, this.x + 0.5, this.y + 0.5, this.z + 0.5, AetherBlocks.GRASS_AETHER, EnumBlockSoundEffectType.DIG);
            }

            this.prevTimeSheepEating = this.timeSheepEating++;
            if (this.prevTimeSheepEating == 35 && blockBelow == AetherBlocks.GRASS_AETHER && !this.world.isClientSide) {
                this.world.playBlockEvent(null, 2001, (int)this.x, (int)this.y - 1, (int)this.z, this.world.getBlockId((int)this.x, (int)this.y - 1, (int)this.z));
                this.world.setBlockWithNotify(blockX, blockY - 1, blockZ, AetherBlocks.DIRT_AETHER.id());
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

    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Sheared", this.getSheared());
        tag.putBoolean("Puffed", this.getPuffed());
        tag.putByte("Color", (byte)this.getFleeceColor().blockMeta);
        tag.putShort("GrowthTimer", (short)this.growthTimer);
    }

    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setSheared(tag.getBoolean("Sheared"));
        this.setSheared(tag.getBoolean("Puffed"));
        this.setFleeceColor(DyeColor.colorFromBlockMeta(tag.getByte("Color")));
        this.setGrowthTimer(tag.getShort("GrowthTimer"));
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

    public void jump() {
        if (this.getPuffed()) {
            this.yd = 1.8;
            this.xd += this.random.nextGaussian() * 0.5;
            this.zd += this.random.nextGaussian() * 0.5;
        } else {
            this.yd = 0.41999998688697815;
        }

    }

    public void spawnInit() {
        this.setFleeceColor(getRandomFleeceColor(this.random));
    }

    public static DyeColor getRandomFleeceColor(Random random) {
        int i = random.nextInt(100);
        if (i < 5) {
            return DyeColor.CYAN;
        } else if (i < 10) {
            return DyeColor.LIGHT_BLUE;
        } else if (i < 15) {
            return DyeColor.LIME;
        } else if (i < 18) {
            return DyeColor.PURPLE;
        } else {
            return random.nextInt(500) != 0 ? DyeColor.WHITE : DyeColor.PINK;
        }
    }


}
