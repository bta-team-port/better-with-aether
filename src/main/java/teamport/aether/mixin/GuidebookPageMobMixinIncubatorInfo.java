package teamport.aether.mixin;

import net.minecraft.client.gui.guidebook.mobs.GuidebookPageMob;
import net.minecraft.client.render.Font;
import net.minecraft.client.render.TextureManager;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.AetherRecipes;
import teamport.aether.blocks.AetherBlocks;

@Mixin(value = GuidebookPageMob.class, remap = false)
public class GuidebookPageMobMixinIncubatorInfo {

    @Shadow @Final private Mob example;

    @Inject(method = "renderForeground",at=@At("TAIL"))
    public void drawIncubatorIcon(TextureManager re, Font fr, int x, int y, int mouseX, int mouseY, float partialTicks, CallbackInfo ci){
        if(!incubatorIsDiscovered())return;
        if(!AetherRecipes.INCUBATOR.isOutput(example.getClass())) return;
        ItemStack icon = AetherBlocks.INCUBATOR_IDLE.getDefaultStack();

        GuidebookPageMob gui = (GuidebookPageMob)(Object)this;

        GL11.glEnable(2929);
        ItemModelDispatcher.getInstance().getDispatch(icon).renderItemIntoGui(Tessellator.instance, fr, re, icon, 0, 0, 1.0F);
        GL11.glDisable(2929);

    }

    private boolean incubatorIsDiscovered() {
        return true;
    }
}
