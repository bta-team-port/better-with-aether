package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderBlocks;
import net.minecraft.client.render.block.model.BlockModelGrass;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.util.helper.Axis;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.WorldSource;

import static teamport.aether.AetherMod.MOD_ID;

@Environment(EnvType.CLIENT)
public class BlockModelGrassAether<T extends BlockLogic> extends BlockModelStandard<T> {
    private final IconCoordinate snowSide = TextureRegistry.getTexture(MOD_ID + ":block/grass_aether/snowy_side");
    private final IconCoordinate snowSideRetro = TextureRegistry.getTexture(MOD_ID + ":block/grass_aether/snowy_side_retro");

    private static final IconCoordinate[] overlayIndices = new IconCoordinate[]{
        null, null,
        TextureRegistry.getTexture(MOD_ID + ":block/grass_aether/side_overlay"),
        TextureRegistry.getTexture(MOD_ID + ":block/grass_aether/side_overlay"),
        TextureRegistry.getTexture(MOD_ID + ":block/grass_aether/side_overlay"),
        TextureRegistry.getTexture(MOD_ID + ":block/grass_aether/side_overlay"),
    };

    public BlockModelGrassAether(Block<T> block) {
        super(block);
    }

    @Override
    public boolean render(Tessellator tessellator, int x, int y, int z) {
        boolean rendered = super.render(tessellator, x, y, z);

        if (RenderBlocks.fancyGrass && !this.isRetro()) {
            BlockModelGrass.useOverlay = true;
            rendered |= super.render(tessellator, x, y, z);
            BlockModelGrass.useOverlay = false;
        }

        return rendered;
    }

    @Override
    public IconCoordinate getBlockTexture(WorldSource world, int x, int y, int z, Side side) {
        Material above = world.getBlockMaterial(x, y + 1, z);
        boolean snowy = (above == Material.topSnow || above == Material.snow) && side.getAxis() != Axis.Y;

        if (snowy) {
            return isRetro() ? snowSideRetro : snowSide;
        }

        return super.getBlockTexture(world, x, y, z, side);
    }

    @Override
    public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int meta) {
        return BlockModelGrass.useOverlay ? overlayIndices[side.getId()] : super.getBlockTextureFromSideAndMetadata(side, meta);
    }

    @Override
    public boolean shouldSideBeColored(WorldSource world, int x, int y, int z, int sideId, int meta) {
        Material above = world.getBlockMaterial(x, y + 1, z);
        if (above == Material.topSnow || above == Material.snow) {
            return false;
        }
        return BlockModelGrass.useOverlay || sideId == Side.TOP.getId();
    }

    @Override
    public void renderBlockOnInventory(Tessellator tessellator, int metadata, float brightness, float alpha, Integer lightmapCoordinate) {
        super.renderBlockOnInventory(tessellator, metadata, brightness, alpha, lightmapCoordinate);

        if (RenderBlocks.fancyGrass && !this.isRetro()) {
            BlockModelGrass.useOverlay = true;
            super.renderBlockOnInventory(tessellator, metadata, brightness, alpha, lightmapCoordinate);
            BlockModelGrass.useOverlay = false;
        }
    }
}
