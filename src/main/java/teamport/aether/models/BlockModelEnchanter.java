package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelHorizontalRotation;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.helper.Sides;
import net.minecraft.core.world.WorldSource;

@Environment(EnvType.CLIENT)
public class BlockModelEnchanter<T extends BlockLogic> extends BlockModelHorizontalRotation<T> {
    public BlockModelEnchanter(Block<T> block) {
        super(block);
    }

    @Override
    public IconCoordinate getBlockTexture(WorldSource blockAccess, int x, int y, int z, Side side) {
        int data = blockAccess.getBlockMetadata(x, y, z);
        int index = Sides.orientationLookUpHorizontal[6 * Math.min(data, 5) + side.getId()];
        if (index >= Sides.orientationLookUpHorizontal.length) {
            if (isRetro()) {
                return this.retroBlockTextures.get(Side.BOTTOM);
            }
            return this.blockTextures.get(Side.BOTTOM);
        } else if (index == Side.NORTH.getId()) {
            IconCoordinate originalFront;
            if (isRetro()) {
                originalFront = this.retroBlockTextures.get(Side.NORTH);
            } else {
                originalFront = this.blockTextures.get(Side.NORTH);
            }
            Container container = (Container) blockAccess.getTileEntity(x, y, z);
            if (container != null) {
                boolean hasOutput = container.getItem(2) != null;
                if (hasOutput && originalFront != null) {
                    return TextureRegistry.getTexture(originalFront.namespaceId.namespace() + ":block/" + originalFront.namespaceId.value() + "_filled");
                }
            }

            return originalFront;
        } else {
            if (isRetro()) {
                return this.retroBlockTextures.get(Side.getSideById(index));
            }
            return this.blockTextures.get(Side.getSideById(index));
        }
    }
}
