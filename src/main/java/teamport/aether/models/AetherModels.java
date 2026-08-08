package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.EntityRendererDispatcher;
import net.minecraft.client.render.block.color.BlockColorCustom;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.*;
import net.minecraft.client.render.block.model.generic.BlockModelGenericPortal;
import net.minecraft.client.render.block.model.generic.BlockModelGenericChest;
import net.minecraft.client.render.block.model.generic.BlockModelGenericLantern;
import net.minecraft.client.render.block.model.generic.BlockModelGenericTorch;
import net.minecraft.client.render.entity.EntityRendererSprite;
import net.minecraft.client.render.entity.MobRendererPlayer;
import net.minecraft.client.render.item.model.ItemModelBlock;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.core.item.block.ItemBlock;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.Side;
import org.jspecify.annotations.NonNull;
import teamport.aether.AetherClient;
import teamport.aether.block.AetherBlocks;
import teamport.aether.entity.animal.aerbunny.MobAerbunny;
import teamport.aether.entity.animal.aerbunny.MobRendererAerbunny;
import teamport.aether.entity.animal.aerwhale.MobAerwhale;
import teamport.aether.entity.animal.aerwhale.MobRendererAerwhale;
import teamport.aether.entity.animal.moa.MobMoaBlack;
import teamport.aether.entity.animal.moa.MobMoaBlue;
import teamport.aether.entity.animal.moa.MobMoaWhite;
import teamport.aether.entity.animal.moa.MobRendererMoa;
import teamport.aether.entity.animal.phow.MobPhow;
import teamport.aether.entity.animal.phow.MobRendererPhow;
import teamport.aether.entity.animal.phyg.MobPhyg;
import teamport.aether.entity.animal.phyg.MobRendererPhyg;
import teamport.aether.entity.animal.sheepuff.MobRendererSheepuff;
import teamport.aether.entity.animal.sheepuff.MobSheepuff;
import teamport.aether.entity.animal.whirly.MobRendererWhirly;
import teamport.aether.entity.animal.whirly.MobWhirly;
import teamport.aether.entity.boss.slider.MobBossSlider;
import teamport.aether.entity.boss.slider.MobRendererSlider;
import teamport.aether.entity.boss.sunspirit.MobBossSunspirit;
import teamport.aether.entity.boss.sunspirit.MobRendererSunspirit;
import teamport.aether.entity.boss.valkyrie.queen.MobBossValkyrie;
import teamport.aether.entity.boss.valkyrie.queen.MobRendererBossValkyrie;
import teamport.aether.entity.floating_block.EntityFloatingBlock;
import teamport.aether.entity.floating_block.EntityRendererFloatingBlock;
import teamport.aether.entity.monster.aechorplant.MobAechorPlant;
import teamport.aether.entity.monster.aechorplant.MobRendererAechorPlant;
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
import teamport.aether.entity.monster.tempest.MobRendererTempest;
import teamport.aether.entity.monster.tempest.MobTempest;
import teamport.aether.entity.monster.valkyrie.MobRendererValkyrie;
import teamport.aether.entity.monster.valkyrie.MobValkyrie;
import teamport.aether.entity.monster.zephyr.MobRendererZephyr;
import teamport.aether.entity.monster.zephyr.MobZephyr;
import teamport.aether.entity.projectile.*;
import teamport.aether.entity.renderer.EntityRendererArrowFlaming;
import teamport.aether.entity.renderer.EntityRendererDart;
import teamport.aether.entity.renderer.EntityRendererKnifeLightning;
import teamport.aether.entity.renderer.EntityRendererNeedle;
import teamport.aether.entity.vehicle.parachute.EntityParachute;
import teamport.aether.entity.vehicle.parachute.EntityParachuteGold;
import teamport.aether.entity.vehicle.parachute.EntityRendererParachute;
import teamport.aether.entity.vehicle.parachute.EntityRendererParachuteGold;
import teamport.aether.item.AetherItems;
import teamport.aether.models.dungeon.BlockModelDungeonDoor;
import teamport.aether.models.dungeon.BlockModelMimic;
import teamport.aether.models.dungeon.BlockModelPaintedOakMimic;
import teamport.aether.models.skyroot.*;

@Environment(EnvType.CLIENT)
public class AetherModels {
    Side[] TOP_BOTTOM = new Side[]{Side.TOP, Side.BOTTOM};
    Side[] SIDES = new Side[]{Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST};

