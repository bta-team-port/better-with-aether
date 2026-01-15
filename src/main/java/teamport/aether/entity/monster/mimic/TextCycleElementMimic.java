package teamport.aether.entity.monster.mimic;

import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.modelviewer.elements.TextCycleElement;
import net.minecraft.client.render.Font;
import net.minecraft.core.block.Blocks;

import static teamport.aether.AetherMod.TRANSLATOR;

public class TextCycleElementMimic extends TextCycleElement<String> {
	public TextCycleElementMimic(Screen parent, Font font, int xPosition, int yPosition, int width, int height, String initialElement) {
		super(parent, font, xPosition, yPosition, width, height, initialElement);
	}

	@Override
	public String cycleElement(String string, int i) {
        int index = MimicRegistry.getMimicVariantByName(string).getMimicVariant();
		if(i == -1) return MimicRegistry.getMimicVariantByID(MimicRegistry.getPrevValue(index)).getPathName();
		return MimicRegistry.getMimicVariantByID(MimicRegistry.getNextValue(index)).getPathName();
	}

	@Override
	public String getElementFromString(String s) {
		if (s.isEmpty()) {
			return MimicRegistry.DEFAULT.getPathName();
		}
		return MimicRegistry.getMimicVariantByName(s).getPathName();
	}


	@Override
	public String getNameFromElement(String string) {
        MimicEntry entry =  MimicRegistry.getMimicVariantByName(string);
        return TRANSLATOR.translateNameKey(Blocks.getBlock(entry.getMimicChestID()).getLanguageKey(entry.getMimicChestMetadata()));
	}
}
