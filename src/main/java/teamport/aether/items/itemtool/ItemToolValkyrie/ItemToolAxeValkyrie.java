package teamport.aether.items.itemtool.ItemToolValkyrie;

import net.minecraft.core.block.Block;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.item.material.ToolMaterial;
import teamport.aether.blocks.AetherBlockTags;
import teamport.aether.items.itemtool.ItemToolAxeAether;

public class ItemToolAxeValkyrie extends ItemToolAxeAether {
    public ItemToolAxeValkyrie(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    public float getStrVsBlock(ItemStack itemstack, Block<?> block) {
        if (!block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_AXE)) return Items.TOOL_AXE_DIAMOND.getStrVsBlock(itemstack, block);
        return this.material.getEfficiency(false);
    }
}
