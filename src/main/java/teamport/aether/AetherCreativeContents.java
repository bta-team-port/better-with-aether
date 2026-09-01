package teamport.aether;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.IPainted;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemBucket;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DyeColor;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlocks;
import teamport.aether.item.AetherItems;

import java.util.List;

public final class AetherCreativeContents {
    private static final DyeColor[] RAINBOW_ORDER;

    static {
        RAINBOW_ORDER = new DyeColor[]{
            DyeColor.RED,
            DyeColor.ORANGE,
            DyeColor.YELLOW,
            DyeColor.LIME,
            DyeColor.GREEN,
            DyeColor.CYAN,
            DyeColor.LIGHT_BLUE,
            DyeColor.BLUE,
            DyeColor.PURPLE,
            DyeColor.MAGENTA,
            DyeColor.PINK,
            DyeColor.BROWN,
            DyeColor.WHITE,
            DyeColor.SILVER,
            DyeColor.GRAY,
            DyeColor.BLACK};
    }

    public AetherCreativeContents() {
    }

    public static void populate(List<ItemStack> list) {
        AetherCreativeContents.addNaturalTypes(list);
        AetherCreativeContents.addStoneTypes(list);
        AetherCreativeContents.addWoodTypes(list);
        AetherCreativeContents.addPaintedTypes(list);
        AetherCreativeContents.addLogTypes(list);
        AetherCreativeContents.addLeafTypes(list);
        AetherCreativeContents.addSaplingTypes(list);
        AetherCreativeContents.addOrganicTypes(list);
        AetherCreativeContents.addWorkstationsAndGlass(list);
        AetherCreativeContents.addRedstoneTypes(list);
        AetherCreativeContents.addOreTypes(list);
        AetherCreativeContents.addStorageTypes(list);
        AetherCreativeContents.addDungeonBlocks(list);
        AetherCreativeContents.addPlaceables(list);
        AetherCreativeContents.addTools(list);
        AetherCreativeContents.addArmor(list);
        AetherCreativeContents.addAccessories(list);
        AetherCreativeContents.addMiscTools(list);
        AetherCreativeContents.addFood(list);
        AetherCreativeContents.addDyes(list);
        AetherCreativeContents.addOreProducts(list);
        AetherCreativeContents.addBasics(list);
        AetherCreativeContents.addMobDrops(list);
        AetherCreativeContents.addMisc(list);
        AetherCreativeContents.addRecords(list);
    }

    private static void addTools(@NonNull List<ItemStack> list) {
        list.add(new ItemStack(Items.TOOL_SHOVEL_WOOD));
        list.add(new ItemStack(Items.TOOL_PICKAXE_WOOD));
        list.add(new ItemStack(Items.TOOL_AXE_WOOD));
        list.add(new ItemStack(Items.TOOL_HOE_WOOD));
        list.add(new ItemStack(Items.TOOL_SWORD_WOOD));

        list.add(new ItemStack(Items.TOOL_SHOVEL_STONE));
        list.add(new ItemStack(Items.TOOL_PICKAXE_STONE));
        list.add(new ItemStack(Items.TOOL_AXE_STONE));
        list.add(new ItemStack(Items.TOOL_HOE_STONE));
        list.add(new ItemStack(Items.TOOL_SWORD_STONE));

        list.add(new ItemStack(Items.TOOL_SHOVEL_IRON));
        list.add(new ItemStack(Items.TOOL_PICKAXE_IRON));
        list.add(new ItemStack(Items.TOOL_AXE_IRON));
        list.add(new ItemStack(Items.TOOL_HOE_IRON));
        list.add(new ItemStack(Items.TOOL_SWORD_IRON));

        list.add(new ItemStack(Items.TOOL_SHOVEL_GOLD));
        list.add(new ItemStack(Items.TOOL_PICKAXE_GOLD));
        list.add(new ItemStack(Items.TOOL_AXE_GOLD));
        list.add(new ItemStack(Items.TOOL_HOE_GOLD));
        list.add(new ItemStack(Items.TOOL_SWORD_GOLD));

        list.add(new ItemStack(Items.TOOL_SHOVEL_DIAMOND));
        list.add(new ItemStack(Items.TOOL_PICKAXE_DIAMOND));
        list.add(new ItemStack(Items.TOOL_AXE_DIAMOND));
        list.add(new ItemStack(Items.TOOL_HOE_DIAMOND));
        list.add(new ItemStack(Items.TOOL_SWORD_DIAMOND));

        list.add(new ItemStack(Items.TOOL_SHOVEL_STEEL));
        list.add(new ItemStack(Items.TOOL_PICKAXE_STEEL));
        list.add(new ItemStack(Items.TOOL_AXE_STEEL));
        list.add(new ItemStack(Items.TOOL_HOE_STEEL));
        list.add(new ItemStack(Items.TOOL_SWORD_STEEL));

        list.add(new ItemStack(AetherItems.TOOL_SHOVEL_SKYROOT));
        list.add(new ItemStack(AetherItems.TOOL_PICKAXE_SKYROOT));
        list.add(new ItemStack(AetherItems.TOOL_AXE_SKYROOT));
        list.add(new ItemStack(AetherItems.TOOL_SWORD_SKYROOT));

        list.add(new ItemStack(AetherItems.TOOL_SHOVEL_HOLYSTONE));
        list.add(new ItemStack(AetherItems.TOOL_PICKAXE_HOLYSTONE));
        list.add(new ItemStack(AetherItems.TOOL_AXE_HOLYSTONE));
        list.add(new ItemStack(AetherItems.TOOL_SWORD_HOLYSTONE));

        list.add(new ItemStack(AetherItems.TOOL_SHOVEL_ZANITE));
        list.add(new ItemStack(AetherItems.TOOL_PICKAXE_ZANITE));
        list.add(new ItemStack(AetherItems.TOOL_AXE_ZANITE));
        list.add(new ItemStack(AetherItems.TOOL_SWORD_ZANITE));

        list.add(new ItemStack(AetherItems.TOOL_SHOVEL_GRAVITITE));
        list.add(new ItemStack(AetherItems.TOOL_PICKAXE_GRAVITITE));
        list.add(new ItemStack(AetherItems.TOOL_AXE_GRAVITITE));
        list.add(new ItemStack(AetherItems.TOOL_SWORD_GRAVITITE));

        list.add(new ItemStack(AetherItems.TOOL_SHOVEL_VALKYRIE));
        list.add(new ItemStack(AetherItems.TOOL_PICKAXE_VALKYRIE));
        list.add(new ItemStack(AetherItems.TOOL_AXE_VALKYRIE));
        list.add(new ItemStack(AetherItems.TOOL_SWORD_VALKYRIE));
    }

