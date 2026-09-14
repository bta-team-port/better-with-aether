package teamport.aether.mixin.gui.screens;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.paged.Page;
import net.minecraft.client.gui.paged.PageComponent;
import net.minecraft.client.gui.worldsettings.NodeComponent;
import net.minecraft.client.gui.worldsettings.ScreenWorldSettings;
import net.minecraft.client.gui.worldsettings.settingnode.CategoryComponent;
import net.minecraft.core.lang.text.TranslatableText;
import net.minecraft.core.world.Dimension;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.world.AetherDimension;

import java.util.Map;

@Environment(EnvType.CLIENT)
@Mixin(ScreenWorldSettings.class)
public abstract class ScreenWorldSettingsAddAetherMixin {

    @Shadow
    @Final
    @NonNull
    public static Page PAGE_WORLD_SETTINGS;

    @Shadow
    @Final
    private static @NonNull Map<@NonNull Dimension, @NonNull NodeComponent> WORLD_SETTINGS_ROOT_COMPONENTS;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void addAetherDimensionSettings(CallbackInfo ci) {
        NodeComponent aetherComponent = WORLD_SETTINGS_ROOT_COMPONENTS.get(AetherDimension.getAether());

        if (aetherComponent != null) {
            PAGE_WORLD_SETTINGS.withComponent(new CategoryComponent(new TranslatableText("gui.world_settings.page.world_settings.category.aether"))
                    .withComponent(aetherComponent)
            );
        }
    }
}
