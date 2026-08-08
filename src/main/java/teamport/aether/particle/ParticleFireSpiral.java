package teamport.aether.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.particle.Particle;
import net.minecraft.client.render.tessellator.TessellatorParticle;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.util.helper.LightIndexHelper;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class ParticleFireSpiral extends Particle {
    private final double centerX;
    private final double centerZ;
    private double angle;
    private double radius;
    private final float oSize;

    public ParticleFireSpiral(World world, double x, double y, double z) {
        super(world, x, y, z, 0.0, 0.0, 0.0);
        this.centerX = x;
        this.centerZ = z;
        this.noPhysics = true;
        this.oSize = this.size;
        this.lifetime = 20;
        this.rCol = this.gCol = this.bCol = 1.0F;
        this.angle = ((double) random.nextFloat()) * Math.PI * 2.0;
        this.radius = 0.25 + ((double) random.nextFloat()) * 0.5;
        this.yd = 0.24;
    }

    @Override
    public void render(@NonNull TessellatorParticle tessellator, float partialTick) {
        float progress = (this.age + partialTick) / this.lifetime;
        this.size = this.oSize * (1.0F - progress * progress * 0.5F);
        super.render(tessellator, partialTick);
    }

    @Override
    public float getBrightness(float partialTick) {
        float decay = MathHelper.clamp((this.age + partialTick) / this.lifetime, 0.0F, 1.0F);
        return super.getBrightness(partialTick) * decay + (1.0F - decay);
    }

    @Override
    public byte getLightIndex(float partialTick) {
        return LightIndexHelper.setBlockLight(super.getLightIndex(partialTick), 15);
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
        this.radius += 0.008 * (age);
        this.x = this.centerX + Math.cos(this.angle) * this.radius;
        this.z = this.centerZ + Math.sin(this.angle) * this.radius;
        this.y += this.yd;
        this.tex = TextureRegistry.getTexture("minecraft:particle/fire");
    }
}
