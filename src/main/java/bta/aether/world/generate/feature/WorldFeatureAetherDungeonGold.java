package bta.aether.world.generate.feature;

import bta.aether.block.AetherBlocks;
import bta.aether.entity.EntityBossBase;
import bta.aether.entity.EntityBossSlider;
import bta.aether.item.AetherItems;
import bta.aether.util.AetherBlockCoord;
import bta.aether.util.Pair;
import bta.aether.world.AetherDimension;
import bta.aether.world.WorldFeatureGoldenOak;
import bta.aether.world.generate.BlockPallet;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.generate.feature.WorldFeatureFlowers;

import java.util.*;

public class WorldFeatureAetherDungeonGold extends WorldFeatureAetherDungeonBase{
    private static BlockPallet hellfire = new BlockPallet();
    private static BlockPallet holystone = new BlockPallet();

    private static final List<Integer> stones = Arrays.asList(AetherBlocks.holystoneMossy.id, AetherBlocks.holystone.id);
    static {
        hellfire.addEntry(AetherBlocks.stoneHellfireLocked.id, 0, 90);
        hellfire.addEntry(AetherBlocks.stoneHellfireLightLocked.id, 0, 10);

        holystone.addEntry(AetherBlocks.holystone.id, 0, 90);
        holystone.addEntry(AetherBlocks.holystoneMossy.id, 0, 10);
    }

    private static final int radius = 16;

    private static final Pair<Integer, WorldFeature>[] veggies = new Pair[]{
            new Pair<>(128, new WorldFeatureGoldenOak(AetherBlocks.leavesOakGolden.id, AetherBlocks.logOakGolden.id)),
            new Pair<>(32, new WorldFeatureFlowers(AetherBlocks.aetherTallGrass.id)),
            new Pair<>(64, new WorldFeatureFlowers(AetherBlocks.flowerWhite.id))
    };

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        if (AetherDimension.dugeonMap.values().stream().anyMatch(dungeon -> distanceToSqr(x, y, z, dungeon.x, dungeon.y, dungeon.z) < 250000)) return false;
        int dungeonID = AetherDimension.registerDungeonToMap(x, y, z);

        // generate main spheroid
        drawSpheroid(world, random, x, y + 15, z, radius, (int) (radius * 1.12), radius, holystone, true);
        decorateTopLevelInRadius(veggies, radius, world, random, x, y, z);

        // place the outer spheres
        List<Integer> angles = new ArrayList<>();
        for (int angle = 0; angle < 10; angle++) angles.add(angle * (360 / 10));
        for (int index = 0; index < 6 + random.nextInt(4); index++) {
            int angleIndex = random.nextInt(angles.size());
            int angle = angles.get(angleIndex);
            angles.remove(angleIndex);

            double newX = x + radius * Math.cos(Math.toRadians(angle));
            double newZ = z + radius * Math.sin(Math.toRadians(angle));
            double radMod = (double) (4 + random.nextInt(5)) / 10;

            drawSphere(world, random, (int) newX, (int) (y + (radius * 0.8F)), (int) newZ, (int) (radius * radMod), holystone, true);
            decorateTopLevelInRadius(veggies, (int) (radius * radMod), world, random, (int) newX, (int) (y + (radius * 0.8F)), (int) newZ);
        }

        double radMod2 = 0.7F;
        drawSphere(world, random,x + radius, (int) (y + (radius*0.8F)), z, (int) (radius*radMod2), holystone, true);
        decorateTopLevelInRadius(veggies, (int) (radius * radMod2), world, random, x + radius, (int) (y + (radius*0.8F)), z);

        // main room
        int xRoomLength = 19;
        int YRoomHeight = 8;
        int ZRoomLength = 19;
        drawHollowShell(world, random, hellfire, Direction.WEST, xRoomLength, Direction.NORTH, ZRoomLength, Direction.UP, YRoomHeight, x +1 +radius/2, y + radius/2, z +1 +radius/2, true);

        // chest room
        xRoomLength = 7;
        YRoomHeight = 5;
        ZRoomLength = 7;
        drawHollowShell(world, random, hellfire, Direction.WEST, xRoomLength, Direction.NORTH, ZRoomLength, Direction.UP, YRoomHeight, x -1 +radius, y +radius/2, z +ZRoomLength/2, true);

        ItemStack key = makeTreasureChest(lootTableGoldRare, 6 + random.nextInt(6), AetherItems.keySilver, true, world, x, y + radius / 2 + 1, z);

        // Boss TODO: replace with sunfire spirit.
        EntityBossBase boss = placeBoss(world, x, y + radius / 2 + 2, z, EntityBossSlider.class);
        if (boss != null) {
            boss.setToDungeon(dungeonID);
            boss.setKeychain(key);
            boss.setReturnPoint(new AetherBlockCoord(x, y + radius / 2 + 2, z));
            boss.setBlocksDestroyOnDeath(null);
        }

        return true;
    }

    private void drawHollowShell(World world, Random random, BlockPallet pallet, Direction direction1, int length1, Direction direction2, int length2, Direction direction3, int length3, int startX, int startY, int startZ, boolean withNotify) {
        drawVolume(world, random, pallet, direction1, length1, direction2, length2, direction3, length3, startX, startY, startZ, withNotify);
        drawVolume(world, 0, 0, direction1, length1 -2, direction2, length2 -2, direction3, length3 -2, startX -1, startY +1, startZ -1, withNotify);
    }

    private void decorateTopLevelInRadius(Pair<Integer, WorldFeature>[] worldFeaturePair, int radius, World world, Random random, int x, int y, int z) {
        int radX, radZ, height;
        for (radX = -radius; radX < radius; radX++) for (radZ = -radius; radZ < radius; radZ++) {
            if (WorldFeatureAetherDungeonBase.distanceToSqr((radX + x), y, (radZ + z), x, y, z) < Math.pow(radius, 2)) {
                height = world.getHeightValue((radX + x), (radZ + z));

                if (stones.contains(world.getBlockId((radX + x), height - 1, (radZ + z)))) world.setBlockWithNotify((radX + x), height - 1, (radZ + z), AetherBlocks.grassAether.id);
                if (stones.contains(world.getBlockId((radX + x), height - 2, (radZ + z)))) world.setBlockWithNotify((radX + x), height - 2, (radZ + z), AetherBlocks.dirtAether.id);
                if (stones.contains(world.getBlockId((radX + x), height - 3, (radZ + z)))) world.setBlockWithNotify((radX + x), height - 3, (radZ + z), AetherBlocks.dirtAether.id);

                for (Pair<Integer, WorldFeature> integerWorldFeaturePair : worldFeaturePair)
                    if (random.nextInt(integerWorldFeaturePair.first) == 0)
                        integerWorldFeaturePair.second.generate(world, random, (radX + x), height, (radZ + z));
            }
        }
    }
}
