package teamport.aether.models.skyroot;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelSlab;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicSlab;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePosc;

@Environment(EnvType.CLIENT)
public class BlockModelPaintedSkyrootSlab<T extends BlockLogicSlab> extends BlockModelSlab<T> {
    public BlockModelPaintedSkyrootSlab(Block<T> block) {
        super(block);
    }

    @Override
    public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int meta) {
        meta >>= 4;
        return BlockModelPaintedSkyrootPlanks.TEX_COORDS[meta & 15];
    }

    @Override
    public IconCoordinate getBlockTexture(WorldSource blockAccess, TilePosc pos, Side side) {
        return this.getBlockTextureFromSideAndMetadata(side, blockAccess.getBlockMetadata(pos.x(), pos.y(), pos.z()));
    }
}
