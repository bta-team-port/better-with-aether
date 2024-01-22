package bta.aether.item.tool.base;

import bta.aether.AetherBlockTags;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemTool;

import java.util.HashMap;
import java.util.Map;

public class ItemToolAetherPickaxe extends ItemTool {
    public static Map<Block, Integer> miningLevels = new HashMap<>();

    public ItemToolAetherPickaxe(String name, int id, ToolMaterial enumtoolmaterial) {
        super(name, id, 2, enumtoolmaterial, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);
    }

    @Override
    public boolean canHarvestBlock(Block block) {
        Integer miningLevel = miningLevels.get(block);
        if (miningLevel != null) {
            return this.material.getMiningLevel() >= miningLevel;
        }
        return block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);
    }
}
