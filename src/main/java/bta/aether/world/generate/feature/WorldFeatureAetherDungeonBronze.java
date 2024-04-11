package bta.aether.world.generate.feature;

import bta.aether.block.AetherBlocks;
import bta.aether.world.AetherDimension;
import bta.aether.world.generate.BlockPallet;
import net.minecraft.core.world.World;

import java.util.Random;

public class WorldFeatureAetherDungeonBronze extends WorldFeatureAetherDungeonBase {
    private static BlockPallet carvedHolystone = new BlockPallet();
    private static BlockPallet holystone = new BlockPallet();
    static {
        carvedHolystone.addEntry(AetherBlocks.stoneCarvedLocked.id, 0, 95);
        carvedHolystone.addEntry(AetherBlocks.stoneCarvedLightLocked.id, 0, 0);

        holystone.addEntry(AetherBlocks.holystone.id, 0, 90);
        holystone.addEntry(AetherBlocks.holystoneMossy.id, 0, 10);
    }

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {

        if (AetherDimension.dugeonMap.values().stream().anyMatch(dungeon -> distanceToSqr(x, y, z, dungeon.x, dungeon.y, dungeon.z) < AetherDimension.dungeonRadiusSQR*1.5)) return false;
        int dungeonID = AetherDimension.registerDungeonToMap(x, y, z);

        return false;
    }
}
