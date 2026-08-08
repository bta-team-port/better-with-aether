package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelCrossedSquares;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.helper.Side;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class BlockModelRetroCrossedSquares<T extends BlockLogic> extends BlockModelCrossedSquares<T> {
    private final IconCoordinate retroTexture;

    public BlockModelRetroCrossedSquares(Block<T> block, String retroTexture) {
        super(block);
        this.retroTexture = TextureRegistry.getTexture(retroTexture);
    }

    @Override
    public IconCoordinate getBlockTextureFromSideAndMetadata(@NonNull Side side, int metadata) {
        return isRetro() ? retroTexture : super.getBlockTextureFromSideAndMetadata(side, metadata);
    }
}