    private static void addMiscTools(@NonNull List<ItemStack> list) {
        list.add(new ItemStack(Items.TOOL_FIRESTRIKER_IRON));
        list.add(new ItemStack(Items.TOOL_FIRESTRIKER_STEEL));

        list.add(new ItemStack(Items.TOOL_SHEARS));
        list.add(new ItemStack(Items.TOOL_SHEARS_STEEL));

        addBucketVariants(list, Items.BUCKET_IRON);
        addBucketVariants(list, Items.BUCKET_STEEL);
        list.add(new ItemStack(AetherItems.BUCKET_SKYROOT));
        list.add(new ItemStack(AetherItems.BUCKET_SKYROOT_WATER));
        list.add(new ItemStack(AetherItems.BUCKET_SKYROOT_MILK));
        list.add(new ItemStack(AetherItems.BUCKET_SKYROOT_ICECREAM));
        list.add(new ItemStack(AetherItems.BUCKET_SKYROOT_POISON));
        list.add(new ItemStack(AetherItems.BUCKET_SKYROOT_REMEDY));

        list.add(new ItemStack(AetherItems.PARACHUTE_CLOUD));
        list.add(new ItemStack(AetherItems.PARACHUTE_CLOUD_GOLD));

        list.add(new ItemStack(Items.PAINTBRUSH));

        list.add(new ItemStack(Items.TOOL_FISHINGROD));

        list.add(new ItemStack(Items.LABEL));

        list.add(new ItemStack(Items.TOOL_COMPASS));
        list.add(new ItemStack(Items.TOOL_CLOCK));
        list.add(new ItemStack(Items.TOOL_CALENDAR));
        list.add(new ItemStack(Items.MAP));
        list.add(new ItemStack(AetherItems.TOOL_DUNGEON_COMPASS));

        list.add(new ItemStack(AetherItems.TOOL_SWORD_PIG));
        list.add(new ItemStack(AetherItems.TOOL_SWORD_VAMPIRE));

        list.add(new ItemStack(AetherItems.TOOL_SWORD_FLAME));
        list.add(new ItemStack(AetherItems.TOOL_SWORD_HOLY));
        list.add(new ItemStack(AetherItems.TOOL_SWORD_LIGHTNING));

        list.add(new ItemStack(AetherItems.TOOL_KNIFE_LIGHTNING));

        list.add(new ItemStack(AetherItems.TOOL_STAFF_NATURE));
        list.add(new ItemStack(AetherItems.TOOL_STAFF_CLOUD));

        list.add(new ItemStack(AetherItems.TOOL_HAMMER_NOTCH));

        list.add(new ItemStack(Items.TOOL_BOW));
        list.add(new ItemStack(AetherItems.TOOL_BOW_PHOENIX));
        list.add(new ItemStack(Items.AMMO_ARROW));
        list.add(new ItemStack(Items.AMMO_ARROW_GOLD));
        list.add(new ItemStack(Items.AMMO_ARROW_FLAMING));

        list.add(new ItemStack(Items.HANDCANNON_UNLOADED));
        list.add(new ItemStack(Items.AMMO_CHARGE_EXPLOSIVE));
//        list.add(new ItemStack(Items.AMMO_FIREBALL));
//        list.add(new ItemStack(AetherItems.AMMO_WINDBALL));
//        list.add(new ItemStack(AetherItems.PROJECTILE_FIRE));
//        list.add(new ItemStack(AetherItems.PROJECTILE_ICE));
//        list.add(new ItemStack(AetherItems.PROJECTILE_LIGHTNING));

        list.add(new ItemStack(AetherItems.TOOL_SHOOTER));
        list.add(new ItemStack(AetherItems.AMMO_DART_GOLDEN));
        list.add(new ItemStack(AetherItems.AMMO_DART_POISON));
        list.add(new ItemStack(AetherItems.AMMO_DART_ENCHANTED));

        list.add(new ItemStack(Items.WAND_MONSTER_SPAWNER));
        list.add(new ItemStack(Items.WAND_NBT));
    }

