package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.phys.AABB;
import org.jspecify.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class BlockModelAetherStoneMossy<T extends BlockLogic> extends BlockModelStandard<T> {
    protected IconCoordinate mossOverlay = TextureRegistry.getTexture("aether:block/moss_overlay");

    public BlockModelAetherStoneMossy(Block<T> block) {
        super(block);
    }

    @Override
    public boolean render(Tessellator tessellator, int x, int y, int z) {
        AABB bounds = this.block.getBlockBoundsFromState(renderBlocks.blockAccess, x, y, z);
        this.renderStandardBlock(tessellator, bounds, x, y, z, 1.0F, 1.0F, 1.0F);
        renderBlocks.overrideBlockTexture = this.mossOverlay;
        this.renderStandardBlock(tessellator, bounds, x, y, z);
        renderBlocks.overrideBlockTexture = null;
        return true;
    }

    @Override
    public void renderBlockOnInventory(Tessellator tessellator, int metadata, float brightness, float alpha, @Nullable Integer lightmapCoordinate) {
        renderBlocks.useInventoryTint = false;
        super.renderBlockOnInventory(tessellator, metadata, brightness, alpha, lightmapCoordinate);
        renderBlocks.useInventoryTint = true;
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        int color = (BlockColorDispatcher.getInstance().getDispatch(this.block)).getFallbackColor(metadata);
        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;
        GL11.glColor4f(r * brightness, g * brightness, b * brightness, alpha);
        AABB bounds = this.block.getBounds();
        IconCoordinate mossCoord = this.mossOverlay;
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        this.renderBottomFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, mossCoord);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        this.renderTopFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, mossCoord);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        this.renderNorthFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, mossCoord);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        this.renderSouthFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, mossCoord);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        this.renderWestFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, mossCoord);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        this.renderEastFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, mossCoord);
        tessellator.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }
}
