package bta.aether.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class BlockQuicksoil extends Block {
    protected final Class<?> toolClass;
    public BlockQuicksoil(String key, int id, Material material, Class<?> toolClass) {
        super(key, id, material);
        this.movementScale = 1.1f;
        this.toolClass = toolClass;
    }
    @Override
    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, Side side, int meta, EntityPlayer player, Item item) {
        if (toolClass.isInstance(item) && player.getGamemode().consumeBlocks() && meta == 0){
            dropBlockWithCause(world, EnumDropCause.PROPER_TOOL, x, y, z, meta, world.getBlockTileEntity(x, y, z));
        }
    }
}
