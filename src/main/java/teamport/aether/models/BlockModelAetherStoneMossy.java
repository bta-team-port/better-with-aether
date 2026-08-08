package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class BlockModelAetherStoneMossy<T extends BlockLogic> extends BlockModelStandard<T> {
    protected IconCoordinate mossOverlay = TextureRegistry.getTexture("aether:block/moss_overlay");

    public BlockModelAetherStoneMossy(Block<T> block) {
        super(block);
    }

    @Override
    public boolean render(@NonNull TessellatorGeneral tessellator, @NonNull WorldSource blockAccess, @NonNull TilePosc pos) {
        boolean rendered = super.render(tessellator, blockAccess, pos);
        renderBlocks.overrideBlockTexture = mossOverlay;
        boolean overlayRendered = super.render(tessellator, blockAccess, pos);
        renderBlocks.overrideBlockTexture = null;
        return rendered || overlayRendered;
    }

    @Override
    public void renderStandalone(@NonNull TessellatorGeneral tessellator, int metadata, byte lightmap) {
        super.renderStandalone(tessellator, metadata, lightmap);
        renderBlocks.overrideBlockTexture = mossOverlay;
        super.renderStandalone(tessellator, metadata, lightmap);
        renderBlocks.overrideBlockTexture = null;
    }
}
