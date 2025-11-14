package teamport.aether.blocks.dungeon;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;

import java.util.Random;

public class BlockLogicLocked extends BlockLogicDungeon {

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
    public @Nullable ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
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
}
