package teamport.aether.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.Option;
import net.minecraft.client.option.OptionBoolean;
import net.minecraft.client.option.OptionRange;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.game_settings.AetherGameSettingsOptions;

@Environment(EnvType.CLIENT)
@Mixin(value = GameSettings.class, remap = false)
public abstract class GameSettingsMixin implements AetherGameSettingsOptions {
    @Shadow
    @Final
    public Minecraft mc;
    @Unique
    private final OptionBoolean flickAccessoryIconsOption = new OptionBoolean((GameSettings) (Object) this, "aether.flickAccessoryIcons", true);
    @SuppressWarnings("DataFlowIssue")
    @Unique
    private final OptionRange flickAccessorySpeed = new OptionRange((GameSettings) (Object) this, "aether.flickAccessorySpeed", 5, 1, 60);
    @Override
    public OptionBoolean aether$getFlickAccessoryIconsOption() {
        return flickAccessoryIconsOption;
    }
    @Override
    public OptionRange aether$getAccessoryFlickSpeed() {
        return flickAccessorySpeed;
    }
    @ModifyReturnValue(method = "getDisplayString", at = @At("RETURN"))
    private String changeDisplayString(String original, Option<?> option) {
        if (option == null) return "";
        if (option == flickAccessorySpeed) return option.value + " seconds";
        return original;
    }
}
