package bta.aether.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockFlower;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

import java.util.HashMap;
import java.util.Random;

public class BlockIcestone extends Block {
    public BlockIcestone(String key, int id, Material material) {
        super(key, id, material);
        this.setTicking(true);
    }

    public static int radius = 4;
    public static HashMap<Integer, Integer> freezeResultNatural = new HashMap<>();
    public static HashMap<Integer, Integer> freezeResultOnPlace = new HashMap<>();

    static {
        freezeResultOnPlace.put(Block.fluidWaterStill.id, Block.ice.id);
        freezeResultOnPlace.put(Block.fluidLavaStill.id, Block.obsidian.id);

        freezeResultNatural.put(Block.stone.id, Block.permafrost.id);
        freezeResultNatural.put(Block.cobbleStone.id, Block.cobblePermafrost.id);
        freezeResultNatural.put(Block.ice.id, Block.permaice.id);
    }

    @Override
    public int tickRate() {
        return 50;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        attemptFreeze(false, world, x, y, z);
        super.updateTick(world, x, y, z, rand);
    }

    @Override
    public void onBlockPlaced(World world, int x, int y, int z, Side side, EntityLiving entity, double sideHeight) {
        attemptFreeze(true, world, x, y, z);
        super.onBlockPlaced(world, x, y, z, side, entity, sideHeight);
    }

    public void attemptFreeze(Boolean onPlace, World world, int x, int y, int z) {
        for(int l = 0; l < 32;) {
            int x1 = x + world.rand.nextInt(8) - world.rand.nextInt(8);
            int y1 = y + world.rand.nextInt(4) - world.rand.nextInt(4);
            int z1 = z + world.rand.nextInt(8) - world.rand.nextInt(8);

            if(Math.pow((x1 - x), 2) + Math.pow((y1 - y), 2) + Math.pow((z1 - z), 2) > Math.pow(radius, 2)){
                continue;
            }

            freezeBlock(onPlace, world, x1, y1, z1);
            l++;
        }
    }

    public void freezeBlock(Boolean onPlace, World world,int x, int y, int z) {
        int block = world.getBlockId(x, y, z);

        if (!onPlace) {
            Integer result = freezeResultNatural.get(block);
            if (result != null) world.setBlockWithNotify(x, y, z, result);
            return;
        }

        Integer result = freezeResultOnPlace.get(block);
        if (result != null) world.setBlockWithNotify(x, y, z, result);
    }

}