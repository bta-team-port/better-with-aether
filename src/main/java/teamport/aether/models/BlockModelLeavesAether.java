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
    public BlockModelLeavesAether(Block<T> block, String leavesTex, boolean canBeRetro) {
        super(block, leavesTex, canBeRetro);
    }

    @Override
    public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int data) {
        if (this.canBeRetro && this.isRetro()) {
            return fancyGraphics ? TextureRegistry.getTexture("aether:block/leaves/oak_golden_retro_fancy") : TextureRegistry.getTexture("aether:block/leaves/oak_golden_retro");
        } else {
            return fancyGraphics ? this.fancyLeavesTexture : super.getBlockTextureFromSideAndMetadata(side, data);
        }
    }
}
