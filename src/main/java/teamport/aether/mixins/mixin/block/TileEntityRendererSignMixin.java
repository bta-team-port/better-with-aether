package teamport.aether.mixins.mixin.block;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.client.render.tileentity.TileEntityRenderer;
import net.minecraft.client.render.tileentity.TileEntityRendererSign;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.IPainted;
import net.minecraft.core.block.entity.TileEntitySign;
import net.minecraft.core.util.helper.DyeColor;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.block.skyroot.BlockLogicPaintableSignSkyroot;
import teamport.aether.block.skyroot.BlockLogicPaintedSignSkyroot;

@Environment(EnvType.CLIENT)
@Mixin(TileEntityRendererSign.class)
public abstract class TileEntityRendererSignMixin extends TileEntityRenderer<TileEntitySign> {

    @Unique
    private static final String[] signSkyrootColorTextures = new String[16];

    static {
        for (int i = 0; i < 16; ++i) {
            DyeColor dye = DyeColor.colorFromBlockMeta(i);
            signSkyrootColorTextures[i] = "/assets/aether/textures/entity/sign_skyroot/" + dye.colorID + ".png";
        }
    }

    @WrapOperation(method = "doRender(Lnet/minecraft/client/render/tessellator/TessellatorGeneral;Lnet/minecraft/core/block/entity/TileEntitySign;DDDF)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tileentity/TileEntityRendererSign;bindTexture(Ljava/lang/String;)V"))
    private void wrapSignTextureLoad(TileEntityRendererSign renderer, String originalTexture, Operation<Void> original, TessellatorGeneral t, @NonNull TileEntitySign tileEntity, double x, double y, double z, float partialTick) {
        Block<?> block = tileEntity.getBlock();
        int meta = tileEntity.getBlockMeta();
        String newTexture = null;

        if (Block.hasLogicClass(block, BlockLogicPaintedSignSkyroot.class)) {
            DyeColor dye = ((IPainted) block.getLogic()).fromMetadata(meta);
            newTexture = signSkyrootColorTextures[dye.blockMeta];
        } else if (Block.hasLogicClass(block, BlockLogicPaintableSignSkyroot.class)) {
            newTexture = "/assets/aether/textures/entity/sign_skyroot.png";
        }

        String textureToUse = (newTexture != null) ? newTexture : originalTexture;
        original.call(renderer, textureToUse);
    }
}
