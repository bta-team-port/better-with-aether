package teamport.aether.entity.vehicle.parachute;

import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;

public class EntityParachuteGold extends EntityParachute {
    public EntityParachuteGold(@Nullable World world) {
        super(world);
        maxSpeed = 0.15F;
    }

    @Override
    public String getPathParticle() {
        return "goldendust";
    }

    @Override
    protected void handleParachuteMovement() {
        this.move(this.xd, this.yd, this.zd);
        if (this.yd < -0.2) {
            this.yd *= 0.40F;
        }

        this.xd *= 0.98F;
        this.zd *= 0.98F;
    }
}
