package teamport.aether.entity.animal.sheepuff;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.item.ItemDye;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolShears;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlocks;
import teamport.aether.entity.animal.MobAetherAnimal;
import teamport.aether.item.AetherItemTags;

import java.util.Random;

public class MobSheepuff extends MobAetherAnimal {
    protected static final float[][] FLEECE_COLOR_TABLE = new float[][]{{1.0F, 1.0F, 1.0F}, {0.95F, 0.7F, 0.2F}, {0.9F, 0.5F, 0.85F}, {0.6F, 0.7F, 0.95F}, {0.9F, 0.9F, 0.2F}, {0.5F, 0.8F, 0.1F}, {0.95F, 0.7F, 0.8F}, {0.3F, 0.3F, 0.3F}, {0.6F, 0.6F, 0.6F}, {0.3F, 0.6F, 0.7F}, {0.7F, 0.4F, 0.9F}, {0.2F, 0.4F, 0.8F}, {0.5F, 0.4F, 0.3F}, {0.4F, 0.5F, 0.2F}, {0.8F, 0.3F, 0.3F}, {0.1F, 0.1F, 0.1F}};
    private int growthTimer;
    private int timeSheepEating;
    private int prevTimeSheepEating;

    public MobSheepuff(World world) {
        super(world);
        this.setTextureIdentifier("aether", "sheepuff");
        this.growthTimer = this.random.nextInt(100) + 100;
        this.setSize(0.9F, 1.3F);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(16, (byte) 0, Byte.class);
        this.entityData.define(17, (byte) 0, Byte.class);
    }

    @Override
    public void spawnInit() {
        this.setFleeceColor(getRandomFleeceColor(this.random));
        if (random.nextInt(5) == 0) {
            this.setPuffed(true);
        }
    }

    @Override
    public void dropDeathItems() {
        super.dropDeathItems();
        if (!this.getSheared() && !this.getPuffed()) {
            this.dropItem(new ItemStack(Blocks.WOOL.id(), 1, this.getFleeceColor().blockMeta), 0.0F);
        }

        if (this.getPuffed()) {
            this.dropItem(new ItemStack(Blocks.WOOL.id(), 2, this.getFleeceColor().blockMeta), 0.0F);
        }

    }

    @Override
    public boolean interact(@NonNull Player player) {
        if (this.world == null) return super.interact(player);
        if (super.interact(player)) return true;

        ItemStack heldItem = player.inventory.getCurrentItem();
        if (heldItem == null) {
            return false;
        }

        if (heldItem.getItem() instanceof ItemDye) {
            if (!this.world.isClientSide) {
                DyeColor newColor = DyeColor.colorFromItemMeta(heldItem.getMetadata());
                if (this.getFleeceColor() != newColor && heldItem.consumeItem(player)) {
                    this.setFleeceColor(newColor);
                }
            }
            return true;
        }

        if (!(heldItem.getItem() instanceof ItemToolShears)) {
            return false;
        }

        boolean canShear = this.getPuffed() || (!this.getSheared() && !this.getPuffed());
        if (!canShear) {
            return false;
        }

        if (!this.world.isClientSide) {
            if (this.getPuffed()) {
                this.setPuffed(false);
            } else {
                this.setSheared(true);
            }

            int woolCount = 2 + this.random.nextInt(3);
            int meta = this.getFleeceColor().blockMeta;

            for (int i = 0; i < woolCount; i++) {
                EntityItem entityItem = this.dropItem(new ItemStack(Blocks.WOOL.id(), 1, meta), 1.0F);
                entityItem.yd += this.random.nextFloat() * 0.05F;
                entityItem.xd += (this.random.nextFloat() - this.random.nextFloat()) * 0.1F;
                entityItem.zd += (this.random.nextFloat() - this.random.nextFloat()) * 0.1F;
            }
        }

        heldItem.damageItem(1, player);
        if (heldItem.stackSize <= 0) {
            player.destroyCurrentEquippedItem();
        }

        return true;
    }

