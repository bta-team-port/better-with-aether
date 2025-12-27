package teamport.aether.block.dungeon;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import teamport.aether.achievements.AetherAchievements;
import teamport.aether.entity.monster.sentry.MobSentry;
import teamport.aether.helper.ParticleMaker;
import turniplabs.halplibe.helper.EnvironmentHelper;

import java.util.Random;

public class BlockLogicTrapped extends BlockLogicDungeon implements AetherBlockTriggerStandOn {
    public final Class<? extends Entity> monster;
    private final Block<?> breakResult;
    private final Block<?> replaceOnClear;
    private final int cooldown;

    public BlockLogicTrapped(Block<?> block, Block<?> breakResult, Block<?> replaceOnClear, Class<? extends Entity> monster, int cooldown) {
        super(block, Material.stone);
        block.setTicking(true);
        this.monster = monster;
        this.breakResult = breakResult;
        this.replaceOnClear = replaceOnClear;
        this.cooldown = cooldown;
    }

    @Override
    public @Nullable ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
        return breakResult.getBreakResult(world, dropCause, meta, tileEntity);
    }

    @Override
    public int tickDelay() {
        return cooldown;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (!world.isClientSide && world.getBlockMetadata(x, y, z) == 1) {
            world.setBlockMetadata(x, y, z, 0);
        }
    }

    @Override
    public void onEntityStandOn(World world, int x, int y, int z, Entity entity) {
        this.onEntityWalking(world, x, y, z, entity);
    }

    @Override
    public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
        if (EnvironmentHelper.isClientWorld()
            || !(entity instanceof Player)
            || world.getBlockMetadata(x, y, z) != 0
        ) {
            return;
        }
        this.triggerTrap(world, x, y, z, entity);
    }

    private void triggerTrap(World world, int x, int y, int z, Entity entity) {
        Entity theMonster = EntityDispatcher.createEntityInWorld(this.monster, world);
        if (theMonster == null) {
            return;
        }
        theMonster.spawnInit();

        int distance = 6 + world.rand.nextInt(2);
        while (distance --> 0) {
            int tries = 16;
            while (tries --> 0) {
                final double angleRad = Math.toRadians(world.rand.nextInt(360));

                float actualDistance = distance - ((float) world.rand.nextInt(11) / 10);
                double spawnX = x + actualDistance * Math.cos(angleRad);
                double spawnZ = z + actualDistance * Math.sin(angleRad);
                double spawnY = y + 1.0;

                theMonster.moveTo(spawnX, spawnY, spawnZ, 0.0f, 0.0f);

                if (world.getIsAnySolidGround(theMonster.bb)) {
                    continue;
                }

                ///  checks sight between player and entity
                HitResult hit = world.checkBlockCollisionBetweenPoints(
                    Vec3.getPermanentVec3(entity.x, entity.y, entity.z),
                    Vec3.getPermanentVec3(theMonster.x, theMonster.y, theMonster.z),
                    false, false, true
                );
                if (hit != null) {
                    continue;
                }

                ///  checks if the entity can be spawned on the choosen block
                HitResult hit1 = world.checkBlockCollisionBetweenPoints(
                    Vec3.getPermanentVec3(theMonster.x, theMonster.y, theMonster.z),
                    Vec3.getPermanentVec3(theMonster.x, theMonster.y - 5, theMonster.z),
                    true, false, true
                );
                if (hit1 == null || hit1.hitType != HitResult.HitType.TILE || !world.getBlockMaterial(hit1.x, hit1.y, hit1.z).isSolid() || world.getBlockId(hit1.x, hit1.y, hit1.z) == Blocks.SPIKES.id()) {
                    continue;
                }

                world.entityJoinedWorld(theMonster);
                world.setBlockMetadata(x, y, z, 1);
                world.scheduleBlockUpdate(x, y, z, this.id(), this.tickDelay());
                this.spawnParticles(world, spawnX, spawnY + 0.25, spawnZ);
                this.playSound(world, x, y, z, entity, theMonster);
                this.giveAchievement((Player) entity, theMonster);
                return;
            }
        }
    }

    @SuppressWarnings("java:S1452")
    public Block<?> getReplaceOnClear() {
        return replaceOnClear;
    }

    @SuppressWarnings("java:S1452")
    public Block<?> getBreakResult() {
        return breakResult;
    }

    private void giveAchievement(Player player, Entity theMonster) {
        if (theMonster instanceof MobSentry) {
            player.triggerAchievement(AetherAchievements.SENTRY_DEPLOYED);
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
