package teamport.aether.mixin.accessory.cape.invisibilitycape;

import net.minecraft.client.entity.player.PlayerLocal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import teamport.aether.items.IAetherAccessories;

@Mixin(value = PlayerLocal.class, remap = false)
public class PlayerLocalMixinInvisibility implements IAetherAccessories {
    @Unique
    boolean invisible = false;

    @Unique
    public void aether$setInvisible(boolean invisible) {
        this.invisible = invisible;
    }
    @Unique
    public boolean aether$getInvisible() {
        return invisible;
    }
}
