package teamport.aether.models;

import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.helper.Sides;
import org.jetbrains.annotations.Nullable;
import teamport.aether.blocks.BlockLogicChestMimic;

import java.util.HashMap;
import java.util.Map;

public class BlockModelChestMimic extends BlockModelStandard<BlockLogicChestMimic> {

    protected Map<Integer, IconCoordinate[]> blockTexturesByVariant = new HashMap<>();
    protected Map<Integer, IconCoordinate[]> overbrightTexturesByVariant = new HashMap<>();


    public BlockModelChestMimic(Block block) {
        super(block);
    }

    public IconCoordinate getBlockOverbrightTextureFromSideAndMeta(Side side, int data) {
        int variant = BlockLogicChestMimic.getVariantFromMeta(data);
        int sideMeta = data & 7;
        int index = Sides.orientationLookUpHorizontal[6 * Math.min(sideMeta, 5) + side.getId()];

        if (!overbrightTexturesByVariant.containsKey(variant)) return null;

        IconCoordinate i;
        if (index >= Sides.orientationLookUpHorizontal.length) {
            i = this.overbrightTexturesByVariant.get(variant)[Side.BOTTOM.getId()];
        }
        else {
            i = this.overbrightTexturesByVariant.get(variant)[index];
        }

        return i;
    }

    public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int data) {
        int variant = BlockLogicChestMimic.getVariantFromMeta(data);
        int sideMeta = data & 7;
        int index = Sides.orientationLookUpHorizontal[6 * Math.min(sideMeta, 5) + side.getId()];

        if (!blockTexturesByVariant.containsKey(variant)) return BlockModelStandard.BLOCK_TEXTURE_UNASSIGNED;

        IconCoordinate i;
        if (index >= Sides.orientationLookUpHorizontal.length) {
            i = this.blockTexturesByVariant.get(variant)[Side.BOTTOM.getId()];
        }
        else {
            i = this.blockTexturesByVariant.get(variant)[index];
        }

        if (i == null) return BlockModelStandard.BLOCK_TEXTURE_UNASSIGNED;
        return i;
    }

    public BlockModelChestMimic setTex(int layer, int variant, @Nullable String texture, Side... sides) {
        if (texture == null) return this;

        IconCoordinate[] textures;
        switch (layer) {
            case 0:
                textures = this.blockTexturesByVariant.computeIfAbsent(variant, (k) -> new IconCoordinate[6]);
                break;

            case 1:
                textures = this.overbrightTexturesByVariant.computeIfAbsent(variant, (k) -> new IconCoordinate[6]);
                break;

            default:
                throw new RuntimeException("I was too lazy to implement this! - Ya boi, Oly");
        }

        for (Side side : sides) {
            textures[side.getId()] = TextureRegistry.getTexture(texture);
        }

        return this;
    }
}
