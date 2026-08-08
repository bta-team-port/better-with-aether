package teamport.aether.item.item_tool.item_tool_valkyrie;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.DamageType;
import org.jspecify.annotations.NonNull;
import teamport.aether.AetherMod;
import teamport.aether.block.AetherBlockTags;
import teamport.aether.item.AetherHasCustomDamageType;
import teamport.aether.item.item_tool.ItemToolAxeAether;

public class ItemToolAxeValkyrie extends ItemToolAxeAether implements AetherHasCustomDamageType {
    public ItemToolAxeValkyrie(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    @Override
    public float getStrVsBlock(@NonNull ItemStack itemstack, @NonNull Block<?> block) {
        return block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_AXE) || block.hasTag(BlockTags.MINEABLE_BY_AXE) ? this.material.getEfficiency(false) : 1.0F;
    }

    @Override
    public DamageType getDamageType() {
        return AetherMod.HOLY;
    }
}
