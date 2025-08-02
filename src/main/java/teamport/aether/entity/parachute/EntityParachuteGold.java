package teamport.aether.entity.parachute;

import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import teamport.aether.mixin.accessors.EntityAccessor;

public class EntityParachuteGold extends EntityParachute{
    public EntityParachuteGold(@Nullable World world) {
        super(world);
    }
@Override
    public void tick() {
    super.tick();

    double x = this.x + ((EntityAccessor) this).getRandom().nextDouble() * 0.75 * 2.0 - 0.75;
    double y = this.bb.minY - 0.5 + ((EntityAccessor) this).getRandom().nextDouble() * 0.75 * 2.0 - 0.75;
    double z = this.z + ((EntityAccessor) this).getRandom().nextDouble() * 0.75 * 2.0 - 0.75;
    world.spawnParticle("goldendust", x, y, z, 0.0, 0.0, 0.0, 0);

    if (this.passenger == null) {
        this.remove();
    }

    this.move(this.xd, this.yd, this.zd);
    if (this.yd < -0.2) {
        this.yd *= 0.5F;
    }

    this.xd *= 0.9F;
    this.zd *= 0.9F;

    if (this.onGround) {
        this.ejectRider();
        this.remove();
    }
}

}
