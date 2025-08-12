package teamport.aether.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.hud.HudIngame;
import net.minecraft.client.gui.hud.component.HudComponentMovable;
import net.minecraft.client.gui.hud.component.layout.Layout;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import org.lwjgl.opengl.GL11;

public class ComponentJumpBar extends HudComponentMovable {
    private final IconCoordinate jump_empty = TextureRegistry.getTexture("aether:gui/sprites/hud/jump_empty.png");
    private final IconCoordinate jump_full = TextureRegistry.getTexture("aether:gui/sprites/hud/jump_full.png");

    public ComponentJumpBar(String key, Layout layout) {
        super(key, 81, 10, layout);
    }

    @Override
    public boolean isVisible(Minecraft mc) {
        return mc.thePlayer.isPassenger();
    }

    @Override
    public void render(Minecraft mc, HudIngame hud, int xScreenSize, int yScreenSize, float partialTick) {
        int x = this.getLayout().getComponentX(mc, this, xScreenSize);
        int y = this.getLayout().getComponentY(mc, this, yScreenSize);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(3042);
        int armorValue = mc.thePlayer.getPlayerProtectionAmount();

        for(int i = 0; i < 10; ++i) {
            if (armorValue > 0) {
                int xArmor = x + this.getXSize(mc) - i * 8 - 9;
                if (i * 2 + 1 < armorValue) {
                    hud.drawGuiIcon(xArmor, y, 9, 9, this.jump_full);
                }

                if (i * 2 + 1 > armorValue) {
                    hud.drawGuiIcon(xArmor, y, 9, 9, this.jump_empty);
                }
            }
        }

    }

    @Override
    public void renderPreview(Minecraft mc, Gui gui, Layout layout, int xScreenSize, int yScreenSize) {
            int x = layout.getComponentX(mc, this, xScreenSize);
            int y = layout.getComponentY(mc, this, yScreenSize);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(3042);
            int armorValue = 11;

            for(int i = 0; i < 10; ++i) {
                int xArmor = x + this.getXSize(mc) - i * 8 - 9;
                if (i * 2 + 1 < armorValue) {
                    gui.drawGuiIcon(xArmor, y, 9, 9, this.jump_full);
                }

                if (i * 2 + 1 > armorValue) {
                    gui.drawGuiIcon(xArmor, y, 9, 9, this.jump_empty);
                }
            }

        }
}
