package teamport.aether.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.world.World;

import java.lang.reflect.InvocationTargetException;

public class BlockLogicTrapped extends BlockLogic {
    public final Class<? extends Entity> monster;
    public final int replacementBlock;

    public BlockLogicTrapped(Block<?> block, int replacementBlock, Class<? extends Entity> monster) {
        super(block, Material.stone);
        this.monster = monster;
        this.replacementBlock = replacementBlock;
    }

    public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
        if (world.rand.nextInt(3) == 0 && entity instanceof Player) {
            Player player = (Player) entity;
            {
                int mobs = 1 + world.rand.nextInt(2);
                int tries = 50;
                while (tries-- > 0 && mobs > 0) {
                    final double angleRad = Math.toRadians(world.rand.nextInt(361));
                    final float distance = 2 + world.rand.nextInt(2) - ((float) world.rand.nextInt(11) / 10);
                    double spawnX = x + 0.5 + distance * Math.cos(angleRad);
                    double spawnZ = z + 0.5 + distance * Math.sin(angleRad);

                    if (world.getBlockId((int) spawnX, y + 1, (int) spawnZ) != 0) continue;

                    Entity monster;
                    try {
                        monster = this.monster.getConstructor(World.class).newInstance(world);
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                             NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
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
                        world.spawnParticle("snowshovel", spawnX, y + 1.25, spawnZ, -Math.cos(angle) / 15.0, 0.03, -Math.sin(angle) / 15.0, 0);
                        world.spawnParticle("snowshovel", spawnX, y + 1.25, spawnZ, -Math.cos(angle) / 15.0, 0.03, -Math.sin(angle) / 15.0, 0);
                        world.spawnParticle("largesmoke", spawnX, y + 1.25, spawnZ, -Math.cos(angle) / 15.0, 0.03, -Math.sin(angle) / 15.0, 0);
                    }

                    world.playSoundEffect(player, SoundCategory.ENTITY_SOUNDS, x, y, z, "mob.ghast.fireball", 1.0f, 1.0f);
                    world.playSoundAtEntity(player, monster, "mob.ghast.fireball", 0.25F, 0.75F);
                    world.setBlockWithNotify(x, y, z, this.replacementBlock);
                }
            }
        }
    }
}
