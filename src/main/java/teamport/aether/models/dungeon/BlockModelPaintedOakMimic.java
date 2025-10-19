package teamport.aether.models.dungeon;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelHorizontalRotation;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.WorldSource;
import teamport.aether.blocks.dungeon.BlockLogicChestMimic;

@Environment(EnvType.CLIENT)
public class BlockModelPaintedOakMimic<T extends BlockLogicChestMimic> extends BlockModelHorizontalRotation<T> {
    public static final IconCoordinate[] topTextures = new IconCoordinate[16];
    public static final IconCoordinate[] sideTextures = new IconCoordinate[16];
    public static final IconCoordinate[] frontTextures = new IconCoordinate[16];

    public BlockModelPaintedOakMimic(Block<T> block) {
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

    public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int data) {
        int color = data >> 4;
        if (side == Side.SOUTH) {
            return frontTextures[color];
        } else {
            return side.isHorizontal() ? sideTextures[color] : topTextures[color];
        }
    }

    static {
        for (DyeColor c : DyeColor.blockOrderedColors()) {
            frontTextures[c.blockMeta] = TextureRegistry.getTexture("minecraft:block/chest/planks_" + c.colorID + "/front"); // 0
            sideTextures[c.blockMeta] = TextureRegistry.getTexture("minecraft:block/chest/planks_" + c.colorID + "/side");   // 5
            topTextures[c.blockMeta] = TextureRegistry.getTexture("minecraft:block/chest/planks_" + c.colorID + "/top");     // 6
        }
    }
}
