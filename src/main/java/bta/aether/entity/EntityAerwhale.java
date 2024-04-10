package bta.aether.entity;

import net.minecraft.core.entity.EntityFlying;
import net.minecraft.core.entity.monster.IEnemy;
import net.minecraft.core.world.World;

public class EntityAerwhale extends EntityFlying implements IEnemy {

    public EntityAerwhale(World world) {
        super(world);
    }

    @Override
    public String getEntityTexture() {
        return "/assets/aether/mobs/aerwhale.png";
    }

    @Override
    public void moveEntityWithHeading(float moveStrafing, float moveForward) {
        this.move(this.xd, 0.0F, this.zd);
        super.moveEntityWithHeading(moveStrafing, moveForward);
    }

    @Override
    public boolean getCanSpawnHere() {
        return this.random.nextInt(20) == 0 && super.getCanSpawnHere();
    }
}
