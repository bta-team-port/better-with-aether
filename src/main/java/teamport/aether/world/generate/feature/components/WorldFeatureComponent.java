package teamport.aether.world.generate.feature.components;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.world.generate.feature.BlockPallet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static teamport.aether.world.generate.feature.components.WorldFeatureBlock.wfb;

public class WorldFeatureComponent {
    public WorldFeaturePoint anker;
    public WorldFeaturePoint tail;
    public List<WorldFeatureBlock> blockList;

    public WorldFeatureComponent(){
        this.blockList = new ArrayList<>();
    }

    public WorldFeatureComponent(int startX, int startY, int startZ){
        this.blockList = new ArrayList<>();
        this.anker = new WorldFeaturePoint(startX, startY, startZ);
    }

    public static WorldFeatureComponent wfc(int x, int y, int z){
        return new WorldFeatureComponent(x,y,z);
    }

    public void add(WorldFeatureComponent component){
        this.blockList.addAll(component.blockList);
        this.tail = component.tail;
    }

    public void add(List<WorldFeatureBlock> list){
        this.blockList.addAll(list);
        this.tail = makePoint(list.get(list.size() - 1));
    }

    private WorldFeaturePoint makePoint(WorldFeatureBlock wfb) {
        return new WorldFeaturePoint(wfb.x, wfb.y, wfb.z);
    }

    public void add(WorldFeatureBlock wfb){
        this.blockList.add(wfb);
        this.tail = makePoint(wfb);
    }

    public void setTail(int x, int y, int z){
        this.tail = new WorldFeaturePoint(x,y,z);
    }


    public void rotateYAxis(int fixPointX, int fixPointY, int fixPointZ, float angle){
        for(WorldFeatureBlock block : this.blockList){
            block.rotateFixPointYAxis(fixPointX, fixPointY, fixPointZ, angle);
        }
    }

    public void place(World world) {
        for(WorldFeatureBlock worldFeatureBlock: this.blockList){
            worldFeatureBlock.place(world);
        }
    }

    public static double distanceToSqr(int x, int y, int z, int x1, int y1, int z1) {
        double d3 = x - x1;
        double d4 = y - y1;
        double d5 = z - z1;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }

    public WorldFeaturePoint getAnker(){
        return anker;
    }

    public WorldFeaturePoint getTail(){
        return tail;
    }

    public static WorldFeatureComponent placeChestOrMimic(
            Random random, int x, int y, int z
    ) {
        WorldFeatureComponent component = new WorldFeatureComponent();
        if (random.nextInt(2) == 0) {
            component.add(wfb(x, y, z, AetherBlocks.CHEST_PLANKS_SKYROOT.id(), 0, true));
            return component;
        }
        component.add(wfb(x, y, z, AetherBlocks.CHEST_MIMIC.id(), 0, true));
        return component;
    }

    public static WorldFeatureComponent placeChestOrMimic(
            Random random, int x, int y, int z, int metadata
    ) {
        WorldFeatureComponent component = new WorldFeatureComponent();
        if (random.nextInt(2) == 0) {
            component.add(wfb(x, y, z, AetherBlocks.CHEST_PLANKS_SKYROOT.id(), metadata, true));
            return component;
        }
        component.add(wfb(x, y, z, AetherBlocks.CHEST_MIMIC.id(), 0, true));
        return component;
    }

    public static void populateChest(
            World world, WorldFeatureBlock wfb,
            Random random, WeightedRandomBag<WeightedRandomLootObject> lootTable,
            int quantity
    ) {
        Container inventory = BlockLogicChest.getInventory(world, wfb.x, wfb.y, wfb.z);
        if(inventory == null) return;
        for (int i = 0; i < quantity; i++) {
            inventory.setItem(
                    random.nextInt(inventory.getContainerSize()),
                    lootTable.getRandom().getItemStack(random)
            );
        }
    }

    public static WorldFeatureComponent drawSphere(
            Random random, BlockPallet pallet,
            int x, int y, int z,
            int radius,
            boolean withNotify
    ) {
        WorldFeatureComponent component = new WorldFeatureComponent();
        for (int blockX = x - radius; blockX <= x + radius; blockX++) {
            for (int blockY = y - radius; blockY <= y + radius; blockY++) {
                for (int blockZ = z - radius; blockZ <= z + radius; blockZ++) {
                    double offX = x - blockX;
                    double offY = y - blockY;
                    double offZ = z - blockZ;
                    double currentDist = offX * offX + offY * offY + offZ * offZ;
                    if (currentDist < radius * radius) {
                        component.add(wfb(blockX, blockY, blockZ, pallet.getRandom(random), withNotify));
                    }
                }
            }
        }
        return component;
    }

