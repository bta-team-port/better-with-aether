package bta.aether.block;

import bta.aether.item.AetherToolMaterial;
import net.minecraft.core.block.BlockAxisAligned;
import net.minecraft.core.block.BlockLog;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.enums.PlacementMode;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.tool.ItemTool;
import net.minecraft.core.util.helper.Axis;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class BlockDoubleLog extends BlockLog {
    protected final Class<? extends ItemTool> toolClass;
    public BlockDoubleLog(String key, int id, Class<? extends ItemTool> toolClass) {
        super(key, id);
        this.toolClass = toolClass;
    }
    @Override
    public void onBlockPlaced(World world, int x, int y, int z, Side side, EntityLiving entity, double sideHeight) {
        int meta = world.getBlockMetadata(x, y, z);
        Axis axis = entity.getPlacementDirection(side, PlacementMode.SIDE).getAxis();
        world.setBlockMetadataWithNotify(x, y, z, BlockAxisAligned.axisToMeta(axis) | (meta & 0b1000_0000));
    }
    @Override
    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta, EntityPlayer player, Item item) {
        if (toolClass.isInstance(item) && ((ItemTool) item).getMaterial() == AetherToolMaterial.TOOL_SKYROOT && player.getGamemode().consumeBlocks() && meta == 0){
            dropBlockWithCause(world, EnumDropCause.PROPER_TOOL, x, y, z, meta, world.getBlockTileEntity(x, y, z));
        }
    }
}