    private static void addStoneTypes(List<ItemStack> list) {
        addBlock(list, Blocks.STONE);
        addBlock(list, Blocks.MOSS_STONE);
        addBlock(list, Blocks.COBBLE_STONE);
        addBlock(list, Blocks.STAIRS_COBBLE_STONE);
        addBlock(list, Blocks.SLAB_COBBLE_STONE);
        addBlock(list, Blocks.COBBLE_STONE_MOSSY);
        addBlock(list, Blocks.BRICK_STONE);
        addBlock(list, Blocks.STAIRS_BRICK_STONE);
        addBlock(list, Blocks.SLAB_BRICK_STONE);
        addBlock(list, Blocks.STONE_POLISHED);
        addBlock(list, Blocks.STONE_CARVED);
        addBlock(list, Blocks.SLAB_STONE_POLISHED);
        addBlock(list, Blocks.BRICK_STONE_POLISHED);
        addBlock(list, Blocks.STAIRS_BRICK_STONE_POLISHED);
        addBlock(list, Blocks.SLAB_BRICK_STONE_POLISHED);
        addBlock(list, Blocks.BRICK_STONE_POLISHED_MOSSY);
        list.add(new ItemStack(Items.STATUE_STONE));
        addBlock(list, Blocks.BUTTON_STONE);
        addBlock(list, Blocks.PRESSURE_PLATE_STONE);
        addBlock(list, Blocks.PRESSURE_PLATE_COBBLE_STONE);

        addBlock(list, Blocks.BASALT);
        addBlock(list, Blocks.MOSS_BASALT);
        addBlock(list, Blocks.COBBLE_BASALT);
        addBlock(list, Blocks.STAIRS_COBBLE_BASALT);
        addBlock(list, Blocks.SLAB_COBBLE_BASALT);
        addBlock(list, Blocks.COBBLE_BASALT_MOSSY);
        addBlock(list, Blocks.BRICK_BASALT);
        addBlock(list, Blocks.STAIRS_BRICK_BASALT);
        addBlock(list, Blocks.SLAB_BRICK_BASALT);
        addBlock(list, Blocks.BASALT_POLISHED);
        addBlock(list, Blocks.BASALT_CARVED);
        addBlock(list, Blocks.SLAB_BASALT_POLISHED);
        list.add(new ItemStack(Items.STATUE_BASALT));
        addBlock(list, Blocks.BUTTON_BASALT);
        addBlock(list, Blocks.PRESSURE_PLATE_BASALT);
        addBlock(list, Blocks.PRESSURE_PLATE_COBBLE_BASALT);

        addBlock(list, Blocks.LIMESTONE);
        addBlock(list, Blocks.MOSS_LIMESTONE);
        addBlock(list, Blocks.COBBLE_LIMESTONE);
        addBlock(list, Blocks.STAIRS_COBBLE_LIMESTONE);
        addBlock(list, Blocks.SLAB_COBBLE_LIMESTONE);
        addBlock(list, Blocks.COBBLE_LIMESTONE_MOSSY);
        addBlock(list, Blocks.BRICK_LIMESTONE);
        addBlock(list, Blocks.STAIRS_BRICK_LIMESTONE);
        addBlock(list, Blocks.SLAB_BRICK_LIMESTONE);
        addBlock(list, Blocks.LIMESTONE_POLISHED);
        addBlock(list, Blocks.LIMESTONE_CARVED);
        addBlock(list, Blocks.SLAB_LIMESTONE_POLISHED);
        list.add(new ItemStack(Items.STATUE_LIMESTONE));
        addBlock(list, Blocks.BUTTON_LIMESTONE);
        addBlock(list, Blocks.PRESSURE_PLATE_LIMESTONE);
        addBlock(list, Blocks.PRESSURE_PLATE_COBBLE_LIMESTONE);

        addBlock(list, Blocks.GRANITE);
        addBlock(list, Blocks.MOSS_GRANITE);
        addBlock(list, Blocks.COBBLE_GRANITE);
        addBlock(list, Blocks.STAIRS_COBBLE_GRANITE);
        addBlock(list, Blocks.SLAB_COBBLE_GRANITE);
        addBlock(list, Blocks.COBBLE_GRANITE_MOSSY);
        addBlock(list, Blocks.BRICK_GRANITE);
        addBlock(list, Blocks.STAIRS_BRICK_GRANITE);
        addBlock(list, Blocks.SLAB_BRICK_GRANITE);
        addBlock(list, Blocks.GRANITE_POLISHED);
        addBlock(list, Blocks.GRANITE_CARVED);
        addBlock(list, Blocks.SLAB_GRANITE_POLISHED);
        list.add(new ItemStack(Items.STATUE_GRANITE));
        addBlock(list, Blocks.BUTTON_GRANITE);
        addBlock(list, Blocks.PRESSURE_PLATE_GRANITE);
        addBlock(list, Blocks.PRESSURE_PLATE_COBBLE_GRANITE);

        addBlock(list, Blocks.PERMAFROST);
        addBlock(list, Blocks.COBBLE_PERMAFROST);
        addBlock(list, Blocks.STAIRS_COBBLE_PERMAFROST);
        addBlock(list, Blocks.SLAB_COBBLE_PERMAFROST);
        addBlock(list, Blocks.BRICK_PERMAFROST);
        addBlock(list, Blocks.STAIRS_BRICK_PERMAFROST);
        addBlock(list, Blocks.SLAB_BRICK_PERMAFROST);
        addBlock(list, Blocks.PERMAFROST_POLISHED);
        addBlock(list, Blocks.PERMAFROST_CARVED);
        addBlock(list, Blocks.SLAB_PERMAFROST_POLISHED);
        list.add(new ItemStack(Items.STATUE_PERMAFROST));
        addBlock(list, Blocks.BUTTON_PERMAFROST);
        addBlock(list, Blocks.PRESSURE_PLATE_PERMAFROST);
        addBlock(list, Blocks.PRESSURE_PLATE_COBBLE_PERMAFROST);

        addBlock(list, Blocks.NETHERRACK);
        addBlock(list, Blocks.COBBLE_NETHERRACK);
        addBlock(list, Blocks.STAIRS_COBBLE_NETHERRACK);
        addBlock(list, Blocks.SLAB_COBBLE_NETHERRACK);
        addBlock(list, Blocks.COBBLE_NETHERRACK_CRYSTALLINE);
        addBlock(list, Blocks.BRICK_NETHERRACK);
        addBlock(list, Blocks.STAIRS_BRICK_NETHERRACK);
        addBlock(list, Blocks.SLAB_BRICK_NETHERRACK);
        addBlock(list, Blocks.NETHERRACK_POLISHED);
        addBlock(list, Blocks.NETHERRACK_CARVED);
        addBlock(list, Blocks.SLAB_NETHERRACK_POLISHED);
        list.add(new ItemStack(Items.STATUE_NETHERRACK));
        addBlock(list, Blocks.BUTTON_NETHERRACK);
        addBlock(list, Blocks.PRESSURE_PLATE_NETHERRACK);
        addBlock(list, Blocks.PRESSURE_PLATE_COBBLE_NETHERRACK);

        addBlock(list, Blocks.GLOOMSTONE);
        addBlock(list, Blocks.COBBLE_GLOOMSTONE);
        addBlock(list, Blocks.STAIRS_COBBLE_GLOOMSTONE);
        addBlock(list, Blocks.SLAB_COBBLE_GLOOMSTONE);
        addBlock(list, Blocks.BRICK_GLOOMSTONE);
        addBlock(list, Blocks.STAIRS_BRICK_GLOOMSTONE);
        addBlock(list, Blocks.SLAB_BRICK_GLOOMSTONE);
        addBlock(list, Blocks.GLOOMSTONE_POLISHED);
        addBlock(list, Blocks.GLOOMSTONE_CARVED);
        addBlock(list, Blocks.SLAB_GLOOMSTONE_POLISHED);
        list.add(new ItemStack(Items.STATUE_GLOOMSTONE));
        addBlock(list, Blocks.BUTTON_GLOOMSTONE);
        addBlock(list, Blocks.PRESSURE_PLATE_GLOOMSTONE);
        addBlock(list, Blocks.PRESSURE_PLATE_COBBLE_GLOOMSTONE);

        addBlock(list, Blocks.SLATE);
        addBlock(list, Blocks.LAYER_SLATE);
        addBlock(list, Blocks.BRICK_SLATE);
        addBlock(list, Blocks.STAIRS_BRICK_SLATE);
        addBlock(list, Blocks.SLAB_BRICK_SLATE);
        addBlock(list, Blocks.SLATE_POLISHED);
        addBlock(list, Blocks.SLATE_CARVED);
        addBlock(list, Blocks.SLAB_SLATE_POLISHED);
        list.add(new ItemStack(Items.STATUE_SLATE));
        addBlock(list, Blocks.BUTTON_SLATE);
        addBlock(list, Blocks.PRESSURE_PLATE_SLATE);

        addBlock(list, Blocks.SAND);
        addBlock(list, Blocks.SANDSTONE);
        addBlock(list, Blocks.STAIRS_SANDSTONE);
        addBlock(list, Blocks.SLAB_SANDSTONE);
        addBlock(list, Blocks.BRICK_SANDSTONE);
        addBlock(list, Blocks.STAIRS_BRICK_SANDSTONE);
        addBlock(list, Blocks.SLAB_BRICK_SANDSTONE);
        addBlock(list, Blocks.BUTTON_SANDSTONE);
        addBlock(list, Blocks.PRESSURE_PLATE_SANDSTONE);

        addBlock(list, Blocks.BRIMSAND);
        addBlock(list, Blocks.BRIMSTONE);
        addBlock(list, Blocks.STAIRS_BRIMSTONE);
        addBlock(list, Blocks.SLAB_BRIMSTONE);
        addBlock(list, Blocks.BRICK_BRIMSTONE);
        addBlock(list, Blocks.STAIRS_BRICK_BRIMSTONE);
        addBlock(list, Blocks.SLAB_BRICK_BRIMSTONE);
        addBlock(list, Blocks.BUTTON_BRIMSTONE);
        addBlock(list, Blocks.PRESSURE_PLATE_BRIMSTONE);

        addBlock(list, Blocks.MARBLE);
        addBlock(list, Blocks.BRICK_MARBLE);
        addBlock(list, Blocks.STAIRS_BRICK_MARBLE);
        addBlock(list, Blocks.SLAB_BRICK_MARBLE);
        addBlock(list, Blocks.PILLAR_MARBLE);
        addBlock(list, Blocks.CAPSTONE_MARBLE);
        addBlock(list, Blocks.SLAB_CAPSTONE_MARBLE);
        list.add(new ItemStack(Items.STATUE_MARBLE));
        list.add(new ItemStack(Items.STATUE_PIGMAN));
        addBlock(list, Blocks.BUTTON_MARBLE);
        addBlock(list, Blocks.PRESSURE_PLATE_MARBLE);

        addBlock(list, AetherBlocks.HOLYSTONE);
        addBlock(list, AetherBlocks.HOLYSTONE_MOSSY);
        addBlock(list, AetherBlocks.COBBLE_HOLYSTONE);
        addBlock(list, AetherBlocks.STAIRS_COBBLE_HOLYSTONE);
        addBlock(list, AetherBlocks.SLAB_COBBLE_HOLYSTONE);
        addBlock(list, AetherBlocks.COBBLE_HOLYSTONE_MOSSY);
        addBlock(list, AetherBlocks.BRICK_HOLYSTONE);
        addBlock(list, AetherBlocks.STAIRS_BRICK_HOLYSTONE);
        addBlock(list, AetherBlocks.SLAB_BRICK_HOLYSTONE);
        addBlock(list, AetherBlocks.HOLYSTONE_POLISHED);
        addBlock(list, AetherBlocks.HOLYSTONE_CARVED);
        addBlock(list, AetherBlocks.SLAB_HOLYSTONE_POLISHED);
        list.add(new ItemStack(AetherItems.STATUE_HOLYSTONE));
        addBlock(list, AetherBlocks.BUTTON_HOLYSTONE);
        addBlock(list, AetherBlocks.PRESSURE_PLATE_HOLYSTONE);
        addBlock(list, AetherBlocks.PRESSURE_PLATE_COBBLE_HOLYSTONE);

    }

    private static void addLogTypes(List<ItemStack> list) {
        addBlock(list, Blocks.LOG_OAK);
        addBlock(list, Blocks.LOG_OAK_MOSSY);
        addBlock(list, Blocks.LOG_PINE);
        addBlock(list, Blocks.LOG_BIRCH);
        addBlock(list, Blocks.LOG_CHERRY);
        addBlock(list, Blocks.LOG_EUCALYPTUS);
        addBlock(list, Blocks.LOG_THORN);
        addBlock(list, Blocks.LOG_PALM);
        addBlock(list, Blocks.LOG_SCORCHED);
        addBlock(list, AetherBlocks.LOG_SKYROOT);
        addBlock(list, AetherBlocks.LOG_OAK_GOLDEN);
    }

    private static void addLeafTypes(List<ItemStack> list) {
        addBlock(list, Blocks.LEAVES_OAK);
        addBlock(list, Blocks.LAYER_LEAVES_OAK);
        addBlock(list, Blocks.LEAVES_OAK_RETRO);
        addBlock(list, Blocks.LEAVES_PINE);
        addBlock(list, Blocks.LEAVES_BIRCH);
        addBlock(list, Blocks.LEAVES_CHERRY);
        addBlock(list, Blocks.LEAVES_CHERRY_FLOWERING);
        addBlock(list, Blocks.LEAVES_EUCALYPTUS);
        addBlock(list, Blocks.LEAVES_THORN);
        addBlock(list, Blocks.LEAVES_PALM);
        addBlock(list, Blocks.LEAVES_SHRUB);
        addBlock(list, Blocks.LEAVES_CACAO);
        addBlock(list, AetherBlocks.LEAVES_SKYROOT);
        addBlock(list, AetherBlocks.LEAVES_OAK_GOLDEN);
    }

