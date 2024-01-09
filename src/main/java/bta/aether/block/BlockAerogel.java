package bta.aether.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockTransparent;
import net.minecraft.core.block.material.Material;

public class BlockAerogel extends BlockTransparent {
    public BlockAerogel(String key, int id, Material material) {
        super(key, id, material, true);
    }

    @Override
    public int getRenderBlockPass() {
        return 1;
    }
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    @Override
    public boolean isSolidRender() {
        return false;
    }
}
