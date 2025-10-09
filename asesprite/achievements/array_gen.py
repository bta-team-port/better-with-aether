# /// script
# requires-python = ">=3.13"
# dependencies = [
#     "numpy",
#     "pillow",
# ]
# ///


from pathlib import Path
from PIL import Image
import numpy as np
from os import remove
from textwrap import dedent

ROOT = Path(__file__).parent

islands = ROOT/"islands"
clouds = ROOT/"clouds"
big_clouds = ROOT/"big_clouds"

out = ROOT/"AchievementScreenArrays.java"

if (out.exists()): remove(out)
out_handle = out.open("+a")

out_handle.write("""
package teamport.aether.achievements;

import teamport.aether.helper.unboxed.IntPair;

public class AetherAchievementPageBackground {
""")

level = ROOT/"teamport.aether.achievements/simplified/Huge"
level_name = level.name.upper()

def make_array(text:str):
    result = ""
    lines = text.split("\n")
    
    for line in lines:
        line = line.strip()
        if line == "": continue

        result += "{" + line + "},\n"

    return result
    
layer_1 = make_array((level/"Terrain1.csv").read_text())
layer_2 = make_array((level/"Terrain2.csv").read_text())
layer_3 = make_array((level/"Terrain3.csv").read_text())
layer_4 = make_array((level/"Terrain4.csv").read_text())

water_sources_txt = ""
w_path = level/"Specials.csv"

water_sources = np.array([
    [int(char) for char in line.split(',') if char != '']
    for line in w_path.read_text().splitlines()
])


for y, x in np.ndindex(water_sources.shape):
    block = water_sources[y, x]
    if block > 0:
        water_sources_txt += f"new IntPair({x}, {y}),\n"

layer_1 = layer_1.replace("\n", "\n        ")
layer_2 = layer_2.replace("\n", "\n        ")
layer_3 = layer_3.replace("\n", "\n        ")
layer_4 = layer_4.replace("\n", "\n        ")
water_sources = water_sources_txt.replace("\n", "\n        ")
out_handle.write(dedent(f"""
    public final static IntPair[] waterSourc = new IntPair[] {{
        {water_sources}
    }};

    public final static int[][] TerrainLayer1 = new int[][] {{
        {layer_1}
    }};

    public final static int[][] TerrainLayer2 = new int[][] {{
        {layer_2}
    }};

    public final static int[][] TerrainLayer3 = new int[][] {{
        {layer_3}
    }};

    public final static int[][] TerrainLayer4 = new int[][] {{
        {layer_4}
    }};
"""))

out_handle.write("}")
out_handle.close()