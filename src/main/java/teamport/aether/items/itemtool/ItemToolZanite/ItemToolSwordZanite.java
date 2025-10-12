package teamport.aether.items.itemtool.ItemToolZanite;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.MathHelper;
import teamport.aether.blocks.AetherBlockTags;
import teamport.aether.items.itemtool.ItemToolSwordAether;
import teamport.aether.mixin.accessors.ItemToolSwordAccessor;

import static teamport.aether.AetherMod.ZANITE_MULTIPLIER;

public class ItemToolSwordZanite extends ItemToolSwordAether {
    public ItemToolSwordZanite(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);

    }

    @Override
    public int getDamageVsEntity(Entity entity, ItemStack is) {
        // we will 'lerp' between the starting damage and starting damage time ZANITE_MULTIPLIER
        float durability_progress = (float) is.getMetadata() / this.getMaxDamage();
        float starting_damage = (float) super.getDamageVsEntity(entity, is);
        return Math.round(MathHelper.lerp(starting_damage, starting_damage * ZANITE_MULTIPLIER, durability_progress));
    }

    @Override
    public float getStrVsBlock(ItemStack itemstack, Block<?> block) {
        if (itemstack == null) return 1.0f;
        if (!block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_SWORD)) return 1.0F;
        float durability_progress = ((float) itemstack.getMetadata() / this.getMaxDamage());

        // we will 'lerp' between the starting damage and starting damage time ZANITE_MULTIPLIER
        float base_efficiency = ((ItemToolSwordAccessor) this).getMaterial().getEfficiency(false);
        return MathHelper.lerp(base_efficiency, base_efficiency * ZANITE_MULTIPLIER, durability_progress);
    }

}
