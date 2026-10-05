package teamport.aether.models.skyroot;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelEmpty;
import net.minecraft.client.render.block.model.generic.BlockModelGenericPlanksPainted;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.Side;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.block.skyroot.BlockLogicPaintedSignSkyroot;

@Environment(EnvType.CLIENT)
public class BlockModelSignSkyrootPainted<T extends BlockLogicPaintedSignSkyroot> extends BlockModelEmpty<T> {
    public BlockModelSignSkyrootPainted(Block<T> block) {
        super(block);
    }

    @Override
    public @Nullable IconCoordinate getParticleTexture(@NonNull Side side, int meta) {
        return BlockModelGenericPlanksPainted.texCoords[this.block.getLogic().fromMetadata(meta).blockMeta];
    }
}
