package bta.aether.block;

import net.minecraft.core.block.BlockChest;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.util.helper.Side;
import turniplabs.halplibe.helper.TextureHelper;

import java.util.ArrayList;
import java.util.List;

import static bta.aether.Aether.MOD_ID;

public class BlockChestSkyroot extends BlockChest {
    public static List<int[]> textures = new ArrayList<>();

    protected BlockChestSkyroot(String key, int id, Material material) {
        super(key, id, material);
    }

    private int indexFromSideAndMeta(Side side, int meta) {
        int[] chestColored = new int[0];
        int color = meta >> 4;
        int rotation = meta & 0b00000011;
        Type type = BlockChest.getTypeFromMeta(meta);
        if (textures.size() > color) {
        }
        int index = 0;
        int sideNum = 0;
        if (side == Side.TOP || side == Side.BOTTOM) {
            index = 0;
        } else {
            switch (side) {
                case NORTH:
                    sideNum = 0;
                    break;
                case EAST:
                    sideNum = 3;
                    break;
                case SOUTH:
                    sideNum = 2;
                    break;
                case WEST:
                    sideNum = 1;
                    break;
            }
            int prod = (sideNum + rotation) % 4;
            if (prod == 0) {
                if (type == Type.SINGLE) {
                    index = 2;
                } else if (type == Type.LEFT) {
                    index = 3;
                } else {
                    index = 4;
                }
            } else if (prod == 2) {
                if (type == Type.SINGLE) {
                    index = 1;
                } else if (type == Type.LEFT) {
                    index = 6;
                } else {
                    index = 5;
                }
            } else {
                index = 1;
            }
        }
        return chestColored[index];
    }


    static {
        int[] chestTextures = new int[7];
        chestTextures[0] = TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "assets/aether/block/SkyrootChestTop.png");
        chestTextures[1] = TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "assets/aether/block/SkyrootChestSide.png");
        chestTextures[2] = TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "assets/aether/block/SkyrootChestFront.png");
        chestTextures[3] = TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "assets/aether/block/SkyrootChestFrontLeft.png");
        chestTextures[4] = TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "assets/aether/block/SkyrootChestFrontRight.png");
        chestTextures[5] = TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "assets/aether/block/SkyrootChestSideLeft.png");
        chestTextures[6] = TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "assets/aether/block/SkyrootChestSideLeft.png");
        textures.add(chestTextures);
    }
}
