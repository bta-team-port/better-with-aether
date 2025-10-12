package teamport.aether.gui;

import teamport.aether.entity.tile.TileEntityEnchanter;
import teamport.aether.entity.tile.TileEntityFreezer;
import teamport.aether.entity.tile.TileEntityIncubator;
import teamport.aether.entity.tile.TileEntitySignSkyroot;

public interface AetherScreens {
    void aether$displayEnchanterScreen(TileEntityEnchanter tileEntity);

    void aether$displayFreezerScreen(TileEntityFreezer tileEntity);

    void aether$displayIncubatorScreen(TileEntityIncubator tileEntity);

    void aether$displaySignSkyrootEditorScreen(TileEntitySignSkyroot tileEntity);
}
