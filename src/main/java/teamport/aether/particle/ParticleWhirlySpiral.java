package teamport.aether.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.particle.Particle;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.world.World;

@Environment(EnvType.CLIENT)
public class ParticleWhirlySpiral extends Particle {
    private final double centerX;
    private final double centerZ;
    private double angle;
    private double radius;

    public ParticleWhirlySpiral(World world, double x, double y, double z) {
        super(world, x, y, z, 0.0, 0.0, 0.0);
        this.centerX = x;
        this.centerZ = z;
        this.noPhysics = true;
        this.size = 2.5F;
        this.lifetime = 40;
        this.rCol = this.gCol = this.bCol = 1.0F - (random.nextFloat() * 0.3F);
        this.angle = ((double) random.nextFloat()) * Math.PI * 2.0;
        this.radius = 0.25 + ((double) random.nextFloat()) * 0.5;
        this.yd = 0.22;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age++ >= this.lifetime) {
            this.remove();
            return;
        }

        this.angle += 0.50;
        this.radius += 0.002 * (age * 2);

        this.x = this.centerX + Math.cos(this.angle) * this.radius;
        this.z = this.centerZ + Math.sin(this.angle) * this.radius;
        this.y += this.yd;

        int texIndex = 7 - this.age * 8 / this.lifetime;
        if (texIndex >= 0) {
            this.tex = TextureRegistry.getTexture("minecraft:particle/puff_" + texIndex);
        } else {
            this.tex = null;
        }
    }
}
