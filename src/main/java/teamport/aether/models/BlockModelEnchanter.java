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
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;

import java.util.EnumMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class BlockModelEnchanter<T extends BlockLogic> extends BlockModelHorizontalRotation<T> {
    private final Map<Side, IconCoordinate> retroTextures = new EnumMap<>(Side.class);

    public BlockModelEnchanter(Block<T> block) {
        super(block);
    }

    @Override
    public IconCoordinate getBlockTexture(@NonNull WorldSource blockAccess, @NonNull TilePosc pos, @NonNull Side side) {
        int data = blockAccess.getBlockData(pos);
        int index = Sides.orientationLookUpHorizontal[6 * Math.min(data, 5) + side.id];
        if (index >= Sides.orientationLookUpHorizontal.length) {
            return this.blockTextures.get(Side.BOTTOM);
        } else if (index == Side.NORTH.id) {
            IconCoordinate originalFront = isRetro() ? retroTextures.get(Side.NORTH) : this.blockTextures.get(Side.NORTH);
            Container container = (Container) blockAccess.getTileEntity(pos);
            if (container != null) {
                boolean hasOutput = container.getItem(2) != null;
                if (hasOutput && originalFront != null) {
                    return TextureRegistry.getTexture(originalFront.namespaceId.namespace() + ":block/" + originalFront.namespaceId.value() + "_filled");
                }
            }

            return originalFront;
        } else {
            Side effectiveSide = Side.fromId(index);
            IconCoordinate retroTexture = retroTextures.get(effectiveSide);
            return isRetro() && retroTexture != null ? retroTexture : this.blockTextures.get(effectiveSide);
        }
    }

    public BlockModelEnchanter<T> setRetroTex(String texture, Side @NonNull ... sides) {
        IconCoordinate coordinate = TextureRegistry.getTexture(texture);
        for (Side side : sides) retroTextures.put(side, coordinate);
        return this;
    }
}
