package teamport.aether.entity.animal.moa;

import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import teamport.aether.item.AetherItems;

public class MobMoaBlack extends MobMoa {

    @SuppressWarnings("unused")
    public MobMoaBlack(@Nullable World world) {
        super(world);
        setupAppearance();
    }

    @SuppressWarnings("unused")
    public MobMoaBlack(@Nullable World world, boolean tamed) {
        super(world, tamed);
        setupAppearance();
    }

    @Override
    protected void setupAppearance() {
        this.setTextureIdentifier("aether", "moa_black");
        this.eggColor = AetherItems.EGG_MOA_BLACK;
    }

    @Override
    public int getJumpMaxAmount() {
        return 7;
    }

    @Override
    public void onGround() {
        if (this.onGround) this.jumpsRemaining = getJumpMaxAmount();
    }
}
