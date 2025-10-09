package achievements;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.achievements.ScreenAchievements;
import net.minecraft.client.gui.achievements.data.AchievementPage;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.achievement.Achievement;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import org.jetbrains.annotations.NotNull;
import teamport.aether.AetherMod;
import teamport.aether.helper.unboxed.IntPair;

import java.util.List;
import java.util.Objects;
import java.util.Random;

@Environment(EnvType.CLIENT)
public class AchievementPageAether extends AchievementPage implements AetherAchievementPageExtras {
    public final String name;
    public final ItemStack icon;

    private static final IconCoordinate WATER_FLOWING;

    private static final @NotNull IconCoordinate AETHER_GRASS;
    private static final @NotNull IconCoordinate COBBLED_HOLYSTONE;
    private static final @NotNull IconCoordinate COBBLED_HOLYSTONE_MOSSY;
    private static final @NotNull IconCoordinate AETHER_DIRT ;
    private static final @NotNull IconCoordinate QUICKSOIL;
    private static final @NotNull IconCoordinate LOG_SKYROOT;
    private static final @NotNull IconCoordinate LEAVES_SKYROOT;
    private static final @NotNull IconCoordinate BLUE_CLOUD;
    private static final @NotNull IconCoordinate YELLOW_CLOUD;
    private static final @NotNull IconCoordinate CLOUD;

    private static final @NotNull IconCoordinate AETHER_TALL_GRASS;
    private static final @NotNull IconCoordinate WHITE_FLOWER;
    private static final @NotNull IconCoordinate PURPLE_FLOWER;
    private static final @NotNull IconCoordinate LEAVES_GOLDEN;
    private static final @NotNull IconCoordinate LOG_GOLDEN;

    private static final IconCoordinate[] TERRAIN_MAP;

    private static final AetherAchievementPageBackground BACKGROUND = new AetherAchievementPageBackground();

    private static final IconCoordinate SENTRY_STONE;

    private static final IconCoordinate SENTRY_STONE_LIGHT;

    static {
        IconCoordinate water = TextureRegistry.getTexture("aether:block/jank/water_flow");
        WATER_FLOWING = new IconCoordinate(water.parentAtlas, water.namespaceId, water.getImageSource());
        WATER_FLOWING.setDimension(water.width/2, water.height/2);
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

        TERRAIN_MAP = new IconCoordinate[18];
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
    }

    public AchievementPageAether(String name, ItemStack icon) {
        this.name = name;
        this.icon = icon;
    }

    @Override
    public void postProcessBackground(ScreenAchievements screen, Random random, ScreenAchievements.BGLayer bGLayer, int i, int j) {}

    @Override
    public IconCoordinate getBackgroundTile(ScreenAchievements screen, int layer, Random random, int tileX, int tileY) {
        tileX += 200;
        tileY += 20;

        if (layer == 0) {
            List<IntPair> water = BACKGROUND.WaterSources;
            for (IntPair w: water) {
                if (w.first == tileX && w.second <= tileY) return WATER_FLOWING;
            }
        }

        List<List<Integer>> struct_layer = null;
        if (layer == 1) struct_layer = BACKGROUND.TerrainLayer1;
        if (layer == 2) struct_layer = BACKGROUND.TerrainLayer2;
        if (layer == 3) struct_layer = BACKGROUND.TerrainLayer3;
        if (layer == 4) struct_layer = BACKGROUND.TerrainLayer4;

        if (struct_layer == null) return null;

        List<Integer> col = struct_layer.get(Math.abs(tileY) % (struct_layer.size() -1));
        int row = col.get(Math.abs(tileX) % (col.size() -1));

        return TERRAIN_MAP[row];
    }


    @Override
    public @NotNull ItemStack getIcon() {
        return this.icon;
    }

    @Override
    public int backgroundLayers() {
        return 5;
    }

    @Override
    public int backgroundColor() {
        return 0xc0c0ff;
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
    public float getShadowScale(int layer) {
        if (layer == 3) return 1.7F;
        if (layer == 2) return 1.30F;
        return 1;
    }
}
