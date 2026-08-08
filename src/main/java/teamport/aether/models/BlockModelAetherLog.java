package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelAxisAligned;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.helper.Side;

import java.util.EnumMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class BlockModelAetherLog<T extends BlockLogic> extends BlockModelAxisAligned<T> {
    private final Map<Side, IconCoordinate> retroTextures = new EnumMap<>(Side.class);

    public BlockModelAetherLog(Block<T> block) {
        super(block);
    }

    @Override
    public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int metadata) {
        IconCoordinate retroTexture = retroTextures.get(side);
        return isRetro() && retroTexture != null ? retroTexture : super.getBlockTextureFromSideAndMetadata(side, metadata);
    }

    public BlockModelAetherLog<T> setRetroTex(String texture, Side... sides) {
        IconCoordinate coordinate = TextureRegistry.getTexture(texture);
        for (Side side : sides) retroTextures.put(side, coordinate);
        return this;
    }
}
