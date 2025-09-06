package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.*;
import net.minecraft.client.render.entity.EntityRendererFallingBlock;
import net.minecraft.client.render.entity.EntityRendererSprite;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.item.model.ItemModelBlock;
import net.minecraft.client.render.item.model.ItemModelBow;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.model.ModelSlime;
import net.minecraft.core.item.block.ItemBlock;
import net.minecraft.core.util.helper.Side;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.entity.EntityFloatingBlock;
import teamport.aether.entity.animal.aerbunny.MobAerbunny;
import teamport.aether.entity.animal.aerbunny.MobRendererAerbunny;
import teamport.aether.entity.animal.aerbunny.ModelAerbunny;
import teamport.aether.entity.animal.aerwhale.MobAerwhale;
import teamport.aether.entity.animal.aerwhale.MobRendererAerwhale;
import teamport.aether.entity.animal.aerwhale.ModelAerwhale;
import teamport.aether.entity.animal.moa.MobMoaBlue;
import teamport.aether.entity.animal.moa.MobRendererMoa;
import teamport.aether.entity.animal.moa.ModelMoa;
import teamport.aether.entity.animal.phow.MobPhow;
import teamport.aether.entity.animal.phow.MobRendererPhow;
import teamport.aether.entity.animal.phow.ModelPhow;
import teamport.aether.entity.animal.phyg.MobPhyg;
import teamport.aether.entity.animal.phyg.MobRendererPhyg;
import teamport.aether.entity.animal.phyg.ModelPhyg;
import teamport.aether.entity.animal.sheepuff.*;
import teamport.aether.entity.boss.slider.MobBossSlider;
import teamport.aether.entity.boss.slider.MobRendererSlider;
import teamport.aether.entity.boss.slider.ModelSlider;
import teamport.aether.entity.boss.sunspirit.MobBossSunspirit;
import teamport.aether.entity.boss.sunspirit.MobRendererSunspirit;
import teamport.aether.entity.boss.valkyrie.queen.MobBossValkyrie;
import teamport.aether.entity.boss.valkyrie.queen.MobRendererValkyrieBoss;
import teamport.aether.entity.monster.aechorplant.MobAechorPlant;
import teamport.aether.entity.monster.aechorplant.MobRendererAechorPlant;
import teamport.aether.entity.monster.aechorplant.ModelAechorPlant;
import teamport.aether.entity.monster.cockatrice.MobCockatrice;
import teamport.aether.entity.monster.cockatrice.MobRendererCockatrice;
import teamport.aether.entity.monster.fireminion.MobFireMinion;
import teamport.aether.entity.monster.fireminion.MobRendererFireMinion;
import teamport.aether.entity.monster.mimic.MobMimic;
import teamport.aether.entity.monster.mimic.MobRendererMimic;
import teamport.aether.entity.monster.sentry.MobRendererSentry;
import teamport.aether.entity.monster.sentry.MobSentry;
import teamport.aether.entity.monster.swet.MobRendererSwet;
import teamport.aether.entity.monster.swet.MobSwet;
import teamport.aether.entity.monster.swet.MobSwetGold;
import teamport.aether.entity.monster.valkyrie.MobRendererValkyrie;
import teamport.aether.entity.monster.valkyrie.MobValkyrie;
import teamport.aether.entity.monster.valkyrie.ModelValkyrie;
import teamport.aether.entity.monster.whirly.MobRendererWhirly;
import teamport.aether.entity.monster.whirly.MobWhirly;
import teamport.aether.entity.monster.zephyr.MobRendererZephyr;
import teamport.aether.entity.monster.zephyr.MobZephyr;
import teamport.aether.entity.projectile.*;
import teamport.aether.entity.renderer.EntityRendererArrowFlaming;
import teamport.aether.entity.renderer.EntityRendererDart;
import teamport.aether.entity.renderer.EntityRendererKnifeLightning;
import teamport.aether.entity.renderer.EntityRendererNeedle;
import teamport.aether.entity.vehicle.minicloud.MobMinicloud;
import teamport.aether.entity.vehicle.minicloud.ModelMinicloud;
import teamport.aether.entity.vehicle.parachute.EntityParachute;
import teamport.aether.entity.vehicle.parachute.EntityParachuteGold;
import teamport.aether.entity.vehicle.parachute.EntityRendererParachute;
import teamport.aether.entity.vehicle.parachute.EntityRendererParachuteGold;
import teamport.aether.items.AetherItems;
import turniplabs.halplibe.helper.ModelHelper;
import turniplabs.halplibe.util.ModelEntrypoint;

@Environment(EnvType.CLIENT)
public class AetherModels implements ModelEntrypoint {

