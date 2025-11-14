package teamport.aether.gui;

import net.minecraft.client.gui.ButtonElement;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.player.controller.PlayerControllerSP;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.save.SaveFile;
import org.lwjgl.input.Keyboard;

public class UNDataMissingScreen extends Screen {

    private final Screen previous;
    private final SaveFile level;

    private String[] body;

    private int y;

    private int currBtn = 0;

    private ButtonElement continueBtn;

    private final I18n i18n = I18n.getInstance();

    public UNDataMissingScreen(Screen previous, SaveFile level) {
        this.previous = previous;
        this.level = level;
    }

    @Override
    public void init() {
        Keyboard.enableRepeatEvents(true);

        body = i18n.translateKeyAndFormat("aether.gui.un_missing_warn.body", level.getDisplayName(), level.getFileName()).split("\n");
        int bodyHeight = body.length * this.font.fontHeight;

        y = (int) (height * 0.25);


        this.buttons.clear();

        this.buttons.add(new ButtonElement(1, this.width / 2 - 100, y + bodyHeight + 24, i18n.translateKey("gui.select_world.button.cancel")));

        continueBtn = new ButtonElement(2, this.width / 2 - 100, y + bodyHeight + 48, i18n.translateKey("aether.gui.un_missing_warn.proceed_0"));
        buttons.add(continueBtn);
    }

    @Override
    public void removed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void render(int mx, int my, float partialTick) {
        this.renderBackground();

        for (int i = 0; i < body.length; i++) {
            this.drawStringCentered(this.font, body[i], this.width / 2, y + font.fontHeight * i, DyeColor.WHITE.color.value);
        }

        super.render(mx, my, partialTick);
    }

    @Override
    protected void buttonClicked(ButtonElement button) {
        if (button.enabled) {
            if (button.id == 1) {
                this.mc.displayScreen(this.previous);
            }

            if (button.id == 2) {
                if (currBtn >= 3) {
                    this.mc.playerController = new PlayerControllerSP(this.mc);
                    mc.startWorld(level.getFileName(), level.getDisplayName(), 0L);
                    mc.displayScreen(null);
                    return;
                }

                continueBtn.displayString = i18n.translateKey("aether.gui.un_missing_warn.proceed_" + ++currBtn);


                if (currBtn == 1) {
                    continueBtn.xPosition = (((int) ((width - 200) * .25f)) / 20) * 20;
                    continueBtn.yPosition = (((int) ((height - 20) * .75f)) / 20) * 20;
                } else if (currBtn == 2) {
                    continueBtn.xPosition = (((int) ((width - 200) * .75f)) / 20) * 20;
                    continueBtn.yPosition = (((int) ((height - 20) * .05f)) / 20) * 20;
                } else if (currBtn == 3) {
                    continueBtn.xPosition = this.width / 2 - 100;
                    continueBtn.yPosition = y + body.length * this.font.fontHeight + 48;
                }
            }
        }
    }
}
