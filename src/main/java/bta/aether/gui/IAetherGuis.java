package bta.aether.gui;

import bta.aether.tile.TileEntityEnchanter;
import bta.aether.tile.TileEntityFreezer;

public interface IAetherGuis {
    void aether$displayGUIEnchanter(TileEntityEnchanter tile);
    void aether$displayGUIFreeze(TileEntityFreezer tile);
    void aether$displayGUILoreBook(int loreId);
}
