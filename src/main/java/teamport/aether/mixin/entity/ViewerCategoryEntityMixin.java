package teamport.aether.mixin.entity;

import net.minecraft.client.gui.modelviewer.ScreenModelViewer;
import net.minecraft.client.gui.modelviewer.categories.ViewerCategoryEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.entity.animal.sheepuff.EntityEntrySheepuff;
import teamport.aether.entity.boss.slider.EntityEntryBossSlider;
import teamport.aether.entity.monster.sentry.EntityEntrySentry;
import teamport.aether.models.EntityEntryFloatingBlock;

@Mixin(value = ViewerCategoryEntity.class, remap = false)
public class ViewerCategoryEntityMixin {
    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/modelviewer/categories/ViewerCategoryEntity;createEntries()V", shift = At.Shift.AFTER))
    private void injectSheepuffEntry(ScreenModelViewer modelViewer, CallbackInfo ci) {
        ViewerCategoryEntity category = (ViewerCategoryEntity) (Object) this;
        category.addEntry(new EntityEntrySheepuff());
        category.addEntry(new EntityEntryBossSlider());
        category.addEntry(new EntityEntrySentry());
        category.addEntry(new EntityEntryFloatingBlock());
    }
}
