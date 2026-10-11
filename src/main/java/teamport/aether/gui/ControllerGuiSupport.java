package teamport.aether.gui;

import org.jetbrains.annotations.NotNull;
import teamport.aether.gui.machine.enchanter.ScreenEnchanter;
import teamport.aether.gui.machine.freezer.ScreenFreezer;
import teamport.aether.gui.machine.incubator.ScreenIncubator;

public interface ControllerGuiSupport {

    void handleEnchanter(@NotNull ScreenEnchanter enchanter);
    void handleFreezer(@NotNull ScreenFreezer freezer);
    void handleIncubator(@NotNull ScreenIncubator incubator);
}
