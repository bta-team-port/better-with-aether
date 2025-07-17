package teamport.aether;

import net.minecraft.client.gui.achievements.ScreenAchievements;
import net.minecraft.client.gui.achievements.data.AchievementPage;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.achievement.Achievement;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import org.jetbrains.annotations.NotNull;
import teamport.aether.blocks.AetherBlocks;

import java.util.Objects;
import java.util.Random;

public class AchievementPageAether extends AchievementPage {
    public final String name;
    public final ItemStack icon;

    public AchievementPageAether(String name, ItemStack icon) {
        this.name = name;
        this.icon = icon;
    }

    @Override
    public @NotNull String getName() {
        return I18n.getInstance().translateNameKey(name);
    }

    @Override
    public @NotNull String getDescription() {
        return I18n.getInstance().translateNameKey(name);
    }

    @Override
    public @NotNull AchievementEntry onOpenAchievement() {
        return Objects.requireNonNull(this.getEntry(AetherAchievements.HOSTILE_PARADISE));
    }

    @Override
    public IconCoordinate getBackgroundTile(ScreenAchievements screen, int layer, Random random, int tileX, int tileY) {
        random.setSeed(random.nextLong() + (long)this.name.hashCode());
        int offsetY = tileY + random.nextInt(4);
        int r = random.nextInt(50);
        IconCoordinate texture = getTextureFromBlock(AetherBlocks.COBBLE_HOLYSTONE);
        if (offsetY >= 35) {
            return null;
        } else if (r == 10) {
            texture = getTextureFromBlock(AetherBlocks.ORE_GRAVITITE_HOLYSTONE);
        } else if (r <= 3) {
            texture = getTextureFromBlock(AetherBlocks.ORE_ZANITE_HOLYSTONE);
        } else if (r >= 45) {
            texture = getTextureFromBlock(AetherBlocks.ORE_AMBROSIUM_HOLYSTONE);
        }

        return texture;
    }

    @Override
    public void postProcessBackground(ScreenAchievements screen, Random random, ScreenAchievements.BGLayer bGLayer, int i, int j) {
    }

    @Override
    public @NotNull ItemStack getIcon() {
        return this.icon;
    }

    @Override
    public int backgroundLayers() {
        return 1;
    }

    @Override
    public int backgroundColor() {
        return 11316430;
    }

    @Override
    public IconCoordinate getAchievementIcon(Achievement achievement) {
        return TextureRegistry.getTexture(achievement.getType().texture);
    }

    @Override
    public int lineColorLocked(boolean bl) {
        return 0;
    }

    @Override
    public int lineColorUnlocked(boolean bl) {
        return 7368816;
    }

    @Override
    public int lineColorCanUnlock(boolean bl) {
        return 65280;
    }

}
