package teamport.aether.entity.moa;

import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import teamport.aether.items.AetherItems;

public class MobMoaBlack extends MobMoa {
    public MobMoaBlack(@Nullable World world) {
        super(world);
        this.setSize(1.0F, 2.0F);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "moa_black");
        this.jumpsRemaining = 8;
        this.eggColor = AetherItems.EGG_MOA_BLUE;
    }

    public void onGround() {
        if (this.onGround ) {
            this.jumpsRemaining = 8;
        }
    }
}
