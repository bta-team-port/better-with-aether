package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelRotatable;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.helper.Sides;
import teamport.aether.blocks.BlockLogicDungeonDoor;

@Environment(EnvType.CLIENT)
public class BlockModelDungeonDoor extends BlockModelRotatable<BlockLogicDungeonDoor> {

    int width = 3;
    int height = 3;

    public BlockModelDungeonDoor(Block<BlockLogicDungeonDoor> block) {
        super(block);
    }

    protected IconCoordinate cropTexture(IconCoordinate texture, int x, int y) {
        int textWidth = texture.width / width;
        int textHeight = texture.height / height;

        IconCoordinate i = new IconCoordinate(texture.parentAtlas, texture.namespaceId, texture.getImageSource());
        i.setPosition(texture.iconX + x*textWidth, texture.iconY + y*textHeight);
        i.setDimension(textWidth, textHeight);

        return i;
    }

    @Override
    public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int meta) {
        Side sideRotated = Side.getSideById(Sides.orientationLookUpHorizontal[6 * (meta & 7) + side.getId()]);

        IconCoordinate baseTex = this.blockTextures.get(sideRotated);
        if (baseTex == null) return TextureRegistry.getTexture("minecraft:block/texture_missing");

        int y = BlockLogicDungeonDoor.getHeightByMeta(meta).ordinal();
        int x = BlockLogicDungeonDoor.getSideByMeta(meta).ordinal();
        if (side == Side.EAST || side == Side.NORTH)  x = width -1 -x;

        return cropTexture(baseTex, x, y);
    }

    @Override
    public boolean hasOverbright() {
        return true;
    }

    @Override
    public IconCoordinate getBlockOverbrightTextureFromSideAndMeta(Side side, int meta) {
        Side sideRotated = Side.getSideById(Sides.orientationLookUpHorizontal[6 * (meta & 7) + side.getId()]);

        IconCoordinate baseTex = this.overbrightTextures.get(sideRotated);
        if (baseTex == null) return null;

        int y = BlockLogicDungeonDoor.getHeightByMeta(meta).ordinal();
        int x = BlockLogicDungeonDoor.getSideByMeta(meta).ordinal();
        if (side == Side.EAST || side == Side.NORTH)  x = width -1 -x;

        return cropTexture(baseTex, x, y);
    }
}
