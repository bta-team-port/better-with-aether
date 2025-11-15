package teamport.aether.models.skyroot;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelEmpty;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.Side;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.blocks.skyroot.BlockLogicPaintedSignSkyroot;

@Environment(EnvType.CLIENT)
public class BlockModelPaintedSkyrootSign<T extends BlockLogicPaintedSignSkyroot> extends BlockModelEmpty<T> {
    public BlockModelPaintedSkyrootSign(Block<T> block) {
        super(block);
    }

    @Override
    public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int meta) {
        return BlockModelPaintedSkyrootPlanks.TEX_COORDS[(this.block.getLogic()).fromMetadata(meta).blockMeta];
    }

    @Override
    public @Nullable IconCoordinate getParticleTexture(@NonNull Side side, int meta) {
        return BlockModelPaintedSkyrootPlanks.TEX_COORDS[(this.block.getLogic()).fromMetadata(meta).blockMeta];
    }
}