    private static void addSaplingTypes(List<ItemStack> list) {
        addBlock(list, Blocks.SAPLING_OAK);
        addBlock(list, Blocks.SAPLING_OAK_RETRO);
        addBlock(list, Blocks.SAPLING_PINE);
        addBlock(list, Blocks.SAPLING_BIRCH);
        addBlock(list, Blocks.SAPLING_CHERRY);
        addBlock(list, Blocks.SAPLING_EUCALYPTUS);
        addBlock(list, Blocks.SAPLING_THORN);
        addBlock(list, Blocks.SAPLING_PALM);
        addBlock(list, Blocks.SAPLING_SHRUB);
        addBlock(list, Blocks.SAPLING_CACAO);
        addBlock(list, AetherBlocks.SAPLING_SKYROOT);
        addBlock(list, AetherBlocks.SAPLING_OAK_GOLDEN);
    }

    private static void addWoodTypes(List<ItemStack> list) {
        addBlock(list, Blocks.PLANKS_OAK);
        addBlock(list, Blocks.STAIRS_PLANKS_OAK);
        addBlock(list, Blocks.SLAB_PLANKS_OAK);
        addBlock(list, Blocks.FENCE_PLANKS_OAK);
        addBlock(list, Blocks.FENCE_GATE_PLANKS_OAK);
        addBlock(list, Blocks.TRAPDOOR_PLANKS_OAK);
        addBlock(list, Blocks.CHEST_PLANKS_OAK);
        list.add(new ItemStack(Items.DOOR_OAK));
        list.add(new ItemStack(Items.SIGN));
        addBlock(list, Blocks.BUTTON_PLANKS_OAK);
        addBlock(list, Blocks.PRESSURE_PLATE_PLANKS_OAK);

        addBlock(list, AetherBlocks.PLANKS_SKYROOT);
        addBlock(list, AetherBlocks.STAIRS_PLANKS_SKYROOT);
        addBlock(list, AetherBlocks.SLAB_PLANKS_SKYROOT);
        addBlock(list, AetherBlocks.FENCE_PLANKS_SKYROOT);
        addBlock(list, AetherBlocks.FENCEGATE_PLANKS_SKYROOT);
        addBlock(list, AetherBlocks.TRAPDOOR_PLANKS_SKYROOT);
        addBlock(list, AetherBlocks.CHEST_PLANKS_SKYROOT);
        list.add(new ItemStack(AetherItems.DOOR_SKYROOT));
        list.add(new ItemStack(AetherItems.SIGN_SKYROOT));

        addBlock(list, AetherBlocks.BUTTON_PLANKS_SKYROOT);
        addBlock(list, AetherBlocks.PRESSURE_PLATE_PLANKS_SKYROOT);
    }

    private static void addPaintedTypes(List<ItemStack> list) {
        for (DyeColor color : RAINBOW_ORDER) {
            addPainted(list, color);
        }

    }

    private static void addPainted(@NonNull List<ItemStack> list, DyeColor color) {
        addPaintedBlock(list, Blocks.PLANKS_OAK_PAINTED, color);
        addPaintedBlock(list, Blocks.STAIRS_PLANKS_PAINTED, color);
        addPaintedBlock(list, Blocks.SLAB_PLANKS_PAINTED, color);
        addPaintedBlock(list, Blocks.FENCE_PLANKS_OAK_PAINTED, color);
        addPaintedBlock(list, Blocks.FENCE_GATE_PLANKS_OAK_PAINTED, color);
        addPaintedBlock(list, Blocks.TRAPDOOR_PLANKS_PAINTED, color);
        addPaintedBlock(list, Blocks.CHEST_PLANKS_OAK_PAINTED, color);
        list.add(new ItemStack(Items.DOOR_OAK_PAINTED, 1, color.itemMeta));
        list.add(new ItemStack(Items.SIGN_PAINTED, 1, color.itemMeta));
        addPaintedBlock(list, Blocks.BUTTON_PLANKS_PAINTED, color);
        addPaintedBlock(list, Blocks.PRESSURE_PLATE_PLANKS_OAK_PAINTED, color);

        addPaintedBlock(list, AetherBlocks.PLANKS_SKYROOT_PAINTED, color);
        addPaintedBlock(list, AetherBlocks.STAIRS_PLANKS_SKYROOT_PAINTED, color);
        addPaintedBlock(list, AetherBlocks.SLAB_PLANKS_SKYROOT_PAINTED, color);
        addPaintedBlock(list, AetherBlocks.FENCE_PLANKS_SKYROOT_PAINTED, color);
        addPaintedBlock(list, AetherBlocks.FENCEGATE_PLANKS_SKYROOT_PAINTED, color);
        addPaintedBlock(list, AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED, color);
        addPaintedBlock(list, AetherBlocks.CHEST_PLANKS_SKYROOT_PAINTED, color);
        list.add(new ItemStack(AetherItems.DOOR_SKYROOT_PAINTED, 1, color.itemMeta));
        list.add(new ItemStack(AetherItems.SIGN_SKYROOT_PAINTED, 1, color.itemMeta));
        addPaintedBlock(list, AetherBlocks.BUTTON_PLANKS_SKYROOT_PAINTED, color);
        addPaintedBlock(list, AetherBlocks.PRESSURE_PLATE_PLANKS_SKYROOT_PAINTED, color);

        addPaintedBlock(list, Blocks.WOOL, color);

        addPaintedBlock(list, Blocks.LAMP_IDLE, color);
    }

    private static void addPaintedBlock(@NonNull List<ItemStack> list, Block<?> block, DyeColor color) {
        list.add(new ItemStack(block, 1, painted(block).toMetadata(color)));
    }

    private static void addDyes(List<ItemStack> list) {
        for (DyeColor color : RAINBOW_ORDER) {
            list.add(new ItemStack(Items.DYE, 1, color.itemMeta));
        }

    }

    private static void addNaturalTypes(List<ItemStack> list) {
        addBlock(list, Blocks.GRASS);
        addBlock(list, Blocks.GRASS_RETRO);
        addBlock(list, Blocks.DIRT);

        addBlock(list, Blocks.PATH_DIRT);
        addBlock(list, Blocks.FARMLAND_DIRT);

        addBlock(list, Blocks.GRASS_SCORCHED);
        addBlock(list, Blocks.DIRT_SCORCHED);
        addBlock(list, Blocks.DIRT_SCORCHED_RICH);

        addBlock(list, AetherBlocks.GRASS_AETHER);
        addBlock(list, AetherBlocks.DIRT_AETHER);

        addBlock(list, AetherBlocks.PATH_DIRT_AETHER);

        addBlock(list, Blocks.MUD);
        addBlock(list, Blocks.MUD_BAKED);

        addBlock(list, Blocks.GRAVEL);

        addBlock(list, Blocks.BLOCK_CLAY);
        addBlock(list, Blocks.BRICK_CLAY);
        addBlock(list, Blocks.STAIRS_BRICK_CLAY);
        addBlock(list, Blocks.SLAB_BRICK_CLAY);

        addBlock(list, Blocks.ICE);
        addBlock(list, Blocks.PERMAICE);
        addBlock(list, AetherBlocks.ICESTONE);

        addBlock(list, Blocks.BLOCK_SNOW);
        addBlock(list, Blocks.LAYER_SNOW);

        addBlock(list, Blocks.BLOCK_ASH);
        addBlock(list, Blocks.LAYER_ASH);

        addBlock(list, Blocks.BONESHALE);
        addBlock(list, Blocks.BEDROCK);

        addBlock(list, Blocks.OBSIDIAN);
        addBlock(list, AetherBlocks.AEROGEL);

        addBlock(list, Blocks.SOULSAND);
        addBlock(list, Blocks.SOULSCHIST);

        addBlock(list, Blocks.MAGMA);
        addBlock(list, Blocks.BRIMTHAW);

        addBlock(list, Blocks.GLOWSTONE);

        addBlock(list, Blocks.SULFUR);

        addBlock(list, Blocks.THERMAL_VENT);
        addBlock(list, Blocks.EMBER);

        addBlock(list, AetherBlocks.QUICKSOIL);

        addBlock(list, AetherBlocks.AERCLOUD_WHITE);
        addBlock(list, AetherBlocks.AERCLOUD_BLUE);
        addBlock(list, AetherBlocks.AERCLOUD_GOLD);

    }

