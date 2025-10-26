package teamport.aether.items.itemtool.ItemToolValkyrie;

import net.minecraft.core.block.Block;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.DamageType;
import teamport.aether.AetherMod;
import teamport.aether.blocks.AetherBlockTags;
import teamport.aether.items.AetherHasCustomDamageType;
import teamport.aether.items.itemtool.ItemToolShovelAether;

public class ItemToolShovelValkyrie extends ItemToolShovelAether implements AetherHasCustomDamageType {

    public ItemToolShovelValkyrie(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    @Override
    public float getStrVsBlock(ItemStack itemstack, Block<?> block) {
        if (!block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL))
            return Items.TOOL_SHOVEL_DIAMOND.getStrVsBlock(itemstack, block);
        return this.material.getEfficiency(false);
    }

    @Override
    public DamageType getDamageType() {
        return AetherMod.HOLY;
    }
}