    @Override
    public void initBlockModels(BlockModelDispatcher dispatcher) {

        dispatcher.addDispatch(new BlockModelPortal<>(AetherBlocks.PORTAL_AETHER, "aether:block/portal_aether/")
                .setAllTextures(0, "aether:block/portal_aether"));


        dispatcher.addDispatch(new BlockModelGrassAether<>(AetherBlocks.GRASS_AETHER)
                .setTex(0, "aether:block/grass_aether/top", Side.TOP)
                .setTex(0, "aether:block/grass_aether/bottom", Side.BOTTOM)
                .setTex(0, "aether:block/grass_aether/side", Side.EAST, Side.WEST, Side.NORTH, Side.SOUTH));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.DIRT_AETHER)
                .setAllTextures(0, "aether:block/dirt_aether"));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.PATH_DIRT_AETHER)
                .setTex(0, "aether:block/grass_path_aether/top", Side.TOP)
                .setTex(0, "aether:block/grass_path_aether/bottom", Side.BOTTOM)
                .setTex(0, "aether:block/grass_path_aether/side", Side.EAST, Side.WEST, Side.NORTH, Side.SOUTH));


        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.HOLYSTONE)
                .setAllTextures(0, "aether:block/holystone"));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.HOLYSTONE_MOSSY)
                .setAllTextures(0, "aether:block/holystone_mossy"));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.HOLYSTONE_POLISHED)
                .setTex(0, "aether:block/polished_holystone_side", Side.sides)
                .setTex(0, "aether:block/polished_holystone_top", Side.TOP, Side.BOTTOM));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.HOLYSTONE_CARVED)
                .setTex(0, "aether:block/carved_holystone", Side.sides)
                .setTex(0, "aether:block/polished_holystone_top", Side.TOP, Side.BOTTOM));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.COBBLE_HOLYSTONE)
                .setAllTextures(0, "aether:block/cobbled_holystone"));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.COBBLE_HOLYSTONE_MOSSY)
                .setAllTextures(0, "aether:block/cobbled_holystone_mossy"));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.BRICK_HOLYSTONE)
                .setAllTextures(0, "aether:block/brick_holystone"));


        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.ICESTONE)
                .setAllTextures(0, "aether:block/icestone"));


        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.QUICKSOIL)
                .setAllTextures(0, "aether:block/quicksoil"));

        dispatcher.addDispatch(new BlockModelTransparent<>(AetherBlocks.GLASS_QUICKSOIL, false).onRenderLayer(1)
                .setAllTextures(0, "aether:block/glass_quicksoil"));

        dispatcher.addDispatch(new BlockModelDoorGlass<>(AetherBlocks.DOOR_GLASS_QUICKSOIL_BOTTOM).onRenderLayer(1)
                .setTex(0, "aether:block/door/glass_quicksoil/frame", Side.TOP, Side.BOTTOM)
                .setTex(0, "aether:block/door/glass_quicksoil/bottom", Side.sides));

        dispatcher.addDispatch(new BlockModelDoorGlass<>(AetherBlocks.DOOR_GLASS_QUICKSOIL_TOP).onRenderLayer(1)
                .setTex(0, "aether:block/door/glass_quicksoil/frame", Side.TOP, Side.BOTTOM)
                .setTex(0, "aether:block/door/glass_quicksoil/top", Side.sides));

        dispatcher.addDispatch(new BlockModelTrapDoor<>(AetherBlocks.TRAPDOOR_GLASS_QUICKSOIL).onRenderLayer(1)
                .setTex(0, "aether:block/trapdoor/glass_quicksoil/top", Side.TOP, Side.BOTTOM)
                .setTex(0, "aether:block/trapdoor/glass_quicksoil/side", Side.EAST, Side.NORTH, Side.SOUTH, Side.WEST));

        dispatcher.addDispatch(new BlockModelDungeonDoor(AetherBlocks.DOOR_DUNGEON)
                .setTex(0, "aether:block/door/boss/top_middle", Side.sides)
        );

        dispatcher.addDispatch(new BlockModelFlowerStackable<>(AetherBlocks.FLOWER_PURPLE, "aether:block/flower_purple/"));
        dispatcher.addDispatch(new BlockModelFlowerStackable<>(AetherBlocks.FLOWER_WHITE, "aether:block/flower_white/"));

        dispatcher.addDispatch(new BlockModelAetherTallgrass<>(AetherBlocks.TALLGRASS_AETHER).setAllTextures(0, "aether:block/tallgrass_aether"));


        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.PLANKS_SKYROOT)
                .setAllTextures(0, "aether:block/skyroot"));

        dispatcher.addDispatch(new BlockModelDoor<>(AetherBlocks.DOOR_PLANKS_SKYROOT_BOTTOM)
                .setTex(0, "aether:block/door/skyroot/frame", Side.TOP, Side.BOTTOM)
                .setTex(0, "aether:block/door/skyroot/bottom", Side.sides));
        dispatcher.addDispatch(new BlockModelDoor<>(AetherBlocks.DOOR_PLANKS_SKYROOT_TOP)
                .setTex(0, "aether:block/door/skyroot/frame", Side.TOP, Side.BOTTOM)
                .setTex(0, "aether:block/door/skyroot/top", Side.sides));

