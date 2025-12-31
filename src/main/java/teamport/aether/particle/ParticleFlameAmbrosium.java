package teamport.aether.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.entity.particle.Particle;
import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;

import static teamport.aether.AetherMod.MOD_ID;

@Environment(EnvType.CLIENT)
public class ParticleFlameAmbrosium extends Particle {
    private final float originalScale;

    public ParticleFlameAmbrosium(World world, double x, double y, double z, double xd, double yd, double zd) {
        super(world, x, y, z, xd, yd, zd);
        this.xd = this.xd * 0.01 + xd;
        this.yd = this.yd * 0.01 + yd;
        this.zd = this.zd * 0.01 + zd;
        this.x += (this.random.nextFloat() - this.random.nextFloat()) * 0.05F;
        this.y += (this.random.nextFloat() - this.random.nextFloat()) * 0.05F;
        this.z += (this.random.nextFloat() - this.random.nextFloat()) * 0.05F;
        this.originalScale = this.size;
        this.rCol = this.gCol = this.bCol = 1.0F;
        this.lifetime = (int) (8.0 / (0.2 + 0.8 * (this.random.nextInt(10000) / 10000.0))) + 4;
        this.noPhysics = true;
        this.tex = TextureRegistry.getTexture(MOD_ID + ":particle/flameambrosium");
    }

    @Override
    public void render(Tessellator t, float partialTick, double xOff, double yOff, double zOff, float xa, float ya, float za, float xa2, float za2) {
        float s = (this.age + partialTick) / this.lifetime;
        this.size = this.originalScale * (1.0F - s * s * 0.5F);
        super.render(t, partialTick, xOff, yOff, zOff, xa, ya, za, xa2, za2);
    }

    @Override
    public float getBrightness(float partialTick) {
        float decay = MathHelper.clamp((this.age + partialTick) / this.lifetime, 0.0F, 1.0F);
        return super.getBrightness(partialTick) * decay + (1.0F - decay);
    }

    @Override
    public int getLightmapCoord(float partialTick) {
        return LightmapHelper.setBlocklightValue(super.getLightmapCoord(partialTick), 15);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        }

        this.move(this.xd, this.yd, this.zd);
        this.xd *= 0.96;
        this.yd *= 0.96;
        this.zd *= 0.96;
        if (this.onGround) {
            this.xd *= 0.7;
            this.zd *= 0.7;
        }

    }
}
