package bta.aether.mixin;

import bta.aether.gui.IAetherGuis;
import bta.aether.tile.TileEntityEnchanter;
import bta.aether.tile.TileEntityFreezer;
import net.minecraft.core.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = EntityPlayer.class, remap = false)
public class EntityPlayerMixin implements IAetherGuis {
    @Override
    public void aether$displayGUIEnchanter(TileEntityEnchanter tile) {

    }

    @Override
    public void aether$displayGUIFreeze(TileEntityFreezer tile) {

    }

    @Override
    public void aether$displayGUILoreBook(int loreId) {

    }
}
