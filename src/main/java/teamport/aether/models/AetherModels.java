package teamport.aether.models;

import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.*;
import net.minecraft.client.render.entity.EntityRendererSprite;
import net.minecraft.client.render.item.model.ItemModelBow;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.core.util.helper.Side;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.entity.projectile.ProjectileHammerHead;
import teamport.aether.items.AetherItems;
import turniplabs.halplibe.helper.ModelHelper;
import turniplabs.halplibe.util.ModelEntrypoint;

public class AetherModels implements ModelEntrypoint {

    @Override
    public void initBlockModels(BlockModelDispatcher dispatcher) {

        ModelHelper.setBlockModel(AetherBlocks.PORTAL_AETHER, () -> new BlockModelPortal<>(AetherBlocks.PORTAL_AETHER, "aether:block/portal_aether/")
                .setAllTextures(0, "aether:block/portal_aether"));


        ModelHelper.setBlockModel(AetherBlocks.GRASS_AETHER, () -> new BlockModelStandard<>(AetherBlocks.GRASS_AETHER)
                .setTex(0, "aether:block/grass_aether/top", Side.TOP)
                .setTex(0, "aether:block/grass_aether/bottom", Side.BOTTOM)
                .setTex(0, "aether:block/grass_aether/side", Side.NORTH, Side.SOUTH, Side.EAST, Side.WEST));

        ModelHelper.setBlockModel(AetherBlocks.DIRT_AETHER, () -> new BlockModelStandard<>(AetherBlocks.DIRT_AETHER)
                .setAllTextures(0, "aether:block/dirt_aether"));


        ModelHelper.setBlockModel(AetherBlocks.HOLYSTONE, () -> new BlockModelStandard<>(AetherBlocks.HOLYSTONE)
                .setAllTextures(0, "aether:block/holystone"));

        ModelHelper.setBlockModel(AetherBlocks.HOLYSTONE_POLISHED, () -> new BlockModelStandard<>(AetherBlocks.HOLYSTONE_POLISHED)
                .setTex(0, "aether:block/polished_holystone_side", Side.NORTH, Side.SOUTH, Side.EAST, Side.WEST)
                .setTex(0, "aether:block/polished_holystone_top", Side.TOP, Side.BOTTOM));

        ModelHelper.setBlockModel(AetherBlocks.HOLYSTONE_CARVED, () -> new BlockModelStandard<>(AetherBlocks.HOLYSTONE_CARVED)
                .setTex(0, "aether:block/carved_holystone", Side.NORTH, Side.SOUTH, Side.EAST, Side.WEST)
                .setTex(0, "aether:block/polished_holystone_top", Side.TOP, Side.BOTTOM));

        ModelHelper.setBlockModel(AetherBlocks.COBBLE_HOLYSTONE, () -> new BlockModelStandard<>(AetherBlocks.HOLYSTONE)
                .setAllTextures(0, "aether:block/cobbled_holystone"));

        ModelHelper.setBlockModel(AetherBlocks.COBBLE_HOLYSTONE_MOSSY, () -> new BlockModelStandard<>(AetherBlocks.HOLYSTONE)
                .setAllTextures(0, "aether:block/cobbled_holystone_mossy"));

        ModelHelper.setBlockModel(AetherBlocks.BRICK_HOLYSTONE, () -> new BlockModelStandard<>(AetherBlocks.HOLYSTONE)
                .setAllTextures(0, "aether:block/brick_holystone"));


        ModelHelper.setBlockModel(AetherBlocks.ICESTONE, () -> new BlockModelStandard<>(AetherBlocks.ICESTONE)
                .setAllTextures(0, "aether:block/icestone"));


        ModelHelper.setBlockModel(AetherBlocks.QUICKSOIL, () -> new BlockModelStandard<>(AetherBlocks.QUICKSOIL)
                .setAllTextures(0, "aether:block/quicksoil"));

        ModelHelper.setBlockModel(AetherBlocks.GLASS_QUICKSOIL, () -> new BlockModelTransparent<>(AetherBlocks.QUICKSOIL, false).onRenderLayer(1)
                .setAllTextures(0, "aether:block/glass_quicksoil"));

        ModelHelper.setBlockModel(AetherBlocks.DOOR_GLASS_QUICKSOIL_BOTTOM, () -> new BlockModelDoorGlass<>(AetherBlocks.DOOR_GLASS_QUICKSOIL_BOTTOM).onRenderLayer(1)
                .setTex(0, "aether:block/door/glass_quicksoil/frame", Side.TOP, Side.BOTTOM)
                .setTex(0, "aether:block/door/glass_quicksoil/bottom", Side.NORTH, Side.SOUTH, Side.EAST, Side.WEST));
        ModelHelper.setBlockModel(AetherBlocks.DOOR_GLASS_QUICKSOIL_TOP, () -> new BlockModelDoorGlass<>(AetherBlocks.DOOR_GLASS_QUICKSOIL_TOP).onRenderLayer(1)
                .setTex(0, "aether:block/door/glass_quicksoil/frame", Side.TOP, Side.BOTTOM)
                .setTex(0, "aether:block/door/glass_quicksoil/top", Side.NORTH, Side.SOUTH, Side.EAST, Side.WEST));

        ModelHelper.setBlockModel(AetherBlocks.TRAPDOOR_GLASS_QUICKSOIL, () -> new BlockModelTrapDoor<>(AetherBlocks.TRAPDOOR_GLASS_QUICKSOIL).onRenderLayer(1)
                .setTex(0, "aether:block/trapdoor/glass_quicksoil/top", Side.TOP, Side.BOTTOM)
                .setTex(0, "aether:block/trapdoor/glass_quicksoil/side", Side.NORTH, Side.SOUTH, Side.EAST, Side.WEST));


        ModelHelper.setBlockModel(AetherBlocks.FLOWER_PURPLE, () -> new BlockModelFlowerStackable<>(AetherBlocks.FLOWER_PURPLE, "aether:block/flower_purple/"));
        ModelHelper.setBlockModel(AetherBlocks.FLOWER_WHITE, () -> new BlockModelFlowerStackable<>(AetherBlocks.FLOWER_WHITE, "aether:block/flower_white/"));


        ModelHelper.setBlockModel(AetherBlocks.PLANKS_SKYROOT, () -> new BlockModelStandard<>(AetherBlocks.PLANKS_SKYROOT)
                .setAllTextures(0, "aether:block/skyroot"));

        ModelHelper.setBlockModel(AetherBlocks.DOOR_PLANKS_SKYROOT_BOTTOM, () -> new BlockModelDoor<>(AetherBlocks.DOOR_PLANKS_SKYROOT_BOTTOM)
                .setTex(0, "aether:block/door/skyroot/frame", Side.TOP, Side.BOTTOM)
                .setTex(0, "aether:block/door/skyroot/bottom", Side.NORTH, Side.SOUTH, Side.EAST, Side.WEST));
        ModelHelper.setBlockModel(AetherBlocks.DOOR_PLANKS_SKYROOT_TOP, () -> new BlockModelDoor<>(AetherBlocks.DOOR_PLANKS_SKYROOT_TOP)
                .setTex(0, "aether:block/door/skyroot/frame", Side.TOP, Side.BOTTOM)
                .setTex(0, "aether:block/door/skyroot/top", Side.NORTH, Side.SOUTH, Side.EAST, Side.WEST));

        ModelHelper.setBlockModel(AetherBlocks.TRAPDOOR_PLANKS_SKYROOT, () -> new BlockModelTrapDoor<>(AetherBlocks.TRAPDOOR_PLANKS_SKYROOT)
                .setTex(0, "aether:block/trapdoor/skyroot/top", Side.TOP, Side.BOTTOM)
                .setTex(0, "aether:block/trapdoor/skyroot/side", Side.NORTH, Side.SOUTH, Side.EAST, Side.WEST));

        ModelHelper.setBlockModel(AetherBlocks.CHEST_PLANKS_SKYROOT, () -> new BlockModelChest<>(AetherBlocks.CHEST_PLANKS_SKYROOT, "aether:block/chest/skyroot/")
                .setAllTextures(0, "aether:block/chest/skyroot/top"));

        ModelHelper.setBlockModel(AetherBlocks.BUTTON_PLANKS_SKYROOT, () -> new BlockModelStandard<>(AetherBlocks.BUTTON_PLANKS_SKYROOT)
                .setAllTextures(0, "aether:block/skyroot").withCustomItemBounds(0.3125, 0.375, 0.375, 0.6875, 0.625, 0.625));

        ModelHelper.setBlockModel(AetherBlocks.PRESSURE_PLATE_PLANKS_SKYROOT, () -> new BlockModelStandard<>(AetherBlocks.PRESSURE_PLATE_PLANKS_SKYROOT)
                .setAllTextures(0, "aether:block/skyroot").withCustomItemBounds(0.0, 0.375, 0.0, 1.0, 0.625, 1.0));



        ModelHelper.setBlockModel(AetherBlocks.LOG_SKYROOT, () -> new BlockModelAxisAligned<>(AetherBlocks.LOG_SKYROOT)
                .setTex(0, "aether:block/log/skyroot_side", Side.NORTH, Side.SOUTH, Side.EAST, Side.WEST)
                .setTex(0, "aether:block/log/skyroot_top", Side.TOP, Side.BOTTOM));
        ModelHelper.setBlockModel(AetherBlocks.LOG_OAK_GOLDEN, () -> new BlockModelAxisAligned<>(AetherBlocks.LOG_OAK_GOLDEN)
                .setTex(0, "aether:block/log/oak_golden_side", Side.NORTH, Side.SOUTH, Side.EAST, Side.WEST)
                .setTex(0, "aether:block/log/oak_golden_top", Side.TOP, Side.BOTTOM));

        ModelHelper.setBlockModel(AetherBlocks.LEAVES_SKYROOT, () -> new BlockModelLeaves<>(AetherBlocks.LEAVES_SKYROOT,"aether:block/leaves/skyroot", false));
        ModelHelper.setBlockModel(AetherBlocks.LEAVES_OAK_GOLDEN, () -> new BlockModelLeaves<>(AetherBlocks.LEAVES_OAK_GOLDEN,"aether:block/leaves/oak_golden", false));

        ModelHelper.setBlockModel(AetherBlocks.SAPLING_SKYROOT, () -> new BlockModelCrossedSquares<>(AetherBlocks.SAPLING_SKYROOT)
                .setAllTextures(0, "aether:block/sapling/skyroot"));
        ModelHelper.setBlockModel(AetherBlocks.SAPLING_OAK_GOLDEN, () -> new BlockModelCrossedSquares<>(AetherBlocks.SAPLING_OAK_GOLDEN)
                .setAllTextures(0, "aether:block/sapling/oak_golden"));


        ModelHelper.setBlockModel(AetherBlocks.AERCLOUD_WHITE, () -> new BlockModelTransparent<>(AetherBlocks.AERCLOUD_WHITE, false).onRenderLayer(1)
                .setAllTextures(0, "aether:block/aercloud_white"));
        ModelHelper.setBlockModel(AetherBlocks.AERCLOUD_BLUE, () -> new BlockModelTransparent<>(AetherBlocks.AERCLOUD_BLUE, false).onRenderLayer(1)
                .setAllTextures(0, "aether:block/aercloud_blue"));
        ModelHelper.setBlockModel(AetherBlocks.AERCLOUD_GOLD, () -> new BlockModelTransparent<>(AetherBlocks.AERCLOUD_GOLD, false).onRenderLayer(1)
                .setAllTextures(0, "aether:block/aercloud_gold"));

        ModelHelper.setBlockModel(AetherBlocks.AEROGEL, () -> new BlockModelTransparent<>(AetherBlocks.AEROGEL, false).onRenderLayer(1)
                .setAllTextures(0, "aether:block/aerogel"));


        ModelHelper.setBlockModel(AetherBlocks.ORE_AMBROSIUM_HOLYSTONE, () -> new BlockModelStandard<>(AetherBlocks.ORE_AMBROSIUM_HOLYSTONE)
                .setAllTextures(0, "aether:block/ore/ambrosium/holystone"));
        ModelHelper.setBlockModel(AetherBlocks.ORE_ZANITE_HOLYSTONE, () -> new BlockModelStandard<>(AetherBlocks.ORE_ZANITE_HOLYSTONE)
                .setAllTextures(0, "aether:block/ore/zanite/holystone"));
        ModelHelper.setBlockModel(AetherBlocks.ORE_GRAVITITE_HOLYSTONE, () -> new BlockModelStandard<>(AetherBlocks.ORE_GRAVITITE_HOLYSTONE)
                .setAllTextures(0, "aether:block/ore/gravitite/holystone"));

        ModelHelper.setBlockModel(AetherBlocks.BLOCK_AMBROSIUM, () -> new BlockModelStandard<>(AetherBlocks.BLOCK_AMBROSIUM)
                .setAllTextures(0, "aether:block/block_ambrosium"));
        ModelHelper.setBlockModel(AetherBlocks.BLOCK_ZANITE, () -> new BlockModelStandard<>(AetherBlocks.BLOCK_ZANITE)
                .setTex(0, "aether:block/block_zanite/side", Side.NORTH, Side.SOUTH, Side.EAST, Side.WEST)
                .setTex(0, "aether:block/block_zanite/top", Side.TOP)
                .setTex(0, "aether:block/block_zanite/bottom", Side.BOTTOM));
        ModelHelper.setBlockModel(AetherBlocks.BLOCK_GRAVITITE, () -> new BlockModelStandard<>(AetherBlocks.BLOCK_GRAVITITE)
                .setTex(0, "aether:block/block_gravitite/side", Side.NORTH, Side.SOUTH, Side.EAST, Side.WEST)
                .setTex(0, "aether:block/block_gravitite/top", Side.TOP)
                .setTex(0, "aether:block/block_gravitite/bottom", Side.BOTTOM));

        ModelHelper.setBlockModel(AetherBlocks.BRICK_ZANITE, () -> new BlockModelStandard<>(AetherBlocks.BRICK_ZANITE)
                .setAllTextures(0, "aether:block/brick_zanite"));


        ModelHelper.setBlockModel(AetherBlocks.CARVED_STONE, () -> new BlockModelStandard<>(AetherBlocks.CARVED_STONE)
                .setAllTextures(0, "aether:block/carved"));
        ModelHelper.setBlockModel(AetherBlocks.CARVED_STONE_LIGHT, () -> new BlockModelStandard<>(AetherBlocks.CARVED_STONE_LIGHT)
                .setAllTextures(0, "aether:block/carved_glow")
                .setAllTextures(1, "aether:block/carved_overlay"));

        ModelHelper.setBlockModel(AetherBlocks.CARVED_ANGELIC, () -> new BlockModelStandard<>(AetherBlocks.CARVED_ANGELIC)
                .setAllTextures(0, "aether:block/angelic"));
        ModelHelper.setBlockModel(AetherBlocks.CARVED_ANGELIC_LIGHT, () -> new BlockModelStandard<>(AetherBlocks.CARVED_ANGELIC_LIGHT)
                .setAllTextures(0, "aether:block/angelic_glow")
                .setAllTextures(1, "aether:block/angelic_overlay"));

        ModelHelper.setBlockModel(AetherBlocks.CARVED_HELLFIRE, () -> new BlockModelStandard<>(AetherBlocks.CARVED_HELLFIRE)
                .setAllTextures(0, "aether:block/hellfire"));
        ModelHelper.setBlockModel(AetherBlocks.CARVED_HELLFIRE_LIGHT, () -> new BlockModelStandard<>(AetherBlocks.CARVED_HELLFIRE_LIGHT)
                .setAllTextures(0, "aether:block/hellfire_glow")
                .setAllTextures(1, "aether:block/hellfire_overlay"));


        ModelHelper.setBlockModel(AetherBlocks.PILLAR, () -> new BlockModelAxisAligned<>(AetherBlocks.PILLAR)
                .setTex(0, "aether:block/pillar/side", Side.NORTH, Side.SOUTH, Side.EAST, Side.WEST)
                .setTex(0, "aether:block/pillar/top", Side.TOP, Side.BOTTOM));

        ModelHelper.setBlockModel(AetherBlocks.PILLAR_CAPSTONE, () -> new BlockModelAxisAligned<>(AetherBlocks.PILLAR_CAPSTONE)
                .setTex(0, "aether:block/pillar_capstone/side", Side.NORTH, Side.SOUTH, Side.EAST, Side.WEST)
                .setTex(0, "aether:block/pillar_capstone/top", Side.TOP, Side.BOTTOM));


        ModelHelper.setBlockModel(AetherBlocks.CHEST_DUNGEON, () -> new BlockModelHorizontalRotation<>(AetherBlocks.CHEST_DUNGEON)
                .setTex(0, "aether:block/chest/dungeon/front", Side.NORTH)
                .setTex(0, "aether:block/chest/dungeon/side", Side.SOUTH, Side.EAST, Side.WEST)
                .setTex(0, "aether:block/chest/dungeon/top", Side.TOP, Side.BOTTOM));

        ModelHelper.setBlockModel(AetherBlocks.CHEST_DUNGEON_LOCKED, () -> new BlockModelHorizontalRotation<>(AetherBlocks.CHEST_DUNGEON_LOCKED)
                .setTex(0, "aether:block/chest/dungeon/front", Side.NORTH)
                .setTex(0, "aether:block/chest/dungeon/side", Side.SOUTH, Side.EAST, Side.WEST)
                .setTex(0, "aether:block/chest/dungeon/top", Side.TOP, Side.BOTTOM));


        ModelHelper.setBlockModel(AetherBlocks.CARVED_STONE_LOCKED, () -> new BlockModelStandard<>(AetherBlocks.CARVED_STONE_LOCKED)
                .setAllTextures(0, "aether:block/carved"));
        ModelHelper.setBlockModel(AetherBlocks.CARVED_STONE_LIGHT_LOCKED, () -> new BlockModelStandard<>(AetherBlocks.CARVED_STONE_LIGHT_LOCKED)
                .setAllTextures(0, "aether:block/carved_glow")
                .setAllTextures(1, "aether:block/carved_overlay"));

        ModelHelper.setBlockModel(AetherBlocks.CARVED_ANGELIC_LOCKED, () -> new BlockModelStandard<>(AetherBlocks.CARVED_ANGELIC_LOCKED)
                .setAllTextures(0, "aether:block/angelic"));
        ModelHelper.setBlockModel(AetherBlocks.CARVED_ANGELIC_LIGHT_LOCKED, () -> new BlockModelStandard<>(AetherBlocks.CARVED_ANGELIC_LIGHT_LOCKED)
                .setAllTextures(0, "aether:block/angelic_glow")
                .setAllTextures(1, "aether:block/angelic_overlay"));

        ModelHelper.setBlockModel(AetherBlocks.CARVED_HELLFIRE_LOCKED, () -> new BlockModelStandard<>(AetherBlocks.CARVED_HELLFIRE_LOCKED)
                .setAllTextures(0, "aether:block/hellfire"));
        ModelHelper.setBlockModel(AetherBlocks.CARVED_HELLFIRE_LIGHT_LOCKED, () -> new BlockModelStandard<>(AetherBlocks.CARVED_HELLFIRE_LIGHT_LOCKED)
                .setAllTextures(0, "aether:block/hellfire_glow")
                .setAllTextures(1, "aether:block/hellfire_overlay"));

        ModelHelper.setBlockModel(AetherBlocks.CARVED_STONE_TRAPPED, () -> new BlockModelStandard<>(AetherBlocks.CARVED_STONE_TRAPPED)
                .setAllTextures(0, "aether:block/carved"));
        ModelHelper.setBlockModel(AetherBlocks.CARVED_ANGELIC_TRAPPED, () -> new BlockModelStandard<>(AetherBlocks.CARVED_ANGELIC_TRAPPED)
                .setAllTextures(0, "aether:block/angelic"));

        ModelHelper.setBlockModel(AetherBlocks.CHEST_MIMIC, () -> new BlockModelHorizontalRotation<>(AetherBlocks.CHEST_MIMIC)
                .setTex(0, "aether:block/chest/skyroot/front", Side.NORTH)
                .setTex(0, "aether:block/chest/skyroot/side", Side.SOUTH, Side.EAST, Side.WEST)
                .setTex(0, "aether:block/chest/skyroot/top", Side.TOP, Side.BOTTOM));



        ModelHelper.setBlockModel(AetherBlocks.STAIRS_COBBLE_HOLYSTONE, () -> new BlockModelStairs<>(AetherBlocks.STAIRS_COBBLE_HOLYSTONE));
        ModelHelper.setBlockModel(AetherBlocks.STAIRS_BRICK_HOLYSTONE, () -> new BlockModelStairs<>(AetherBlocks.STAIRS_BRICK_HOLYSTONE));
        ModelHelper.setBlockModel(AetherBlocks.STAIRS_CARVED_STONE, () -> new BlockModelStairs<>(AetherBlocks.STAIRS_CARVED_STONE));
        ModelHelper.setBlockModel(AetherBlocks.STAIRS_CARVED_ANGELIC, () -> new BlockModelStairs<>(AetherBlocks.STAIRS_CARVED_ANGELIC));
        ModelHelper.setBlockModel(AetherBlocks.STAIRS_CARVED_HELLFIRE, () -> new BlockModelStairs<>(AetherBlocks.STAIRS_CARVED_HELLFIRE));
        ModelHelper.setBlockModel(AetherBlocks.STAIRS_PLANKS_SKYROOT, () -> new BlockModelStairs<>(AetherBlocks.STAIRS_PLANKS_SKYROOT));
        ModelHelper.setBlockModel(AetherBlocks.STAIRS_BRICK_ZANITE, () -> new BlockModelStairs<>(AetherBlocks.STAIRS_BRICK_ZANITE));

        ModelHelper.setBlockModel(AetherBlocks.SLAB_COBBLE_HOLYSTONE, () -> new BlockModelSlab<>(AetherBlocks.SLAB_COBBLE_HOLYSTONE));
        ModelHelper.setBlockModel(AetherBlocks.SLAB_BRICK_HOLYSTONE, () -> new BlockModelSlab<>(AetherBlocks.SLAB_BRICK_HOLYSTONE));
        ModelHelper.setBlockModel(AetherBlocks.SLAB_HOLYSTONE_POLISHED, () -> new BlockModelSlab<>(AetherBlocks.SLAB_HOLYSTONE_POLISHED));
        ModelHelper.setBlockModel(AetherBlocks.SLAB_CARVED_STONE, () -> new BlockModelSlab<>(AetherBlocks.SLAB_CARVED_STONE));
        ModelHelper.setBlockModel(AetherBlocks.SLAB_CARVED_ANGELIC, () -> new BlockModelSlab<>(AetherBlocks.SLAB_CARVED_ANGELIC));
        ModelHelper.setBlockModel(AetherBlocks.SLAB_CARVED_HELLFIRE, () -> new BlockModelSlab<>(AetherBlocks.SLAB_CARVED_HELLFIRE));
        ModelHelper.setBlockModel(AetherBlocks.SLAB_PLANKS_SKYROOT, () -> new BlockModelSlab<>(AetherBlocks.SLAB_PLANKS_SKYROOT));
        ModelHelper.setBlockModel(AetherBlocks.SLAB_BRICK_ZANITE, () -> new BlockModelSlab<>(AetherBlocks.SLAB_BRICK_ZANITE));


        ModelHelper.setBlockModel(AetherBlocks.FENCE_PLANKS_SKYROOT, () -> new BlockModelFence<>(AetherBlocks.FENCE_PLANKS_SKYROOT)
                .setAllTextures(0, "aether:block/skyroot"));
        ModelHelper.setBlockModel(AetherBlocks.FENCEGATE_PLANKS_SKYROOT, () -> new BlockModelFenceGate<>(AetherBlocks.FENCEGATE_PLANKS_SKYROOT)
                .setAllTextures(0, "aether:block/skyroot"));


        ModelHelper.setBlockModel(AetherBlocks.TORCH_AMBROSIUM, () -> new BlockModelTorch<>(AetherBlocks.TORCH_AMBROSIUM)
                .setAllTextures(0, "aether:block/torch_ambrosium"));

        ModelHelper.setBlockModel(AetherBlocks.LANTERN_FIREFLY_SILVER, () -> new BlockModelLantern<>(AetherBlocks.LANTERN_FIREFLY_SILVER)
                .setAllTextures(0, "aether:block/lantern_firefly_silver"));

    }

