package bta.aether.block;

import bta.aether.world.AetherDimension;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

import java.util.Random;

public class BlockDungeon extends Block {

    private final int replacementID;

    public BlockDungeon(String key, int id, Material material, int replacementID) {
        super(key, id, material);
        this.replacementID = replacementID;
        this.setTicking(true);
    }

    @Override
    public int tickRate() {
        return 1200;
    }

    @Override
    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
        if (dropCause != EnumDropCause.IMPROPER_TOOL) {
            return new ItemStack[]{new ItemStack(Block.getBlock(replacementID),1)};
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

    private double getDistanceFrom(double x1, double y1, double z1, double x2, double y2, double z2) {
        double d = x1 - x2;
        double d1 = y1 - y2;
        double d2 = z1 - z2;
        return d * d + d1 * d1 + d2 * d2;
    }

    public void attemptPropagate(World world, int x, int y, int z) {
        final boolean[] canBreak = {true};
        AetherDimension.dugeonMap.forEach((id, cords) -> {
            if (getDistanceFrom(x, y, z, cords.x, cords.y, cords.z) < 90000) {
                canBreak[0] = false;
            }
        });

        if (canBreak[0]) {
            world.setBlock(x, y, z, replacementID);
            for (int x1 = -3; x1 < 3; x1++) {
                for (int z1 = -3; z1 < 3; z1++) {
                    for (int y1 = -3; y1 < 3; y1++) {
                        world.scheduleBlockUpdate(x + x1, y + y1, z + z1, this.id, 1);
                    }
                }
            }
        }
    }
}