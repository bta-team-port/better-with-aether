package teamport.aether.achievements;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.achievements.ScreenAchievements;
import net.minecraft.client.gui.achievements.data.AchievementPage;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.achievement.Achievement;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.util.helper.Color;
import org.jspecify.annotations.NonNull;
import teamport.aether.helper.unboxed.IntPair;

import java.util.List;
import java.util.Objects;
import java.util.Random;

@Environment(EnvType.CLIENT)
public class AchievementPageAether extends AchievementPage implements AetherAchievementPageExtras {
    public final String name;
    public final ItemStack icon;

    private static final IconCoordinate WATER_FLOWING;

    private static final @NonNull IconCoordinate AETHER_GRASS;
    private static final @NonNull IconCoordinate COBBLED_HOLYSTONE;
    private static final @NonNull IconCoordinate COBBLED_HOLYSTONE_MOSSY;
    private static final @NonNull IconCoordinate AETHER_DIRT;
    private static final @NonNull IconCoordinate QUICKSOIL;
    private static final @NonNull IconCoordinate LOG_SKYROOT;
    private static final @NonNull IconCoordinate LEAVES_SKYROOT;
    private static final @NonNull IconCoordinate BLUE_CLOUD;
    private static final @NonNull IconCoordinate YELLOW_CLOUD;
    private static final @NonNull IconCoordinate CLOUD;

    private static final @NonNull IconCoordinate AETHER_TALL_GRASS;
    private static final @NonNull IconCoordinate WHITE_FLOWER;
    private static final @NonNull IconCoordinate PURPLE_FLOWER;
    private static final @NonNull IconCoordinate LEAVES_GOLDEN;
    private static final @NonNull IconCoordinate LOG_GOLDEN;

    private static final @NonNull IconCoordinate AMBROSIUM;
    private static final @NonNull IconCoordinate GRAVITITE;
    private static final @NonNull IconCoordinate ICE_STONE;

    private static final @NonNull IconCoordinate SLIDER_TOP_LEFT;
    private static final @NonNull IconCoordinate SLIDER_BOTTOM_LEFT;
    private static final @NonNull IconCoordinate SLIDER_TOP_RIGHT;
    private static final @NonNull IconCoordinate SLIDER_BOTTOM_RIGHT;

    private static final IconCoordinate[] TERRAIN_MAP;

    private static final AetherAchievementPageBackground BACKGROUND = new AetherAchievementPageBackground();

    private static final IconCoordinate SENTRY_STONE;

    private static final IconCoordinate SENTRY_STONE_LIGHT;

