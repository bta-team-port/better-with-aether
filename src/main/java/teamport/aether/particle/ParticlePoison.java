package teamport.aether.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.entity.particle.Particle;
import net.minecraft.client.entity.particle.ParticleArrowGolden;
import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;

import static teamport.aether.AetherMod.MOD_ID;

@Environment(EnvType.CLIENT)
public class ParticlePoison extends Particle {

    public ParticlePoison(World world, double x, double y, double z, double xd, double yd, double zd) {
        super(world, x, y, z, xd, yd, zd);
        this.tex = TextureRegistry.getTexture(MOD_ID + ":particle/poison");
        this.xd *= 0.01;
        this.yd *= yd;
        this.zd *= 0.01;
    }

    @Override
    public void render(Tessellator t, float partialTick, double xOff, double yOff, double zOff, float xa, float ya, float za, float xa2, float za2) {
        float s = ((float) this.age + partialTick) / (float) this.lifetime;
        this.size = this.size * (1.0F - s * s * 0.5F);
        super.render(t, partialTick, xOff, yOff, zOff, xa, ya, za, xa2, za2);
    }
}
