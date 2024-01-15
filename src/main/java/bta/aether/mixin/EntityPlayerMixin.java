package bta.aether.mixin;

import bta.aether.entity.IPlayerJumpAmount;
import bta.aether.gui.IAetherGuis;
import bta.aether.tile.TileEntityEnchanter;
import bta.aether.tile.TileEntityFreezer;
import net.minecraft.core.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = EntityPlayer.class, remap = false)
public class EntityPlayerMixin implements IAetherGuis, IPlayerJumpAmount {
    @Unique
    private int jumpMaxAmount;
    @Unique
    private int jumpAmount;

    @Override
    public void aether$displayGUIEnchanter(TileEntityEnchanter tile) {
    }

    @Override
    public void aether$displayGUIFreeze(TileEntityFreezer tile) {
    }

    @Override
    public void aether$displayGUILoreBook(String loreId) {
    }

    @Unique
    public int aether$getJumpMaxAmount() {
        return this.jumpMaxAmount;
    }

    @Unique
    public int aether$getJumpAmount() {
        return this.jumpAmount;
    }

    @Unique
    public void aether$setJumpMaxAmount(int jumpMaxAmount) {
        this.jumpMaxAmount = jumpMaxAmount;
        if (this.jumpMaxAmount > this.jumpAmount) this.jumpAmount = this.jumpMaxAmount;
    }

    @Unique
    public void aether$setJumpAmount(int jumpAmount) {
        this.jumpAmount = jumpAmount;
    }

}
