package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelAxisAligned;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicAxisAligned;
import net.minecraft.core.util.helper.Axis;

@Environment(EnvType.CLIENT)
public class BlockModelAetherLog<T extends BlockLogic> extends BlockModelAxisAligned<T> {
    public BlockModelAetherLog(Block<T> block) {
        super(block);
    }

    @SuppressWarnings("java:S131")
    @Override
    public boolean render(Tessellator tessellator, int x, int y, int z) {
        int meta = renderBlocks.blockAccess.getBlockMetadata(x, y, z);
        Axis axis = BlockLogicAxisAligned.metaToAxis(meta & 0b11);
        switch (axis) {
            case Y:
                renderBlocks.uvRotateEast = 0;
                renderBlocks.uvRotateWest = 0;
                renderBlocks.uvRotateSouth = 0;
                renderBlocks.uvRotateNorth = 0;
                break;
            case Z:
                renderBlocks.uvRotateSouth = 1;
                renderBlocks.uvRotateNorth = 1;
                break;
            case X:
                renderBlocks.uvRotateEast = 1;
                renderBlocks.uvRotateWest = 1;
                renderBlocks.uvRotateTop = 1;
                renderBlocks.uvRotateBottom = 1;
        }

        this.renderStandardBlock(tessellator, this.block.getBlockBoundsFromState(renderBlocks.blockAccess, x, y, z), x, y, z);
        this.resetRenderBlocks();
        return true;
    }
}
