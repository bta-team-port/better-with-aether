package teamport.aether.entity.vehicle.parachute;

import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlocks;

public class EntityParachuteGold extends EntityParachute {
    public EntityParachuteGold(@NonNull World world) {
        super(world);
        this.maxSpeed = 0.375;
        this.acceleration = 0.0375;
        this.MAX_PACKET_SPEED_CHANGE = 0.078F;
        particleBlock = AetherBlocks.AERCLOUD_GOLD;
        pathParticle = "goldendust";
    }

    public EntityParachuteGold(World world, double x, double y, double z) {
        super(world, x, y, z);
        this.setPos(x, y + (double) this.heightOffset, z);
        this.xd = 0.0;
        this.yd = 0.0;
        this.zd = 0.0;
        this.xo = x;
        this.yo = y;
        this.zo = z;

        this.maxSpeed = 0.375;
        this.acceleration = 0.0375;
        this.MAX_PACKET_SPEED_CHANGE = 0.078F;
        particleBlock = AetherBlocks.AERCLOUD_GOLD;
        pathParticle = "goldendust";
    }

    @Override
    public @NonNull String getEntityTexture() {
        return "/assets/aether/textures/entity/parachute_gold.png";
    }
}
