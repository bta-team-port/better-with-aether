
package teamport.aether.achievements;

import teamport.aether.helper.unboxed.IntPair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AetherAchievementPageBackground {

    public final List<IntPair> WaterSources;
    public final List<List<Integer>> Specials;
    public final List<List<Integer>> TerrainLayer1;
    public final List<List<Integer>> TerrainLayer2;
    public final List<List<Integer>> TerrainLayer3;
    public final List<List<Integer>> TerrainLayer4;

    public final int width;
    public final int height;

    public AetherAchievementPageBackground() {
        String resource = "assets/aether/misc/achievement_page_background";

        this.TerrainLayer1 = loadCSV(ClassLoader.getSystemResourceAsStream(resource + "/Terrain1.csv"));
        this.TerrainLayer2 = loadCSV(ClassLoader.getSystemResourceAsStream(resource + "/Terrain2.csv"));
        this.TerrainLayer3 = loadCSV(ClassLoader.getSystemResourceAsStream(resource + "/Terrain3.csv"));
        this.TerrainLayer4 = loadCSV(ClassLoader.getSystemResourceAsStream(resource + "/Terrain4.csv"));

        this.height = TerrainLayer1.size()-1;
        this.width = TerrainLayer1.get(0).size()-1;

        this.WaterSources = new ArrayList<>();
        Specials = loadCSV(ClassLoader.getSystemResourceAsStream(resource + "/Specials.csv"));

        for (int y = 0; y < Specials.size(); y++) {
            List<Integer> row = Specials.get(y);
            for (int x = 0; x < row.size(); x++) {
                if (row.get(x) == 1) this.WaterSources.add(new IntPair(x, y));
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