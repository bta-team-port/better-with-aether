package bta.aether.entity;

import net.minecraft.core.entity.EntityFlying;
import net.minecraft.core.entity.animal.IAnimal;
import net.minecraft.core.world.World;

public class EntityAerwhale extends EntityFlying implements IAnimal {

    public EntityAerwhale(World world) {
        super(world);
        this.health = 20;
    }

    @Override
    public String getEntityTexture() {
        return "/assets/aether/mobs/Mob_Aerwhale.png";
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
