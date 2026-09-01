package teamport.aether.compat.waila;

import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.Global;
import net.minecraft.core.item.ItemStack;
import teamport.aether.block.entity.TileEntityFreezer;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.TileTooltip;
import toufoumaster.btwaila.util.Colors;
import toufoumaster.btwaila.util.ProgressBarOptions;
import toufoumaster.btwaila.util.TextureOptions;

import static net.minecraft.core.net.command.TextFormatting.LIGHT_BLUE;
import static net.minecraft.core.net.command.TextFormatting.RESET;
import static toufoumaster.btwaila.BTWaila.translator;

public class FreezerTooltip extends TileTooltip<TileEntityFreezer> {
    @Override
    public void initTooltip() {
        this.addClass(TileEntityFreezer.class);
    }

    @Override
    public void drawAdvancedTooltip(TileEntityFreezer freezer, AdvancedInfoComponent advancedInfoComponent) {
        ItemStack input = freezer.getItem(0);
        ItemStack fuel = freezer.getItem(1);
        ItemStack output = freezer.getItem(2);
        ProgressBarOptions options = new ProgressBarOptions(
            0, String.format("%s%s%s ", LIGHT_BLUE, translator.translateKey("aether.tooltip.freezer.progress"), RESET), true, true,
            new TextureOptions(Colors.WHITE, TextureRegistry.getTexture("aether:extras/freezer_bg")),
            new TextureOptions(Colors.WHITE, TextureRegistry.getTexture("aether:extras/freezer_fg"))
        );
        advancedInfoComponent.drawProgressBarTextureWithText(freezer.getProcessProgressScaled(100), 100, options, 0); //getCookProgressScaled

        advancedInfoComponent.drawStringWithShadow(String.format(translator.translateKey("aether.tooltip.machine.energy"), freezer.getCurrentEnergyTime() / Global.TICKS_PER_SECOND), 0);
        ItemStack[] stacks = new ItemStack[]{input, fuel, output};
        advancedInfoComponent.drawItemList(stacks, 0);
    }
}

