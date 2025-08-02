package teamport.aether.entity.projectile;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.world.World;

public class ProjectileElementLightning extends ProjectileElementBase{
    public ProjectileElementLightning(World world) {
        super(world);
    }

    public ProjectileElementLightning(World world, Mob owner) {
        super(world, owner);
        this.initProjectile();
    }

    public ProjectileElementLightning(World world, double x, double y, double z) {
        super(world, x, y, z);
    }
}
