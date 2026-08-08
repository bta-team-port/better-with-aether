package teamport.aether.item.item_tool.item_tool_zanite;

import net.minecraft.core.block.Block;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.MathHelper;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlockTags;
import teamport.aether.item.item_tool.ItemToolShovelAether;

import static teamport.aether.AetherMod.ZANITE_MULTIPLIER;

public class ItemToolShovelZanite extends ItemToolShovelAether {

    public ItemToolShovelZanite(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    @Override
    public float getStrVsBlock(@NonNull ItemStack itemstack, @NonNull Block<?> block) {
        if (!block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL)) return 1.0F;
        float durabilityProgress = ((float) itemstack.getMetadata() / this.getMaxDamage());
        // we will 'lerp' between the starting damage and starting damage time ZANITE_MULTIPLIER
        float baseEfficiency = this.material.getEfficiency(false);
        return MathHelper.lerp(baseEfficiency, baseEfficiency * ZANITE_MULTIPLIER, durabilityProgress);
    }
}
