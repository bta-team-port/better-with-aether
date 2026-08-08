package teamport.aether.entity;

import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.entity.EntityDispatcher;
import teamport.aether.block.entity.TileEntityEnchanter;
import teamport.aether.block.entity.TileEntityFreezer;
import teamport.aether.block.entity.TileEntityIncubator;
import teamport.aether.block.entity.TileEntityMimic;
import teamport.aether.entity.animal.aerbunny.MobAerbunny;
import teamport.aether.entity.animal.aerwhale.MobAerwhale;
import teamport.aether.entity.animal.moa.MobMoaBlack;
import teamport.aether.entity.animal.moa.MobMoaBlue;
import teamport.aether.entity.animal.moa.MobMoaWhite;
import teamport.aether.entity.animal.phow.MobPhow;
import teamport.aether.entity.animal.phyg.MobPhyg;
import teamport.aether.entity.animal.sheepuff.MobSheepuff;
import teamport.aether.entity.animal.whirly.MobWhirly;
import teamport.aether.entity.boss.slider.MobBossSlider;
import teamport.aether.entity.boss.sunspirit.MobBossSunspirit;
import teamport.aether.entity.boss.valkyrie.queen.MobBossValkyrie;
import teamport.aether.entity.floating_block.EntityFloatingBlock;
import teamport.aether.entity.monster.aechorplant.MobAechorPlant;
import teamport.aether.entity.monster.cockatrice.MobCockatrice;
import teamport.aether.entity.monster.fireminion.MobFireMinion;
import teamport.aether.entity.monster.mimic.MobMimic;
import teamport.aether.entity.monster.sentry.MobSentry;
import teamport.aether.entity.monster.swet.MobSwet;
import teamport.aether.entity.monster.swet.MobSwetGold;
import teamport.aether.entity.monster.tempest.MobTempest;
import teamport.aether.entity.monster.valkyrie.MobValkyrie;
import teamport.aether.entity.monster.zephyr.MobZephyr;
import teamport.aether.entity.projectile.*;
import teamport.aether.entity.vehicle.parachute.EntityParachute;
import teamport.aether.entity.vehicle.parachute.EntityParachuteGold;
import turniplabs.halplibe.helper.EntityHelper;

import static teamport.aether.AetherMod.MOD_ID;

public final class AetherEntities {
    private static boolean hasInit = false;

    public static void init() {
        if (!hasInit) {
            hasInit = true;
            initializeEntities();
        }

    }

