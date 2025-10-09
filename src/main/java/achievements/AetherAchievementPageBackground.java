
package achievements;

import teamport.aether.helper.unboxed.IntPair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AetherAchievementPageBackground {

    public final List<IntPair> WaterSources;
    public final List<List<Integer>> TerrainLayer1;
    public final List<List<Integer>> TerrainLayer2;
    public final List<List<Integer>> TerrainLayer3;
    public final List<List<Integer>> TerrainLayer4;

    public AetherAchievementPageBackground() {
        String resource = "assets/aether/misc/achievement_page_background";

        this.TerrainLayer1 = loadCSV(ClassLoader.getSystemResourceAsStream(resource + "/terrain/1.csv"));
        this.TerrainLayer2 = loadCSV(ClassLoader.getSystemResourceAsStream(resource + "/terrain/2.csv"));
        this.TerrainLayer3 = loadCSV(ClassLoader.getSystemResourceAsStream(resource + "/terrain/3.csv"));
        this.TerrainLayer4 = loadCSV(ClassLoader.getSystemResourceAsStream(resource + "/terrain/4.csv"));

        this.WaterSources = new ArrayList<>();
        List<List<Integer>> specials = loadCSV(ClassLoader.getSystemResourceAsStream(resource + "/specials.csv"));

        for (int x = 0; x < specials.size(); x++) {
            List<Integer> col = specials.get(x);
            for (int y = 0; y < col.size(); y++) {
                if (col.get(y) == 1) this.WaterSources.add(new IntPair(x, y));
            }
        }
    }

    private static List<List<Integer>> loadCSV(InputStream in) {
        List<List<Integer>> output = new ArrayList<>();

        InputStreamReader reader = new InputStreamReader(in);
        BufferedReader buf = new BufferedReader(reader);
        buf.lines().forEach( l -> {
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