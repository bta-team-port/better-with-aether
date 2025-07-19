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
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class BlockModelTopStateMachine<T extends BlockLogic> extends BlockModelStandard<T> {
    public BlockModelTopStateMachine(Block<T> block) {
        super(block);
    }

    public IconCoordinate getBlockTexture(WorldSource blockAccess, int x, int y, int z, Side side) {
        if (side.getId() == Side.TOP.getId()) {
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
