package teamport.aether.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.entity.particle.ParticleLeaf;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.world.World;

@Environment(EnvType.CLIENT)
public class ParticleAetherLeaf extends ParticleLeaf {
    public ParticleAetherLeaf(World world, double x, double y, double z, double xa, double ya, double za) {
        super(world, x, y, z, xa, ya, za);
        this.tex = TextureRegistry.getTexture("aether:block/leaves/skyroot_fancy");
    }
}
