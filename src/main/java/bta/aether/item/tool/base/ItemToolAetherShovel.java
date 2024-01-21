package bta.aether.item.tool.base;

import bta.aether.AetherBlockTags;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemTool;

public class ItemToolAetherShovel extends ItemTool {

    public ItemToolAetherShovel(String name, int id, ToolMaterial enumtoolmaterial) {
        super(name, id, 1, enumtoolmaterial, AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL);
    }

    @Override
    public boolean canHarvestBlock(Block block) {
        return block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL);
    }
}
