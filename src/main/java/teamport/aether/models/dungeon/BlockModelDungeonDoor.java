package teamport.aether.models.dungeon;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelRotatable;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicRotatable;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.helper.Sides;
import net.minecraft.core.world.WorldSource;
import teamport.aether.world.feature.util.WorldFeaturePoint;

@Environment(EnvType.CLIENT)
public class BlockModelDungeonDoor<T extends BlockLogic> extends BlockModelRotatable<T> {
    private final int width;
    private final int height;

    private final IconCoordinate buffer = new IconCoordinate(TextureRegistry.blockAtlas, null, null);
    private IconCoordinate particleTexture = TextureRegistry.getTexture("minecraft:block/texture_missing");
    private IconCoordinate particleTextureRetro = TextureRegistry.getTexture("minecraft:block/texture_missing");


    public BlockModelDungeonDoor(Block<T> block, int width, int height) {
        super(block);
        this.width = width;
        this.height = height;
    }

    protected IconCoordinate ctm(TextureLayer layer, IconCoordinate fallback, WorldSource blockAccess, int x, int y, int z, Side side) {
        int meta = blockAccess.getBlockMetadata(x, y, z);

        Side sideRotated = Side.getSideById(Sides.orientationLookUpHorizontal[6 * Math.min(meta & BlockLogicRotatable.MASK_DIRECTION, 5) + side.getId()]);
        IconCoordinate baseTex = layer.get(sideRotated);

        if (baseTex == null) return fallback;

        Direction dir = BlockLogicRotatable.getDirectionFromMeta(meta);
        Direction offsetLeft = dir.rotate(-1);
        Direction offsetRight = dir.rotate(1);

        if (dir == Direction.WEST || dir == Direction.SOUTH) {
            offsetLeft = offsetLeft.getOpposite();
            offsetRight = offsetRight.getOpposite();
        }

        boolean up = blockAccess.getBlockId(x, y + 1, z) == block.id();
        boolean down = blockAccess.getBlockId(x, y - 1, z) == block.id();

        boolean right = blockAccess.getBlockId(
            x + offsetRight.getOffsetX(),
            y + offsetRight.getOffsetY(),
            z + offsetRight.getOffsetZ()
        ) == block.id();

        boolean left = blockAccess.getBlockId(
            x + offsetLeft.getOffsetX(),
            y + offsetLeft.getOffsetY(),
            z + offsetLeft.getOffsetZ()
        ) == block.id();

        int u;
        int v;

        if (!up) v = 0;
        else if (!down) v = height - 1;
        else {
            v = 0;
            while (v < height - 1) {
                if (blockAccess.getBlockId(x, y + 1 + v, z) == block.id()) v++;
                else break;
            }
        }

        if (!left) u = 0;
        else if (!right) u = width - 1;
        else {
            u = 0;
            WorldFeaturePoint p = new WorldFeaturePoint(x, y, z);
            while (u < width - 1) {
                p = p.moveInDirection(offsetRight).copy();
                if (blockAccess.getBlockId(p.getX(), p.getY(), p.getZ()) == block.id()) u++;
                else break;
            }

            u = width - 1 - u;
        }

        if (side == Side.EAST || side == Side.NORTH) u = width - 1 - u;

        int textWidth = baseTex.width / width;
        int textHeight = baseTex.height / height;

        buffer.setPosition(baseTex.iconX + u * textWidth, baseTex.iconY + v * textHeight);
        buffer.setDimension(textWidth, textHeight);
        return buffer;
    }

    @Override
    public IconCoordinate getBlockTexture(WorldSource blockAccess, int x, int y, int z, Side side) {
        if (isRetro()) {
            return ctm(this.retroBlockTextures, TextureRegistry.getTexture("minecraft:block/texture_missing"), blockAccess, x, y, z, side);
        }
        return ctm(this.blockTextures, TextureRegistry.getTexture("minecraft:block/texture_missing"), blockAccess, x, y, z, side);
    }

    @Override
    public IconCoordinate getBlockOverbrightTexture(WorldSource blockAccess, int x, int y, int z, int side) {
        if (isRetro()) {
            return ctm(this.retroOverbrightTextures, null, blockAccess, x, y, z, Side.getSideById(side));
        }
        return ctm(this.overbrightTextures, null, blockAccess, x, y, z, Side.getSideById(side));
    }

    @Override
    public IconCoordinate getParticleTexture(Side side, int meta) {
        if (isRetro()) return particleTextureRetro;
        return particleTexture;
    }

    public BlockModelDungeonDoor<T> setParticleTexture(boolean isRetro, String texture) {
        if (isRetro) particleTextureRetro = TextureRegistry.getTexture(texture);
        else particleTexture = TextureRegistry.getTexture(texture);
        return this;
    }
}
