package bta.aether.gui;

import bta.aether.tile.TileEntityEnchanter;
import bta.aether.tile.TileEntityFreezer;
import bta.aether.tile.TileEntityIncubator;

public interface IAetherGuis {
    void aether$displayGUIEnchanter(TileEntityEnchanter tile);
    void aether$displayGUIFreeze(TileEntityFreezer tile);
    void aether$displayGUIIncubator(TileEntityIncubator tile);

    void aether$displayGUILoreBook(String  loreId);
}
