package teamport.aether.mixin.light;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.ducks.IBlockAether;

@Mixin(value = Block.class, remap = false)
public abstract class BlockMixin<T extends BlockLogic> implements IBlockAether {
    @Shadow
    public int emission;
    @Unique
    byte emissionOverride = 0;
    @Inject(method = "withLightEmission(I)Lnet/minecraft/core/block/Block;", at = @At("TAIL"))
    public void saveEmission1(int lightEmission, CallbackInfoReturnable<Block<T>> cir) {
        emissionOverride = (byte) this.emission;
    }
    @Inject(method = "withLightEmission(F)Lnet/minecraft/core/block/Block;", at = @At("TAIL"))
    public void saveEmission2(float lightEmission, CallbackInfoReturnable<Block<T>> cir) {
        emissionOverride = (byte) this.emission;
    }
    @Override
    public int better_with_aether$getEmissionOverride() {
        return emissionOverride;
    }
    @Override
    public void better_with_aether$setEmissionOverride(int emission) {
        this.emissionOverride = (byte) emission;
    }
}
