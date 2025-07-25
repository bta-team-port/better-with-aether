package teamport.aether.mixin.accessory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.MobRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = MobRenderer.class, remap = false)
public class MobRendererMixinExtendArmor {
    @Shadow @Final private Minecraft mc;

    @ModifyConstant(method = "render(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/Mob;DDDFF)V", constant = @Constant(intValue = 4))
    private int replace4WithArmorSize(int value) {
        return this.mc.thePlayer.inventory.armorInventory.length;
    }

}
