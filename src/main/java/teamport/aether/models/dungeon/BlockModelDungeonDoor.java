package teamport.aether.models.dungeon;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelRotatable;
import net.minecraft.client.render.texture.stitcher.AtlasStitcher;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicRotatable;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.helper.Sides;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import teamport.aether.world.feature.util.WorldFeaturePoint;

import java.util.EnumMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class BlockModelDungeonDoor<T extends BlockLogic> extends BlockModelRotatable<T> {
    private final int width;
    private final int height;
    private final Map<Side, IconCoordinate> retroBlockTextures = new EnumMap<>(Side.class);
    private IconCoordinate particleTexture = TextureRegistry.getTexture("minecraft:block/texture_missing");
    private IconCoordinate particleTextureRetro = TextureRegistry.getTexture("minecraft:block/texture_missing");

    public BlockModelDungeonDoor(Block<T> block, int width, int height) {
        super(block);
        this.width = width;
        this.height = height;
    }

    protected IconCoordinate ctm(IconCoordinate fallback, @NonNull WorldSource blockAccess, int x, int y, int z, @NonNull Side side) {
        int meta = blockAccess.getBlockMetadata(x, y, z);
        Side sideRotated = Side.fromId(Sides.orientationLookUpHorizontal[6 * Math.min(meta & BlockLogicRotatable.MASK_DIRECTION, 5) + side.id]);
        IconCoordinate baseTex;
        baseTex = isRetro() ? retroBlockTextures.get(sideRotated) : blockTextures.get(sideRotated);
        if (baseTex == null) return fallback;

        Direction dir = BlockLogicRotatable.getDirectionFromMeta(meta);
        Direction offsetLeft = dir.rotateY(1);
        Direction offsetRight = dir.rotateY(-1);
        if (dir == Direction.WEST || dir == Direction.SOUTH) {
            offsetLeft = offsetLeft.opposite();
            offsetRight = offsetRight.opposite();
        }

        boolean up = blockAccess.getBlockId(x, y + 1, z) == block.id();
        boolean down = blockAccess.getBlockId(x, y - 1, z) == block.id();
        boolean right = blockAccess.getBlockId(x + offsetRight.offsetX(), y + offsetRight.offsetY(), z + offsetRight.offsetZ()) == block.id();
        boolean left = blockAccess.getBlockId(x + offsetLeft.offsetX(), y + offsetLeft.offsetY(), z + offsetLeft.offsetZ()) == block.id();

        int u;
        int v;
        if (!up) v = 0;
        else if (!down) v = height - 1;
        else {
            v = 0;
            while (v < height - 1 && blockAccess.getBlockId(x, y + 1 + v, z) == block.id()) v++;
        }

        if (!left) u = 0;
        else if (!right) u = width - 1;
        else {
            u = 0;
            WorldFeaturePoint point = new WorldFeaturePoint(x, y, z);
            while (u < width - 1) {
                point = point.moveInDirection(offsetRight).copy();
                if (blockAccess.getBlockId(point.getX(), point.getY(), point.getZ()) == block.id()) u++;
                else break;
            }
            u = width - 1 - u;
        }

        if (side == Side.EAST || side == Side.NORTH) u = width - 1 - u;
        int textureWidth = baseTex.width / width;
        int textureHeight = baseTex.height / height;
        CoordinateBuffer buffer = new CoordinateBuffer(baseTex.parentAtlas);
        buffer.setCoordinates(baseTex.iconX + u * textureWidth, baseTex.iconY + v * textureHeight, textureWidth, textureHeight);
        return buffer;
    }

    private static final class CoordinateBuffer extends IconCoordinate {

        private CoordinateBuffer(AtlasStitcher atlas) {
            super(atlas, null);
        }

        private void setCoordinates(int x, int y, int width, int height) {
            this.setPosition(x, y);
            this.setDimension(width, height);
            this.cacheUVs();
        }
    }

    @Override
    public IconCoordinate getBlockTexture(@NonNull WorldSource blockAccess, @NonNull TilePosc pos, @NonNull Side side) {
        return ctm(TextureRegistry.getTexture("minecraft:block/texture_missing"), blockAccess, pos.x(), pos.y(), pos.z(), side);
    }

    @Override
    public IconCoordinate getBlockTextureFromSideAndMetadata(@NonNull Side side, int metadata) {
        return isRetro() ? particleTextureRetro : particleTexture;
    }

    @Override
    public IconCoordinate getParticleTexture(@NonNull Side side, int meta) {
        return isRetro() ? particleTextureRetro : particleTexture;
    }

    public BlockModelDungeonDoor<T> setRetroTex(String texture, Side @NonNull ... sides) {
        IconCoordinate coordinate = TextureRegistry.getTexture(texture);
        for (Side side : sides) this.retroBlockTextures.put(side, coordinate);
        return this;
    }

    public BlockModelDungeonDoor<T> setParticleTexture(boolean isRetro, String texture) {
        if (isRetro) particleTextureRetro = TextureRegistry.getTexture(texture);
        else particleTexture = TextureRegistry.getTexture(texture);
        return this;
    }

}
