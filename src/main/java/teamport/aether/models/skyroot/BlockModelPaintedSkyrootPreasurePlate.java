package teamport.aether.models.skyroot;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.helper.Side;

@Environment(EnvType.CLIENT)
public class BlockModelPaintedSkyrootPreasurePlate<T extends BlockLogic> extends BlockModelStandard<T> {
    public BlockModelPaintedSkyrootPreasurePlate(Block<T> block) {
        super(block);
    }

    @Override
    public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int meta) {
        meta >>= 4;
        return BlockModelPaintedSkyrootPlanks.TEX_COORDS[meta & 15];
    }
}
