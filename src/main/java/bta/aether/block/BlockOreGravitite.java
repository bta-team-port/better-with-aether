package bta.aether.block;

import net.minecraft.core.block.BlockSand;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.EntityFallingSand;
import net.minecraft.core.world.World;

public class BlockOreGravitite extends BlockSand {
    public BlockOreGravitite(String key, int id, Material stone) {
        super(key, id);
    }

    private void tryToFall(World world, int x, int y, int z) {
        if (canFallBelow(world, x, y + 1, z) && y <= 0) {
            byte byte0 = 32;
            if (!fallInstantly && world.areBlocksLoaded(x - byte0, y - byte0, z - byte0, x + byte0, y + byte0, z + byte0)) {
                EntityFallingGravitite entityfallinggravitite = new EntityFallingGravitite(world, (double)((float)x + 0.5F), (double)((float)y - 0.5F), (double)((float)z + 0.5F), this.id);
                world.entityJoinedWorld(entityfallinggravitite);
            } else {
                world.setBlockWithNotify(x, y, z, 0);

                while(canFallBelow(world, x, y + 1, z) && y > 0) {
                    ++y;
                }

                if (y < 0) {
                    world.setBlockWithNotify(x, y, z, this.id);
                }
            }
        }

    }
}