    public void onItemInteract(ItemStack itemStack) {
        boolean canShear = this.getPuffed() || (!this.getSheared() && !this.getPuffed());
        if (itemStack.getItem() instanceof ItemToolShears && canShear) {

            if (!this.world.isClientSide) {
                if (this.getPuffed()) {
                    this.setPuffed(false);
                } else {
                    this.setSheared(true);
                }

                int woolCount = 2 + this.random.nextInt(3);
                for (int i = 0; i < woolCount; i++) {
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
    public void addAdditionalSaveData(@NonNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Sheared", this.getSheared());
        tag.putBoolean("Puffed", this.getPuffed());
        tag.putByte("Color", (byte) this.getFleeceColor().blockMeta);
        tag.putShort("GrowthTimer", (short) this.growthTimer);
    }

    @Override
    public void readAdditionalSaveData(@NonNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setSheared(tag.getBoolean("Sheared"));
        this.setPuffed(tag.getBoolean("Puffed"));
        this.setFleeceColor(DyeColor.colorFromBlockMeta(tag.getByte("Color")));
        this.setGrowthTimer(tag.getShort("GrowthTimer"));
    }

    @Override
    public boolean isMovementBlocked() {
        return super.isMovementBlocked() || this.getIsSheepEating();
    }

    public boolean getIsSheepEating() {
        return this.entityData.getByte(17) != 0;
    }

    public void setIsSheepEating(boolean value) {
        this.entityData.set(17, (byte) (value ? 1 : 0));
    }

    @Override
    public void onLivingUpdate() {
        if (this.world == null) return;
        super.onLivingUpdate();

        if (!this.getPuffed()) {
            ++this.growthTimer;
        }

        if (this.getPuffed()) {
            this.setSheared(false);
        }

        if (this.getSheared()) {
            this.setPuffed(false);
        }

        if (this.getPuffed()) {
            this.fallDistance = 0.0F;
            if (this.yd < -0.05) {
                this.yd *= 0.4;
            }
        }

        if (!this.getPuffed() && this.growthTimer > 400) {
            this.growthTimer = 0;

            Block<?> blockBelow = getBlockBelow();
            if (isValidGrass(blockBelow) && !this.world.isClientSide) {
                this.setIsSheepEating(true);
            }

            this.timeSheepEating = 0;
            this.prevTimeSheepEating = 0;
        }

        if (this.getIsSheepEating()) {
            this.prevTimeSheepEating = this.timeSheepEating;
            this.timeSheepEating++;

            int x = MathHelper.floor(this.x);
            int y = MathHelper.floor(this.y - 0.1);
            int z = MathHelper.floor(this.z);

            if (this.timeSheepEating >= 5 && this.timeSheepEating <= 35 && this.timeSheepEating % 5 == 0 && !this.world.isClientSide) {
                this.world.playBlockSoundEffect(null, this.x + 0.5, this.y + 0.5, this.z + 0.5, AetherBlocks.GRASS_AETHER, EnumBlockSoundEffectType.DIG);
            }

            if (this.prevTimeSheepEating == 35 && !this.world.isClientSide && isValidGrass(getBlockBelow())) {
                int dirtId = (getBlockBelow() == AetherBlocks.GRASS_AETHER) ? AetherBlocks.DIRT_AETHER.id() : Blocks.DIRT.id();
                this.world.playBlockEvent(null, 2001, x, y, z, this.world.getBlockId(x, y, z));
                this.world.setBlockWithNotify(x, y, z, dirtId);

                if (this.getSheared()) {
                    this.setSheared(false);
                } else {
                    this.setPuffed(true);
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

    private Block<?> getBlockBelow() {
        return this.world.getBlock(
            MathHelper.floor(this.x),
            MathHelper.floor(this.y - 0.1),
            MathHelper.floor(this.z)
        );
    }

    private boolean isValidGrass(Block<?> block) {
        return block == Blocks.GRASS || block == Blocks.GRASS_RETRO || block == AetherBlocks.GRASS_AETHER;
    }

    @Override
    public String getLivingSound() {
        return "mob.sheep";
    }

    @Override
    public String getHurtSound() {
        return "mob.sheep";
    }

    @Override
    public String getDeathSound() {
        return "mob.sheep";
    }

    public DyeColor getFleeceColor() {
        return DyeColor.colorFromBlockMeta(this.entityData.getByte(16) & 15);
    }

    public void setFleeceColor(DyeColor color) {
        byte woolState = this.entityData.getByte(16);
        this.entityData.set(16, (byte) (woolState & -16 | color.blockMeta & 15));
    }

    public boolean getSheared() {
        return (this.entityData.getByte(16) & 16) != 0;
    }

    public void setSheared(boolean flag) {
        byte woolState = this.entityData.getByte(16);
        if (flag) {
            this.entityData.set(16, (byte) (woolState | 16));
        } else {
            this.entityData.set(16, (byte) (woolState & -17));
        }

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

    public static DyeColor getRandomFleeceColor(Random random) {
        int i = random.nextInt(100);
        if(i < 5){
            return DyeColor.LIGHT_BLUE;
        }else if(i < 10){
            return DyeColor.CYAN;
        } else if (i < 15) {
            return DyeColor.LIME;
        } else if (i < 18) {
            return DyeColor.PINK;
        }
        return random.nextInt(500) == 0 ? DyeColor.PURPLE : DyeColor.WHITE;
    }

    public int getTimeSheepEating() {
        return timeSheepEating;
    }

    public int getPrevTimeSheepEating() {
        return prevTimeSheepEating;
    }

    @Override
    public boolean isFavouriteItem(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.itemID < Blocks.blocksList.length) {
            Block<?> block = Blocks.blocksList[itemStack.itemID];
            if (block != null && block.hasTag(BlockTags.SHEEPS_FAVOURITE_BLOCK)) return true;
        }
        return itemStack.getItem().hasTag(AetherItemTags.NATURE_STAFF_FOLLOW);
    }

    public void setGrowthTimer(int growthTimer) {
        this.growthTimer = growthTimer;
    }

    @Override
    public void jump() {
        if (this.getIsSheepEating()) {
            this.yd = 0.0;
        } else if (this.getPuffed()) {
            this.yd = 1.5;
            this.xd += this.random.nextGaussian() * 0.5;
            this.zd += this.random.nextGaussian() * 0.5;
        } else {
            this.yd = 0.41999998688697815;
        }

    }

}
