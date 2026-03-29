package teamport.aether.mixin.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.modelviewer.ScreenModelViewer;
import net.minecraft.client.gui.modelviewer.categories.ViewerCategoryEntity;
import net.minecraft.client.gui.modelviewer.categories.entries.entity.EntityEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.entity.animal.sheepuff.EntityEntrySheepuff;
import teamport.aether.entity.boss.slider.EntityEntryBossSlider;
import teamport.aether.entity.monster.mimic.EntityEntryMimic;
import teamport.aether.entity.monster.sentry.EntityEntrySentry;
import teamport.aether.models.EntityEntryFloatingBlock;

@Environment(EnvType.CLIENT)
@Mixin(value = ViewerCategoryEntity.class)
public abstract class ViewerCategoryEntityMixin {
    @Shadow
    public abstract void addEntry(EntityEntry<?> entry);
    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectSheepuffEntry(ScreenModelViewer modelViewer, CallbackInfo ci) {
        addEntry(new EntityEntrySheepuff());
        addEntry(new EntityEntryBossSlider());
        addEntry(new EntityEntrySentry());
        addEntry(new EntityEntryFloatingBlock());
        addEntry(new EntityEntryMimic());
    }
}
