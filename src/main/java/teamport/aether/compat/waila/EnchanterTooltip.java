package teamport.aether.compat.waila;

import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.Global;
import net.minecraft.core.item.ItemStack;
import teamport.aether.block.entity.TileEntityEnchanter;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.TileTooltip;
import toufoumaster.btwaila.util.Colors;
import toufoumaster.btwaila.util.ProgressBarOptions;
import toufoumaster.btwaila.util.TextureOptions;

import static net.minecraft.core.net.command.TextFormatting.LIME;
import static net.minecraft.core.net.command.TextFormatting.RESET;
import static toufoumaster.btwaila.BTWaila.translator;

public class EnchanterTooltip extends TileTooltip<TileEntityEnchanter> {
    @Override
    public void initTooltip() {
        this.addClass(TileEntityEnchanter.class);
    }

    @Override
    public void drawAdvancedTooltip(TileEntityEnchanter enchanter, AdvancedInfoComponent advancedInfoComponent) {
        ItemStack input = enchanter.getItem(0);
        ItemStack fuel = enchanter.getItem(1);
        ItemStack output = enchanter.getItem(2);

        ProgressBarOptions options = new ProgressBarOptions(
            0, String.format("%s%s%s ", LIME, translator.translateKey("aether.tooltip.enchanter.progress"), RESET), true, true,
            new TextureOptions(Colors.WHITE, TextureRegistry.getTexture("aether:extras/enchanter_bg")),
            new TextureOptions(Colors.WHITE, TextureRegistry.getTexture("aether:extras/enchanter_fg"))
        );
        advancedInfoComponent.drawProgressBarTextureWithText(enchanter.getProcessProgressScaled(100), 100, options, -12); //getCookProgressScaled
        advancedInfoComponent.drawStringWithShadow(String.format(translator.translateKey("aether.tooltip.machine.energy"), enchanter.getCurrentEnergyTime() / Global.TICKS_PER_SECOND), 0);
        ItemStack[] stacks = new ItemStack[]{input, fuel, output};
        advancedInfoComponent.drawItemList(stacks, 0);
    }
}

