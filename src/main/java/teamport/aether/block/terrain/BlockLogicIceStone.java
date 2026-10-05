package teamport.aether.block.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BlockLogicIceStone extends BlockLogic {
    public static final @NonNull Map<@NonNull Block<?>, @NonNull Block<?>> NO_ICE_TO_ICE_MAP = new HashMap<>();
    public static final @NonNull Map<@NonNull Block<?>, @NonNull Block<?>> ICE_TO_NO_ICE_MAP = new HashMap<>();

    public BlockLogicIceStone(@NonNull Block<?> block) {
        super(block, Materials.STONE);
        block.setTicking(true);
    }

    public static @Nullable Block<?> getIceBlock(@NonNull Block<?> noIceBlock) {
        return NO_ICE_TO_ICE_MAP.get(noIceBlock);
    }

    public static @Nullable Block<?> getNoIceBlock(@NonNull Block<?> iceBlock) {
        return ICE_TO_NO_ICE_MAP.get(iceBlock);
    }

    public static void initFreezeMap() {
        NO_ICE_TO_ICE_MAP.clear();
        ICE_TO_NO_ICE_MAP.clear();

        NO_ICE_TO_ICE_MAP.put(Blocks.FLUID_WATER_STILL, Blocks.ICE);
        NO_ICE_TO_ICE_MAP.put(Blocks.FLUID_LAVA_STILL, Blocks.OBSIDIAN);
        NO_ICE_TO_ICE_MAP.put(Blocks.FLUID_ACID_STILL, Blocks.BRIMTHAW);
        NO_ICE_TO_ICE_MAP.put(Blocks.STONE, Blocks.PERMAFROST);

        NO_ICE_TO_ICE_MAP.put(Blocks.COBBLE_STONE, Blocks.COBBLE_PERMAFROST);
        NO_ICE_TO_ICE_MAP.put(Blocks.ICE, Blocks.PERMAICE);

        for (Map.Entry<Block<?>, Block<?>> entry : NO_ICE_TO_ICE_MAP.entrySet()) {
            ICE_TO_NO_ICE_MAP.put(entry.getValue(), entry.getKey());
        }

    }

    @Override
    public void updateTick(@NonNull World world, @NonNull TilePosc pos, @NonNull Random rand, boolean scheduled) {
        if (world.isClientSide) return;

        if (rand.nextInt(20) == 0) {
            attemptFreeze(world, pos, rand);
        }
    }

    @Override
    public void onPlacedByWorld(@NonNull World world, @NonNull TilePosc tilePos) {
        for (int i = 0; i < 4; i++) {
            attemptFreeze(world, tilePos, world.rand);
        }
        world.scheduleBlockUpdate(tilePos, this.block, this.tickDelay());
    }

    private void attemptFreeze(World world, TilePosc tilePos, Random rand) {
        for (int i = 0; i < 4; i++) {
            Direction dir = Direction.ID_MAP[rand.nextInt(6)];
            TilePos target = tilePos.add(dir, new TilePos());

            if (rand.nextInt(3) == 0) {
                target = target.add(Direction.ID_MAP[rand.nextInt(6)], new TilePos());
            }

            if (freezeBlock(world, target)) {
                break;
            }
        }
    }

    private boolean freezeBlock(@NonNull World world, @NonNull TilePosc tilePos) {
        if (!world.isBlockLoaded(tilePos)) return false;

        Block<?> block = world.getBlockType(tilePos);
        int meta = world.getBlockData(tilePos);
        if ((block == Blocks.FLUID_WATER_FLOWING || block == Blocks.FLUID_LAVA_FLOWING || block == Blocks.FLUID_ACID_FLOWING) && meta != 0) {
            return false;
        }

        Block<?> result = getIceBlock(world.getBlockType(tilePos));
        if (result != null) {
            return world.setBlockTypeNotify(tilePos, result);
        }
        return false;
    }

}
