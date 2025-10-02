package teamport.aether.models;

import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.util.helper.Side;

public class BlockModelPaintedBlock<T extends BlockLogic> extends BlockModelStandard<T> {
    public static final IconCoordinate[] texCoords = new IconCoordinate[16];

    public BlockModelPaintedBlock(Block<T> block, String texturePath) {
        super(block);
        for (DyeColor color : DyeColor.values()) {
            texCoords[color.blockMeta] = TextureRegistry.getTexture(texturePath + color.colorID);
        }
    }

    public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int data) {
        return texCoords[data & 15];
    }
}