    private static void addFood(@NonNull List<ItemStack> list) {
        list.add(new ItemStack(Items.FOOD_APPLE));
        list.add(new ItemStack(Items.FOOD_APPLE_GOLD));
        list.add(new ItemStack(Items.FOOD_CHERRY));
        list.add(new ItemStack(Items.FOOD_PORKCHOP_RAW));
        list.add(new ItemStack(Items.FOOD_PORKCHOP_COOKED));
        list.add(new ItemStack(Items.FOOD_VENISON_RAW));
        list.add(new ItemStack(Items.FOOD_VENISON_COOKED));
        list.add(new ItemStack(Items.FOOD_FISH_RAW));
        list.add(new ItemStack(Items.FOOD_FISH_COOKED));
        list.add(new ItemStack(Items.DUST_SUGAR));
        list.add(new ItemStack(Items.EGG_CHICKEN));
        list.add(new ItemStack(AetherItems.EGG_MOA_BLUE));
        list.add(new ItemStack(AetherItems.EGG_MOA_WHITE));
        list.add(new ItemStack(AetherItems.EGG_MOA_BLACK));
        list.add(new ItemStack(Items.SEEDS_WHEAT));
        list.add(new ItemStack(Items.SEEDS_PUMPKIN));
        list.add(new ItemStack(Items.WHEAT));
        list.add(new ItemStack(Items.DOUGH));
        list.add(new ItemStack(Items.FOOD_BREAD));
        list.add(new ItemStack(Items.FOOD_COOKIE));
        list.add(new ItemStack(Items.FOOD_CAKE));
        list.add(new ItemStack(Items.FOOD_PUMPKIN_PIE));
        list.add(new ItemStack(Items.BOWL));
        list.add(new ItemStack(Items.FOOD_STEW_MUSHROOM));
        list.add(new ItemStack(AetherItems.FOOD_HEALING_STONE));
        list.add(new ItemStack(AetherItems.FOOD_GUMMY_BLUE));
        list.add(new ItemStack(AetherItems.FOOD_GUMMY_GOLD));

        list.add(new ItemStack(AetherItems.LIFESHARD));
    }

    private static void addOrganicTypes(@NonNull List<ItemStack> list) {
        list.add(new ItemStack(Blocks.FLOWER_YELLOW, 1, 0));
        list.add(new ItemStack(Blocks.FLOWER_RED, 1, 0));
        list.add(new ItemStack(Blocks.FLOWER_PINK, 1, 0));
        list.add(new ItemStack(Blocks.FLOWER_PURPLE, 1, 0));
        list.add(new ItemStack(Blocks.FLOWER_LIGHT_BLUE, 1, 0));
        list.add(new ItemStack(Blocks.FLOWER_ORANGE, 1, 0));
        list.add(new ItemStack(AetherBlocks.FLOWER_PURPLE, 1, 0));
        list.add(new ItemStack(AetherBlocks.FLOWER_WHITE, 1, 0));

        addBlock(list, Blocks.MUSHROOM_BROWN);
        addBlock(list, Blocks.MUSHROOM_RED);

        addBlock(list, Blocks.TALLGRASS);
        addBlock(list, AetherBlocks.TALLGRASS_AETHER);
        addBlock(list, Blocks.TALLGRASS_FERN);
        addBlock(list, Blocks.DEADBUSH);
        addBlock(list, AetherBlocks.DEADBUSH_AETHER);
        addBlock(list, Blocks.SPINIFEX);
        addBlock(list, Blocks.ALGAE);
        addBlock(list, Blocks.CACTUS);

        addBlock(list, Blocks.COBWEB);
        addBlock(list, Blocks.BONE_PILE);
        addBlock(list, Blocks.SOUL_CATCHER);
        addBlock(list, Blocks.BOULDER_MAGMATIC);
        addBlock(list, Blocks.BOULDER_SULFURIC);

        addBlock(list, Blocks.PUMPKIN);
        addBlock(list, Blocks.PUMPKIN_CARVED_IDLE);
        addBlock(list, Blocks.PUMPKIN_CARVED_ACTIVE);
        addBlock(list, Blocks.BLOCK_SUGARCANE);
        addBlock(list, Blocks.BLOCK_SUGARCANE_BAKED);

        addBlock(list, Blocks.SPONGE_DRY);
        addBlock(list, Blocks.SPONGE_WET);
        addBlock(list, Blocks.PUMICE_DRY);
        addBlock(list, Blocks.PUMICE_WET);

    }

    private static void addWorkstationsAndGlass(List<ItemStack> list) {
        addBlock(list, Blocks.WORKBENCH);
        addBlock(list, Blocks.FURNACE_STONE_IDLE);
        addBlock(list, Blocks.FURNACE_BLAST_IDLE);
        addBlock(list, Blocks.TROMMEL_IDLE);
        addBlock(list, AetherBlocks.ENCHANTER_IDLE);
        addBlock(list, AetherBlocks.FREEZER_IDLE);
        addBlock(list, AetherBlocks.INCUBATOR_IDLE);
        addBlock(list, Blocks.BOOKSHELF_PLANKS_OAK);
        addBlock(list, Blocks.LADDER_OAK);
        addBlock(list, Blocks.TORCH_COAL);
        addBlock(list, AetherBlocks.TORCH_AMBROSIUM);
        addBlock(list, Blocks.GLASS);
        addBlock(list, Blocks.TRAPDOOR_GLASS);
        addBlock(list, Blocks.GLASS_TINTED);
        addBlock(list, Blocks.GLASS_STEEL);
        addBlock(list, AetherBlocks.GLASS_QUICKSOIL);
        addBlock(list, AetherBlocks.TRAPDOOR_GLASS_QUICKSOIL);
    }

