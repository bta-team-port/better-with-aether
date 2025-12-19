package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorCustom;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.*;
import net.minecraft.client.render.entity.EntityRendererSprite;
import net.minecraft.client.render.item.model.ItemModelBlock;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.model.ModelSlime;
import net.minecraft.core.item.block.ItemBlock;
import net.minecraft.core.util.helper.Color;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.util.helper.Side;
import teamport.aether.AetherClient;
import teamport.aether.block.AetherBlocks;
import teamport.aether.block.entity.TileEntityRendererSignSkyroot;
import teamport.aether.block.entity.TileEntitySignSkyroot;
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
import teamport.aether.entity.boss.slider.MobBossSlider;
import teamport.aether.entity.boss.slider.MobRendererSlider;
import teamport.aether.entity.boss.slider.ModelSlider;
import teamport.aether.entity.boss.sunspirit.MobBossSunspirit;
import teamport.aether.entity.boss.sunspirit.MobRendererSunspirit;
import teamport.aether.entity.boss.valkyrie.queen.MobBossValkyrie;
import teamport.aether.entity.boss.valkyrie.queen.MobRendererBossValkyrie;
import teamport.aether.entity.floating_block.EntityFloatingBlock;
import teamport.aether.entity.floating_block.EntityRendererFloatingBlock;
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
import teamport.aether.entity.animal.whirly.MobRendererWhirly;
import teamport.aether.entity.monster.tempest.MobRendererTempest;
import teamport.aether.entity.animal.whirly.MobWhirly;
import teamport.aether.entity.monster.tempest.MobTempest;
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
import teamport.aether.models.dungeon.*;
import teamport.aether.models.skyroot.*;
import turniplabs.halplibe.helper.ModelHelper;
import turniplabs.halplibe.util.ModelEntrypoint;

import java.util.Arrays;

import static net.minecraft.client.render.block.model.BlockModelStandard.*;

@Environment(EnvType.CLIENT)
public class AetherModels implements ModelEntrypoint {
    Side[] TOP_BOTTOM = new Side[]{Side.TOP, Side.BOTTOM};
    Side[] SIDES = new Side[]{Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST};

