package teamport.aether.item.item_tool.item_tool_zanite;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.MathHelper;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlockTags;
import teamport.aether.item.item_tool.ItemToolSwordAether;
import teamport.aether.mixins.mixin.accessors.ItemToolSwordAccessor;

import static teamport.aether.AetherMod.ZANITE_MULTIPLIER;

public class ItemToolSwordZanite extends ItemToolSwordAether {
    public ItemToolSwordZanite(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    @Override
    public int getDamageVsEntity(@NonNull ItemStack is, @NonNull Entity entity) {
        // we will 'lerp' between the starting damage and starting damage time ZANITE_MULTIPLIER
        float durabilityProgress = (float) is.getMetadata() / this.getMaxDamage();
        float startingDamage = (float) super.getDamageVsEntity(is, entity);
        return Math.round(MathHelper.lerp(startingDamage, startingDamage * ZANITE_MULTIPLIER, durabilityProgress));
    }

    @Override
    public float getStrVsBlock(@NonNull ItemStack itemstack, @NonNull Block<?> block) {
        if (!block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_SWORD)) return 1.0F;
        float durabilityProgress = ((float) itemstack.getMetadata() / this.getMaxDamage());
        // we will 'lerp' between the starting damage and starting damage time ZANITE_MULTIPLIER
        float baseEfficiency = ((ItemToolSwordAccessor) this).getMaterial().getEfficiency(false);
        return MathHelper.lerp(baseEfficiency, baseEfficiency * ZANITE_MULTIPLIER, durabilityProgress);
    }

}
