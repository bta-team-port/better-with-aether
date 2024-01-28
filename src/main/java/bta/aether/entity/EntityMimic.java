package bta.aether.entity;

import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.world.World;

public class EntityMimic extends EntityMonster {
    public EntityMimic(World world) {
        super(world);
        this.health = 20;
    }
    public String getEntityTexture() {
            return "/assets/aether/mobs/Mimic2.png";
        }

    public String getDefaultEntityTexture() {
        return "/assets/aether/mobs/Mimic2.png";
    }
}
