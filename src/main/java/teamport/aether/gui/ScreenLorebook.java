package teamport.aether.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.container.ScreenContainerAbstract;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.player.inventory.slot.Slot;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class ScreenLorebook extends ScreenContainerAbstract {
    private final String loreId;
    private final String texturePath;

    public ScreenLorebook(MenuLorebook menu, String loreId) {
        super(menu);
        this.xSize = 256;
        this.ySize = 195;
        this.loreId = loreId;

        String dimension = "aether";

        if (loreId.startsWith("overworld")) {
            dimension = "overworld";
        } else if (loreId.startsWith("nether")) {
            dimension = "nether";
        } else if (loreId.startsWith("paradise")) {
            dimension = "drift";
        } else if (loreId.startsWith("aether")) {
            dimension = "aether";
        }

        this.texturePath = "/assets/aether/textures/gui/container/lore_" + dimension + ".png";
    }

    @Override
    public void removed() {
        super.removed();
        this.inventorySlots.onCraftGuiClosed(this.mc.thePlayer);
    }

    @Override
    protected void drawGuiContainerForegroundLayer() {
        I18n i18n = I18n.getInstance();

        String line1 = i18n.translateKey("aether.gui.lorebook.book." + loreId + ".line1");
        String line2 = i18n.translateKey("aether.gui.lorebook.book." + loreId + ".line2");
        String line3 = i18n.translateKey("aether.gui.lorebook.book." + loreId + ".line3");

        int titleCenterX = 37;
        font.drawString(line1, titleCenterX, 19, 4210752);
        font.drawString(line2, titleCenterX, 29, 4210752);
        font.drawString(line3, titleCenterX, 39, 4210752);

        String itemLabel = i18n.translateKey("aether.gui.lorebook.item");
        font.drawString(itemLabel, 70 - font.getStringWidth(itemLabel), 73, 4210752);

        Slot loreSlot = inventorySlots.getSlot(0);
        if (loreSlot.hasItem()) {
            ItemStack stack = loreSlot.getItemStack();
            String loreKey = stack.getItemKey() + ".lore." + loreId;
            String loreText = i18n.translateKey(loreKey);

            List<String> lines = new ArrayList<>();

            if (loreText.equals(loreKey)) {
                loreText = i18n.translateKey("aether.gui.lorebook.unknown");
            } else {
                String itemName = i18n.translateKey(stack.getItemKey() + ".name");
                lines.addAll(wrapText(itemName, 20));
            }
            lines.addAll(wrapText(loreText, 20));

            int x = 130;
            int y = 15;
            boolean first = true;
            for (int i = 0; i < Math.min(lines.size(), 6); i++) {
                String line = lines.get(i).trim();
                font.drawString(line, x, y + (first ? 0 : 5), 4210752);
                y += 10;
                first = false;
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.textureManager.loadTexture(texturePath).bind();
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }

    private List<String> wrapText(String text, int maxChars) {
        if (text == null || text.isEmpty()) {
            return new ArrayList<>();
        }
        if (text.length() <= maxChars) {
            List<String> list = new ArrayList<>();
            list.add(text);
            return list;
        }

        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder current = new StringBuilder();

        for (String word : words) {
            String test = current.length() == 0 ? word : current + " " + word;
            if (test.length() > maxChars) {
                lines.add(current.toString());
                current = new StringBuilder(word);
            } else {
                if (current.length() > 0) current.append(" ");
                current.append(word);
            }
        }
        if (current.length() > 0) lines.add(current.toString());
        return lines;
    }
}
