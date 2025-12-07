package teamport.aether.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.ButtonElement;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.modelviewer.elements.ListenerButtonElement;
import net.minecraft.client.input.controller.ControllerInput;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.block.Block;
import net.minecraft.core.enums.EnumSignPicture;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.net.packet.PacketSignUpdate;
import net.minecraft.core.util.helper.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import teamport.aether.block.AetherBlocks;
import teamport.aether.block.skyroot.BlockLogicPaintableSignSkyroot;

@Environment(EnvType.CLIENT)
public class ScreenSignSkyrootEditor extends Screen {
    private final String screenTitle = I18n.getInstance().translateKey("gui.edit_sign.label.title");
    private final TileEntitySignSkyroot entitySign;
    private int updateCounter;
    private int editLine = 0;
    private int yOffset = 0;

    public ScreenSignSkyrootEditor(TileEntitySignSkyroot sign) {
        this.entitySign = sign;
    }

    @Override
    public void init() {
        this.buttons.clear();
        Keyboard.enableRepeatEvents(true);
        Block<?> block = this.entitySign.getBlock();
        if (block == AetherBlocks.SIGN_POST_PLANKS_SKYROOT) {
            this.yOffset = 36;
        }

        this.buttons.add(new ButtonElement(0, this.width / 2 - 100, 200 + this.yOffset, I18n.getInstance().translateKey("gui.edit_sign.button.done")));
        this.buttons.add(new ButtonElement(1, this.width / 2 - 20, 170 + this.yOffset, 20, 20, "<"));
        this.buttons.add(new ButtonElement(2, this.width / 2, 170 + this.yOffset, 20, 20, ">"));
        boolean wallSign = !((BlockLogicPaintableSignSkyroot) block.getLogic()).isFreeStanding;
        this.add((new ListenerButtonElement(30, this.width / 2 - 50, 67 + (wallSign ? 31 : 0), 100, 12, "")).setActionListener(() -> this.editLine = 0)).mute().hide();
        this.add((new ListenerButtonElement(30, this.width / 2 - 50, 79 + (wallSign ? 31 : 0), 100, 12, "")).setActionListener(() -> this.editLine = 1)).mute().hide();
        this.add((new ListenerButtonElement(30, this.width / 2 - 50, 91 + (wallSign ? 31 : 0), 100, 12, "")).setActionListener(() -> this.editLine = 2)).mute().hide();
        this.add((new ListenerButtonElement(30, this.width / 2 - 50, 103 + (wallSign ? 31 : 0), 100, 12, "")).setActionListener(() -> this.editLine = 3)).mute().hide();
    }

    @Override
    public void removed() {
        Keyboard.enableRepeatEvents(false);
        if (this.mc.currentWorld != null && this.mc.currentWorld.isClientSide) {
            EnumSignPicture enumsignpicture = this.entitySign.getPicture();
            if (enumsignpicture != null) this.mc.getSendQueue().addToSendQueue(new PacketSignUpdate(this.entitySign.x, this.entitySign.y, this.entitySign.z, this.entitySign.signText, enumsignpicture.getId(), this.entitySign.getColor().id));
        }

    }

    @Override
    public void tick() {
        ++this.updateCounter;
    }

    @Override
    public void buttonClicked(ButtonElement button) {
        if (button.enabled) {
            if (button.id == 0) {
                this.entitySign.setChanged();
                this.mc.displayScreen(null);
            }

            int id;
            int nextId;
            if (button.id == 1) {
                EnumSignPicture enumsignpicture = this.entitySign.getPicture();
                if (enumsignpicture != null) {
                    id = enumsignpicture.getId();
                    nextId = id - 1;
                    if (nextId < 0) {
                        nextId = EnumSignPicture.values().length - 1;
                    }

                    this.entitySign.setPicture(EnumSignPicture.values()[nextId]);
                    this.entitySign.setChanged();
                }
            }

            if (button.id == 2) {
                EnumSignPicture enumsignpicture = this.entitySign.getPicture();
                if (enumsignpicture != null) {
                    id = this.entitySign.getPicture().getId();
                    nextId = id + 1;
                    if (nextId >= EnumSignPicture.values().length) {
                        nextId = 0;
                    }

                    this.entitySign.setPicture(EnumSignPicture.values()[nextId]);
                    this.entitySign.setChanged();
                }
            }

        }
    }

