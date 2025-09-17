package teamport.aether.models;

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
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.world.generate.feature.components.WorldFeaturePoint;

@Environment(EnvType.CLIENT)
public class BlockModelDungeonDoor<T extends BlockLogic> extends BlockModelRotatable<T> {
    public int width;
    public int height;

    public final IconCoordinate itemTexture;
    public final IconCoordinate itemOverbrightTexture;
    public final IconCoordinate itemTextureRetro;
    public final IconCoordinate itemOverbrightTextureRetro;

    public BlockModelDungeonDoor(Block<T> block, int width, int height) {
        super(block);
        this.width = width;
        this.height = height;

        this.itemTexture = TextureRegistry.getTexture(getTexturePath(block, false));
        this.itemOverbrightTexture = TextureRegistry.getTexture(getTexturePath(block, true));
        this.itemTextureRetro = TextureRegistry.getTexture(getRetroTexturePath(block, false));
        this.itemOverbrightTextureRetro = TextureRegistry.getTexture(getRetroTexturePath(block, true));
    }

    public String getTexturePath(Block<?> block, boolean overbright) {
        String basePath = "aether:block/ctm/boss_door/";
        if (block == AetherBlocks.DOOR_DUNGEON_GOLD) {
            return basePath + "gold/" + (overbright ? "hellfire_door_overlay" : "hellfire_door");
        } else if (block == AetherBlocks.DOOR_DUNGEON_SILVER) {
            return basePath + "silver/" + (overbright ? "angelic_door_overlay" : "angelic_door");
        } else if (block == AetherBlocks.DOOR_DUNGEON_BRONZE) {
            return basePath + "bronze/" + (overbright ? "carved_door_overlay" : "carved_door");
        }
        return "minecraft:block/texture_missing";
    }

    public String getRetroTexturePath(Block<?> block, boolean overbright) {
        String basePath = "aether:block/ctm/boss_door/";
        if (block == AetherBlocks.DOOR_DUNGEON_GOLD) {
            return basePath + "gold/" + (overbright ? "hellfire_door_retro_overlay" : "hellfire_door_retro");
        } else if (block == AetherBlocks.DOOR_DUNGEON_SILVER) {
            return basePath + "silver/" + (overbright ? "angelic_door_retro_overlay" : "angelic_door_retro");
        } else if (block == AetherBlocks.DOOR_DUNGEON_BRONZE) {
            return basePath + "bronze/" + (overbright ? "carved_door_retro_overlay" : "carved_door_retro");
        }
        return "minecraft:block/texture_missing";
    }

    protected IconCoordinate cropTexture(IconCoordinate texture, int x, int y) {
        int textWidth = texture.width / width;
        int textHeight = texture.height / height;

        IconCoordinate i = new IconCoordinate(texture.parentAtlas, texture.namespaceId, texture.getImageSource());
        i.setPosition(texture.iconX + x * textWidth, texture.iconY + y * textHeight);
        i.setDimension(textWidth, textHeight);

        return i;
    }

    protected IconCoordinate ctm(TextureLayer layer, IconCoordinate fallback, WorldSource blockAccess, int x, int y, int z, Side side) {
        int meta = blockAccess.getBlockMetadata(x, y, z);
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
                if (blockAccess.getBlockId(p.x, p.y, p.z) == block.id()) u++;
                else break;
            }

            u = width - 1 - u;
        }

        Side sideRotated = Side.getSideById(Sides.orientationLookUpHorizontal[6 * (meta & 7) + side.getId()]);
        IconCoordinate baseTex = layer.get(sideRotated);
        if (baseTex == null) return fallback;

        if (side == Side.EAST || side == Side.NORTH) u = width - 1 - u;
        return cropTexture(baseTex, u, v);
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
            return ctm(this.retroOverbrightTextures, TextureRegistry.getTexture("minecraft:block/texture_missing"), blockAccess, x, y, z, Side.getSideById(side));
        }
        return ctm(this.overbrightTextures, TextureRegistry.getTexture("minecraft:block/texture_missing"), blockAccess, x, y, z, Side.getSideById(side));
    }

    @Override
    public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int metadata) {
        if (isRetro()) {
            return itemTextureRetro;
        }
        return itemTexture;
    }

    @Override
    public IconCoordinate getBlockOverbrightTextureFromSideAndMeta(Side side, int metadata) {
        if (isRetro()) {
            return itemOverbrightTextureRetro;
        }
        return itemOverbrightTexture;
    }

}
