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
import teamport.aether.block.AetherBlocks;

import java.util.EnumMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class BlockModelFreezer<T extends BlockLogic> extends BlockModelHorizontalRotation<T> {
    private final Map<Side, IconCoordinate> retroTextures = new EnumMap<>(Side.class);

    public BlockModelFreezer(Block<T> block) {
        super(block);
    }

    @Override
    public IconCoordinate getBlockTexture(WorldSource blockAccess, TilePosc pos, Side side) {
        int data = blockAccess.getBlockMetadata(pos.x(), pos.y(), pos.z());
        int index = Sides.orientationLookUpHorizontal[6 * Math.min(data, 5) + side.id];
        if (index >= Sides.orientationLookUpHorizontal.length) {
            return getSideTexture(Side.BOTTOM);
        }

        Side effectiveSide = Side.fromId(index);
        if (effectiveSide == Side.TOP) {
            IconCoordinate originalTop = getSideTexture(Side.TOP);
            Container container = (Container) blockAccess.getTileEntity(pos.x(), pos.y(), pos.z());
            if (container != null) {
                boolean hasOutput = container.getItem(2) != null;
                if (hasOutput) {
                    String namespace = originalTop.namespaceId.namespace();
                    String path = originalTop.namespaceId.value();
                    return TextureRegistry.getTexture(namespace + ":block/" + path + "_filled");
                }
                if (this.block == AetherBlocks.FREEZER_ACTIVE) {
                    String namespace = originalTop.namespaceId.namespace();
                    String path = originalTop.namespaceId.value();
                    return TextureRegistry.getTexture(namespace + ":block/" + path.replace("idle_top", "active_top"));
                }
            }
            return originalTop;
        } else {
            return getSideTexture(effectiveSide);
        }
    }

    private IconCoordinate getSideTexture(Side side) {
        if (isRetro()) {
            IconCoordinate retroTexture = retroTextures.get(side);
            if (retroTexture != null) return retroTexture;
        }
        IconCoordinate standardTexture = this.blockTextures.get(side);
        return standardTexture != null ? standardTexture : BLOCK_TEXTURE_UNASSIGNED;
    }

    public BlockModelFreezer<T> setRetroTex(String texture, Side... sides) {
        IconCoordinate coordinate = TextureRegistry.getTexture(texture);
        for (Side side : sides) retroTextures.put(side, coordinate);
        return this;
    }
}
