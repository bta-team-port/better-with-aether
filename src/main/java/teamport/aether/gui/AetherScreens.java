package teamport.aether.gui;

import teamport.aether.block.entity.TileEntityEnchanter;
import teamport.aether.block.entity.TileEntityFreezer;
import teamport.aether.block.entity.TileEntityIncubator;
import teamport.aether.block.entity.TileEntitySignSkyroot;

public interface AetherScreens {
    void aether$displayEnchanterScreen(TileEntityEnchanter tileEntity);

    void aether$displayFreezerScreen(TileEntityFreezer tileEntity);

    void aether$displayIncubatorScreen(TileEntityIncubator tileEntity);

    void aether$displaySignSkyrootEditorScreen(TileEntitySignSkyroot tileEntity);
}
