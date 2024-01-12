package bta.aether.block;

import bta.aether.AetherBlockTags;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockFlower;

public class BlockAetherFlower extends BlockFlower {

    public BlockAetherFlower(String key, int id) {
        super(key, id);
        this.setTicking(true);
    }

    @Override
    protected boolean canThisPlantGrowOnThisBlockID(int i) {
        return Block.blocksList[i] != null && Block.blocksList[i].hasTag(AetherBlockTags.GROWS_AETHER_FLOWERS);
    }
}

