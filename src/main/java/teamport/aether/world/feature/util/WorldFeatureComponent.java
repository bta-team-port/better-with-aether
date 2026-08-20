package teamport.aether.world.feature.util;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.world.feature.util.WorldFeatureBlock.wfb;

public class WorldFeatureComponent {
    private WorldFeaturePoint tail;
    private final List<WorldFeatureBlock> blockList;

    @FunctionalInterface
    public interface LootGenerator {
        List<ItemStack> generate(Random random);
    }

    public WorldFeatureComponent() {
        this.blockList = new ArrayList<>();
    }

    public void add(@NonNull WorldFeatureComponent component) {
        this.blockList.addAll(component.blockList);
        this.tail = component.tail;
    }

    public void add(List<WorldFeatureBlock> list) {
        this.blockList.addAll(list);
        this.tail = makePoint(list.get(list.size() - 1));
    }

    private @NonNull WorldFeaturePoint makePoint(@NonNull WorldFeatureBlock wfb) {
        return new WorldFeaturePoint(wfb.getX(), wfb.getY(), wfb.getZ());
    }

    public void add(WorldFeatureBlock wfb) {
        this.blockList.add(wfb);
        this.tail = makePoint(wfb);
    }

    public void setTail(int x, int y, int z) {
        this.tail = new WorldFeaturePoint(x, y, z);
    }

    public void rotateYAroundPivot(WorldFeaturePoint pivot, Direction direction) {
        blockList.forEach(d -> d.rotateYAroundPivot(pivot, direction));
    }


    public void place(World world) {
        for (WorldFeatureBlock worldFeatureBlock : this.blockList) {
            worldFeatureBlock.place(world);
        }
    }

    public WorldFeaturePoint getTail() {
        return tail;
    }

    public static void populateChest(
        World world,
        Random random,
        @NonNull WorldFeatureBlock wfb,
        LootGenerator lootGenerator
    ) {
        Container inventory = getOrCreateChestInventory(world, new TilePos(wfb.getX(), wfb.getY(), wfb.getZ()));

        if (inventory == null) return;
        List<ItemStack> stacks = lootGenerator.generate(random);

        for (ItemStack stack : stacks) {
            WorldFeatureComponent.placeItemInChest(random, stack, inventory);
        }
    }

