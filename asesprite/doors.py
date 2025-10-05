# /// script
# requires-python = ">=3.13"
# dependencies = [
#     "pillow",
# ]
# ///


from PIL import Image
from pathlib import Path

ROOT = Path(__file__).parent

door_img = Image.open(str(ROOT/"door.png"))

COLORS = [
    "NONE",
    "white",
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
    "pink",
]

get_path = lambda color: Path(f"door/skyroot_{color}/")

for i, color in enumerate(COLORS):
    path = get_path(color)
    path.mkdir(parents=True, exist_ok=True)

    width, height = 16, 16
    TRAP_DOOR_TOP = door_img.crop([i * width, 2 * height, (i+1) * width, 3 * height])
    DOOR_TOP = door_img.crop([i * width, 3 * height, (i+1) * width, 4 * height])
    DOOR_BOTTOM = door_img.crop([i * width, 4 * height, (i+1) * width, 5 * height])

    TRAP_DOOR_TOP.save(path/"frame_top.png", format="png")
    DOOR_TOP.save(path/"top.png", format="png")
    DOOR_BOTTOM.save(path/"bottom.png", format="png")

    trapdoor_path = Path(f"trapdoor/skyroot/{color}/")
    trapdoor_path.mkdir(parents=True, exist_ok=True)
    TRAP_DOOR_TOP.save(trapdoor_path/"top.png", format="png")
    TRAP_DOOR_TOP.save(trapdoor_path/"side.png", format="png")

    ITEM_DOOR = door_img.crop([i * width, 1 * height, (i+1) * width, 2 * height])
    path_item = ROOT/"door_skyroot"
    path_item.mkdir(parents=True, exist_ok=True)
    ITEM_DOOR.save(path_item/f"{color}.png")
