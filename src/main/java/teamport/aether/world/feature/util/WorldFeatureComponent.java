package teamport.aether.world.feature.util;

import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.helper.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.world.feature.util.WorldFeatureBlock.wfb;

public class WorldFeatureComponent {
    WorldFeaturePoint min;
    WorldFeaturePoint max;
    public WorldFeaturePoint anker;
    public WorldFeaturePoint tail;
    public List<WorldFeatureBlock> blockList;

    @FunctionalInterface
    public interface LootGenerator {
        List<ItemStack> generate(Random random);
    }

    public WorldFeatureComponent() {
        this.blockList = new ArrayList<>();
    }

    public WorldFeatureComponent(int startX, int startY, int startZ) {
        this.blockList = new ArrayList<>();
        this.anker = new WorldFeaturePoint(startX, startY, startZ);
    }

    public static WorldFeatureComponent wfc(int x, int y, int z) {
        return new WorldFeatureComponent(x, y, z);
    }

    public void add(WorldFeatureComponent component) {
        this.blockList.addAll(component.blockList);
        this.tail = component.tail;
    }

    public void add(List<WorldFeatureBlock> list) {
        this.blockList.addAll(list);
        this.tail = makePoint(list.get(list.size() - 1));
    }

    private WorldFeaturePoint makePoint(WorldFeatureBlock wfb) {
        return new WorldFeaturePoint(wfb.x, wfb.y, wfb.z);
    }

    public void add(WorldFeatureBlock wfb) {
        this.blockList.add(wfb);
        this.tail = makePoint(wfb);
    }

    public void setTail(int x, int y, int z) {
        this.tail = new WorldFeaturePoint(x, y, z);
    }

    public void rotateYAxis(int fixPointX, int fixPointY, int fixPointZ, float angle) {
        for (WorldFeatureBlock block : this.blockList) {
            block.rotateYAroundPivot(fixPointX, fixPointY, fixPointZ, angle);
        }
    }

    public void rotateYAroundPivot(WorldFeaturePoint pivot, Direction direction) {
        blockList.forEach(d -> d.rotateYAroundPivot(pivot, direction));
    }


    public void place(World world) {
        for (WorldFeatureBlock worldFeatureBlock : this.blockList) {
            worldFeatureBlock.place(world);
        }
    }

    public WorldFeaturePoint getAnker() {
        return anker;
    }

    public WorldFeaturePoint getTail() {
        return tail;
    }

    public static void populateChest(
            World world,
            Random random,
            WorldFeatureBlock wfb,
            LootGenerator lootGenerator
    ) {
        Container inventory = BlockLogicChest.getInventory(world, wfb.x, wfb.y, wfb.z);

        if (inventory == null) return;
        List<ItemStack> stacks = lootGenerator.generate(random);

        for (ItemStack stack : stacks) {
            WorldFeatureComponent.placeItemInChest(random, stack, inventory);
        }
    }

    public static void placeItemInChest(Random random, @Nullable ItemStack itemstack, @NonNull Container inventory) {
        if (itemstack == null) return;
        int invSize = inventory.getContainerSize();
        int index = random.nextInt(invSize);
        int count = invSize;
        while (count-- > 0) {
            ItemStack stack = inventory.getItem(index);
            if (stack == null) {
                break;
            }
            if (stack.itemID == itemstack.itemID) {
                if (stack.getMaxStackSize() >= stack.stackSize + itemstack.stackSize) {
                    itemstack.stackSize += stack.stackSize;
                    break;
                } else {
                    itemstack.stackSize = itemstack.stackSize - stack.getMaxStackSize() + stack.stackSize;
                    stack.stackSize = stack.getMaxStackSize();
                }
            }
            index++;
            if (index >= invSize) {
                index = 0;
            }
        }
        inventory.setItem(index, itemstack);
    }

