package bta.aether.mixin;

import net.minecraft.client.entity.fx.EntityFX;
import net.minecraft.client.entity.fx.EntityFireflyFX;
import net.minecraft.core.enums.EnumFireflyColor;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityFireflyFX.class, remap = false)
public class EntityFireflyFXMixin extends EntityFX {
    @Shadow
    float midR;

    @Shadow
    float midG;

    @Shadow
    float midB;

    @Shadow
    float maxR;

    @Shadow
    float maxG;

    @Shadow
    float maxB;

    public EntityFireflyFXMixin(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(world, x, y, z, motionX, motionY, motionZ);
    }

    @Inject(method = "setColour(I)V", at = @At("HEAD"), cancellable = true)
    private void setNewColor(int type, CallbackInfo ci){
        if (type == EnumFireflyColor.SILVER.getId()){
            this.midR = 0.3f;
            this.midG = 0.9f;
            this.midB = 0.7f;
            this.particleRed = this.midR;
            this.particleGreen = this.midG;
            this.particleBlue = this.midB;
            this.maxR = MathHelper.clamp(this.midR + 0.25f, 0.0f, 1.0f);
            this.maxG = MathHelper.clamp(this.midG + 0.25f, 0.0f, 1.0f);
            this.maxB = MathHelper.clamp(this.midB + 0.25f, 0.0f, 1.0f);
            ci.cancel();
        }
    }
}
