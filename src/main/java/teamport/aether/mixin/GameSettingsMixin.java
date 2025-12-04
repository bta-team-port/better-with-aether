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
    @SuppressWarnings("DataFlowIssue")
    @Unique
    private final OptionRange flickAccessorySpeed = new OptionRange((GameSettings) (Object) this, "aether.flickAccessorySpeed", 5, 0, 60);
    @Override
    public OptionRange aether$getAccessoryFlickSpeed() {
        return flickAccessorySpeed;
    }
    @ModifyReturnValue(method = "getDisplayString", at = @At("RETURN"))
    private String changeDisplayString(String original, Option<?> option) {
        if (option == null) return "";
        if (option == flickAccessorySpeed){
            int speed = Integer.parseInt(option.value.toString());
            if(speed == 0) return "OFF";
            return option.value + " seconds";
        }
        return original;
    }
}