    private static void addArmor(@NonNull List<ItemStack> list) {
        list.add(new ItemStack(Items.ARMOR_HELMET_LEATHER));
        list.add(new ItemStack(Items.ARMOR_CHESTPLATE_LEATHER));
        list.add(new ItemStack(Items.ARMOR_LEGGINGS_LEATHER));
        list.add(new ItemStack(Items.ARMOR_BOOTS_LEATHER));
        list.add(new ItemStack(AetherItems.ARMOR_GLOVES_LEATHER));
        list.add(new ItemStack(AetherItems.ARMOR_TALISMAN_LEATHER));
        list.add(new ItemStack(Items.ARMOR_WOLF_LEATHER));

        list.add(new ItemStack(Items.ARMOR_HELMET_CHAINMAIL));
        list.add(new ItemStack(Items.ARMOR_CHESTPLATE_CHAINMAIL));
        list.add(new ItemStack(Items.ARMOR_LEGGINGS_CHAINMAIL));
        list.add(new ItemStack(Items.ARMOR_BOOTS_CHAINMAIL));
        list.add(new ItemStack(AetherItems.ARMOR_GLOVES_CHAINMAIL));
        list.add(new ItemStack(AetherItems.ARMOR_TALISMAN_CHAINMAIL));
        list.add(new ItemStack(Items.ARMOR_WOLF_CHAINMAIL));

        list.add(new ItemStack(Items.ARMOR_HELMET_IRON));
        list.add(new ItemStack(Items.ARMOR_CHESTPLATE_IRON));
        list.add(new ItemStack(Items.ARMOR_LEGGINGS_IRON));
        list.add(new ItemStack(Items.ARMOR_BOOTS_IRON));
        list.add(new ItemStack(AetherItems.ARMOR_GLOVES_IRON));
        list.add(new ItemStack(AetherItems.ARMOR_TALISMAN_IRON));
        list.add(new ItemStack(Items.ARMOR_WOLF_IRON));

        list.add(new ItemStack(Items.ARMOR_HELMET_GOLD));
        list.add(new ItemStack(Items.ARMOR_CHESTPLATE_GOLD));
        list.add(new ItemStack(Items.ARMOR_LEGGINGS_GOLD));
        list.add(new ItemStack(Items.ARMOR_BOOTS_GOLD));
        list.add(new ItemStack(AetherItems.ARMOR_GLOVES_GOLD));
        list.add(new ItemStack(AetherItems.ARMOR_TALISMAN_GOLD));
        list.add(new ItemStack(Items.ARMOR_WOLF_GOLD));

        list.add(new ItemStack(Items.ARMOR_HELMET_DIAMOND));
        list.add(new ItemStack(Items.ARMOR_CHESTPLATE_DIAMOND));
        list.add(new ItemStack(Items.ARMOR_LEGGINGS_DIAMOND));
        list.add(new ItemStack(Items.ARMOR_BOOTS_DIAMOND));
        list.add(new ItemStack(AetherItems.ARMOR_GLOVES_DIAMOND));
        list.add(new ItemStack(AetherItems.ARMOR_TALISMAN_DIAMOND));
        list.add(new ItemStack(Items.ARMOR_WOLF_DIAMOND));

        list.add(new ItemStack(Items.ARMOR_HELMET_STEEL));
        list.add(new ItemStack(Items.ARMOR_CHESTPLATE_STEEL));
        list.add(new ItemStack(Items.ARMOR_LEGGINGS_STEEL));
        list.add(new ItemStack(Items.ARMOR_BOOTS_STEEL));
        list.add(new ItemStack(AetherItems.ARMOR_GLOVES_STEEL));
        list.add(new ItemStack(AetherItems.ARMOR_TALISMAN_STEEL));
        list.add(new ItemStack(Items.ARMOR_WOLF_STEEL));

        list.add(new ItemStack(AetherItems.ARMOR_HELMET_ZANITE));
        list.add(new ItemStack(AetherItems.ARMOR_CHESTPLATE_ZANITE));
        list.add(new ItemStack(AetherItems.ARMOR_LEGGINGS_ZANITE));
        list.add(new ItemStack(AetherItems.ARMOR_BOOTS_ZANITE));
        list.add(new ItemStack(AetherItems.ARMOR_GLOVES_ZANITE));
        list.add(new ItemStack(AetherItems.ARMOR_TALISMAN_ZANITE));
        list.add(new ItemStack(AetherItems.ARMOR_WOLF_ZANITE));

        list.add(new ItemStack(AetherItems.ARMOR_HELMET_GRAVITITE));
        list.add(new ItemStack(AetherItems.ARMOR_CHESTPLATE_GRAVITITE));
        list.add(new ItemStack(AetherItems.ARMOR_LEGGINGS_GRAVITITE));
        list.add(new ItemStack(AetherItems.ARMOR_BOOTS_GRAVITITE));
        list.add(new ItemStack(AetherItems.ARMOR_GLOVES_GRAVITITE));
        list.add(new ItemStack(AetherItems.ARMOR_TALISMAN_GRAVITITE));
        list.add(new ItemStack(AetherItems.ARMOR_WOLF_GRAVITITE));

        list.add(new ItemStack(AetherItems.ARMOR_HELMET_OBSIDIAN));
        list.add(new ItemStack(AetherItems.ARMOR_CHESTPLATE_OBSIDIAN));
        list.add(new ItemStack(AetherItems.ARMOR_LEGGINGS_OBSIDIAN));
        list.add(new ItemStack(AetherItems.ARMOR_BOOTS_OBSIDIAN));
        list.add(new ItemStack(AetherItems.ARMOR_GLOVES_OBSIDIAN));
        list.add(new ItemStack(AetherItems.ARMOR_WOLF_OBSIDIAN));

        list.add(new ItemStack(AetherItems.ARMOR_HELMET_PHOENIX));
        list.add(new ItemStack(AetherItems.ARMOR_CHESTPLATE_PHOENIX));
        list.add(new ItemStack(AetherItems.ARMOR_LEGGINGS_PHOENIX));
        list.add(new ItemStack(AetherItems.ARMOR_BOOTS_PHOENIX));
        list.add(new ItemStack(AetherItems.ARMOR_GLOVES_PHOENIX));
        list.add(new ItemStack(AetherItems.ARMOR_WOLF_PHOENIX));

        list.add(new ItemStack(AetherItems.ARMOR_HELMET_NEPTUNE));
        list.add(new ItemStack(AetherItems.ARMOR_CHESTPLATE_NEPTUNE));
        list.add(new ItemStack(AetherItems.ARMOR_LEGGINGS_NEPTUNE));
        list.add(new ItemStack(AetherItems.ARMOR_BOOTS_NEPTUNE));
        list.add(new ItemStack(AetherItems.ARMOR_GLOVES_NEPTUNE));
        list.add(new ItemStack(AetherItems.ARMOR_WOLF_NEPTUNE));

        list.add(new ItemStack(Items.ARMOR_QUIVER));
        list.add(new ItemStack(Items.ARMOR_QUIVER_GOLD));

        list.add(new ItemStack(Items.ARMOR_BOOTS_ICESKATES));
        list.add(new ItemStack(AetherItems.ARMOR_TALISMAN_ICE));

    }

    private static void addAccessories(@NonNull List<ItemStack> list) {
        list.add(new ItemStack(AetherItems.ARMOR_CAPE_AGILITY));
        list.add(new ItemStack(AetherItems.ARMOR_CAPE_INVISIBILITY));
        list.add(new ItemStack(AetherItems.ARMOR_CAPE_SWET));

        list.add(new ItemStack(AetherItems.ARMOR_CAPE_RED));
        list.add(new ItemStack(AetherItems.ARMOR_CAPE_ORANGE));
        list.add(new ItemStack(AetherItems.ARMOR_CAPE_YELLOW));
        list.add(new ItemStack(AetherItems.ARMOR_CAPE_LIME));
        list.add(new ItemStack(AetherItems.ARMOR_CAPE_GREEN));
        list.add(new ItemStack(AetherItems.ARMOR_CAPE_CYAN));
        list.add(new ItemStack(AetherItems.ARMOR_CAPE_LIGHTBLUE));
        list.add(new ItemStack(AetherItems.ARMOR_CAPE_BLUE));
        list.add(new ItemStack(AetherItems.ARMOR_CAPE_PURPLE));
        list.add(new ItemStack(AetherItems.ARMOR_CAPE_MAGENTA));
        list.add(new ItemStack(AetherItems.ARMOR_CAPE_PINK));
        list.add(new ItemStack(AetherItems.ARMOR_CAPE_BROWN));
        list.add(new ItemStack(AetherItems.ARMOR_CAPE_WHITE));
        list.add(new ItemStack(AetherItems.ARMOR_CAPE_SILVER));
        list.add(new ItemStack(AetherItems.ARMOR_CAPE_GRAY));
        list.add(new ItemStack(AetherItems.ARMOR_CAPE_BLACK));

        list.add(new ItemStack(AetherItems.ARMOR_SHIELD_REPULSION));

        list.add(new ItemStack(AetherItems.ARMOR_TALISMAN_BUBBLE));
        list.add(new ItemStack(AetherItems.ARMOR_TALISMAN_FEATHER_GOLD));
        list.add(new ItemStack(AetherItems.ARMOR_TALISMAN_REGEN));
    }

    private static void addRedstoneTypes(List<ItemStack> list) {
        addBlock(list, Blocks.BLOCK_REDSTONE);
        list.add(new ItemStack(Items.DUST_REDSTONE));
        list.add(new ItemStack(Items.REPEATER));
        list.add(new ItemStack(Items.TIMER));
        addBlock(list, Blocks.TORCH_REDSTONE_ACTIVE);
        addBlock(list, Blocks.LEVER_COBBLE_STONE);
        addBlock(list, Blocks.PUMPKIN_REDSTONE);
        addBlock(list, Blocks.PISTON_BASE);
        addBlock(list, Blocks.PISTON_BASE_STICKY);
        addBlock(list, Blocks.PISTON_BASE_STEEL);
        addBlock(list, Blocks.DISPENSER_COBBLE_STONE);
        addBlock(list, Blocks.MOTION_SENSOR_IDLE);

        addBlock(list, Blocks.ACTIVATOR);
        addBlock(list, Blocks.MATCHER);
        addBlock(list, Blocks.CONDUIT);
        addBlock(list, Blocks.RUBYGLASS_COLUMN);
        addBlock(list, Blocks.RUBYGLASS_NODE);
        addBlock(list, Blocks.BLOCK_RUBYGLASS);
        addBlock(list, Blocks.RUBYGLASS_GROWTH);
        addBlock(list, Blocks.RUBYGLASS_CRYSTAL);
        addBlock(list, Blocks.MESH);
        addBlock(list, Blocks.MESH_GOLD);
        addBlock(list, Blocks.MOBSPAWNER);
        addBlock(list, Blocks.MOBSPAWNER_DEACTIVATED);
        addBlock(list, Blocks.RAIL);
        addBlock(list, Blocks.RAIL_POWERED);
        addBlock(list, Blocks.RAIL_DETECTOR);
        list.add(new ItemStack(Items.MINECART));
        list.add(new ItemStack(Items.MINECART_CHEST));
        list.add(new ItemStack(Items.MINECART_FURNACE));
        list.add(new ItemStack(Items.SADDLE));
        list.add(new ItemStack(Items.BOAT));
        addBlock(list, Blocks.NOTEBLOCK);
        addBlock(list, Blocks.JUKEBOX);
        addBlock(list, Blocks.TNT);
        addBlock(list, Blocks.SPIKES);

    }

