package teamport.aether.items.itemtool.ItemToolZanite;

import net.minecraft.core.block.Block;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.MathHelper;
import teamport.aether.blocks.AetherBlockTags;
import teamport.aether.items.itemtool.ItemToolPickaxeAether;

import static teamport.aether.AetherMod.ZANITE_MULTIPLIER;

public class ItemToolPickaxeZanite extends ItemToolPickaxeAether {

    public ItemToolPickaxeZanite(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    @Override
    public float getStrVsBlock(ItemStack itemstack, Block<?> block) {
        if (itemstack == null) return 0f;

        if (!block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE)) return 1.0F;
        float durability_progress = ((float) itemstack.getMetadata() / this.getMaxDamage());
        // we will 'lerp' between the starting damage and starting damage time ZANITE_MULTIPLIER
        float base_efficiency = this.material.getEfficiency(false);
        return MathHelper.lerp(base_efficiency, base_efficiency * ZANITE_MULTIPLIER, durability_progress);
    }
}
