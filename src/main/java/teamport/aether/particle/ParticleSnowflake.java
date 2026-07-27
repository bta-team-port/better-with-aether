package teamport.aether.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.particle.Particle;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;

import static teamport.aether.AetherMod.MOD_ID;

@Environment(EnvType.CLIENT)
public class ParticleSnowflake extends Particle {
    public ParticleSnowflake(World world, double x, double y, double z, double xa, double ya, double za) {
        super(world, x, y, z, xa, ya, za);
        this.tex = TextureRegistry.getTexture(MOD_ID + ":particle/snowflake");
        this.setScale((this.random.nextFloat() * 0.2F + 0.8F));
        float speed = (float) (Math.random() * 0.05F + 0.05F);
        float dd = MathHelper.sqrt(this.xd * this.xd + this.yd * this.yd + this.zd * this.zd);
        if (dd > 0.0F) {
            this.xd = this.xd / dd * speed;
            this.zd = this.zd / dd * speed;
        }
        this.yd = -0.02F - (this.random.nextFloat() * 0.01F);
        this.gravity = 0.01F;
        this.lifetime = (int) (20.0 / (0.5 + 0.5 * (this.random.nextInt(10000) / 10000.0)));
        this.xd *= 0.8F;
        this.zd *= 0.8F;
    }

    @Override
    public void tick() {
        super.tick();

        this.xd += (this.random.nextFloat() - this.random.nextFloat()) * 0.002F;
        this.zd += (this.random.nextFloat() - this.random.nextFloat()) * 0.002F;

        this.xd *= 0.99F;
        this.zd *= 0.99F;

        if (this.onGround) {
            this.lifetime = Math.min(this.lifetime, this.age + 5);
        }
    }
}
