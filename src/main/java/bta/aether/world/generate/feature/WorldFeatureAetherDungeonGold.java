package bta.aether.world.generate.feature;

import bta.aether.block.AetherBlocks;
import bta.aether.entity.EntityBossBase;
import bta.aether.entity.EntityBossSlider;
import bta.aether.item.AetherItems;
import bta.aether.util.AetherBlockCoord;
import bta.aether.world.AetherDimension;
import bta.aether.world.WorldFeatureGoldenOak;
import bta.aether.world.generate.BlockPallet;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeatureFlowers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        //if (AetherDimension.dugeonMap.values().stream().anyMatch(dungeon -> distanceToSqr(x, y, z, dungeon.x, dungeon.y, dungeon.z) < 250000)) return false;
        int dungeonID = AetherDimension.registerDungeonToMap(x, y, z);


        // generate main spheroid
        int radius = 16;
        drawSpheroid(world, random, x, y + 15, z, radius, (int) (radius*1.1), radius, holystone, true);

        // cover it in grass and put some trees.
        int radX, radZ, height;
        for (radX = -radius; radX < radius; radX++) for (radZ = -radius; radZ < radius; radZ++)
            if(WorldFeatureAetherDungeonBase.distanceToSqr(radX + x, y, radZ + z, x, y, z) < Math.pow(radius, 2)) {
                height = world.getHeightValue(radX + x, radZ + z);
                if (stones.contains(world.getBlockId(radX + x, height-1, radZ + z))) world.setBlock(radX + x, height-1, radZ + z, AetherBlocks.grassAether.id);
                if (stones.contains(world.getBlockId(radX + x, height-2, radZ + z))) world.setBlock(radX + x, height-2, radZ + z, AetherBlocks.dirtAether.id);
                if (stones.contains(world.getBlockId(radX + x, height-3, radZ + z))) world.setBlock(radX + x, height-3, radZ + z, AetherBlocks.dirtAether.id);
                if (random.nextInt(84) == 0) new WorldFeatureGoldenOak(AetherBlocks.leavesOakGolden.id, AetherBlocks.logOakGolden.id, 5 + random.nextInt(10)).generate(world, random, radX + x, height-1, radZ + z);
                if (random.nextInt(64) == 0) new WorldFeatureFlowers(AetherBlocks.flowerWhite.id).generate(world, random, radX + x, height-1, radZ + z);
                if (random.nextInt(64) == 0) new WorldFeatureFlowers(AetherBlocks.aetherTallGrass.id).generate(world, random, radX + x, height-1, radZ + z);
            }

        // place the outer spheres
        radius -= 2;
        List<Integer> angles = new ArrayList<>();
        for (int angle = 0; angle < 10; angle++) angles.add(angle*(360/10));

        for (int index = 0; index < 6 + random.nextInt(4); index++) {
            int angleIndex = random.nextInt(angles.size());
            int angle = angles.get(angleIndex);
            angles.remove(angleIndex);

            double newX = x + radius * Math.cos(Math.toRadians(angle));
            double newZ = z + radius * Math.tan(Math.toRadians(angle));
            double mod = (double) (4 + random.nextInt(5)) / 10;
            drawSphere(world, random, (int) newX, (int) (y + (radius*0.8F)), (int) newZ, (int) (radius*mod), holystone, true);

            // greenery
            for (radX = (int) -(radius*mod); radX < (int) (radius*mod); radX++) for (radZ = (int) -(radius*mod); radZ < (int) (radius*mod); radZ++)
                if(WorldFeatureAetherDungeonBase.distanceToSqr(radX + x, y, radZ + z, x, y, z) < Math.pow(radius, 2)) {
                    height = world.getHeightValue((int) (radX + newX), (int) (radZ + newZ));
                    if (stones.contains(world.getBlockId((int) (radX + newX), height-1, (int) (radZ + newZ)))) world.setBlockWithNotify((int) (radX + newX), height-1, (int) (radZ + newZ), AetherBlocks.grassAether.id);
                    if (stones.contains(world.getBlockId((int) (radX + newX), height-2, (int) (radZ + newZ)))) world.setBlockWithNotify((int) (radX + newX), height-2, (int) (radZ + newZ), AetherBlocks.dirtAether.id);
                    if (stones.contains(world.getBlockId((int) (radX + newX), height-3, (int) (radZ + newZ)))) world.setBlockWithNotify((int) (radX + newX), height-3, (int) (radZ + newZ), AetherBlocks.dirtAether.id);
                    if (random.nextInt(126) == 0) new WorldFeatureGoldenOak(AetherBlocks.leavesOakGolden.id, AetherBlocks.logOakGolden.id, 5 + random.nextInt(10)).generate(world, random, (int) (radX + newX), height-1, (int) (radZ + newZ));
                    if (random.nextInt(84) == 0) new WorldFeatureFlowers(AetherBlocks.flowerWhite.id).generate(world, random, (int) (radX + newX), height-1, (int) (radZ + newZ));
                    if (random.nextInt(84) == 0) new WorldFeatureFlowers(AetherBlocks.aetherTallGrass.id).generate(world, random, (int) (radX + newX), height-1, (int) (radZ + newZ));
                }
        }

        drawVolume(world, random, hellfire, Direction.WEST, 19, Direction.NORTH, 19, Direction.UP, 8, x +1 +radius/2, y +radius/2, z +1 +radius/2, true);
        drawVolume(world, 0, 0, Direction.WEST, 17, Direction.NORTH, 17, Direction.UP, 6, x +radius/2, y +1 +radius/2, z +radius/2, true);

        // Chest hole
        ItemStack key = makeTreasureChest(lootTableGoldRare, 6 + random.nextInt(6), AetherItems.keySilver, true, world, x, y +radius/2 +1, z);

        // Boss TODO: replace with sunfire spirit.
        EntityBossBase boss = placeBoss(world, x, y +radius/2 + 2, z, EntityBossSlider.class);
        if (boss != null) {
            boss.setToDungeon(dungeonID);
            boss.setKeychain(key);
            boss.setReturnPoint(new AetherBlockCoord(x, y +radius/2 +2, z));
            boss.setBlocksDestroyOnDeath(null);
        }

        return true;
    }
}
