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
import net.minecraft.core.util.helper.Sides;
import net.minecraft.core.world.WorldSource;

@Environment(EnvType.CLIENT)
public class BlockModelTopStateMachine<T extends BlockLogic> extends BlockModelStandard<T> {
    public BlockModelTopStateMachine(Block<T> block) {
        super(block);
    }

    public IconCoordinate getBlockTexture(WorldSource blockAccess, int x, int y, int z, Side side) {
        int sideId = side.getId();
        if (sideId >= Sides.orientationLookUpHorizontal.length) {
            return this.blockTextures.get(Side.BOTTOM);
        } else if (sideId == Side.TOP.getId()) {
            IconCoordinate originalFront = this.blockTextures.get(Side.TOP);
            Container container = (Container) blockAccess.getTileEntity(x, y, z);
            if (container != null) {
                boolean hasOutput = container.getItem(2) != null;
                if (hasOutput) {
                    return TextureRegistry.getTexture(originalFront.namespaceId.namespace() + ":block/" + originalFront.namespaceId.value() + "_filled");
                }
            }
            return originalFront;
        }
        return this.blockTextures.get(side);
    }

}
