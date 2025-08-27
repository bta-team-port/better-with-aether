package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.helper.Sides;
import net.minecraft.core.world.WorldSource;
import teamport.aether.blocks.BlockLogicFreezer;

@Environment(EnvType.CLIENT)
public class BlockModelFreezer<T extends BlockLogic> extends BlockModelStandard<T> {

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

    @Override
    public boolean render(Tessellator tessellator, int x, int y, int z) {
        int meta = renderBlocks.blockAccess.getBlockMetadata(x, y, z);
        Direction direction = BlockLogicFreezer.getDirectionFromMeta(meta);

        switch (direction) {
            case NORTH:
                renderBlocks.uvRotateTop = 3;
                break;

            case EAST:
                renderBlocks.uvRotateTop = 2;
                break;

            case SOUTH:
                renderBlocks.uvRotateTop = 0;
                break;

            case WEST:
                renderBlocks.uvRotateTop = 1;
                break;

            default:
                break;
        }

        this.renderStandardBlock(tessellator, this.block.getBounds(), x, y, z);
        this.resetRenderBlocks();
        return true;
    }
}