    @Override
    public void initItemModels(ItemModelDispatcher dispatcher) {

        ModelHelper.setItemModel(AetherItems.MEDAL_VICTORY, () -> new ItemModelStandard(AetherItems.MEDAL_VICTORY, null).setIcon("aether:item/medal_victory"));

        ModelHelper.setItemModel(AetherItems.KEY_BRONZE, () -> new ItemModelStandard(AetherItems.KEY_BRONZE, null).setIcon("aether:item/key_bronze"));
        ModelHelper.setItemModel(AetherItems.KEY_SILVER, () -> new ItemModelStandard(AetherItems.KEY_SILVER, null).setIcon("aether:item/key_silver"));
        ModelHelper.setItemModel(AetherItems.KEY_GOLD, () -> new ItemModelStandard(AetherItems.KEY_GOLD, null).setIcon("aether:item/key_gold"));

        ModelHelper.setItemModel(AetherItems.EGG_MOA_BLUE, () -> new ItemModelStandard(AetherItems.EGG_MOA_BLUE, null).setIcon("aether:item/egg_moa_blue"));
        ModelHelper.setItemModel(AetherItems.EGG_MOA_WHITE, () -> new ItemModelStandard(AetherItems.EGG_MOA_WHITE, null).setIcon("aether:item/egg_moa_white"));
        ModelHelper.setItemModel(AetherItems.EGG_MOA_BLACK, () -> new ItemModelStandard(AetherItems.EGG_MOA_BLACK, null).setIcon("aether:item/egg_moa_black"));

        ModelHelper.setItemModel(AetherItems.RECORD_AETHER, () -> new ItemModelStandard(AetherItems.RECORD_AETHER, null).setIcon("aether:item/record_aether"));
        ModelHelper.setItemModel(AetherItems.RECORD_MORNING, () -> new ItemModelStandard(AetherItems.RECORD_MORNING, null).setIcon("aether:item/record_morning"));
        ModelHelper.setItemModel(AetherItems.RECORD_DAWN, () -> new ItemModelStandard(AetherItems.RECORD_DAWN, null).setIcon("aether:item/record_dawn"));

        ModelHelper.setItemModel(AetherItems.AMBER, () -> new ItemModelStandard(AetherItems.AMBER, null).setIcon("aether:item/amber"));
        ModelHelper.setItemModel(AetherItems.PETAL_AECHOR, () -> new ItemModelStandard(AetherItems.PETAL_AECHOR, null).setIcon("aether:item/petal_aechor"));
        ModelHelper.setItemModel(AetherItems.STICK_SKYROOT, () -> new ItemModelStandard(AetherItems.STICK_SKYROOT, null).setIcon("aether:item/stick_skyroot"));


        ModelHelper.setItemModel(AetherItems.AMBROSIUM, () -> new ItemModelStandard(AetherItems.AMBROSIUM, null).setIcon("aether:item/ambrosium"));
        ModelHelper.setItemModel(AetherItems.ZANITE, () -> new ItemModelStandard(AetherItems.ZANITE, null).setIcon("aether:item/zanite"));

        ModelHelper.setItemModel(AetherItems.BUCKET_SKYROOT, () -> new ItemModelStandard(AetherItems.BUCKET_SKYROOT, null).setIcon("aether:item/bucket_skyroot"));
        ModelHelper.setItemModel(AetherItems.BUCKET_SKYROOT_WATER, () -> new ItemModelStandard(AetherItems.BUCKET_SKYROOT_WATER, null).setIcon("aether:item/bucket_skyroot_water"));
        ModelHelper.setItemModel(AetherItems.BUCKET_SKYROOT_MILK, () -> new ItemModelStandard(AetherItems.BUCKET_SKYROOT_MILK, null).setIcon("aether:item/bucket_skyroot_milk"));
        ModelHelper.setItemModel(AetherItems.BUCKET_SKYROOT_POISON, () -> new ItemModelStandard(AetherItems.BUCKET_SKYROOT_POISON, null).setIcon("aether:item/bucket_skyroot_poison"));
        ModelHelper.setItemModel(AetherItems.BUCKET_SKYROOT_REMEDY, () -> new ItemModelStandard(AetherItems.BUCKET_SKYROOT_REMEDY, null).setIcon("aether:item/bucket_skyroot_remedy"));
        ModelHelper.setItemModel(AetherItems.BUCKET_SKYROOT_ICECREAM, () -> new ItemModelStandard(AetherItems.BUCKET_SKYROOT_ICECREAM, null).setIcon("aether:item/bucket_skyroot_icecream"));



        ModelHelper.setItemModel(AetherItems.TOOL_SWORD_SKYROOT, () -> new ItemModelStandard(AetherItems.TOOL_SWORD_SKYROOT, null).setIcon("aether:item/tool_sword_skyroot").setFull3D());
        ModelHelper.setItemModel(AetherItems.TOOL_SHOVEL_SKYROOT, () -> new ItemModelStandard(AetherItems.TOOL_SHOVEL_SKYROOT, null).setIcon("aether:item/tool_shovel_skyroot").setFull3D());
        ModelHelper.setItemModel(AetherItems.TOOL_PICKAXE_SKYROOT, () -> new ItemModelStandard(AetherItems.TOOL_PICKAXE_SKYROOT, null).setIcon("aether:item/tool_pickaxe_skyroot").setFull3D());
        ModelHelper.setItemModel(AetherItems.TOOL_AXE_SKYROOT, () -> new ItemModelStandard(AetherItems.TOOL_AXE_SKYROOT, null).setIcon("aether:item/tool_axe_skyroot").setFull3D());

        ModelHelper.setItemModel(AetherItems.TOOL_SWORD_HOLYSTONE, () -> new ItemModelStandard(AetherItems.TOOL_SWORD_HOLYSTONE, null).setIcon("aether:item/tool_sword_holystone").setFull3D());
        ModelHelper.setItemModel(AetherItems.TOOL_SHOVEL_HOLYSTONE, () -> new ItemModelStandard(AetherItems.TOOL_SHOVEL_HOLYSTONE, null).setIcon("aether:item/tool_shovel_holystone").setFull3D());
        ModelHelper.setItemModel(AetherItems.TOOL_PICKAXE_HOLYSTONE, () -> new ItemModelStandard(AetherItems.TOOL_PICKAXE_HOLYSTONE, null).setIcon("aether:item/tool_pickaxe_holystone").setFull3D());
        ModelHelper.setItemModel(AetherItems.TOOL_AXE_HOLYSTONE, () -> new ItemModelStandard(AetherItems.TOOL_AXE_HOLYSTONE, null).setIcon("aether:item/tool_axe_holystone").setFull3D());

        ModelHelper.setItemModel(AetherItems.TOOL_SWORD_ZANITE, () -> new ItemModelStandard(AetherItems.TOOL_SWORD_ZANITE, null).setIcon("aether:item/tool_sword_zanite").setFull3D());
        ModelHelper.setItemModel(AetherItems.TOOL_SHOVEL_ZANITE, () -> new ItemModelStandard(AetherItems.TOOL_SHOVEL_ZANITE, null).setIcon("aether:item/tool_shovel_zanite").setFull3D());
        ModelHelper.setItemModel(AetherItems.TOOL_PICKAXE_ZANITE, () -> new ItemModelStandard(AetherItems.TOOL_PICKAXE_ZANITE, null).setIcon("aether:item/tool_pickaxe_zanite").setFull3D());
        ModelHelper.setItemModel(AetherItems.TOOL_AXE_ZANITE, () -> new ItemModelStandard(AetherItems.TOOL_AXE_ZANITE, null).setIcon("aether:item/tool_axe_zanite").setFull3D());

        ModelHelper.setItemModel(AetherItems.TOOL_SWORD_GRAVITITE, () -> new ItemModelStandard(AetherItems.TOOL_SWORD_GRAVITITE, null).setIcon("aether:item/tool_sword_gravitite").setFull3D());
        ModelHelper.setItemModel(AetherItems.TOOL_SHOVEL_GRAVITITE, () -> new ItemModelStandard(AetherItems.TOOL_SHOVEL_GRAVITITE, null).setIcon("aether:item/tool_shovel_gravitite").setFull3D());
        ModelHelper.setItemModel(AetherItems.TOOL_PICKAXE_GRAVITITE, () -> new ItemModelStandard(AetherItems.TOOL_PICKAXE_GRAVITITE, null).setIcon("aether:item/tool_pickaxe_gravitite").setFull3D());
        ModelHelper.setItemModel(AetherItems.TOOL_AXE_GRAVITITE, () -> new ItemModelStandard(AetherItems.TOOL_AXE_GRAVITITE, null).setIcon("aether:item/tool_axe_gravitite").setFull3D());

        ModelHelper.setItemModel(AetherItems.TOOL_SWORD_VALKYRIE, () -> new ItemModelStandard(AetherItems.TOOL_SWORD_VALKYRIE, null).setIcon("aether:item/tool_sword_valk"));
        ModelHelper.setItemModel(AetherItems.TOOL_SHOVEL_VALKYRIE, () -> new ItemModelStandard(AetherItems.TOOL_SHOVEL_VALKYRIE, null).setIcon("aether:item/tool_shovel_valk").setFull3D());
        ModelHelper.setItemModel(AetherItems.TOOL_PICKAXE_VALKYRIE, () -> new ItemModelStandard(AetherItems.TOOL_PICKAXE_VALKYRIE, null).setIcon("aether:item/tool_pickaxe_valk").setFull3D());
        ModelHelper.setItemModel(AetherItems.TOOL_AXE_VALKYRIE, () -> new ItemModelStandard(AetherItems.TOOL_AXE_VALKYRIE, null).setIcon("aether:item/tool_axe_valk").setFull3D());

        ModelHelper.setItemModel(AetherItems.TOOL_KNIFE_LIGHTNING, () -> new ItemModelStandard(AetherItems.TOOL_KNIFE_LIGHTNING, null).setIcon("aether:item/tool_knife_lightning").setFull3D());
        ModelHelper.setItemModel(AetherItems.TOOL_HAMMER_NOTCH, () -> new ItemModelStandard(AetherItems.TOOL_HAMMER_NOTCH, null).setIcon("aether:item/tool_hammer_notch").setFull3D());
        ModelHelper.setItemModel(AetherItems.AMMO_HAMMER_HEAD, () -> new ItemModelStandard(AetherItems.AMMO_HAMMER_HEAD, null).setIcon("aether:item/notch_wave"));


        ModelHelper.setItemModel(AetherItems.TOOL_BOW_PHOENIX, () -> new ItemModelBow(AetherItems.TOOL_BOW_PHOENIX, null).setIcon("aether:item/tool_bow_phoenix"));

        ModelHelper.setItemModel(AetherItems.AMMO_DART_GOLDEN, () -> new ItemModelStandard(AetherItems.AMMO_DART_GOLDEN, null).setIcon("aether:item/dart_golden"));
        ModelHelper.setItemModel(AetherItems.AMMO_DART_POISON, () -> new ItemModelStandard(AetherItems.AMMO_DART_POISON, null).setIcon("aether:item/dart_poison"));
        ModelHelper.setItemModel(AetherItems.AMMO_DART_ENCHANTED, () -> new ItemModelStandard(AetherItems.AMMO_DART_ENCHANTED, null).setIcon("aether:item/dart_enchanted"));

        ModelHelper.setItemModel(AetherItems.TOOL_SHOOTER, () -> new ItemModelStandard(AetherItems.TOOL_SHOOTER, null).setIcon("aether:item/shooter_gold"));

        ModelHelper.setItemModel(AetherItems.TOOL_SWORD_PIG, () -> new ItemModelStandard(AetherItems.TOOL_SWORD_PIG, null).setIcon("aether:item/tool_knife_pig").setFull3D());
        ModelHelper.setItemModel(AetherItems.TOOL_SWORD_VAMPIRE, () -> new ItemModelStandard(AetherItems.TOOL_SWORD_VAMPIRE, null).setIcon("aether:item/tool_sword_vampire").setFull3D());

        ModelHelper.setItemModel(AetherItems.TOOL_SWORD_FLAME, () -> new ItemModelStandard(AetherItems.TOOL_SWORD_FLAME, null).setIcon("aether:item/tool_sword_element_fire").setFull3D());
        ModelHelper.setItemModel(AetherItems.TOOL_SWORD_HOLY, () -> new ItemModelStandard(AetherItems.TOOL_SWORD_HOLY, null).setIcon("aether:item/tool_sword_element_holy").setFull3D());
        ModelHelper.setItemModel(AetherItems.TOOL_SWORD_LIGHTNING, () -> new ItemModelStandard(AetherItems.TOOL_SWORD_LIGHTNING, null).setIcon("aether:item/tool_sword_element_lightning").setFull3D());

        ModelHelper.setItemModel(AetherItems.TOOL_STAFF_NATURE, () -> new ItemModelStandard(AetherItems.TOOL_STAFF_NATURE, null).setIcon("aether:item/staff_nature").setFull3D());
        ModelHelper.setItemModel(AetherItems.TOOL_STAFF_CLOUD, () -> new ItemModelStandard(AetherItems.TOOL_STAFF_CLOUD, null).setIcon("aether:item/staff_cloud").setFull3D());


        ModelHelper.setItemModel(AetherItems.ARMOR_HELMET_ZANITE, () -> new ItemModelStandard(AetherItems.ARMOR_HELMET_ZANITE, null).setIcon("aether:item/armor_helmet_zanite"));
        ModelHelper.setItemModel(AetherItems.ARMOR_CHESTPLATE_ZANITE, () -> new ItemModelStandard(AetherItems.ARMOR_CHESTPLATE_ZANITE, null).setIcon("aether:item/armor_chestplate_zanite"));
        ModelHelper.setItemModel(AetherItems.ARMOR_LEGGINGS_ZANITE, () -> new ItemModelStandard(AetherItems.ARMOR_LEGGINGS_ZANITE, null).setIcon("aether:item/armor_leggings_zanite"));
        ModelHelper.setItemModel(AetherItems.ARMOR_BOOTS_ZANITE, () -> new ItemModelStandard(AetherItems.ARMOR_BOOTS_ZANITE, null).setIcon("aether:item/armor_boots_zanite"));

        ModelHelper.setItemModel(AetherItems.ARMOR_HELMET_GRAVITITE, () -> new ItemModelStandard(AetherItems.ARMOR_HELMET_GRAVITITE, null).setIcon("aether:item/armor_helmet_gravitite"));
        ModelHelper.setItemModel(AetherItems.ARMOR_CHESTPLATE_GRAVITITE, () -> new ItemModelStandard(AetherItems.ARMOR_CHESTPLATE_GRAVITITE, null).setIcon("aether:item/armor_chestplate_gravitite"));
        ModelHelper.setItemModel(AetherItems.ARMOR_LEGGINGS_GRAVITITE, () -> new ItemModelStandard(AetherItems.ARMOR_LEGGINGS_GRAVITITE, null).setIcon("aether:item/armor_leggings_gravitite"));
        ModelHelper.setItemModel(AetherItems.ARMOR_BOOTS_GRAVITITE, () -> new ItemModelStandard(AetherItems.ARMOR_BOOTS_GRAVITITE, null).setIcon("aether:item/armor_boots_gravitite"));

        ModelHelper.setItemModel(AetherItems.ARMOR_HELMET_OBSIDIAN, () -> new ItemModelStandard(AetherItems.ARMOR_HELMET_OBSIDIAN, null).setIcon("aether:item/armor_helmet_obsidian"));
        ModelHelper.setItemModel(AetherItems.ARMOR_CHESTPLATE_OBSIDIAN, () -> new ItemModelStandard(AetherItems.ARMOR_CHESTPLATE_OBSIDIAN, null).setIcon("aether:item/armor_chestplate_obsidian"));
        ModelHelper.setItemModel(AetherItems.ARMOR_LEGGINGS_OBSIDIAN, () -> new ItemModelStandard(AetherItems.ARMOR_LEGGINGS_OBSIDIAN, null).setIcon("aether:item/armor_leggings_obsidian"));
        ModelHelper.setItemModel(AetherItems.ARMOR_BOOTS_OBSIDIAN, () -> new ItemModelStandard(AetherItems.ARMOR_BOOTS_OBSIDIAN, null).setIcon("aether:item/armor_boots_obsidian"));

        ModelHelper.setItemModel(AetherItems.ARMOR_HELMET_PHOENIX, () -> new ItemModelStandard(AetherItems.ARMOR_HELMET_PHOENIX, null).setIcon("aether:item/armor_helmet_phoenix"));
        ModelHelper.setItemModel(AetherItems.ARMOR_CHESTPLATE_PHOENIX, () -> new ItemModelStandard(AetherItems.ARMOR_CHESTPLATE_PHOENIX, null).setIcon("aether:item/armor_chestplate_phoenix"));
        ModelHelper.setItemModel(AetherItems.ARMOR_LEGGINGS_PHOENIX, () -> new ItemModelStandard(AetherItems.ARMOR_LEGGINGS_PHOENIX, null).setIcon("aether:item/armor_leggings_phoenix"));
        ModelHelper.setItemModel(AetherItems.ARMOR_BOOTS_PHOENIX, () -> new ItemModelStandard(AetherItems.ARMOR_BOOTS_PHOENIX, null).setIcon("aether:item/armor_boots_phoenix"));

        ModelHelper.setItemModel(AetherItems.ARMOR_HELMET_NEPTUNE, () -> new ItemModelStandard(AetherItems.ARMOR_HELMET_NEPTUNE, null).setIcon("aether:item/armor_helmet_neptune"));
        ModelHelper.setItemModel(AetherItems.ARMOR_CHESTPLATE_NEPTUNE, () -> new ItemModelStandard(AetherItems.ARMOR_CHESTPLATE_NEPTUNE, null).setIcon("aether:item/armor_chestplate_neptune"));
        ModelHelper.setItemModel(AetherItems.ARMOR_LEGGINGS_NEPTUNE, () -> new ItemModelStandard(AetherItems.ARMOR_LEGGINGS_NEPTUNE, null).setIcon("aether:item/armor_leggings_neptune"));
        ModelHelper.setItemModel(AetherItems.ARMOR_BOOTS_NEPTUNE, () -> new ItemModelStandard(AetherItems.ARMOR_BOOTS_NEPTUNE, null).setIcon("aether:item/armor_boots_neptune"));


        ModelHelper.setItemModel(AetherItems.FOOD_HEALING_STONE, () -> new ItemModelStandard(AetherItems.FOOD_HEALING_STONE, null).setIcon("aether:item/food_healing_stone"));

        ModelHelper.setItemModel(AetherItems.FOOD_GUMMY_BLUE, () -> new ItemModelStandard(AetherItems.FOOD_GUMMY_BLUE, null).setIcon("aether:item/food_sweet_gold"));
        ModelHelper.setItemModel(AetherItems.FOOD_GUMMY_GOLD, () -> new ItemModelStandard(AetherItems.FOOD_GUMMY_GOLD, null).setIcon("aether:item/food_sweet_blue"));

        ModelHelper.setItemModel(AetherItems.LIFESHARD, () -> new ItemModelStandard(AetherItems.LIFESHARD, null).setIcon("aether:item/food_lifeshard"));

        ModelHelper.setItemModel(AetherItems.PARACHUTE_CLOUD, () -> new ItemModelStandard(AetherItems.PARACHUTE_CLOUD, null).setIcon("aether:item/parachute"));
        ModelHelper.setItemModel(AetherItems.PARACHUTE_CLOUD_GOLD, () -> new ItemModelStandard(AetherItems.PARACHUTE_CLOUD_GOLD, null).setIcon("aether:item/parachute_gold"));


        ModelHelper.setItemModel(AetherItems.LANTERN_FIREFLY_SILVER, () -> new ItemModelStandard(AetherItems.LANTERN_FIREFLY_SILVER, null).setIcon("aether:item/lantern_firefly_silver"));
        ModelHelper.setItemModel(AetherItems.DOOR_SKYROOT, () -> new ItemModelStandard(AetherItems.DOOR_SKYROOT, null).setIcon("aether:item/door_skyroot"));
        ModelHelper.setItemModel(AetherItems.DOOR_GLASS_AMBROSIUM, () -> new ItemModelStandard(AetherItems.DOOR_GLASS_AMBROSIUM, null).setIcon("aether:item/door_glass_ambrosium"));

    }

    @Override
    public void initEntityModels(EntityRenderDispatcher dispatcher) {

        ModelHelper.setEntityModel(ProjectileHammerHead.class, () -> new EntityRendererSprite<>(AetherItems.AMMO_HAMMER_HEAD));

    }

    @Override
    public void initTileEntityModels(TileEntityRenderDispatcher dispatcher) {
    }

    @Override
    public void initBlockColors(BlockColorDispatcher dispatcher) {
    }
}
