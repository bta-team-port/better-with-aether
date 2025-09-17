package teamport.aether.models;

import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.helper.Sides;
import org.jetbrains.annotations.Nullable;
import teamport.aether.blocks.BlockLogicChestMimic;

import java.util.HashMap;
import java.util.Map;

public class BlockModelChestMimic<T extends BlockLogic> extends BlockModelStandard<T> {

    protected Map<Integer, IconCoordinate[]> blockTexturesByVariant = new HashMap<>();

    public BlockModelChestMimic(Block<T> block) {
        super(block);
    }

    public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int data) {
        int variant = BlockLogicChestMimic.getVariantFromMeta(data);
        int sideMeta = data & 7;
        int index = Sides.orientationLookUpHorizontal[6 * Math.min(sideMeta, 5) + side.getId()];

        if (!blockTexturesByVariant.containsKey(variant)) return BlockModelStandard.BLOCK_TEXTURE_UNASSIGNED;

        IconCoordinate i;
        if (index >= Sides.orientationLookUpHorizontal.length) {
            i = this.blockTexturesByVariant.get(variant)[Side.BOTTOM.getId()];
        } else {
            i = this.blockTexturesByVariant.get(variant)[index];
        }

        if (i == null) return BlockModelStandard.BLOCK_TEXTURE_UNASSIGNED;
        return i;
    }

    public BlockModelChestMimic<?> setTex(int layer, int variant, @Nullable String texture, Side... sides) {
        if (texture == null) return this;

        IconCoordinate[] textures;
        textures = this.blockTexturesByVariant.computeIfAbsent(variant, (k) -> new IconCoordinate[6]);

        for (Side side : sides) {
            textures[side.getId()] = TextureRegistry.getTexture(texture);
        }

        return this;
    }
}
