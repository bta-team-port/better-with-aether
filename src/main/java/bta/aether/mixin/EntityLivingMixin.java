package bta.aether.mixin;

import bta.aether.item.ToolMaterialAether;
import bta.aether.mixin.accessors.ItemToolSwordAccessor;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemTool;
import net.minecraft.core.item.tool.ItemToolSword;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityLiving.class, remap = false)
public abstract class EntityLivingMixin extends Entity {
    @Shadow protected abstract void dropFewItems();

    public EntityLivingMixin(World world) {
        super(world);
    }
    @Inject(method = "onDeath(Lnet/minecraft/core/entity/Entity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/EntityLiving;dropFewItems()V"))
    private void multiplyDrop(Entity entity, CallbackInfo ci){
        if (entity instanceof EntityPlayer){
            ItemStack heldStack = ((EntityPlayer) entity).getHeldItem();
            if (heldStack != null && heldStack.getItem() instanceof ItemToolSword && ((ItemToolSwordAccessor) heldStack.getItem()).getMaterial() instanceof ToolMaterialAether){
                int dropMultiplier = ((ToolMaterialAether)((ItemToolSwordAccessor) heldStack.getItem()).getMaterial()).getDropMultipier() - 1; // Acounts for the base dropFewItems Call
                for (int i = 0; i < dropMultiplier; i++) {
                    dropFewItems();
                }
            }
        }
    }
}
