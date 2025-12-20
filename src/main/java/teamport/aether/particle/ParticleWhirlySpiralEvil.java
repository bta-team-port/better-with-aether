package teamport.aether.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.entity.particle.Particle;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.world.World;

@Environment(EnvType.CLIENT)
public class ParticleWhirlySpiralEvil extends Particle {
    private final double centerX;
    private final double centerZ;
    private double angle;
    private double radius;

    public ParticleWhirlySpiralEvil(World world, double x, double y, double z) {
        super(world, x, y, z, 0.0, 0.0, 0.0);
        this.centerX = x;
        this.centerZ = z;
        this.noPhysics = true;
        this.viewScale = 10.0F;
        this.size = 3.0F;
        this.lifetime = 20;
        this.rCol = this.gCol = this.bCol = (float) (Math.random() * 0.3);
        this.setRot(0.25F + this.random.nextFloat() * 0.1F, 0.25F + this.random.nextFloat() * 0.1F);
        this.angle = this.random.nextDouble() * Math.PI * 2.0;
        this.radius = 0.25 + this.random.nextDouble() * 0.5;
        this.yd = 0.44;
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

        this.angle += 0.75;
        this.radius += 0.008 * (age * 4);

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
