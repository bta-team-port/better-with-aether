package bta.aether.block;

import bta.aether.Aether;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;

public class BlockMobTrap extends BlockDungeon {
    private final Class<? extends Entity> monster;

    public BlockMobTrap(String key, int id, Material material, int replacementID, Class<? extends Entity> monster) {
        super(key, id, material, replacementID);
        this.monster = monster;
    }

    public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
        if (world.rand.nextInt(3) == 0 && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            try {
                int mobs = 1 + world.rand.nextInt(2);
                int tries = 50;
                while (tries-- > 0 && mobs > 0) {
                    final double angleRad = Math.toRadians(world.rand.nextInt(361));
                    final float distance = 4 + world.rand.nextInt(4) - ((float) world.rand.nextInt(11) / 10);
                    double spawnX = x + 0.5 + distance * Math.cos(angleRad);
                    double spawnZ = z + 0.5 + distance * Math.sin(angleRad);

                    if (world.getBlockId((int) spawnX, y + 1, (int) spawnZ) != 0) continue;

                    Entity monster = this.monster.getConstructor(World.class).newInstance(world);
                    monster.spawnInit();
                    monster.moveTo(spawnX, y + 1, spawnZ, 0.0f, 0.0f);
                    if (monster.isInWall()) {
                        monster.remove();
                        continue;
                    }
                    world.entityJoinedWorld(monster);
                    mobs--;

                    for (int l = 0; l < 8; ++l) {
                        double angle = Math.toRadians(l * 45);
                        world.spawnParticle("snowshovel", spawnX, y + 1.25, spawnZ, -Math.cos(angle) / 15.0, 0.03, -Math.sin(angle) / 15.0);
                        world.spawnParticle("snowshovel", spawnX, y + 1.25, spawnZ, -Math.cos(angle) / 15.0, 0.03, -Math.sin(angle) / 15.0);
                        world.spawnParticle("largesmoke", spawnX, y + 1.25, spawnZ, -Math.cos(angle) / 15.0, 0.03, -Math.sin(angle) / 15.0);
                    }

                    world.playSoundEffect(player, 1003, x, y, z, 0);
                    world.playSoundAtEntity(player, monster, "mob.ghast.fireball", 0.25F, 0.75F);
                    world.setBlockWithNotify(x, y, z, this.replacementID);
                }
            }
            catch(Exception exception) {
                Aether.LOGGER.error("Failed to spawn monster at trap!");
                Aether.LOGGER.error(String.valueOf(exception));
            }
        }
    }
}
