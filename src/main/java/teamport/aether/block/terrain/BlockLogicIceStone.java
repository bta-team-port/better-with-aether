package teamport.aether.block.terrain;

import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BlockLogicIceStone extends BlockLogic {

    public static Int2IntArrayMap variantMap = new Int2IntArrayMap();
    public static final @NonNull Map<@NonNull Block<?>, @NonNull Block<?>> NO_ICE_TO_ICE_MAP = new HashMap<>();
    public static final @NonNull Map<@NonNull Block<?>, @NonNull Block<?>> ICE_TO_NO_ICE_MAP = new HashMap<>();

    public BlockLogicIceStone(@NonNull Block<?> block, @NonNull Block<?> parentBlock) {
        super(block, Materials.STONE);
        block.setTicking(true);
        variantMap.put(parentBlock.id(), block.id());
    }

    public static @Nullable Block<?> getIceBlock(@NonNull Block<?> noMossBlock) {
        return NO_ICE_TO_ICE_MAP.get(noMossBlock);
    }

    public static @Nullable Block<?> getNoIceBlock(@NonNull Block<?> mossBlock) {
        return ICE_TO_NO_ICE_MAP.get(mossBlock);
    }

    public static void initFreezeMap() {
        NO_ICE_TO_ICE_MAP.clear();
        ICE_TO_NO_ICE_MAP.clear();

        NO_ICE_TO_ICE_MAP.put(Blocks.FLUID_WATER_STILL, Blocks.ICE);
        NO_ICE_TO_ICE_MAP.put(Blocks.FLUID_LAVA_STILL, Blocks.OBSIDIAN);
        NO_ICE_TO_ICE_MAP.put(Blocks.STONE, Blocks.PERMAFROST);

        NO_ICE_TO_ICE_MAP.put(Blocks.COBBLE_STONE, Blocks.COBBLE_PERMAFROST);
        NO_ICE_TO_ICE_MAP.put(Blocks.ICE, Blocks.PERMAICE);

        for (Map.Entry<Block<?>, Block<?>> entry : NO_ICE_TO_ICE_MAP.entrySet()) {
            ICE_TO_NO_ICE_MAP.put(entry.getValue(), entry.getKey());
        }

    }

    @Override
    public int tickDelay() {
        return 50;
    }

    @Override
    public void updateTick(@NonNull World world, @NonNull TilePosc pos, @NonNull Random rand, boolean scheduled) {
        attemptFreeze(world, pos);
        super.updateTick(world, pos, rand, scheduled);
    }

    @Override
    public void onPlacedByWorld(@NonNull World world, @NonNull TilePosc tilePos) {
        attemptFreeze(world, tilePos);
        attemptFreeze(world, tilePos);
        attemptFreeze(world, tilePos);
        attemptFreeze(world, tilePos);
        attemptFreeze(world, tilePos);
        world.scheduleBlockUpdate(tilePos, this.block, this.tickDelay());
    }

    public void attemptFreeze(World world, TilePosc tilePos) {
        int l = 0;
        while (l < 32) {
            int x1 = tilePos.x() + world.rand.nextInt(8) - world.rand.nextInt(8);
            int y1 = tilePos.y() + world.rand.nextInt(4) - world.rand.nextInt(4);
            int z1 = tilePos.z() + world.rand.nextInt(8) - world.rand.nextInt(8);

            int radius = 4;
            if (Math.pow((x1 - tilePos.x()), 2) + Math.pow((y1 - tilePos.y()), 2) + Math.pow((z1 - tilePos.z()), 2) > Math.pow(radius, 2)) {
                continue;
            }

            TilePos tilePos1 = new TilePos(x1, y1, z1);
            freezeBlock(world, tilePos1);
            l++;
        }
    }

    public void freezeBlock(@NonNull World world, @NonNull TilePosc tilePos) {
        Block<?> block = world.getBlockType(tilePos);
        int meta = world.getBlockData(tilePos);

        // jank.
        if ((block == Blocks.FLUID_WATER_STILL
            || block == Blocks.FLUID_LAVA_STILL
            || block == Blocks.FLUID_WATER_FLOWING
            || block == Blocks.FLUID_LAVA_FLOWING
        ) && meta != 0) {
            return;
        }

        Block<?> result = NO_ICE_TO_ICE_MAP.get(block);
        world.setBlockTypeNotify(tilePos, result);
    }

}
