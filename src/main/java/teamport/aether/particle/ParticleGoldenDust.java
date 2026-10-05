package teamport.aether.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.particle.Particle;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicLeavesBase;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import org.joml.primitives.AABBdc;

@Environment(EnvType.CLIENT)
public class ParticleGoldenDust extends Particle {
    public ParticleGoldenDust(World world, double x, double y, double z, double xa, double ya, double za) {
        super(world, x, y, z, xa, ya, za);
        this.bCol = 0.1F;
        this.gCol = 0.8F;
        this.rCol = 1.0F;
        this.gravity = 0.125F / 3;
        this.size /= 2.0F;
        this.lifetime *= 8;
        this.yd = 0.0;
        this.xd = 0.0;
        this.zd = 0.0;
    }

    @Override
    protected AABBdc getCollisionBox(TilePos tilePos) {
        Block<?> block = this.world.getBlockType(tilePos);
        return Block.hasLogicClass(block, BlockLogicLeavesBase.class) ? null : super.getCollisionBox(tilePos);
    }

    @Override
    public void tick() {
        float windDirection = this.world.getWorldType().getWindManager().getWindDirection(this.world, (float) this.x, (float) this.y, (float) this.z);
        float windIntensity = this.world.getWorldType().getWindManager().getWindIntensity(this.world, (float) this.x, (float) this.y, (float) this.z) * 0.01F;
        double dx = Math.cos(windDirection * Math.PI * 2.0) * (windIntensity / 4.0);
        double dz = Math.sin(windDirection * Math.PI * 2.0) * (windIntensity / 4.0);
        this.xd += dx / 2.0;
        this.zd += dz / 2.0;
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        }

        int val = 7 - this.age * 8 / this.lifetime;
        if (val >= 0) {
            this.tex = TextureRegistry.getTexture("minecraft:particle/puff_" + val);
        } else {
            this.tex = null;
        }

        this.yd -= 0.04 * this.gravity;
        if (this.onGround) {
            this.xd *= 0.0;
            this.zd *= 0.0;
        }

        this.move(this.xd, this.yd, this.zd);
    }
}
