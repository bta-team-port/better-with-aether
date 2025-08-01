package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelRotatable;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.helper.Sides;
import net.minecraft.core.world.WorldSource;

@Environment(EnvType.CLIENT)
public class BlockModelFreezer<T extends BlockLogic> extends BlockModelRotatable<T> {
    public BlockModelFreezer(Block<T> block) {
        super(block);
    }

    // TODO make the lid rotate with blocks rotation, need an special render for this might be overkill
    public IconCoordinate getBlockTexture(WorldSource blockAccess, int x, int y, int z, Side side) {
        int data = blockAccess.getBlockMetadata(x, y, z);
        int index = Sides.orientationLookUpHorizontal[6 * Math.min(data, 5) + side.getId()];
        if (index == Side.TOP.getId()) {
            IconCoordinate texture = this.blockTextures.get(Side.TOP);
            Container container = (Container) blockAccess.getTileEntity(x, y, z);
            if (container != null) {
                boolean hasOutput = container.getItem(2) != null;
                if (hasOutput) {
                    return TextureRegistry.getTexture(texture.namespaceId.namespace() + ":block/" + texture.namespaceId.value() + "_filled");
                }
            }
            return texture;
        }
        return this.blockTextures.get(side.getId());
    }

}