    static {
        IconCoordinate water = TextureRegistry.getTexture("aether:block/jank/water_flow");
        WATER_FLOWING = new IconCoordinate(water.parentAtlas, water.namespaceId, water.getImageSource());
        WATER_FLOWING.setDimension(water.width / 2, water.height / 2);
        WATER_FLOWING.setPosition(water.iconX, water.iconY);

        AETHER_GRASS = TextureRegistry.getTexture("aether:block/grass_aether/side");
        AETHER_DIRT = TextureRegistry.getTexture("aether:block/dirt_aether");
        COBBLED_HOLYSTONE = TextureRegistry.getTexture("aether:block/cobbled_holystone");
        COBBLED_HOLYSTONE_MOSSY = TextureRegistry.getTexture("aether:block/cobbled_holystone_mossy");
        QUICKSOIL = TextureRegistry.getTexture("aether:block/quicksoil");
        LOG_SKYROOT = TextureRegistry.getTexture("aether:block/log/skyroot_side");
        LEAVES_SKYROOT = TextureRegistry.getTexture("aether:block/leaves/skyroot");

        LEAVES_GOLDEN = TextureRegistry.getTexture("aether:block/leaves/oak_golden");
        LOG_GOLDEN = TextureRegistry.getTexture("aether:block/log/oak_golden_side");

        BLUE_CLOUD = TextureRegistry.getTexture("aether:block/aercloud_blue");
        YELLOW_CLOUD = TextureRegistry.getTexture("aether:block/aercloud_gold");
        CLOUD = TextureRegistry.getTexture("aether:block/aercloud_white");

        AETHER_TALL_GRASS = TextureRegistry.getTexture("aether:block/tallgrass_aether");
        WHITE_FLOWER = TextureRegistry.getTexture("aether:block/flower_white/0");
        PURPLE_FLOWER = TextureRegistry.getTexture("aether:block/flower_purple/0");

        SENTRY_STONE = TextureRegistry.getTexture("aether:block/dungeon/carved");
        SENTRY_STONE_LIGHT = TextureRegistry.getTexture("aether:block/dungeon/carved_glow");

        AMBROSIUM = TextureRegistry.getTexture("aether:block/ore/ambrosium/holystone");
        GRAVITITE = TextureRegistry.getTexture("aether:block/ore/gravitite/holystone");
        ICE_STONE = TextureRegistry.getTexture("aether:block/icestone");

        IconCoordinate sliderSheet = TextureRegistry.getTexture("aether:block/jank/slider");
        SLIDER_TOP_LEFT     = new IconCoordinate(sliderSheet.parentAtlas, sliderSheet.namespaceId, sliderSheet.getImageSource());
        SLIDER_BOTTOM_LEFT  = new IconCoordinate(sliderSheet.parentAtlas, sliderSheet.namespaceId, sliderSheet.getImageSource());
        SLIDER_TOP_RIGHT    = new IconCoordinate(sliderSheet.parentAtlas, sliderSheet.namespaceId, sliderSheet.getImageSource());
        SLIDER_BOTTOM_RIGHT = new IconCoordinate(sliderSheet.parentAtlas, sliderSheet.namespaceId, sliderSheet.getImageSource());

        SLIDER_TOP_LEFT.setDimension(16, 16);
        SLIDER_BOTTOM_LEFT.setDimension(16, 16);
        SLIDER_TOP_RIGHT.setDimension(16, 16);
        SLIDER_BOTTOM_RIGHT.setDimension(16, 16);

        SLIDER_TOP_LEFT.setPosition(sliderSheet.iconX,             sliderSheet.iconY);
        SLIDER_BOTTOM_LEFT.setPosition(sliderSheet.iconX,          sliderSheet.iconY + 16);
        SLIDER_TOP_RIGHT.setPosition(sliderSheet.iconX    + 16, sliderSheet.iconY);
        SLIDER_BOTTOM_RIGHT.setPosition(sliderSheet.iconX + 16, sliderSheet.iconY + 16);

        TERRAIN_MAP = new IconCoordinate[21];
        TERRAIN_MAP[0] = null;
        TERRAIN_MAP[8] = AETHER_TALL_GRASS;
        TERRAIN_MAP[3] = AETHER_GRASS;
        TERRAIN_MAP[2] = AETHER_DIRT;
        TERRAIN_MAP[1] = COBBLED_HOLYSTONE;
        TERRAIN_MAP[4] = COBBLED_HOLYSTONE_MOSSY;
        TERRAIN_MAP[5] = QUICKSOIL;
        TERRAIN_MAP[6] = LOG_SKYROOT;
        TERRAIN_MAP[7] = LEAVES_SKYROOT;
        TERRAIN_MAP[9] = WHITE_FLOWER;
        TERRAIN_MAP[10] = PURPLE_FLOWER;
        TERRAIN_MAP[11] = LEAVES_GOLDEN;
        TERRAIN_MAP[12] = LOG_GOLDEN;

        TERRAIN_MAP[13] = BLUE_CLOUD;
        TERRAIN_MAP[14] = YELLOW_CLOUD;
        TERRAIN_MAP[15] = CLOUD;

        TERRAIN_MAP[16] = SENTRY_STONE;
        TERRAIN_MAP[17] = SENTRY_STONE_LIGHT;

        TERRAIN_MAP[18] = AMBROSIUM;
        TERRAIN_MAP[19] = GRAVITITE;
        TERRAIN_MAP[20] = ICE_STONE;
    }

    public AchievementPageAether(String name, ItemStack icon) {
        this.name = name;
        this.icon = icon;
    }

    @Override
    public void postProcessBackground(ScreenAchievements screen, Random random, ScreenAchievements.BGLayer bGLayer, int i, int j) {
    }

