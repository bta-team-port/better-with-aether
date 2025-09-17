package teamport.aether.world.generate.feature.dungeon;

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
import teamport.aether.helper.Pair;
import teamport.aether.helper.unboxed.PriorityEntry;
import teamport.aether.items.AetherItems;
import teamport.aether.world.generate.feature.BlockPallet;
import teamport.aether.world.generate.feature.components.WorldFeatureComponent;
import teamport.aether.world.generate.feature.components.WorldFeaturePoint;
import teamport.aether.world.generate.feature.components.dungeon.bronze.*;
import teamport.aether.world.generate.feature.components.dungeon.bronze.BaseBronzeRoom.Door;

import java.util.*;
import java.util.function.Supplier;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.helper.Pair.pair;
import static teamport.aether.helper.unboxed.PriorityEntry.pEntry;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.drawVolume;
import static teamport.aether.world.generate.feature.components.WorldFeaturePoint.wfp;

public class WorldFeatureAetherBronzeDungeon extends WorldFeature {
    public float MAX_WEIGHT = 40;
    public static final int TUNNEL_WIDTH = 6;
    public static final int TUNNEL_COUNT = 4;
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
//        lockedCarvedHolystone.addEntry(AetherBlocks.CARVED_STONE_TR.id(), 10);

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
    public static WeightedRandomBag<Supplier<? extends BaseBronzeRoom>> treasureRooms;

    static {
        FabricLoader.getInstance()
                .getEntrypointContainers("aether", AetherPlugin.class)
                .forEach(plugin -> plugin.getEntrypoint().registerBronzeDungeonRoom(manager));


        WeightedRandomBag<Supplier<? extends BaseBronzeRoom>> boss = new WeightedRandomBag<>();
        boss.addEntry(BossRoom::new, 1);
        treasureRooms = new WeightedRandomBag<>();
        treasureRooms.addEntry(TreasureRoom::new, 1);
        treasureRooms.addEntry(JumpRoom::new, 1);
        treasureRooms.addEntry(DisplayRoom::new, 1);

        WeightedRandomBag<Supplier<? extends BaseBronzeRoom>> trapRooms = new WeightedRandomBag<>();
        trapRooms.addEntry(SpikerRoom::new, 1);

        WeightedRandomBag<Supplier<? extends BaseBronzeRoom>> hallway = new WeightedRandomBag<>();
        hallway.addEntry(HallwayRoom::new, 50);
        hallway.addEntry(TallRoom::new, 5);
        hallway.addEntry(StairwellRoom::new, 10);

        manager.addBag(treasureRooms, 50);
        manager.addBag(hallway, 25);
        manager.addBag(trapRooms, 20);
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
        float roomCount = boss.roomWeight;
        int bossRoomCount = 1;
        seenRooms.add(boss);
        avaibleRooms.add(boss);
        BaseBronzeRoom currentRoom = null;
        while (!avaibleRooms.isEmpty() && MAX_WEIGHT > roomCount) {
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
            currentRoom.markDoor(door);

            boolean managedToplace = false;
            for (WorldFeaturePoint anchor : listAnchor) {
                if (this.intercept(seenRooms, nextRoom, anchor)) {
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
                    roomCount += nextRoom.roomWeight;
                    seenRooms.add(nextRoom);
                    avaibleRooms.add(nextRoom);
                    nextRoom.markDoor(nextRoom.getDoor(nextDoor));
                    currentRoom = nextRoom;
                    managedToplace = true;
                    break;
                }
            }

            // Okay so, if you failed to place the room it could be either of two things.
            //
            // A) the room is too large or something;
            // B) the position is plain stupid, and it won't place.
            // ----------------------------------------------------
            // While you could fix A by attempting to reroll, you just gonna reroll forever in case of B.
            // Therefore, just mark the door and go to another room!

            if (!managedToplace) {
                currentRoom.markDoor(door);
                currentRoom = null;
            }
        }
        PriorityQueue<PriorityEntry<Pair<WorldFeaturePoint, WorldFeaturePoint>>> tunnels = new PriorityQueue<>();
        for (BaseBronzeRoom room : avaibleRooms) {
            for (Door door : room.getAvailableDoors()) {
                WorldFeaturePoint p1 = door.p1.copy();
                WorldFeaturePoint p2 = door.p2.copy();
                while (!this.breaksSurface(p1, p2) && p1.distanceTo(door.p1) < 100) {
                    p1.moveInDirection(door.heading);
                    p2.moveInDirection(door.heading);
                }
                if(seenRooms.stream().anyMatch(r -> r.intercept(p1))){
                    continue;
                }
                tunnels.add(pEntry(p1.distanceTo(door.p1) * bias(door.heading), pair(p1.moveInDirection(door.heading), door.p2.copy().moveInDirection(door.heading.getOpposite()))));
            }
        }
        if(tunnels.size() <= 3){
            AetherMod.LOGGER.info("No Tunnels are generating.");
            return true;
        }
        for (int i = 0; i < TUNNEL_COUNT; i++) {
            PriorityEntry<Pair<WorldFeaturePoint, WorldFeaturePoint>> entry = tunnels.peek();
            AetherMod.LOGGER.info("Tunnel distance:{}, p1:{}, p2:{}", entry.getWeight(), entry.getData().first, entry.getData().second);
            tunnels.remove(entry);
            Pair<WorldFeaturePoint, WorldFeaturePoint> door = entry.getData();
            drawVolume(AetherBlocks.AERCLOUD_WHITE.id(), 0, door.first, door.second, true).place(world);
        }

        return true;
    }

    private boolean intercept(Set<BaseBronzeRoom> seen, BaseBronzeRoom nextRoom, WorldFeaturePoint anchor){
        for(BaseBronzeRoom room: seen){
            if(room.intercept(anchor, nextRoom)){
                return true;
            }
        }
        return false;
    }

    private boolean breaksSurface(WorldFeaturePoint p1, WorldFeaturePoint p2) {
        WorldFeatureComponent door = drawVolume(0, 0, p1, p2, false);
        int count = 0;
        for (WorldFeaturePoint point : door.blockList) {
            Block<?> block = world.getBlock(point.x, point.y, point.z);
            int blockID = block == null ? 0 : block.id();
            Material blockMaterial = block == null ? Material.air : block.getMaterial();
            if (blockID == 0 || blockMaterial.isLiquid()) count++;
        }
        return count >= door.blockList.size();
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
