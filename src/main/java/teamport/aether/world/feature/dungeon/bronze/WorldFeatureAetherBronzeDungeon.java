package teamport.aether.world.feature.dungeon.bronze;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import teamport.aether.AetherMod;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.compat.AetherPlugin;
import teamport.aether.helper.AetherMathHelper;
import teamport.aether.helper.unboxed.PriorityEntry;
import teamport.aether.items.AetherItems;
import teamport.aether.world.AetherDimension;
import teamport.aether.world.feature.dungeon.bronze.component.*;
import teamport.aether.world.feature.dungeon.bronze.component.BaseBronzeRoom.Door;
import teamport.aether.world.feature.util.BlockPallet;
import teamport.aether.world.feature.util.WorldFeatureBlock;
import teamport.aether.world.feature.util.WorldFeatureComponent;
import teamport.aether.world.feature.util.WorldFeaturePoint;

import java.util.*;
import java.util.function.Supplier;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.helper.unboxed.PriorityEntry.pEntry;
import static teamport.aether.world.feature.dungeon.bronze.component.BaseBronzeRoom.ClosingType.*;
import static teamport.aether.world.feature.dungeon.bronze.component.BaseBronzeRoom.Door.door;
import static teamport.aether.world.feature.util.WorldFeatureComponent.drawVolume;
import static teamport.aether.world.feature.util.WorldFeatureComponent.drawVolumeWithPoint;
import static teamport.aether.world.feature.util.WorldFeaturePoint.wfp;

public class WorldFeatureAetherBronzeDungeon extends WorldFeature {
    private static final float MAX_WEIGHT = 40;
    private static final int TUNNEL_WIDTH = 6;
    private static final int TUNNEL_COUNT = 4;
    private static final int TUNNEL_MAX_LENGTH = 100;
    private World world;
    private Random random;

    public static final BlockPallet carvedHolystone = new BlockPallet();
    public static final BlockPallet lockedCarvedHolystone = new BlockPallet();
    public static final BlockPallet holystone = new BlockPallet();
    public static final BlockPallet chestsOrMimic = new BlockPallet();

    static {
        carvedHolystone.addEntry(AetherBlocks.CARVED_STONE.id(), 85);
        carvedHolystone.addEntry(AetherBlocks.CARVED_STONE_LIGHT.id(), 5);
        carvedHolystone.addEntry(AetherBlocks.CARVED_STONE_TRAPPED.id(), 10);

        lockedCarvedHolystone.addEntry(AetherBlocks.CARVED_STONE_LOCKED.id(), 85);
        lockedCarvedHolystone.addEntry(AetherBlocks.CARVED_STONE_LIGHT_LOCKED.id(), 5);

        holystone.addEntry(AetherBlocks.COBBLE_HOLYSTONE.id(), 90);
        holystone.addEntry(AetherBlocks.COBBLE_HOLYSTONE_MOSSY.id(), 10);

        chestsOrMimic.addEntry(0, 1);
        chestsOrMimic.addEntry(AetherBlocks.CHEST_MIMIC_SKYROOT.id(), 1);
        chestsOrMimic.addEntry(AetherBlocks.CHEST_PLANKS_SKYROOT.id(), 1);
    }

    public static final WeightedRandomBag<WeightedRandomLootObject> ARMOR = new WeightedRandomBag<>();
    public static final WeightedRandomBag<WeightedRandomLootObject> JUNK = new WeightedRandomBag<>();
    public static final WeightedRandomBag<WeightedRandomLootObject> AMMO = new WeightedRandomBag<>();
    public static final WeightedRandomBag<WeightedRandomLootObject> GADGET = new WeightedRandomBag<>();
    public static final WeightedRandomBag<WeightedRandomLootObject> FOOD = new WeightedRandomBag<>();

