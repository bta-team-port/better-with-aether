package teamport.aether.mixin.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelJar;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityFlowerJar;
import net.minecraft.core.world.WorldSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import teamport.aether.blocks.AetherBlockTags;

import static net.minecraft.client.render.block.model.BlockModel.renderBlocks;

@Environment(EnvType.CLIENT)
@Mixin(value = BlockModelJar.class, remap = false)
public abstract class BlockModelJarAetherDirtMixin {
    @Unique
    private static final IconCoordinate jarFullAether = TextureRegistry.getTexture("aether:block/jar_aether_dirt");
    @ModifyVariable(method = "render(Lnet/minecraft/client/render/tessellator/Tessellator;III)Z", at = @At(value = "STORE", ordinal = 0))
    private IconCoordinate modifyJarTexture(IconCoordinate originalTexIndex, Tessellator tessellator, int x, int y, int z) {
        WorldSource blockAccess = renderBlocks.blockAccess;
        int meta = blockAccess.getBlockMetadata(x, y, z);
        if (meta == 0) {
            return originalTexIndex;
        }
        TileEntity tileEntity = blockAccess.getTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityFlowerJar) {
            TileEntityFlowerJar jarTe = (TileEntityFlowerJar) tileEntity;
            Block<?> block = Blocks.blocksList[jarTe.flowerInPot];
            if (block != null && block.hasTag(AetherBlockTags.PLANTABLE_IN_AETHER_JAR)) {
                return jarFullAether;
            }
        }
        return originalTexIndex;
    }
}
