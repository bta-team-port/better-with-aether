package teamport.aether.world.feature.dungeon.bronze.component;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import teamport.aether.world.AetherDimension;
import teamport.aether.world.feature.dungeon.bronze.WorldFeatureAetherBronzeDungeon;
import teamport.aether.world.feature.util.WorldFeatureComponent;
import teamport.aether.world.feature.util.WorldFeaturePoint;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.world.feature.dungeon.bronze.WorldFeatureAetherBronzeDungeon.adjustCornerForLining;
import static teamport.aether.world.feature.dungeon.bronze.WorldFeatureAetherBronzeDungeon.placeWorldLining;
import static teamport.aether.world.feature.util.WorldFeatureComponent.drawVolume;
import static teamport.aether.world.feature.util.WorldFeatureComponent.drawVolumeWithPoint;
import static teamport.aether.world.feature.util.WorldFeaturePoint.wfp;

public class HallwayRoom extends BaseBronzeRoom {
    public HallwayRoom() {
        super();
        this.roomWeight = 0.25F;
        this.airTolerance = this.liquidTolerance = 0.85F;
        this.topAirTolerance = this.bottomAirTolerance = this.topLiquidTolerance = this.bottomLiquidTolerance = 0.85F;
        addDoor(NORTH, wfp(4, 1, 0), UP, 6, EAST, 4);
        addDoor(EAST, wfp(11, 1, 4), UP, 6, SOUTH, 4);
        addDoor(SOUTH, wfp(4, 1, 11), UP, 6, EAST, 4);
        addDoor(WEST, wfp(0, 1, 4), UP, 6, SOUTH, 4);
    }

    @Override
    public boolean canPlace() {
        if (this.y <= 11 && this.y + height + 3 >= world.getHeightBlocks()) {
            return false;
        }
        WorldFeatureComponent check;
        int countAir = 0;
        int countLiquid = 0;

        check = drawVolume(0, 0, SOUTH, width, UP, 7, EAST, 4, x + 4, y, z, false);
        check.add(drawVolume(0, 0, SOUTH, 4, UP, 7, EAST, length, x, y, z + 4, false));
        for (WorldFeaturePoint point : check.getBlockList()) {
            Block<?> block = world.getBlock(point.getX(), point.getY(), point.getZ());
            Material blockMaterial = block == null ? Material.air : block.getMaterial();
            if (block != null && block.blockHardness < 0) return false;
            if (blockMaterial == Material.air) countAir++;
            if (blockMaterial.isLiquid()) countLiquid++;
        }
        return check.getBlockList().size() * airTolerance >= countAir && check.getBlockList().size() * liquidTolerance >= countLiquid;
    }


    @Override
    public void makeRoom() {
        if (world.dimension.equals(AetherDimension.getAether())) {
            room.add(drawVolume(random, WorldFeatureAetherBronzeDungeon.holystone, SOUTH, 6, UP, 8, EAST, 6, x + 3, y, z + 3, false));
        }
        room.add(drawVolume(0, 0, SOUTH, 4, UP, 6, EAST, 4, x + 4, y + 1, z + 4, false));
    }


    @Override
    public void markDoor(Door door, ClosingType closingType) {
        super.markDoor(door, closingType);
        if (closingType != ClosingType.PLACED) return;
        WorldFeaturePoint p2 = wfp().moveInDirection(door.getHeading().getOpposite()).multiply(7).add(door.getP2());
        if (world.dimension.equals(AetherDimension.getAether())) {
            WorldFeaturePoint liningBottomCorner = door.getP1().copy();
            WorldFeaturePoint liningTopCorner = p2.copy();
            adjustCornerForLining(door.getHeading(), liningBottomCorner, liningTopCorner);
            placeWorldLining(world, drawVolumeWithPoint(this.random, WorldFeatureAetherBronzeDungeon.holystone, liningBottomCorner, liningTopCorner, false));
        }
        room.add(drawVolume(0, 0, door.getP1(), p2, false));
        this.placeRoom();
    }
}
