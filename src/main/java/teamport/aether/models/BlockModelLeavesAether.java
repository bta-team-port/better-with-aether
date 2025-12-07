package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelLeaves;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.helper.Side;

@Environment(EnvType.CLIENT)
public class BlockModelLeavesAether<T extends BlockLogic> extends BlockModelLeaves<T> {
    private final String retroBaseTexture;

    public BlockModelLeavesAether(Block<T> block, String normalLeavesTex, String retroLeavesTex) {
        super(block, normalLeavesTex, true);
        this.retroBaseTexture = retroLeavesTex;
    }

    @Override
    public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int data) {
        String retroTex = fancyGraphics ? retroBaseTexture + "_fancy" : retroBaseTexture;
        if (this.canBeRetro && this.isRetro()) {
            return TextureRegistry.getTexture(retroTex);
        } else {
            return fancyGraphics ? this.fancyLeavesTexture : super.getBlockTextureFromSideAndMetadata(side, data);
        }
    }
}
