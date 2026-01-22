package teamport.aether.compat.waila;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.Items;
import net.minecraft.core.player.inventory.container.Container;
import org.slf4j.Logger;
import teamport.aether.block.AetherBlocks;
import teamport.aether.block.entity.TileEntityMimic;
import teamport.aether.entity.animal.aerwhale.MobAerwhale;
import teamport.aether.entity.animal.moa.MobMoaBlack;
import teamport.aether.entity.animal.moa.MobMoaBlue;
import teamport.aether.entity.animal.moa.MobMoaWhite;
import teamport.aether.entity.animal.phyg.MobPhyg;
import teamport.aether.entity.animal.sheepuff.MobSheepuff;
import teamport.aether.entity.animal.whirly.MobWhirly;
import teamport.aether.entity.boss.slider.MobBossSlider;
import teamport.aether.entity.boss.sunspirit.MobBossSunspirit;
import teamport.aether.entity.boss.valkyrie.queen.MobBossValkyrie;
import teamport.aether.entity.monster.fireminion.MobFireMinion;
import teamport.aether.entity.monster.mimic.MobMimic;
import teamport.aether.entity.monster.tempest.MobTempest;
import teamport.aether.entity.monster.zephyr.MobZephyr;
import teamport.aether.item.AetherItems;
import toufoumaster.btwaila.entryplugins.waila.BTWailaCustomTooltipPlugin;
import toufoumaster.btwaila.entryplugins.waila.BTWailaPlugin;
import toufoumaster.btwaila.tooltips.TileTooltip;
import toufoumaster.btwaila.tooltips.TooltipRegistry;

import static toufoumaster.btwaila.gui.components.WailaTextComponent.addEntityIcon;

public class AetherWailaPlugin implements BTWailaCustomTooltipPlugin {
    @Override
    public void initializePlugin(TooltipRegistry tooltipRegistry, Logger logger) {
        TileTooltip<Container> inventory = BTWailaPlugin.INVENTORY;
        inventory.addClass(TileEntityMimic.class);
        tooltipRegistry.register(new EnchanterTooltip());
        tooltipRegistry.register(new FreezerTooltip());
        tooltipRegistry.register(new IncubatorTooltip());

        addEntityIcon(MobPhyg.class, Items.FOOD_PORKCHOP_RAW);
        addEntityIcon(MobSheepuff.class, Blocks.WOOL);

        addEntityIcon(MobAerwhale.class, Items.BUCKET_ICECREAM);

        addEntityIcon(MobWhirly.class, AetherItems.AMMO_WINDBALL);
        addEntityIcon(MobTempest.class, AetherItems.PROJECTILE_LIGHTNING);
        addEntityIcon(MobFireMinion.class, Blocks.FIRE);

        addEntityIcon(MobMimic.class, AetherBlocks.CHEST_MIMIC_SKYROOT);

        addEntityIcon(MobZephyr.class, AetherItems.AMMO_WINDBALL);

        addEntityIcon(MobMoaBlue.class, AetherItems.EGG_MOA_BLUE);
        addEntityIcon(MobMoaWhite.class, AetherItems.EGG_MOA_WHITE);
        addEntityIcon(MobMoaBlack.class, AetherItems.EGG_MOA_BLACK);

        addEntityIcon(MobBossSlider.class, AetherItems.KEY_BRONZE);
        addEntityIcon(MobBossValkyrie.class, AetherItems.KEY_SILVER);
        addEntityIcon(MobBossSunspirit.class, AetherItems.KEY_GOLD);
    }
}