    @Override
    public void initBlockModels(BlockModelDispatcher dispatcher) {

        this.setBlockDungeonModels(dispatcher);
        this.setBlockMachineModels(dispatcher);
        this.setBlockSkyrootModels(dispatcher);
        this.setBlockPlantModels(dispatcher);
        this.setBlockCloudModels(dispatcher);


        dispatcher.addDispatch(new BlockModelPortal<>(AetherBlocks.PORTAL_AETHER, "aether:block/portal_aether/")
            .setAllTextures(BLOCK_TEXTURES, "aether:block/portal_aether"));

        dispatcher.addDispatch(new BlockModelGrassAether<>(AetherBlocks.GRASS_AETHER)
            .setTex(BLOCK_TEXTURES, "aether:block/grass_aether/top", Side.TOP)
            .setTex(BLOCK_TEXTURES, "aether:block/grass_aether/bottom", Side.BOTTOM)
            .setTex(BLOCK_TEXTURES, "aether:block/grass_aether/side", SIDES)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/grass_aether/top_retro", Side.TOP)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/grass_aether/bottom_retro", Side.BOTTOM)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/grass_aether/side_retro", SIDES));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.DIRT_AETHER)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/dirt_aether")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/dirt_aether_retro"));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.PATH_DIRT_AETHER)
            .setTex(BLOCK_TEXTURES, "aether:block/grass_path_aether/top", Side.TOP)
            .setTex(BLOCK_TEXTURES, "aether:block/grass_path_aether/bottom", Side.BOTTOM)
            .setTex(BLOCK_TEXTURES, "aether:block/grass_path_aether/side", SIDES)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/grass_path_aether/top_retro", Side.TOP)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/grass_path_aether/bottom_retro", Side.BOTTOM)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/grass_path_aether/side_retro", SIDES));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.HOLYSTONE)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/holystone"));

        dispatcher.addDispatch(new BlockModelAetherStoneMossy<>(AetherBlocks.HOLYSTONE_MOSSY)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/holystone"));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.HOLYSTONE_POLISHED)
            .setTex(BLOCK_TEXTURES, "aether:block/polished_holystone_side", SIDES)
            .setTex(BLOCK_TEXTURES, "aether:block/polished_holystone_top", TOP_BOTTOM));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.HOLYSTONE_CARVED)
            .setTex(BLOCK_TEXTURES, "aether:block/carved_holystone", SIDES)
            .setTex(BLOCK_TEXTURES, "aether:block/polished_holystone_top", TOP_BOTTOM));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.COBBLE_HOLYSTONE)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/cobbled_holystone")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/cobbled_holystone_retro"));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.COBBLE_HOLYSTONE_MOSSY)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/cobbled_holystone_mossy")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/cobbled_holystone_mossy_retro"));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.BRICK_HOLYSTONE)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/brick_holystone"));


        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.ICESTONE)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/icestone"));


        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.QUICKSOIL)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/quicksoil"));

        dispatcher.addDispatch(new BlockModelTransparent<>(AetherBlocks.GLASS_QUICKSOIL, false).onRenderLayer(1)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/glass_quicksoil")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/glass_quicksoil_retro"));

        dispatcher.addDispatch(new BlockModelDoorGlass<>(AetherBlocks.DOOR_GLASS_QUICKSOIL_BOTTOM).onRenderLayer(1)
            .setTex(BLOCK_TEXTURES, "aether:block/door/glass_quicksoil/frame", TOP_BOTTOM)
            .setTex(BLOCK_TEXTURES, "aether:block/door/glass_quicksoil/bottom", SIDES)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/door/glass_quicksoil/frame_retro", TOP_BOTTOM)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/door/glass_quicksoil/bottom_retro", SIDES));

        dispatcher.addDispatch(new BlockModelDoorGlass<>(AetherBlocks.DOOR_GLASS_QUICKSOIL_TOP).onRenderLayer(1)
            .setTex(BLOCK_TEXTURES, "aether:block/door/glass_quicksoil/frame", TOP_BOTTOM)
            .setTex(BLOCK_TEXTURES, "aether:block/door/glass_quicksoil/top", SIDES)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/door/glass_quicksoil/frame_retro", TOP_BOTTOM)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/door/glass_quicksoil/top_retro", SIDES));

        dispatcher.addDispatch(new BlockModelTrapDoor<>(AetherBlocks.TRAPDOOR_GLASS_QUICKSOIL).onRenderLayer(1)
            .setTex(BLOCK_TEXTURES, "aether:block/trapdoor/glass_quicksoil/top", TOP_BOTTOM)
            .setTex(BLOCK_TEXTURES, "aether:block/trapdoor/glass_quicksoil/side", Side.EAST, Side.NORTH, Side.SOUTH, Side.WEST)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/trapdoor/glass_quicksoil/top_retro", TOP_BOTTOM)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/trapdoor/glass_quicksoil/side_retro", Side.EAST, Side.NORTH, Side.SOUTH, Side.WEST));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.ORE_AMBROSIUM_HOLYSTONE)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/ore/ambrosium/holystone")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/ore/ambrosium/holystone_retro"));
        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.ORE_ZANITE_HOLYSTONE)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/ore/zanite/holystone")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/ore/zanite/holystone_retro"));
        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.ORE_GRAVITITE_HOLYSTONE)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/ore/gravitite/holystone")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/ore/gravitite/holystone_retro"));

        dispatcher.addDispatch(new BlockModelTransparent<>(AetherBlocks.BLOCK_AMBER, true).onRenderLayer(1)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/block_amber"));
        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.BLOCK_AMBROSIUM)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/block_ambrosium"));
        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.BLOCK_ZANITE)
            .setTex(BLOCK_TEXTURES, "aether:block/block_zanite/side", SIDES)
            .setTex(BLOCK_TEXTURES, "aether:block/block_zanite/top", Side.TOP)
            .setTex(BLOCK_TEXTURES, "aether:block/block_zanite/bottom", Side.BOTTOM)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/block_zanite/side_retro", SIDES)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/block_zanite/top_retro", Side.TOP)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/block_zanite/bottom_retro", Side.BOTTOM));
        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.BLOCK_GRAVITITE)
            .setTex(BLOCK_TEXTURES, "aether:block/block_gravitite/side", SIDES)
            .setTex(BLOCK_TEXTURES, "aether:block/block_gravitite/top", Side.TOP)
            .setTex(BLOCK_TEXTURES, "aether:block/block_gravitite/bottom", Side.BOTTOM)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/block_gravitite/side_retro", SIDES)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/block_gravitite/top_retro", Side.TOP)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/block_gravitite/bottom_retro", Side.BOTTOM));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.BRICK_ZANITE)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/brick_zanite"));

        dispatcher.addDispatch(new BlockModelStairs<>(AetherBlocks.STAIRS_COBBLE_HOLYSTONE));
        dispatcher.addDispatch(new BlockModelStairs<>(AetherBlocks.STAIRS_BRICK_HOLYSTONE));
        dispatcher.addDispatch(new BlockModelStairs<>(AetherBlocks.STAIRS_BRICK_ZANITE));

        dispatcher.addDispatch(new BlockModelSlab<>(AetherBlocks.SLAB_COBBLE_HOLYSTONE));
        dispatcher.addDispatch(new BlockModelSlab<>(AetherBlocks.SLAB_BRICK_HOLYSTONE));
        dispatcher.addDispatch(new BlockModelSlab<>(AetherBlocks.SLAB_HOLYSTONE_POLISHED));
        dispatcher.addDispatch(new BlockModelSlab<>(AetherBlocks.SLAB_BRICK_ZANITE));


        dispatcher.addDispatch(AetherBlocks.TORCH_AMBROSIUM, new BlockModelTorch<>(AetherBlocks.TORCH_AMBROSIUM)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/torch_ambrosium"));


        dispatcher.addDispatch(AetherBlocks.LANTERN_FIREFLY_SILVER, new BlockModelLantern<>(AetherBlocks.LANTERN_FIREFLY_SILVER)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/lantern_firefly_silver"));


    }

    @Override
    public void initItemModels(ItemModelDispatcher dispatcher) {
        this.setItemToolModels(dispatcher);
        this.setItemArmorModels(dispatcher);
        this.setItemBlockModels(dispatcher);

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
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ORE_RAW_GRAVITITE, null).setIcon("aether:item/ore_raw_gravitite"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.BUCKET_SKYROOT, null).setIcon("aether:item/bucket_skyroot"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.BUCKET_SKYROOT_WATER, null).setIcon("aether:item/bucket_skyroot_water"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.BUCKET_SKYROOT_MILK, null).setIcon("aether:item/bucket_skyroot_milk"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.BUCKET_SKYROOT_POISON, null).setIcon("aether:item/bucket_skyroot_poison"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.BUCKET_SKYROOT_REMEDY, null).setIcon("aether:item/bucket_skyroot_remedy"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.BUCKET_SKYROOT_ICECREAM, null).setIcon("aether:item/bucket_skyroot_icecream"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.FOOD_HEALING_STONE, null).setIcon("aether:item/food_healing_stone"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.FOOD_GUMMY_BLUE, null).setIcon("aether:item/food_sweet_blue"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.FOOD_GUMMY_GOLD, null).setIcon("aether:item/food_sweet_gold"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.LIFESHARD, null).setIcon("aether:item/food_lifeshard"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.PARACHUTE_CLOUD, null).setIcon("aether:item/parachute"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.PARACHUTE_CLOUD_GOLD, null).setIcon("aether:item/parachute_gold"));


        dispatcher.addDispatch(new ItemModelStandard(AetherItems.LANTERN_FIREFLY_SILVER, null).setIcon("aether:item/lantern_firefly_silver").setFullBright());

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.AMMO_WINDBALL, null).setIcon("aether:item/windball"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.PROJECTILE_FIRE, null).setIcon("aether:item/projectile_fire").setFullBright());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.PROJECTILE_ICE, null).setIcon("aether:item/projectile_ice"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.PROJECTILE_LIGHTNING, null).setIcon("aether:item/projectile_lightning"));


        dispatcher.addDispatch((new ItemModelBlock((ItemBlock<?>) AetherBlocks.TORCH_AMBROSIUM.asItem())).setFullBright());

    }

    @Override
    public void initEntityModels(EntityRenderDispatcher dispatcher) {
        this.setProjectileModels();
        this.setMobEnemyModels();
        this.setMobBossModels();
        this.setMobAnimalModels();

        ModelHelper.setEntityModel(EntityFloatingBlock.class, EntityRendererFloatingBlock::new);
        ModelHelper.setEntityModel(EntityParachute.class, EntityRendererParachute::new);
        ModelHelper.setEntityModel(EntityParachuteGold.class, EntityRendererParachuteGold::new);
    }

    @Override
    public void initBlockColors(BlockColorDispatcher dispatcher) {
        dispatcher.addDispatch(AetherBlocks.GRASS_AETHER, new BlockColorCustom(AetherClient.grassAether));
        dispatcher.addDispatch(AetherBlocks.TALLGRASS_AETHER, new BlockColorCustom(AetherClient.grassAether));

        dispatcher.addDispatch(AetherBlocks.HOLYSTONE_MOSSY, new BlockColorCustom(AetherClient.grassAether));

        dispatcher.addDispatch(AetherBlocks.LEAVES_SKYROOT, new BlockColorCustom(AetherClient.skyroot));
        dispatcher.addDispatch(AetherBlocks.LEAVES_OAK_GOLDEN, new BlockColorCustom(AetherClient.oakGolden));
    }

    private void setBlockSkyrootModels(BlockModelDispatcher dispatcher) {
        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.PLANKS_SKYROOT)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/planks_skyroot/skyroot")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/planks_skyroot/skyroot_retro"));

        dispatcher.addDispatch(new BlockModelPaintedSkyrootPlanks<>(AetherBlocks.PLANKS_SKYROOT_PAINTED));

        dispatcher.addDispatch(new BlockModelDoor<>(AetherBlocks.DOOR_PLANKS_SKYROOT_BOTTOM)
            .setTex(BLOCK_TEXTURES, "aether:block/door/skyroot/frame", TOP_BOTTOM)
            .setTex(BLOCK_TEXTURES, "aether:block/door/skyroot/bottom", SIDES));
        dispatcher.addDispatch(new BlockModelDoor<>(AetherBlocks.DOOR_PLANKS_SKYROOT_TOP)
            .setTex(BLOCK_TEXTURES, "aether:block/door/skyroot/frame", TOP_BOTTOM)
            .setTex(BLOCK_TEXTURES, "aether:block/door/skyroot/top", SIDES));
        dispatcher.addDispatch(new BlockModelPaintedSkyrootDoor<>(AetherBlocks.DOOR_PLANKS_SKYROOT_PAINTED_BOTTOM, false)
            .setTex(BLOCK_TEXTURES, "aether:block/door/skyroot/frame", TOP_BOTTOM)
            .setTex(BLOCK_TEXTURES, "aether:block/door/skyroot/bottom", SIDES));
        dispatcher.addDispatch(new BlockModelPaintedSkyrootDoor<>(AetherBlocks.DOOR_PLANKS_SKYROOT_PAINTED_TOP, true)
            .setTex(BLOCK_TEXTURES, "aether:block/door/skyroot/frame", TOP_BOTTOM)
            .setTex(BLOCK_TEXTURES, "aether:block/door/skyroot/top", SIDES));

        dispatcher.addDispatch(new BlockModelEmpty<>(AetherBlocks.SIGN_POST_PLANKS_SKYROOT)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/planks_skyroot/skyroot"));
        dispatcher.addDispatch(new BlockModelEmpty<>(AetherBlocks.SIGN_WALL_PLANKS_SKYROOT)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/planks_skyroot/skyroot"));

        dispatcher.addDispatch(new BlockModelPaintedSkyrootSign<>(AetherBlocks.SIGN_POST_PLANKS_SKYROOT_PAINTED));
        dispatcher.addDispatch(new BlockModelPaintedSkyrootSign<>(AetherBlocks.SIGN_WALL_PLANKS_SKYROOT_PAINTED));

        dispatcher.addDispatch(new BlockModelTrapDoor<>(AetherBlocks.TRAPDOOR_PLANKS_SKYROOT)
            .setTex(BLOCK_TEXTURES, "aether:block/trapdoor/skyroot/top", TOP_BOTTOM)
            .setTex(BLOCK_TEXTURES, "aether:block/trapdoor/skyroot/side", Side.EAST, Side.NORTH, Side.SOUTH, Side.WEST));
        dispatcher.addDispatch(new BlockModelPaintedSkyrootTrapDoor<>(AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED));

        dispatcher.addDispatch(new BlockModelChest<>(AetherBlocks.CHEST_PLANKS_SKYROOT, "aether:block/chest/skyroot/")
            .setAllTextures(BLOCK_TEXTURES, "aether:block/chest/skyroot/top"));
        dispatcher.addDispatch(new BlockModelPaintedSkyrootChest<>(AetherBlocks.CHEST_PLANKS_SKYROOT_PAINTED)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/chest/skyroot/top"));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.BUTTON_PLANKS_SKYROOT)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/planks_skyroot/skyroot")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/planks_skyroot/skyroot_retro").withCustomItemBounds(0.3125, 0.375, 0.375, 0.6875, 0.625, 0.625));
        dispatcher.addDispatch(new BlockModelPaintedSkyrootButton<>(AetherBlocks.BUTTON_PLANKS_SKYROOT_PAINTED)
            .withCustomItemBounds(0.3125, 0.375, 0.375, 0.6875, 0.625, 0.625));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.PRESSURE_PLATE_PLANKS_SKYROOT)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/planks_skyroot/skyroot")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/planks_skyroot/skyroot_retro").withCustomItemBounds(0.0, 0.375, 0.0, 1.0, 0.625, 1.0));
        dispatcher.addDispatch(new BlockModelPaintedSkyrootPreasurePlate<>(AetherBlocks.PRESSURE_PLATE_PLANKS_SKYROOT_PAINTED)
            .withCustomItemBounds(0.0, 0.375, 0.0, 1.0, 0.625, 1.0));

        dispatcher.addDispatch(AetherBlocks.FENCE_PLANKS_SKYROOT, new BlockModelFence<>(AetherBlocks.FENCE_PLANKS_SKYROOT)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/planks_skyroot/skyroot")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/planks_skyroot/skyroot_retro"));
        dispatcher.addDispatch(new BlockModelPaintedSkyrootFence<>(AetherBlocks.FENCE_PLANKS_SKYROOT_PAINTED));

        dispatcher.addDispatch(AetherBlocks.FENCEGATE_PLANKS_SKYROOT, new BlockModelFenceGate<>(AetherBlocks.FENCEGATE_PLANKS_SKYROOT)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/planks_skyroot/skyroot")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/planks_skyroot/skyroot_retro"));
        dispatcher.addDispatch(new BlockModelPaintedSkyrootFenceGate<>(AetherBlocks.FENCEGATE_PLANKS_SKYROOT_PAINTED));

        dispatcher.addDispatch(new BlockModelSlab<>(AetherBlocks.SLAB_PLANKS_SKYROOT));
        dispatcher.addDispatch(new BlockModelPaintedSkyrootSlab<>(AetherBlocks.SLAB_PLANKS_SKYROOT_PAINTED));

        dispatcher.addDispatch(new BlockModelStairs<>(AetherBlocks.STAIRS_PLANKS_SKYROOT));
        dispatcher.addDispatch(new BlockModelPaintedSkyrootStairs<>(AetherBlocks.STAIRS_PLANKS_SKYROOT_PAINTED));
    }

    private void setBlockMachineModels(BlockModelDispatcher dispatcher) {
        dispatcher.addDispatch(new BlockModelEnchanter<>(AetherBlocks.ENCHANTER_IDLE)
            .setTex(BLOCK_TEXTURES, "aether:block/enchanter/top", Side.TOP)
            .setTex(BLOCK_TEXTURES, "aether:block/enchanter/bottom", Side.BOTTOM)
            .setTex(BLOCK_TEXTURES, "aether:block/enchanter/idle_front", Side.NORTH)
            .setTex(BLOCK_TEXTURES, "aether:block/enchanter/side", Side.EAST, Side.WEST, Side.SOUTH)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/enchanter/top_retro", Side.TOP)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/enchanter/bottom_retro", Side.BOTTOM)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/enchanter/idle_front_retro", Side.NORTH)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/enchanter/side_retro", Side.EAST, Side.WEST, Side.SOUTH));
        dispatcher.addDispatch(new BlockModelEnchanter<>(AetherBlocks.ENCHANTER_ACTIVE)
            .setTex(BLOCK_TEXTURES, "aether:block/enchanter/top", Side.TOP)
            .setTex(BLOCK_TEXTURES, "aether:block/enchanter/bottom", Side.BOTTOM)
            .setTex(BLOCK_TEXTURES, "aether:block/enchanter/active_front", Side.NORTH)
            .setTex(BLOCK_TEXTURES, "aether:block/enchanter/side", Side.EAST, Side.WEST, Side.SOUTH)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/enchanter/top_retro", Side.TOP)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/enchanter/bottom_retro", Side.BOTTOM)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/enchanter/active_front_retro", Side.NORTH)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/enchanter/side_retro", Side.EAST, Side.WEST, Side.SOUTH));

        dispatcher.addDispatch(new BlockModelFreezer<>(AetherBlocks.FREEZER_IDLE)
            .setTex(BLOCK_TEXTURES, "aether:block/freezer/idle_top", Side.TOP)
            .setTex(BLOCK_TEXTURES, "aether:block/freezer/bottom", Side.BOTTOM)
            .setTex(BLOCK_TEXTURES, "aether:block/freezer/side", Side.EAST, Side.WEST, Side.SOUTH, Side.NORTH)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/freezer/idle_top_retro", Side.TOP)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/freezer/bottom_retro", Side.BOTTOM)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/freezer/side_retro", Side.EAST, Side.WEST, Side.SOUTH, Side.NORTH));
        dispatcher.addDispatch(new BlockModelFreezer<>(AetherBlocks.FREEZER_ACTIVE)
            .setTex(BLOCK_TEXTURES, "aether:block/freezer/active_top", Side.TOP)
            .setTex(BLOCK_TEXTURES, "aether:block/freezer/bottom", Side.BOTTOM)
            .setTex(BLOCK_TEXTURES, "aether:block/freezer/side", Side.EAST, Side.WEST, Side.SOUTH, Side.NORTH)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/freezer/active_top_retro", Side.TOP)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/freezer/bottom_retro", Side.BOTTOM)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/freezer/side_retro", Side.EAST, Side.WEST, Side.SOUTH, Side.NORTH));

        dispatcher.addDispatch(new BlockModelIncubator<>(AetherBlocks.INCUBATOR_IDLE)
            .setTex(BLOCK_TEXTURES, "aether:block/incubator/idle_top", Side.TOP)
            .setTex(BLOCK_TEXTURES, "aether:block/incubator/bottom", Side.BOTTOM)
            .setTex(BLOCK_TEXTURES, "aether:block/incubator/side", Side.EAST, Side.WEST, Side.SOUTH, Side.NORTH)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/incubator/idle_top_retro", Side.TOP)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/incubator/bottom_retro", Side.BOTTOM)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/incubator/side_retro", Side.EAST, Side.WEST, Side.SOUTH, Side.NORTH));
        dispatcher.addDispatch(new BlockModelIncubator<>(AetherBlocks.INCUBATOR_ACTIVE)
            .setTex(BLOCK_TEXTURES, "aether:block/incubator/active_top", Side.TOP)
            .setTex(BLOCK_TEXTURES, "aether:block/incubator/bottom", Side.BOTTOM)
            .setTex(BLOCK_TEXTURES, "aether:block/incubator/side", Side.EAST, Side.WEST, Side.SOUTH, Side.NORTH)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/incubator/active_top_retro", Side.TOP)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/incubator/bottom_retro", Side.BOTTOM)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/incubator/side_retro", Side.EAST, Side.WEST, Side.SOUTH, Side.NORTH));


        dispatcher.addDispatch(new BlockModelDungeonDoor<>(AetherBlocks.DOOR_DUNGEON_BRONZE, 4, 4)
            .setParticleTexture(false, "aether:block/ctm/boss_door/bronze/particle")
            .setParticleTexture(true, "aether:block/ctm/boss_door/bronze/particle_retro")
            .setParticleOverbrightTexture(false, "aether:block/ctm/boss_door/bronze/particle_overbright")
            .setParticleOverbrightTexture(true, "aether:block/ctm/boss_door/bronze/particle_overbright_retro")

            .setTex(BLOCK_TEXTURES, "aether:block/ctm/boss_door/bronze/back", Side.sides)
            .setTex(OVERBRIGHT_TEXTURES, "aether:block/ctm/boss_door/bronze/back_overbright", Side.sides)
            .setTex(BLOCK_TEXTURES, "aether:block/ctm/boss_door/bronze/front", Side.NORTH)
            .setTex(OVERBRIGHT_TEXTURES, "aether:block/ctm/boss_door/bronze/front_overbright", Side.NORTH)

            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/ctm/boss_door/bronze/back_retro", Side.sides)
            .setTex(RETRO_OVERBRIGHT_TEXTURES, "aether:block/ctm/boss_door/bronze/back_overbright_retro", Side.sides)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/ctm/boss_door/bronze/front_retro", Side.NORTH)
            .setTex(RETRO_OVERBRIGHT_TEXTURES, "aether:block/ctm/boss_door/bronze/front_overbright_retro", Side.NORTH)
        );

        dispatcher.addDispatch(new BlockModelDungeonDoor<>(AetherBlocks.DOOR_DUNGEON_SILVER, 2, 3)
            .setParticleTexture(false, "aether:block/ctm/boss_door/silver/particle")
            .setParticleTexture(true, "aether:block/ctm/boss_door/silver/particle_retro")
            .setParticleOverbrightTexture(false, "aether:block/ctm/boss_door/silver/particle_overbright")
            .setParticleOverbrightTexture(true, "aether:block/ctm/boss_door/silver/particle_overbright_retro")

            .setTex(BLOCK_TEXTURES, "aether:block/ctm/boss_door/silver/back", Side.sides)
            .setTex(OVERBRIGHT_TEXTURES, "aether:block/ctm/boss_door/silver/back_overbright", Side.sides)
            .setTex(BLOCK_TEXTURES, "aether:block/ctm/boss_door/silver/front", Side.NORTH)
            .setTex(OVERBRIGHT_TEXTURES, "aether:block/ctm/boss_door/silver/front_overbright", Side.NORTH)

            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/ctm/boss_door/silver/back_retro", Side.sides)
            .setTex(RETRO_OVERBRIGHT_TEXTURES, "aether:block/ctm/boss_door/silver/back_overbright_retro", Side.sides)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/ctm/boss_door/silver/front_retro", Side.NORTH)
            .setTex(RETRO_OVERBRIGHT_TEXTURES, "aether:block/ctm/boss_door/silver/front_overbright_retro", Side.NORTH)
        );

        dispatcher.addDispatch(new BlockModelDungeonDoor<>(AetherBlocks.DOOR_DUNGEON_GOLD, 3, 3)
            .setParticleTexture(false, "aether:block/ctm/boss_door/gold/particle")
            .setParticleTexture(true, "aether:block/ctm/boss_door/gold/particle_retro")
            .setParticleOverbrightTexture(false, "aether:block/ctm/boss_door/gold/particle_overbright")
            .setParticleOverbrightTexture(true, "aether:block/ctm/boss_door/gold/particle_overbright_retro")

            .setTex(BLOCK_TEXTURES, "aether:block/ctm/boss_door/gold/back", Side.sides)
            .setTex(OVERBRIGHT_TEXTURES, "aether:block/ctm/boss_door/gold/back_overbright", Side.sides)
            .setTex(BLOCK_TEXTURES, "aether:block/ctm/boss_door/gold/front", Side.NORTH)
            .setTex(OVERBRIGHT_TEXTURES, "aether:block/ctm/boss_door/gold/front_overbright", Side.NORTH)

            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/ctm/boss_door/gold/back_retro", Side.sides)
            .setTex(RETRO_OVERBRIGHT_TEXTURES, "aether:block/ctm/boss_door/gold/back_overbright_retro", Side.sides)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/ctm/boss_door/gold/front_retro", Side.NORTH)
            .setTex(RETRO_OVERBRIGHT_TEXTURES, "aether:block/ctm/boss_door/gold/front_overbright_retro", Side.NORTH)
        );
    }

    private void setBlockDungeonModels(BlockModelDispatcher dispatcher) {
        dispatcher.addDispatch(new BlockModelHorizontalRotation<>(AetherBlocks.CHEST_DUNGEON_BRONZE)
            .setTex(BLOCK_TEXTURES, "aether:block/chest/dungeon_bronze/front", Side.NORTH)
            .setTex(BLOCK_TEXTURES, "aether:block/chest/dungeon_bronze/side", Side.EAST, Side.WEST, Side.SOUTH)
            .setTex(BLOCK_TEXTURES, "aether:block/chest/dungeon_bronze/top", TOP_BOTTOM));

        dispatcher.addDispatch(new BlockModelHorizontalRotation<>(AetherBlocks.CHEST_DUNGEON_BRONZE_LOCKED)
            .setTex(BLOCK_TEXTURES, "aether:block/chest/dungeon_bronze/front_locked", Side.NORTH)
            .setTex(BLOCK_TEXTURES, "aether:block/chest/dungeon_bronze/side", Side.EAST, Side.WEST, Side.SOUTH)
            .setTex(BLOCK_TEXTURES, "aether:block/chest/dungeon_bronze/top", TOP_BOTTOM));

        dispatcher.addDispatch(new BlockModelHorizontalRotation<>(AetherBlocks.CHEST_DUNGEON_SILVER)
            .setTex(BLOCK_TEXTURES, "aether:block/chest/dungeon_silver/front", Side.NORTH)
            .setTex(BLOCK_TEXTURES, "aether:block/chest/dungeon_silver/side", Side.EAST, Side.WEST, Side.SOUTH)
            .setTex(BLOCK_TEXTURES, "aether:block/chest/dungeon_silver/top", TOP_BOTTOM));

        dispatcher.addDispatch(new BlockModelHorizontalRotation<>(AetherBlocks.CHEST_DUNGEON_SILVER_LOCKED)
            .setTex(BLOCK_TEXTURES, "aether:block/chest/dungeon_silver/front_locked", Side.NORTH)
            .setTex(BLOCK_TEXTURES, "aether:block/chest/dungeon_silver/side", Side.EAST, Side.WEST, Side.SOUTH)
            .setTex(BLOCK_TEXTURES, "aether:block/chest/dungeon_silver/top", TOP_BOTTOM));

        dispatcher.addDispatch(new BlockModelHorizontalRotation<>(AetherBlocks.CHEST_DUNGEON_GOLD)
            .setTex(BLOCK_TEXTURES, "aether:block/chest/dungeon_gold/front", Side.NORTH)
            .setTex(BLOCK_TEXTURES, "aether:block/chest/dungeon_gold/side", Side.EAST, Side.WEST, Side.SOUTH)
            .setTex(BLOCK_TEXTURES, "aether:block/chest/dungeon_gold/top", TOP_BOTTOM));

        dispatcher.addDispatch(new BlockModelHorizontalRotation<>(AetherBlocks.CHEST_DUNGEON_GOLD_LOCKED)
            .setTex(BLOCK_TEXTURES, "aether:block/chest/dungeon_gold/front_locked", Side.NORTH)
            .setTex(BLOCK_TEXTURES, "aether:block/chest/dungeon_gold/side", Side.EAST, Side.WEST, Side.SOUTH)
            .setTex(BLOCK_TEXTURES, "aether:block/chest/dungeon_gold/top", TOP_BOTTOM));

        dispatcher.addDispatch(new BlockModelChest<>(AetherBlocks.CHEST_MIMIC_SKYROOT, "aether:block/chest/skyroot/")
            .setAllTextures(BLOCK_TEXTURES, "aether:block/chest/skyroot/top")
        );

        dispatcher.addDispatch(new BlockModelChest<>(AetherBlocks.CHEST_MIMIC_OAK, "minecraft:block/chest/planks/")
            .setAllTextures(BLOCK_TEXTURES, "minecraft:block/chest/planks/top")
        );

        dispatcher.addDispatch(new BlockModelMimic<>(AetherBlocks.CHEST_MIMIC_BRONZE, "aether:block/chest/dungeon_bronze/")
            .setAllTextures(BLOCK_TEXTURES, "aether:block/chest/dungeon_bronze/top")
        );

        dispatcher.addDispatch(new BlockModelMimic<>(AetherBlocks.CHEST_MIMIC_SILVER, "aether:block/chest/dungeon_silver/")
            .setAllTextures(BLOCK_TEXTURES, "aether:block/chest/dungeon_silver/top")
        );

        dispatcher.addDispatch(new BlockModelMimic<>(AetherBlocks.CHEST_MIMIC_GOLD, "aether:block/chest/dungeon_gold/")
            .setAllTextures(BLOCK_TEXTURES, "aether:block/chest/dungeon_gold/top")
        );

        dispatcher.addDispatch(new BlockModelPaintedSkyrootMimic<>(AetherBlocks.CHEST_MIMIC_SKYROOT_PAINTED));
        dispatcher.addDispatch(new BlockModelPaintedOakMimic<>(AetherBlocks.CHEST_MIMIC_OAK_PAINTED));


        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_STONE_LOCKED)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/dungeon/carved")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/dungeon/carved_retro"));
        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_STONE_LIGHT_LOCKED)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/dungeon/carved_glow")
            .setAllTextures(OVERBRIGHT_TEXTURES, "aether:block/dungeon/carved_overlay")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/dungeon/carved_glow_retro")
            .setAllTextures(RETRO_OVERBRIGHT_TEXTURES, "aether:block/dungeon/carved_overlay_retro"));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_ANGELIC_LOCKED)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/dungeon/angelic")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/dungeon/angelic_retro"));
        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_ANGELIC_LIGHT_LOCKED)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/dungeon/angelic_glow")
            .setAllTextures(OVERBRIGHT_TEXTURES, "aether:block/dungeon/angelic_overlay")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/dungeon/angelic_glow")
            .setAllTextures(RETRO_OVERBRIGHT_TEXTURES, "aether:block/dungeon/angelic_overlay"));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_HELLFIRE_LOCKED)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/dungeon/hellfire")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/dungeon/hellfire_retro"));
        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_HELLFIRE_LIGHT_LOCKED)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/dungeon/hellfire_glow")
            .setAllTextures(OVERBRIGHT_TEXTURES, "aether:block/dungeon/hellfire_overlay")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/dungeon/hellfire_glow_retro")
            .setAllTextures(RETRO_OVERBRIGHT_TEXTURES, "aether:block/dungeon/hellfire_overlay_retro"));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_STONE_TRAPPED)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/dungeon/carved")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/dungeon/carved_retro"));
        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_STONE_TRAPPED_LOCKED)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/dungeon/carved")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/dungeon/carved_retro"));


        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_ANGELIC_TRAPPED)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/dungeon/angelic")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/dungeon/angelic_retro"));
        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_ANGELIC_TRAPPED_LOCKED)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/dungeon/angelic")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/dungeon/angelic_retro"));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_HELLFIRE_TRAPPED)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/dungeon/hellfire")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/dungeon/hellfire_retro"));
        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_HELLFIRE_TRAPPED_LOCKED)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/dungeon/hellfire")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/dungeon/hellfire_retro"));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_STONE)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/dungeon/carved")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/dungeon/carved_retro"));
        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_STONE_LIGHT)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/dungeon/carved_glow")
            .setAllTextures(OVERBRIGHT_TEXTURES, "aether:block/dungeon/carved_overlay")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/dungeon/carved_glow_retro")
            .setAllTextures(RETRO_OVERBRIGHT_TEXTURES, "aether:block/dungeon/carved_overlay_retro"));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_ANGELIC)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/dungeon/angelic")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/dungeon/angelic_retro"));
        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_ANGELIC_LIGHT)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/dungeon/angelic_glow")
            .setAllTextures(OVERBRIGHT_TEXTURES, "aether:block/dungeon/angelic_overlay")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/dungeon/angelic_glow_retro")
            .setAllTextures(RETRO_OVERBRIGHT_TEXTURES, "aether:block/dungeon/angelic_overlay_retro"));

        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_HELLFIRE)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/dungeon/hellfire")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/dungeon/hellfire_retro"));
        dispatcher.addDispatch(new BlockModelStandard<>(AetherBlocks.CARVED_HELLFIRE_LIGHT)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/dungeon/hellfire_glow")
            .setAllTextures(OVERBRIGHT_TEXTURES, "aether:block/dungeon/hellfire_overlay")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/dungeon/hellfire_glow_retro")
            .setAllTextures(RETRO_OVERBRIGHT_TEXTURES, "aether:block/dungeon/hellfire_overlay_retro"));

        dispatcher.addDispatch(new BlockModelAxisAligned<>(AetherBlocks.PILLAR)
            .setTex(BLOCK_TEXTURES, "aether:block/pillar/side", SIDES)
            .setTex(BLOCK_TEXTURES, "aether:block/pillar/top", TOP_BOTTOM)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/pillar/side", SIDES)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/pillar/top_retro", TOP_BOTTOM));
        dispatcher.addDispatch(new BlockModelAxisAligned<>(AetherBlocks.PILLAR_CAPSTONE)
            .setTex(BLOCK_TEXTURES, "aether:block/pillar_capstone/side", SIDES)
            .setTex(BLOCK_TEXTURES, "aether:block/pillar_capstone/top", TOP_BOTTOM)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/pillar_capstone/side", SIDES)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/pillar_capstone/top_retro", TOP_BOTTOM));

        dispatcher.addDispatch(new BlockModelSlab<>(AetherBlocks.SLAB_CARVED_STONE));
        dispatcher.addDispatch(new BlockModelSlab<>(AetherBlocks.SLAB_CARVED_ANGELIC));
        dispatcher.addDispatch(new BlockModelSlab<>(AetherBlocks.SLAB_CARVED_HELLFIRE));

        dispatcher.addDispatch(new BlockModelStairs<>(AetherBlocks.STAIRS_CARVED_STONE));
        dispatcher.addDispatch(new BlockModelStairs<>(AetherBlocks.STAIRS_CARVED_ANGELIC));
        dispatcher.addDispatch(new BlockModelStairs<>(AetherBlocks.STAIRS_CARVED_HELLFIRE));

    }

    private void setBlockPlantModels(BlockModelDispatcher dispatcher) {
        dispatcher.addDispatch(new BlockModelAetherLog<>(AetherBlocks.LOG_SKYROOT)
            .setTex(BLOCK_TEXTURES, "aether:block/log/skyroot_side", SIDES)
            .setTex(BLOCK_TEXTURES, "aether:block/log/skyroot_top", TOP_BOTTOM)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/log/skyroot_side_retro", SIDES)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/log/skyroot_top_retro", TOP_BOTTOM));
        dispatcher.addDispatch(new BlockModelAetherLog<>(AetherBlocks.LOG_OAK_GOLDEN)
            .setTex(BLOCK_TEXTURES, "aether:block/log/oak_golden_side", SIDES)
            .setTex(BLOCK_TEXTURES, "aether:block/log/oak_golden_top", TOP_BOTTOM)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/log/oak_golden_side_retro", SIDES)
            .setTex(RETRO_BLOCK_TEXTURES, "aether:block/log/oak_golden_top_retro", TOP_BOTTOM));

        dispatcher.addDispatch(new BlockModelLeavesAether<>(AetherBlocks.LEAVES_SKYROOT, "aether:block/leaves/skyroot", "aether:block/leaves/skyroot_retro"));
        dispatcher.addDispatch(new BlockModelLeavesAether<>(AetherBlocks.LEAVES_OAK_GOLDEN, "aether:block/leaves/oak_golden", "aether:block/leaves/oak_golden_retro"));

        dispatcher.addDispatch(new BlockModelCrossedSquares<>(AetherBlocks.SAPLING_SKYROOT)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/sapling/skyroot")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/sapling/skyroot_retro"));
        dispatcher.addDispatch(new BlockModelCrossedSquares<>(AetherBlocks.SAPLING_OAK_GOLDEN)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/sapling/oak_golden")
            .setAllTextures(RETRO_BLOCK_TEXTURES, "aether:block/sapling/oak_golden_retro"));

        dispatcher.addDispatch(new BlockModelFlowerStackableAether<>(AetherBlocks.FLOWER_PURPLE, "aether:block/flower_purple/"));
        dispatcher.addDispatch(new BlockModelFlowerStackableAether<>(AetherBlocks.FLOWER_WHITE, "aether:block/flower_white/"));

        dispatcher.addDispatch(new BlockModelAetherTallgrass<>(AetherBlocks.TALLGRASS_AETHER).setAllTextures(BLOCK_TEXTURES, "aether:block/tallgrass_aether"));

        dispatcher.addDispatch(new BlockModelCrossedSquares<>(AetherBlocks.DEADBUSH_AETHER).setAllTextures(BLOCK_TEXTURES, "aether:block/deadbush_aether"));
    }

    private void setBlockCloudModels(BlockModelDispatcher dispatcher) {
        dispatcher.addDispatch(new BlockModelTransparent<>(AetherBlocks.AERCLOUD_WHITE, false).onRenderLayer(1)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/aercloud_white"));
        dispatcher.addDispatch(new BlockModelTransparent<>(AetherBlocks.AERCLOUD_BLUE, false).onRenderLayer(1)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/aercloud_blue"));
        dispatcher.addDispatch(new BlockModelTransparent<>(AetherBlocks.AERCLOUD_GOLD, false).onRenderLayer(1)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/aercloud_gold"));

        dispatcher.addDispatch(new BlockModelTransparent<>(AetherBlocks.AEROGEL, false).onRenderLayer(1)
            .setAllTextures(BLOCK_TEXTURES, "aether:block/aerogel"));
    }

    private void setItemArmorModels(ItemModelDispatcher dispatcher) {
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.AMMO_HAMMER_HEAD, null).setIcon("aether:item/notch_wave"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_SHIELD_REPULSION, null).setIcon("aether:item/tool_shield_repulsion"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.AMMO_DART_GOLDEN, null).setIcon("aether:item/dart_golden"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.AMMO_DART_POISON, null).setIcon("aether:item/dart_poison"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.AMMO_DART_ENCHANTED, null).setIcon("aether:item/dart_enchanted"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.AMMO_ARROW_FLAMING, null).setIcon("aether:item/ammo_arrow_flaming").setFullBright());


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
    }

    private void setItemToolModels(ItemModelDispatcher dispatcher) {
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

        dispatcher.addDispatch(new ItemModelLance(AetherItems.TOOL_SWORD_VALKYRIE, null).setIcon("aether:item/tool_sword_valk").setFull3D());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_SHOVEL_VALKYRIE, null).setIcon("aether:item/tool_shovel_valk").setFull3D());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_PICKAXE_VALKYRIE, null).setIcon("aether:item/tool_pickaxe_valk").setFull3D());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_AXE_VALKYRIE, null).setIcon("aether:item/tool_axe_valk").setFull3D());

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_KNIFE_LIGHTNING, null).setIcon("aether:item/tool_knife_lightning").setFull3D());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_HAMMER_NOTCH, null).setIcon("aether:item/tool_hammer_notch").setFull3D());
        dispatcher.addDispatch(new ItemModelBowPhoenix(AetherItems.TOOL_BOW_PHOENIX, null).setIcon("aether:item/tool_bow_phoenix").setFullBright());
        dispatcher.addDispatch(new ItemModelShooter(AetherItems.TOOL_SHOOTER, null).setIcon("aether:item/shooter_gold"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_SWORD_FLAME, null).setIcon("aether:item/tool_sword_element_fire").setFull3D().setFullBright());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_SWORD_HOLY, null).setIcon("aether:item/tool_sword_element_holy").setFull3D());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_SWORD_LIGHTNING, null).setIcon("aether:item/tool_sword_element_lightning").setFull3D());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_SWORD_PIG, null).setIcon("aether:item/tool_knife_pig").setFull3D());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_SWORD_VAMPIRE, null).setIcon("aether:item/tool_sword_vampire").setFull3D());

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_STAFF_NATURE, null).setIcon("aether:item/staff_nature").setFull3D());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_STAFF_CLOUD, null).setIcon("aether:item/staff_cloud").setFull3D());

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_DUNGEON_COMPASS, null).setIcon("aether:item/tool_dungeon_compass"));
    }

    private void setItemBlockModels(ItemModelDispatcher dispatcher) {
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.DOOR_DUNGEON_BRONZE, null).setIcon("aether:item/door_dungeon_bronze"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.DOOR_DUNGEON_SILVER, null).setIcon("aether:item/door_dungeon_silver"));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.DOOR_DUNGEON_GOLD, null).setIcon("aether:item/door_dungeon_gold"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.DOOR_GLASS_AMBROSIUM, null).setIcon("aether:item/door_glass_ambrosium"));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.DOOR_SKYROOT, null).setIcon("aether:item/door_skyroot"));
        dispatcher.addDispatch(new ItemModelPaintedSkyrootDoor(AetherItems.DOOR_SKYROOT_PAINTED));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.SIGN_SKYROOT, null).setIcon("aether:item/sign_skyroot"));
        dispatcher.addDispatch(new ItemModelPaintedSkyrootSign(AetherItems.SIGN_SKYROOT_PAINTED));
    }

    private void setMobAnimalModels() {
        ModelHelper.setEntityModel(MobSheepuff.class, () -> new MobRendererSheepuff(0.7F));
        ModelHelper.setEntityModel(MobPhow.class, () -> new MobRendererPhow(0.7F));
        ModelHelper.setEntityModel(MobMoaBlue.class, () -> new MobRendererMoa(0.7F));
        ModelHelper.setEntityModel(MobMoaWhite.class, () -> new MobRendererMoa(0.7F));
        ModelHelper.setEntityModel(MobMoaBlack.class, () -> new MobRendererMoa(0.7F));
        ModelHelper.setEntityModel(MobPhyg.class, () -> new MobRendererPhyg(0.7F));
        ModelHelper.setEntityModel(MobAerwhale.class, () -> new MobRendererAerwhale(1.0F));
        ModelHelper.setEntityModel(MobAerbunny.class, () -> new MobRendererAerbunny(0.5F));
        ModelHelper.setEntityModel(MobWhirly.class, () -> new MobRendererWhirly(0.7F));
    }

    private void setMobBossModels() {
        ModelHelper.setEntityModel(MobBossSlider.class, () -> new MobRendererSlider(new ModelSlider(0.0F, 12.0623F), 1.5F));
        ModelHelper.setEntityModel(MobBossValkyrie.class, () -> new MobRendererBossValkyrie(new ModelValkyrie(), 0.5F));
        ModelHelper.setEntityModel(MobBossSunspirit.class, MobRendererSunspirit::new);
        ModelHelper.setEntityModel(MobFireMinion.class, () -> new MobRendererFireMinion(0.4F));
    }

    private void setMobEnemyModels() {
        ModelHelper.setEntityModel(MobSentry.class, () -> new MobRendererSentry(0.6F));
        ModelHelper.setEntityModel(MobAechorPlant.class, () -> new MobRendererAechorPlant(new ModelAechorPlant(), 0.3F));
        ModelHelper.setEntityModel(MobSwet.class, () -> new MobRendererSwet(new ModelSlime(16), new ModelSlime(0), 1.0f));
        ModelHelper.setEntityModel(MobSwetGold.class, () -> new MobRendererSwet(new ModelSlime(16), new ModelSlime(0), 1.0f));
        ModelHelper.setEntityModel(MobZephyr.class, () -> new MobRendererZephyr(0.5F));
        ModelHelper.setEntityModel(MobMimic.class, () -> new MobRendererMimic(0.7f));
        ModelHelper.setEntityModel(MobCockatrice.class, () -> new MobRendererCockatrice(0.7F));
        ModelHelper.setEntityModel(MobValkyrie.class, () -> new MobRendererValkyrie(new ModelValkyrie(), 0.5F));
        ModelHelper.setEntityModel(MobTempest.class, () -> new MobRendererTempest(0.7F));
    }

    private void setProjectileModels() {
        ModelHelper.setEntityModel(ProjectileHammerHead.class, () -> new EntityRendererSprite<>(AetherItems.AMMO_HAMMER_HEAD).setScale(2.0f));
        ModelHelper.setEntityModel(ProjectileWindball.class, () -> new EntityRendererSprite<>(AetherItems.AMMO_WINDBALL).setScale(4.0F).setFullBright());
        ModelHelper.setEntityModel(ProjectileElementFire.class, () -> new EntityRendererSprite<>(AetherItems.PROJECTILE_FIRE).setScale(3.0F).setFullBright());
        ModelHelper.setEntityModel(ProjectileElementIce.class, () -> new EntityRendererSprite<>(AetherItems.PROJECTILE_ICE).setScale(3.0F).setFullBright());
        ModelHelper.setEntityModel(ProjectileElementLightning.class, () -> new EntityRendererSprite<>(AetherItems.PROJECTILE_LIGHTNING).setScale(3.0F).setFullBright());
        ModelHelper.setEntityModel(ProjectileDart.class, EntityRendererDart::new);
        ModelHelper.setEntityModel(ProjectileNeedle.class, EntityRendererNeedle::new);
        ModelHelper.setEntityModel(ProjectileArrowFlaming.class, EntityRendererArrowFlaming::new);
        ModelHelper.setEntityModel(ProjectileKnifeLightning.class, EntityRendererKnifeLightning::new);
    }

    @Override
    public void initTileEntityModels(TileEntityRenderDispatcher dispatcher) {
        ModelHelper.setTileEntityModel(TileEntitySignSkyroot.class, () -> {
                TileEntityRendererSignSkyroot renderer = new TileEntityRendererSignSkyroot()
                    .setDefaultTexture("/assets/aether/textures/entity/sign_skyroot.png", new Color().setARGB(0x695E43));

                Arrays.stream(DyeColor.values()).iterator().forEachRemaining(
                    dyeColor -> renderer.setColoredTexture(dyeColor.blockMeta, String.format("/assets/aether/textures/entity/sign_skyroot/%s.png", dyeColor.colorID))
                );

                return renderer;
            }
        );
    }
}
