package teamport.aether.entity.aerwhale;

import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import teamport.aether.entity.MobAetherAnimal;

public class MobAerwhale extends MobAetherAnimal {
    public MobAerwhale(World world) {
        super(world);
        setSize(8.0f, 4.0f);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "aerwhale");
        this.viewScale = 4f;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.yd *= 0.0;
    }

    public void causeFallDamage(float distance) {
    }

    public boolean canSpawnHere() {
        return false;
    }

}
