package bta.aether.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLeavesBase;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.world.World;

public class BlockAetherDouble extends Block {
    protected final Class<?> toolClass;
    public BlockAetherDouble(String key, int id, Material material, Class<?> toolClass) {
        super(key, id, material);
        this.toolClass = toolClass;
    }
    @Override
    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta, EntityPlayer player, Item item) {
        if (toolClass.isInstance(item) && player.getGamemode().consumeBlocks() && meta == 0){
            dropBlockWithCause(world, EnumDropCause.PROPER_TOOL, x, y, z, meta, world.getBlockTileEntity(x, y, z));
        }
    }
}