    public void initBlockModels(BlockModelDispatcher dispatcher) {

        this.setBlockDungeonModels(dispatcher);
        this.setBlockMachineModels(dispatcher);
        this.setBlockSkyrootModels(dispatcher);
        this.setBlockPlantModels(dispatcher);
        this.setBlockCloudModels(dispatcher);


        dispatcher.addDispatch(new BlockModelGenericPortal<>(AetherBlocks.PORTAL_AETHER, "aether:block/portal_aether/"));

        dispatcher.addDispatch(new BlockModelGrassAether<>(AetherBlocks.GRASS_AETHER)
            .setTex("aether:block/grass_aether/top", Side.TOP)
            .setTex("aether:block/grass_aether/bottom", Side.BOTTOM)
            .setTex("aether:block/grass_aether/side", SIDES));

        dispatcher.addDispatch(new BlockModelRetroStandard<>(AetherBlocks.DIRT_AETHER)
            .setRetroAllTextures("aether:block/dirt_aether_retro")
            .setAllTextures("aether:block/dirt_aether"));

        dispatcher.addDispatch(new BlockModelRetroStandard<>(AetherBlocks.PATH_DIRT_AETHER)
            .setRetroTex("aether:block/grass_path_aether/top_retro", Side.TOP)
            .setRetroTex("aether:block/grass_path_aether/bottom_retro", Side.BOTTOM)
            .setRetroTex("aether:block/grass_path_aether/side_retro", SIDES)
            .setTex("aether:block/grass_path_aether/top", Side.TOP)
            .setTex("aether:block/grass_path_aether/bottom", Side.BOTTOM)
            .setTex("aether:block/grass_path_aether/side", SIDES));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.HOLYSTONE)
            .setAllTextures("aether:block/holystone"));

        dispatcher.addDispatch(new BlockModelAetherStoneMossy<>(AetherBlocks.HOLYSTONE_MOSSY)
            .setAllTextures("aether:block/holystone"));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.HOLYSTONE_POLISHED)
            .setTex("aether:block/polished_holystone_side", SIDES)
            .setTex("aether:block/polished_holystone_top", TOP_BOTTOM));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.HOLYSTONE_CARVED)
            .setTex("aether:block/carved_holystone", SIDES)
            .setTex("aether:block/polished_holystone_top", TOP_BOTTOM));

        dispatcher.addDispatch(new BlockModelRetroStandard<>(AetherBlocks.COBBLE_HOLYSTONE)
            .setRetroAllTextures("aether:block/cobbled_holystone_retro")
            .setAllTextures("aether:block/cobbled_holystone"));

        dispatcher.addDispatch(new BlockModelRetroStandard<>(AetherBlocks.COBBLE_HOLYSTONE_MOSSY)
            .setRetroAllTextures("aether:block/cobbled_holystone_mossy_retro")
            .setAllTextures("aether:block/cobbled_holystone_mossy"));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.BRICK_HOLYSTONE)
            .setAllTextures("aether:block/brick_holystone"));


        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.ICESTONE)
            .setAllTextures("aether:block/icestone"));


        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.QUICKSOIL)
            .setAllTextures("aether:block/quicksoil"));

        dispatcher.addDispatch(new BlockModelTransparent<>(AetherBlocks.GLASS_QUICKSOIL, false).onRenderLayer(1)
            .setAllTextures("aether:block/glass_quicksoil"));

        dispatcher.addDispatch(new BlockModelRetroDoor<>(AetherBlocks.DOOR_GLASS_QUICKSOIL_BOTTOM)
            .setRetroTex("aether:block/door/glass_quicksoil/frame_retro", TOP_BOTTOM)
            .setRetroTex("aether:block/door/glass_quicksoil/bottom_retro", SIDES)
            .onRenderLayer(1)
            .setTex("aether:block/door/glass_quicksoil/frame", TOP_BOTTOM)
            .setTex("aether:block/door/glass_quicksoil/bottom", SIDES));

        dispatcher.addDispatch(new BlockModelRetroDoor<>(AetherBlocks.DOOR_GLASS_QUICKSOIL_TOP)
            .setRetroTex("aether:block/door/glass_quicksoil/frame_retro", TOP_BOTTOM)
            .setRetroTex("aether:block/door/glass_quicksoil/top_retro", SIDES)
            .onRenderLayer(1)
            .setTex("aether:block/door/glass_quicksoil/frame", TOP_BOTTOM)
            .setTex("aether:block/door/glass_quicksoil/top", SIDES));

        dispatcher.addDispatch(new BlockModelRetroTrapDoor<>(AetherBlocks.TRAPDOOR_GLASS_QUICKSOIL)
            .setRetroTex("aether:block/trapdoor/glass_quicksoil/top_retro", TOP_BOTTOM)
            .setRetroTex("aether:block/trapdoor/glass_quicksoil/side_retro", SIDES)
            .onRenderLayer(1)
            .setTex("aether:block/trapdoor/glass_quicksoil/top", TOP_BOTTOM)
            .setTex("aether:block/trapdoor/glass_quicksoil/side", Side.EAST, Side.NORTH, Side.SOUTH, Side.WEST));

        dispatcher.addDispatch(new BlockModelRetroStandard<>(AetherBlocks.ORE_AMBROSIUM_HOLYSTONE)
            .setRetroAllTextures("aether:block/ore/ambrosium/holystone_retro")
            .setAllTextures("aether:block/ore/ambrosium/holystone"));
        dispatcher.addDispatch(new BlockModelRetroStandard<>(AetherBlocks.ORE_ZANITE_HOLYSTONE)
            .setRetroAllTextures("aether:block/ore/zanite/holystone_retro")
            .setAllTextures("aether:block/ore/zanite/holystone"));
        dispatcher.addDispatch(new BlockModelRetroStandard<>(AetherBlocks.ORE_GRAVITITE_HOLYSTONE)
            .setRetroAllTextures("aether:block/ore/gravitite/holystone_retro")
            .setAllTextures("aether:block/ore/gravitite/holystone"));

        dispatcher.addDispatch(new BlockModelTransparent<>(AetherBlocks.BLOCK_AMBER, true).onRenderLayer(1)
            .setAllTextures("aether:block/block_amber"));
        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.BLOCK_AMBROSIUM)
            .setAllTextures("aether:block/block_ambrosium"));
        dispatcher.addDispatch(new BlockModelRetroStandard<>(AetherBlocks.BLOCK_ZANITE)
            .setRetroTex("aether:block/block_zanite/side_retro", SIDES)
            .setRetroTex("aether:block/block_zanite/top_retro", Side.TOP)
            .setRetroTex("aether:block/block_zanite/bottom_retro", Side.BOTTOM)
            .setTex("aether:block/block_zanite/side", SIDES)
            .setTex("aether:block/block_zanite/top", Side.TOP)
            .setTex("aether:block/block_zanite/bottom", Side.BOTTOM));
        dispatcher.addDispatch(new BlockModelRetroStandard<>(AetherBlocks.BLOCK_GRAVITITE)
            .setRetroTex("aether:block/block_gravitite/side_retro", SIDES)
            .setRetroTex("aether:block/block_gravitite/top_retro", Side.TOP)
            .setRetroTex("aether:block/block_gravitite/bottom_retro", Side.BOTTOM)
            .setTex("aether:block/block_gravitite/side", SIDES)
            .setTex("aether:block/block_gravitite/top", Side.TOP)
            .setTex("aether:block/block_gravitite/bottom", Side.BOTTOM));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.BRICK_ZANITE)
            .setAllTextures("aether:block/brick_zanite"));

        dispatcher.addDispatch(new BlockModelStairs<>(AetherBlocks.STAIRS_COBBLE_HOLYSTONE));
        dispatcher.addDispatch(new BlockModelStairs<>(AetherBlocks.STAIRS_BRICK_HOLYSTONE));
        dispatcher.addDispatch(new BlockModelStairs<>(AetherBlocks.STAIRS_BRICK_ZANITE));

        dispatcher.addDispatch(new BlockModelSlab<>(AetherBlocks.SLAB_COBBLE_HOLYSTONE));
        dispatcher.addDispatch(new BlockModelSlab<>(AetherBlocks.SLAB_BRICK_HOLYSTONE));
        dispatcher.addDispatch(new BlockModelSlab<>(AetherBlocks.SLAB_HOLYSTONE_POLISHED));
        dispatcher.addDispatch(new BlockModelSlab<>(AetherBlocks.SLAB_BRICK_ZANITE));


        dispatcher.addDispatch(new BlockModelGenericTorch<>(
            AetherBlocks.TORCH_AMBROSIUM,
            "aether:block/torch_ambrosium"
        ).render3D(false));


        dispatcher.addDispatch(new BlockModelGenericLantern<>(
            AetherBlocks.LANTERN_FIREFLY_SILVER,
            BlockModelDispatcher.loadDataModel("aether:block/lantern_firefly_silver"),
            BlockModelDispatcher.loadDataModel("aether:block/lantern_firefly_silver_hanging")
        ));


    }

    public void initItemModels(ItemModelDispatcher dispatcher) {
        this.setItemToolModels(dispatcher);
        this.setItemArmorModels(dispatcher);
        this.setItemBlockModels(dispatcher);

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.MEDAL_VICTORY, false).setIcon("aether:item/medal_victory"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.KEY_BRONZE, false).setIcon("aether:item/key_bronze"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.KEY_SILVER, false).setIcon("aether:item/key_silver"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.KEY_GOLD, false).setIcon("aether:item/key_gold"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.EGG_MOA_BLUE, false).setIcon("aether:item/egg_moa_blue"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.EGG_MOA_WHITE, false).setIcon("aether:item/egg_moa_white"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.EGG_MOA_BLACK, false).setIcon("aether:item/egg_moa_black"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.RECORD_AETHER, false).setIcon("aether:item/record_aether"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.RECORD_MORNING, false).setIcon("aether:item/record_morning"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.RECORD_DAWN, false).setIcon("aether:item/record_dawn"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.RECORD_NETHER, false).setIcon("aether:item/record_nether"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.AMBER, false).setIcon("aether:item/amber"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.PETAL_AECHOR, false).setIcon("aether:item/petal_aechor"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.STICK_SKYROOT, false).setIcon("aether:item/stick_skyroot"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.AMBROSIUM, false).setIcon("aether:item/ambrosium"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ZANITE, false).setIcon("aether:item/zanite"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ORE_RAW_GRAVITITE, false).setIcon("aether:item/ore_raw_gravitite"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.BUCKET_SKYROOT, false).setIcon("aether:item/bucket_skyroot"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.BUCKET_SKYROOT_WATER, false).setIcon("aether:item/bucket_skyroot_water"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.BUCKET_SKYROOT_MILK, false).setIcon("aether:item/bucket_skyroot_milk"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.BUCKET_SKYROOT_POISON, false).setIcon("aether:item/bucket_skyroot_poison"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.BUCKET_SKYROOT_REMEDY, false).setIcon("aether:item/bucket_skyroot_remedy"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.BUCKET_SKYROOT_ICECREAM, false).setIcon("aether:item/bucket_skyroot_icecream"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.FOOD_HEALING_STONE, false).setIcon("aether:item/food_healing_stone"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.FOOD_GUMMY_BLUE, false).setIcon("aether:item/food_sweet_blue"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.FOOD_GUMMY_GOLD, false).setIcon("aether:item/food_sweet_gold"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.LIFESHARD, false).setIcon("aether:item/food_lifeshard"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.PARACHUTE_CLOUD, false).setIcon("aether:item/parachute"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.PARACHUTE_CLOUD_GOLD, false).setIcon("aether:item/parachute_gold"));


        dispatcher.addDispatch(new ItemModelStandard(AetherItems.LANTERN_FIREFLY_SILVER, false).setIcon("aether:item/lantern_firefly_silver").setFullBright());

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.AMMO_WINDBALL, false).setIcon("aether:item/windball"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.PROJECTILE_FIRE, false).setIcon("aether:item/projectile_fire").setFullBright());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.PROJECTILE_ICE, false).setIcon("aether:item/projectile_ice"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.PROJECTILE_LIGHTNING, false).setIcon("aether:item/projectile_lightning"));


        dispatcher.addDispatch((new ItemModelBlock((ItemBlock<?>) AetherBlocks.TORCH_AMBROSIUM.asItem())).setFullBright());

    }

    public void initEntityModels(EntityRendererDispatcher dispatcher) {
        this.setProjectileModels(dispatcher);
        this.setMobEnemyModels(dispatcher);
        this.setMobBossModels(dispatcher);
        this.setMobAnimalModels(dispatcher);

        MobRendererPlayer playerRenderer = (MobRendererPlayer) dispatcher.getRenderer(Player.class);
        playerRenderer
            .setModel("aether.accessory.base", "geometry.humanoid.armor", 1.1)
            .setModel("aether.accessory.heart", "geometry.humanoid.armor", 1.0)
            .setModel("aether.accessory.bubble", "geometry.humanoid.armor", 1.0)
            .setModel("aether.accessory.feather", "geometry.aether.player.accessory.feather", 1.0)
            .setModel("aether.accessory.shield", "geometry.humanoid.armor", 1.5)
            .setModel("aether.accessory.gloves", "geometry.humanoid.armor", 1.0)
            .setModel("aether.accessory.quiver", "geometry.humanoid.armor", 1.05);

        dispatcher.assignRenderer(EntityFloatingBlock.class, new EntityRendererFloatingBlock());
        dispatcher.assignRenderer(EntityParachute.class, new EntityRendererParachute());
        dispatcher.assignRenderer(EntityParachuteGold.class, new EntityRendererParachuteGold());
    }

    public void initBlockColors(@NonNull BlockColorDispatcher dispatcher) {
        dispatcher.addDispatch(AetherBlocks.GRASS_AETHER, new BlockColorCustom(AetherClient.grassAether));
        dispatcher.addDispatch(AetherBlocks.TALLGRASS_AETHER, new BlockColorCustom(AetherClient.grassAether));

        dispatcher.addDispatch(AetherBlocks.HOLYSTONE_MOSSY, new BlockColorCustom(AetherClient.grassAether));

        dispatcher.addDispatch(AetherBlocks.LEAVES_SKYROOT, new BlockColorCustom(AetherClient.skyroot));
        dispatcher.addDispatch(AetherBlocks.LEAVES_OAK_GOLDEN, new BlockColorCustom(AetherClient.oakGolden));
    }

    private void setBlockSkyrootModels(@NonNull BlockModelDispatcher dispatcher) {
        dispatcher.addDispatch(new BlockModelRetroStandard<>(AetherBlocks.PLANKS_SKYROOT)
            .setRetroAllTextures("aether:block/planks_skyroot/skyroot_retro")
            .setAllTextures("aether:block/planks_skyroot/skyroot"));

        dispatcher.addDispatch(new BlockModelPaintedSkyrootPlanks<>(AetherBlocks.PLANKS_SKYROOT_PAINTED));

        dispatcher.addDispatch(new BlockModelDoor<>(AetherBlocks.DOOR_PLANKS_SKYROOT_BOTTOM)
            .setTex("aether:block/door/skyroot/frame", TOP_BOTTOM)
            .setTex("aether:block/door/skyroot/bottom", SIDES));
        dispatcher.addDispatch(new BlockModelDoor<>(AetherBlocks.DOOR_PLANKS_SKYROOT_TOP)
            .setTex("aether:block/door/skyroot/frame", TOP_BOTTOM)
            .setTex("aether:block/door/skyroot/top", SIDES));
        dispatcher.addDispatch(new BlockModelPaintedSkyrootDoor<>(AetherBlocks.DOOR_PLANKS_SKYROOT_PAINTED_BOTTOM, false)
            .setTex("aether:block/door/skyroot/frame", TOP_BOTTOM)
            .setTex("aether:block/door/skyroot/bottom", SIDES));
        dispatcher.addDispatch(new BlockModelPaintedSkyrootDoor<>(AetherBlocks.DOOR_PLANKS_SKYROOT_PAINTED_TOP, true)
            .setTex("aether:block/door/skyroot/frame", TOP_BOTTOM)
            .setTex("aether:block/door/skyroot/top", SIDES));

        dispatcher.addDispatch(new BlockModelEmpty<>(AetherBlocks.SIGN_POST_PLANKS_SKYROOT)
            .setAllTextures("aether:block/planks_skyroot/skyroot"));
        dispatcher.addDispatch(new BlockModelEmpty<>(AetherBlocks.SIGN_WALL_PLANKS_SKYROOT)
            .setAllTextures("aether:block/planks_skyroot/skyroot"));

        dispatcher.addDispatch(new BlockModelPaintedSkyrootSign<>(AetherBlocks.SIGN_POST_PLANKS_SKYROOT_PAINTED));
        dispatcher.addDispatch(new BlockModelPaintedSkyrootSign<>(AetherBlocks.SIGN_WALL_PLANKS_SKYROOT_PAINTED));

        dispatcher.addDispatch(new BlockModelTrapDoor<>(AetherBlocks.TRAPDOOR_PLANKS_SKYROOT)
            .setTex("aether:block/trapdoor/skyroot/top", TOP_BOTTOM)
            .setTex("aether:block/trapdoor/skyroot/side", Side.EAST, Side.NORTH, Side.SOUTH, Side.WEST));
        dispatcher.addDispatch(new BlockModelPaintedSkyrootTrapDoor<>(AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED));

        dispatcher.addDispatch(new BlockModelGenericChest<>(AetherBlocks.CHEST_PLANKS_SKYROOT,
            BlockModelDispatcher.loadDataModel("aether:block/chest/single/skyroot"),
            BlockModelDispatcher.loadDataModel("aether:block/chest/left/skyroot"),
            BlockModelDispatcher.loadDataModel("aether:block/chest/right/skyroot")));
        dispatcher.addDispatch(new BlockModelPaintedSkyrootChest<>(AetherBlocks.CHEST_PLANKS_SKYROOT_PAINTED));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.BUTTON_PLANKS_SKYROOT)
            .setAllTextures("aether:block/planks_skyroot/skyroot"));
        dispatcher.addDispatch(new BlockModelPaintedSkyrootButton<>(AetherBlocks.BUTTON_PLANKS_SKYROOT_PAINTED)
            .withCustomItemBounds(0.3125, 0.375, 0.375, 0.6875, 0.625, 0.625));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.PRESSURE_PLATE_PLANKS_SKYROOT)
            .setAllTextures("aether:block/planks_skyroot/skyroot"));
        dispatcher.addDispatch(new BlockModelPaintedSkyrootPressurePlate<>(AetherBlocks.PRESSURE_PLATE_PLANKS_SKYROOT_PAINTED)
            .withCustomItemBounds(0.0, 0.375, 0.0, 1.0, 0.625, 1.0));

        dispatcher.addDispatch(AetherBlocks.FENCE_PLANKS_SKYROOT, new BlockModelFence<>(AetherBlocks.FENCE_PLANKS_SKYROOT)
            .setAllTextures("aether:block/planks_skyroot/skyroot"));
        dispatcher.addDispatch(new BlockModelPaintedSkyrootFence<>(AetherBlocks.FENCE_PLANKS_SKYROOT_PAINTED));

        dispatcher.addDispatch(AetherBlocks.FENCEGATE_PLANKS_SKYROOT, new BlockModelFenceGate<>(AetherBlocks.FENCEGATE_PLANKS_SKYROOT)
            .setAllTextures("aether:block/planks_skyroot/skyroot"));
        dispatcher.addDispatch(new BlockModelPaintedSkyrootFenceGate<>(AetherBlocks.FENCEGATE_PLANKS_SKYROOT_PAINTED));

        dispatcher.addDispatch(new BlockModelSlab<>(AetherBlocks.SLAB_PLANKS_SKYROOT));
        dispatcher.addDispatch(new BlockModelPaintedSkyrootSlab<>(AetherBlocks.SLAB_PLANKS_SKYROOT_PAINTED));

        dispatcher.addDispatch(new BlockModelStairs<>(AetherBlocks.STAIRS_PLANKS_SKYROOT));
        dispatcher.addDispatch(new BlockModelPaintedSkyrootStairs<>(AetherBlocks.STAIRS_PLANKS_SKYROOT_PAINTED));
    }

    private void setBlockMachineModels(@NonNull BlockModelDispatcher dispatcher) {
        dispatcher.addDispatch(new BlockModelEnchanter<>(AetherBlocks.ENCHANTER_IDLE)
            .setRetroTex("aether:block/enchanter/top_retro", Side.TOP)
            .setRetroTex("aether:block/enchanter/bottom_retro", Side.BOTTOM)
            .setRetroTex("aether:block/enchanter/idle_front_retro", Side.NORTH)
            .setRetroTex("aether:block/enchanter/side_retro", Side.EAST, Side.WEST, Side.SOUTH)
            .setTex("aether:block/enchanter/top", Side.TOP)
            .setTex("aether:block/enchanter/bottom", Side.BOTTOM)
            .setTex("aether:block/enchanter/idle_front", Side.NORTH)
            .setTex("aether:block/enchanter/side", Side.EAST, Side.WEST, Side.SOUTH));
        dispatcher.addDispatch(new BlockModelEnchanter<>(AetherBlocks.ENCHANTER_ACTIVE)
            .setRetroTex("aether:block/enchanter/top_retro", Side.TOP)
            .setRetroTex("aether:block/enchanter/bottom_retro", Side.BOTTOM)
            .setRetroTex("aether:block/enchanter/active_front_retro", Side.NORTH)
            .setRetroTex("aether:block/enchanter/side_retro", Side.EAST, Side.WEST, Side.SOUTH)
            .setTex("aether:block/enchanter/top", Side.TOP)
            .setTex("aether:block/enchanter/bottom", Side.BOTTOM)
            .setTex("aether:block/enchanter/active_front", Side.NORTH)
            .setTex("aether:block/enchanter/side", Side.EAST, Side.WEST, Side.SOUTH));

        dispatcher.addDispatch(new BlockModelFreezer<>(AetherBlocks.FREEZER_IDLE)
            .setRetroTex("aether:block/freezer/idle_top_retro", Side.TOP)
            .setRetroTex("aether:block/freezer/bottom_retro", Side.BOTTOM)
            .setRetroTex("aether:block/freezer/side_retro", Side.EAST, Side.WEST, Side.SOUTH, Side.NORTH)
            .setTex("aether:block/freezer/idle_top", Side.TOP)
            .setTex("aether:block/freezer/bottom", Side.BOTTOM)
            .setTex("aether:block/freezer/side", Side.EAST, Side.WEST, Side.SOUTH, Side.NORTH));
        dispatcher.addDispatch(new BlockModelFreezer<>(AetherBlocks.FREEZER_ACTIVE)
            .setRetroTex("aether:block/freezer/active_top_retro", Side.TOP)
            .setRetroTex("aether:block/freezer/bottom_retro", Side.BOTTOM)
            .setRetroTex("aether:block/freezer/side_retro", Side.EAST, Side.WEST, Side.SOUTH, Side.NORTH)
            .setTex("aether:block/freezer/active_top", Side.TOP)
            .setTex("aether:block/freezer/bottom", Side.BOTTOM)
            .setTex("aether:block/freezer/side", Side.EAST, Side.WEST, Side.SOUTH, Side.NORTH));

        dispatcher.addDispatch(new BlockModelIncubator<>(AetherBlocks.INCUBATOR_IDLE)
            .setTopFilled("aether:block/incubator/idle_top_filled")
            .setRetroTopFilled("aether:block/incubator/idle_top_retro_filled")
            .setRetroTex("aether:block/incubator/idle_top_retro", Side.TOP)
            .setRetroTex("aether:block/incubator/bottom_retro", Side.BOTTOM)
            .setRetroTex("aether:block/incubator/side_retro", Side.EAST, Side.WEST, Side.SOUTH, Side.NORTH)
            .setTex("aether:block/incubator/idle_top", Side.TOP)
            .setTex("aether:block/incubator/bottom", Side.BOTTOM)
            .setTex("aether:block/incubator/side", Side.EAST, Side.WEST, Side.SOUTH, Side.NORTH));
        dispatcher.addDispatch(new BlockModelIncubator<>(AetherBlocks.INCUBATOR_ACTIVE)
            .setTopFilled("aether:block/incubator/active_top_filled")
            .setRetroTopFilled("aether:block/incubator/active_top_retro_filled")
            .setRetroTex("aether:block/incubator/active_top_retro", Side.TOP)
            .setRetroTex("aether:block/incubator/bottom_retro", Side.BOTTOM)
            .setRetroTex("aether:block/incubator/side_retro", Side.EAST, Side.WEST, Side.SOUTH, Side.NORTH)
            .setTex("aether:block/incubator/active_top", Side.TOP)
            .setTex("aether:block/incubator/bottom", Side.BOTTOM)
            .setTex("aether:block/incubator/side", Side.EAST, Side.WEST, Side.SOUTH, Side.NORTH));


        dispatcher.addDispatch(new BlockModelDungeonDoor<>(AetherBlocks.DOOR_DUNGEON_BRONZE, 4, 4)
             .setRetroTex("aether:block/ctm/boss_door/bronze/back_retro", Side.sides)
             .setRetroTex("aether:block/ctm/boss_door/bronze/front_retro", Side.NORTH)
             .setParticleTexture(false, "aether:block/ctm/boss_door/bronze/particle")
            .setParticleTexture(true, "aether:block/ctm/boss_door/bronze/particle_retro")

             .setTex("aether:block/ctm/boss_door/bronze/back", Side.sides)
             .setTex("aether:block/ctm/boss_door/bronze/front", Side.NORTH)
         );

        dispatcher.addDispatch(new BlockModelDungeonDoor<>(AetherBlocks.DOOR_DUNGEON_SILVER, 2, 3)
             .setRetroTex("aether:block/ctm/boss_door/silver/back_retro", Side.sides)
             .setRetroTex("aether:block/ctm/boss_door/silver/front_retro", Side.NORTH)
             .setParticleTexture(false, "aether:block/ctm/boss_door/silver/particle")
            .setParticleTexture(true, "aether:block/ctm/boss_door/silver/particle_retro")

             .setTex("aether:block/ctm/boss_door/silver/back", Side.sides)
             .setTex("aether:block/ctm/boss_door/silver/front", Side.NORTH)
         );

        dispatcher.addDispatch(new BlockModelDungeonDoor<>(AetherBlocks.DOOR_DUNGEON_GOLD, 3, 3)
             .setRetroTex("aether:block/ctm/boss_door/gold/back_retro", Side.sides)
             .setRetroTex("aether:block/ctm/boss_door/gold/front_retro", Side.NORTH)
             .setParticleTexture(false, "aether:block/ctm/boss_door/gold/particle")
            .setParticleTexture(true, "aether:block/ctm/boss_door/gold/particle_retro")

             .setTex("aether:block/ctm/boss_door/gold/back", Side.sides)
             .setTex("aether:block/ctm/boss_door/gold/front", Side.NORTH)
         );
    }

    private void setBlockDungeonModels(@NonNull BlockModelDispatcher dispatcher) {
        dispatcher.addDispatch(new BlockModelHorizontalRotation<>(AetherBlocks.CHEST_DUNGEON_BRONZE)
            .setTex("aether:block/chest/dungeon_bronze/front", Side.NORTH)
            .setTex("aether:block/chest/dungeon_bronze/side", Side.EAST, Side.WEST, Side.SOUTH)
            .setTex("aether:block/chest/dungeon_bronze/top", TOP_BOTTOM));

        dispatcher.addDispatch(new BlockModelHorizontalRotation<>(AetherBlocks.CHEST_DUNGEON_BRONZE_LOCKED)
            .setTex("aether:block/chest/dungeon_bronze/front_locked", Side.NORTH)
            .setTex("aether:block/chest/dungeon_bronze/side", Side.EAST, Side.WEST, Side.SOUTH)
            .setTex("aether:block/chest/dungeon_bronze/top", TOP_BOTTOM));

        dispatcher.addDispatch(new BlockModelHorizontalRotation<>(AetherBlocks.CHEST_DUNGEON_SILVER)
            .setTex("aether:block/chest/dungeon_silver/front", Side.NORTH)
            .setTex("aether:block/chest/dungeon_silver/side", Side.EAST, Side.WEST, Side.SOUTH)
            .setTex("aether:block/chest/dungeon_silver/top", TOP_BOTTOM));

        dispatcher.addDispatch(new BlockModelHorizontalRotation<>(AetherBlocks.CHEST_DUNGEON_SILVER_LOCKED)
            .setTex("aether:block/chest/dungeon_silver/front_locked", Side.NORTH)
            .setTex("aether:block/chest/dungeon_silver/side", Side.EAST, Side.WEST, Side.SOUTH)
            .setTex("aether:block/chest/dungeon_silver/top", TOP_BOTTOM));

        dispatcher.addDispatch(new BlockModelHorizontalRotation<>(AetherBlocks.CHEST_DUNGEON_GOLD)
            .setTex("aether:block/chest/dungeon_gold/front", Side.NORTH)
            .setTex("aether:block/chest/dungeon_gold/side", Side.EAST, Side.WEST, Side.SOUTH)
            .setTex("aether:block/chest/dungeon_gold/top", TOP_BOTTOM));

        dispatcher.addDispatch(new BlockModelHorizontalRotation<>(AetherBlocks.CHEST_DUNGEON_GOLD_LOCKED)
            .setTex("aether:block/chest/dungeon_gold/front_locked", Side.NORTH)
            .setTex("aether:block/chest/dungeon_gold/side", Side.EAST, Side.WEST, Side.SOUTH)
            .setTex("aether:block/chest/dungeon_gold/top", TOP_BOTTOM));

        dispatcher.addDispatch(new BlockModelMimic<>(AetherBlocks.CHEST_MIMIC_SKYROOT, "aether:block/chest/skyroot/")
            .setAllTextures("aether:block/chest/skyroot/top"));

        dispatcher.addDispatch(new BlockModelMimic<>(AetherBlocks.CHEST_MIMIC_OAK, "minecraft:block/chest/planks/")
            .setAllTextures("minecraft:block/chest/planks/top"));

        dispatcher.addDispatch(new BlockModelMimic<>(AetherBlocks.CHEST_MIMIC_BRONZE, "aether:block/chest/dungeon_bronze/")
            .setAllTextures("aether:block/chest/dungeon_bronze/top"));

        dispatcher.addDispatch(new BlockModelMimic<>(AetherBlocks.CHEST_MIMIC_SILVER, "aether:block/chest/dungeon_silver/")
            .setAllTextures("aether:block/chest/dungeon_silver/top"));

        dispatcher.addDispatch(new BlockModelMimic<>(AetherBlocks.CHEST_MIMIC_GOLD, "aether:block/chest/dungeon_gold/")
            .setAllTextures("aether:block/chest/dungeon_gold/top"));

        dispatcher.addDispatch(new BlockModelPaintedSkyrootMimic<>(AetherBlocks.CHEST_MIMIC_SKYROOT_PAINTED));
        dispatcher.addDispatch(new BlockModelPaintedOakMimic<>(AetherBlocks.CHEST_MIMIC_OAK_PAINTED));


        dispatcher.addDispatch(new BlockModelRetroStandard<>(AetherBlocks.CARVED_STONE_LOCKED)
            .setRetroAllTextures("aether:block/dungeon/carved_retro")
            .setAllTextures("aether:block/dungeon/carved"));
        dispatcher.addDispatch(new BlockModelRetroStandard<>(AetherBlocks.CARVED_STONE_LIGHT_LOCKED)
            .setRetroAllTextures("aether:block/dungeon/carved_glow_retro")
            .setAllTextures("aether:block/dungeon/carved_glow")
            );

        dispatcher.addDispatch(new BlockModelRetroStandard<>(AetherBlocks.CARVED_ANGELIC_LOCKED)
            .setRetroAllTextures("aether:block/dungeon/angelic_retro")
            .setAllTextures("aether:block/dungeon/angelic"));
        dispatcher.addDispatch(new BlockModelRetroStandard<>(AetherBlocks.CARVED_ANGELIC_LIGHT_LOCKED)
            .setRetroAllTextures("aether:block/dungeon/angelic_glow_retro")
            .setAllTextures("aether:block/dungeon/angelic_glow")
            );

        dispatcher.addDispatch(new BlockModelRetroStandard<>(AetherBlocks.CARVED_HELLFIRE_LOCKED)
            .setRetroAllTextures("aether:block/dungeon/hellfire_retro")
            .setAllTextures("aether:block/dungeon/hellfire"));
        dispatcher.addDispatch(new BlockModelRetroStandard<>(AetherBlocks.CARVED_HELLFIRE_LIGHT_LOCKED)
            .setRetroAllTextures("aether:block/dungeon/hellfire_glow_retro")
            .setAllTextures("aether:block/dungeon/hellfire_glow")
            );

        dispatcher.addDispatch(new BlockModelRetroStandard<>(AetherBlocks.CARVED_STONE_TRAPPED)
            .setRetroAllTextures("aether:block/dungeon/carved_retro")
            .setAllTextures("aether:block/dungeon/carved"));
        dispatcher.addDispatch(new BlockModelRetroStandard<>(AetherBlocks.CARVED_STONE_TRAPPED_LOCKED)
            .setRetroAllTextures("aether:block/dungeon/carved_retro")
            .setAllTextures("aether:block/dungeon/carved"));


        dispatcher.addDispatch(new BlockModelRetroStandard<>(AetherBlocks.CARVED_ANGELIC_TRAPPED)
            .setRetroAllTextures("aether:block/dungeon/angelic_retro")
            .setAllTextures("aether:block/dungeon/angelic"));
        dispatcher.addDispatch(new BlockModelRetroStandard<>(AetherBlocks.CARVED_ANGELIC_TRAPPED_LOCKED)
            .setRetroAllTextures("aether:block/dungeon/angelic_retro")
            .setAllTextures("aether:block/dungeon/angelic"));

        dispatcher.addDispatch(new BlockModelRetroStandard<>(AetherBlocks.CARVED_HELLFIRE_TRAPPED)
            .setRetroAllTextures("aether:block/dungeon/hellfire_retro")
            .setAllTextures("aether:block/dungeon/hellfire"));
        dispatcher.addDispatch(new BlockModelRetroStandard<>(AetherBlocks.CARVED_HELLFIRE_TRAPPED_LOCKED)
            .setRetroAllTextures("aether:block/dungeon/hellfire_retro")
            .setAllTextures("aether:block/dungeon/hellfire"));

        dispatcher.addDispatch(new BlockModelRetroStandard<>(AetherBlocks.CARVED_STONE)
            .setRetroAllTextures("aether:block/dungeon/carved_retro")
            .setAllTextures("aether:block/dungeon/carved"));
        dispatcher.addDispatch(new BlockModelRetroStandard<>(AetherBlocks.CARVED_STONE_LIGHT)
            .setRetroAllTextures("aether:block/dungeon/carved_glow_retro")
            .setAllTextures("aether:block/dungeon/carved_glow")
            );

        dispatcher.addDispatch(new BlockModelRetroStandard<>(AetherBlocks.CARVED_ANGELIC)
            .setRetroAllTextures("aether:block/dungeon/angelic_retro")
            .setAllTextures("aether:block/dungeon/angelic"));
        dispatcher.addDispatch(new BlockModelRetroStandard<>(AetherBlocks.CARVED_ANGELIC_LIGHT)
            .setRetroAllTextures("aether:block/dungeon/angelic_glow_retro")
            .setAllTextures("aether:block/dungeon/angelic_glow")
            );

        dispatcher.addDispatch(new BlockModelRetroStandard<>(AetherBlocks.CARVED_HELLFIRE)
            .setRetroAllTextures("aether:block/dungeon/hellfire_retro")
            .setAllTextures("aether:block/dungeon/hellfire"));
        dispatcher.addDispatch(new BlockModelRetroStandard<>(AetherBlocks.CARVED_HELLFIRE_LIGHT)
            .setRetroAllTextures("aether:block/dungeon/hellfire_glow_retro")
            .setAllTextures("aether:block/dungeon/hellfire_glow")
            );

        dispatcher.addDispatch(new BlockModelRetroAxisAligned<>(AetherBlocks.PILLAR)
            .setRetroTex("aether:block/pillar/side", SIDES)
            .setRetroTex("aether:block/pillar/top_retro", TOP_BOTTOM)
            .setTex("aether:block/pillar/side", SIDES)
            .setTex("aether:block/pillar/top", TOP_BOTTOM));
        dispatcher.addDispatch(new BlockModelRetroAxisAligned<>(AetherBlocks.PILLAR_CAPSTONE)
            .setRetroTex("aether:block/pillar_capstone/side", SIDES)
            .setRetroTex("aether:block/pillar_capstone/top_retro", TOP_BOTTOM)
            .setTex("aether:block/pillar_capstone/side", SIDES)
            .setTex("aether:block/pillar_capstone/top", TOP_BOTTOM));

        dispatcher.addDispatch(new BlockModelSlab<>(AetherBlocks.SLAB_CARVED_STONE));
        dispatcher.addDispatch(new BlockModelSlab<>(AetherBlocks.SLAB_CARVED_ANGELIC));
        dispatcher.addDispatch(new BlockModelSlab<>(AetherBlocks.SLAB_CARVED_HELLFIRE));

        dispatcher.addDispatch(new BlockModelStairs<>(AetherBlocks.STAIRS_CARVED_STONE));
        dispatcher.addDispatch(new BlockModelStairs<>(AetherBlocks.STAIRS_CARVED_ANGELIC));
        dispatcher.addDispatch(new BlockModelStairs<>(AetherBlocks.STAIRS_CARVED_HELLFIRE));

    }

    private void setBlockPlantModels(@NonNull BlockModelDispatcher dispatcher) {
        dispatcher.addDispatch(new BlockModelAetherLog<>(AetherBlocks.LOG_SKYROOT)
            .setRetroTex("aether:block/log/skyroot_side_retro", SIDES)
            .setRetroTex("aether:block/log/skyroot_top_retro", TOP_BOTTOM)
            .setTex("aether:block/log/skyroot_side", SIDES)
            .setTex("aether:block/log/skyroot_top", TOP_BOTTOM));
        dispatcher.addDispatch(new BlockModelAetherLog<>(AetherBlocks.LOG_OAK_GOLDEN)
            .setRetroTex("aether:block/log/oak_golden_side_retro", SIDES)
            .setRetroTex("aether:block/log/oak_golden_top_retro", TOP_BOTTOM)
            .setTex("aether:block/log/oak_golden_side", SIDES)
            .setTex("aether:block/log/oak_golden_top", TOP_BOTTOM));

        dispatcher.addDispatch(new BlockModelLeavesAether<>(AetherBlocks.LEAVES_SKYROOT, "aether:block/leaves/skyroot", "aether:block/leaves/skyroot_retro"));
        dispatcher.addDispatch(new BlockModelLeavesAether<>(AetherBlocks.LEAVES_OAK_GOLDEN, "aether:block/leaves/oak_golden", "aether:block/leaves/oak_golden_retro"));

        dispatcher.addDispatch(new BlockModelRetroCrossedSquares<>(AetherBlocks.SAPLING_SKYROOT, "aether:block/sapling/skyroot_retro")
            .setAllTextures("aether:block/sapling/skyroot"));
        dispatcher.addDispatch(new BlockModelRetroCrossedSquares<>(AetherBlocks.SAPLING_OAK_GOLDEN, "aether:block/sapling/oak_golden_retro")
            .setAllTextures("aether:block/sapling/oak_golden"));

        dispatcher.addDispatch(new BlockModelFlowerStackableAether<>(AetherBlocks.FLOWER_PURPLE, "aether:block/flower_purple/"));
        dispatcher.addDispatch(new BlockModelFlowerStackableAether<>(AetherBlocks.FLOWER_WHITE, "aether:block/flower_white/"));

        dispatcher.addDispatch(new BlockModelAetherTallgrass<>(AetherBlocks.TALLGRASS_AETHER).setAllTextures("aether:block/tallgrass_aether"));

        dispatcher.addDispatch(new BlockModelCrossedSquares<>(AetherBlocks.DEADBUSH_AETHER).setAllTextures("aether:block/deadbush_aether"));
    }

    private void setBlockCloudModels(@NonNull BlockModelDispatcher dispatcher) {
        dispatcher.addDispatch(new BlockModelTransparent<>(AetherBlocks.AERCLOUD_WHITE, false).onRenderLayer(1)
            .setAllTextures("aether:block/aercloud_white"));
        dispatcher.addDispatch(new BlockModelTransparent<>(AetherBlocks.AERCLOUD_BLUE, false).onRenderLayer(1)
            .setAllTextures("aether:block/aercloud_blue"));
        dispatcher.addDispatch(new BlockModelTransparent<>(AetherBlocks.AERCLOUD_GOLD, false).onRenderLayer(1)
            .setAllTextures("aether:block/aercloud_gold"));

        dispatcher.addDispatch(new BlockModelTransparent<>(AetherBlocks.AEROGEL, false).onRenderLayer(1)
            .setAllTextures("aether:block/aerogel"));
    }

    private void setItemArmorModels(@NonNull ItemModelDispatcher dispatcher) {
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.AMMO_HAMMER_HEAD, false).setIcon("aether:item/notch_wave"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_SHIELD_REPULSION, false).setIcon("aether:item/tool_shield_repulsion"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.AMMO_DART_GOLDEN, false).setIcon("aether:item/dart_golden"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.AMMO_DART_POISON, false).setIcon("aether:item/dart_poison"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.AMMO_DART_ENCHANTED, false).setIcon("aether:item/dart_enchanted"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.AMMO_ARROW_FLAMING, false).setIcon("aether:item/ammo_arrow_flaming").setFullBright());


        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_HELMET_ZANITE, false).setIcon("aether:item/armor_helmet_zanite"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CHESTPLATE_ZANITE, false).setIcon("aether:item/armor_chestplate_zanite"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_LEGGINGS_ZANITE, false).setIcon("aether:item/armor_leggings_zanite"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_BOOTS_ZANITE, false).setIcon("aether:item/armor_boots_zanite"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_HELMET_GRAVITITE, false).setIcon("aether:item/armor_helmet_gravitite"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CHESTPLATE_GRAVITITE, false).setIcon("aether:item/armor_chestplate_gravitite"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_LEGGINGS_GRAVITITE, false).setIcon("aether:item/armor_leggings_gravitite"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_BOOTS_GRAVITITE, false).setIcon("aether:item/armor_boots_gravitite"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_HELMET_OBSIDIAN, false).setIcon("aether:item/armor_helmet_obsidian"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CHESTPLATE_OBSIDIAN, false).setIcon("aether:item/armor_chestplate_obsidian"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_LEGGINGS_OBSIDIAN, false).setIcon("aether:item/armor_leggings_obsidian"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_BOOTS_OBSIDIAN, false).setIcon("aether:item/armor_boots_obsidian"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_HELMET_PHOENIX, false).setIcon("aether:item/armor_helmet_phoenix").setFullBright());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CHESTPLATE_PHOENIX, false).setIcon("aether:item/armor_chestplate_phoenix").setFullBright());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_LEGGINGS_PHOENIX, false).setIcon("aether:item/armor_leggings_phoenix").setFullBright());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_BOOTS_PHOENIX, false).setIcon("aether:item/armor_boots_phoenix").setFullBright());

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_HELMET_NEPTUNE, false).setIcon("aether:item/armor_helmet_neptune"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CHESTPLATE_NEPTUNE, false).setIcon("aether:item/armor_chestplate_neptune"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_LEGGINGS_NEPTUNE, false).setIcon("aether:item/armor_leggings_neptune"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_BOOTS_NEPTUNE, false).setIcon("aether:item/armor_boots_neptune"));


        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_REGEN, false).setIcon("aether:item/accessory_healing"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_BUBBLE, false).setIcon("aether:item/accessory_bubble"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_FEATHER_GOLD, false).setIcon("aether:item/accessory_feather"));


        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_LEATHER, false).setIcon("aether:item/armor_pendant_leather"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_CHAIN, false).setIcon("aether:item/armor_pendant_chain"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_IRON, false).setIcon("aether:item/armor_pendant_iron"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_GOLD, false).setIcon("aether:item/armor_pendant_gold"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_DIAMOND, false).setIcon("aether:item/armor_pendant_diamond"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_STEEL, false).setIcon("aether:item/armor_pendant_steel"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_ZANITE, false).setIcon("aether:item/armor_pendant_zanite"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_GRAVITITE, false).setIcon("aether:item/armor_pendant_gravitite"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_ICE, false).setIcon("aether:item/armor_pendant_ice"));


        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_LEATHER, false).setIcon("aether:item/armor_gloves_leather"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_CHAIN, false).setIcon("aether:item/armor_gloves_chain"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_IRON, false).setIcon("aether:item/armor_gloves_iron"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_GOLD, false).setIcon("aether:item/armor_gloves_gold"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_DIAMOND, false).setIcon("aether:item/armor_gloves_diamond"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_STEEL, false).setIcon("aether:item/armor_gloves_steel"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_ZANITE, false).setIcon("aether:item/armor_gloves_zanite"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_GRAVITITE, false).setIcon("aether:item/armor_gloves_gravitite"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_OBSIDIAN, false).setIcon("aether:item/armor_gloves_obsidian"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_PHOENIX, false).setIcon("aether:item/armor_gloves_phoenix").setFullBright());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_NEPTUNE, false).setIcon("aether:item/armor_gloves_neptune"));


        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_AGILITY, false).setIcon("aether:item/armor_cape_agility"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_SWET, false).setIcon("aether:item/armor_cape_swet"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_INVISIBILITY, false).setIcon("aether:item/armor_cape_invisibility"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_WHITE, false).setIcon("aether:item/armor_cape_white"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_SILVER, false).setIcon("aether:item/armor_cape_silver"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_GRAY, false).setIcon("aether:item/armor_cape_gray"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_BLACK, false).setIcon("aether:item/armor_cape_black"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_BROWN, false).setIcon("aether:item/armor_cape_brown"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_RED, false).setIcon("aether:item/armor_cape_red"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_YELLOW, false).setIcon("aether:item/armor_cape_yellow"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_ORANGE, false).setIcon("aether:item/armor_cape_orange"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_LIME, false).setIcon("aether:item/armor_cape_lime"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_GREEN, false).setIcon("aether:item/armor_cape_green"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_CYAN, false).setIcon("aether:item/armor_cape_cyan"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_BLUE, false).setIcon("aether:item/armor_cape_blue"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_LIGHTBLUE, false).setIcon("aether:item/armor_cape_lightblue"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_PURPLE, false).setIcon("aether:item/armor_cape_purple"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_MAGENTA, false).setIcon("aether:item/armor_cape_magenta"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_PINK, false).setIcon("aether:item/armor_cape_pink"));
    }

    private static @NonNull ItemModelStandard handheldTool(net.minecraft.core.item.Item item, String icon) {
        return new ItemModelStandard(item, true).setIcon(icon)
            .setDisplayPos(org.useless.dragonfly.DisplayPos.FIRST_PERSON_RIGHT_HAND, ItemModelDispatcher.HANDHELD_FIRST_PERSON_RIGHT_HAND)
            .setDisplayPos(org.useless.dragonfly.DisplayPos.FIRST_PERSON_LEFT_HAND, ItemModelDispatcher.HANDHELD_FIRST_PERSON_LEFT_HAND)
            .setDisplayPos(org.useless.dragonfly.DisplayPos.THIRD_PERSON_RIGHT_HAND, ItemModelDispatcher.HANDHELD_THIRD_PERSON_RIGHT_HAND)
            .setDisplayPos(org.useless.dragonfly.DisplayPos.THIRD_PERSON_LEFT_HAND, ItemModelDispatcher.HANDHELD_THIRD_PERSON_LEFT_HAND);
    }

    private void setItemToolModels(@NonNull ItemModelDispatcher dispatcher) {
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_SWORD_SKYROOT, "aether:item/tool_sword_skyroot"));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_SHOVEL_SKYROOT, "aether:item/tool_shovel_skyroot"));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_PICKAXE_SKYROOT, "aether:item/tool_pickaxe_skyroot"));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_AXE_SKYROOT, "aether:item/tool_axe_skyroot"));

        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_SWORD_HOLYSTONE, "aether:item/tool_sword_holystone"));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_SHOVEL_HOLYSTONE, "aether:item/tool_shovel_holystone"));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_PICKAXE_HOLYSTONE, "aether:item/tool_pickaxe_holystone"));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_AXE_HOLYSTONE, "aether:item/tool_axe_holystone"));

        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_SWORD_ZANITE, "aether:item/tool_sword_zanite"));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_SHOVEL_ZANITE, "aether:item/tool_shovel_zanite"));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_PICKAXE_ZANITE, "aether:item/tool_pickaxe_zanite"));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_AXE_ZANITE, "aether:item/tool_axe_zanite"));

        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_SWORD_GRAVITITE, "aether:item/tool_sword_gravitite"));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_SHOVEL_GRAVITITE, "aether:item/tool_shovel_gravitite"));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_PICKAXE_GRAVITITE, "aether:item/tool_pickaxe_gravitite"));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_AXE_GRAVITITE, "aether:item/tool_axe_gravitite"));

        dispatcher.addDispatch(new ItemModelLance(AetherItems.TOOL_SWORD_VALKYRIE, true).setIcon("aether:item/tool_sword_valk"));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_SHOVEL_VALKYRIE, "aether:item/tool_shovel_valk"));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_PICKAXE_VALKYRIE, "aether:item/tool_pickaxe_valk"));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_AXE_VALKYRIE, "aether:item/tool_axe_valk"));

        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_KNIFE_LIGHTNING, "aether:item/tool_knife_lightning"));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_HAMMER_NOTCH, "aether:item/tool_hammer_notch"));
        dispatcher.addDispatch(new ItemModelBowPhoenix(AetherItems.TOOL_BOW_PHOENIX, true).setIcon("aether:item/tool_bow_phoenix").setFullBright()
            .setDisplayPos("firstperson_righthand", new org.useless.dragonfly.DisplayPos(0.070625f, 0.2f, 0.070625f, 0f, -90f, 25f, 0.68f, 0.68f, 0.68f))
            .setDisplayPos("firstperson_lefthand", new org.useless.dragonfly.DisplayPos(0.070625f, 0.2f, 0.070625f, 0f, 90f, -25f, 0.68f, 0.68f, 0.68f))
            .setDisplayPos("thirdperson_righthand", new org.useless.dragonfly.DisplayPos(-0.0625f, -0.125f, 0.15625f, -80f, 260f, -40f, 0.9f, 0.9f, 0.9f))
            .setDisplayPos("thirdperson_lefthand", new org.useless.dragonfly.DisplayPos(-0.0625f, -0.125f, 0.15625f, -80f, -280f, 40f, 0.9f, 0.9f, 0.9f)));
        dispatcher.addDispatch(new ItemModelShooter(AetherItems.TOOL_SHOOTER, false).setIcon("aether:item/shooter_gold"));

        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_SWORD_FLAME, "aether:item/tool_sword_element_fire").setFullBright());
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_SWORD_HOLY, "aether:item/tool_sword_element_holy"));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_SWORD_LIGHTNING, "aether:item/tool_sword_element_lightning"));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_SWORD_PIG, "aether:item/tool_knife_pig"));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_SWORD_VAMPIRE, "aether:item/tool_sword_vampire"));

        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_STAFF_NATURE, "aether:item/staff_nature"));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_STAFF_CLOUD, "aether:item/staff_cloud"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_DUNGEON_COMPASS, false).setIcon("aether:item/tool_dungeon_compass"));
    }

    private void setItemBlockModels(@NonNull ItemModelDispatcher dispatcher) {
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.DOOR_DUNGEON_BRONZE, false).setIcon("aether:item/door_dungeon_bronze"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.DOOR_DUNGEON_SILVER, false).setIcon("aether:item/door_dungeon_silver"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.DOOR_DUNGEON_GOLD, false).setIcon("aether:item/door_dungeon_gold"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.DOOR_GLASS_AMBROSIUM, false).setIcon("aether:item/door_glass_ambrosium"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.DOOR_SKYROOT, false).setIcon("aether:item/door_skyroot"));
        dispatcher.addDispatch(new ItemModelPaintedSkyrootDoor(AetherItems.DOOR_SKYROOT_PAINTED));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.SIGN_SKYROOT, false).setIcon("aether:item/sign_skyroot"));
        dispatcher.addDispatch(new ItemModelPaintedSkyrootSign(AetherItems.SIGN_SKYROOT_PAINTED));
    }

    private void setMobAnimalModels(@NonNull EntityRendererDispatcher dispatcher) {
        dispatcher.assignRenderer(MobSheepuff.class, new MobRendererSheepuff(0.7F));
        dispatcher.assignRenderer(MobPhow.class, new MobRendererPhow(0.7F));
        dispatcher.assignRenderer(MobMoaBlue.class, new MobRendererMoa(0.7F));
        dispatcher.assignRenderer(MobMoaWhite.class, new MobRendererMoa(0.7F));
        dispatcher.assignRenderer(MobMoaBlack.class, new MobRendererMoa(0.7F));
        dispatcher.assignRenderer(MobPhyg.class, new MobRendererPhyg(0.7F));
        dispatcher.assignRenderer(MobAerwhale.class, new MobRendererAerwhale(1.0F));
        dispatcher.assignRenderer(MobAerbunny.class, new MobRendererAerbunny(0.5F));
        dispatcher.assignRenderer(MobWhirly.class, new MobRendererWhirly(0.7F));
    }

    private void setMobBossModels(@NonNull EntityRendererDispatcher dispatcher) {
        dispatcher.assignRenderer(MobBossSlider.class, new MobRendererSlider(1.5F));
        dispatcher.assignRenderer(MobBossValkyrie.class, new MobRendererBossValkyrie(0.5F));
        dispatcher.assignRenderer(MobBossSunspirit.class, new MobRendererSunspirit());
        dispatcher.assignRenderer(MobFireMinion.class, new MobRendererFireMinion(0.4F));
    }

    private void setMobEnemyModels(@NonNull EntityRendererDispatcher dispatcher) {
        dispatcher.assignRenderer(MobSentry.class, new MobRendererSentry(0.6F));
        dispatcher.assignRenderer(MobAechorPlant.class, new MobRendererAechorPlant(0.3F));
        dispatcher.assignRenderer(MobSwet.class, new MobRendererSwet(1.0f));
        dispatcher.assignRenderer(MobSwetGold.class, new MobRendererSwet(1.0f));
        dispatcher.assignRenderer(MobZephyr.class, new MobRendererZephyr(0.5F));
        dispatcher.assignRenderer(MobMimic.class, new MobRendererMimic(0.7f));
        dispatcher.assignRenderer(MobCockatrice.class, new MobRendererCockatrice(0.7F));
        dispatcher.assignRenderer(MobValkyrie.class, new MobRendererValkyrie(0.5F));
        dispatcher.assignRenderer(MobTempest.class, new MobRendererTempest(0.7F));
    }

    private void setProjectileModels(@NonNull EntityRendererDispatcher dispatcher) {
        dispatcher.assignRenderer(ProjectileHammerHead.class, new EntityRendererSprite<>(AetherItems.AMMO_HAMMER_HEAD).setScale(2.0f));
        dispatcher.assignRenderer(ProjectileWindball.class, new EntityRendererSprite<>(AetherItems.AMMO_WINDBALL).setScale(4.0F).setFullBright());
        dispatcher.assignRenderer(ProjectileElementFire.class, new EntityRendererSprite<>(AetherItems.PROJECTILE_FIRE).setScale(3.0F).setFullBright());
        dispatcher.assignRenderer(ProjectileElementIce.class, new EntityRendererSprite<>(AetherItems.PROJECTILE_ICE).setScale(3.0F).setFullBright());
        dispatcher.assignRenderer(ProjectileElementLightning.class, new EntityRendererSprite<>(AetherItems.PROJECTILE_LIGHTNING).setScale(3.0F).setFullBright());
        dispatcher.assignRenderer(ProjectileDart.class, new EntityRendererDart());
        dispatcher.assignRenderer(ProjectileNeedle.class, new EntityRendererNeedle());
        dispatcher.assignRenderer(ProjectileArrowFlaming.class, new EntityRendererArrowFlaming());
        dispatcher.assignRenderer(ProjectileKnifeLightning.class, new EntityRendererKnifeLightning());
    }
}