//        dispatcher.addDispatch((new BlockModelEmpty<>(AetherBlocks.SIGN_POST_PLANKS_SKYROOT)).setAllTextures(0, "aether:block/skyroot"));
//        dispatcher.addDispatch((new BlockModelEmpty<>(AetherBlocks.SIGN_WALL_PLANKS_SKYROOT)).setAllTextures(0, "aether:block/skyroot"));

        dispatcher.addDispatch(new BlockModelTrapDoor<>(AetherBlocks.TRAPDOOR_PLANKS_SKYROOT)
                .setTex(0, "aether:block/trapdoor/skyroot/top", Side.TOP, Side.BOTTOM)
                .setTex(0, "aether:block/trapdoor/skyroot/side", Side.EAST, Side.NORTH, Side.SOUTH, Side.WEST));

        dispatcher.addDispatch(new BlockModelChest<>(AetherBlocks.CHEST_PLANKS_SKYROOT, "aether:block/chest/skyroot/")
                .setAllTextures(0, "aether:block/chest/skyroot/top"));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.BUTTON_PLANKS_SKYROOT)
                .setAllTextures(0, "aether:block/skyroot").withCustomItemBounds(0.3125, 0.375, 0.375, 0.6875, 0.625, 0.625));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.PRESSURE_PLATE_PLANKS_SKYROOT)
                .setAllTextures(0, "aether:block/skyroot").withCustomItemBounds(0.0, 0.375, 0.0, 1.0, 0.625, 1.0));

        dispatcher.addDispatch(AetherBlocks.FENCE_PLANKS_SKYROOT, new BlockModelFence<>(AetherBlocks.FENCE_PLANKS_SKYROOT)
                .setAllTextures(0, "aether:block/skyroot"));
        dispatcher.addDispatch(AetherBlocks.FENCEGATE_PLANKS_SKYROOT, new BlockModelFenceGate<>(AetherBlocks.FENCEGATE_PLANKS_SKYROOT)
                .setAllTextures(0, "aether:block/skyroot"));



        dispatcher.addDispatch(new BlockModelAetherLog<>(AetherBlocks.LOG_SKYROOT)
                .setTex(0, "aether:block/log/skyroot_side", Side.sides)
                .setTex(0, "aether:block/log/skyroot_top", Side.TOP, Side.BOTTOM));
        dispatcher.addDispatch(new BlockModelAetherLog<>(AetherBlocks.LOG_OAK_GOLDEN)
                .setTex(0, "aether:block/log/oak_golden_side", Side.sides)
                .setTex(0, "aether:block/log/oak_golden_top", Side.TOP, Side.BOTTOM));

        dispatcher.addDispatch(new BlockModelLeaves<>(AetherBlocks.LEAVES_SKYROOT,"aether:block/leaves/skyroot", false));
        dispatcher.addDispatch(new BlockModelLeaves<>(AetherBlocks.LEAVES_OAK_GOLDEN,"aether:block/leaves/oak_golden", false));

        dispatcher.addDispatch(new BlockModelCrossedSquares<>(AetherBlocks.SAPLING_SKYROOT)
                .setAllTextures(0, "aether:block/sapling/skyroot"));
        dispatcher.addDispatch(new BlockModelCrossedSquares<>(AetherBlocks.SAPLING_OAK_GOLDEN)
                .setAllTextures(0, "aether:block/sapling/oak_golden"));


        dispatcher.addDispatch(new BlockModelTransparent<>(AetherBlocks.AERCLOUD_WHITE, false).onRenderLayer(1)
                .setAllTextures(0, "aether:block/aercloud_white"));
        dispatcher.addDispatch(new BlockModelTransparent<>(AetherBlocks.AERCLOUD_BLUE, false).onRenderLayer(1)
                .setAllTextures(0, "aether:block/aercloud_blue"));
        dispatcher.addDispatch(new BlockModelTransparent<>(AetherBlocks.AERCLOUD_GOLD, false).onRenderLayer(1)
                .setAllTextures(0, "aether:block/aercloud_gold"));

        dispatcher.addDispatch(new BlockModelTransparent<>(AetherBlocks.AEROGEL, false).onRenderLayer(1)
                .setAllTextures(0, "aether:block/aerogel"));


        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.ORE_AMBROSIUM_HOLYSTONE)
                .setAllTextures(0, "aether:block/ore/ambrosium/holystone"));
        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.ORE_ZANITE_HOLYSTONE)
                .setAllTextures(0, "aether:block/ore/zanite/holystone"));
        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.ORE_GRAVITITE_HOLYSTONE)
                .setAllTextures(0, "aether:block/ore/gravitite/holystone"));

        dispatcher.addDispatch(new BlockModelTransparent<>(AetherBlocks.BLOCK_AMBER, true).onRenderLayer(1)
                .setAllTextures(0, "aether:block/block_amber"));
        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.BLOCK_AMBROSIUM)
                .setAllTextures(0, "aether:block/block_ambrosium"));
        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.BLOCK_ZANITE)
                .setTex(0, "aether:block/block_zanite/side", Side.sides)
                .setTex(0, "aether:block/block_zanite/top", Side.TOP)
                .setTex(0, "aether:block/block_zanite/bottom", Side.BOTTOM));
        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.BLOCK_GRAVITITE)
                .setTex(0, "aether:block/block_gravitite/side", Side.sides)
                .setTex(0, "aether:block/block_gravitite/top", Side.TOP)
                .setTex(0, "aether:block/block_gravitite/bottom", Side.BOTTOM));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.BRICK_ZANITE)
                .setAllTextures(0, "aether:block/brick_zanite"));


        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_STONE)
                .setAllTextures(0, "aether:block/carved"));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_STONE_LIGHT)
                .setAllTextures(0, "aether:block/carved_glow")
                .setAllTextures(1, "aether:block/carved_overlay"));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_ANGELIC)
                .setAllTextures(0, "aether:block/angelic"));
        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_ANGELIC_LIGHT)
                .setAllTextures(0, "aether:block/angelic_glow")
                .setAllTextures(1, "aether:block/angelic_overlay"));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_HELLFIRE)
                .setAllTextures(0, "aether:block/hellfire"));
        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_HELLFIRE_LIGHT)
                .setAllTextures(0, "aether:block/hellfire_glow")
                .setAllTextures(1, "aether:block/hellfire_overlay"));


        dispatcher.addDispatch(new BlockModelAxisAligned<>(AetherBlocks.PILLAR)
                .setTex(0, "aether:block/pillar/side", Side.sides)
                .setTex(0, "aether:block/pillar/top", Side.TOP, Side.BOTTOM));

        dispatcher.addDispatch(new BlockModelAxisAligned<>(AetherBlocks.PILLAR_CAPSTONE)
                .setTex(0, "aether:block/pillar_capstone/side", Side.sides)
                .setTex(0, "aether:block/pillar_capstone/top", Side.TOP, Side.BOTTOM));

        dispatcher.addDispatch(new BlockModelHorizontalRotation<>(AetherBlocks.CHEST_DUNGEON_BRONZE)
                .setTex(0, "aether:block/chest/dungeon_bronze/front", Side.NORTH)
                .setTex(0, "aether:block/chest/dungeon_bronze/side", Side.EAST, Side.WEST, Side.SOUTH)
                .setTex(0, "aether:block/chest/dungeon_bronze/top", Side.TOP, Side.BOTTOM));

        dispatcher.addDispatch(new BlockModelHorizontalRotation<>(AetherBlocks.CHEST_DUNGEON_BRONZE_LOCKED)
                .setTex(0, "aether:block/chest/dungeon_bronze/front_locked", Side.NORTH)
                .setTex(0, "aether:block/chest/dungeon_bronze/side", Side.EAST, Side.WEST, Side.SOUTH)
                .setTex(0, "aether:block/chest/dungeon_bronze/top", Side.TOP, Side.BOTTOM));

        dispatcher.addDispatch(new BlockModelHorizontalRotation<>(AetherBlocks.CHEST_DUNGEON_SILVER)
                .setTex(0, "aether:block/chest/dungeon_silver/front", Side.NORTH)
                .setTex(0, "aether:block/chest/dungeon_silver/side", Side.EAST, Side.WEST, Side.SOUTH)
                .setTex(0, "aether:block/chest/dungeon_silver/top", Side.TOP, Side.BOTTOM));

        dispatcher.addDispatch(new BlockModelHorizontalRotation<>(AetherBlocks.CHEST_DUNGEON_SILVER_LOCKED)
                .setTex(0, "aether:block/chest/dungeon_silver/front_locked", Side.NORTH)
                .setTex(0, "aether:block/chest/dungeon_silver/side", Side.EAST, Side.WEST, Side.SOUTH)
                .setTex(0, "aether:block/chest/dungeon_silver/top", Side.TOP, Side.BOTTOM));

        dispatcher.addDispatch(new BlockModelHorizontalRotation<>(AetherBlocks.CHEST_DUNGEON_GOLD)
                .setTex(0, "aether:block/chest/dungeon_gold/front", Side.NORTH)
                .setTex(0, "aether:block/chest/dungeon_gold/side", Side.EAST, Side.WEST, Side.SOUTH)
                .setTex(0, "aether:block/chest/dungeon_gold/top", Side.TOP, Side.BOTTOM));

        dispatcher.addDispatch(new BlockModelHorizontalRotation<>(AetherBlocks.CHEST_DUNGEON_GOLD_LOCKED)
                .setTex(0, "aether:block/chest/dungeon_gold/front_locked", Side.NORTH)
                .setTex(0, "aether:block/chest/dungeon_gold/side", Side.EAST, Side.WEST, Side.SOUTH)
                .setTex(0, "aether:block/chest/dungeon_gold/top", Side.TOP, Side.BOTTOM));
        //

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_STONE_LOCKED)
                .setAllTextures(0, "aether:block/carved"));
        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_STONE_LIGHT_LOCKED)
                .setAllTextures(0, "aether:block/carved_glow")
                .setAllTextures(1, "aether:block/carved_overlay"));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_ANGELIC_LOCKED)
                .setAllTextures(0, "aether:block/angelic"));
        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_ANGELIC_LIGHT_LOCKED)
                .setAllTextures(0, "aether:block/angelic_glow")
                .setAllTextures(1, "aether:block/angelic_overlay"));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_HELLFIRE_LOCKED)
                .setAllTextures(0, "aether:block/hellfire"));
        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_HELLFIRE_LIGHT_LOCKED)
                .setAllTextures(0, "aether:block/hellfire_glow")
                .setAllTextures(1, "aether:block/hellfire_overlay"));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_STONE_TRAPPED)
                .setAllTextures(0, "aether:block/carved"));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_STONE_TRAPPED_LOCKED)
                .setAllTextures(0, "aether:block/carved"));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_ANGELIC_TRAPPED)
                .setAllTextures(0, "aether:block/angelic"));

        dispatcher.addDispatch(new BlockModelHorizontalRotation<>(AetherBlocks.CHEST_MIMIC)
                .setTex(0, "aether:block/chest/skyroot/front", Side.NORTH)
                .setTex(0, "aether:block/chest/skyroot/side", Side.EAST, Side.WEST, Side.SOUTH)
                .setTex(0, "aether:block/chest/skyroot/top", Side.TOP, Side.BOTTOM));


        dispatcher.addDispatch(AetherBlocks.TORCH_AMBROSIUM, new BlockModelTorch<>(AetherBlocks.TORCH_AMBROSIUM)
                .setAllTextures(0, "aether:block/torch_ambrosium"));


        dispatcher.addDispatch(AetherBlocks.LANTERN_FIREFLY_SILVER, new BlockModelLantern<>(AetherBlocks.LANTERN_FIREFLY_SILVER)
                .setAllTextures(0, "aether:block/lantern_firefly_silver"));



        dispatcher.addDispatch(new BlockModelFurnace<>(AetherBlocks.ENCHANTER_IDLE)
                .setTex(0, "aether:block/enchanter/top", Side.TOP)
                .setTex(0, "aether:block/enchanter/bottom", Side.BOTTOM)
                .setTex(0, "aether:block/enchanter/idle_front", Side.NORTH)
                .setTex(0, "aether:block/enchanter/side", Side.EAST, Side.WEST, Side.SOUTH));
        dispatcher.addDispatch(new BlockModelFurnace<>(AetherBlocks.ENCHANTER_ACTIVE)
                .setTex(0, "aether:block/enchanter/top", Side.TOP)
                .setTex(0, "aether:block/enchanter/bottom", Side.BOTTOM)
                .setTex(0, "aether:block/enchanter/active_front", Side.NORTH)
                .setTex(0, "aether:block/enchanter/side", Side.EAST, Side.WEST, Side.SOUTH));

        dispatcher.addDispatch(new BlockModelFreezer<>(AetherBlocks.FREEZER_IDLE)
                .setTex(0, "aether:block/freezer/idle_top", Side.TOP)
                .setTex(0, "aether:block/freezer/bottom", Side.BOTTOM)
                .setTex(0, "aether:block/freezer/side", Side.EAST, Side.WEST, Side.SOUTH, Side.NORTH));
        dispatcher.addDispatch(new BlockModelFreezer<>(AetherBlocks.FREEZER_ACTIVE)
                .setTex(0, "aether:block/freezer/active_top", Side.TOP)
                .setTex(0, "aether:block/freezer/bottom", Side.BOTTOM)
                .setTex(0, "aether:block/freezer/side", Side.EAST, Side.WEST, Side.SOUTH, Side.NORTH));

        dispatcher.addDispatch(new BlockModelIncubator<>(AetherBlocks.INCUBATOR_IDLE)
                .setTex(0, "aether:block/incubator/idle_top", Side.TOP)
                .setTex(0, "aether:block/incubator/bottom", Side.BOTTOM)
                .setTex(0, "aether:block/incubator/side", Side.EAST, Side.WEST, Side.SOUTH, Side.NORTH));
        dispatcher.addDispatch(new BlockModelIncubator<>(AetherBlocks.INCUBATOR_ACTIVE)
                .setTex(0, "aether:block/incubator/active_top", Side.TOP)
                .setTex(0, "aether:block/incubator/bottom", Side.BOTTOM)
                .setTex(0, "aether:block/incubator/side", Side.EAST, Side.WEST, Side.SOUTH, Side.NORTH));



        dispatcher.addDispatch(new BlockModelStairs<>(AetherBlocks.STAIRS_COBBLE_HOLYSTONE));
        dispatcher.addDispatch(new BlockModelStairs<>(AetherBlocks.STAIRS_BRICK_HOLYSTONE));
        dispatcher.addDispatch(new BlockModelStairs<>(AetherBlocks.STAIRS_CARVED_STONE));
        dispatcher.addDispatch(new BlockModelStairs<>(AetherBlocks.STAIRS_CARVED_ANGELIC));
        dispatcher.addDispatch(new BlockModelStairs<>(AetherBlocks.STAIRS_CARVED_HELLFIRE));
        dispatcher.addDispatch(new BlockModelStairs<>(AetherBlocks.STAIRS_PLANKS_SKYROOT));
        dispatcher.addDispatch(new BlockModelStairs<>(AetherBlocks.STAIRS_BRICK_ZANITE));

        dispatcher.addDispatch(new BlockModelSlab<>(AetherBlocks.SLAB_COBBLE_HOLYSTONE));
        dispatcher.addDispatch(new BlockModelSlab<>(AetherBlocks.SLAB_BRICK_HOLYSTONE));
        dispatcher.addDispatch(new BlockModelSlab<>(AetherBlocks.SLAB_HOLYSTONE_POLISHED));
        dispatcher.addDispatch(new BlockModelSlab<>(AetherBlocks.SLAB_CARVED_STONE));
        dispatcher.addDispatch(new BlockModelSlab<>(AetherBlocks.SLAB_CARVED_ANGELIC));
        dispatcher.addDispatch(new BlockModelSlab<>(AetherBlocks.SLAB_CARVED_HELLFIRE));
        dispatcher.addDispatch(new BlockModelSlab<>(AetherBlocks.SLAB_PLANKS_SKYROOT));
        dispatcher.addDispatch(new BlockModelSlab<>(AetherBlocks.SLAB_BRICK_ZANITE));

    }

    @Override
    public void initItemModels(ItemModelDispatcher dispatcher) {

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.MEDAL_VICTORY, null).setIcon("aether:item/medal_victory"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.KEY_BRONZE, null).setIcon("aether:item/key_bronze"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.KEY_SILVER, null).setIcon("aether:item/key_silver"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.KEY_GOLD, null).setIcon("aether:item/key_gold"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.EGG_MOA_BLUE, null).setIcon("aether:item/egg_moa_blue"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.EGG_MOA_WHITE, null).setIcon("aether:item/egg_moa_white"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.EGG_MOA_BLACK, null).setIcon("aether:item/egg_moa_black"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.RECORD_AETHER, null).setIcon("aether:item/record_aether"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.RECORD_MORNING, null).setIcon("aether:item/record_morning"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.RECORD_DAWN, null).setIcon("aether:item/record_dawn"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.RECORD_NETHER, null).setIcon("aether:item/record_nether"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.AMBER, null).setIcon("aether:item/amber"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.PETAL_AECHOR, null).setIcon("aether:item/petal_aechor"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.STICK_SKYROOT, null).setIcon("aether:item/stick_skyroot"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.AMBROSIUM, null).setIcon("aether:item/ambrosium"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ZANITE, null).setIcon("aether:item/zanite"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.BUCKET_SKYROOT, null).setIcon("aether:item/bucket_skyroot"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.BUCKET_SKYROOT_WATER, null).setIcon("aether:item/bucket_skyroot_water"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.BUCKET_SKYROOT_MILK, null).setIcon("aether:item/bucket_skyroot_milk"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.BUCKET_SKYROOT_POISON, null).setIcon("aether:item/bucket_skyroot_poison"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.BUCKET_SKYROOT_REMEDY, null).setIcon("aether:item/bucket_skyroot_remedy"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.BUCKET_SKYROOT_ICECREAM, null).setIcon("aether:item/bucket_skyroot_icecream"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_SWORD_SKYROOT, null).setIcon("aether:item/tool_sword_skyroot").setFull3D());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_SHOVEL_SKYROOT, null).setIcon("aether:item/tool_shovel_skyroot").setFull3D());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_PICKAXE_SKYROOT, null).setIcon("aether:item/tool_pickaxe_skyroot").setFull3D());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_AXE_SKYROOT, null).setIcon("aether:item/tool_axe_skyroot").setFull3D());

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_SWORD_HOLYSTONE, null).setIcon("aether:item/tool_sword_holystone").setFull3D());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_SHOVEL_HOLYSTONE, null).setIcon("aether:item/tool_shovel_holystone").setFull3D());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_PICKAXE_HOLYSTONE, null).setIcon("aether:item/tool_pickaxe_holystone").setFull3D());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_AXE_HOLYSTONE, null).setIcon("aether:item/tool_axe_holystone").setFull3D());

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_SWORD_ZANITE, null).setIcon("aether:item/tool_sword_zanite").setFull3D());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_SHOVEL_ZANITE, null).setIcon("aether:item/tool_shovel_zanite").setFull3D());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_PICKAXE_ZANITE, null).setIcon("aether:item/tool_pickaxe_zanite").setFull3D());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_AXE_ZANITE, null).setIcon("aether:item/tool_axe_zanite").setFull3D());

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_SWORD_GRAVITITE, null).setIcon("aether:item/tool_sword_gravitite").setFull3D());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_SHOVEL_GRAVITITE, null).setIcon("aether:item/tool_shovel_gravitite").setFull3D());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_PICKAXE_GRAVITITE, null).setIcon("aether:item/tool_pickaxe_gravitite").setFull3D());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_AXE_GRAVITITE, null).setIcon("aether:item/tool_axe_gravitite").setFull3D());

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_SWORD_VALKYRIE, null).setIcon("aether:item/tool_sword_valk").setFull3D());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_SHOVEL_VALKYRIE, null).setIcon("aether:item/tool_shovel_valk").setFull3D());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_PICKAXE_VALKYRIE, null).setIcon("aether:item/tool_pickaxe_valk").setFull3D());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_AXE_VALKYRIE, null).setIcon("aether:item/tool_axe_valk").setFull3D());

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_KNIFE_LIGHTNING, null).setIcon("aether:item/tool_knife_lightning").setFull3D());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_HAMMER_NOTCH, null).setIcon("aether:item/tool_hammer_notch").setFull3D());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.AMMO_HAMMER_HEAD, null).setIcon("aether:item/notch_wave"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_SHIELD_REPULSION, null).setIcon("aether:item/tool_shield_repulsion"));
        dispatcher.addDispatch(new ItemModelBow(AetherItems.TOOL_BOW_PHOENIX, null).setIcon("aether:item/tool_bow_phoenix").setFullBright());

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.AMMO_DART_GOLDEN, null).setIcon("aether:item/dart_golden"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.AMMO_DART_POISON, null).setIcon("aether:item/dart_poison"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.AMMO_DART_ENCHANTED, null).setIcon("aether:item/dart_enchanted"));

        dispatcher.addDispatch(new ItemModelShooter(AetherItems.TOOL_SHOOTER, null).setIcon("aether:item/shooter_gold"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_SWORD_PIG, null).setIcon("aether:item/tool_knife_pig").setFull3D());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_SWORD_VAMPIRE, null).setIcon("aether:item/tool_sword_vampire").setFull3D());

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_SWORD_FLAME, null).setIcon("aether:item/tool_sword_element_fire").setFull3D().setFullBright());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_SWORD_HOLY, null).setIcon("aether:item/tool_sword_element_holy").setFull3D());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_SWORD_LIGHTNING, null).setIcon("aether:item/tool_sword_element_lightning").setFull3D());

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_STAFF_NATURE, null).setIcon("aether:item/staff_nature").setFull3D());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_STAFF_CLOUD, null).setIcon("aether:item/staff_cloud").setFull3D());

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_DUNGEON_COMPASS, null).setIcon("aether:item/tool_dungeon_compass"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_HELMET_ZANITE, null).setIcon("aether:item/armor_helmet_zanite"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CHESTPLATE_ZANITE, null).setIcon("aether:item/armor_chestplate_zanite"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_LEGGINGS_ZANITE, null).setIcon("aether:item/armor_leggings_zanite"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_BOOTS_ZANITE, null).setIcon("aether:item/armor_boots_zanite"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_HELMET_GRAVITITE, null).setIcon("aether:item/armor_helmet_gravitite"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CHESTPLATE_GRAVITITE, null).setIcon("aether:item/armor_chestplate_gravitite"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_LEGGINGS_GRAVITITE, null).setIcon("aether:item/armor_leggings_gravitite"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_BOOTS_GRAVITITE, null).setIcon("aether:item/armor_boots_gravitite"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_HELMET_OBSIDIAN, null).setIcon("aether:item/armor_helmet_obsidian"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CHESTPLATE_OBSIDIAN, null).setIcon("aether:item/armor_chestplate_obsidian"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_LEGGINGS_OBSIDIAN, null).setIcon("aether:item/armor_leggings_obsidian"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_BOOTS_OBSIDIAN, null).setIcon("aether:item/armor_boots_obsidian"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_HELMET_PHOENIX, null).setIcon("aether:item/armor_helmet_phoenix").setFullBright());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CHESTPLATE_PHOENIX, null).setIcon("aether:item/armor_chestplate_phoenix").setFullBright());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_LEGGINGS_PHOENIX, null).setIcon("aether:item/armor_leggings_phoenix").setFullBright());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_BOOTS_PHOENIX, null).setIcon("aether:item/armor_boots_phoenix").setFullBright());

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_HELMET_NEPTUNE, null).setIcon("aether:item/armor_helmet_neptune"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CHESTPLATE_NEPTUNE, null).setIcon("aether:item/armor_chestplate_neptune"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_LEGGINGS_NEPTUNE, null).setIcon("aether:item/armor_leggings_neptune"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_BOOTS_NEPTUNE, null).setIcon("aether:item/armor_boots_neptune"));


        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_REGEN, null).setIcon("aether:item/accessory_healing"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_BUBBLE, null).setIcon("aether:item/accessory_bubble"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_FEATHER_GOLD, null).setIcon("aether:item/accessory_feather"));


        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_LEATHER, null).setIcon("aether:item/armor_pendant_leather"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_CHAIN, null).setIcon("aether:item/armor_pendant_chain"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_IRON, null).setIcon("aether:item/armor_pendant_iron"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_GOLD, null).setIcon("aether:item/armor_pendant_gold"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_DIAMOND, null).setIcon("aether:item/armor_pendant_diamond"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_STEEL, null).setIcon("aether:item/armor_pendant_steel"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_ZANITE, null).setIcon("aether:item/armor_pendant_zanite"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_GRAVITITE, null).setIcon("aether:item/armor_pendant_gravitite"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_ICE, null).setIcon("aether:item/armor_pendant_ice"));


        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_LEATHER, null).setIcon("aether:item/armor_gloves_leather"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_CHAIN, null).setIcon("aether:item/armor_gloves_chain"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_IRON, null).setIcon("aether:item/armor_gloves_iron"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_GOLD, null).setIcon("aether:item/armor_gloves_gold"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_DIAMOND, null).setIcon("aether:item/armor_gloves_diamond"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_STEEL, null).setIcon("aether:item/armor_gloves_steel"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_ZANITE, null).setIcon("aether:item/armor_gloves_zanite"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_GRAVITITE, null).setIcon("aether:item/armor_gloves_gravitite"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_OBSIDIAN, null).setIcon("aether:item/armor_gloves_obsidian"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_PHOENIX, null).setIcon("aether:item/armor_gloves_phoenix").setFullBright());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_NEPTUNE, null).setIcon("aether:item/armor_gloves_neptune"));


        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_AGILITY, null).setIcon("aether:item/armor_cape_agility"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_SWET, null).setIcon("aether:item/armor_cape_swet"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_INVISIBILITY, null).setIcon("aether:item/armor_cape_invisibility"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_WHITE, null).setIcon("aether:item/armor_cape_white"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_SILVER, null).setIcon("aether:item/armor_cape_silver"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_GRAY, null).setIcon("aether:item/armor_cape_gray"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_BLACK, null).setIcon("aether:item/armor_cape_black"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_BROWN, null).setIcon("aether:item/armor_cape_brown"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_RED, null).setIcon("aether:item/armor_cape_red"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_YELLOW, null).setIcon("aether:item/armor_cape_yellow"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_ORANGE, null).setIcon("aether:item/armor_cape_orange"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_LIME, null).setIcon("aether:item/armor_cape_lime"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_GREEN, null).setIcon("aether:item/armor_cape_green"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_CYAN, null).setIcon("aether:item/armor_cape_cyan"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_BLUE, null).setIcon("aether:item/armor_cape_blue"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_LIGHTBLUE, null).setIcon("aether:item/armor_cape_lightblue"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_PURPLE, null).setIcon("aether:item/armor_cape_purple"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_MAGENTA, null).setIcon("aether:item/armor_cape_magenta"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_PINK, null).setIcon("aether:item/armor_cape_pink"));


        dispatcher.addDispatch(new ItemModelStandard(AetherItems.FOOD_HEALING_STONE, null).setIcon("aether:item/food_healing_stone"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.FOOD_GUMMY_BLUE, null).setIcon("aether:item/food_sweet_blue"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.FOOD_GUMMY_GOLD, null).setIcon("aether:item/food_sweet_gold"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.LIFESHARD, null).setIcon("aether:item/food_lifeshard"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.PARACHUTE_CLOUD, null).setIcon("aether:item/parachute"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.PARACHUTE_CLOUD_GOLD, null).setIcon("aether:item/parachute_gold"));


        dispatcher.addDispatch(new ItemModelStandard(AetherItems.LANTERN_FIREFLY_SILVER, null).setIcon("aether:item/lantern_firefly_silver").setFullBright());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.DOOR_SKYROOT, null).setIcon("aether:item/door_skyroot"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.DOOR_GLASS_AMBROSIUM, null).setIcon("aether:item/door_glass_ambrosium"));

