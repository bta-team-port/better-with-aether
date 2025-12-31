package teamport.aether.compat.waila;

import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.Global;
import net.minecraft.core.item.ItemStack;
import teamport.aether.block.entity.TileEntityIncubator;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.TileTooltip;
import toufoumaster.btwaila.util.Colors;
import toufoumaster.btwaila.util.ProgressBarOptions;
import toufoumaster.btwaila.util.TextureOptions;

import static net.minecraft.core.net.command.TextFormatting.ORANGE;
import static net.minecraft.core.net.command.TextFormatting.RESET;
import static toufoumaster.btwaila.BTWaila.translator;

public class IncubatorTooltip extends TileTooltip<TileEntityIncubator> {
    @Override
    public void initTooltip() {
        addClass(TileEntityIncubator.class);
    }

    @Override
    public void drawAdvancedTooltip(TileEntityIncubator incubator, AdvancedInfoComponent advancedInfoComponent) {
        ItemStack input = incubator.getItem(0);
        ItemStack fuel = incubator.getItem(1);
        ProgressBarOptions options = new ProgressBarOptions(
            0, String.format("%s%s%s ", ORANGE, translator.translateKey("aether.tooltip.incubator.progress"), RESET), true, true,
            new TextureOptions(Colors.WHITE, TextureRegistry.getTexture("aether:extras/incubator_bg")),
            new TextureOptions(Colors.WHITE, TextureRegistry.getTexture("aether:extras/incubator_fg"))
        );
        advancedInfoComponent.drawProgressBarTextureWithText(incubator.getProcessProgressScaled(100), 100, options, 0); //getCookProgressScaled
        advancedInfoComponent.drawStringWithShadow(String.format(translator.translateKey("aether.tooltip.machine.energy"), incubator.getCurrentEnergyTime() / Global.TICKS_PER_SECOND), 0);
        ItemStack[] stacks = new ItemStack[]{input, fuel};
        advancedInfoComponent.drawItemList(stacks, 0);
    }
}
