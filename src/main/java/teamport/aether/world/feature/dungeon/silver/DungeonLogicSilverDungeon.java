package teamport.aether.world.feature.dungeon.silver;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import teamport.aether.world.feature.util.map.DungeonLogic;

import java.util.Random;

public class DungeonLogicSilverDungeon extends DungeonLogic {

    protected Direction direction;

    public DungeonLogicSilverDungeon(int dimensionID, int id, long seed) {
        super(dimensionID, id, seed);
    }

    private int horizontalIndex(Direction dir) {
        for (int i = 0; i < Direction.horizontal.length; i++) {
            if (Direction.horizontal[i] == dir) return i;
        }
        return 0;
    }

    @Override
    protected boolean placeDungeon(World world, Random random) {
        return new WorldFeatureAetherSilverDungeon(horizontalIndex(direction)).generate(this, world, this.seed, position.getX(), position.getY(), position.getZ());
    }

    @Override
    protected boolean canPlaceDungeon(World world) {
        return new WorldFeatureAetherSilverDungeon(horizontalIndex(direction)).canPlace(world, position.getX(), position.getY(), position.getZ());
    }

    @Override
    public CompoundTag saveStructureData(CompoundTag data) {
        data.putInt("direction", direction.id);
        return super.saveStructureData(data);
    }

    @Override
    public void loadStructureData(CompoundTag data) {
        direction = Direction.fromId(data.getInteger("direction"));
        super.loadStructureData(data);
    }
}
