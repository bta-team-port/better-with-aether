package teamport.aether.mixin.player;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Mob.class)
public abstract class PlayerDeathMessageMixin {
    // TODO: remove the redirect
    @WrapOperation(method = "onDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;sendGlobalMessageTranslated(Lnet/minecraft/core/net/command/TextFormatting$Base;Ljava/lang/String;[Ljava/lang/String;)V"))
    private void sendAetherDeathMessages(
        World world,
        TextFormatting.Base base,
        String format,
        String[] args,
        Operation<Void> original,
        @Local(argsOnly = true) Entity entityKilledBy
    ) {
        Mob asThis = (Mob) (Object) this;
        original.call(world, base, format, args);

        String[] argsl;
        if(entityKilledBy == null){
            argsl = new String[]{TextFormatting.scoped(Entity.getNameFromEntity(asThis, true))};
        }else {
            argsl = new String[]{TextFormatting.scoped(Entity.getNameFromEntity(asThis, true)), TextFormatting.scoped(Entity.getNameFromEntity(entityKilledBy, true))};
        }

        world.sendGlobalMessageTranslated(TextFormatting.Base.RED, asThis.getDeathMessageKey(entityKilledBy), argsl);
    }
}


