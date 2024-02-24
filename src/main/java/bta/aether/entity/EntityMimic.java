package bta.aether.entity;

import bta.aether.block.AetherBlocks;
import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;

public class EntityMimic extends EntityMonster {
    public EntityMimic(World world) {
        super(world);
        this.health = 20;
    }

    @Override
    public void tick() {
        super.tick();
        if (world.players.stream().noneMatch(entityPlayer -> this.distanceToSqr(entityPlayer) < 10000) && world.getBlockId((int) this.x, (int) this.y, (int) this.z) == 0) {
            world.setBlockWithNotify((int) this.x, (int) this.y, (int) this.z, AetherBlocks.chestMimic.id);
            this.remove();
        }
    }

    public String getEntityTexture() {
            return "/assets/aether/mobs/Mimic2.png";
        }

    public String getDefaultEntityTexture() {
        return "/assets/aether/mobs/Mimic2.png";
    }
}