    @Override
    public IconCoordinate getBackgroundTile(ScreenAchievements screen, int layer, Random random, int tileX, int tileY) {
        tileX += 50;
        tileY += 15;

        int origY = tileY;

        if (tileX < 0) tileX += BACKGROUND.width;
        if (tileY < 0) tileY += BACKGROUND.height;

        tileX = Math.abs(tileX % BACKGROUND.width);
        tileY = Math.abs(tileY % BACKGROUND.height);

        List<List<Integer>> structLayer = null;

        if (layer == 0 && origY > 0) {
            List<IntPair> water = BACKGROUND.waterSources;
            for (IntPair w : water) {
                if (w.getFirst() == tileX && w.getSecond() <= origY)
                    return WATER_FLOWING;
            }
        }

        if (layer == 3) {
            structLayer = BACKGROUND.specials;
            List<Integer> row = structLayer.get(tileY);
            if (row.get(tileX) == 2) {
                List<Integer> upperRow = structLayer.get(tileY + 1);

                boolean upper = upperRow.get(tileX) == 2;
                boolean left = row.get(tileX - 1) == 2;

                if (!upper) {
                    if (left) return SLIDER_BOTTOM_RIGHT;
                    else return SLIDER_BOTTOM_LEFT;
                }

                if (left) return SLIDER_TOP_RIGHT;
                return SLIDER_TOP_LEFT;
            }
        }

        if (layer == 1) structLayer = BACKGROUND.terrainLayer1;
        if (layer == 2) structLayer = BACKGROUND.terrainLayer2;
        if (layer == 3) structLayer = BACKGROUND.terrainLayer3;
        if (layer == 4) structLayer = BACKGROUND.terrainLayer4;

        if (structLayer == null) return null;

        List<Integer> row = structLayer.get(tileY);
        int col = row.get(tileX);

        return TERRAIN_MAP[col];
    }


    @Override
    public @NonNull ItemStack getIcon() {
        return this.icon;
    }

    @Override
    public int backgroundLayers() {
        return 5;
    }

    @Override
    public int backgroundColor() {
        return 0xFFb0b0f7;
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

    @Override
    public @NonNull String getName() {
        return I18n.getInstance().translateNameKey(name);
    }

    @Override
    public @NonNull String getDescription() {
        return I18n.getInstance().translateNameKey(name);
    }

    @Override
    public @NonNull AchievementEntry onOpenAchievement() {
        return Objects.requireNonNull(this.getEntry(AetherAchievements.HOSTILE_PARADISE));
    }

    @Override
    public float getShadowScale(int layer) {
        if (layer == 3) return 1.7F;
        if (layer == 2) return 1.30F;
        return 1;
    }

    public static int mixColor(int colorA, int colorB, float ratio) {
        int alphaA = Color.alphaFromInt(colorA);
        int redA   = Color.redFromInt(colorA);
        int blueA  = Color.blueFromInt(colorA);
        int greenA = Color.greenFromInt(colorA);
        int alphaB = Color.alphaFromInt(colorB);
        int redB   = Color.redFromInt(colorB);
        int blueB  = Color.blueFromInt(colorB);
        int greenB = Color.greenFromInt(colorB);

        int alphaRes = (int) (alphaA * ratio + alphaB * (1 - ratio));
        int redRes = (int) (redA * ratio + redB * (1 - ratio));
        int blueRes = (int) (blueA * ratio + blueB * (1 - ratio));
        int greenRes = (int) (greenA * ratio + greenB * (1 - ratio));

        return Color.intToIntARGB(alphaRes, redRes, greenRes, blueRes);
    }

    @Override
    public void drawBeforeTiles(ScreenAchievements gui, double shiftX, double shiftY, int mouseX, int mouseY, int left, int top, int right, int bottom) {
        double shiftYAdjusted = (Math.floor(shiftY) + 288) / 576;

        int bottomTop = 0xFF7970ca;
        int bottomBottom = 0xFF514f69;
        int colorBottom = mixColor(bottomBottom, bottomTop, (float) shiftYAdjusted);

        gui.drawGradientRect(left, top, right, bottom, backgroundColor(), colorBottom);
    }
}