    @Override
    public void keyPressed(char eventCharacter, int eventKey, int mx, int my) {
        if (eventKey == Keyboard.KEY_ESCAPE) {
            this.entitySign.setChanged();
            this.mc.displayScreen(null);
        }

        if (eventKey == Keyboard.KEY_UP) {
            this.editLine = this.editLine - 1 & 3;
        }

        if (eventKey == Keyboard.KEY_DOWN || eventKey == Keyboard.KEY_PRINT_SCREEN || eventKey == Keyboard.KEY_NUMPADENTER) {
            this.editLine = this.editLine + 1 & 3;
        }

        if (eventKey == Keyboard.KEY_BACK && !this.entitySign.signText[this.editLine].isEmpty()) {
            this.entitySign.signText[this.editLine] = this.entitySign.signText[this.editLine].substring(0, this.entitySign.signText[this.editLine].length() - 1);
        }

        if ((ChatAllowedCharacters.ALLOWED_CHARACTERS.indexOf(eventCharacter) >= 0 || Character.isLetterOrDigit(eventCharacter)) && this.entitySign.signText[this.editLine].length() < 15) {
            String[] var10002 = this.entitySign.signText;
            int var10004 = this.editLine;
            var10002[var10004] = String.valueOf(var10002[var10004]) + eventCharacter;
        }

    }

    @Override
    public void render(int mx, int my, float partialTick) {
        this.renderBackground();
        this.drawStringCentered(this.font, this.screenTitle, this.width / 2, 40, 16777215);
        GL11.glPushMatrix();
        GL11.glTranslatef(this.width / 2.0F, 0.0F, 50.0F);
        float scale = 93.75F;
        GL11.glScalef(-scale, -scale, -scale);
        GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
        Block<?> block = this.entitySign.getBlock();
        if (((BlockLogicPaintableSignSkyroot) block.getLogic()).isFreeStanding) {
            float signAngle = ((this.entitySign.getBlockMeta() & 15) * 360) / 16.0F;
            GL11.glRotatef(signAngle, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.0F, -1.0625F, 0.0F);
        } else {
            int meta = this.entitySign.getBlockMeta() & 15;
            float signAngle = 0.0F;
            if (meta == 2) {
                signAngle = 180.0F;
            }

            if (meta == 4) {
                signAngle = 90.0F;
            }

            if (meta == 5) {
                signAngle = -90.0F;
            }

            GL11.glRotatef(signAngle, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.0F, -1.0625F, 0.0F);
        }

        if (this.updateCounter / 6 % 2 == 0) {
            this.entitySign.lineBeingEdited = this.editLine;
        }

        GL11.glEnable(GL11.GL_BLEND);
        TileEntityRenderDispatcher.instance.renderTileEntity(Tessellator.instance, this.entitySign, -0.5, -0.75, -0.5, 0.0F);
        this.entitySign.lineBeingEdited = -1;
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        EnumSignPicture enumsignpicture = this.entitySign.getPicture();
        if (enumsignpicture != null) this.drawStringCentered(this.font, I18n.getInstance().translateKey(enumsignpicture.getLanguageKey()), this.width / 2, 150 + this.yOffset, 16777215);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        super.render(mx, my, partialTick);
    }

    @Override
    public void guiSpecificControllerInput(ControllerInput controller) {
        super.guiSpecificControllerInput(controller);
        if (controller.digitalPad.up.pressedThisFrame()) {
            this.editLine = this.editLine - 1 & 3;
        }

        if (controller.digitalPad.down.pressedThisFrame()) {
            this.editLine = this.editLine + 1 & 3;
        }

    }
}
