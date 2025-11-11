package teamport.aether.blocks.dungeon;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import teamport.aether.achievements.AetherAchievements;
import teamport.aether.entity.monster.sentry.MobSentry;
import teamport.aether.helper.ParticleMaker;
import turniplabs.halplibe.helper.EnvironmentHelper;

public class BlockLogicTrapped extends BlockLogicDungeon {
    public final Class<? extends Entity> monster;
    public final Block<?> breakResult;
    public final Block<?> replaceOnClear;
    public final int CHANCE;

    public BlockLogicTrapped(Block<?> block, Block<?> breakResult, Block<?> replaceOnClear, Class<? extends Entity> monster, int CHANCE) {
        super(block, Material.stone);
        this.monster = monster;
        this.breakResult = breakResult;
        this.replaceOnClear = replaceOnClear;
        this.CHANCE = CHANCE > 0 ? CHANCE : 2;
    }

    @Override
    public ItemStack @Nullable [] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
        return breakResult.getBreakResult(world, dropCause, meta, tileEntity);
    }


    public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
        if (EnvironmentHelper.isClientWorld()) return;
        if (!(entity instanceof Player)) {
            return;
        }
        if (world.rand.nextInt(CHANCE) != 0) {
            return;
        }
        int tries = 16;
        while (tries-- > 0) {
            final double angleRad = Math.toRadians(world.rand.nextInt(360));
            final float distance = 2 + world.rand.nextInt(2) - ((float) world.rand.nextInt(11) / 10);
            double spawnX = x + 0.5 + distance * Math.cos(angleRad);
            double spawnZ = z + 0.5 + distance * Math.sin(angleRad);
            double spawnY = y + 1.25;
            if (!isSafe(world, spawnX, spawnY, spawnZ)) continue;
            Entity monster = EntityDispatcher.createEntityInWorld(this.monster, world);
            if (monster == null) continue;
            monster.spawnInit();
            monster.moveTo(spawnX, y + 1, spawnZ, 0.0f, 0.0f);
            world.entityJoinedWorld(monster);
            spawnDecorations(world, x, y, z, spawnX, spawnY, spawnZ, entity, monster);
            if (monster instanceof MobSentry) {
                ((Player) entity).triggerAchievement(AetherAchievements.SENTRY_DEPLOYED);
            }
            return;
        }
    }

    private void spawnDecorations(World world, int x, int y, int z, double spawnX, double spawnY, double spawnZ, Entity player, Entity monster) {
        for (int l = 0; l < 8; ++l) {
            double angle = Math.toRadians(l * 45);
            ParticleMaker.spawnParticle(world, "snowshovel", spawnX, spawnY, spawnZ, -Math.cos(angle) / 15.0, 0.03, -Math.sin(angle) / 15.0, 0);
            ParticleMaker.spawnParticle(world, "snowshovel", spawnX, spawnY, spawnZ, -Math.cos(angle) / 15.0, 0.03, -Math.sin(angle) / 15.0, 0);
            ParticleMaker.spawnParticle(world, "largesmoke", spawnX, spawnY, spawnZ, -Math.cos(angle) / 15.0, 0.03, -Math.sin(angle) / 15.0, 0);
        }
        world.playSoundEffect(player, SoundCategory.ENTITY_SOUNDS, x, y, z, "mob.ghast.fireball", 1.0f, 1.0f);
        world.playSoundAtEntity(player, monster, "mob.ghast.fireball", 0.25F, 0.75F);
    }

    private static boolean isSafe(World world, double x, double y, double z) {
        int ix = (int) Math.round(x);
        int iy = (int) Math.round(y);
        int iz = (int) Math.round(z);
        return !world.isBlockNormalCube(ix, iy, iz) && !world.isBlockNormalCube(ix, iy + 1, iz);
    }
}
