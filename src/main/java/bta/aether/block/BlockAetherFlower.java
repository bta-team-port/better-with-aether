package bta.aether.block;

import net.minecraft.core.block.BlockFlower;

public class BlockAetherFlower extends BlockFlower {

    public BlockAetherFlower(String key, int id) {
        super(key, id);
    }

    @Override
    protected boolean canThisPlantGrowOnThisBlockID(int i) {
        return i == AetherBlocks.grassAether.id;
    }
}

