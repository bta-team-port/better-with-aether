package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderBlocks;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.util.helper.Axis;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.WorldSource;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class BlockModelGrassAether<T extends BlockLogic> extends BlockModelStandard<T> {
    public static boolean useOverlay = false;
    private static final IconCoordinate[] overlayIndices = new IconCoordinate[]{
        null,
        null,
        TextureRegistry.getTexture("aether:block/grass_aether/side_overlay"),
        TextureRegistry.getTexture("aether:block/grass_aether/side_overlay"),
        TextureRegistry.getTexture("aether:block/grass_aether/side_overlay"),
        TextureRegistry.getTexture("aether:block/grass_aether/side_overlay")};
    protected IconCoordinate snowSide = TextureRegistry.getTexture("aether:block/grass_aether/snowy_side");
    protected IconCoordinate retroSnowSide = TextureRegistry.getTexture("aether:block/grass_aether/snowy_side_retro");

    public BlockModelGrassAether(Block<T> block) {
        super(block);
    }

    @Override
    public boolean render(Tessellator tessellator, int x, int y, int z) {
        AABB bounds = this.block.getBounds();
        boolean didRender = this.isRetro() ? this.renderStandardBlock(tessellator, bounds, x, y, z, 1.0F, 1.0F, 1.0F) : this.renderStandardBlock(tessellator, bounds, x, y, z);
        if (RenderBlocks.fancyGrass && (!this.retroBlockTextures.hasTexture() || !this.isRetro())) {
            useOverlay = true;
            didRender |= this.renderStandardBlock(tessellator, bounds, x, y, z);
            useOverlay = false;
        }

        return didRender;
    }

    @Override
    public void renderBlockOnInventory(Tessellator tessellator, int metadata, float brightness, float alpha, @Nullable Integer lightmapCoordinate) {
        GL11.glColor4f(brightness, brightness, brightness, alpha);
        float yOffset = 0.5F;
        AABB bounds = this.getBlockBoundsForItemRender();
        GL11.glTranslatef(-0.5F, 0.0F - yOffset, -0.5F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        this.renderBottomFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.getBlockTextureFromSideAndMetadata(Side.BOTTOM, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        this.renderNorthFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.getBlockTextureFromSideAndMetadata(Side.NORTH, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        this.renderSouthFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.getBlockTextureFromSideAndMetadata(Side.SOUTH, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        this.renderWestFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.getBlockTextureFromSideAndMetadata(Side.WEST, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        this.renderEastFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.getBlockTextureFromSideAndMetadata(Side.EAST, metadata));
        tessellator.draw();
        if (renderBlocks.useInventoryTint && !this.isRetro()) {
            int l = BlockColorDispatcher.getInstance().getDispatch(this.block).getFallbackColor(metadata);
            float f4 = (l >> 16 & 255) / 255.0F;
            float f8 = (l >> 8 & 255) / 255.0F;
            float f9 = (l & 255) / 255.0F;
            GL11.glColor4f(f4 * brightness, f8 * brightness, f9 * brightness, alpha);
        }

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        this.renderTopFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.getBlockTextureFromSideAndMetadata(Side.TOP, metadata));
        tessellator.draw();
        if (RenderBlocks.fancyGrass && !this.isRetro()) {
            useOverlay = true;
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, -1.0F);
            this.renderNorthFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.getBlockTextureFromSideAndMetadata(Side.NORTH, metadata));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, 1.0F);
            this.renderSouthFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.getBlockTextureFromSideAndMetadata(Side.SOUTH, metadata));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(-1.0F, 0.0F, 0.0F);
            this.renderWestFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.getBlockTextureFromSideAndMetadata(Side.WEST, metadata));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            this.renderEastFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.getBlockTextureFromSideAndMetadata(Side.EAST, metadata));
            tessellator.draw();
            useOverlay = false;
        }

        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

    @Override
    public IconCoordinate getBlockTexture(WorldSource blockAccess, int x, int y, int z, Side side) {
        Material above = blockAccess.getBlockMaterial(x, y + 1, z);
        boolean isSnowy = (above == Material.topSnow || above == Material.snow);

        if (isSnowy && side.getAxis() != Axis.Y) {
            return this.isRetro() ? retroSnowSide : snowSide;
        }

        return super.getBlockTexture(blockAccess, x, y, z, side);
    }

    @Override
    public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int data) {
        return useOverlay ? overlayIndices[side.getId()] : super.getBlockTextureFromSideAndMetadata(side, data);
    }

    @Override
    public boolean shouldSideBeColored(WorldSource blockAccess, int x, int y, int z, int side, int meta) {
        Material material = blockAccess.getBlockMaterial(x, y + 1, z);
        if (material != Material.topSnow && material != Material.snow) {
            return useOverlay || side == Side.TOP.getId();
        } else {
            return false;
        }
    }
}