    static {
        // junk                                    8-10
        JUNK.addEntry(new WeightedRandomLootObject(null), 8);
        JUNK.addEntry(new WeightedRandomLootObject(AetherItems.AMBROSIUM.getDefaultStack(), 1, 6), 4);
        JUNK.addEntry(new WeightedRandomLootObject(AetherBlocks.TORCH_AMBROSIUM.getDefaultStack(), 1, 4), 4);
        JUNK.addEntry(new WeightedRandomLootObject(AetherBlocks.HOLYSTONE.getDefaultStack(), 4, 12), 2);
        JUNK.addEntry(new WeightedRandomLootObject(AetherBlocks.HOLYSTONE_MOSSY.getDefaultStack(), 4, 12), 2);
        JUNK.addEntry(new WeightedRandomLootObject(AetherBlocks.ICESTONE.getDefaultStack(), 4, 12), 2);
        JUNK.addEntry(new WeightedRandomLootObject(AetherBlocks.CARVED_STONE_LIGHT.getDefaultStack(), 4, 12), 2);
        JUNK.addEntry(new WeightedRandomLootObject(AetherBlocks.CARVED_STONE.getDefaultStack(), 4, 12), 2);
        JUNK.addEntry(new WeightedRandomLootObject(AetherItems.BUCKET_SKYROOT.getDefaultStack()), 1);
        JUNK.addEntry(new WeightedRandomLootObject(AetherItems.ZANITE.getDefaultStack(), 1, 4), 1);
        // food                                     2-4
        FOOD.addEntry(new WeightedRandomLootObject(null), 8);
        FOOD.addEntry(new WeightedRandomLootObject(AetherItems.FOOD_HEALING_STONE.getDefaultStack(), 1, 4), 4);
        FOOD.addEntry(new WeightedRandomLootObject(AetherItems.FOOD_GUMMY_BLUE.getDefaultStack(), 1, 4), 2);
        FOOD.addEntry(new WeightedRandomLootObject(AetherItems.FOOD_GUMMY_GOLD.getDefaultStack(), 1, 2), 1);
        // armor & tool - chestplate                0-2(super rare)
        int minTool = AetherItems.TOOL_PICKAXE_HOLYSTONE.getMaxDamage() / 2;
        int maxTool = AetherItems.TOOL_PICKAXE_HOLYSTONE.getMaxDamage();
        ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_PICKAXE_HOLYSTONE.getDefaultStack())
            .setRandomMetadata(minTool, maxTool), 1);
        ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_AXE_HOLYSTONE.getDefaultStack())
            .setRandomMetadata(minTool, maxTool), 1);
        ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SWORD_HOLYSTONE.getDefaultStack())
            .setRandomMetadata(minTool, maxTool), 1);
        ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SHOVEL_HOLYSTONE.getDefaultStack())
            .setRandomMetadata(minTool, maxTool), 1);
        // ammo                                     2-5
        AMMO.addEntry(new WeightedRandomLootObject(null), 8);
        AMMO.addEntry(new WeightedRandomLootObject(AetherItems.AMMO_DART_GOLDEN.getDefaultStack(), 2, 6), 4);
        AMMO.addEntry(new WeightedRandomLootObject(AetherItems.AMMO_DART_POISON.getDefaultStack(), 2, 6), 2);
        AMMO.addEntry(new WeightedRandomLootObject(AetherItems.AMMO_DART_ENCHANTED.getDefaultStack(), 2, 6), 1);
        // gadget - cape colored, talisman          0-2(super rare)
        GADGET.addEntry(new WeightedRandomLootObject(null), 4);
        GADGET.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_TALISMAN_LEATHER.getDefaultStack()), 3);
        GADGET.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_TALISMAN_ZANITE.getDefaultStack()), 1);
    }

    public static final WeightedRandomBag<WeightedRandomLootObject> TREASURE = new WeightedRandomBag<>();

    static {
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.RECORD_MORNING.getDefaultStack()), 1);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_HAMMER_NOTCH.getDefaultStack()), 10);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_KNIFE_LIGHTNING.getDefaultStack(), 8, 16), 10);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SWORD_LIGHTNING.getDefaultStack()), 10);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_TALISMAN_REGEN.getDefaultStack()), 10);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_CAPE_SWET.getDefaultStack()), 10);
    }

    private static final RoomManager MANAGER = new RoomManager();
    private static final WeightedRandomBag<Supplier<? extends BaseBronzeRoom>> TREASURE_ROOMS;

    static {
        FabricLoader.getInstance()
            .getEntrypointContainers("aether", AetherPlugin.class)
            .forEach(plugin -> plugin.getEntrypoint().registerBronzeDungeonRoom(MANAGER));


        WeightedRandomBag<Supplier<? extends BaseBronzeRoom>> boss = new WeightedRandomBag<>();
        boss.addEntry(BossRoom::new, 1);
        TREASURE_ROOMS = new WeightedRandomBag<>();
        TREASURE_ROOMS.addEntry(TreasureRoom::new, 1);
        TREASURE_ROOMS.addEntry(JumpRoom::new, 1);
        TREASURE_ROOMS.addEntry(DisplayRoom::new, 1);

        WeightedRandomBag<Supplier<? extends BaseBronzeRoom>> trapRooms = new WeightedRandomBag<>();
        trapRooms.addEntry(SpikerRoom::new, 1);

        WeightedRandomBag<Supplier<? extends BaseBronzeRoom>> hallway = new WeightedRandomBag<>();
        hallway.addEntry(HallwayRoom::new, 10);
        hallway.addEntry(StairwellRoom::new, 2);
        hallway.addEntry(TallRoom::new, 5);

        MANAGER.addBag(TREASURE_ROOMS, 55);
        MANAGER.addBag(hallway, 20);
        MANAGER.addBag(trapRooms, 10);
        MANAGER.addBag(boss, 15);
    }

    public WorldFeatureAetherBronzeDungeon() {}

    @SuppressWarnings("java:S6541")
    @Override
    public boolean place(final World world, final Random random, final int x, final int y, final int z) {
        this.world = world;
        this.random = random;
        Set<BaseBronzeRoom> seenRooms = new HashSet<>();
        List<BaseBronzeRoom> availableRooms = new ArrayList<>();
        BaseBronzeRoom boss = new BossRoom();
        if (world.canBlockSeeTheSky(x, y, z) || !boss.place(world, random, x, y, z)) {
            return false;
        }
        float roomWeight = boss.getRoomWeight();
        int bossRoomCount = 1;
        seenRooms.add(boss);
        availableRooms.add(boss);
        BaseBronzeRoom currentRoom = null;
        while (!availableRooms.isEmpty() && MAX_WEIGHT > roomWeight) {
            if (currentRoom == null) {
                currentRoom = availableRooms.get(random.nextInt(availableRooms.size()));
            }

            List<Door> listDoor = currentRoom.getAvailableDoors();
            if (listDoor.isEmpty()) {
                availableRooms.remove(currentRoom);
                currentRoom = null;
                continue;
            }

            WeightedRandomBag<Door> bagDoors = this.makeRoomBag(listDoor);
            Door door = bagDoors.getRandom(random);
            BaseBronzeRoom nextRoom = MANAGER.getRoom(random).get();
            if (nextRoom instanceof BossRoom) {
                bossRoomCount++;
                if (bossRoomCount / roomWeight > 0.65F) {
                    nextRoom = TREASURE_ROOMS.getRandom(random).get();
                }
            }
            WorldFeaturePoint nextDoor = wfp(0, 0, 0).moveInDirection(door.getHeading()).multiply(TUNNEL_WIDTH).add(door.getP1());
            List<WorldFeaturePoint> listAnchor = nextRoom.getAnchors(nextDoor, door.getHeading());
            Collections.shuffle(listAnchor, random);
            for (WorldFeaturePoint anchor : listAnchor) {
                if (this.intercept(seenRooms, nextRoom, anchor)) {
                    currentRoom.markDoor(door, INTERCEPT);
                    currentRoom = null;
                    break;
                } else if (nextRoom.place(world, random, anchor.getX(), anchor.getY(), anchor.getZ())) {
                    WorldFeaturePoint topCorner;
                    WorldFeaturePoint bottomCorner;
                    bottomCorner = door.getP1().copy();
                    topCorner = getTopCornerPoint(seenRooms, nextRoom, nextDoor, door);

                    createTunnel(bottomCorner, topCorner, door.getHeading());
                    roomWeight += nextRoom.getRoomWeight();

                    seenRooms.add(nextRoom);
                    availableRooms.add(nextRoom);
                    currentRoom.markDoor(door, PLACED);
                    nextRoom.markDoor(nextRoom.getDoor(nextDoor), PLACED);

                    currentRoom = nextRoom;
                    break;
                }
            }
            if (currentRoom == null) continue;
            currentRoom.markDoor(door, NO_SPACE);
        }
        PriorityQueue<PriorityEntry<Door>> tunnels = new PriorityQueue<>();
        for (BaseBronzeRoom room : seenRooms) {
            List<Door> listDoor = room.getAdjustedDoors();
            if (room instanceof BossRoom && seenRooms.size() < 4) {
                Door d = listDoor.get(random.nextInt(listDoor.size()));
                listDoor = new ArrayList<>();
                listDoor.add(d);
                room.markDoor(d, PLACED);
            }
            if (room instanceof HallwayRoom) continue;
            for (Door door : listDoor) {
                if (door.getMark() != OPEN && door.getMark() != NO_SPACE && !(room instanceof BossRoom)) {
                    continue;
                }
                AetherMod.LOGGER.debug("door type:{}, door heading:{}", door.getMark(), door.getHeading());
                WorldFeaturePoint p1 = door.getP1().copy();
                WorldFeaturePoint p2 = door.getP2().copy();
                while (!this.breaksSurface(p1, p2)
                    && p1.distanceTo(door.getP1()) < TUNNEL_MAX_LENGTH
                    && y > 5 && y < world.getHeightBlocks()
                ) {
                    p1.moveInDirection(door.getHeading());
                    p2.moveInDirection(door.getHeading());
                }
                if (seenRooms.stream().anyMatch(r -> r.intercept(p1))) {
                    continue;
                }
                tunnels.add(pEntry(p1.distanceTo(door.getP1()) * bias(door.getHeading()), door(door.getHeading(), p1.moveInDirection(door.getHeading()), door.getP2().copy().moveInDirection(door.getHeading().getOpposite()))));
            }
        }
        if (tunnels.isEmpty()) {
            AetherMod.LOGGER.debug("No exit tunnels are generating for this bronze dungeon at {} {} {}", x, y, z);
            return true;
        }
        int tunnelAmount = tunnels.size() > 4 ? TUNNEL_COUNT : tunnels.size();
        for (int i = 0; i < tunnelAmount; i++) {
            PriorityEntry<Door> entry = tunnels.peek();
            tunnels.remove(entry);
            if (entry == null) continue;
            Door door = entry.getData();
            AetherMod.LOGGER.debug("Tunnel distance:{}, p1:{}, p2:{}, direction:{}.", entry.getWeight(), door.getP1(), door.getP2(), door.getHeading());
            createTunnel(door.getP1(), door.getP2(), door.getHeading());
        }
        return true;
    }

    public void createTunnel(WorldFeaturePoint bottomCorner, WorldFeaturePoint topCorner, Direction direction) {
        if (world.dimension.equals(AetherDimension.getAether())) {
            WorldFeaturePoint liningBottomCorner = bottomCorner.copy();
            WorldFeaturePoint liningTopCorner = topCorner.copy();
            adjustCornerForLining(direction, liningBottomCorner, liningTopCorner);
            placeWorldLining(world, drawVolumeWithPoint(this.random, holystone, liningBottomCorner, liningTopCorner, false));
        }
        drawVolumeWithPoint(0, 0, bottomCorner, topCorner, false).place(world);
    }

    public static void placeWorldLining(World world, WorldFeatureComponent lining) {
        for (WorldFeatureBlock block : lining.getBlockList()) {
            if (BaseBronzeRoom.roomCanReplace(world, block) && block.getY() > 5 && block.getY() <= world.getHeightBlocks()) {
                block.place(world);
            }
        }

    }

    public static void adjustCornerForLining(Direction direction, WorldFeaturePoint liningBottomCorner, WorldFeaturePoint liningTopCorner) {
        if (direction.isHorizontal()) {
            liningBottomCorner.moveInDirection(DOWN);
            liningTopCorner.moveInDirection(UP);
            if (direction == NORTH || direction == SOUTH) {
                liningBottomCorner.moveInDirection(WEST);
                liningTopCorner.moveInDirection(EAST);
            } else {
                liningBottomCorner.moveInDirection(NORTH);
                liningTopCorner.moveInDirection(SOUTH);
            }
        } else {
            if (direction == UP) {
                liningBottomCorner.moveInDirection(NORTH).moveInDirection(WEST);
                liningTopCorner.moveInDirection(SOUTH).moveInDirection(EAST);
            } else {
                liningBottomCorner.moveInDirection(SOUTH).moveInDirection(EAST);
                liningTopCorner.moveInDirection(NORTH).moveInDirection(WEST);
            }
        }
    }

    private static WorldFeaturePoint getTopCornerPoint(Set<BaseBronzeRoom> seenRooms, BaseBronzeRoom nextRoom, WorldFeaturePoint nextDoor, Door door) {
        WorldFeaturePoint point = nextRoom.getDoor(nextDoor).getP2().copy().moveInDirection(door.getHeading());
        if (seenRooms.size() == 1) {
            return point.moveInDirection(DOWN, 2);
        }
        return point;
    }

    private boolean intercept(Set<BaseBronzeRoom> seen, BaseBronzeRoom nextRoom, WorldFeaturePoint anchor) {
        for (BaseBronzeRoom room : seen) {
            if (room.intercept(anchor, nextRoom)) {
                return true;
            }
        }
        return false;
    }

    private boolean breaksSurface(WorldFeaturePoint p1, WorldFeaturePoint p2) {
        WorldFeatureComponent door = drawVolume(0, 0, p1, p2, false);
        int count = 0;
        for (WorldFeaturePoint point : door.getBlockList()) {
            Block<?> block = world.getBlock(point.getX(), point.getY(), point.getZ());
            int blockID = block == null ? 0 : block.id();
            Material blockMaterial = block == null ? Material.air : block.getMaterial();
            if (blockID == 0 || blockMaterial.isLiquid()) count++;
        }
        return count >= door.getBlockList().size();
    }

    private WeightedRandomBag<Door> makeRoomBag(List<Door> listDoor) {
        WeightedRandomBag<Door> bag = new WeightedRandomBag<>();
        for (Door door : listDoor) {
            if (door.getHeading() == UP || door.getHeading() == DOWN) {
                bag.addEntry(door, 1.0F);
            } else {
                bag.addEntry(door, 4.0F);
            }
        }
        return bag;
    }

    public static float bias(Direction direction) {
        if (direction == UP || direction == DOWN) {
            return 4.0F;
        } else {
            return 1.0F;
        }
    }


    public static class RoomManager {
        WeightedRandomBag<Object> bag;

        public RoomManager() {
            this.bag = new WeightedRandomBag<>();
        }

        @SuppressWarnings("UnusedReturnValue")
        public RoomManager addBag(WeightedRandomBag<?> rooms, float weight) {
            bag.addEntry(rooms, weight);
            return this;
        }

        @SuppressWarnings("unchecked")
        public Supplier<? extends BaseBronzeRoom> getRoom(Random random) {
            Object obj = bag.getRandom(random);

            while (obj instanceof WeightedRandomBag) {
                obj = ((WeightedRandomBag<?>) obj).getRandom(random);
            }

            if (!(obj instanceof Supplier)) {
                throw new IllegalStateException("Entry is not of type Supplier: " + obj);
            }

            Object result = ((Supplier<?>) obj).get();
            if (result instanceof BaseBronzeRoom) {
                return (Supplier<? extends BaseBronzeRoom>) obj;
            }
            throw new IllegalStateException("Entry is not instance of BaseBronzeRoom: " + obj);
        }
    }

    public static List<ItemStack> generateLoot(Random random) {
        List<ItemStack> loot = new ArrayList<>();
        int count = 5;
        for (int i = 0; i < count; i++) loot.add(JUNK.getRandom(random).getItemStack());
        // min 2 max 5
        count = random.nextInt(4) + 2;
        for (int i = 0; i < count; i++) loot.add(AMMO.getRandom(random).getItemStack());
        loot.add(FOOD.getRandom(random).getItemStack());
        loot.add(ARMOR.getRandom(random).getItemStack());
        loot.add(GADGET.getRandom(random).getItemStack());
        return loot;
    }
}
