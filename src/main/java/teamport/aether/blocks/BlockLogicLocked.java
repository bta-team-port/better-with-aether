package teamport.aether.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import teamport.aether.AetherAchievements;

import java.util.Random;

public class BlockLogicLocked extends BlockLogic {

    public final Block<?> replacement;

    public BlockLogicLocked(Block<?> block, Material material, Block<?> replacement) {
        super(block, material);
        this.replacement = replacement;
    }

    @Override
    public int tickDelay() {
        return 1200;
    }

    @Override
    public void onBlockLeftClicked(World world, int x, int y, int z, Player player, Side side, double xHit, double yHit) {
        attemptPropagate(world, x, y, z);
    }

    @Override
    public ItemStack @Nullable [] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
        if (dropCause != EnumDropCause.IMPROPER_TOOL) {
            return new ItemStack[]{new ItemStack(replacement, 1)};
        }

        return null;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        attemptPropagate(world, x, y, z);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
        attemptPropagate(world, x, y, z);
    }

    public double getDistanceFrom(double x1, double y1, double z1, double x2, double y2, double z2) {
        double d = x1 - x2;
        double d1 = y1 - y2;
        double d2 = z1 - z2;
        return d * d + d1 * d1 + d2 * d2;
    }

    public void attemptPropagate(World world, int x, int y, int z) {
//        final boolean[] canBreak = {true};
//        AetherDimension.dungeonMap.forEach((id, cords) -> {
//            if (getDistanceFrom(x, y, z, cords.x, cords.y, cords.z) < AetherDimension.dungeonRadiusSQR) {
//                canBreak[0] = false;
//            }
//        });
//
//        if (canBreak[0]) {
//            world.setBlock(x, y, z, replacement.id());
//            for (int x1 = -3; x1 < 3; x1++) {
//                for (int z1 = -3; z1 < 3; z1++) {
//                    for (int y1 = -3; y1 < 3; y1++) {
//                        world.scheduleBlockUpdate(x + x1, y + y1, z + z1, this.id(), 1);
//                    }
//                }
//            }
//        }
    }

    @Override
    public boolean collidesWithEntity(Entity entity, World world, int x, int y, int z) {
        if (entity instanceof Player) {
            ((Player) entity).triggerAchievement(AetherAchievements.WEVE_GOT_HOSTILES);
        }

        return super.collidesWithEntity(entity, world, x, y, z);
    }
}
