package teamport.aether.mixin.gui;

import net.minecraft.client.input.controller.ControllerInventoryHandler;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.gui.ControllerGuiSupport;
import teamport.aether.gui.machine.enchanter.ScreenEnchanter;
import teamport.aether.gui.machine.freezer.ScreenFreezer;
import teamport.aether.gui.machine.incubator.ScreenIncubator;

@Mixin(ControllerInventoryHandler.class)
public class ControllerInputMixinInput implements ControllerGuiSupport {

    ControllerInventoryHandler asThis = (ControllerInventoryHandler) (Object) this;

    @Override
    public void handleEnchanter(@NotNull ScreenEnchanter enchanter){
        asThis.handleAbstractCrafting(enchanter, (e) -> e.index >= 3 && e.index <= 38, (e) -> e.index >= 0 && e.index <= 1, (e) -> e.index == 2, 0, 2, 16);

    }

    @Override
    public void handleFreezer(@NotNull ScreenFreezer freezer){
        asThis.handleAbstractCrafting(freezer, (e) -> e.index >= 3 && e.index <= 38, (e) -> e.index >= 0 && e.index <= 1, (e) -> e.index == 2, 0, 2, 16);

    }

    @Override
    public void handleIncubator(@NotNull ScreenIncubator incubator){
        asThis.handleAbstractCrafting(incubator, (e) -> e.index >= 2 && e.index <= 37, (e) -> e.index == 0, (e) -> e.index == 0, 0, 0, 16);
    }
}
