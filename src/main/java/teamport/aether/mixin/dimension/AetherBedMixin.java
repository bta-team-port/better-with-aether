package teamport.aether.mixin.dimension;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicBed;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.entity.monster.fireminion.MobFireMinion;
import teamport.aether.helper.ParticleHelper;
import teamport.aether.world.AetherDimension;
import teamport.aether.world.generate.feature.components.WorldFeaturePoint;

import java.util.Random;

import static teamport.aether.world.generate.feature.components.WorldFeaturePoint.wfp;

@Mixin(value = BlockLogicBed.class, remap = false)
public class AetherBedMixin extends BlockLogic {
    public AetherBedMixin(Block<?> block, Material material) {
        super(block, material);
    }

    @Unique
    private void doEffect(World world, Random r, int x, int y, int z) {
        for (int i = 0; i < 8; i++) {
            ParticleHelper.spawnParticle(world,
                    "largesmoke",
                    x + r.nextFloat(), y + r.nextFloat(), z + r.nextFloat(),
                    0, 0.01, 0, 0
            );
        }
    }

    @Inject(method = "onBlockRightClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;setBlockWithNotify(IIII)Z", shift = At.Shift.AFTER), cancellable = true)
    public void onBlockRightClicked(World world, int x, int y, int z, Player player, Side side, double xPlaced, double yPlaced, CallbackInfoReturnable<Boolean> cir) {
        if (world.dimension.id == AetherDimension.AetherDimensionID) {
            cir.setReturnValue(true);
            cir.cancel();
        }
        else return;

        Random rand = world.rand;

        Vec3 wind = Vec3.getTempVec3(0.1, 0, 0);
        wind.rotateAroundY(MathHelper.toRadians(rand.nextInt(360)));
        for (int i = 0; i < 32; i++) {
            ParticleHelper.spawnParticle(world,
                "flame",
                x + rand.nextGaussian(), y + rand.nextGaussian(), z + rand.nextGaussian(),
                wind.x, 0.2, wind.z, 0
            );
            ParticleHelper.spawnParticle(world,
                "smoke",
                x + rand.nextGaussian(), y + rand.nextGaussian(), z + rand.nextGaussian(),
                wind.x, 0.2, wind.z, 0
            );
        }

        doEffect(world, rand, x, y, z);
        for (Direction d : Direction.horizontalDirections) {
            WorldFeaturePoint p = wfp(x, y, z).moveInDirection(d);
            if (world.getBlockId(p.x, p.y, p.z) == Blocks.BED.id()) doEffect(world, rand, p.x, p.y, p.z);
        }

        player.fireHurt();
        world.playSoundEffect(player, SoundCategory.WORLD_SOUNDS, x, y, z, "mob.ghast.fireball", 0.25F, (1.3F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);

        WorldFeaturePoint anchor = wfp((int) player.x, (int) player.y, (int) player.z);

        int mobsToSpawn = 6;
        final int maxAttempts = 200;
        while (mobsToSpawn > 0) {
            int attempts = 0;

            MobFireMinion minion = new MobFireMinion(world);
            WorldFeaturePoint spawn;
            do {
                attempts++;
                if (attempts > maxAttempts) break;

                spawn = anchor.copy();
                spawn.moveInDirection(Direction.NORTH, 4 + (int) ((1.0F + rand.nextGaussian()) * 3));
                spawn.rotateYAroundPivot(anchor, rand.nextFloat() * 360);

                int topBlock = world.getHeightValue(spawn.x, spawn.z);
                if (topBlock <= 0 || Math.abs(topBlock - spawn.y) > 15) continue;

                minion.setPos(spawn.x, topBlock, spawn.z);
            } while (minion.isInWall());

            if (attempts > maxAttempts) break;

            minion.setTarget(player);
            world.entityJoinedWorld(minion);

            world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, minion.x, minion.y, minion.z, "mob.ghast.fireball", 0.25F, (1.3F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
            mobsToSpawn--;
        }
    }
}
