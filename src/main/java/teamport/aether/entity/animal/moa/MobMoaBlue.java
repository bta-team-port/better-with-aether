package teamport.aether.entity.animal.moa;

import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import teamport.aether.item.AetherItems;


public class MobMoaBlue extends MobMoa {

    @SuppressWarnings("unused")
    public MobMoaBlue(@Nullable World world) {
        super(world);
        setupAppearance();
    }

    @SuppressWarnings("unused")
    public MobMoaBlue(@Nullable World world, boolean tamed) {
        super(world, tamed);
        setupAppearance();
    }

    @Override
    protected void setupAppearance() {
        this.setTextureIdentifier("aether", "moa_blue");
        this.eggColor = AetherItems.EGG_MOA_BLUE;
    }

    @Override
    public int getJumpMaxAmount() {
        return 3;
    }

}
