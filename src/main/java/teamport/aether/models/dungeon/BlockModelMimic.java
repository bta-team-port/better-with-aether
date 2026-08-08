package teamport.aether.models.dungeon;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelRotatable;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePosc;
import teamport.aether.block.dungeon.BlockLogicChestMimic;

@Environment(EnvType.CLIENT)
public class BlockModelMimic<T extends BlockLogicChestMimic> extends BlockModelRotatable<T> {
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
    public IconCoordinate getBlockTexture(WorldSource blockAccess, TilePosc pos, Side side) {
        int meta = blockAccess.getBlockMetadata(pos.x(), pos.y(), pos.z());
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
    public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int data) {
        if (side == Side.SOUTH) {
            return frontTexture;
        } else {
            return side.isHorizontal() ? sideTexture : topTexture;
        }
    }
}
