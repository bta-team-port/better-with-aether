package teamport.aether.entity.tile;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.block.motion.CarriedBlock;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import teamport.aether.blocks.dungeon.BlockLogicChestMimic;

public class TileEntityMimic extends TileEntityChest implements Container {

    @Override
    public void dropContents(World world, int x, int y, int z) {
    }

    @Override
    public @Nullable ItemStack removeItem(int index, int takeAmount) {
        return null;
    }

    public String getNameTranslationKey() {
        return "aether.container.chest.trapped.name";
    }

    @Override
    public boolean tryPlace(World world, Entity holder, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
        CarriedBlock carriedBlock = this.carriedBlock;
        this.x = blockX + side.getOffsetX();
        this.y = blockY + side.getOffsetY();
        this.z = blockZ + side.getOffsetZ();
        Block<?> currentBlock = world.getBlock(this.x, this.y, this.z);
        if (currentBlock != null && !currentBlock.hasTag(BlockTags.PLACE_OVERWRITES)) {
            return false;
        } else {
            world.setBlockAndMetadata(this.x, this.y, this.z, carriedBlock.blockId, carriedBlock.metadata);
            this.worldObj = world;
            this.validate();
            world.removeBlockTileEntity(this.x, this.y, this.z);
            world.setTileEntity(this.x, this.y, this.z, this);
            Block<?> b = world.getBlock(this.x, this.y, this.z);
            if (b != null && holder instanceof Mob) {
                b.onBlockPlacedByMob(world, this.x, this.y, this.z, side, (Mob) holder, xPlaced, yPlaced);
            }

            int variantId = BlockLogicChestMimic.getVariantFromMeta(carriedBlock.metadata);
            int directionMeta = holder instanceof Mob ? ((Mob) holder).getHorizontalPlacementDirection(side).getOpposite().getId() : side.getDirection().getOpposite().getId();
            int finalMeta = BlockLogicChestMimic.setVariantToMeta(directionMeta, variantId);
            world.setBlockMetadataWithNotify(this.x, this.y, this.z, finalMeta);

            world.notifyBlockChange(this.x, this.y, this.z, carriedBlock.blockId);
            if (carriedBlock.blockId != 0 && Blocks.getBlock(carriedBlock.blockId).isSignalSource()) {
                Side[] var14 = Side.sides;

                for (Side s : var14) {
                    world.notifyBlocksOfNeighborChange(this.x + s.getOffsetX(), this.y + s.getOffsetY(), this.z + s.getOffsetZ(), this.getBlockId());
                }
            }

            return true;
        }
    }

    @Override
    public CarriedBlock getCarriedEntry(World world, Entity holder, Block<?> currentBlock, int currentMeta) {
        int variantId = BlockLogicChestMimic.getVariantFromMeta(currentMeta);
        int carriedMeta = BlockLogicChestMimic.setVariantToMeta(Direction.NORTH.getId(), variantId);
        return new CarriedBlock(holder, currentBlock, carriedMeta, this);
    }
}
