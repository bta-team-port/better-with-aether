package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelCrossedSquares;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePosc;

@Environment(EnvType.CLIENT)
public class BlockModelAetherTallgrass<T extends BlockLogic> extends BlockModelCrossedSquares<T> {
    private final IconCoordinate retroTexture;

    public BlockModelAetherTallgrass(Block<T> block) {
        super(block);
        this.retroTexture = TextureRegistry.getTexture("aether:block/tallgrass_aether_retro");
    }

    @Override
    public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int data) {
        return isRetro() ? retroTexture : super.getBlockTextureFromSideAndMetadata(side, data);
    }

    @Override
    public boolean render(net.minecraft.client.render.tessellator.TessellatorGeneral tessellator,
                          WorldSource world, TilePosc pos) {
        if (this.block.getLogic() instanceof teamport.aether.block.terrain.BlockLogicTallGrassAether) {
            long random = pos.x() * 3129871L ^ pos.z() * 116129781L ^ pos.y();
            random = random * random * 42317861L + random * 11L;
            double offsetX = (((random >> 16 & 15L) / 15.0F) - 0.5) * 0.5;
            double offsetY = (((random >> 20 & 15L) / 15.0F) - 1.0) * 0.2;
            double offsetZ = (((random >> 24 & 15L) / 15.0F) - 0.5) * 0.5;
            tessellator.offsetTranslation(offsetX, offsetY, offsetZ);
            try {
                return super.render(tessellator, world, pos);
            } finally {
                tessellator.offsetTranslation(-offsetX, -offsetY, -offsetZ);
            }
        }
        return super.render(tessellator, world, pos);
    }
}
