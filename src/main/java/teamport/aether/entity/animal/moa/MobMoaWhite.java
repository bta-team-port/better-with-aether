package teamport.aether.entity.animal.moa;

import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import teamport.aether.item.AetherItems;

public class MobMoaWhite extends MobMoa {

    @SuppressWarnings("unused")
    public MobMoaWhite(@Nullable World world) {
        super(world);
        this.setTextureIdentifier("aether", "moa_white");
        this.eggColor = AetherItems.EGG_MOA_WHITE;
    }

    @Override
    public int getJumpMaxAmount() {
        return 5;
    }

}
