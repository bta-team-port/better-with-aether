package teamport.aether.gui;

import teamport.aether.tile.TileEntityEnchanter;
import teamport.aether.tile.TileEntityFreezer;
import teamport.aether.tile.TileEntityIncubator;

// TODO implement the remaining functions in player and playerlocal
public interface IAetherScreens {
    void aether$displayEnchanterScreen(TileEntityEnchanter tileEntity);
    void aether$displayFreezerScreen(TileEntityFreezer tileEntity);
    void aether$displayIncubatorScreen(TileEntityIncubator tile);
}
