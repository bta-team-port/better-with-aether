package bta.aether.entity;

import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.world.World;

public class EntityValk extends EntityMonster {
    public EntityValk(World world) {
        super(world);
    }
    public String getEntityTexture() {
        return "/assets/aether/mobs/valkyrie.png";
    }
    public String getDefaultEntityTexture() {
        return "/assets/aether/mobs/valkyrie2.png";
    }
}
