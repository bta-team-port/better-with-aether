package bta.aether.block;

import net.minecraft.core.block.BlockTrapDoor;
import net.minecraft.core.block.material.Material;

public class BlockTrapdoorAmbrosium extends BlockTrapDoor {
    public BlockTrapdoorAmbrosium(String key, int id, Material material, boolean isIron) {
        super(key, id, material, isIron);
    }

    public int getRenderBlockPass() {
        return 1;
    }
    public boolean isOpaqueCube() {
        return false;
    }

}
