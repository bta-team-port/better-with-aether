package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelCrossedSquares;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFlowerStackable;
import net.minecraft.core.util.helper.Side;

@Environment(EnvType.CLIENT)
public class BlockModelFlowerStackableAether<T extends BlockLogicFlowerStackable> extends BlockModelCrossedSquares<T> {
    public final IconCoordinate[] ICONS = new IconCoordinate[4];
    public final IconCoordinate[] ICONSRETRO = new IconCoordinate[4];


    public BlockModelFlowerStackableAether(Block<T> block, String baseTexture) {
        super(block);

        for (int i = 0; i < 4; ++i) {
            this.ICONSRETRO[i] = TextureRegistry.getTexture(baseTexture + i + "_retro");
            this.ICONS[i] = TextureRegistry.getTexture(baseTexture + i);
        }

    }

    public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int data) {
        int stackCount = BlockLogicFlowerStackable.getStackCount(data);
        if (isRetro()) {
            return stackCount > 3 ? this.ICONSRETRO[0] : this.ICONSRETRO[stackCount];
        }
        return stackCount > 3 ? this.ICONS[0] : this.ICONS[stackCount];
    }
}
