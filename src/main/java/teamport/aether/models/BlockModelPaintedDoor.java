package teamport.aether.models;

import net.minecraft.client.render.block.model.BlockModelDoor;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.Axis;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.util.helper.Side;
import teamport.aether.blocks.skyroot.BlockLogicPaintedDoor;

public class BlockModelPaintedSkyrootDoor<T extends BlockLogicPaintedDoor> extends BlockModelDoor<T> {
    public static final IconCoordinate[] doorBottomTextures = new IconCoordinate[16];
    public static final IconCoordinate[] doorTopTextures = new IconCoordinate[16];
    public static final IconCoordinate[] frameTopTextures = new IconCoordinate[16];
    private final boolean isTop;

    public BlockModelPaintedSkyrootDoor(Block<T> block, boolean isTop) {
        super(block);
        this.isTop = isTop;
    }

    public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int data) {
        int color = data >> 4 & 15;
        if (side.getAxis() == Axis.Y) {
            return frameTopTextures[color];
        } else {
            return this.isTop ? doorTopTextures[color] : doorBottomTextures[color];
        }
    }

    static {
        for(DyeColor c : DyeColor.blockOrderedColors()) {
            doorTopTextures[c.blockMeta] = TextureRegistry.getTexture("aether:block/door/planks_" + c.colorID + "/top");
            doorBottomTextures[c.blockMeta] = TextureRegistry.getTexture("aether:block/door/planks_" + c.colorID + "/bottom");
            frameTopTextures[c.blockMeta] = TextureRegistry.getTexture("aether:block/door/planks_" + c.colorID + "/frame_top");
        }
    }
}
