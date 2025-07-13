package teamport.aether.mixin.item;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.items.AetherToolMaterial;
import teamport.aether.mixin.accessors.ItemToolSwordAccessor;

@Mixin(value = Mob.class, remap = false)
public abstract class SkyrootSwordDropMultiplier {

    @Shadow
    protected abstract void dropDeathItems();

    @Inject(method = "onDeath(Lnet/minecraft/core/entity/Entity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Mob;dropDeathItems()V"))
    public void multiplyDrop(Entity entity, CallbackInfo ci){
        if (!(entity instanceof Player)) {
            return;
        }
        ItemStack heldStack = ((Player) entity).getHeldItem();
        if (heldStack == null || !(heldStack.getItem() instanceof ItemToolSword)) {
            return;
        }
        ToolMaterial material = ((ItemToolSwordAccessor) heldStack.getItem()).getMaterial();
        if (material != null && material == AetherToolMaterial.SKYROOT) {
            dropDeathItems();
        }
        dropDeathItems();
    }
}
