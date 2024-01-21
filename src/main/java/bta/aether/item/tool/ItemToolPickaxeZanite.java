package bta.aether.item.tool;

import bta.aether.AetherBlockTags;
import bta.aether.item.tool.base.ItemToolAetherPickaxe;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;

public class ItemToolPickaxeZanite extends ItemToolAetherPickaxe {

    public ItemToolPickaxeZanite(String name, int id, ToolMaterial enumtoolmaterial) {
        super(name, id, enumtoolmaterial);
    }

    @Override
    public float getStrVsBlock(ItemStack itemstack, Block block) {

        if (!block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE))
            return 1.0F;

        float durability_progress = (float) itemstack.getMetadata() / this.getMaxDamage();

        // we will 'lerp' between the starting efficiency and the unused 'haste' efficiency of tools
        float starting_efficiency = this.material.getEfficiency(false);
        float ending_efficiency = this.material.getEfficiency(true);

        return (float) (starting_efficiency * (1.0 - durability_progress) + (ending_efficiency * durability_progress));
    }
}