    private static void addOreTypes(List<ItemStack> list) {
        addBlock(list, Blocks.ORE_COAL_STONE);
        addBlock(list, Blocks.ORE_COAL_BASALT);
        addBlock(list, Blocks.ORE_COAL_LIMESTONE);
        addBlock(list, Blocks.ORE_COAL_GRANITE);
        addBlock(list, Blocks.ORE_COAL_PERMAFROST);

        addBlock(list, Blocks.ORE_IRON_STONE);
        addBlock(list, Blocks.ORE_IRON_BASALT);
        addBlock(list, Blocks.ORE_IRON_LIMESTONE);
        addBlock(list, Blocks.ORE_IRON_GRANITE);
        addBlock(list, Blocks.ORE_IRON_PERMAFROST);

        addBlock(list, Blocks.ORE_GOLD_STONE);
        addBlock(list, Blocks.ORE_GOLD_BASALT);
        addBlock(list, Blocks.ORE_GOLD_LIMESTONE);
        addBlock(list, Blocks.ORE_GOLD_GRANITE);
        addBlock(list, Blocks.ORE_GOLD_PERMAFROST);

        addBlock(list, Blocks.ORE_LAPIS_STONE);
        addBlock(list, Blocks.ORE_LAPIS_BASALT);
        addBlock(list, Blocks.ORE_LAPIS_LIMESTONE);
        addBlock(list, Blocks.ORE_LAPIS_GRANITE);
        addBlock(list, Blocks.ORE_LAPIS_PERMAFROST);

        addBlock(list, Blocks.ORE_REDSTONE_STONE);
        addBlock(list, Blocks.ORE_REDSTONE_BASALT);
        addBlock(list, Blocks.ORE_REDSTONE_LIMESTONE);
        addBlock(list, Blocks.ORE_REDSTONE_GRANITE);
        addBlock(list, Blocks.ORE_REDSTONE_PERMAFROST);

        addBlock(list, Blocks.ORE_DIAMOND_STONE);
        addBlock(list, Blocks.ORE_DIAMOND_BASALT);
        addBlock(list, Blocks.ORE_DIAMOND_LIMESTONE);
        addBlock(list, Blocks.ORE_DIAMOND_GRANITE);
        addBlock(list, Blocks.ORE_DIAMOND_PERMAFROST);

        addBlock(list, Blocks.ORE_NETHERCOAL_BASALT);
        addBlock(list, Blocks.ORE_NETHERCOAL_NETHERRACK);
        addBlock(list, Blocks.ORE_NETHERCOAL_GLOOMSTONE);

        addBlock(list, AetherBlocks.ORE_AMBROSIUM_HOLYSTONE);

        addBlock(list, AetherBlocks.ORE_ZANITE_HOLYSTONE);

        addBlock(list, AetherBlocks.ORE_GRAVITITE_HOLYSTONE);

    }

    private static void addOreProducts(@NonNull List<ItemStack> list) {
        list.add(new ItemStack(Items.AMMO_PEBBLE));
        list.add(new ItemStack(Items.COAL));
        list.add(new ItemStack(Items.COAL, 1, 1));
        list.add(new ItemStack(Items.NETHERCOAL));
        list.add(new ItemStack(Items.OLIVINE));
        list.add(new ItemStack(AetherItems.AMBROSIUM));

        list.add(new ItemStack(Items.ORE_RAW_IRON));
        list.add(new ItemStack(Items.INGOT_IRON));
        list.add(new ItemStack(Items.INGOT_STEEL_CRUDE));
        list.add(new ItemStack(Items.INGOT_STEEL));
        list.add(new ItemStack(Items.ORE_RAW_GOLD));
        list.add(new ItemStack(Items.INGOT_GOLD));
        list.add(new ItemStack(Items.QUARTZ));
        list.add(new ItemStack(Items.RUBYGLASS));
        list.add(new ItemStack(Items.DIAMOND));
        list.add(new ItemStack(Items.FLINT));
        list.add(new ItemStack(Items.CLAY));
        list.add(new ItemStack(Items.BRICK_CLAY));

        list.add(new ItemStack(Items.SULFUR));
        list.add(new ItemStack(Items.DUST_GLOWSTONE));

        list.add(new ItemStack(AetherItems.AMBER));
        list.add(new ItemStack(AetherItems.ZANITE));
        list.add(new ItemStack(AetherItems.ORE_RAW_GRAVITITE));
    }

    private static void addStorageTypes(List<ItemStack> list) {
        addBlock(list, Blocks.BLOCK_IRON);
        addBlock(list, Blocks.BLOCK_STEEL);
        addBlock(list, Blocks.BLOCK_GOLD);
        addBlock(list, Blocks.BLOCK_LAPIS);
        addBlock(list, Blocks.BLOCK_DIAMOND);
        addBlock(list, Blocks.BLOCK_QUARTZ);
        addBlock(list, AetherBlocks.BLOCK_AMBER);
        addBlock(list, AetherBlocks.BLOCK_ZANITE);
        addBlock(list, AetherBlocks.BLOCK_GRAVITITE);

        addBlock(list, Blocks.BLOCK_COAL);
        addBlock(list, Blocks.BLOCK_CHARCOAL);
        addBlock(list, Blocks.BLOCK_NETHER_COAL);
        addBlock(list, Blocks.BLOCK_OLIVINE);
        addBlock(list, AetherBlocks.BLOCK_AMBROSIUM);

        addBlock(list, Blocks.BRICK_IRON);
        addBlock(list, Blocks.STAIRS_BRICK_IRON);
        addBlock(list, Blocks.SLAB_BRICK_IRON);

        addBlock(list, Blocks.BRICK_STEEL);
        addBlock(list, Blocks.STAIRS_BRICK_STEEL);
        addBlock(list, Blocks.SLAB_BRICK_STEEL);

        addBlock(list, Blocks.BRICK_GOLD);
        addBlock(list, Blocks.STAIRS_BRICK_GOLD);
        addBlock(list, Blocks.SLAB_BRICK_GOLD);

        addBlock(list, Blocks.BRICK_DIAMOND);
        addBlock(list, Blocks.STAIRS_BRICK_DIAMOND);
        addBlock(list, Blocks.SLAB_BRICK_DIAMOND);

        addBlock(list, Blocks.BRICK_QUARTZ);
        addBlock(list, Blocks.STAIRS_BRICK_QUARTZ);
        addBlock(list, Blocks.SLAB_BRICK_QUARTZ);

        addBlock(list, Blocks.BRICK_LAPIS);
        addBlock(list, Blocks.STAIRS_BRICK_LAPIS);
        addBlock(list, Blocks.SLAB_BRICK_LAPIS);

        addBlock(list, Blocks.BRICK_OLIVINE);
        addBlock(list, Blocks.STAIRS_BRICK_OLIVINE);
        addBlock(list, Blocks.SLAB_BRICK_OLIVINE);

        addBlock(list, Blocks.BRICK_RUBYGLASS);
        addBlock(list, Blocks.STAIRS_BRICK_RUBYGLASS);
        addBlock(list, Blocks.SLAB_BRICK_RUBYGLASS);

        addBlock(list, AetherBlocks.BRICK_ZANITE);
        addBlock(list, AetherBlocks.STAIRS_BRICK_ZANITE);
        addBlock(list, AetherBlocks.SLAB_BRICK_ZANITE);

        addBlock(list, AetherBlocks.BRICK_GRAVITITE);
        addBlock(list, AetherBlocks.STAIRS_BRICK_GRAVITITE);
        addBlock(list, AetherBlocks.SLAB_BRICK_GRAVITITE);

    }

