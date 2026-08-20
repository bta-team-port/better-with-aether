package teamport.aether.mixins.mixin.gui.screens;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.ScreenSelectWorld;
import net.minecraft.core.world.save.SaveFile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import teamport.aether.gui.UNDataMissingScreen;
import teamport.aether.world.AetherDimension;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(value = ScreenSelectWorld.class, priority = 0)
public abstract class UNWarnMixin extends Screen {
    @Shadow
    private List<SaveFile> saveList;
    @Shadow
    private boolean selected;
    @Shadow
    private int selectedWorld;

    @WrapMethod(method = "selectWorld")
    private void aether$warnAboutMissingUselessNumericalData(int i, Operation<Void> original) {
        SaveFile save = this.saveList.get(i);
        Path savePath = this.mc.getMinecraftDir().toPath().resolve("saves").resolve(save.getFileName());
        Path aetherDimension = savePath.resolve("dimensions").resolve(Integer.toString(AetherDimension.getAether().id));

        if (Files.notExists(savePath.resolve("uselessNumericalSave.dat")) && Files.exists(aetherDimension)) {
            this.selected = false;
            this.selectedWorld = -1;
            this.mc.displayScreen(new UNDataMissingScreen(this, save));
            return;
        }

        original.call(i);
    }
}