    public static WorldFeatureComponent drawSphere(
            Random random, BlockPallet pallet,
            int x, int y, int z,
            int radius,
            boolean withNotify
    ) {
        assert radius > 0 : "Radius has to be bigger zero!";
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
        assert length > 0 : "Length has to be bigger zero";
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
        assert length > 0 : "Length has to be bigger zero";
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
        length1 = Math.max(length1, 1);
        length2 = Math.max(length2, 1);
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
        length1 = Math.max(length1, 1);
        length2 = Math.max(length2, 1);
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
            Direction direction1, int length1,
            Direction direction2, int length2,
            Direction direction3, int length3,
            int startX, int startY, int startZ,
            boolean withNotify) {
        WorldFeatureComponent component = new WorldFeatureComponent();
        int blockX = startX;
        int blockY = startY;
        int blockZ = startZ;
        length1 = Math.max(length1, 1);
        length2 = Math.max(length2, 1);
        length3 = Math.max(length3, 1);
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
                    blockX += direction1.getOffsetX();
                    blockY += direction1.getOffsetY();
                    blockZ += direction1.getOffsetZ();
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
        length1 = Math.max(length1, 1);
        length2 = Math.max(length2, 1);
        length3 = Math.max(length3, 1);
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

    public static WorldFeatureComponent drawHollowShell(
            Random random, BlockPallet pallet,
            Direction direction1, int length1,
            Direction direction2, int length2,
            Direction direction3, int length3,
            int startX, int startY, int startZ,
            boolean withNotify
    ) {
        WorldFeatureComponent hollow = drawVolume(
                random, pallet,
                direction1, length1,
                direction2, length2,
                direction3, length3,
                startX, startY, startZ, withNotify
        );
        hollow.add(drawVolume(
                0, 0,
                direction1, length1 - 2,
                direction2, length2 - 2,
                direction3, length3 - 2,
                startX + direction1.getOffsetX() + direction2.getOffsetX() + direction3.getOffsetX(),
                startY + direction1.getOffsetY() + direction2.getOffsetY() + direction3.getOffsetY(),
                startZ + direction1.getOffsetZ() + direction2.getOffsetZ() + direction3.getOffsetZ(),
                withNotify)
        );
        return hollow;
    }

    public static WorldFeatureComponent drawSquareCylinder(
            Random random, BlockPallet pallet,
            Direction direction1, int length1,
            Direction direction2, int length2,
            Direction direction3, int length3,
            int startX, int startY, int startZ,
            boolean withNotify
    ) {
        WorldFeatureComponent cylinder = drawVolume(
                random, pallet,
                direction1, length1,
                direction2, length2,
                direction3, length3,
                startX, startY, startZ, withNotify
        );
        cylinder.add(drawVolume(
                0, 0,
                direction1, length1 - 2,
                direction2, length2 - 2,
                direction3, length3,
                startX + direction1.getOffsetX() + direction2.getOffsetX() + direction3.getOffsetX(),
                startY,
                startZ + direction1.getOffsetZ() + direction2.getOffsetZ() + direction3.getOffsetZ(),
                withNotify
        ));
        return cylinder;
    }

    public static WorldFeatureComponent drawVolume(int id, int meta, WorldFeaturePoint p1, WorldFeaturePoint p2, boolean withNotify) {
        int minX = Math.min(p1.x, p2.x);
        int minY = Math.min(p1.y, p2.y);
        int minZ = Math.min(p1.z, p2.z);
        int length1 = Math.abs(p1.x - p2.x);
        int length2 = Math.abs(p1.y - p2.y);
        int length3 = Math.abs(p1.z - p2.z);
        return drawVolume(id, meta, EAST, length1, UP, length2, SOUTH, length3, minX, minY, minZ, withNotify);
    }

    public static WorldFeatureComponent drawVolumeWithPoint(int id, int meta, WorldFeaturePoint p1, WorldFeaturePoint p2, boolean withNotify) {
        int length1 = p1.x - p2.x;
        int length2 = p1.y - p2.y;
        int length3 = p1.z - p2.z;
        Direction dir1 = length1 >= 0 ? WEST : EAST;
        Direction dir2 = length2 >= 0 ? DOWN : UP;
        Direction dir3 = length3 >= 0 ? NORTH : SOUTH;
        return drawVolume(id, meta, dir1, Math.abs(length1), dir2, Math.abs(length2), dir3, Math.abs(length3), p1.x, p1.y, p1.z, withNotify);
    }

    public static WorldFeatureComponent drawVolumeWithPoint(Random random, BlockPallet pallet, WorldFeaturePoint p1, WorldFeaturePoint p2, boolean withNotify) {
        int length1 = p1.x - p2.x;
        int length2 = p1.y - p2.y;
        int length3 = p1.z - p2.z;
        Direction dir1 = length1 >= 0 ? WEST : EAST;
        Direction dir2 = length2 >= 0 ? DOWN : UP;
        Direction dir3 = length3 >= 0 ? NORTH : SOUTH;
        return drawVolume(random, pallet, dir1, Math.abs(length1), dir2, Math.abs(length2), dir3, Math.abs(length3), p1.x, p1.y, p1.z, withNotify);
    }

    public static WorldFeatureComponent drawVolume(Random random, BlockPallet pallet, WorldFeaturePoint p1, WorldFeaturePoint p2, boolean withNotify) {
        int minX = Math.min(p1.x, p2.x);
        int minY = Math.min(p1.y, p2.y);
        int minZ = Math.min(p1.z, p2.z);
        int length1 = Math.abs(p1.x - p2.x);
        int length2 = Math.abs(p1.y - p2.y);
        int length3 = Math.abs(p1.z - p2.z);
        return drawVolume(random, pallet, EAST, length1, UP, length2, SOUTH, length3, minX, minY, minZ, withNotify);
    }

    public static void iterate3d(Pair<WorldFeaturePoint, WorldFeaturePoint> area, Consumer<WorldFeaturePoint> func) {
        iterate3d(area.first, area.second, func);
    }

    public static void iterate3d(WorldFeaturePoint first, WorldFeaturePoint second, Consumer<WorldFeaturePoint> func) {
        int firstX = Math.min(first.x, second.x);
        int secondX = Math.max(first.x, second.x);
        int firstY = Math.min(first.y, second.y);
        int secondY = Math.max(first.y, second.y);
        int firstZ = Math.min(first.z, second.z);
        int secondZ = Math.max(first.z, second.z);

        for (int x = firstX; x < secondX; x++) {
            for (int y = firstY; y < secondY; y++) {
                for (int z = firstZ; z < secondZ; z++) {
                    func.accept(WorldFeaturePoint.wfp(x, y, z));
                }
            }
        }
    }

    public static WorldFeatureComponent drawVolumeX(int blockID, int metadata, WorldFeaturePoint p1, WorldFeaturePoint p2, boolean withNotify) {
        int dx = p1.x - p2.x;
        int dy = p1.y - p2.y;
        int dz = p1.z - p2.z;
        Direction direction1 = dx > 0 ? WEST : EAST;
        Direction direction2 = dy > 0 ? DOWN : UP;
        Direction direction3 = dz > 0 ? NORTH : SOUTH;
        return drawVolume(blockID, metadata, direction1, Math.abs(dx), direction2, Math.abs(dy), direction3, Math.abs(dz), p1.x, p1.y, p1.z, withNotify);
    }

    public static WorldFeatureComponent drawVolumeX(Random random, BlockPallet pallet, WorldFeaturePoint p1, WorldFeaturePoint p2, boolean withNotify) {
        int minX = Math.min(p1.x, p2.x);
        int minY = Math.min(p1.y, p2.y);
        int minZ = Math.min(p1.z, p2.z);
        int length1 = Math.abs(p1.x - p2.x);
        int length2 = Math.abs(p1.y - p2.y);
        int length3 = Math.abs(p1.z - p2.z);
        return drawVolume(random, pallet, EAST, length1, UP, length2, SOUTH, length3, minX, minY, minZ, withNotify);
    }

    public void moveByOffset(WorldFeaturePoint offset) {
        blockList.forEach(p -> p.add(offset));
    }

}
