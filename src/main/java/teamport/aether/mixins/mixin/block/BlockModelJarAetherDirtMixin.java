package teamport.aether.mixins.mixin.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.generic.BlockModelGeneric;
import net.minecraft.client.render.block.model.generic.BlockModelGenericJar;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityFlowerJar;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePosc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import teamport.aether.block.AetherBlockTags;

@Environment(EnvType.CLIENT)
@Mixin(BlockModelGeneric.class)
public abstract class BlockModelJarAetherDirtMixin {
    @Unique
    private static final IconCoordinate jarFullAether = TextureRegistry.getTexture("aether:block/jar_aether_dirt");

    @ModifyVariable(
        method = "renderAttached(Lnet/minecraft/client/render/tessellator/TessellatorGeneral;Lnet/minecraft/core/world/WorldSource;Lnet/minecraft/core/world/pos/TilePosc;ZLnet/minecraft/client/render/texture/stitcher/IconCoordinate;)Z",
        at = @At("HEAD"),
        argsOnly = true
    )
    private IconCoordinate modifyJarTexture(IconCoordinate originalTexIndex, TessellatorGeneral tessellator, WorldSource worldSource, TilePosc tilePos, boolean cullFaces, IconCoordinate overrideTexture) {
        if (!((Object) this instanceof BlockModelGenericJar<?>)) {
            return originalTexIndex;
        }

        int meta = worldSource.getBlockData(tilePos);
        if (meta == 0) {
            return originalTexIndex;
        }
        TileEntity tileEntity = worldSource.getTileEntity(tilePos);
        if (tileEntity instanceof TileEntityFlowerJar jarTe) {
            Block<?> block = Blocks.blocksList[jarTe.flowerInPot];
            if (block != null && block.hasTag(AetherBlockTags.PLANTABLE_IN_AETHER_JAR)) {
                return jarFullAether;
            }
        }
        return originalTexIndex;
    }
}
