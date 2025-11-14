package teamport.aether.items.item_tool.item_tool_valkyrie;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.DamageType;
import teamport.aether.AetherMod;
import teamport.aether.blocks.AetherBlockTags;
import teamport.aether.items.AetherHasCustomDamageType;
import teamport.aether.items.item_tool.ItemToolPickaxeAether;

public class ItemToolPickaxeValkyrie extends ItemToolPickaxeAether implements AetherHasCustomDamageType {
    public ItemToolPickaxeValkyrie(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    @Override
    public boolean canHarvestBlock(Mob mob, ItemStack itemStack, Block<?> block) {
        Integer miningLevel = MINING_LEVELS.get(block);
        if (miningLevel != null) {
            return this.material.getMiningLevel() >= miningLevel;
        } else {
            return block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE) || block.hasTag(BlockTags.MINEABLE_BY_PICKAXE);
        }
    }

    @Override
    public float getStrVsBlock(ItemStack itemstack, Block<?> block) {
        return block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE) || block.hasTag(BlockTags.MINEABLE_BY_PICKAXE) ? this.material.getEfficiency(false) : 1.0F;
    }

    @Override
    public DamageType getDamageType() {
        return AetherMod.HOLY;
    }
}
