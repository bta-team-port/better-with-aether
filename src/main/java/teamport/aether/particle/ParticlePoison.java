package teamport.aether.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.entity.particle.Particle;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;

import static teamport.aether.AetherMod.MOD_ID;

@Environment(EnvType.CLIENT)
public class ParticlePoison extends Particle {
    public ParticlePoison(World world, double x, double y, double z, double xa, double ya, double za) {
        super(world, x, y, z, xa, ya, za);
        this.tex = TextureRegistry.getTexture(MOD_ID + ":particle/poison");
        this.xd = this.zd = 0;
        // rising
        this.yd = ya + (Math.random() * 0.4F);
        float speed = 0.15F;
        float dd = MathHelper.sqrt(this.xd * this.xd + this.yd * this.yd + this.zd * this.zd);
        this.yd = this.yd / dd * speed * 0.4 + 0.1;
    }


    @Override
    public void tick() {
        this.age++;
        if (this.age + 4 < this.lifetime) {
            this.yo = this.y;
            this.move(this.xd, this.yd, this.zd);
            this.yd -= 0.04 * this.gravity;
            this.yd *= 0.8;
            return;
        }
        if (this.age + 2 >= this.lifetime && this.age < this.lifetime) {
            this.tex = TextureRegistry.getTexture(MOD_ID + ":particle/poison_pop");
        }
        this.setScale(1.1F);
        if (this.age >= this.lifetime) {
            this.remove();
        }
    }
}
