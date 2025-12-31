package teamport.aether.models.skyroot;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelDoor;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.Axis;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.util.helper.Side;
import teamport.aether.block.skyroot.BlockLogicPaintedDoor;

@Environment(EnvType.CLIENT)
public class BlockModelPaintedSkyrootDoor<T extends BlockLogicPaintedDoor> extends BlockModelDoor<T> {
    private static final IconCoordinate[] DOOR_BOTTOM_TEXTURES = new IconCoordinate[16];
    private static final IconCoordinate[] DOOR_TOP_TEXTURES = new IconCoordinate[16];
    private static final IconCoordinate[] FRAME_TOP_TEXTURES = new IconCoordinate[16];
    private final boolean isTop;

    public BlockModelPaintedSkyrootDoor(Block<T> block, boolean isTop) {
        super(block);
        this.isTop = isTop;
    }

    @Override
    public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int data) {
        int color = data >> 4 & 15;
        if (side.getAxis() == Axis.Y) {
            return FRAME_TOP_TEXTURES[color];
        } else {
            return this.isTop ? DOOR_TOP_TEXTURES[color] : DOOR_BOTTOM_TEXTURES[color];
        }
    }

    static {
        for (DyeColor c : DyeColor.blockOrderedColors()) {
            DOOR_TOP_TEXTURES[c.blockMeta] = TextureRegistry.getTexture("aether:block/door/skyroot_" + c.colorID + "/top");
            DOOR_BOTTOM_TEXTURES[c.blockMeta] = TextureRegistry.getTexture("aether:block/door/skyroot_" + c.colorID + "/bottom");
            FRAME_TOP_TEXTURES[c.blockMeta] = TextureRegistry.getTexture("aether:block/door/skyroot_" + c.colorID + "/frame_top");
        }
    }
}
