package teamport.aether.models.skyroot;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelFenceGate;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFenceGate;
import net.minecraft.core.util.helper.Side;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class BlockModelPaintedSkyrootFenceGate<T extends BlockLogicFenceGate> extends BlockModelFenceGate<T> {
    public BlockModelPaintedSkyrootFenceGate(Block<T> block) {
        super(block);
    }

    @Override
    public IconCoordinate getBlockTextureFromSideAndMetadata(@NonNull Side side, int meta) {
        meta >>= 4;
        return BlockModelPaintedSkyrootPlanks.TEX_COORDS[meta & 15];
    }
}
