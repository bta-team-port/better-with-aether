package teamport.aether.items.itemtool.ItemToolZanite;

import net.minecraft.client.Minecraft;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import teamport.aether.blocks.AetherBlockTags;
import teamport.aether.items.itemtool.ItemToolAxeAether;

public class ItemToolAxeZanite extends ItemToolAxeAether {
    public ItemToolAxeZanite(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    @Override
    public float getStrVsBlock(ItemStack itemstack, Block<?> block) {
        if (!block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_AXE)) return 1.0F;
        float durability_progress = ((float) itemstack.getMetadata() / this.getMaxDamage());

        // we will 'lerp' between the starting efficiency and the unused 'haste' efficiency of tools
        float base_efficiency = this.material.getEfficiency(false);
        float haste_efficiency = this.material.getEfficiency(true);

        return  base_efficiency + (haste_efficiency - base_efficiency) * durability_progress;
    }
}

