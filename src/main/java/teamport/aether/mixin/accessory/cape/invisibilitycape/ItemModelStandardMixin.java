//package teamport.aether.mixin.accessory.cape.invisibilitycape;
//
//
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.render.item.model.ItemModelStandard;
//import net.minecraft.client.render.tessellator.Tessellator;
//import net.minecraft.core.entity.Entity;
//import net.minecraft.core.item.ItemStack;
//import org.lwjgl.opengl.GL11;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//import teamport.aether.items.accessory.AetherInvisibility;
//
//@Mixin(value = {ItemModelStandard.class}, remap = false)
//public abstract class ItemModelStandardMixin {
//
//
//    @Inject(method = "renderItemInWorld(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/Entity;Lnet/minecraft/core/item/ItemStack;FFZ)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/model/ItemModelStandard;getIcon(Lnet/minecraft/core/entity/Entity;Lnet/minecraft/core/item/ItemStack;)Lnet/minecraft/client/render/texture/stitcher/IconCoordinate;", shift = At.Shift.BEFORE))
//    public void injectModelStandard(Tessellator tessellator, Entity entity, ItemStack itemStack, float brightness, float alpha, boolean worldTransform, CallbackInfo ci){
//        if (((AetherInvisibility) entity).aether$isInvisible() && ((AetherInvisibility) Minecraft.getMinecraft().thePlayer).aether$isInvisible()) {
//            GL11.glColor4f(0.5F, 0.5F, 0.5F, 0.15F);
//        }
//    }
//}
