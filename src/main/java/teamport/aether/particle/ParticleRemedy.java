package teamport.aether.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.particle.Particle;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.world.World;

import static teamport.aether.AetherMod.MOD_ID;

@Environment(EnvType.CLIENT)
public class ParticleRemedy extends Particle {
    private static final int TIM_SCALING = 2;

    public ParticleRemedy(World world, double x, double y, double z, double xa, double ya, double za) {
        super(world, x, y, z, xa, ya, za);
        this.tex = TextureRegistry.getTexture(MOD_ID + ":particle/remedy");
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.xd = xa;
        this.yd = ya;
        this.zd = za;
        this.size = 1.0F;
        this.lifetime = 10 * TIM_SCALING;
    }


    @Override
    public void tick() {
        bindNextTexture();
        this.yd -= 0.01;
        super.tick();
        if (this.onGround) {
            this.remove();
        }
    }

    private void bindNextTexture() {
        if (age > 8 * TIM_SCALING) {
            this.tex = TextureRegistry.getTexture(MOD_ID + ":particle/remedy_2");
            return;
        }
        if (age > 4 * TIM_SCALING) {
            this.tex = TextureRegistry.getTexture(MOD_ID + ":particle/remedy_1");
        }
    }
}
