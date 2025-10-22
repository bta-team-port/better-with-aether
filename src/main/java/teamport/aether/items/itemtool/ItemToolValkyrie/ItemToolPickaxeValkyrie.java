package teamport.aether.items.itemtool.ItemToolValkyrie;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.item.material.ToolMaterial;
import teamport.aether.AetherMod;
import teamport.aether.blocks.AetherBlockTags;
import teamport.aether.items.itemtool.ItemToolPickaxeAether;

public class ItemToolPickaxeValkyrie extends ItemToolPickaxeAether {
    public ItemToolPickaxeValkyrie(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
        target.hurt(attacker, getDamageVsEntity(target, itemstack), AetherMod.HOLY);
        itemstack.damageItem(2, attacker);
        return true;
    }

    @Override
    public boolean canHarvestBlock(Mob mob, ItemStack itemStack, Block<?> block) {
        Integer miningLevel = miningLevels.get(block);
        if (miningLevel != null) {
            return this.material.getMiningLevel() >= miningLevel;
        } else {
            return block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE) || block.hasTag(BlockTags.MINEABLE_BY_PICKAXE);
        }
    }

    public float getStrVsBlock(ItemStack itemstack, Block<?> block) {
        if (!block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE))
            return Items.TOOL_PICKAXE_DIAMOND.getStrVsBlock(itemstack, block);
        return this.material.getEfficiency(false);
    }
}
