package bta.aether.mixin;

import bta.aether.catalyst.effects.AetherEffects;
import bta.aether.item.ToolMaterialAether;
import bta.aether.entity.IAetherEntityLiving;
import bta.aether.mixin.accessors.ItemToolSwordAccessor;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolSword;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sunsetsatellite.catalyst.effects.api.effect.EffectStack;
import sunsetsatellite.catalyst.effects.api.effect.IHasEffects;

@Mixin(value = EntityLiving.class, remap = false)
public abstract class EntityLivingMixin extends Entity implements IAetherEntityLiving {
    @Unique
    public double poisonSlideX = 0.0;
    @Unique
    public double poisonSlideZ = 0.0;

    @Unique
    public void setPoisonSlide(double x, double z) {
        poisonSlideX = x;
        poisonSlideZ = z;
    }

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

    @Inject(method = "tick()V", at = @At("TAIL"))
    public void tick(CallbackInfo ci) {
        IHasEffects entityEffects = (IHasEffects) this;

        for (EffectStack effect : entityEffects.getContainer().getEffects()) {
            if (effect.getEffect() == AetherEffects.poisonEffect) {
                double timeLeft = effect.getTimeLeft();
                if (effect.getAmount() <= 0) return;
                double slideX = (poisonSlideX/200.0) * timeLeft;
                double slideZ = (poisonSlideZ/200.0) * timeLeft;
                this.move(slideX, -0.001, slideZ);
            }
        }
    }
}
