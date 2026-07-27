package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelTrapDoor;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicTrapDoor;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.helper.Sides;

import java.util.EnumMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class BlockModelRetroTrapDoor<T extends BlockLogic> extends BlockModelTrapDoor<T> {
    private final Map<Side, IconCoordinate> retroTextures = new EnumMap<>(Side.class);

    public BlockModelRetroTrapDoor(Block<T> block) {
        super(block);
    }

    @Override
    public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int metadata) {
        int rotation = metadata & 3;
        Side effectiveSide;
        if (BlockLogicTrapDoor.isTrapdoorOpen(metadata)) {
            effectiveSide = Side.fromId(Sides.orientationLookUpTrapdoorOpen[6 * rotation + side.id]);
        } else if (side.axis().isVertical()) {
            effectiveSide = Side.BOTTOM;
        } else {
            effectiveSide = Side.SOUTH;
        }

        IconCoordinate retroTexture = retroTextures.get(effectiveSide);
        return isRetro() && retroTexture != null ? retroTexture : super.getBlockTextureFromSideAndMetadata(side, metadata);
    }

    public BlockModelRetroTrapDoor<T> setRetroTex(String texture, Side... sides) {
        IconCoordinate coordinate = TextureRegistry.getTexture(texture);
        for (Side side : sides) retroTextures.put(side, coordinate);
        return this;
    }
}
