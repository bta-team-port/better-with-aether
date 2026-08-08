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
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;

import java.util.EnumMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class BlockModelIncubator<T extends BlockLogic> extends BlockModelStandard<T> {
    private final Map<Side, IconCoordinate> retroTextures = new EnumMap<>(Side.class);
    private IconCoordinate topFilled;
    private IconCoordinate retroTopFilled;

    public BlockModelIncubator(Block<T> block) {
        super(block);
    }

    @Override
    public IconCoordinate getBlockTexture(@NonNull WorldSource blockAccess, @NonNull TilePosc pos, @NonNull Side side) {
        if (side.id == Side.TOP.id) {
            boolean retro = isRetro();
            IconCoordinate texture = retro ? retroTextures.get(Side.TOP) : this.blockTextures.get(Side.TOP);
            Container container = (Container) blockAccess.getTileEntity(pos);
            if (container != null && container.getItem(0) != null) {
                IconCoordinate filled = retro ? retroTopFilled : topFilled;
                if (filled != null) return filled;
            }
            return texture;
        }
        IconCoordinate retroTexture = retroTextures.get(side);
        return isRetro() && retroTexture != null ? retroTexture : this.blockTextures.get(side.id);
    }

    public BlockModelIncubator<T> setRetroTex(String texture, Side @NonNull ... sides) {
        IconCoordinate coordinate = TextureRegistry.getTexture(texture);
        for (Side side : sides) retroTextures.put(side, coordinate);
        return this;
    }

    public BlockModelIncubator<T> setTopFilled(String texture) {
        this.topFilled = TextureRegistry.getTexture(texture);
        return this;
    }

    public BlockModelIncubator<T> setRetroTopFilled(String texture) {
        this.retroTopFilled = TextureRegistry.getTexture(texture);
        return this;
    }
}
