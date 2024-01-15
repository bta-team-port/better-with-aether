package bta.aether.mixin;

import net.minecraft.client.render.RenderBlocks;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = RenderBlocks.class, remap = false)
public class RenderBlocksMixin {
    @Shadow private World world;
    @Unique
    private int jarX;
    @Unique
    private int jarY;
    @Unique
    private int jarZ;
    @Inject(method = "renderBlockJar(Lnet/minecraft/core/block/Block;III)Z", at = @At("HEAD"))
    private void getTextureFromBlockProperly(Block block, int x, int y, int z, CallbackInfoReturnable<Boolean> cir){
        this.jarX = x;
        this.jarY = y;
        this.jarZ = z;
    }
    @Redirect(method = "renderBlockJar(Lnet/minecraft/core/block/Block;III)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/block/Block;getBlockTextureFromSideAndMetadata(Lnet/minecraft/core/util/helper/Side;I)I"))
    private int getTextureFromBlockProperly(Block instance, Side side, int data){
        return instance.getBlockTexture(world, jarX, jarY, jarZ, side);
    }
}
