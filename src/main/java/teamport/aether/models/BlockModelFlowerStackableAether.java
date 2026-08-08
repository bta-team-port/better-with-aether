package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelCrossedSquares;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFlowerStackable;
import net.minecraft.core.util.helper.Side;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class BlockModelFlowerStackableAether<T extends BlockLogicFlowerStackable> extends BlockModelCrossedSquares<T> {
    private final IconCoordinate[] icons = new IconCoordinate[4];
    private final IconCoordinate[] iconsRetro = new IconCoordinate[4];


    public BlockModelFlowerStackableAether(Block<T> block, String baseTexture) {
        super(block);

        for (int i = 0; i < 4; ++i) {
            this.iconsRetro[i] = TextureRegistry.getTexture(baseTexture + i + "_retro");
            this.icons[i] = TextureRegistry.getTexture(baseTexture + i);
        }

    }

    @Override
    public IconCoordinate getBlockTextureFromSideAndMetadata(@NonNull Side side, int data) {
        int stackCount = BlockLogicFlowerStackable.getStackCount(data);
        if (isRetro()) {
            return stackCount > 3 ? this.iconsRetro[0] : this.iconsRetro[stackCount];
        }
        return stackCount > 3 ? this.icons[0] : this.icons[stackCount];
    }
}
