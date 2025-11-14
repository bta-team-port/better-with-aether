package teamport.aether.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.entity.particle.ParticleArrowGolden;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.world.World;

@Environment(EnvType.CLIENT)
public class ParticleDartEnchanted extends ParticleArrowGolden {
    public ParticleDartEnchanted(World world, double x, double y, double z, double xa, double ya, double za) {
        super(world, x, y, z, xa, ya, za);
        this.bCol = this.gCol = 0.8F;
        this.rCol = (float) (Math.random() * 0.3);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        }

        int val = 7 - this.age * 8 / this.lifetime;
        if (val >= 0) {
            this.tex = TextureRegistry.getTexture("minecraft:particle/puff_" + val);
        } else {
            this.tex = null;
        }

        this.yd += 0.004;
        this.move(this.xd, this.yd, this.zd);
        if (this.y == this.yo) {
            this.xd *= 1.1;
            this.zd *= 1.1;
        }

        this.xd *= 0.96;
        this.yd *= 0.96;
        this.zd *= 0.96;
        if (this.onGround) {
            this.xd *= 0.7;
            this.zd *= 0.7;
        }

    }
}