    private static void addDungeonBlocks(@NonNull List<ItemStack> list) {
        addBlock(list, AetherBlocks.CARVED_STONE);
        addBlock(list, AetherBlocks.STAIRS_CARVED_STONE);
        addBlock(list, AetherBlocks.SLAB_CARVED_STONE);
        addBlock(list, AetherBlocks.CARVED_STONE_LIGHT);

        addBlock(list, AetherBlocks.CHEST_DUNGEON_BRONZE);
        list.add(new ItemStack(AetherItems.DOOR_DUNGEON_BRONZE));

        addBlock(list, AetherBlocks.CARVED_ANGELIC);
        addBlock(list, AetherBlocks.STAIRS_CARVED_ANGELIC);
        addBlock(list, AetherBlocks.SLAB_CARVED_ANGELIC);
        addBlock(list, AetherBlocks.CARVED_ANGELIC_LIGHT);

        addBlock(list, AetherBlocks.CHEST_DUNGEON_SILVER);
        list.add(new ItemStack(AetherItems.DOOR_DUNGEON_SILVER));

        addBlock(list, AetherBlocks.CARVED_HELLFIRE);
        addBlock(list, AetherBlocks.STAIRS_CARVED_HELLFIRE);
        addBlock(list, AetherBlocks.SLAB_CARVED_HELLFIRE);
        addBlock(list, AetherBlocks.CARVED_HELLFIRE_LIGHT);

        addBlock(list, AetherBlocks.CHEST_DUNGEON_GOLD);
        list.add(new ItemStack(AetherItems.DOOR_DUNGEON_GOLD));

        addBlock(list, AetherBlocks.PILLAR);
        addBlock(list, AetherBlocks.PILLAR_CAPSTONE);

        addBlock(list, AetherBlocks.CHEST_MIMIC_OAK);
        for (DyeColor color : RAINBOW_ORDER) {
            addPaintedBlock(list, AetherBlocks.CHEST_MIMIC_OAK_PAINTED, color);
        }

        addBlock(list, AetherBlocks.CHEST_MIMIC_SKYROOT);
        for (DyeColor color : RAINBOW_ORDER) {
            addPaintedBlock(list, AetherBlocks.CHEST_MIMIC_SKYROOT_PAINTED, color);
        }

        addBlock(list, AetherBlocks.CHEST_MIMIC_BRONZE);
        addBlock(list, AetherBlocks.CHEST_MIMIC_SILVER);
        addBlock(list, AetherBlocks.CHEST_MIMIC_GOLD);
    }

    private static void addBasics(@NonNull List<ItemStack> list) {
        list.add(new ItemStack(Items.STICK));
        list.add(new ItemStack(AetherItems.STICK_SKYROOT));
        list.add(new ItemStack(Items.AMMO_SNOWBALL));
        list.add(new ItemStack(Items.SUGARCANE));
        list.add(new ItemStack(Items.PAPER));
        list.add(new ItemStack(Items.BOOK));
    }

    private static void addMobDrops(@NonNull List<ItemStack> list) {
        list.add(new ItemStack(Items.CLOTH));
        list.add(new ItemStack(Items.STRING));
        list.add(new ItemStack(Items.FEATHER_CHICKEN));
        list.add(new ItemStack(Items.GUNPOWDER));
        list.add(new ItemStack(Items.BONE));
        list.add(new ItemStack(Items.CHAINLINK));
        list.add(new ItemStack(Items.SLIMEBALL));
        list.add(new ItemStack(Items.LEATHER));
        list.add(new ItemStack(AetherItems.PETAL_AECHOR));
    }

    private static void addMisc(@NonNull List<ItemStack> list) {
        list.add(new ItemStack(AetherItems.MEDAL_VICTORY));

        list.add(new ItemStack(AetherItems.KEY_BRONZE));
        list.add(new ItemStack(AetherItems.KEY_SILVER));
        list.add(new ItemStack(AetherItems.KEY_GOLD));
    }

    private static void addPlaceables(List<ItemStack> list) {
        addBlock(list, Blocks.PAPER_WALL);
        addBlock(list, Blocks.FENCE_PAPER_WALL);
        addBlock(list, Blocks.FENCE_CHAINLINK);
        addBlock(list, Blocks.FENCE_STEEL);

        list.add(new ItemStack(Items.PAINTING));
        list.add(new ItemStack(Items.ROPE));

        list.add(new ItemStack(Items.DOOR_GLASS));
        list.add(new ItemStack(Items.DOOR_IRON));
        list.add(new ItemStack(Items.DOOR_STEEL));
        list.add(new ItemStack(AetherItems.DOOR_GLASS_AMBROSIUM));

        addBlock(list, Blocks.TRAPDOOR_IRON);
        addBlock(list, Blocks.TRAPDOOR_STEEL);

        list.add(new ItemStack(Items.FLAG));
        list.add(new ItemStack(Items.BED));
        list.add(new ItemStack(Items.SEAT));
        addBlock(list, Blocks.BRAZIER_INACTIVE);
        list.add(new ItemStack(Items.BASKET));
        list.add(new ItemStack(Items.JAR));

        list.add(new ItemStack(Items.LANTERN_FIREFLY_GREEN));
        list.add(new ItemStack(Items.LANTERN_FIREFLY_BLUE));
        list.add(new ItemStack(Items.LANTERN_FIREFLY_ORANGE));
        list.add(new ItemStack(Items.LANTERN_FIREFLY_RED));
        list.add(new ItemStack(AetherItems.LANTERN_FIREFLY_SILVER));

        list.add(new ItemStack(Items.JAR_BUTTERFLY_BLUE));
        list.add(new ItemStack(Items.JAR_BUTTERFLY_ORANGE));
        list.add(new ItemStack(Items.JAR_BUTTERFLY_PINK));
        list.add(new ItemStack(Items.JAR_BUTTERFLY_SILVER));
    }

    private static void addRecords(@NonNull List<ItemStack> list) {
        list.add(new ItemStack(Items.RECORD_13));
        list.add(new ItemStack(Items.RECORD_CAT));
        list.add(new ItemStack(Items.RECORD_BLOCKS));
        list.add(new ItemStack(Items.RECORD_CHIRP));
        list.add(new ItemStack(Items.RECORD_FAR));
        list.add(new ItemStack(Items.RECORD_MALL));
        list.add(new ItemStack(Items.RECORD_MELLOHI));
        list.add(new ItemStack(Items.RECORD_STAL));
        list.add(new ItemStack(Items.RECORD_STRAD));
        list.add(new ItemStack(Items.RECORD_WARD));
        list.add(new ItemStack(Items.RECORD_WAIT));
        list.add(new ItemStack(Items.RECORD_DOG));
        list.add(new ItemStack(AetherItems.RECORD_AETHER));
        list.add(new ItemStack(AetherItems.RECORD_MORNING));
        list.add(new ItemStack(AetherItems.RECORD_DAWN));
        list.add(new ItemStack(AetherItems.RECORD_NETHER));
    }

    private static void addBucketVariants(List<ItemStack> list, Item item) {
        if (item instanceof ItemBucket itemBucket) {
            list.add(new ItemStack(item));

            for (NamespaceID stateId : ItemBucket.getRegisteredStateIds()) {
                if (!ItemBucket.STATE_EMPTY.equals(stateId)) {
                    ItemStack itemStack = new ItemStack(item, 1);
                    ItemBucket.setState(itemStack, stateId);
                    ItemBucket.setCharges(itemStack, itemBucket.maxCharges);
                    list.add(itemStack);
                }
            }
        }

    }

    private static void addBlock(List<ItemStack> list, @NonNull Block<?> @NonNull ... blocks) {
        for (Block<?> block : blocks) {
            list.add(new ItemStack(block));
        }

    }

    private static @NonNull IPainted painted(@NonNull Block<?> block) {
        return (IPainted) block.getLogic();
    }

}
