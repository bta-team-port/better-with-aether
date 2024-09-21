package bta.aether;

import bta.aether.block.AetherBlocks;
import bta.aether.item.AetherItems;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.stitcher.IconCoordinate;
import net.minecraft.core.achievement.Achievement;
import net.minecraft.core.achievement.stat.Stat;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import net.minecraft.core.util.helper.Side;
import org.lwjgl.opengl.GL11;
import turniplabs.halplibe.util.achievements.AchievementPage;
import turniplabs.halplibe.util.achievements.GuiAchievements;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Random;

public class AetherAchievements extends AchievementPage {

    public AetherAchievements() {
        super("AetherMod", "achievements.page.aether");
        Field[] achievements = AetherAchievements.class.getDeclaredFields();
        Arrays.stream(achievements).filter((F)->F.getType().equals(Achievement.class)).forEach((F)->{
            try {
                achievementList.add((Achievement) ((Stat) F.get(null)).registerStat());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        ((Stat) HOSTILE_PARADISE).registerStat();
        achievementList.add(HOSTILE_PARADISE);

        ((Stat) BOUNCE).registerStat();
        achievementList.add(BOUNCE);

        ((Stat) MOA).registerStat();
        achievementList.add(MOA);
        ((Stat) PHYG).registerStat();
        achievementList.add(PHYG);

        ((Stat) ENCHANTER).registerStat();
        achievementList.add(ENCHANTER);

        ((Stat) BRONZE).registerStat();
        achievementList.add(BRONZE);
        ((Stat) SILVER).registerStat();
        achievementList.add(SILVER);
        ((Stat) GOLD).registerStat();
        achievementList.add(GOLD);

        ((Stat) GRAVITITE).registerStat();
        achievementList.add(GRAVITITE);

        ((Stat) LORE).registerStat();
        achievementList.add(LORE);
        ((Stat) LORECEPTION).registerStat();
        achievementList.add(LORECEPTION);
    }

    public static final int aetherAchievementsID = 524300;
    public static final Achievement HOSTILE_PARADISE = new Achievement(aetherAchievementsID + 1, "aether.hostile.paradise", 0, 0, Block.glowstone, null);
    public static final Achievement BOUNCE = new Achievement(aetherAchievementsID + 1, "aether.bounce", -2, -1, AetherBlocks.aercloudBlue, HOSTILE_PARADISE);
    public static final Achievement MOA = new Achievement(aetherAchievementsID + 1, "aether.moa", 2, -1, AetherBlocks.incubator, HOSTILE_PARADISE);
    public static final Achievement PHYG = new Achievement(aetherAchievementsID + 1, "aether.phyg", -2, 1, Item.saddle, HOSTILE_PARADISE);
    public static final Achievement ENCHANTER = new Achievement(aetherAchievementsID + 1, "aether.enchanter", 2, 1, AetherBlocks.enchanter, HOSTILE_PARADISE);
    public static final Achievement BRONZE = new Achievement(aetherAchievementsID + 1, "aether.bronze", -2, 3, AetherItems.keyBronze, HOSTILE_PARADISE);
    public static final Achievement SILVER = new Achievement(aetherAchievementsID + 1, "aether.silver", 0, 4, AetherItems.keySilver, HOSTILE_PARADISE);
    public static final Achievement GOLD = new Achievement(aetherAchievementsID + 1, "aether.gold", 2, 3, AetherItems.keyGold, HOSTILE_PARADISE);
    public static final Achievement GRAVITITE = new Achievement(aetherAchievementsID + 1, "aether.gravitite", -1, -3, AetherItems.toolPickaxeGravitite, HOSTILE_PARADISE);
    public static final Achievement LORE = new Achievement(aetherAchievementsID + 1, "aether.lore", 1, -3, AetherItems.bookLoreAether, HOSTILE_PARADISE);
    public static final Achievement LORECEPTION = new Achievement(aetherAchievementsID + 1, "aether.lore.inception", 1, -5, AetherItems.bookLoreAether, LORE);

    @Override
    public void getBackground(GuiAchievements guiAchievements, Random random, int iOffset, int jOffset, int blockX1, int blockY1, int blockX2, int blockY2) {
        int row = 0;
        while (row * 16 - blockY2 < 155) {
            float f5 = 0.6f - (float)(blockY1 + row) / 25.0f * 0.3f;
            GL11.glColor4f(f5, f5, f5, 1.0f);
            int column = 0;
            while (column * 16 - blockX2 < 224) {
                IconCoordinate texture = getTextureFromBlock(AetherBlocks.cobbleHolystone);
                guiAchievements.drawTexturedIcon(
                        iOffset + column * 16 - blockX2,
                        jOffset + row * 16 - blockY2,
                        texture.width,
                        texture.height,
                        texture
                );
                ++column;
            }
            ++row;
        }
    }

    public IconCoordinate getTextureFromBlock(Block block) {
        return BlockModelDispatcher.getInstance().getDispatch(block).getBlockTextureFromSideAndMetadata(Side.BOTTOM, 0);
    }

}