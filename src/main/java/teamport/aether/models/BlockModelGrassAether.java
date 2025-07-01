package teamport.aether.models;

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

import static teamport.aether.AetherMod.MOD_ID;

public class BlockModelGrassAether<T extends BlockLogic> extends BlockModelStandard<T> {
    protected IconCoordinate snowSide = TextureRegistry.getTexture(MOD_ID + ":block/grass_aether/snowy_side");

    public BlockModelGrassAether(Block<T> block) {
        super(block);
    }

    public boolean render(Tessellator tessellator, int x, int y, int z) {
        AABB bounds = this.block.getBounds();

        return this.renderStandardBlock(tessellator, bounds, x, y, z, 1.0F, 1.0F, 1.0F);
    }

    public void renderBlockOnInventory(Tessellator tessellator, int metadata, float brightness, float alpha, @Nullable Integer lightmapCoordinate) {
        GL11.glColor4f(brightness, brightness, brightness, alpha);
        float yOffset = 0.5F;
        AABB bounds = this.getBlockBoundsForItemRender();
        GL11.glTranslatef(-0.5F, 0.0F - yOffset, -0.5F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        this.renderBottomFace(tessellator, bounds, 0.0, 0.0, 0.0, this.getBlockTextureFromSideAndMetadata(Side.BOTTOM, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        this.renderNorthFace(tessellator, bounds, 0.0, 0.0, 0.0, this.getBlockTextureFromSideAndMetadata(Side.NORTH, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        this.renderSouthFace(tessellator, bounds, 0.0, 0.0, 0.0, this.getBlockTextureFromSideAndMetadata(Side.SOUTH, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        this.renderWestFace(tessellator, bounds, 0.0, 0.0, 0.0, this.getBlockTextureFromSideAndMetadata(Side.WEST, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        this.renderEastFace(tessellator, bounds, 0.0, 0.0, 0.0, this.getBlockTextureFromSideAndMetadata(Side.EAST, metadata));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        this.renderTopFace(tessellator, bounds, 0.0, 0.0, 0.0, this.getBlockTextureFromSideAndMetadata(Side.TOP, metadata));
        tessellator.draw();

        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

    public IconCoordinate getBlockTexture(WorldSource blockAccess, int x, int y, int z, Side side) {
        Material material = blockAccess.getBlockMaterial(x, y + 1, z);
        return (material == Material.topSnow || material == Material.snow) && side.getAxis() != Axis.Y ? this.snowSide : super.getBlockTexture(blockAccess, x, y, z, side);
    }
}
