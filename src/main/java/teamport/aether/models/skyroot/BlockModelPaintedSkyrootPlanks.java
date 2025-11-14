package teamport.aether.models.skyroot;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.util.helper.Side;

@Environment(EnvType.CLIENT)
public class BlockModelPaintedSkyrootPlanks<T extends BlockLogic> extends BlockModelStandard<T> {
    protected static final IconCoordinate[] TEX_COORDS = new IconCoordinate[16];

    public BlockModelPaintedSkyrootPlanks(Block<T> block) {
        super(block);
    }

    @Override
    public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int data) {
        return TEX_COORDS[data & 15];
    }

    static {
        for (DyeColor color : DyeColor.values()) {
            TEX_COORDS[color.blockMeta] = TextureRegistry.getTexture("aether:block/skyroot/" + color.colorID);
        }
    }
}
