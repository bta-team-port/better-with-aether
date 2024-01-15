package bta.aether.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.LivingRenderer;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Debug(export = true)
@Mixin(value = LivingRenderer.class, remap = false)
public class LivingRendererMixin {

    @Shadow @Final private Minecraft mc;

    @ModifyConstant(method = "render(Lnet/minecraft/core/entity/EntityLiving;DDDFF)V", constant = @Constant(intValue = 4))
    private int replace4WithArmorSize(int value) {
        return this.mc.thePlayer.inventory.armorInventory.length;
    }
}
