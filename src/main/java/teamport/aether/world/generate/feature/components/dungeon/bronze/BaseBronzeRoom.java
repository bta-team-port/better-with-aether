package teamport.aether.world.generate.feature.components.dungeon.bronze;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.helper.AetherMathHelper;
import teamport.aether.world.generate.feature.BlockPallet;
import teamport.aether.world.generate.feature.components.WorldFeatureBlock;
import teamport.aether.world.generate.feature.components.WorldFeatureComponent;
import teamport.aether.world.generate.feature.components.WorldFeaturePoint;

import java.util.*;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.*;
import static teamport.aether.world.generate.feature.components.WorldFeaturePoint.wfp;
import static teamport.aether.world.generate.feature.dungeon.WorldFeatureAetherBronzeDungeon.*;

public abstract class BaseBronzeRoom {

    public static class Door {
        public Direction heading;
        public WorldFeaturePoint p1;
        public WorldFeaturePoint p2;
        public boolean mark;

        Door(Direction heading, WorldFeaturePoint p1, WorldFeaturePoint p2) {
            this.heading = heading;
            this.p1 = p1;
            this.p2 = p2;
            this.mark = false;
        }
    }

    protected World world;
    protected Random random;
    public int x;
    public int y;
    public int z;
    protected int height;
    protected int width;
    protected int length;
    protected float tolerance;
    public WorldFeatureComponent room;
    public WorldFeatureComponent decoration;
    public WorldFeatureComponent chest;
    public List<Door> doors;

