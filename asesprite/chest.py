# /// script
# requires-python = ">=3.13"
# dependencies = [
#     "numpy",
#     "pillow",
# ]
# ///


from PIL import Image
from pathlib import Path
import numpy as np

ROOT = Path(__file__).parent

TEMPLATE = Image.open(str(ROOT/"chest_template.png"))

COLORS = [
    "white", # 0
    "silver",
    "gray",
    "black",
    "brown",
    "red",
    "orange",
    "yellow",
    "lime",
    "green",
    "cyan",
    "lightblue",
    "blue",
    "purple",
    "magenta",
    "pink", # 15
]

SIDES = [
    "front",
    "left_back",
    "left_front",
    "right_back",
    "right_front",
    "side",
    "top",
    "top_left",
    "top_right",
]

PALLETTES = list()

def chest_out(color, side):
    return Path(f"chest/skyroot/{color}/{side}.png")

def coord(x, y, w, h):
    return [x, y, x+w, y+h]

for i in range(len(COLORS)):
    PALLETTE = np.array(TEMPLATE.crop(coord(16, 32+i, 4, 1)))[0]
    PALLETTES.append(PALLETTE)
    
    print(PALLETTE, "\n")

def make_chest(color: int):
    base = Image.new(
        mode="RGBA",
        size=[16, 16]
    )
    
    base.paste(
        TEMPLATE.crop(coord(0, 32, 16, 16)),
        [0,0]
    )

    data = np.array(base)
    for i in range(4):
        orig_color = PALLETTES[0][i]
        replacement_color = PALLETTES[color][i]
        data[(data == orig_color).all(axis = -1)] = replacement_color

    base = Image.fromarray(data, mode='RGBA')

    for i, side in enumerate(SIDES):
        result = base.copy()
        
        sprite_shadow = TEMPLATE.crop(coord(i * 16, 16, 16, 16)).convert("L")
        array_shadow = np.array(sprite_shadow, dtype=np.float32) / 255.0

        array_result = np.array(result, dtype=np.float32) * array_shadow[..., None]
        
        result = Image.fromarray(
            np.clip(array_result, 0, 255).astype(np.uint8),
            mode="RGBA"
        )

        result.putalpha(255)

        sprite_side = TEMPLATE.crop(coord(i * 16, 0, 16, 16))
        result.paste(sprite_side, [0,0], mask=sprite_side)

        path = chest_out(COLORS[color], side)
        path.parent.mkdir(parents=True, exist_ok=True)
        result.save(str(path))
    
if __name__ == "__main__": 
    for i in range(len(COLORS)):
        make_chest(i)