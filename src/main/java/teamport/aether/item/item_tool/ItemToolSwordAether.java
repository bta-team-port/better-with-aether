package teamport.aether.item.item_tool;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;
import teamport.aether.block.AetherBlockTags;

public class ItemToolSwordAether extends ItemToolSword {
    public ItemToolSwordAether(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
        this.maxStackSize = 1;
        this.setMaxDamage(enumtoolmaterial.getDurability());
    }

    @Override
    public boolean canHarvestBlock(ItemStack itemStack, Mob mob, Block<?> block) {
        return block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_SWORD) || block.hasTag(BlockTags.MINEABLE_BY_SWORD);
    }
}
