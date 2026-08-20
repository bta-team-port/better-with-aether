package teamport.aether.mixin.accessory.cape.invisibility_cape.render;


import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.entity.MobRendererBipedArmored;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.renderer.State;
import net.minecraft.core.entity.IArmorWearing;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.enums.HumanArmorShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import teamport.aether.entity.player.PlayerUtil;

@Mixin(MobRendererBipedArmored.class)
public class MobRendererBipedArmoredMixin<T extends Mob & IArmorWearing<HumanArmorShape>> {

    @WrapOperation(method = "getAndSetupModelForLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/MobRendererBipedArmored;setupAnimations(Lnet/minecraft/core/entity/Mob;Lorg/useless/dragonfly/models/entity/StaticEntityModel;FI)Lorg/useless/dragonfly/models/entity/StaticEntityModel;"))
    private @Nullable StaticEntityModel adjustPlayerVisibility(
        MobRendererBipedArmored<T> instance,
        @NotNull T entity,
        @Nullable StaticEntityModel model,
        float partialTick, int layer,
        Operation<StaticEntityModel> original
    ){
        if(PlayerUtil.isInvisible(entity)){
            GLRenderer.enableState(State.BLEND);
            GLRenderer.setColor4f(1.0F, 1.0F, 1.0F, 0.15F);
        }
        return original.call(instance, entity, model, partialTick, layer);
    }

}
