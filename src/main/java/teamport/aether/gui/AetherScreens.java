package teamport.aether.gui;

import teamport.aether.tile.TileEntityEnchanter;
import teamport.aether.tile.TileEntityFreezer;
import teamport.aether.tile.TileEntityIncubator;
import teamport.aether.tile.TileEntitySignSkyroot;

public interface AetherScreens {
    void aether$displayEnchanterScreen(TileEntityEnchanter tileEntity);
    void aether$displayFreezerScreen(TileEntityFreezer tileEntity);
    void aether$displayIncubatorScreen(TileEntityIncubator tileEntity);

    void aether$displaySignSkyrootEditorScreen(TileEntitySignSkyroot tileEntity);
}
