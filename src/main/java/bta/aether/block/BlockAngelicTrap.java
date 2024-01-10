package bta.aether.block;

import bta.aether.entity.EntitySentry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;

public class BlockAngelicTrap extends Block {
    public BlockAngelicTrap(String key, int id, Material material) {
        super(key, id, material);
    }

    public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
        if (world.rand.nextInt(1) == 0 && entity instanceof EntityPlayer) {
            Entity sentry = new EntitySentry(world);
            sentry.spawnInit();
            sentry.moveTo(x + 0.5, y + 1, z + 0.5, 0.0f, 0.0f);
            world.entityJoinedWorld(sentry);
            world.playSoundAtEntity(entity, "mob.ghast.fireball", 0.025F, 0.75F);
            world.setBlockWithNotify(x, y, z, AetherBlocks.stoneAngelicLocked.id);
        }
    }
}
