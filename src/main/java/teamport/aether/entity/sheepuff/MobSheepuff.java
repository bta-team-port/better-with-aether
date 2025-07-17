package teamport.aether.entity.sheepuff;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.animal.MobSheep;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolShears;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.entity.MobAetherAnimal;

import java.util.Random;

public class MobSheepuff extends MobAetherAnimal {
    public int growthTimer;
    public MobSheepuff(World world) {
        super(world);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "sheepuff");
        this.setSize(0.9F, 1.3F);
    }

    protected void dropDeathItems() {
        super.dropDeathItems();
        if (!this.getSheared()) {
            this.dropItem(new ItemStack(Blocks.WOOL.id(), 1, this.getFleeceColor().blockMeta), 0.0F);
        }

        if (this.getPuffed()) {
            this.dropItem(new ItemStack(Blocks.WOOL.id(), 2, this.getFleeceColor().blockMeta), 0.0F);
        }

    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (!this.getPuffed()) {
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
                if (!this.getPuffed() && !this.getSheared()) {
                    this.setPuffed(true);
                }
                if (!this.getPuffed() && this.getSheared()) {
                    this.setSheared(false);
                }
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

    @Override
    public boolean interact(@NotNull Player player) {
        if (super.interact(player)) {
            return true;
        } else {
            ItemStack itemstack = player.inventory.getCurrentItem();
            if (itemstack != null && itemstack.getItem() instanceof ItemToolShears && this.getPuffed()) {
                if (!this.world.isClientSide) {
                    if (this.getPuffed()) {
                        this.setPuffed(false);
                    } else if (!this.getSheared()) {
                        this.setSheared(true);
                        this.setPuffed(false);
                    }

                    int count = 2 + this.random.nextInt(3);
                    for (int j = 0; j < count; ++j) {
                        EntityItem entityitem = this.dropItem(new ItemStack(Blocks.WOOL.id(), 1, this.getFleeceColor().blockMeta), 1.0F);
                        entityitem.yd += this.random.nextFloat() * 0.05F;
                        entityitem.xd += (this.random.nextFloat() - this.random.nextFloat()) * 0.1F;
                        entityitem.zd += (this.random.nextFloat() - this.random.nextFloat()) * 0.1F;
                    }
                }
                itemstack.damageItem(1, player);
                if (itemstack.stackSize <= 0) {
                    player.destroyCurrentEquippedItem();
                }

                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void onItemInteract(ItemStack itemStack) {
        if (itemStack.getItem() instanceof ItemToolShears) {
            if (!this.world.isClientSide) {
                if (this.getPuffed()) {
                    this.setPuffed(false);
                } else if (!this.getSheared()) {
                    this.setSheared(true);
                    this.setPuffed(false);
                }

                int count = 2 + this.random.nextInt(3);
                for(int j = 0; j < count; ++j) {
                    EntityItem entityItem = this.dropItem(new ItemStack(Blocks.WOOL.id(), 1, this.getFleeceColor().blockMeta), 1.0F);
                    entityItem.yd += this.random.nextFloat() * 0.05F;
                    entityItem.xd += (this.random.nextFloat() - this.random.nextFloat()) * 0.1F;
                    entityItem.zd += (this.random.nextFloat() - this.random.nextFloat()) * 0.1F;
                }
            }
            itemStack.damageItem(1, null);
        }
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Sheared", this.getSheared());
        tag.putBoolean("Puffed", this.getPuffed());
        tag.putByte("Color", (byte)this.getFleeceColor().blockMeta);
        tag.putShort("GrowthTimer", (short)this.growthTimer);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setSheared(tag.getBoolean("Sheared"));
        this.setPuffed(tag.getBoolean("Puffed"));
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

    @Override
    public void tick() {
        super.tick();

        if (this.getSheared()) {
            this.setPuffed(false);
        }

        if (this.getPuffed()) {
            this.setSheared(false);
        }

        if (this.getPuffed()) {
            this.fallDistance = 0.0F;
            if (this.yd < -0.05) {
                this.yd *= 0.6;
            }
        }
    }

    public void jump() {
        if (this.getPuffed()) {
            this.yd = 1.5;
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
