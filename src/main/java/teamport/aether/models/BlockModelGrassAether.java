package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelStandard;
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

    public BlockModelGrassAether(Block<T> block) {
        super(block);
    }

    @Override
    public IconCoordinate getBlockTexture(WorldSource blockAccess, int x, int y, int z, Side side) {
        Material material = blockAccess.getBlockMaterial(x, y + 1, z);
        boolean isSnow = (material == Material.topSnow || material == Material.snow) && side.getAxis() != Axis.Y;
        if (isRetro()) {
            return isSnow ? this.snowSideRetro : super.getBlockTexture(blockAccess, x, y, z, side);
        }
        return isSnow ? this.snowSide : super.getBlockTexture(blockAccess, x, y, z, side);
    }
}