    public static void initializeEntities() {
        EntityHelper helper = new EntityHelper();
        EntityDispatcher dispatcher = EntityDispatcher.getInstance();
        dispatcher.addMapping(MobSentry.class, NamespaceID.getPermanent(MOD_ID, "sentry"), MobSentry::new, "guidebook.section.mob.sentry.name");
        dispatcher.addMapping(MobZephyr.class, NamespaceID.getPermanent(MOD_ID, "zephyr"), MobZephyr::new, "guidebook.section.mob.zephyr.name");
        dispatcher.addMapping(MobAechorPlant.class, NamespaceID.getPermanent(MOD_ID, "aechorplant"), MobAechorPlant::new, "guidebook.section.mob.aechorplant.name");
        dispatcher.addMapping(MobMimic.class, NamespaceID.getPermanent(MOD_ID, "mimic"), MobMimic::new, "guidebook.section.mob.mimic.name");
        dispatcher.addMapping(MobSwet.class, NamespaceID.getPermanent(MOD_ID, "swet"), MobSwet::new, "guidebook.section.mob.swet.name");
        dispatcher.addMapping(MobSwetGold.class, NamespaceID.getPermanent(MOD_ID, "swet_gold"), MobSwetGold::new, "guidebook.section.mob.swet.gold.name");
        dispatcher.addMapping(MobCockatrice.class, NamespaceID.getPermanent(MOD_ID, "cockatrice"), MobCockatrice::new, "guidebook.section.mob.cockatrice.name");
        dispatcher.addMapping(MobValkyrie.class, NamespaceID.getPermanent(MOD_ID, "valkyrie"), MobValkyrie::new, "guidebook.section.mob.valkyrie.name");
        dispatcher.addMapping(MobWhirly.class, NamespaceID.getPermanent(MOD_ID, "whirly"), MobWhirly::new, "guidebook.section.mob.whirly.name");
        dispatcher.addMapping(MobTempest.class, NamespaceID.getPermanent(MOD_ID, "tempest"), MobTempest::new, "guidebook.section.mob.tempest.name");
        dispatcher.addMapping(MobFireMinion.class, NamespaceID.getPermanent(MOD_ID, "fire_minion"), MobFireMinion::new, "guidebook.section.mob.fireminion.name");

        dispatcher.addMapping(MobBossSlider.class, NamespaceID.getPermanent(MOD_ID, "boss_slider"), MobBossSlider::new, "guidebook.section.mob.slider.name");
        dispatcher.addMapping(MobBossValkyrie.class, NamespaceID.getPermanent(MOD_ID, "boss_valkyrie"), MobBossValkyrie::new, "guidebook.section.mob.valkyrie.queen.name");
        dispatcher.addMapping(MobBossSunspirit.class, NamespaceID.getPermanent(MOD_ID, "boss_sunspirit"), MobBossSunspirit::new, "guidebook.section.mob.sunspirit.name");

        dispatcher.addMapping(MobSheepuff.class, NamespaceID.getPermanent(MOD_ID, "sheepuff"), MobSheepuff::new, "guidebook.section.mob.sheepuff.name");
        dispatcher.addMapping(MobPhow.class, NamespaceID.getPermanent(MOD_ID, "phow"), MobPhow::new, "guidebook.section.mob.phow.name");
        dispatcher.addMapping(MobPhyg.class, NamespaceID.getPermanent(MOD_ID, "phyg"), MobPhyg::new, "guidebook.section.mob.phyg.name");
        dispatcher.addMapping(MobAerwhale.class, NamespaceID.getPermanent(MOD_ID, "aerwhale"), MobAerwhale::new, "guidebook.section.mob.aerwhale.name");
        dispatcher.addMapping(MobAerbunny.class, NamespaceID.getPermanent(MOD_ID, "aerbunny"), MobAerbunny::new, "guidebook.section.mob.aerbunny.name");

        dispatcher.addMapping(MobMoaBlue.class, NamespaceID.getPermanent(MOD_ID, "moa_blue"), MobMoaBlue::new, "guidebook.section.mob.moa.blue.name");
        dispatcher.addMapping(MobMoaWhite.class, NamespaceID.getPermanent(MOD_ID, "moa_white"), MobMoaWhite::new, "guidebook.section.mob.moa.white.name");
        dispatcher.addMapping(MobMoaBlack.class, NamespaceID.getPermanent(MOD_ID, "moa_black"), MobMoaBlack::new, "guidebook.section.mob.moa.black.name");


        helper.createEntity(EntityParachute.class, NamespaceID.getPermanent(MOD_ID, "parachute"), EntityParachute::new);
        helper.createEntity(EntityParachuteGold.class, NamespaceID.getPermanent(MOD_ID, "parachute_gold"), EntityParachuteGold::new);

        helper.createEntity(EntityFloatingBlock.class, NamespaceID.getPermanent(MOD_ID, "floating_block"), EntityFloatingBlock::new);


        EntityHelper.addMapping(TileEntityEnchanter.class, NamespaceID.getPermanent(MOD_ID, "enchanter"));
        EntityHelper.addMapping(TileEntityFreezer.class, NamespaceID.getPermanent(MOD_ID, "freezer"));
        EntityHelper.addMapping(TileEntityIncubator.class, NamespaceID.getPermanent(MOD_ID, "incubator"));
        EntityHelper.addMapping(TileEntityMimic.class, NamespaceID.getPermanent(MOD_ID, "chest_mimic"));

        helper.createEntity(ProjectileKnifeLightning.class, NamespaceID.getPermanent(MOD_ID, "knife_lightning"), ProjectileKnifeLightning::new);
        helper.createEntity(ProjectileDart.class, NamespaceID.getPermanent(MOD_ID, "dart"), ProjectileDart::new);
        helper.createEntity(ProjectileNeedle.class, NamespaceID.getPermanent(MOD_ID, "needle"), ProjectileNeedle::new);
        helper.createEntity(ProjectileArrowFlaming.class, NamespaceID.getPermanent(MOD_ID, "arrow_flaming"), ProjectileArrowFlaming::new);
        helper.createEntity(ProjectileHammerHead.class, NamespaceID.getPermanent(MOD_ID, "hammer_head"), ProjectileHammerHead::new);
        helper.createEntity(ProjectileWindball.class, NamespaceID.getPermanent(MOD_ID, "windball"), ProjectileWindball::new);
        helper.createEntity(ProjectileElementFire.class, NamespaceID.getPermanent(MOD_ID, "projectile_fire"), ProjectileElementFire::new);
        helper.createEntity(ProjectileElementIce.class, NamespaceID.getPermanent(MOD_ID, "projectile_ice"), ProjectileElementIce::new);
        helper.createEntity(ProjectileElementLightning.class, NamespaceID.getPermanent(MOD_ID, "projectile_lightning"), ProjectileElementLightning::new);
    }
}
