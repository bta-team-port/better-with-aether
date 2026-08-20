package teamport.aether.mixin.accessory.cape.invisibility_cape.render;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.entity.player.PlayerUtil;

@Environment(EnvType.CLIENT)
@Mixin(ItemModelStandard.class)
public abstract class MakeHeldItemInvisible {
    @WrapMethod(method = "render(Lnet/minecraft/client/render/tessellator/TessellatorGeneral;Lnet/minecraft/core/entity/Entity;Lnet/minecraft/core/item/ItemStack;Ljava/lang/String;ZIBFZ)V")
    @SuppressWarnings("java:S107")
    private void makeItemInvisible(TessellatorGeneral tessellator, Entity holder, ItemStack itemStack, String displayPosId, boolean items3d, int clusterSize, byte lightIndex, float partialTick, boolean leftHanded, Operation<Void> original) {
        if (holder instanceof Player
            && (holder != Minecraft.getMinecraft().thePlayer || GameSettings.THIRD_PERSON_VIEW.value != 0)
            && PlayerUtil.isInvisible(holder)) {
            return;
        }
        original.call(tessellator, holder, itemStack, displayPosId, items3d, clusterSize, lightIndex, partialTick, leftHanded);
    }
}
