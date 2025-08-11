package teamport.aether.entity.animal.moa;

import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import teamport.aether.items.AetherItems;

public class MobMoaWhite extends MobMoaBlue {

    public MobMoaWhite(@Nullable World world) {
        super(world);
        this.setSize(1.0F, 2.0F);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "moa_white");
        this.jumpsRemaining = 4;
        this.eggColor = AetherItems.EGG_MOA_WHITE;
    }

    public MobMoaWhite(@Nullable World world, boolean tamed) {
        super(world);
        this.setSize(1.0F, 2.0F);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "moa_white");
        this.jumpsRemaining = 4;
        this.eggColor = AetherItems.EGG_MOA_WHITE;
        this.tamed = tamed;
    }

    public void onGround() {
        if (this.onGround ) {
            this.jumpsRemaining = 4;
        }
    }
}