    public static @Nullable Container getOrCreateChestInventory(@NonNull World world, TilePos pos) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity == null) {
            Block<?> block = world.getBlockType(pos);
            if (block.entitySupplier == null) return null;

            tileEntity = block.entitySupplier.get();
            world.setTileEntity(pos, tileEntity);
        }

        if (!(tileEntity instanceof Container)) return null;
        return BlockLogicChest.getInventory(world, pos);
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

    public static @NonNull WorldFeatureComponent drawSphere(
        Random random, BlockPallet pallet,
        int x, int y, int z,
        int radius,
        boolean withNotify
    ) {
        if (radius <= 0) throw new IllegalArgumentException("Radius has to be greater than zero!");

        WorldFeatureComponent component = new WorldFeatureComponent();
        for (int blockX = x - radius; blockX <= x + radius; blockX++) {
            for (int blockY = y - radius; blockY <= y + radius; blockY++) {
                for (int blockZ = z - radius; blockZ <= z + radius; blockZ++) {
                    double offX = (double) x - blockX;
                    double offY = (double) y - blockY;
                    double offZ = (double) z - blockZ;
                    double currentDist = offX * offX + offY * offY + offZ * offZ;
                    if (currentDist < radius * radius) {
                        component.add(wfb(blockX, blockY, blockZ, pallet.getRandom(random), withNotify));
                    }
                }
            }
        }
        return component;
    }

    public static @NonNull WorldFeatureComponent drawSpheroid(
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

    public static @NonNull WorldFeatureComponent drawLine(
        int id, int meta,
        Direction direction, int length,
        int startX, int startY, int startZ,
        boolean withNotify
    ) {
        if (length <= 0) throw new IllegalArgumentException("Length has to be greater than zero");
        WorldFeatureComponent component = new WorldFeatureComponent();
        for (int i = 0; i < length - 1; i++) {
            component.add(wfb(startX, startY, startZ, id, meta, withNotify));
            startX += direction.offsetX();
            startY += direction.offsetY();
            startZ += direction.offsetZ();
        }
        component.add(wfb(startX, startY, startZ, id, meta, withNotify));
        return component;
    }

    public static @NonNull WorldFeatureComponent drawLine(
        Random random, BlockPallet pallet,
        Direction direction, int length,
        int startX, int startY, int startZ,
        boolean withNotify
    ) {
        if (length <= 0) throw new IllegalArgumentException("Length has to be greater than zero");
        WorldFeatureComponent component = new WorldFeatureComponent();
        for (int i = 0; i < length - 1; i++) {
            component.add(wfb(startX, startY, startZ, pallet.getRandom(random), withNotify));
            startX += direction.offsetX();
            startY += direction.offsetY();
            startZ += direction.offsetZ();
        }
        component.add(wfb(startX, startY, startZ, pallet.getRandom(random), withNotify));
        return component;
    }

    public static @NonNull WorldFeatureComponent drawPlane(
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
            blockX = startX + direction2.offsetX() * i;
            blockY = startY + direction2.offsetY() * i;
            blockZ = startZ + direction2.offsetZ() * i;
            for (int j = 0; j < length1; j++) {
                component.add(wfb(blockX, blockY, blockZ, id, meta, withNotify));
                blockX += direction1.offsetX();
                blockY += direction1.offsetY();
                blockZ += direction1.offsetZ();
            }
        }
        component.setTail(blockX, blockY, blockZ);
        return component;
    }

    public static @NonNull WorldFeatureComponent drawPlane(
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
            blockX = startX + direction2.offsetX() * i;
            blockY = startY + direction2.offsetY() * i;
            blockZ = startZ + direction2.offsetZ() * i;
            for (int j = 0; j < length1; j++) {
                component.add(wfb(blockX, blockY, blockZ, pallet.getRandom(random), withNotify));
                blockX += direction1.offsetX();
                blockY += direction1.offsetY();
                blockZ += direction1.offsetZ();
            }
        }
        component.setTail(blockX, blockY, blockZ);
        return component;
    }

    public static @NonNull WorldFeatureComponent drawVolume(
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
            int x3 = startX + direction3.offsetX() * i;
            int y3 = startY + direction3.offsetY() * i;
            int z3 = startZ + direction3.offsetZ() * i;
            for (int j = 0; j < length2; j++) {
                blockX = x3 + direction2.offsetX() * j;
                blockY = y3 + direction2.offsetY() * j;
                blockZ = z3 + direction2.offsetZ() * j;
                for (int k = 0; k < length1; k++) {
                    component.add(wfb(blockX, blockY, blockZ, id, meta, withNotify));
                    blockX += direction1.offsetX();
                    blockY += direction1.offsetY();
                    blockZ += direction1.offsetZ();
                }
            }
        }
        component.setTail(blockX, blockY, blockZ);
        return component;
    }

    public static @NonNull WorldFeatureComponent drawVolume(
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
            int x3 = startX + direction3.offsetX() * i;
            int y3 = startY + direction3.offsetY() * i;
            int z3 = startZ + direction3.offsetZ() * i;
            for (int j = 0; j < length2; j++) {
                blockX = x3 + direction2.offsetX() * j;
                blockY = y3 + direction2.offsetY() * j;
                blockZ = z3 + direction2.offsetZ() * j;
                for (int k = 0; k < length1; k++) {
                    component.add(wfb(blockX, blockY, blockZ, pallet.getRandom(random), withNotify));
                    blockX += direction1.offsetX();
                    blockY += direction1.offsetY();
                    blockZ += direction1.offsetZ();
                }
            }
        }
        component.setTail(blockX, blockY, blockZ);
        return component;
    }

    public static @NonNull WorldFeatureComponent drawShell(
        int id, int meta,
        Direction direction1, int length1,
        Direction direction2, int length2,
        @NonNull Direction direction3, int length3,
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
            startX + direction3.offsetX() * (length3 - 1),
            startY + direction3.offsetY() * (length3 - 1),
            startZ + direction3.offsetZ() * (length3 - 1),
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
            startX + direction2.offsetX() * (length2 - 1),
            startY + direction2.offsetY() * (length2 - 1),
            startZ + direction2.offsetZ() * (length2 - 1),
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
            startX + direction1.offsetX() * (length1 - 1),
            startY + direction1.offsetY() * (length1 - 1),
            startZ + direction1.offsetZ() * (length1 - 1),
            withNotify
        ));
        return component;
    }

    public static @NonNull WorldFeatureComponent drawShell(
        Random random, BlockPallet pallet,
        Direction direction1, int length1,
        Direction direction2, int length2,
        @NonNull Direction direction3, int length3,
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
            startX + direction3.offsetX() * (length3 - 1),
            startY + direction3.offsetY() * (length3 - 1),
            startZ + direction3.offsetZ() * (length3 - 1),
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
            startX + direction2.offsetX() * (length2 - 1),
            startY + direction2.offsetY() * (length2 - 1),
            startZ + direction2.offsetZ() * (length2 - 1),
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
            startX + direction1.offsetX() * (length1 - 1),
            startY + direction1.offsetY() * (length1 - 1),
            startZ + direction1.offsetZ() * (length1 - 1),
            withNotify
        ));
        return component;
    }

    public static @NonNull WorldFeatureComponent drawHollowShell(
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
            startX + direction1.offsetX() + direction2.offsetX() + direction3.offsetX(),
            startY + direction1.offsetY() + direction2.offsetY() + direction3.offsetY(),
            startZ + direction1.offsetZ() + direction2.offsetZ() + direction3.offsetZ(),
            withNotify)
        );
        return hollow;
    }

    public static @NonNull WorldFeatureComponent drawSquareCylinder(
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
            startX + direction1.offsetX() + direction2.offsetX() + direction3.offsetX(),
            startY,
            startZ + direction1.offsetZ() + direction2.offsetZ() + direction3.offsetZ(),
            withNotify
        ));
        return cylinder;
    }

    public static @NonNull WorldFeatureComponent drawVolume(int id, int meta, @NonNull WorldFeaturePoint p1, @NonNull WorldFeaturePoint p2, boolean withNotify) {
        int minX = Math.min(p1.getX(), p2.getX());
        int minY = Math.min(p1.getY(), p2.getY());
        int minZ = Math.min(p1.getZ(), p2.getZ());
        int length1 = Math.abs(p1.getX() - p2.getX());
        int length2 = Math.abs(p1.getY() - p2.getY());
        int length3 = Math.abs(p1.getZ() - p2.getZ());
        return drawVolume(id, meta, EAST, length1, UP, length2, SOUTH, length3, minX, minY, minZ, withNotify);
    }

    public static @NonNull WorldFeatureComponent drawVolumeWithPoint(int id, int meta, @NonNull WorldFeaturePoint p1, @NonNull WorldFeaturePoint p2, boolean withNotify) {
        int length1 = p1.getX() - p2.getX();
        int length2 = p1.getY() - p2.getY();
        int length3 = p1.getZ() - p2.getZ();
        Direction dir1 = length1 >= 0 ? WEST : EAST;
        Direction dir2 = length2 >= 0 ? DOWN : UP;
        Direction dir3 = length3 >= 0 ? NORTH : SOUTH;
        return drawVolume(id, meta, dir1, Math.abs(length1), dir2, Math.abs(length2), dir3, Math.abs(length3), p1.getX(), p1.getY(), p1.getZ(), withNotify);
    }

    public static @NonNull WorldFeatureComponent drawVolumeWithPoint(Random random, BlockPallet pallet, @NonNull WorldFeaturePoint p1, @NonNull WorldFeaturePoint p2, boolean withNotify) {
        int length1 = p1.getX() - p2.getX();
        int length2 = p1.getY() - p2.getY();
        int length3 = p1.getZ() - p2.getZ();
        Direction dir1 = length1 >= 0 ? WEST : EAST;
        Direction dir2 = length2 >= 0 ? DOWN : UP;
        Direction dir3 = length3 >= 0 ? NORTH : SOUTH;
        return drawVolume(random, pallet, dir1, Math.abs(length1), dir2, Math.abs(length2), dir3, Math.abs(length3), p1.getX(), p1.getY(), p1.getZ(), withNotify);
    }

    public static @NonNull WorldFeatureComponent drawVolume(Random random, BlockPallet pallet, @NonNull WorldFeaturePoint p1, @NonNull WorldFeaturePoint p2, boolean withNotify) {
        int minX = Math.min(p1.getX(), p2.getX());
        int minY = Math.min(p1.getY(), p2.getY());
        int minZ = Math.min(p1.getZ(), p2.getZ());
        int length1 = Math.abs(p1.getX() - p2.getX());
        int length2 = Math.abs(p1.getY() - p2.getY());
        int length3 = Math.abs(p1.getZ() - p2.getZ());
        return drawVolume(random, pallet, EAST, length1, UP, length2, SOUTH, length3, minX, minY, minZ, withNotify);
    }

    public static void iterate3d(@NonNull Pair<WorldFeaturePoint, WorldFeaturePoint> area, Consumer<WorldFeaturePoint> func) {
        iterate3d(area.first(), area.second(), func);
    }

    public static void iterate3d(@NonNull WorldFeaturePoint first, @NonNull WorldFeaturePoint second, Consumer<WorldFeaturePoint> func) {
        int firstX = Math.min(first.getX(), second.getX());
        int secondX = Math.max(first.getX(), second.getX());
        int firstY = Math.min(first.getY(), second.getY());
        int secondY = Math.max(first.getY(), second.getY());
        int firstZ = Math.min(first.getZ(), second.getZ());
        int secondZ = Math.max(first.getZ(), second.getZ());

        for (int x = firstX; x < secondX; x++) {
            for (int y = firstY; y < secondY; y++) {
                for (int z = firstZ; z < secondZ; z++) {
                    func.accept(WorldFeaturePoint.wfp(x, y, z));
                }
            }
        }
    }

    public void moveByOffset(WorldFeaturePoint offset) {
        blockList.forEach(p -> p.add(offset));
    }
    public List<WorldFeatureBlock> getBlockList() {
        return blockList;
    }
}
