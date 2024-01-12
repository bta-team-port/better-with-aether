package bta.aether.block;

import bta.aether.world.AetherDimension;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.ChunkCoordinates;

import java.util.Random;

public class BlockDungeon extends Block {

    private final int replacementID;

    public BlockDungeon(String key, int id, Material material, int replacementID) {
        super(key, id, material);
        this.replacementID = replacementID;
    }

    @Override
    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
        if (dropCause != EnumDropCause.IMPROPER_TOOL && canBreak(world, x, y, z)) {
            return new ItemStack[]{new ItemStack(Block.getBlock(replacementID),1)};
        }

        return new ItemStack[]{new ItemStack(Block.getBlock(replacementID), 0)};
    }

    @Override
    public void onBlockRemoved(World world, int x, int y, int z, int data) {
        if (!canBreak(world, x, y, z)) {
            world.setBlock(x, y, z, this.id);
        }
    }

    private double getDistanceFrom(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Math.abs(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2)));
    }

    private boolean canBreak(World world, int x, int y, int z) {
        final boolean[] canBreak = {true};
        AetherDimension.dugeonMap.forEach((cords, defeated) -> {
            if (getDistanceFrom(x, y, z, cords.x, cords.y, cords.z) < 300 && !defeated) {
                canBreak[0] = false;
            }
        });

        return canBreak[0];
    }
}
