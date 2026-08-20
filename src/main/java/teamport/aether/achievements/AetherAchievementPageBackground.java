package teamport.aether.achievements;

import it.unimi.dsi.fastutil.ints.IntIntMutablePair;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import org.jspecify.annotations.NonNull;
import teamport.aether.AetherMod;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class AetherAchievementPageBackground {

    public final List<IntIntPair> waterSources;
    public final List<List<Integer>> specials;
    public final List<List<Integer>> terrainLayer1;
    public final List<List<Integer>> terrainLayer2;
    public final List<List<Integer>> terrainLayer3;
    public final List<List<Integer>> terrainLayer4;

    public final int width;
    public final int height;

    public AetherAchievementPageBackground() {
        String resource = "/assets/aether/misc/achievement_page_background";

        int heightTemp;
        int widthTemp;
        List<List<Integer>> specialsTemp;
        List<List<Integer>> terrainLayer4Temp;
        List<List<Integer>> terrainLayer3Temp;
        List<List<Integer>> terrainLayer2Temp;
        List<List<Integer>> terrainLayer1Temp;

        try {
            terrainLayer1Temp = loadCSV(getClass().getResourceAsStream(resource + "/Terrain1.csv"));
            terrainLayer2Temp = loadCSV(getClass().getResourceAsStream(resource + "/Terrain2.csv"));
            terrainLayer3Temp = loadCSV(getClass().getResourceAsStream(resource + "/Terrain3.csv"));
            terrainLayer4Temp = loadCSV(getClass().getResourceAsStream(resource + "/Terrain4.csv"));
            specialsTemp = loadCSV(getClass().getResourceAsStream(resource + "/Specials.csv"));

            heightTemp = terrainLayer1Temp.size() - 1;
            widthTemp = terrainLayer1Temp.get(0).size() - 1;
        } catch (NullPointerException e) {
            AetherMod.LOGGER.error("Failed to load background files for the achievements screen!", e);

            terrainLayer1Temp = Collections.singletonList(
                Collections.singletonList(0)
            );
            terrainLayer2Temp = Collections.singletonList(
                Collections.singletonList(1)
            );
            terrainLayer3Temp = Collections.singletonList(
                Collections.singletonList(0)
            );
            terrainLayer4Temp = Collections.singletonList(
                Collections.singletonList(0)
            );
            specialsTemp = Collections.singletonList(
                Collections.singletonList(0)
            );

            widthTemp = 1;
            heightTemp = 1;
        }

        this.height = heightTemp;
        this.width = widthTemp;
        this.specials = specialsTemp;
        this.terrainLayer4 = terrainLayer4Temp;
        this.terrainLayer3 = terrainLayer3Temp;
        this.terrainLayer2 = terrainLayer2Temp;
        this.terrainLayer1 = terrainLayer1Temp;
        this.waterSources = new ArrayList<>();

        for (int y = 0; y < specials.size(); y++) {
            List<Integer> row = specials.get(y);
            for (int x = 0; x < row.size(); x++) {
                if (row.get(x) == 1) this.waterSources.add(new IntIntMutablePair(x, y));
            }
        }
    }

    private static @NonNull List<List<Integer>> loadCSV(InputStream in) {
        List<List<Integer>> output = new ArrayList<>();

        InputStreamReader reader = new InputStreamReader(in);
        BufferedReader buf = new BufferedReader(reader);
        buf.lines().forEach(l -> {
            String[] chars = l.split(",");

            List<Integer> toAddLine = new ArrayList<>();
            for (String c : chars) {
                if (Objects.equals(c, "")) continue;
                toAddLine.add(Integer.valueOf(c));
            }

            if (!toAddLine.isEmpty()) {
                output.add(toAddLine);
            }
        });

        return output;
    }

}