//        dispatcher.addDispatch(new ItemModelStandard(AetherItems.SIGN_SKYROOT, null).setIcon("aether:item/sign_skyroot"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.AMMO_WINDBALL, null).setIcon("aether:item/windball"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.PROJECTILE_FIRE, null).setIcon("aether:item/projectile_fire").setFullBright());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.PROJECTILE_ICE, null).setIcon("aether:item/projectile_ice"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.PROJECTILE_LIGHTNING, null).setIcon("aether:item/projectile_lightning"));


        dispatcher.addDispatch((new ItemModelBlock((ItemBlock<?>) AetherBlocks.TORCH_AMBROSIUM.asItem())).setFullBright());
        dispatcher.addDispatch((new ItemModelStandard(AetherItems.DEATH_RAY, null)).setIcon("minecraft:item/wand_monster").setFullBright());

    }

    @Override
    public void initEntityModels(EntityRenderDispatcher dispatcher) {

        ModelHelper.setEntityModel(ProjectileHammerHead.class, () -> new EntityRendererSprite<>(AetherItems.AMMO_HAMMER_HEAD).setScale(2.0f));
        ModelHelper.setEntityModel(ProjectileWindball.class, () -> new EntityRendererSprite<>(AetherItems.AMMO_WINDBALL).setScale(4.0F).setFullBright());
        ModelHelper.setEntityModel(ProjectileElementFire.class, () -> new EntityRendererSprite<>(AetherItems.PROJECTILE_FIRE).setScale(3.0F).setFullBright());
        ModelHelper.setEntityModel(ProjectileElementIce.class, () -> new EntityRendererSprite<>(AetherItems.PROJECTILE_ICE).setScale(3.0F).setFullBright());
        ModelHelper.setEntityModel(ProjectileElementLightning.class, () -> new EntityRendererSprite<>(AetherItems.PROJECTILE_LIGHTNING).setScale(3.0F).setFullBright());
        ModelHelper.setEntityModel(ProjectileDart.class, EntityRendererDart::new);
        ModelHelper.setEntityModel(ProjectileNeedle.class, EntityRendererNeedle::new);
        ModelHelper.setEntityModel(ProjectileArrowFlaming.class, EntityRendererArrowFlaming::new);
        ModelHelper.setEntityModel(ProjectileKnifeLightning.class, EntityRendererKnifeLightning::new);


        ModelHelper.setEntityModel(MobSentry.class, () -> new MobRendererSentry(new ModelSlime(0), 0.2F));
        ModelHelper.setEntityModel(MobAechorPlant.class, () -> new MobRendererAechorPlant(new ModelAechorPlant(), 0.3F));
        ModelHelper.setEntityModel(MobSwet.class, () -> new MobRendererSwet(new ModelSlime(16), new ModelSlime(0), 1.0f));
        ModelHelper.setEntityModel(MobSwetGold.class, () -> new MobRendererSwet(new ModelSlime(16), new ModelSlime(0), 1.0f));
        ModelHelper.setEntityModel(MobZephyr.class, MobRendererZephyr::new);
        ModelHelper.setEntityModel(MobMimic.class, MobRendererMimic::new);
        ModelHelper.setEntityModel(MobCockatrice.class, () -> new MobRendererCockatrice(new ModelMoa(), 0.7F));
        ModelHelper.setEntityModel(MobValkyrie.class, () -> new MobRendererValkyrie(new ModelValkyrie(), 0.5F));
        ModelHelper.setEntityModel(MobWhirly.class, MobRendererWhirly::new);

        ModelHelper.setEntityModel(MobBossSlider.class, () -> new MobRendererSlider(new ModelSlider(0.0F, 12.0623F), 1.5F));
        ModelHelper.setEntityModel(MobBossValkyrie.class, () -> new MobRendererValkyrieBoss(new ModelValkyrie(), 0.5F));
        ModelHelper.setEntityModel(MobBossSunspirit.class, MobRendererSunspirit::new);
        ModelHelper.setEntityModel(MobFireMinion.class, MobRendererFireMinion::new);


        ModelHelper.setEntityModel(MobSheepuff.class, () -> new MobRendererSheepuff(new ModelSheepuff(), new ModelSheepuffWool(), new ModelSheepuffPuff(), new ModelSheepuffOverlay(), 0.7F));
        ModelHelper.setEntityModel(MobPhow.class, () -> new MobRendererPhow(new ModelPhow(), 0.7F));
        ModelHelper.setEntityModel(MobMoaBlue.class, () -> new MobRendererMoa(new ModelMoa(), 0.7F));
        ModelHelper.setEntityModel(MobPhyg.class, () -> new MobRendererPhyg(new ModelPhyg(), 0.7F));
        ModelHelper.setEntityModel(MobAerwhale.class, () -> new MobRendererAerwhale(new ModelAerwhale(), 1.0F));
        ModelHelper.setEntityModel(MobAerbunny.class, () -> new MobRendererAerbunny(new ModelAerbunny(), 0.5F));



        ModelHelper.setEntityModel(EntityFloatingBlock.class, EntityRendererFallingBlock::new);
        ModelHelper.setEntityModel(EntityParachute.class, EntityRendererParachute::new);
        ModelHelper.setEntityModel(EntityParachuteGold.class, EntityRendererParachuteGold::new);
        ModelHelper.setEntityModel(MobMinicloud.class, () -> new MobRenderer<>(new ModelMinicloud(0.0f, 20.0f), 0.35f));



    }

    @Override
    public void initTileEntityModels(TileEntityRenderDispatcher dispatcher) {
//        ModelHelper.setTileEntityModel(TileEntitySignSkyroot.class, TileEntityRendererSignSkyroot::new);

    }

    @Override
    public void initBlockColors(BlockColorDispatcher dispatcher) {
    }
}