    public static BlockPallet ROOM_PALLET = new BlockPallet();
    static {
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE.id(), 0, 85);
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_LIGHT.id(), 0, 5);
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_TRAPPED.id(), 0, 10);
    }

    public BaseBronzeRoom() {
        this.width = this.length = this.height = 12;
        this.tolerance = 0.65F;
        this.room = new WorldFeatureComponent();
        this.chest = new WorldFeatureComponent();
        this.decoration = new WorldFeatureComponent();
        this.doors = new ArrayList<>();
    }

    public void addDoor(Direction heading, WorldFeaturePoint p1, Direction direction1, int length1, Direction direction2, int length2) {
        WorldFeaturePoint p2 = new WorldFeaturePoint(p1.x, p1.y, p1.z);
        p2.add(direction1.getOffsetX() * length1, direction1.getOffsetY() * length1, direction1.getOffsetZ() * length1);
        p2.add(direction2.getOffsetX() * length2, direction2.getOffsetY() * length2, direction2.getOffsetZ() * length2);
        this.addDoor(heading, p1, p2);
    }

    public void addDoor(Direction direction, WorldFeaturePoint p1, WorldFeaturePoint p2) {
        this.doors.add(new Door(direction, p1, p2));
    }

    public final boolean place(World world, Random random, int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.random = random;
        this.world = world;
        if (!canPlace()) return false;
        this.adjustDoorCoords();
        this.makeRoom();
        this.placeRoom();
        return true;
    }

    public BaseBronzeRoom set(World world, Random random, int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
        this.random = random;
        this.world = world;
        return this;
    }

    public boolean canPlace() {
        if (this.y <= 11) {
            return false;
        }

        WorldFeatureComponent check = drawVolume(0, 0, SOUTH, width, UP, height, EAST, length, x, y, z, true);

        int countAir = 0;
        for (WorldFeaturePoint point : check.blockList) {
            int id = world.getBlockId(point.x, point.y, point.z);
            if (id == 0) countAir++;
        }

        return check.blockList.size() * tolerance >= countAir;
    }

    public void adjustDoorCoords() {
        for (Door door : doors) {
            door.p1.add(x, y, z);
            door.p2.add(x, y, z);
        }
    }

    public abstract void makeRoom();

    public void placeRoom() {
        Map<WorldFeaturePoint, WorldFeatureBlock> blockMap = new HashMap<>();
        for (WorldFeatureBlock block : room.blockList) {
            WorldFeaturePoint point = new WorldFeaturePoint(block.x, block.y, block.z);
            blockMap.put(point, block);
        }
        decoration.add(chest);
        for(WorldFeatureBlock block : decoration.blockList){
            WorldFeatureBlock otherBlock = blockMap.computeIfAbsent(wfp(block.x, block.y, block.z), key -> block);
            otherBlock.blockID = block.blockID;
            otherBlock.metadata = block.metadata;
            otherBlock.withNotify = block.withNotify;
        }
        for(WorldFeatureBlock wfblock : blockMap.values()){
            if (this.roomCanReplace(wfblock)) {
                wfblock.place(world);
            }
        }
        for (WorldFeatureBlock wfblock : this.chest.blockList) {
            populateChest(world, random, wfblock, BaseBronzeRoom::generateLoot);
        }
    }

    private boolean roomCanReplace(WorldFeatureBlock wfblock) {
        Block<?> block = world.getBlock(wfblock.x, wfblock.y, wfblock.z);
        int blockID = block == null ? 0 : block.id();
        Material blockMaterial = blockID == 0 ? Material.air : block.getMaterial();
        if (blockID == AetherBlocks.CHEST_MIMIC.id() || blockID == AetherBlocks.CHEST_PLANKS_SKYROOT.id()) {
            world.removeBlockTileEntity(wfblock.x, wfblock.y, wfblock.z);
            return true;
        }
        return BlockTags.CAVES_CUT_THROUGH.appliesTo(block)
                || blockMaterial == Material.grass
                || blockMaterial == Material.dirt
                || blockMaterial == Material.marble
                || blockMaterial == Material.moss
                || blockMaterial == Material.cloth
                || blockMaterial.isStone()
                || blockMaterial.isLiquid();
    }

    public static List<ItemStack> generateLoot(Random random) {
        List<ItemStack> loot = new ArrayList<>();
        //min 8 max 10
        int count = random.nextInt(3) + 8;
        for (int i = 0; i < count; i++) loot.add(JUNK.getRandom(random).getItemStack());
        // min 2 max 5
        count = random.nextInt(4) + 2;
        for (int i = 0; i < count; i++) loot.add(AMMO.getRandom(random).getItemStack());
        // min 2 max 4
        count = random.nextInt(3) + 2;
        for (int i = 0; i < count; i++) loot.add(FOOD.getRandom(random).getItemStack());
        // min 1 max 2
        count = AetherMathHelper.invertedExponentialCapped(random, 0.5F, 2) + 1;
        for (int i = 0; i < count; i++) loot.add(ARMOR.getRandom(random).getItemStack());
        // min 0 max 2
        count = AetherMathHelper.invertedExponentialCapped(random, 0.5F, 2);
        for (int i = 0; i < count; i++) loot.add(GADGET.getRandom(random).getItemStack());
        return loot;
    }

    public boolean decorationCanReplace(WorldFeatureBlock block) {
        return true;
    }

    public List<Door> getAvailableDoors() {
        List<Door> freeDoors = new ArrayList<>();
        for (Door door : doors) {
            if (!door.mark) {
                freeDoors.add(door);
            }
        }
        return freeDoors;
    }

    // not sure if I need this
    public void markDoor(@Nullable Door door) {
        if(door == null) return;
        door.mark = true;
    }

    public void markAllDoor() {
        doors.forEach(d -> d.mark = true);
    }

    public List<WorldFeaturePoint> getAnkers(WorldFeaturePoint doorPoint, Direction heading) {
        List<WorldFeaturePoint> list = new ArrayList<>();
        for (Door door : doors) {
            if (door.heading == heading.getOpposite()) {
                list.add(new WorldFeaturePoint(
                        doorPoint.x - door.p1.x,
                        doorPoint.y - door.p1.y,
                        doorPoint.z - door.p1.z
                ));
            }
        }
        return list;
    }

    public Door getDoor(WorldFeaturePoint nextDoor) {
        for(Door door : doors){
            if(door.p1.equals(nextDoor) || door.p2.equals(nextDoor)){
                return door;
            }
        }
        return null;
    }

    public boolean intersect(WorldFeaturePoint point) {
        return point.x >= this.x && point.x < this.x + length
                && point.y >= this.y && point.y < this.y + height
                && point.z >= this.z && point.z < this.z + width;
    }

    // TODO implement this
    public boolean canConnect(Door door) {
        return false;
    }

}
