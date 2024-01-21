package bta.aether.item.tool.base;

import bta.aether.AetherBlockTags;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemTool;

public class ItemToolAetherPickaxe extends ItemTool {

    public ItemToolAetherPickaxe(String name, int id, ToolMaterial enumtoolmaterial) {
        super(name, id, 2, enumtoolmaterial, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);
    }

    @Override
    public boolean canHarvestBlock(Block block) {
        // TODO: add mining levels, if they exist in this mod???
        return block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);
    }
}
