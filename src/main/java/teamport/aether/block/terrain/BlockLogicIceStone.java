package teamport.aether.block.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BlockLogicIceStone extends BlockLogic {

    public BlockLogicIceStone(Block<?> block) {
        super(block, Material.stone);
    }

    private static final Map<Integer, Integer> freezeResultNatural = new HashMap<>();
    private static final Map<Integer, Integer> freezeResultOnPlace = new HashMap<>();

    static {
        freezeResultOnPlace.put(Blocks.FLUID_WATER_STILL.id(), Blocks.ICE.id());
        freezeResultOnPlace.put(Blocks.FLUID_LAVA_STILL.id(), Blocks.OBSIDIAN.id());

        freezeResultNatural.put(Blocks.FLUID_WATER_STILL.id(), Blocks.ICE.id());
        freezeResultNatural.put(Blocks.FLUID_LAVA_STILL.id(), Blocks.OBSIDIAN.id());
        freezeResultNatural.put(Blocks.STONE.id(), Blocks.PERMAFROST.id());
        freezeResultNatural.put(Blocks.COBBLE_STONE.id(), Blocks.COBBLE_PERMAFROST.id());
        freezeResultNatural.put(Blocks.ICE.id(), Blocks.PERMAICE.id());
    }

    @Override
    public int tickDelay() {
        return 50;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        attemptFreeze(false, world, x, y, z);
        super.updateTick(world, x, y, z, rand);
    }

    @Override
    public void onBlockPlacedByMob(World world, int x, int y, int z, @NonNull Side side, Mob mob, double xPlaced, double yPlaced) {
        attemptFreeze(true, world, x, y, z);
        attemptFreeze(true, world, x, y, z);
        attemptFreeze(true, world, x, y, z);
        attemptFreeze(true, world, x, y, z);
        attemptFreeze(true, world, x, y, z);
        super.onBlockPlacedByMob(world, x, y, z, side, mob, xPlaced, yPlaced);
    }

    public void attemptFreeze(boolean onPlace, World world, int x, int y, int z) {
        int l = 0;
        while (l < 32) {
            int x1 = x + world.rand.nextInt(8) - world.rand.nextInt(8);
            int y1 = y + world.rand.nextInt(4) - world.rand.nextInt(4);
            int z1 = z + world.rand.nextInt(8) - world.rand.nextInt(8);

            int radius = 4;
            if (Math.pow((x1 - x), 2) + Math.pow((y1 - y), 2) + Math.pow((z1 - z), 2) > Math.pow(radius, 2)) {
                continue;
            }

            freezeBlock(onPlace, world, x1, y1, z1);
            l++;
        }
    }

    public void freezeBlock(boolean onPlace, World world, int x, int y, int z) {
        Integer result;

        int block = world.getBlockId(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);

        // jank.
        if ((block == Blocks.FLUID_WATER_STILL.id()
            || block == Blocks.FLUID_LAVA_STILL.id()
            || block == Blocks.FLUID_WATER_FLOWING.id()
            || block == Blocks.FLUID_LAVA_FLOWING.id()
        ) && meta != 0) return;

        if (onPlace) result = freezeResultOnPlace.get(block);
        else result = freezeResultNatural.get(block);

        if (result != null) world.setBlockWithNotify(x, y, z, result);
    }

}
