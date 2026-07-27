package teamport.aether.models.skyroot;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelTrapDoor;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicTrapDoor;
import net.minecraft.core.util.helper.Axis;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.helper.Sides;

@Environment(EnvType.CLIENT)
public class BlockModelPaintedSkyrootTrapDoor<T extends BlockLogic> extends BlockModelTrapDoor<T> {
    private static final IconCoordinate[] TOP_TEXTURES = new IconCoordinate[16];
    private static final IconCoordinate[] SIDE_TEXTURES = new IconCoordinate[16];

    public BlockModelPaintedSkyrootTrapDoor(Block<T> block) {
        super(block);
    }

    @Override
    public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int data) {
        int color = data >> 4 & 15;
        int orientation = data & 3;
        if (BlockLogicTrapDoor.isTrapdoorOpen(data)) {
            int index = Sides.orientationLookUpTrapdoorOpen[6 * orientation + side.id];
            return index < 2 ? TOP_TEXTURES[color] : SIDE_TEXTURES[color];
        } else {
            return side.axis() == Axis.Y ? TOP_TEXTURES[color] : SIDE_TEXTURES[color];
        }
    }

    static {
        for (DyeColor c : DyeColor.blockOrderedColors()) {
            TOP_TEXTURES[c.blockMeta] = TextureRegistry.getTexture("aether:block/trapdoor/skyroot/" + c.colorID + "/top");
            SIDE_TEXTURES[c.blockMeta] = TextureRegistry.getTexture("aether:block/trapdoor/skyroot/" + c.colorID + "/side");
        }
    }
}
