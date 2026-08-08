package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.helper.Side;
import org.jspecify.annotations.NonNull;

import java.util.EnumMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class BlockModelRetroStandard<T extends BlockLogic> extends BlockModelStandard<T> {
    private final Map<Side, IconCoordinate> retroTextures = new EnumMap<>(Side.class);

    public BlockModelRetroStandard(Block<T> block) {
        super(block);
    }

    @Override
    public IconCoordinate getBlockTextureFromSideAndMetadata(@NonNull Side side, int metadata) {
        IconCoordinate retroTexture = retroTextures.get(side);
        return isRetro() && retroTexture != null ? retroTexture : super.getBlockTextureFromSideAndMetadata(side, metadata);
    }

    @Override
    public BlockModelRetroStandard<T> setTex(IconCoordinate texture, Side... sides) {
        super.setTex(texture, sides);
        return this;
    }

    @Override
    public BlockModelRetroStandard<T> setTex(String texture, Side... sides) {
        super.setTex(texture, sides);
        return this;
    }

    @Override
    public BlockModelRetroStandard<T> setAllTextures(IconCoordinate texture) {
        super.setAllTextures(texture);
        return this;
    }

    @Override
    public BlockModelRetroStandard<T> setAllTextures(String texture) {
        super.setAllTextures(texture);
        return this;
    }

    public BlockModelRetroStandard<T> setRetroTex(String texture, Side @NonNull ... sides) {
        IconCoordinate coordinate = TextureRegistry.getTexture(texture);
        for (Side side : sides) retroTextures.put(side, coordinate);
        return this;
    }

    public BlockModelRetroStandard<T> setRetroAllTextures(String texture) {
        return setRetroTex(texture, Side.sides);
    }

}
