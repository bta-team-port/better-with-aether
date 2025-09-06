package teamport.aether.gui;

import net.minecraft.client.gui.ButtonElement;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.player.controller.PlayerControllerSP;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.save.SaveFile;
import org.lwjgl.input.Keyboard;

import java.util.Random;

public class UNDataMissingScreen extends Screen {

    private final Screen previous;
    private final SaveFile level;

    private String[] body;

    private int y;

    public int currBtn = 0;

    ButtonElement continueBtn;

    protected I18n i18n = I18n.getInstance();
    protected Random rand = new Random();

    public UNDataMissingScreen(Screen previous, SaveFile level) {
        this.previous = previous;
        this.level = level;
    }

    public void init() {
        Keyboard.enableRepeatEvents(true);

        body = i18n.translateKeyAndFormat("aether.gui.un_missing_warn.body", level.getDisplayName(), level.getFileName()).split("\n");
        int bodyHeight = body.length * this.font.fontHeight;

        y = (int) (height * 0.25);


        this.buttons.clear();

        this.buttons.add(new ButtonElement(1, this.width/2 - 100,y + bodyHeight + 24, i18n.translateKey("gui.select_world.button.cancel")));

        continueBtn = new ButtonElement(2, this.width/2 - 100, y + bodyHeight + 48, i18n.translateKey("aether.gui.un_missing_warn.proceed_0"));
        buttons.add(continueBtn);
    }

    public void removed() {
        Keyboard.enableRepeatEvents(false);
    }

    public void render(int mx, int my, float partialTick) {
        this.renderBackground();

        for (int i = 0; i < body.length; i++) {
            this.drawStringCentered(this.font, body[i], this.width / 2, y + font.fontHeight * i, DyeColor.WHITE.color.value);
        }

        super.render(mx, my, partialTick);
    }

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

                if (currBtn == 3) {
                    continueBtn.xPosition = this.width / 2 - 100;
                    continueBtn.yPosition = y + body.length * this.font.fontHeight + 48;
                }
                else {
                    continueBtn.xPosition = rand.nextInt(width - 200);
                    continueBtn.yPosition = rand.nextInt(height - 20);
                }
            }
        }
    }

}
