package teamport.aether.block.dungeon;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.world.World;

public interface AetherBlockTriggerStandOn {
    default void onEntityStandOn(World world, int x, int y, int z, Entity entity) {}
}
