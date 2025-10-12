package teamport.aether.models;

import net.minecraft.client.render.block.model.BlockModelHorizontalRotation;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.WorldSource;
import teamport.aether.blocks.dungeon.BlockLogicChestMimic;

public class BlockModelPaintedSkyrootMimic<T extends BlockLogicChestMimic> extends BlockModelHorizontalRotation<T> {
    public static final IconCoordinate[] topTextures = new IconCoordinate[16];
    public static final IconCoordinate[] sideTextures = new IconCoordinate[16];
    public static final IconCoordinate[] frontTextures = new IconCoordinate[16];

    public BlockModelPaintedSkyrootMimic(Block<T> block) {
        super(block);
    }

    public IconCoordinate getBlockTexture(WorldSource blockAccess, int x, int y, int z, Side side) {
        int meta = blockAccess.getBlockMetadata(x, y, z);
        int color = meta >> 4;
        Side facing = BlockLogicChest.getDirectionFromMeta(meta).getSide();
        if (side == Side.TOP || side == Side.BOTTOM) {
            return topTextures[color];
        }
        if (side == facing) {
            return frontTextures[color];
        }
        return side.isHorizontal() ? sideTextures[color] : topTextures[color];
    }

    public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int metadata) {
        int color = metadata >> 4;
        if (side == Side.SOUTH) {
            return frontTextures[color];
        } else {
            return side.isHorizontal() ? sideTextures[color] : topTextures[color];
        }
    }

    static {
        for (DyeColor c : DyeColor.blockOrderedColors()) {
            topTextures[c.blockMeta] = TextureRegistry.getTexture("aether:block/chest/skyroot/" + c.colorID + "/top");
            sideTextures[c.blockMeta] = TextureRegistry.getTexture("aether:block/chest/skyroot/" + c.colorID + "/side");
            frontTextures[c.blockMeta] = TextureRegistry.getTexture("aether:block/chest/skyroot/" + c.colorID + "/front");
        }
    }

}
