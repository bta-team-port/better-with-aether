package bta.aether.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.data.gamerule.GameRules;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemTool;
import net.minecraft.core.world.World;

import java.util.Random;

public class BlockAetherGrass extends BlockAetherDouble {
    public BlockAetherGrass(String key, int id, Material material, Class<? extends ItemTool> toolClass) {
        super(key, id, material, toolClass);
        this.setTicking(true);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (world.isClientSide) {
            return;
        }
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
            if (world.getBlockId(l, i1, j1) == AetherBlocks.dirtAether.id && world.getBlockLightValue(l, i1 + 1, j1) >= 4 && Block.lightBlock[k1] <= 2) {
                world.setBlockWithNotify(l, i1, j1, this.id);
            }
            if (world.getGameRule(GameRules.DO_SEASONAL_GROWTH) && world.getBlockId(x, y + 1, z) == 0 && world.seasonManager.getCurrentSeason() != null && world.seasonManager.getCurrentSeason().growFlowers && rand.nextInt(256) == 0) {
                int idToSpawn = 0;
                int r = rand.nextInt(400);
                idToSpawn = r < 26 ? AetherBlocks.flowerPurple.id : (r < 41 ? AetherBlocks.flowerWhite.id : (AetherBlocks.aetherTallGrass.id));
                world.setBlockWithNotify(x, y + 1, z, idToSpawn);
            }
        }
    }

    @Override
    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
        switch (dropCause) {
            case SILK_TOUCH:
            case PICK_BLOCK: {
                return new ItemStack[]{new ItemStack(this)};
            }
        }
        return new ItemStack[]{new ItemStack(AetherBlocks.dirtAether)};
    }
}