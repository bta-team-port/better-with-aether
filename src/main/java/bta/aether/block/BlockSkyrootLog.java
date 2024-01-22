package bta.aether.block;

import net.minecraft.core.block.BlockLog;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.world.World;

public class BlockSkyrootLog extends BlockLog {
    protected final Class<?> toolClass;
    public BlockSkyrootLog(String key, int id, Class<?> toolClass) {
        super(key, id);
        this.toolClass = toolClass;
    }
    @Override
    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta, EntityPlayer player, Item item) {
        if (toolClass.isInstance(item) && player.getGamemode().consumeBlocks() && (meta & 0b1000_0000) != 0){
            dropBlockWithCause(world, EnumDropCause.PROPER_TOOL, x, y, z, meta, world.getBlockTileEntity(x, y, z));
        }
    }
}
