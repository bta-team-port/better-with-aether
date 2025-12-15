package teamport.aether.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.entity.particle.Particle;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.world.World;

@Environment(EnvType.CLIENT)
public class ParticleWhirlySpiral extends Particle {
    private final Entity whirlyEntity;

    public ParticleWhirlySpiral(World world, double x, double y, double z, Entity entity, boolean evil) {
        super(world, x, y, z, 0.0, 0.125, 0.0);
        this.whirlyEntity = entity;
        this.noPhysics = true;
        this.viewScale = 10.0F;
        if (evil) {
            this.size = 2.0F + this.random.nextFloat();
        } else {
            this.size = 1.0F + this.random.nextFloat();
        }
        this.lifetime = this.random.nextInt(60);
        if (evil) {
            this.rCol = this.gCol = this.bCol = (float)(Math.random() * 0.3);
        } else {
            this.rCol = this.gCol = this.bCol = (float)((Math.random() + 0.1) * 1.8);
        }
        this.setRot(0.25F + this.random.nextFloat() * 0.1F, 0.25F + this.random.nextFloat() * 0.1F);
        this.yd = 0.115 + this.random.nextFloat() * 0.02;
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

        double entityX = whirlyEntity.x;
        double entityY = whirlyEntity.y;
        double entityZ = whirlyEntity.z;

        double distHoriz = Math.sqrt((this.x - entityX) * (this.x - entityX) + (this.z - entityZ) * (this.z - entityZ));
        double vertOffset = this.y - (entityY + whirlyEntity.bbHeight + 0.125 - 0.25);
        double angle = Math.atan2(entityX - this.x, entityZ - this.z) / (Math.PI / 180.0);
        angle += 160.0;
        double pullFactor = (distHoriz * 2.5 - vertOffset) * 0.1;

        this.xd = -Math.cos(angle * (Math.PI / 180.0)) * pullFactor;
        this.zd = Math.sin(angle * (Math.PI / 180.0)) * pullFactor;
        this.yd = 0.115;
        this.move(this.xd, this.yd, this.zd);

        int val = 7 - this.age * 8 / this.lifetime;
        if (val >= 0) {
            this.tex = TextureRegistry.getTexture("minecraft:particle/puff_" + val);
        } else {
            this.tex = null;
        }
    }
}
