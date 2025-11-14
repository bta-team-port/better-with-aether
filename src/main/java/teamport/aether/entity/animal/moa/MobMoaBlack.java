package teamport.aether.entity.animal.moa;

import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import teamport.aether.items.AetherItems;

public class MobMoaBlack extends MobMoaBlue {

    public MobMoaBlack(@Nullable World world) {
        super(world);
        this.setSize(1.0F, 2.0F);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "moa_black");
        this.jumpsRemaining = getJumpMaxAmount();
        this.eggColor = AetherItems.EGG_MOA_BLACK;
    }

    @SuppressWarnings("unused")
    public MobMoaBlack(@Nullable World world, boolean tamed) {
        this(world);
        this.tamed = tamed;
    }

    @Override
    public void onGround() {
        if (this.onGround) {
            this.jumpsRemaining = 7;
        }
    }

    @Override
    public int getJumpMaxAmount() {
        return 7;
    }
}
