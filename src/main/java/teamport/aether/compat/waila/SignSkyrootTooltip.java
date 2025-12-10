package teamport.aether.compat.waila;

import teamport.aether.block.entity.TileEntitySignSkyroot;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.mixin.mixins.accessors.TileEntitySignAccessor;
import toufoumaster.btwaila.tooltips.TileTooltip;
import toufoumaster.btwaila.util.UUIDHelper;

import java.util.UUID;

import static toufoumaster.btwaila.BTWaila.translator;

public class SignSkyrootTooltip extends TileTooltip<TileEntitySignSkyroot> {
    @Override
    public void initTooltip() {
        addClass(TileEntitySignSkyroot.class);
    }

    @Override
    public void drawAdvancedTooltip(TileEntitySignSkyroot interfaceObject, AdvancedInfoComponent advancedInfoComponent) {
        UUID owner = ((TileEntitySignAccessor) interfaceObject).getOwner();
        String text = translator.translateKey("btwaila.tooltip.sign.owner").replace("{id}", owner == null ? translator.translateKey("btwaila.tooltip.sign.owner.none") : String.valueOf(UUIDHelper.getNameFromUUID(owner)));
        advancedInfoComponent.drawStringWithShadow(text, 0);
    }
}
