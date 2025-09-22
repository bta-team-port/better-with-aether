package teamport.aether.mixin.accessory.cape.invisibilitycape.player;

import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import teamport.aether.items.accessory.AetherInvisibility;

@Mixin(value = Player.class, remap = false)
public class PlayerMixinInvisibility implements AetherInvisibility {
    @Unique
    public boolean invisible;

    @Unique
    public void aether$setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

    @Unique
    public boolean aether$isInvisible() {
        return invisible;
    }

}
