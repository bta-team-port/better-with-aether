package teamport.aether.models.dungeon;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelHorizontalRotation;
import net.minecraft.client.render.block.model.BlockModelRotatable;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.dungeon.BlockLogicChestMimic;

@Environment(EnvType.CLIENT)
public class BlockModelMimic<T extends BlockLogicChestMimic> extends BlockModelHorizontalRotation<T> {
    private final IconCoordinate frontTexture;
    private final IconCoordinate sideTexture;
    private final IconCoordinate topTexture;

    public BlockModelMimic(Block<T> block, String rootKey) {
        super(block);
        this.frontTexture = TextureRegistry.getTexture(rootKey + "front");
        this.sideTexture = TextureRegistry.getTexture(rootKey + "side");
        this.topTexture = TextureRegistry.getTexture(rootKey + "top");
    }

    @Override
    public IconCoordinate getBlockTexture(@NonNull WorldSource blockAccess, @NonNull TilePosc pos, @NonNull Side side) {
        int meta = blockAccess.getBlockData(pos);
        Side facing = BlockLogicChest.getDirectionFromMeta(meta).side();
        if (side == Side.TOP || side == Side.BOTTOM) {
            return topTexture;
        }
        if (side == facing) {
            return frontTexture;
        }
        return side.isHorizontal() ? sideTexture : topTexture;
    }

    @Override
    public IconCoordinate getBlockTextureFromSideAndMetadata(@NonNull Side side, int data) {
        if (side == Side.SOUTH) {
            return frontTexture;
        } else {
            return side.isHorizontal() ? sideTexture : topTexture;
        }
    }
}
