package bta.aether.item.tool.base;

import bta.aether.AetherBlockTags;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemTool;

public class ItemToolAetherAxe extends ItemTool {

    public ItemToolAetherAxe(String name, int id, ToolMaterial enumtoolmaterial) {
        super(name, id, 2, enumtoolmaterial, AetherBlockTags.MINEABLE_BY_AETHER_AXE);
    }

    @Override
    public boolean canHarvestBlock(Block block) {
        return block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
    }
}
