package bta.aether.block;

import bta.aether.item.AetherItems;
import net.minecraft.core.block.BlockLog;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.apache.commons.lang3.ArrayUtils;

public class BlockGoldenOakLog extends BlockLog {
    public BlockGoldenOakLog(String key, int id) {
        super(key, id);
        this.setDropOverride(AetherBlocks.dirtAether);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int meta, TileEntity tileEntity) {
        ItemStack item = entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem];
        if (item != null && ArrayUtils.contains(new Item[]{AetherItems.toolAxeZanite, AetherItems.toolAxeGravitite}, item.getItem()) && world.rand.nextBoolean()) {
            world.dropItem(x, y, z, new ItemStack(AetherItems.amberGolden, 2 + world.rand.nextInt(2)));
            world.dropItem(x, y, z, new ItemStack(AetherBlocks.logSkyroot, 1));
            return;
        }
            world.dropItem(x, y, z, new ItemStack(this, 1));
    }

}
