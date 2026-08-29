package teamport.aether.world.feature.util.map;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.generate.feature.WorldFeatureInterface;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.Random;

public abstract class WorldFeatureMap<T extends DungeonLogic> extends WorldFeature implements WorldFeatureInterface {

    @Override
    public boolean place(@NonNull World world, @NotNull Random random, @NotNull TilePosc pos){
        return this.place(world, random, pos.x(), pos.y(), pos.z());
    }

    @Override
    public boolean place(World world, Random random, int i, int j, int k) {
        if (!canPlace(world, i, j, k)) return false;
        registerAndGenerate(world,(world.getRandomSeed() ^ 31 * 7) + random.nextLong(), i, j, k);
        return true;
    }

    /// places the dungeon logic && generates the structure.
    @SuppressWarnings("UnusedReturnValue")
    public T registerAndGenerate(World world, long seed, int x, int y, int z) {
        T logic = register(world, seed, x, y, z);
        generate(logic, world, seed, x, y, z);
        return logic;
    }

    /// places the dungeon logic.
    public T register(World world, long seed, int x, int y, int z) {
        return DungeonMap.register(getAppliedClass(), world, seed, x, y, z);
    }

    protected abstract Class<T> getAppliedClass();

    public abstract boolean canPlace(World world, int x, int y, int z);

    /// generates the structure.
    public abstract boolean generate(T logic, World world, long seed, int x, int y, int z);
}
