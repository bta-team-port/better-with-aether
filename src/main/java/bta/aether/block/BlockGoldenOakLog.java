package bta.aether.block;

import bta.aether.item.AetherItems;
import net.minecraft.core.block.BlockLog;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class BlockGoldenOakLog extends BlockLog {
    public BlockGoldenOakLog(String key, int id) {
        super(key, id);
        this.setDropOverride(AetherBlocks.dirtAether);
    }

    @Override
    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
        if (dropCause.equals(EnumDropCause.SILK_TOUCH)) {
            return super.getBreakResult(world, dropCause, x, y, z, meta, tileEntity);
        }

        return new ItemStack[]{new ItemStack(AetherBlocks.logSkyroot, 1)};
    }

    @Override
    public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int meta, TileEntity tileEntity) {
        Item item = entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem].getItem();
        if (item.hasTag(AetherItems.aetherTool) && item.canHarvestBlock(this) && world.rand.nextInt(3) == 0) {
            world.dropItem(x, y, z, new ItemStack(AetherItems.amberGolden, 2 + world.rand.nextInt(2)));
        }
        super.harvestBlock(world, entityplayer, x, y, z, meta, tileEntity);
    }

}
