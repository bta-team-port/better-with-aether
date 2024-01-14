package bta.aether;

import bta.aether.block.AetherBlocks;
import bta.aether.item.AetherItems;
import net.minecraft.client.render.TextureFX;
import net.minecraft.core.Global;
import net.minecraft.core.achievement.Achievement;
import net.minecraft.core.achievement.AchievementList;
import net.minecraft.core.achievement.stat.Stat;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import net.minecraft.core.util.helper.Side;
import org.lwjgl.opengl.GL11;
import turniplabs.halplibe.util.achievements.AchievementPage;
import turniplabs.halplibe.util.achievements.GuiAchievements;

import java.util.Random;

public class AetherAchievements extends AchievementPage {
    public static final Achievement HOSTILE_PARADISE = new Achievement(AchievementList.achievementList.size() + 1, "aether.hostile.paradise", 0, 0, Block.glowstone, null);
    public static final Achievement BOUNCE = new Achievement(AchievementList.achievementList.size() + 1, "aether.bounce", -2, -1, AetherBlocks.aercloudBlue, HOSTILE_PARADISE);
    public static final Achievement MOA = new Achievement(AchievementList.achievementList.size() + 1, "aether.moa", 2, -1, AetherBlocks.incubator, HOSTILE_PARADISE);
    public static final Achievement PHYG = new Achievement(AchievementList.achievementList.size() + 1, "aether.phyg", -2, 1, Item.saddle, HOSTILE_PARADISE);
    public static final Achievement ENCHANTER = new Achievement(AchievementList.achievementList.size() + 1, "aether.enchanter", 2, 1, AetherBlocks.enchanter, HOSTILE_PARADISE);
    public static final Achievement BRONZE = new Achievement(AchievementList.achievementList.size() + 1, "aether.bronze", -2, 3, AetherItems.keyBronze, HOSTILE_PARADISE);
    public static final Achievement SILVER = new Achievement(AchievementList.achievementList.size() + 1, "aether.silver", 0, 4, AetherItems.keySilver, HOSTILE_PARADISE);
    public static final Achievement GOLD = new Achievement(AchievementList.achievementList.size() + 1, "aether.gold", 2, 3, AetherItems.keyGold, HOSTILE_PARADISE);
    public static final Achievement GRAVITITE = new Achievement(AchievementList.achievementList.size() + 1, "aether.gravitite", -1, -3, AetherItems.toolPickaxeGravitite, HOSTILE_PARADISE);
    public static final Achievement LORE = new Achievement(AchievementList.achievementList.size() + 1, "aether.lore", 1, -3, AetherItems.bookLore3, HOSTILE_PARADISE);
    public static final Achievement LORECEPTION = new Achievement(AchievementList.achievementList.size() + 1, "aether.lore.inception", 1, -5, AetherItems.bookLore3, LORE);

    public AetherAchievements() {
        super("Aether", "achievements.page.aether");
        ((Stat) HOSTILE_PARADISE).registerStat();
        achievementList.add(HOSTILE_PARADISE);
        achievementList.add(BOUNCE);
        achievementList.add(MOA);
        achievementList.add(PHYG);
        achievementList.add(ENCHANTER);
        achievementList.add(BRONZE);
        achievementList.add(SILVER);
        achievementList.add(GOLD);
        achievementList.add(GRAVITITE);
        achievementList.add(LORE);
        achievementList.add(LORECEPTION);
}

    @Override
    public void getBackground(GuiAchievements guiAchievements, Random random, int iOffset, int jOffset, int blockX1, int blockY1, int blockX2, int blockY2) {
        int l7 = 0;
        while (l7 * 16 - blockY2 < 155) {
            float f5 = 0.6f - (float)(blockY1 + l7) / 25.0f * 0.3f;
            GL11.glColor4f(f5, f5, f5, 1.0f);
            int i8 = 0;
            while (i8 * 16 - blockX2 < 224) {
                int k8 = AetherBlocks.holystone.getBlockTextureFromSideAndMetadata(Side.BOTTOM,0);
                guiAchievements.drawTexturedModalRect(iOffset + i8 * 16 - blockX2, jOffset + l7 * 16 - blockY2, k8 % Global.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthTerrain, k8 / Global.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthTerrain, 16, 16, TextureFX.tileWidthTerrain, 1.0f / (float)(Global.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthTerrain));
                ++i8;
            }
            ++l7;
        }
    }
}