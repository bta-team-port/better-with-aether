package teamport.aether.mixin.gui.screens;

import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.ScreenSelectWorld;
import net.minecraft.core.world.save.SaveFile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.gui.UNDataMissingScreen;
import teamport.aether.world.AetherDimension;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Mixin(value = ScreenSelectWorld.class, remap = false, priority = 0)
public class UNWarnMixin extends Screen {

    @Shadow
    private List<SaveFile> saveList;

    @Shadow
    private boolean selected;

    @Shadow
    private int selectedWorld;

    @Inject(method = "selectWorld", at = @At("HEAD"), cancellable = true)
    private void throwWarning(int i, CallbackInfo ci) {
        SaveFile save = this.saveList.get(i);

        Path savePath = Paths.get(mc.getMinecraftDir().toString(), "saves", save.getFileName());

        if (!savePath.resolve("uselessNumericalSave.dat").toFile().exists()
                && savePath.resolve("dimensions/" + AetherDimension.AetherDimensionID + "/").toFile().exists()
        ) {
            this.selected = false;
            this.selectedWorld = -1;
            mc.displayScreen(new UNDataMissingScreen(this, save));
            ci.cancel();
        }
    }
}
