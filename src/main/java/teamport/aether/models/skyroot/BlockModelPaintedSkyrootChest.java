package teamport.aether.models.skyroot;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelChest;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.util.helper.Axis;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.WorldSource;

@Environment(EnvType.CLIENT)
public class BlockModelPaintedSkyrootChest<T extends BlockLogic> extends BlockModelChest<T> {
    private static final IconCoordinate[][] TEX_COORDS = new IconCoordinate[16][];

    public BlockModelPaintedSkyrootChest(Block<T> block) {
        super(block, "aether:block/chest/skyroot/");
    }

    @Override
    public IconCoordinate getBlockTexture(WorldSource blockAccess, int x, int y, int z, Side side) {
        int meta = blockAccess.getBlockMetadata(x, y, z);
        int color = meta >> 4;
        Side facing = BlockLogicChest.getDirectionFromMeta(meta).getSide();
        BlockLogicChest.Type type = BlockLogicChest.getTypeFromMeta(meta);
        if (side != Side.TOP && side != Side.BOTTOM) {
            if (type == BlockLogicChest.Type.SINGLE && side == facing) {
                return TEX_COORDS[color][0];
            } else {
                if (type == BlockLogicChest.Type.LEFT) {
                    if (side == facing) {
                        return TEX_COORDS[color][1];
                    }

                    if (side == facing.getOpposite()) {
                        return TEX_COORDS[color][4];
                    }
                }

                if (type == BlockLogicChest.Type.RIGHT) {
                    if (side == facing) {
                        return TEX_COORDS[color][2];
                    }

                    if (side == facing.getOpposite()) {
                        return TEX_COORDS[color][3];
                    }
                }

                return side.getAxis() != Axis.Y ? TEX_COORDS[color][5] : TEX_COORDS[color][6];
            }
        } else if (type == BlockLogicChest.Type.LEFT) {
            return TEX_COORDS[color][7];
        } else {
            return type == BlockLogicChest.Type.RIGHT ? TEX_COORDS[color][8] : TEX_COORDS[color][6];
        }
    }

    @Override
    public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int data) {
        int color = data >> 4;
        if (side == Side.SOUTH) {
            return TEX_COORDS[color][0];
        } else {
            return side.isHorizontal() ? TEX_COORDS[color][5] : TEX_COORDS[color][6];
        }
    }

    static {
        for (DyeColor c : DyeColor.blockOrderedColors()) {
            String rootKey = "aether:block/chest/skyroot/" + c.colorID + "/";
            TEX_COORDS[c.blockMeta] = new IconCoordinate[9];
            TEX_COORDS[c.blockMeta][0] = TextureRegistry.getTexture(rootKey + "front");
            TEX_COORDS[c.blockMeta][1] = TextureRegistry.getTexture(rootKey + "left_front");
            TEX_COORDS[c.blockMeta][2] = TextureRegistry.getTexture(rootKey + "right_front");
            TEX_COORDS[c.blockMeta][3] = TextureRegistry.getTexture(rootKey + "left_back");
            TEX_COORDS[c.blockMeta][4] = TextureRegistry.getTexture(rootKey + "right_back");
            TEX_COORDS[c.blockMeta][5] = TextureRegistry.getTexture(rootKey + "side");
            TEX_COORDS[c.blockMeta][6] = TextureRegistry.getTexture(rootKey + "top");
            TEX_COORDS[c.blockMeta][7] = TextureRegistry.getTexture(rootKey + "top_left");
            TEX_COORDS[c.blockMeta][8] = TextureRegistry.getTexture(rootKey + "top_right");
        }
    }
}

