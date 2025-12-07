package teamport.aether.compat.waila;

import net.minecraft.core.player.inventory.container.Container;
import org.slf4j.Logger;
import teamport.aether.block.entity.TileEntityMimic;
import toufoumaster.btwaila.entryplugins.waila.BTWailaCustomTooltipPlugin;
import toufoumaster.btwaila.entryplugins.waila.BTWailaPlugin;
import toufoumaster.btwaila.tooltips.TileTooltip;
import toufoumaster.btwaila.tooltips.TooltipRegistry;

public class AetherWailaPlugin implements BTWailaCustomTooltipPlugin {
    @Override
    public void initializePlugin(TooltipRegistry tooltipRegistry, Logger logger) {
        TileTooltip<Container> inventory = BTWailaPlugin.INVENTORY;
        inventory.addClass(TileEntityMimic.class);
        tooltipRegistry.register(new EnchanterTooltip());
        tooltipRegistry.register(new FreezerTooltip());
        tooltipRegistry.register(new IncubatorTooltip());
    }
}
