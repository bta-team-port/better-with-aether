package teamport.aether.entity;

import net.minecraft.client.gui.guidebook.mobs.MobInfoRegistry;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.entity.animal.aerbunny.MobAerbunny;
import teamport.aether.entity.animal.aerwhale.MobAerwhale;
import teamport.aether.entity.animal.moa.MobMoa;
import teamport.aether.entity.animal.moa.MobMoaBlack;
import teamport.aether.entity.animal.moa.MobMoaWhite;
import teamport.aether.entity.animal.phow.MobPhow;
import teamport.aether.entity.animal.phyg.MobPhyg;
import teamport.aether.entity.animal.sheepuff.MobSheepuff;
import teamport.aether.entity.boss.slider.MobBossSlider;
import teamport.aether.entity.boss.sunspirit.MobBossSunspirit;
import teamport.aether.entity.monster.cockatrice.MobCockatrice;
import teamport.aether.entity.monster.mimic.MobMimic;
import teamport.aether.entity.monster.sentry.MobSentry;
import teamport.aether.entity.monster.valkyrie.MobBossValkyrie;
import teamport.aether.entity.monster.valkyrie.MobValkyrie;
import teamport.aether.entity.monster.zephyr.MobZephyr;
import teamport.aether.items.AetherItems;

public class AetherMobInfoRegistry {
    public static boolean hasInit = false;

    public static void init() {
        if(!hasInit){
            hasInit = true;
            initializeMobInfoRegistry();
        }
    }

    public static void initializeMobInfoRegistry() {
        // Passives
        MobInfoRegistry.register(MobPhow.class, "guidebook.section.mob.phow.name", "guidebook.section.mob.phow.desc", 10, 10, new MobInfoRegistry.MobDrop[]{
                new MobInfoRegistry.MobDrop(new ItemStack(Items.LEATHER), 1.0f, 1 ,5),
                new MobInfoRegistry.MobDrop(new ItemStack(Items.FEATHER_CHICKEN), 1.0f, 0, 2)});

        MobInfoRegistry.register(MobPhyg.class, "guidebook.section.mob.phyg.name", "guidebook.section.mob.phyg.desc", 10, 10, new MobInfoRegistry.MobDrop[]{
                new MobInfoRegistry.MobDrop(new ItemStack(Items.FOOD_PORKCHOP_RAW), 1.0f, 1 ,2),
                new MobInfoRegistry.MobDrop(new ItemStack(Items.FEATHER_CHICKEN), 1.0f, 0, 2)});

        MobInfoRegistry.register(MobSheepuff.class, "guidebook.section.mob.sheepuff.name", "guidebook.section.mob.sheepuff.desc", 10, 10, new MobInfoRegistry.MobDrop[]{
                new MobInfoRegistry.MobDrop(new ItemStack(Blocks.WOOL), 1.0f, 0 ,2)});

        MobInfoRegistry.register(MobAerbunny.class, "guidebook.section.mob.aerbunny.name", "guidebook.section.mob.aerbunny.desc", 10, 10, new MobInfoRegistry.MobDrop[]{
                new MobInfoRegistry.MobDrop(new ItemStack(Items.STRING), 1.0f, 1 ,1)});

        MobInfoRegistry.register(MobAerwhale.class, "guidebook.section.mob.aerwhale.name", "guidebook.section.mob.aerwhale.desc", 0, 10, null);


        MobInfoRegistry.register(MobMoa.class, "guidebook.section.mob.moa.name", "guidebook.section.mob.moa.desc", 40, 10, new MobInfoRegistry.MobDrop[]{
                new MobInfoRegistry.MobDrop(new ItemStack(Items.FEATHER_CHICKEN), 1.0f, 0, 2)});
        MobInfoRegistry.register(MobMoaWhite.class, "guidebook.section.mob.moa.white.name", "guidebook.section.mob.moa.white.desc", 40, 10, new MobInfoRegistry.MobDrop[]{
                new MobInfoRegistry.MobDrop(new ItemStack(Items.FEATHER_CHICKEN), 1.0f, 0, 2)});
        MobInfoRegistry.register(MobMoaBlack.class, "guidebook.section.mob.moa.black.name", "guidebook.section.mob.moa.black.desc", 40, 10, new MobInfoRegistry.MobDrop[]{
                new MobInfoRegistry.MobDrop(new ItemStack(Items.FEATHER_CHICKEN), 1.0f, 0, 2)});
        
        // Hostiles
        MobInfoRegistry.register(MobZephyr.class, "guidebook.section.mob.zephyr.name", "guidebook.section.mob.zephyr.desc", 10, 500, new MobInfoRegistry.MobDrop[]{
                new MobInfoRegistry.MobDrop(new ItemStack(AetherBlocks.AERCLOUD_WHITE), 1.0f, 0, 6)});

        MobInfoRegistry.register(MobCockatrice.class, "guidebook.section.mob.cockatrice.name", "guidebook.section.mob.cockatrice.desc", 40, 10, new MobInfoRegistry.MobDrop[]{
                new MobInfoRegistry.MobDrop(new ItemStack(Items.FEATHER_CHICKEN), 1.0f, 0, 2)});
        
        MobInfoRegistry.register(MobSentry.class, "guidebook.section.mob.sentry.name", "guidebook.section.mob.sentry.desc", 10, 200, new MobInfoRegistry.MobDrop[]{
                new MobInfoRegistry.MobDrop(new ItemStack(AetherBlocks.CARVED_STONE), 1.0f, 1 ,1),
                new MobInfoRegistry.MobDrop(new ItemStack(AetherBlocks.CARVED_STONE_LIGHT), 1.0f, 1, 1)});

        MobInfoRegistry.register(MobMimic.class, "guidebook.section.mob.mimic.name", "guidebook.section.mob.mimic.desc", 20, 300, new MobInfoRegistry.MobDrop[]{
                new MobInfoRegistry.MobDrop(new ItemStack(AetherBlocks.CHEST_PLANKS_SKYROOT), 1.0f, 1, 1)});

        MobInfoRegistry.register(MobValkyrie.class, "guidebook.section.mob.valkyrie.name", "guidebook.section.mob.valkyrie.desc", 20, 300, new MobInfoRegistry.MobDrop[]{
                new MobInfoRegistry.MobDrop(new ItemStack(AetherItems.MEDAL_VICTORY), 100.0f, 1, 1)});


        // Bosses
        MobInfoRegistry.register(MobBossSlider.class, "guidebook.section.mob.slider.name", "guidebook.section.mob.slider.desc", 500, 10000, new MobInfoRegistry.MobDrop[]{
                new MobInfoRegistry.MobDrop(new ItemStack(AetherItems.KEY_BRONZE), 100.0f, 1, 1)});

        MobInfoRegistry.register(MobBossValkyrie.class, "guidebook.section.mob.valkyrie.queen.name", "guidebook.section.mob.valkyrie.queen.desc", 750, 50000, new MobInfoRegistry.MobDrop[]{
                new MobInfoRegistry.MobDrop(new ItemStack(AetherItems.KEY_SILVER), 100.0f, 1, 1)});

        MobInfoRegistry.register(MobBossSunspirit.class, "guidebook.section.mob.sunspirit.name", "guidebook.section.mob.sunspirit.desc", 1000, 100000, new MobInfoRegistry.MobDrop[]{
                new MobInfoRegistry.MobDrop(new ItemStack(AetherItems.KEY_GOLD), 100.0f, 1, 1)});


    }
}
