package teamport.aether.particle;

import net.minecraft.client.render.particle.Particle;
import net.minecraft.client.render.tessellator.TessellatorParticle;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.util.helper.LightIndexHelper;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import teamport.aether.helper.ColorHelper;

import java.util.Locale;

import static teamport.aether.AetherMod.MOD_ID;

public class ParticleChaotic extends Particle {
    public static final int PARTICLE_COUNT = 11;
    protected final float originalScale;
    protected final int data;

	public ParticleChaotic(World world, double x, double y, double z, double xa, double ya, double za, int data) {
		super(world, x, y, z, xa, ya, za);
		this.xd = this.xd * 0.1;
		this.yd = this.yd * 0.1;
		this.zd = this.zd * 0.1;
		this.xd += xa;
		this.yd += ya;
		this.zd += za;
		this.tex = TextureRegistry.getTexture(String.format("%s:particle/chaotic/%d", MOD_ID, random.nextInt(PARTICLE_COUNT)));
		this.originalScale = this.size;
		this.noPhysics = false;
		this.lifetime = Math.round((10 + random.nextInt(12)) / (random.nextFloat() + 0.1F));
        this.data = data;
	}

    @Override
	public void render(@NotNull TessellatorParticle tessellatorParticle, float partialTick) {
		float s = (this.age + partialTick) / this.lifetime;
		this.size = this.originalScale * (1.0F - s * s * 0.75F);
        ColorHelper.ColorF color = ColorHelper.colorLerp(this.data);
        this.rCol = color.r();
        this.gCol = color.g();
        this.bCol = color.b();
		super.render(tessellatorParticle, partialTick);
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

		this.xd *= 0.87;
		this.yd *= 0.87;
		this.zd *= 0.87;
		if (this.onGround) {
			this.xd *= 0.5;
			this.zd *= 0.5;
		}
	}

    @Override
    public byte calcLightIndex(float pt) {
        return LightIndexHelper.lightIndex2i(15, 15);
    }

    @Override
    public float getBrightness(float partialTick) {
        return 1.0F;
    }

}
