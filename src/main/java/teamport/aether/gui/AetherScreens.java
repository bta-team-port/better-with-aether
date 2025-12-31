package teamport.aether.gui;

import net.minecraft.core.block.entity.TileEntitySign;
import teamport.aether.block.entity.TileEntityEnchanter;
import teamport.aether.block.entity.TileEntityFreezer;
import teamport.aether.block.entity.TileEntityIncubator;

public interface AetherScreens {
    void aether$displayEnchanterScreen(TileEntityEnchanter tileEntity);

    void aether$displayFreezerScreen(TileEntityFreezer tileEntity);

    void aether$displayIncubatorScreen(TileEntityIncubator tileEntity);

    void aether$displaySignSkyrootEditorScreen(TileEntitySign tileEntity);
}
