package teamport.aether.entity.animal.moa;

import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import teamport.aether.item.AetherItems;

public class MobMoaWhite extends MobMoa {

    @SuppressWarnings("unused")
    public MobMoaWhite(@Nullable World world) {
        super(world);
        setupAppearance();
    }

    @SuppressWarnings("unused")
    public MobMoaWhite(@Nullable World world, boolean tamed) {
        super(world, tamed);
        setupAppearance();
    }

    @Override
    protected void setupAppearance() {
        this.textureIdentifier = NamespaceID.getPermanent("aether", "moa_white");
        this.eggColor = AetherItems.EGG_MOA_WHITE;
    }

    @Override
    public int getJumpMaxAmount() {
        return 5;
    }

    @Override
    public void onGround() {
        if (this.onGround) this.jumpsRemaining = getJumpMaxAmount();
    }
}