    public static WorldFeatureComponent drawSpheroid(
            Random random, BlockPallet pallet,
            int x, int y, int z,
            int width, int height, int depth,
            boolean withNotify
    ) {
        WorldFeatureComponent component = new WorldFeatureComponent();
        for (int blockX = x - width; blockX <= x + width; blockX++) {
            for (int blockY = y - height; blockY <= y + height; blockY++) {
                for (int blockZ = z - depth; blockZ <= z + depth; blockZ++) {
                    double offX = (x - blockX) / (double) width;
                    double offY = (y - blockY) / (double) height;
                    double offZ = (z - blockZ) / (double) depth;
                    double distanceSqr = offX * offX + offY * offY + offZ * offZ;
                    if (distanceSqr <= 1.0) {
                        component.add(wfb(blockX, blockY, blockZ, pallet.getRandom(random), withNotify));
                    }
                }
            }
        }
        return component;
    }

    public static WorldFeatureComponent drawLine(
            int id, int meta,
            Direction direction, int length,
            int startX, int startY, int startZ,
            boolean withNotify
    ) {
        WorldFeatureComponent component = new WorldFeatureComponent();
        for (int i = 0; i < length - 1; i++) {
            component.add(wfb(startX, startY, startZ, id, meta, withNotify));
            startX += direction.getOffsetX();
            startY += direction.getOffsetY();
            startZ += direction.getOffsetZ();
        }
        component.add(wfb(startX, startY, startZ, id, meta, withNotify));
        return component;
    }

    public static WorldFeatureComponent drawLine(
            Random random, BlockPallet pallet,
            Direction direction, int length,
            int startX, int startY, int startZ,
            boolean withNotify
    ) {
        WorldFeatureComponent component = new WorldFeatureComponent();
        for (int i = 0; i < length - 1; i++) {
            component.add(wfb(startX, startY, startZ, pallet.getRandom(random), withNotify));
            startX += direction.getOffsetX();
            startY += direction.getOffsetY();
            startZ += direction.getOffsetZ();
        }
        component.add(wfb(startX, startY, startZ, pallet.getRandom(random), withNotify));
        return component;
    }

    public static WorldFeatureComponent drawPlane(
            int id, int meta,
            Direction direction1, int length1,
            Direction direction2, int length2,
            int startX, int startY, int startZ,
            boolean withNotify
    ) {
        WorldFeatureComponent component = new WorldFeatureComponent();
        int blockX = startX;
        int blockY = startY;
        int blockZ = startZ;
        for (int i = 0; i < length2; i++) {
            blockX = startX + direction2.getOffsetX() * i;
            blockY = startY + direction2.getOffsetY() * i;
            blockZ = startZ + direction2.getOffsetZ() * i;
            for (int j = 0; j < length1; j++) {
                component.add(wfb(blockX, blockY, blockZ, id, meta, withNotify));
                blockX += direction1.getOffsetX();
                blockY += direction1.getOffsetY();
                blockZ += direction1.getOffsetZ();
            }
        }
        component.setTail(blockX, blockY, blockZ);
        return component;
    }

    public static WorldFeatureComponent drawPlane(
            Random random, BlockPallet pallet,
            Direction direction1, int length1,
            Direction direction2, int length2,
            int startX, int startY, int startZ,
            boolean withNotify
    ) {
        WorldFeatureComponent component = new WorldFeatureComponent();
        int blockX = startX;
        int blockY = startY;
        int blockZ = startZ;
        for (int i = 0; i < length2; i++) {
            blockX = startX + direction2.getOffsetX() * i;
            blockY = startY + direction2.getOffsetY() * i;
            blockZ = startZ + direction2.getOffsetZ() * i;
            for (int j = 0; j < length1; j++) {
                component.add(wfb(blockX, blockY, blockZ, pallet.getRandom(random), withNotify));
                blockX += direction1.getOffsetX();
                blockY += direction1.getOffsetY();
                blockZ += direction1.getOffsetZ();
            }
        }
        component.setTail(blockX, blockY, blockZ);
        return component;
    }

    public static WorldFeatureComponent drawVolume(
            int id, int meta,
            Direction directionX, int length1,
            Direction direction2, int length2,
            Direction direction3, int length3,
            int startX, int startY, int startZ,
            boolean withNotify) {
        WorldFeatureComponent component = new WorldFeatureComponent();
        int blockX = startX;
        int blockY = startY;
        int blockZ = startZ;
        for (int i = 0; i < length3; i++) {
            int x3 = startX + direction3.getOffsetX() * i;
            int y3 = startY + direction3.getOffsetY() * i;
            int z3 = startZ + direction3.getOffsetZ() * i;
            for (int j = 0; j < length2; j++) {
                blockX = x3 + direction2.getOffsetX() * j;
                blockY = y3 + direction2.getOffsetY() * j;
                blockZ = z3 + direction2.getOffsetZ() * j;
                for (int k = 0; k < length1; k++) {
                    component.add(wfb(blockX, blockY, blockZ, id, meta, withNotify));
                    blockX += directionX.getOffsetX();
                    blockY += directionX.getOffsetY();
                    blockZ += directionX.getOffsetZ();
                }
            }
        }
        component.setTail(blockX, blockY, blockZ);
        return component;
    }

