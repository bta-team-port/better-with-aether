package teamport.aether.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.ButtonElement;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.player.controller.PlayerControllerSP;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.save.SaveFile;
import org.jspecify.annotations.NonNull;
import org.lwjgl.input.Keyboard;

@Environment(EnvType.CLIENT)
public class UNDataMissingScreen extends Screen {
    private final SaveFile level;
    private final I18n i18n = I18n.getInstance();
    private String[] body;
    private int bodyY;
    private int confirmationStep;
    private ButtonElement continueButton;

    public UNDataMissingScreen(Screen previous, SaveFile level) {
        super(previous);
        this.level = level;
    }

    @Override
    public void init() {
        Keyboard.enableRepeatEvents(true);
        this.body = this.i18n.translateKeyAndFormat(
            "aether.gui.un_missing_warn.body",
            this.level.getDisplayName(),
            this.level.getFileName()
        ).split("\n");
        int bodyHeight = this.body.length * this.fontRenderer.getFont().fontHeight();
        this.bodyY = (int) (this.height * 0.25);
        this.buttons.clear();
        this.buttons.add(new ButtonElement(1, this.width / 2 - 100, this.bodyY + bodyHeight + 24,
            this.i18n.translateKey("gui.select_world.button.cancel")));
        this.continueButton = new ButtonElement(2, this.width / 2 - 100, this.bodyY + bodyHeight + 48,
            this.i18n.translateKey("aether.gui.un_missing_warn.proceed_0"));
        this.buttons.add(this.continueButton);
    }

    @Override
    public void removed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTick) {
        this.renderBackground();
        for (int i = 0; i < this.body.length; i++) {
            this.drawStringCenteredShadow(this.fontRenderer, this.body[i], this.width / 2,
                this.bodyY + this.fontRenderer.getFont().fontHeight() * i, DyeColor.WHITE.color.value);
        }
        super.render(mouseX, mouseY, partialTick);
    }

    @Override
    protected void buttonClicked(@NonNull ButtonElement button) {
        if (!button.enabled) {
            return;
        }
        if (button.id == 1) {
            this.mc.displayScreen(this.getParentScreen());
            return;
        }
        if (button.id != 2) {
            return;
        }
        if (this.confirmationStep >= 3) {
            this.mc.playerController = new PlayerControllerSP(this.mc);
            this.mc.startWorld(this.level.getFileName());
            this.mc.displayScreen(null);
            return;
        }

        this.confirmationStep++;
        this.continueButton.displayString = this.i18n.translateKey("aether.gui.un_missing_warn.proceed_" + this.confirmationStep);
        if (this.confirmationStep == 1) {
            this.continueButton.xPosition = (int) ((this.width - 200) * 0.25f) / 20 * 20;
            this.continueButton.yPosition = (int) ((this.height - 20) * 0.75f) / 20 * 20;
        } else if (this.confirmationStep == 2) {
            this.continueButton.xPosition = (int) ((this.width - 200) * 0.75f) / 20 * 20;
            this.continueButton.yPosition = (int) ((this.height - 20) * 0.05f) / 20 * 20;
        } else {
            this.continueButton.xPosition = this.width / 2 - 100;
            this.continueButton.yPosition = this.bodyY + this.body.length * this.fontRenderer.getFont().fontHeight() + 48;
        }
    }
}
