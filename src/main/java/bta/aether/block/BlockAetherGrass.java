package bta.aether.block;

import bta.aether.Aether;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

import java.util.Random;

public class BlockAetherGrass extends Block {
    public BlockAetherGrass(String key, int id, Material material) {
        super(key, id, material);
        this.setTicking(true);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (!world.isClientSide) {
            if (world.getBlockLightValue(x, y + 1, z) < 4 && Block.lightBlock[world.getBlockId(x, y + 1, z)] > 2) {
                if (rand.nextInt(4) != 0) {
                    return;
                }

                world.setBlockWithNotify(x, y, z, AetherBlocks.dirtAether.id);
            } else if (world.getBlockLightValue(x, y + 1, z) >= 9) {
                int l = x + rand.nextInt(3) - 1;
                int i1 = y + rand.nextInt(5) - 3;
                int j1 = z + rand.nextInt(3) - 1;
                int k1 = world.getBlockId(l, i1 + 1, j1);
                if (world.getBlockId(l, i1, j1) == Block.dirt.id && world.getBlockLightValue(l, i1 + 1, j1) >= 4 && Block.lightBlock[k1] <= 2) {
                    world.setBlockWithNotify(l, i1, j1, this.id);
                }
            }
        }
    }
    @Override
    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
        switch (dropCause) {
            case SILK_TOUCH:
            case PICK_BLOCK:
                return new ItemStack[]{new ItemStack(this)};
            default:
                return new ItemStack[]{new ItemStack(AetherBlocks.dirtAether)};
        }
    }
    }