package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelCrossedSquares;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.helper.Side;
import teamport.aether.block.AetherBlocks;

@Environment(EnvType.CLIENT)
public class BlockModelAetherTallgrass<T extends BlockLogic> extends BlockModelCrossedSquares<T> {
    public BlockModelAetherTallgrass(Block<T> block) {
        super(block);
    }

    @Override
    public boolean render(Tessellator tessellator, int x, int y, int z) {
        float brightness = 1.0F;
        if (!LightmapHelper.isLightmapEnabled()) {
            brightness = this.getBlockBrightness(renderBlocks.blockAccess, x, y, z);
        } else {
            tessellator.setLightmapCoord(this.block.getLightmapCoord(renderBlocks.blockAccess, x, y, z));
        }

        int color = BlockColorDispatcher.getInstance().getDispatch(this.block).getWorldColor(renderBlocks.blockAccess, x, y, z);
        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;
        tessellator.setColorOpaque_F(brightness * r, brightness * g, brightness * b);
        double xd = x;
        double yd = y;
        double zd = z;
        if (this.block == AetherBlocks.TALLGRASS_AETHER) {
            long dRandom = x * 3129871L ^ z * 116129781L ^ y;
            dRandom = dRandom * dRandom * 42317861L + dRandom * 11L;
            xd += (((dRandom >> 16 & 15L) / 15.0F) - 0.5) * 0.5;
            yd += (((dRandom >> 20 & 15L) / 15.0F) - 1.0) * 0.2;
            zd += (((dRandom >> 24 & 15L) / 15.0F) - 0.5) * 0.5;
        }

        int metadata = renderBlocks.blockAccess.getBlockMetadata(x, y, z);
        IconCoordinate texIndex = this.getBlockTextureFromSideAndMetadata(Side.BOTTOM, metadata);
        if (renderBlocks.overrideBlockTexture != null) {
            texIndex = renderBlocks.overrideBlockTexture;
        }

        double minU = texIndex.getIconUMin();
        double maxU = texIndex.getIconUMax();
        double minV = texIndex.getIconVMin();
        double maxV = texIndex.getIconVMax();
        double minX = xd + 0.5 - 0.45;
        double maxX = xd + 0.5 + 0.45;
        double minZ = zd + 0.5 - 0.45;
        double maxZ = zd + 0.5 + 0.45;
        tessellator.addVertexWithUV(minX, yd + 1.0 + 0.0, minZ, minU, minV);
        tessellator.addVertexWithUV(minX, yd + 0.0, minZ, minU, maxV);
        tessellator.addVertexWithUV(maxX, yd + 0.0, maxZ, maxU, maxV);
        tessellator.addVertexWithUV(maxX, yd + 1.0 + 0.0, maxZ, maxU, minV);
        tessellator.addVertexWithUV(maxX, yd + 1.0 + 0.0, maxZ, minU, minV);
        tessellator.addVertexWithUV(maxX, yd + 0.0, maxZ, minU, maxV);
        tessellator.addVertexWithUV(minX, yd + 0.0, minZ, maxU, maxV);
        tessellator.addVertexWithUV(minX, yd + 1.0 + 0.0, minZ, maxU, minV);
        tessellator.addVertexWithUV(minX, yd + 1.0 + 0.0, maxZ, minU, minV);
        tessellator.addVertexWithUV(minX, yd + 0.0, maxZ, minU, maxV);
        tessellator.addVertexWithUV(maxX, yd + 0.0, minZ, maxU, maxV);
        tessellator.addVertexWithUV(maxX, yd + 1.0 + 0.0, minZ, maxU, minV);
        tessellator.addVertexWithUV(maxX, yd + 1.0 + 0.0, minZ, minU, minV);
        tessellator.addVertexWithUV(maxX, yd + 0.0, minZ, minU, maxV);
        tessellator.addVertexWithUV(minX, yd + 0.0, maxZ, maxU, maxV);
        tessellator.addVertexWithUV(minX, yd + 1.0 + 0.0, maxZ, maxU, minV);
        return true;
    }
}