    public static WorldFeatureComponent drawVolume(
            Random random, BlockPallet pallet,
            Direction direction1, int length1,
            Direction direction2, int length2,
            Direction direction3, int length3,
            int startX, int startY, int startZ,
            boolean withNotify
    ) {
        WorldFeatureComponent component = new WorldFeatureComponent();
        int blockX = startX;
        int blockY = startY;
        int blockZ = startZ;
        for (int i = 0; i < length3; i++) {
            int x3 = startX + direction3.getOffsetX() * i;
            int y3 = startY + direction3.getOffsetY() * i;
            int z3 = startZ + direction3.getOffsetZ() * i;
            for (int j = 0; j < length2; j++) {
                blockX = x3 + direction2.getOffsetX() * j;
                blockY = y3 + direction2.getOffsetY() * j;
                blockZ = z3 + direction2.getOffsetZ() * j;
                for (int k = 0; k < length1; k++) {
                    component.add(wfb(blockX, blockY, blockZ, pallet.getRandom(random), withNotify));
                    blockX += direction1.getOffsetX();
                    blockY += direction1.getOffsetY();
                    blockZ += direction1.getOffsetZ();
                }
            }
        }
        component.setTail(blockX, blockY, blockZ);
        return component;
    }

    public static WorldFeatureComponent drawShell(
            int id, int meta,
            Direction direction1, int length1,
            Direction direction2, int length2,
            Direction direction3, int length3,
            int startX, int startY, int startZ,
            boolean withNotify
    ) {
        WorldFeatureComponent component = new WorldFeatureComponent();
        component.add(drawPlane(
                id, meta,
                direction1, length1,
                direction2, length2,
                startX, startY, startZ, withNotify
        ));
        component.add(drawPlane(
                id, meta,
                direction1, length1,
                direction2, length2,
                startX + direction3.getOffsetX() * (length3 - 1),
                startY + direction3.getOffsetY() * (length3 - 1),
                startZ + direction3.getOffsetZ() * (length3 - 1),
                withNotify
        ));
        component.add(drawPlane(
                id, meta,
                direction1, length1,
                direction3, length3,
                startX, startY, startZ,
                withNotify
        ));
        component.add(drawPlane(
                id, meta,
                direction1, length1,
                direction3, length3,
                startX + direction2.getOffsetX() * (length2 - 1),
                startY + direction2.getOffsetY() * (length2 - 1),
                startZ + direction2.getOffsetZ() * (length2 - 1),
                withNotify
        ));
        component.add(drawPlane(
                id, meta,
                direction2, length2,
                direction3, length3,
                startX, startY, startZ,
                withNotify
        ));
        component.add(drawPlane(
                id, meta,
                direction2, length2,
                direction3, length3,
                startX + direction1.getOffsetX() * (length1 - 1),
                startY + direction1.getOffsetY() * (length1 - 1),
                startZ + direction1.getOffsetZ() * (length1 - 1),
                withNotify
        ));
        return component;
    }

    public static WorldFeatureComponent drawShell(
            Random random, BlockPallet pallet,
            Direction direction1, int length1,
            Direction direction2, int length2,
            Direction direction3, int length3,
            int startX, int startY, int startZ,
            boolean withNotify
    ) {
        WorldFeatureComponent component = new WorldFeatureComponent();
        component.add(drawPlane(
                random, pallet,
                direction1, length1,
                direction2, length2,
                startX, startY, startZ,
                withNotify
        ));
        component.add(drawPlane(random, pallet,
                direction1, length1,
                direction2, length2,
                startX + direction3.getOffsetX() * (length3 - 1),
                startY + direction3.getOffsetY() * (length3 - 1),
                startZ + direction3.getOffsetZ() * (length3 - 1),
                withNotify
        ));
        component.add(drawPlane(
                random, pallet,
                direction1, length1,
                direction3, length3,
                startX, startY, startZ,
                withNotify
        ));
        component.add(drawPlane(
                random, pallet,
                direction1, length1,
                direction3, length3,
                startX + direction2.getOffsetX() * (length2 - 1),
                startY + direction2.getOffsetY() * (length2 - 1),
                startZ + direction2.getOffsetZ() * (length2 - 1),
                withNotify
        ));
        component.add(drawPlane(
                random, pallet,
                direction2, length2,
                direction3, length3,
                startX, startY, startZ,
                withNotify
        ));
        component.add(drawPlane(
                random, pallet,
                direction2, length2,
                direction3, length3,
                startX + direction1.getOffsetX() * (length1 - 1),
                startY + direction1.getOffsetY() * (length1 - 1),
                startZ + direction1.getOffsetZ() * (length1 - 1),
                withNotify
        ));
        return component;
    }

}
