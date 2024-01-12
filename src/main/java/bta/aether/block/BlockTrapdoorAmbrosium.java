package bta.aether.block;

import net.minecraft.core.block.BlockTrapDoor;
import net.minecraft.core.block.material.Material;

public class BlockTrapdoorAmbrosium extends BlockTrapDoor {
    public BlockTrapdoorAmbrosium(String key, int id, Material material, boolean isIron) {
        super(key, id, material, isIron);
        this.movementScale = 1.05f;
    }

    @Override
    public int getRenderBlockPass() {
        return 1;
    }
    @Override
    public boolean isSolidRender() {
        return false;
    }

}
