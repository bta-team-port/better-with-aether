package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.EntityRendererDispatcher;
import net.minecraft.client.render.block.color.BlockColorCustom;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.BlockModelEmpty;
import net.minecraft.client.render.block.model.BlockModelGlass;
import net.minecraft.client.render.block.model.BlockModelHorizontalRotation;
import net.minecraft.client.render.block.model.generic.*;
import net.minecraft.client.render.entity.EntityRendererSprite;
import net.minecraft.client.render.entity.MobRendererPlayer;
import net.minecraft.client.render.item.model.ItemModelBlock;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.tileentity.TileEntityRendererStatue;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.block.ItemBlock;
import net.minecraft.core.util.helper.Side;
import org.jspecify.annotations.NonNull;
import org.useless.dragonfly.DisplayPos;
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

import static net.minecraft.client.render.block.model.BlockModelDispatcher.loadDataModel;

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


        dispatcher.addDispatch(new BlockModelGenericPortal<>(AetherBlocks.PORTAL_AETHER,
            "aether:block/portal_aether/"));

        dispatcher.addDispatch(new BlockModelGrassAether<>(AetherBlocks.GRASS_AETHER));

        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.DIRT_AETHER,
            loadDataModel("aether:block/dirt_aether")));

        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.PATH_DIRT_AETHER,
            loadDataModel("aether:block/grass_aether_path")));

        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.HOLYSTONE,
            loadDataModel("aether:block/holystone")));
        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.HOLYSTONE_MOSSY,
            loadDataModel("aether:block/moss/holystone")));

        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.HOLYSTONE_POLISHED,
            loadDataModel("aether:block/polished_holystone")));

        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.HOLYSTONE_CARVED,
            loadDataModel("aether:block/carved_holystone")));

        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.COBBLE_HOLYSTONE,
            loadDataModel("aether:block/cobbled_holystone")));
        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.COBBLE_HOLYSTONE_MOSSY,
            loadDataModel("aether:block/cobbled_holystone_mossy")));

        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.BRICK_HOLYSTONE,
            loadDataModel("aether:block/brick_holystone")));

        dispatcher.addDispatch(new BlockModelGenericPressurePlate<>(AetherBlocks.PRESSURE_PLATE_HOLYSTONE, "aether:block/pressure_plate/stone/holystone"));
        dispatcher.addDispatch(new BlockModelGenericPressurePlate<>(AetherBlocks.PRESSURE_PLATE_COBBLE_HOLYSTONE, "aether:block/pressure_plate/cobbled/holystone"));

        dispatcher.addDispatch(new BlockModelGenericButton<>(AetherBlocks.BUTTON_HOLYSTONE, "aether:block/button/stone/holystone"));

        dispatcher.addDispatch((new BlockModelEmpty<>(AetherBlocks.STATUE_HOLYSTONE_LOWER)).setAllTextures("aether:block/holystone"));
        dispatcher.addDispatch((new BlockModelEmpty<>(AetherBlocks.STATUE_HOLYSTONE_UPPER)).setAllTextures("aether:block/holystone"));

        TileEntityRendererStatue.BLOCK_SKIN_MAP.put(AetherBlocks.STATUE_HOLYSTONE_LOWER, "/assets/aether/textures/entity/statue/holystone.png");

        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.ICESTONE,
            loadDataModel("aether:block/icestone")));

        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.QUICKSOIL,
            loadDataModel("aether:block/quicksoil")));

        dispatcher.addDispatch((new BlockModelGlass<>(AetherBlocks.GLASS_QUICKSOIL,
            "aether:block/glass_quicksoil/"))
            .onRenderLayer(1)
            .setAllTextures("aether:block/glass_quicksoil"));

        dispatcher.addDispatch(new BlockModelGenericTrapdoor<>(AetherBlocks.TRAPDOOR_GLASS_QUICKSOIL,
            "aether:block/trapdoor/glass_quicksoil"));
        dispatcher.addDispatch(new BlockModelGenericDoor<>(AetherBlocks.DOOR_GLASS_QUICKSOIL_BOTTOM,
            "aether:block/door/glass_quicksoil",
            true));
        dispatcher.addDispatch(new BlockModelGenericDoor<>(AetherBlocks.DOOR_GLASS_QUICKSOIL_TOP,
            "aether:block/door/glass_quicksoil",
            false));

        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.ORE_AMBROSIUM_HOLYSTONE,
            loadDataModel("aether:block/ore/ambrosium/holystone")));
        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.ORE_ZANITE_HOLYSTONE,
            loadDataModel("aether:block/ore/zanite/holystone")));
        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.ORE_GRAVITITE_HOLYSTONE,
            loadDataModel("aether:block/ore/gravitite/holystone")));

        dispatcher.addDispatch((new BlockModelGeneric<>(AetherBlocks.BLOCK_AMBER,
            loadDataModel("aether:block/block_amber")))
            .forceCullSelf(true));

        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.BLOCK_AMBROSIUM,
            loadDataModel("aether:block/block_ambrosium")));
        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.BLOCK_ZANITE,
            loadDataModel("aether:block/block_zanite")));
        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.BLOCK_GRAVITITE,
            loadDataModel("aether:block/block_gravitite")));

        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.BRICK_ZANITE,
            loadDataModel("aether:block/brick_zanite")));
        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.BRICK_GRAVITITE,
            loadDataModel("aether:block/brick_gravitite")));

        dispatcher.addDispatch(new BlockModelGenericStairs<>(AetherBlocks.STAIRS_COBBLE_HOLYSTONE,
            loadDataModel("aether:block/stairs/cobbled_holystone")));
        dispatcher.addDispatch(new BlockModelGenericStairs<>(AetherBlocks.STAIRS_BRICK_HOLYSTONE,
            loadDataModel("aether:block/stairs/brick_holystone")));
        dispatcher.addDispatch(new BlockModelGenericStairs<>(AetherBlocks.STAIRS_BRICK_ZANITE,
            loadDataModel("aether:block/stairs/brick_zanite")));
        dispatcher.addDispatch(new BlockModelGenericStairs<>(AetherBlocks.STAIRS_BRICK_GRAVITITE,
            loadDataModel("aether:block/stairs/brick_gravitite")));

        dispatcher.addDispatch(new BlockModelGenericSlab<>(AetherBlocks.SLAB_COBBLE_HOLYSTONE,
            loadDataModel("aether:block/slab/cobbled_holystone/lower"),
            loadDataModel("aether:block/slab/cobbled_holystone/upper"),
            loadDataModel("aether:block/slab/cobbled_holystone/full")));

        dispatcher.addDispatch(new BlockModelGenericSlab<>(AetherBlocks.SLAB_BRICK_HOLYSTONE,
            loadDataModel("aether:block/slab/brick_holystone/lower"),
            loadDataModel("aether:block/slab/brick_holystone/upper"),
            loadDataModel("aether:block/slab/brick_holystone/full")));

        dispatcher.addDispatch(new BlockModelGenericSlab<>(AetherBlocks.SLAB_HOLYSTONE_POLISHED,
            loadDataModel("aether:block/slab/polished_holystone/lower"),
            loadDataModel("aether:block/slab/polished_holystone/upper"),
            loadDataModel("aether:block/slab/polished_holystone/full")));

        dispatcher.addDispatch(new BlockModelGenericSlab<>(AetherBlocks.SLAB_BRICK_ZANITE,
            loadDataModel("aether:block/slab/brick_zanite/lower"),
            loadDataModel("aether:block/slab/brick_zanite/upper"),
            loadDataModel("aether:block/slab/brick_zanite/full")));

        dispatcher.addDispatch(new BlockModelGenericSlab<>(AetherBlocks.SLAB_BRICK_GRAVITITE,
            loadDataModel("aether:block/slab/brick_gravitite/lower"),
            loadDataModel("aether:block/slab/brick_gravitite/upper"),
            loadDataModel("aether:block/slab/brick_gravitite/full")));


        dispatcher.addDispatch(new BlockModelGenericTorch<>(AetherBlocks.TORCH_AMBROSIUM,
            "aether:block/torch_ambrosium")
            .render3D(false));


        dispatcher.addDispatch(new BlockModelGenericLantern<>(AetherBlocks.LANTERN_FIREFLY_SILVER,
            loadDataModel("aether:block/lantern_firefly_silver"),
            loadDataModel("aether:block/lantern_firefly_silver_hanging")));


    }

    public void initItemModels(ItemModelDispatcher dispatcher) {
        this.setItemToolModels(dispatcher);
        this.setItemArmorModels(dispatcher);
        this.setItemBlockModels(dispatcher);

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.MEDAL_VICTORY));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.KEY_BRONZE));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.KEY_SILVER));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.KEY_GOLD));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.EGG_MOA_BLUE));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.EGG_MOA_WHITE));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.EGG_MOA_BLACK));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.RECORD_AETHER));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.RECORD_MORNING));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.RECORD_DAWN));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.RECORD_NETHER));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.AMBER));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.PETAL_AECHOR));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.STICK_SKYROOT));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.AMBROSIUM));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ZANITE));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ORE_RAW_GRAVITITE));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.BUCKET_SKYROOT));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.BUCKET_SKYROOT_WATER));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.BUCKET_SKYROOT_MILK));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.BUCKET_SKYROOT_POISON));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.BUCKET_SKYROOT_REMEDY));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.BUCKET_SKYROOT_ICECREAM));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.FOOD_HEALING_STONE));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.FOOD_GUMMY_BLUE));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.FOOD_GUMMY_GOLD));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.LIFESHARD));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.PARACHUTE_CLOUD));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.PARACHUTE_CLOUD_GOLD));


        dispatcher.addDispatch(new ItemModelStandard(AetherItems.LANTERN_FIREFLY_SILVER).setFullBright());

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.AMMO_WINDBALL));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.PROJECTILE_FIRE).setFullBright());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.PROJECTILE_ICE));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.PROJECTILE_LIGHTNING));


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
        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.PLANKS_SKYROOT,
            loadDataModel("aether:block/planks_skyroot/skyroot")));
        dispatcher.addDispatch(new BlockModelGenericPlanksSkyrootPainted<>(AetherBlocks.PLANKS_SKYROOT_PAINTED,
            loadDataModel("aether:block/planks_skyroot/white")));

        dispatcher.addDispatch(new BlockModelGenericDoor<>(AetherBlocks.DOOR_PLANKS_SKYROOT_BOTTOM,
            "aether:block/door/planks_skyroot/skyroot",
            true));
        dispatcher.addDispatch(new BlockModelGenericDoor<>(AetherBlocks.DOOR_PLANKS_SKYROOT_TOP,
            "aether:block/door/planks_skyroot/skyroot",
            false));

        dispatcher.addDispatch(new BlockModelGenericDoorSkyrootPainted<>(AetherBlocks.DOOR_PLANKS_SKYROOT_PAINTED_BOTTOM, true));
        dispatcher.addDispatch(new BlockModelGenericDoorSkyrootPainted<>(AetherBlocks.DOOR_PLANKS_SKYROOT_PAINTED_TOP, false));

        dispatcher.addDispatch((new BlockModelEmpty<>(AetherBlocks.SIGN_POST_PLANKS_SKYROOT))
            .setAllTextures("aether:block/planks_skyroot/skyroot"));
        dispatcher.addDispatch((new BlockModelEmpty<>(AetherBlocks.SIGN_WALL_PLANKS_SKYROOT))
            .setAllTextures("aether:block/planks_skyroot/skyroot"));
        dispatcher.addDispatch((new BlockModelSignSkyrootPainted<>(AetherBlocks.SIGN_POST_PLANKS_SKYROOT_PAINTED))
            .setAllTextures("aether:block/planks_skyroot/skyroot"));
        dispatcher.addDispatch((new BlockModelSignSkyrootPainted<>(AetherBlocks.SIGN_WALL_PLANKS_SKYROOT_PAINTED))
            .setAllTextures("aether:block/planks_skyroot/skyroot"));

        dispatcher.addDispatch(new BlockModelGenericTrapdoor<>(AetherBlocks.TRAPDOOR_PLANKS_SKYROOT,
            "aether:block/trapdoor/planks_skyroot/skyroot"));
        dispatcher.addDispatch(new BlockModelGenericTrapdoorSkyrootPainted<>(AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED));

        dispatcher.addDispatch(new BlockModelGenericChest<>(AetherBlocks.CHEST_PLANKS_SKYROOT,
            loadDataModel("aether:block/chest/single/skyroot"),
            loadDataModel("aether:block/chest/left/skyroot"),
            loadDataModel("aether:block/chest/right/skyroot")));
        dispatcher.addDispatch(new BlockModelGenericChestSkyrootPainted<>(AetherBlocks.CHEST_PLANKS_SKYROOT_PAINTED));

        dispatcher.addDispatch(new BlockModelGenericButton<>(AetherBlocks.BUTTON_PLANKS_SKYROOT,
            "aether:block/button/planks_skyroot/skyroot"));
        dispatcher.addDispatch(new BlockModelGenericButtonSkyrootPainted<>(AetherBlocks.BUTTON_PLANKS_SKYROOT_PAINTED));

        dispatcher.addDispatch(new BlockModelGenericPressurePlate<>(AetherBlocks.PRESSURE_PLATE_PLANKS_SKYROOT,
            "aether:block/pressure_plate/planks_skyroot/skyroot"));
        dispatcher.addDispatch(new BlockModelGenericPressurePlateSkyrootPainted<>(AetherBlocks.PRESSURE_PLATE_PLANKS_SKYROOT_PAINTED));

        dispatcher.addDispatch(new BlockModelGenericFence<>(AetherBlocks.FENCE_PLANKS_SKYROOT,
            "aether:block/fence/skyroot"));
        dispatcher.addDispatch(new BlockModelGenericFenceSkyrootPainted<>(AetherBlocks.FENCE_PLANKS_SKYROOT_PAINTED));

        dispatcher.addDispatch(new BlockModelGenericFenceGate<>(AetherBlocks.FENCEGATE_PLANKS_SKYROOT,
            "aether:block/fencegate/skyroot"));
        dispatcher.addDispatch(new BlockModelGenericFenceGateSkyrootPainted<>(AetherBlocks.FENCEGATE_PLANKS_SKYROOT_PAINTED));

        dispatcher.addDispatch(new BlockModelGenericSlab<>(AetherBlocks.SLAB_PLANKS_SKYROOT,
            loadDataModel("aether:block/slab/planks_skyroot/skyroot/lower"),
            loadDataModel("aether:block/slab/planks_skyroot/skyroot/upper"),
            loadDataModel("aether:block/slab/planks_skyroot/skyroot/full")));
        dispatcher.addDispatch(new BlockModelGenericSlabSkyrootPainted<>(AetherBlocks.SLAB_PLANKS_SKYROOT_PAINTED));

        dispatcher.addDispatch(new BlockModelGenericStairs<>(AetherBlocks.STAIRS_PLANKS_SKYROOT,
            loadDataModel("aether:block/stairs/planks_skyroot/skyroot")));
        dispatcher.addDispatch(new BlockModelGenericStairsSkyrootPainted<>(AetherBlocks.STAIRS_PLANKS_SKYROOT_PAINTED));
    }

    private void setBlockMachineModels(@NonNull BlockModelDispatcher dispatcher) {
        dispatcher.addDispatch(new BlockModelGenericFurnace<>(AetherBlocks.ENCHANTER_IDLE, "aether:block/enchanter/idle"));
        dispatcher.addDispatch(new BlockModelGenericFurnace<>(AetherBlocks.ENCHANTER_ACTIVE, "aether:block/enchanter/active"));

        dispatcher.addDispatch(new BlockModelGenericFreezer<>(AetherBlocks.FREEZER_IDLE, "aether:block/freezer/idle"));
        dispatcher.addDispatch(new BlockModelGenericFreezer<>(AetherBlocks.FREEZER_ACTIVE, "aether:block/freezer/active"));

        dispatcher.addDispatch(new BlockModelGenericIncubator<>(AetherBlocks.INCUBATOR_IDLE, "aether:block/incubator/idle"));
        dispatcher.addDispatch(new BlockModelGenericIncubator<>(AetherBlocks.INCUBATOR_ACTIVE, "aether:block/incubator/active"));


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

        // =============================================================================================================
        // TODO: make them use generic model
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
        // =============================================================================================================

        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.CARVED_STONE_LOCKED,
            loadDataModel("aether:block/dungeon/carved")));
        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.CARVED_STONE_LIGHT_LOCKED,
            loadDataModel("aether:block/dungeon/carved_glow")));

        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.CARVED_ANGELIC_LOCKED,
            loadDataModel("aether:block/dungeon/angelic")));
        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.CARVED_ANGELIC_LIGHT_LOCKED,
            loadDataModel("aether:block/dungeon/angelic_glow")));

        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.CARVED_HELLFIRE_LOCKED,
            loadDataModel("aether:block/dungeon/hellfire")));
        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.CARVED_HELLFIRE_LIGHT_LOCKED,
            loadDataModel("aether:block/dungeon/hellfire_glow")));

        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.CARVED_STONE_TRAPPED,
            loadDataModel("aether:block/dungeon/carved")));
        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.CARVED_STONE_TRAPPED_LOCKED,
            loadDataModel("aether:block/dungeon/carved")));

        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.CARVED_ANGELIC_TRAPPED,
            loadDataModel("aether:block/dungeon/angelic")));
        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.CARVED_ANGELIC_TRAPPED_LOCKED,
            loadDataModel("aether:block/dungeon/angelic")));

        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.CARVED_HELLFIRE_TRAPPED,
            loadDataModel("aether:block/dungeon/hellfire")));
        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.CARVED_HELLFIRE_TRAPPED_LOCKED,
            loadDataModel("aether:block/dungeon/hellfire")));

        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.CARVED_STONE,
            loadDataModel("aether:block/dungeon/carved")));
        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.CARVED_STONE_LIGHT,
            loadDataModel("aether:block/dungeon/carved_glow")));

        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.CARVED_ANGELIC,
            loadDataModel("aether:block/dungeon/angelic")));
        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.CARVED_ANGELIC_LIGHT,
            loadDataModel("aether:block/dungeon/angelic_glow")));

        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.CARVED_HELLFIRE,
            loadDataModel("aether:block/dungeon/hellfire")));
        dispatcher.addDispatch(new BlockModelGeneric<>(AetherBlocks.CARVED_HELLFIRE_LIGHT,
            loadDataModel("aether:block/dungeon/hellfire_glow")));

        dispatcher.addDispatch(new BlockModelGenericAxis<>(AetherBlocks.PILLAR,
            loadDataModel("aether:block/pillar")));
        dispatcher.addDispatch(new BlockModelGenericAxis<>(AetherBlocks.PILLAR_CAPSTONE,
            loadDataModel("aether:block/pillar_capstone")));

        dispatcher.addDispatch(new BlockModelGenericSlab<>(AetherBlocks.SLAB_CARVED_STONE,
            loadDataModel("aether:block/slab/carved_stone/lower"),
            loadDataModel("aether:block/slab/carved_stone/upper"),
            loadDataModel("aether:block/slab/carved_stone/full")));
        dispatcher.addDispatch(new BlockModelGenericSlab<>(AetherBlocks.SLAB_CARVED_ANGELIC,
            loadDataModel("aether:block/slab/carved_angelic/lower"),
            loadDataModel("aether:block/slab/carved_angelic/upper"),
            loadDataModel("aether:block/slab/carved_angelic/full")));
        dispatcher.addDispatch(new BlockModelGenericSlab<>(AetherBlocks.SLAB_CARVED_HELLFIRE,
            loadDataModel("aether:block/slab/carved_hellfire/lower"),
            loadDataModel("aether:block/slab/carved_hellfire/upper"),
            loadDataModel("aether:block/slab/carved_hellfire/full")));

        dispatcher.addDispatch(new BlockModelGenericStairs<>(AetherBlocks.STAIRS_CARVED_STONE,
            loadDataModel("aether:block/stairs/carved_stone")));
        dispatcher.addDispatch(new BlockModelGenericStairs<>(AetherBlocks.STAIRS_CARVED_ANGELIC,
            loadDataModel("aether:block/stairs/carved_angelic")));
        dispatcher.addDispatch(new BlockModelGenericStairs<>(AetherBlocks.STAIRS_CARVED_HELLFIRE,
            loadDataModel("aether:block/stairs/carved_hellfire")));

    }

    private void setBlockPlantModels(@NonNull BlockModelDispatcher dispatcher) {
        dispatcher.addDispatch(new BlockModelFixedAxis<>(AetherBlocks.LOG_SKYROOT,
            loadDataModel("aether:block/log/skyroot")));
        dispatcher.addDispatch(new BlockModelFixedAxis<>(AetherBlocks.LOG_OAK_GOLDEN,
            loadDataModel("aether:block/log/oak_golden")));
        dispatcher.addDispatch(new BlockModelFixedAxis<>(AetherBlocks.LOG_AETHER_SCORCHED,
            loadDataModel("aether:block/log/aether_scorched")));

        dispatcher.addDispatch(new BlockModelGenericLeaves<>(AetherBlocks.LEAVES_SKYROOT,
            "aether:block/leaves/skyroot"));
        dispatcher.addDispatch(new BlockModelGenericLeaves<>(AetherBlocks.LEAVES_OAK_GOLDEN,
            "aether:block/leaves/oak_golden"));

        dispatcher.addDispatch((new BlockModelGeneric<>(AetherBlocks.SAPLING_SKYROOT,
            loadDataModel("aether:block/sapling/skyroot")))
            .render3D(false));
        dispatcher.addDispatch((new BlockModelGeneric<>(AetherBlocks.SAPLING_OAK_GOLDEN,
            loadDataModel("aether:block/sapling/oak_golden")))
            .render3D(false));

        dispatcher.addDispatch((new BlockModelGenericFlowerStackable<>(AetherBlocks.FLOWER_PURPLE,
            "aether:block/flower_purple/"))
            .render3D(false));
        dispatcher.addDispatch((new BlockModelGenericFlowerStackable<>(AetherBlocks.FLOWER_WHITE,
            "aether:block/flower_white/"))
            .render3D(false));

        dispatcher.addDispatch((new BlockModelGenericShifted<>(AetherBlocks.TALLGRASS_AETHER,
            loadDataModel("aether:block/tallgrass_aether")))
            .render3D(false));

        dispatcher.addDispatch((new BlockModelGeneric<>(AetherBlocks.DEADBUSH_AETHER,
            loadDataModel("aether:block/deadbush_aether")))
            .render3D(false));
    }

    private void setBlockCloudModels(@NonNull BlockModelDispatcher dispatcher) {
        dispatcher.addDispatch((new BlockModelGeneric<>(AetherBlocks.AERCLOUD_WHITE,
            loadDataModel("aether:block/aercloud/white")))
            .forceCullSelf(true));

        dispatcher.addDispatch((new BlockModelGeneric<>(AetherBlocks.AERCLOUD_BLUE,
            loadDataModel("aether:block/aercloud/blue")))
            .forceCullSelf(true));

        dispatcher.addDispatch((new BlockModelGeneric<>(AetherBlocks.AERCLOUD_GOLD,
            loadDataModel("aether:block/aercloud/gold")))
            .forceCullSelf(true));

        dispatcher.addDispatch((new BlockModelGeneric<>(AetherBlocks.AEROGEL,
            loadDataModel("aether:block/aerogel")))
            .forceCullSelf(true));
    }

    private void setItemArmorModels(@NonNull ItemModelDispatcher dispatcher) {
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.AMMO_HAMMER_HEAD));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_SHIELD_REPULSION));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.AMMO_DART_GOLDEN));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.AMMO_DART_POISON));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.AMMO_DART_ENCHANTED));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.AMMO_ARROW_FLAMING).setFullBright());


        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_HELMET_ZANITE));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CHESTPLATE_ZANITE));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_LEGGINGS_ZANITE));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_BOOTS_ZANITE));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_HELMET_GRAVITITE));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CHESTPLATE_GRAVITITE));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_LEGGINGS_GRAVITITE));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_BOOTS_GRAVITITE));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_HELMET_OBSIDIAN));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CHESTPLATE_OBSIDIAN));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_LEGGINGS_OBSIDIAN));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_BOOTS_OBSIDIAN));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_HELMET_PHOENIX).setFullBright());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CHESTPLATE_PHOENIX).setFullBright());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_LEGGINGS_PHOENIX).setFullBright());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_BOOTS_PHOENIX).setFullBright());

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_HELMET_NEPTUNE));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CHESTPLATE_NEPTUNE));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_LEGGINGS_NEPTUNE));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_BOOTS_NEPTUNE));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_WOLF_ZANITE));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_WOLF_GRAVITITE));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_WOLF_NEPTUNE));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_WOLF_OBSIDIAN));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_WOLF_PHOENIX));


        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_REGEN));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_BUBBLE));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_FEATHER_GOLD));


        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_LEATHER));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_CHAINMAIL));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_IRON));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_GOLD));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_DIAMOND));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_STEEL));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_ZANITE));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_GRAVITITE));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_TALISMAN_ICE));


        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_LEATHER));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_CHAINMAIL));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_IRON));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_GOLD));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_DIAMOND));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_STEEL));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_ZANITE));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_GRAVITITE));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_OBSIDIAN));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_PHOENIX).setFullBright());
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_GLOVES_NEPTUNE));


        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_AGILITY));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_SWET));
        dispatcher.addDispatch(new ItemModelTransparent(AetherItems.ARMOR_CAPE_INVISIBILITY));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_WHITE));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_SILVER));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_GRAY));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_BLACK));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_BROWN));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_RED));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_YELLOW));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_ORANGE));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_LIME));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_GREEN));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_CYAN));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_BLUE));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_LIGHTBLUE));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_PURPLE));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_MAGENTA));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.ARMOR_CAPE_PINK));
    }

    private static @NonNull ItemModelStandard handheldTool(Item item) {
        return new ItemModelStandard(item, true)
            .setDisplayPos(DisplayPos.FIRST_PERSON_RIGHT_HAND, ItemModelDispatcher.HANDHELD_FIRST_PERSON_RIGHT_HAND)
            .setDisplayPos(DisplayPos.FIRST_PERSON_LEFT_HAND, ItemModelDispatcher.HANDHELD_FIRST_PERSON_LEFT_HAND)
            .setDisplayPos(DisplayPos.THIRD_PERSON_RIGHT_HAND, ItemModelDispatcher.HANDHELD_THIRD_PERSON_RIGHT_HAND)
            .setDisplayPos(DisplayPos.THIRD_PERSON_LEFT_HAND, ItemModelDispatcher.HANDHELD_THIRD_PERSON_LEFT_HAND);
    }

    private void setItemToolModels(@NonNull ItemModelDispatcher dispatcher) {
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_SWORD_SKYROOT));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_SHOVEL_SKYROOT));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_PICKAXE_SKYROOT));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_AXE_SKYROOT));

        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_SWORD_HOLYSTONE));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_SHOVEL_HOLYSTONE));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_PICKAXE_HOLYSTONE));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_AXE_HOLYSTONE));

        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_SWORD_ZANITE));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_SHOVEL_ZANITE));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_PICKAXE_ZANITE));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_AXE_ZANITE));

        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_SWORD_GRAVITITE));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_SHOVEL_GRAVITITE));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_PICKAXE_GRAVITITE));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_AXE_GRAVITITE));

        dispatcher.addDispatch(new ItemModelLance(AetherItems.TOOL_SWORD_VALKYRIE, true));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_SHOVEL_VALKYRIE));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_PICKAXE_VALKYRIE));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_AXE_VALKYRIE));

        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_KNIFE_LIGHTNING));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_HAMMER_NOTCH));
        dispatcher.addDispatch(new ItemModelBowPhoenix(AetherItems.TOOL_BOW_PHOENIX, true).setFullBright()
            .setDisplayPos("firstperson_righthand", new DisplayPos(0.070625f, 0.2f, 0.070625f, 0f, -90f, 25f, 0.68f, 0.68f, 0.68f))
            .setDisplayPos("firstperson_lefthand", new DisplayPos(0.070625f, 0.2f, 0.070625f, 0f, 90f, -25f, 0.68f, 0.68f, 0.68f))
            .setDisplayPos("thirdperson_righthand", new DisplayPos(-0.0625f, -0.125f, 0.15625f, -80f, 260f, -40f, 0.9f, 0.9f, 0.9f))
            .setDisplayPos("thirdperson_lefthand", new DisplayPos(-0.0625f, -0.125f, 0.15625f, -80f, -280f, 40f, 0.9f, 0.9f, 0.9f)));

        dispatcher.addDispatch(new ItemModelShooter(AetherItems.TOOL_SHOOTER, true));

        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_SWORD_FLAME).setFullBright());
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_SWORD_HOLY));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_SWORD_LIGHTNING));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_SWORD_PIG));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_SWORD_VAMPIRE));

        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_STAFF_NATURE));
        dispatcher.addDispatch(handheldTool(AetherItems.TOOL_STAFF_CLOUD));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.TOOL_DUNGEON_COMPASS));
    }

    private void setItemBlockModels(@NonNull ItemModelDispatcher dispatcher) {
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.DOOR_DUNGEON_BRONZE));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.DOOR_DUNGEON_SILVER));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.DOOR_DUNGEON_GOLD));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.DOOR_GLASS_AMBROSIUM));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.DOOR_SKYROOT));
        dispatcher.addDispatch(new ItemModelPaintedSkyrootDoor(AetherItems.DOOR_SKYROOT_PAINTED));
        dispatcher.addDispatch(new ItemModelStandard(AetherItems.SIGN_SKYROOT));
        dispatcher.addDispatch(new ItemModelPaintedSkyrootSign(AetherItems.SIGN_SKYROOT_PAINTED));

        dispatcher.addDispatch(new ItemModelStandard(AetherItems.STATUE_HOLYSTONE));
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
