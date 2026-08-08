package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.util.helper.Axis;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class BlockModelGrassAether<T extends BlockLogic> extends BlockModelStandard<T> {
    public static boolean useOverlay = false;
    private static final IconCoordinate[] overlayIndices = new IconCoordinate[]{
        null,
        null,
        TextureRegistry.getTexture("aether:block/grass_aether/side_overlay"),
        TextureRegistry.getTexture("aether:block/grass_aether/side_overlay"),
        TextureRegistry.getTexture("aether:block/grass_aether/side_overlay"),
        TextureRegistry.getTexture("aether:block/grass_aether/side_overlay")};
    protected IconCoordinate snowSide = TextureRegistry.getTexture("aether:block/grass_aether/snowy_side");
    protected IconCoordinate retroTop = TextureRegistry.getTexture("aether:block/grass_aether/top_retro");
    protected IconCoordinate retroBottom = TextureRegistry.getTexture("aether:block/grass_aether/bottom_retro");
    protected IconCoordinate retroSide = TextureRegistry.getTexture("aether:block/grass_aether/side_retro");
    protected IconCoordinate retroSnowSide = TextureRegistry.getTexture("aether:block/grass_aether/snowy_side_retro");

    public BlockModelGrassAether(Block<T> block) {
        super(block);
    }

    @Override
    public IconCoordinate getBlockTexture(@NonNull WorldSource blockAccess, @NonNull TilePosc pos, @NonNull Side side) {
        TilePos tilePos = new TilePos(pos);
        Material above = blockAccess.getBlockMaterial(pos.up(tilePos));
        boolean isSnowy = (above == Materials.TOP_SNOW || above == Materials.SNOW);

        if (isSnowy && side.axis() != Axis.Y) {
            return isRetro() ? retroSnowSide : snowSide;
        }

        if (isRetro()) {
            if (side == Side.TOP) return retroTop;
            if (side == Side.BOTTOM) return retroBottom;
            return retroSide;
        }
        return super.getBlockTexture(blockAccess, pos, side);
    }

    @Override
    public IconCoordinate getBlockTextureFromSideAndMetadata(@NonNull Side side, int data) {
        if (isRetro()) {
            if (side == Side.TOP) return retroTop;
            if (side == Side.BOTTOM) return retroBottom;
            return retroSide;
        }
        return useOverlay ? overlayIndices[side.id] : super.getBlockTextureFromSideAndMetadata(side, data);
    }

    @Override
    public boolean shouldSideBeColored(@NonNull WorldSource blockAccess, @NonNull TilePosc pos, @NonNull Side side, int meta) {
        Material material = blockAccess.getBlockMaterial(pos.x(), pos.y() + 1, pos.z());
        if (material != Materials.TOP_SNOW && material != Materials.SNOW) {
            return useOverlay || side == Side.TOP;
        } else {
            return false;
        }
    }
}
