package bta.aether.gui;

import bta.aether.container.ContainerLoreBook;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.player.inventory.slot.Slot;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GuiLorebook extends GuiContainer {
    private int loreId;
    public GuiLorebook(EntityPlayer player, int loreId) {
        super(new ContainerLoreBook(player.inventory));
        this.xSize = 256;
        this.ySize = 195;
        this.loreId = loreId;
    }
    @Override
    public void onClosed() {
        super.onClosed();
        this.inventorySlots.onCraftGuiClosed(this.mc.thePlayer);
    }
    @Override
    protected void drawGuiContainerBackgroundLayer(float f) {
        I18n t = I18n.getInstance();
        int left = (width - xSize) / 2;
        int top = (height - ySize) / 2;
        GL11.glColor4f(1f,1f,1f,1f);
        mc.renderEngine.bindTexture(mc.renderEngine.getTexture("/assets/aether/gui/lore.png"));
        drawTexturedModalRect(left,top, 0,0, xSize, ySize);
        int titleCenterX = left + 20 + 106/2;
        int titleY = top + 20;
        drawStringCenteredNoShadow(fontRenderer, t.translateKey(String.format("aether.gui.lorebook.book%d.line1", loreId)), titleCenterX, titleY, 0x404040);
        drawStringCenteredNoShadow(fontRenderer, t.translateKey(String.format("aether.gui.lorebook.book%d.line2", loreId)), titleCenterX, titleY + 10, 0x404040);
        drawStringCenteredNoShadow(fontRenderer, t.translateKey(String.format("aether.gui.lorebook.book%d.line3", loreId)), titleCenterX, titleY + 20, 0x404040);

        String itemText = t.translateKey("aether.gui.lorebook.item");
        drawStringNoShadow(fontRenderer, itemText, left + 76 - fontRenderer.getStringWidth(itemText), top + 66 + 7, 0x404040);

        Slot loreSlot = inventorySlots.getSlot(0);
        int loreLeft = left + 140;
        if (loreSlot.hasStack()){
            ItemStack stack = loreSlot.getStack();
            String des = t.translateKey(stack.getItemName() + ".lore" + loreId);
            List<String> description = new ArrayList<>();
            if (des.equals(stack.getItemName() + ".lore" + loreId)){
                des = t.translateKey("aether.gui.lorebook.unknown");
            } else {
                description.addAll(constrainString(t.translateKey(stack.getItemName() + ".name"), 15));
            }
            description.addAll(constrainString(des, 15));
            boolean first = true;
            for (int i = 0; i < Math.min(description.size(), 6); i++) {
                drawStringNoShadow(fontRenderer, description.get(i).trim(), loreLeft, top + 15 + (10 * i) + (first ? 0:5), 0x404040);
                first = false;
            }
        }
    }
    private List<String> constrainString(String s, int maxWidth){
        if (s.length() <= maxWidth){
            return new ArrayList<String>(){{add(s);}};
        }
        List<String> builtLines = new ArrayList<>();
        List<String> wordsList = new ArrayList<>(Arrays.asList(s.split(" ")));
        String line = "";
        String word = "";
        String nextLine = "";
        while (!wordsList.isEmpty()){
            word = wordsList.remove(0);
            nextLine = line + " " + word;
            if (nextLine.length() > maxWidth){
                builtLines.add(line);
                line = word;
                nextLine = "";
            } else {
                line = nextLine;
            }
        }
        if (!nextLine.isEmpty()){
            builtLines.add(nextLine);
        }

        return builtLines;
    }
}
