package teamport.aether.mixin.accessory.cape.invisibility_cape.render;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.model.ItemModelBlock;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.entity.player.PlayerUtil;

@Environment(EnvType.CLIENT)
@Mixin(value = ItemModelBlock.class, remap = false)
public abstract class MakeHeldBlockInvisible {
    @WrapMethod(method = "render(Lnet/minecraft/client/render/tessellator/TessellatorGeneral;Lnet/minecraft/core/entity/Entity;Lnet/minecraft/core/item/ItemStack;Ljava/lang/String;ZIBFZ)V")
    private void makeItemInvisible(TessellatorGeneral tessellator, Entity entity, ItemStack itemStack, String displayContext, boolean translate, int count, byte light, float partialTick, boolean leftHanded, Operation<Void> original) {
        if (entity instanceof Player
            && ("thirdperson_lefthand".equals(displayContext) || "thirdperson_righthand".equals(displayContext))
            && PlayerUtil.isInvisible(entity)) {
            return;
        }
        original.call(tessellator, entity, itemStack, displayContext, translate, count, light, partialTick, leftHanded);
    }
}
