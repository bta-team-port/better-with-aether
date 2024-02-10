package bta.aether.entity;

import net.minecraft.client.entity.fx.EntityPortalFX;
import net.minecraft.core.world.World;

public class EntityPortalAetherFX extends EntityPortalFX {

    public EntityPortalAetherFX(World world, double d, double d1, double d2, double d3, double d4, double d5) {
        super(world, d, d1, d2, d3, d4, d5);
        float f = this.random.nextFloat() * 0.6F + 0.4F;
        this.particleGreen = this.particleRed = this.particleBlue = f;
        this.particleGreen *= 0.2F;
        this.particleRed *= 0.2F;
    }
}

