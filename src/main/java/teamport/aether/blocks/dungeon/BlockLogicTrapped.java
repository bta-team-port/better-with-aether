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

import java.util.Random;

import static net.minecraft.core.Global.TICKS_PER_SECOND;

public class BlockLogicTrapped extends BlockLogicDungeon {
    public final Class<? extends Entity> monster;
    public final Block<?> breakResult;
    public final Block<?> replaceOnClear;
    public final int cooldown;

    public BlockLogicTrapped(Block<?> block, Block<?> breakResult, Block<?> replaceOnClear, Class<? extends Entity> monster, int cooldown) {
        super(block, Material.stone);
        block.setTicking(true);
        this.monster = monster;
        this.breakResult = breakResult;
        this.replaceOnClear = replaceOnClear;
        this.cooldown = Math.max(cooldown, TICKS_PER_SECOND);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (world.isClientSide) {
            return;
        }
        if (world.getBlockMetadata(x, y, z) == 1) {
            world.setBlockMetadata(x, y, z, 0);
        }
    }

    @Override
    public int tickDelay() {
        return cooldown;
    }

    @Override
    public @Nullable ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
        return breakResult.getBreakResult(world, dropCause, meta, tileEntity);
    }


    @Override
    public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
        if (EnvironmentHelper.isClientWorld()) {
            return;
        }
        if (!(entity instanceof Player) || !world.getDifficulty().canHostileMobsSpawn() || world.getBlockMetadata(x, y, z) == 1) {
            return;
        }
        Entity theMonster = EntityDispatcher.createEntityInWorld(this.monster, world);
        if (theMonster == null) {
            return;
        }
        int tries = 16;
        theMonster.spawnInit();
        while (tries-- > 0) {
            final double angleRad = Math.toRadians(world.rand.nextInt(360));
            final float distance = 2 + world.rand.nextInt(2) - ((float) world.rand.nextInt(11) / 10);
            double spawnX = x + 0.5 + distance * Math.cos(angleRad);
            double spawnZ = z + 0.5 + distance * Math.sin(angleRad);
            double spawnY = y + 1.25;
            theMonster.moveTo(spawnX, y + 1.0, spawnZ, 0.0f, 0.0f);
            if (!world.checkIfAABBIsClear(theMonster.bb)) {
                continue;
            }
            world.entityJoinedWorld(theMonster);
            this.spawnParticles(world, spawnX, spawnY, spawnZ);
            this.playSound(world, x, y, z, entity, theMonster);
            if (theMonster instanceof MobSentry) {
                ((Player) entity).triggerAchievement(AetherAchievements.SENTRY_DEPLOYED);
            }
            world.setBlockMetadata(x, y, z, 1);
            return;
        }
    }

    private void playSound(World world, int x, int y, int z, Entity entity, Entity theMonster) {
        world.playSoundEffect(entity, SoundCategory.ENTITY_SOUNDS, x, y, z, "mob.ghast.fireball", 1.0f, 1.0f);
        world.playSoundAtEntity(entity, theMonster, "mob.ghast.fireball", 0.25F, 0.75F);
    }

    private void spawnParticles(World world, double x, double y, double z) {
        for (int l = 0; l < 8; ++l) {
            double angle = Math.toRadians(l * 45.0);
            ParticleMaker.spawnParticle(world, "snowshovel", x, y, z, -Math.cos(angle) / 15.0, 0.03, -Math.sin(angle) / 15.0, 0);
            ParticleMaker.spawnParticle(world, "snowshovel", x, y, z, -Math.cos(angle) / 15.0, 0.03, -Math.sin(angle) / 15.0, 0);
            ParticleMaker.spawnParticle(world, "largesmoke", x, y, z, -Math.cos(angle) / 15.0, 0.03, -Math.sin(angle) / 15.0, 0);
        }
    }
}
