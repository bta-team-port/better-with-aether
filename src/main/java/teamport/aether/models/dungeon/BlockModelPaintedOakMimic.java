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
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.dungeon.BlockLogicChestMimic;

@Environment(EnvType.CLIENT)
public class BlockModelPaintedOakMimic<T extends BlockLogicChestMimic> extends BlockModelHorizontalRotation<T> {
    private static final IconCoordinate[] TOP_TEXTURES = new IconCoordinate[16];
    private static final IconCoordinate[] SIDE_TEXTURES = new IconCoordinate[16];
    private static final IconCoordinate[] FRONT_TEXTURES = new IconCoordinate[16];

    public BlockModelPaintedOakMimic(Block<T> block) {
        super(block);
    }

    @Override
    public IconCoordinate getBlockTexture(@NonNull WorldSource blockAccess, @NonNull TilePosc pos, @NonNull Side side) {
        int meta = blockAccess.getBlockData(pos);
        int color = meta >> 4;
        Side facing = BlockLogicChest.getDirectionFromMeta(meta).side();
        if (side == Side.TOP || side == Side.BOTTOM) {
            return TOP_TEXTURES[color];
        }
        if (side == facing) {
            return FRONT_TEXTURES[color];
        }
        return side.isHorizontal() ? SIDE_TEXTURES[color] : TOP_TEXTURES[color];
    }

    @Override
    public IconCoordinate getBlockTextureFromSideAndMetadata(@NonNull Side side, int data) {
        int color = data >> 4;
        if (side == Side.SOUTH) {
            return FRONT_TEXTURES[color];
        } else {
            return side.isHorizontal() ? SIDE_TEXTURES[color] : TOP_TEXTURES[color];
        }
    }

    static {
        for (DyeColor c : DyeColor.blockOrderedColors()) {
            FRONT_TEXTURES[c.blockMeta] = TextureRegistry.getTexture("minecraft:block/chest/planks_" + c.colorID + "/front"); // 0
            SIDE_TEXTURES[c.blockMeta] = TextureRegistry.getTexture("minecraft:block/chest/planks_" + c.colorID + "/side");   // 5
            TOP_TEXTURES[c.blockMeta] = TextureRegistry.getTexture("minecraft:block/chest/planks_" + c.colorID + "/top");     // 6
        }
    }
}
