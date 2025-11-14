package teamport.aether.items.item_tool.item_tool_valkyrie;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.DamageType;
import teamport.aether.AetherMod;
import teamport.aether.blocks.AetherBlockTags;
import teamport.aether.items.AetherHasCustomDamageType;
import teamport.aether.items.item_tool.ItemToolShovelAether;

public class ItemToolShovelValkyrie extends ItemToolShovelAether implements AetherHasCustomDamageType {
    public ItemToolShovelValkyrie(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    @Override
    public float getStrVsBlock(ItemStack itemstack, Block<?> block) {
        return block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL) || block.hasTag(BlockTags.MINEABLE_BY_SHOVEL) ? this.material.getEfficiency(false) : 1.0F;
    }

    @Override
    public DamageType getDamageType() {
        return AetherMod.HOLY;
    }
}
