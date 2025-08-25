package teamport.aether.items.itemtool.ItemToolZanite;

import net.minecraft.core.block.Block;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import teamport.aether.blocks.AetherBlockTags;
import teamport.aether.items.itemtool.ItemToolPickaxeAether;

public class ItemToolPickaxeZanite extends ItemToolPickaxeAether {

    public ItemToolPickaxeZanite(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    @Override
    public float getStrVsBlock(ItemStack itemstack, Block<?> block) {
        if (itemstack == null) return 0f;

        if (!block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE)) return 1.0F;
        float durability_progress = ((float) itemstack.getMetadata() / this.getMaxDamage());

        // we will 'lerp' between the starting efficiency and the unused 'haste' efficiency of tools
        float base_efficiency = this.material.getEfficiency(false);
        float haste_efficiency = this.material.getEfficiency(true);

        return base_efficiency + (haste_efficiency - base_efficiency) * durability_progress;
    }
}
