package teamport.aether.world.generate.feature.dungeon;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.compat.AetherPlugin;
import teamport.aether.helper.AetherMathHelper;
import teamport.aether.items.AetherItems;
import teamport.aether.world.generate.feature.BlockPallet;
import teamport.aether.world.generate.feature.components.WorldFeaturePoint;
import teamport.aether.world.generate.feature.components.dungeon.bronze.*;
import teamport.aether.world.generate.feature.components.dungeon.bronze.BaseBronzeRoom.Door;

import java.util.*;
import java.util.function.Supplier;

import static net.minecraft.core.util.helper.Direction.DOWN;
import static net.minecraft.core.util.helper.Direction.UP;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.drawVolume;
import static teamport.aether.world.generate.feature.components.WorldFeaturePoint.wfp;

public class WorldFeatureAetherBronzeDungeon extends WorldFeature {
    public int ROOM_COUNT_MAX = 40;
    public static final int TUNNEL_WIDTH = 6;
    public World world;
    public Random random;

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
        lockedCarvedHolystone.addEntry(AetherBlocks.CARVED_STONE_TRAPPED_LOCKED.id(), 10);

        holystone.addEntry(AetherBlocks.COBBLE_HOLYSTONE.id(), 90);
        holystone.addEntry(AetherBlocks.COBBLE_HOLYSTONE_MOSSY.id(), 10);

        chestsOrMimic.addEntry(0, 1);
        chestsOrMimic.addEntry(AetherBlocks.CHEST_MIMIC.id(), 1);
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
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_KNIFE_LIGHTNING.getDefaultStack(), 1, 16), 10);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SWORD_LIGHTNING.getDefaultStack()), 10);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_TALISMAN_REGEN.getDefaultStack()), 10);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_CAPE_SWET.getDefaultStack()), 10);
    }

    public static RoomManager manager = new RoomManager();
    public static WeightedRandomBag<Supplier<? extends BaseBronzeRoom>> treasureRooms = new WeightedRandomBag<>();
    static {
        FabricLoader.getInstance()
                .getEntrypointContainers("aether", AetherPlugin.class)
                .forEach(plugin -> plugin.getEntrypoint().registerBronzeDungeonRoom(manager));

        treasureRooms.addEntry(TreasureRoom::new, 50F);
        treasureRooms.addEntry(IceRoom::new, 25F);
        treasureRooms.addEntry(HallwayRoom::new, 50F);
        treasureRooms.addEntry(TallRoom::new, 5F);
        WeightedRandomBag<Supplier<? extends BaseBronzeRoom>> trapRooms = new WeightedRandomBag<>();
        trapRooms.addEntry(SpikerRoom::new, 1);
        WeightedRandomBag<Supplier<? extends BaseBronzeRoom>> boss = new WeightedRandomBag<>();
        boss.addEntry(BossRoom::new, 1);
        manager.addBag(treasureRooms, 60);
        manager.addBag(trapRooms, 35);
        manager.addBag(boss, 5);
    }

    public WorldFeatureAetherBronzeDungeon() {
    }

    public static WorldFeatureAetherBronzeDungeon bronzeDungeon(Random random) {
        return new WorldFeatureAetherBronzeDungeon();
    }

    @Override
    public boolean place(final World world, final Random random, final int x, final int y, final int z) {
        this.world = world;
        this.random = random;
        Set<BaseBronzeRoom> seenRooms = new HashSet<>();
        List<BaseBronzeRoom> avaibleRooms = new ArrayList<>();
        BaseBronzeRoom boss = new BossRoom();
        if (world.canBlockSeeTheSky(x, y, z) || !boss.place(world, random, x, y, z)) {
            return false;
        }
        int roomCount = boss.roomCount;
        int bossRoomCount = 1;
        seenRooms.add(boss);
        avaibleRooms.add(boss);
        BaseBronzeRoom currentRoom = null;
        while (!avaibleRooms.isEmpty() && ROOM_COUNT_MAX >= roomCount) {
            if (currentRoom == null) {
                currentRoom = avaibleRooms.get(random.nextInt(avaibleRooms.size()));
            }

            List<Door> listDoor = currentRoom.getAvailableDoors();
            if (listDoor.isEmpty()) {
                avaibleRooms.remove(currentRoom);
                currentRoom = null;
                continue;
            }

            WeightedRandomBag<Door> bagDoors = this.makeRoomBag(listDoor);
            Door door = bagDoors.getRandom(random);
            BaseBronzeRoom nextRoom = manager.getRoom(random).get();
            if (nextRoom instanceof BossRoom) {
                bossRoomCount++;
                if ((float) bossRoomCount / roomCount > 0.65F) {
                    nextRoom = treasureRooms.getRandom(random).get();
                }
            }
            WorldFeaturePoint nextDoor = wfp(0, 0, 0).moveInDirection(door.heading).multiply(TUNNEL_WIDTH).add(door.p1);
            List<WorldFeaturePoint> listAnchor = nextRoom.getAchors(nextDoor, door.heading);
            for (WorldFeaturePoint anchor : listAnchor) {
                if (seenRooms.stream().anyMatch(room -> room.intersect(anchor))) {
                    currentRoom.markDoor(door);
                    break;
                } else if (nextRoom.place(world, random, anchor.x, anchor.y, anchor.z)) {
                    WorldFeaturePoint topCorner, bottomCorner;
                    // TODO remove this branch at some point to make the code more clean
                    if (seenRooms.size() == 1) {
                        topCorner = nextRoom.getDoor(nextDoor).p1.copy().moveInDirection(door.heading);
                        bottomCorner = door.p2.copy().moveInDirection(door.heading.getOpposite());
                    } else {
                        topCorner = nextRoom.getDoor(nextDoor).p2.copy().moveInDirection(door.heading);
                        bottomCorner = door.p1.copy().moveInDirection(door.heading.getOpposite());
                    }
                    drawVolume(0, 0, topCorner, bottomCorner, true).place(world);
                    roomCount += nextRoom.roomCount;
                    seenRooms.add(nextRoom);
                    avaibleRooms.add(nextRoom);
                    currentRoom.markDoor(door);
                    nextRoom.markDoor(nextRoom.getDoor(nextDoor));
                    currentRoom = nextRoom;
                    break;
                }
            }
        }
        return true;
    }

    private WeightedRandomBag<Door> makeRoomBag(List<Door> listDoor) {
        WeightedRandomBag<Door> bag = new WeightedRandomBag<>();
        for (Door door : listDoor) {
            if (door.heading == UP || door.heading == DOWN) {
                bag.addEntry(door, 1.0F);
            } else {
                bag.addEntry(door, 4.0F);
            }
        }
        return bag;
    }
    public static class RoomManager {
        WeightedRandomBag<Object> bag;

        public RoomManager() {
            this.bag = new WeightedRandomBag<>();
        }

        public RoomManager addBag(WeightedRandomBag<?> rooms, float weight) {
            bag.addEntry(rooms, weight);
            return this;
        }

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

}
