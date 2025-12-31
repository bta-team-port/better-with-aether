package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.WorldSource;

@Environment(EnvType.CLIENT)
public class BlockModelIncubator<T extends BlockLogic> extends BlockModelStandard<T> {
    public BlockModelIncubator(Block<T> block) {
        super(block);
    }

    @Override
    public IconCoordinate getBlockTexture(WorldSource blockAccess, int x, int y, int z, Side side) {
        if (side.getId() == Side.TOP.getId()) {
            IconCoordinate texture;
            if (isRetro()) {
                texture = this.retroBlockTextures.get(Side.TOP);
            } else {
                texture = this.blockTextures.get(Side.TOP);
            }
            Container container = (Container) blockAccess.getTileEntity(x, y, z);
            if (container != null) {
                boolean hasInput = container.getItem(0) != null;
                if (hasInput && texture != null) {
                    return TextureRegistry.getTexture(texture.namespaceId.namespace() + ":block/" + texture.namespaceId.value() + "_filled");
                }
            }
            return texture;
        }
        if (isRetro()) {
            return this.retroBlockTextures.get(side.getId());
        }
        return this.blockTextures.get(side.getId());
    }
}
