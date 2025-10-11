
package teamport.aether.achievements;

import teamport.aether.AetherMod;
import teamport.aether.helper.unboxed.IntPair;

import java.io.*;
import java.util.*;

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
            specialsTemp      = loadCSV(getClass().getResourceAsStream(resource + "/Specials.csv"));

            heightTemp = terrainLayer1Temp.size()-1;
            widthTemp = terrainLayer1Temp.get(0).size()-1;
        }

        catch (NullPointerException e) {
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
        this.Specials = specialsTemp;
        this.TerrainLayer4 = terrainLayer4Temp;
        this.TerrainLayer3 = terrainLayer3Temp;
        this.TerrainLayer2 = terrainLayer2Temp;
        this.TerrainLayer1 = terrainLayer1Temp;
        this.WaterSources = new ArrayList<>();

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