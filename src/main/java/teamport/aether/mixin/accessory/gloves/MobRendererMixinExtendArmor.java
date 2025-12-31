package teamport.aether.mixin.accessory.gloves;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.MobRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(value = MobRenderer.class, remap = false)
public abstract class MobRendererMixinExtendArmor {
    @Shadow
    @Final
    private Minecraft mc;
    @ModifyExpressionValue(method = "render(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/Mob;DDDFF)V", at = @At(value = "CONSTANT", args = "intValue=4"))
    private int replace4WithArmorSize(int value) {
        return this.mc.thePlayer.inventory.armorInventory.length;
    }
}
