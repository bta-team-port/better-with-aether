package teamport.aether.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.entity.particle.Particle;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.world.World;

import static teamport.aether.AetherMod.MOD_ID;

@Environment(EnvType.CLIENT)
public class ParticleRemedy extends Particle {

    // TODO needs to just pop horizontally and only move horizontaly
    public ParticleRemedy(World world, double x, double y, double z, double xd, double yd, double zd) {
        super(world, x, y, z, xd, yd, zd);
        this.tex = TextureRegistry.getTexture(MOD_ID + ":particle/remedy");
        this.noPhysics = false;
    }

    public Particle setPower(float power) {
        return super.setPower(power);
    }

    public Particle setScale(float scale) {
        return super.setScale(scale);
    }

    @Override
    public void render(Tessellator t, float partialTick, double xOff, double yOff, double zOff, float xa, float ya, float za, float xa2, float za2) {
        super.render(t, partialTick, xOff, yOff, zOff, xa, ya, za, xa2, za2);
    }

    public void tick() {
        super.tick();

    }

}
