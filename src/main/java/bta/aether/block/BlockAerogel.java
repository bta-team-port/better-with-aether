package bta.aether.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockTransparent;
import net.minecraft.core.block.material.Material;

public class BlockAerogel extends BlockTransparent {
    public BlockAerogel(String key, int id, Material material) {
        super(key, id, material, true);
    }

    public int getRenderBlockPass() {
        return 1;
    }
    public boolean renderAsNormalBlock() {
        return false;
    }
    public boolean isOpaqueCube() {
        return false;
    }
}
