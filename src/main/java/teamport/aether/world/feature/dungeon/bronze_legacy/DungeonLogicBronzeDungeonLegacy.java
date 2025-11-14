package teamport.aether.world.feature.dungeon.bronze_legacy;

import net.minecraft.core.world.World;
import teamport.aether.world.feature.util.map.DungeonLogic;

import java.util.Random;

public class DungeonLogicBronzeDungeonLegacy extends DungeonLogic {
    public DungeonLogicBronzeDungeonLegacy(int dimensionID, int id, long seed) {
        super(dimensionID, id, seed);
    }

    @Override
    protected boolean placeDungeon(World world, Random random) {
        return false;
    }

    @Override
    protected boolean canPlaceDungeon(World world) {
        return false;
    }
}
