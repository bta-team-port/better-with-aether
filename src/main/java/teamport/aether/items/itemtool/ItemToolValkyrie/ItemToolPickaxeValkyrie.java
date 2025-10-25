package teamport.aether.items.itemtool.ItemToolValkyrie;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.DamageType;
import teamport.aether.AetherMod;
import teamport.aether.blocks.AetherBlockTags;
import teamport.aether.items.AetherHasCustomDamageType;
import teamport.aether.items.itemtool.ItemToolPickaxeAether;

public class ItemToolPickaxeValkyrie extends ItemToolPickaxeAether implements AetherHasCustomDamageType {
    public int weaponDamage;
    public ItemToolPickaxeValkyrie(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
//        this.weaponDamage = 1;
    }

//    @Override
//    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
//        if(target.hurtTime == 10) {
//            target.hurt(attacker, 6, AetherMod.HOLY);
//        }
//        itemstack.damageItem(2, attacker);
//        return true;
//    }

    @Override
    public boolean canHarvestBlock(Mob mob, ItemStack itemStack, Block<?> block) {
        Integer miningLevel = miningLevels.get(block);
        if (miningLevel != null) {
            return this.material.getMiningLevel() >= miningLevel;
        } else {
            return block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE) || block.hasTag(BlockTags.MINEABLE_BY_PICKAXE);
        }
    }

    @Override
    public float getStrVsBlock(ItemStack itemstack, Block<?> block) {
        if (!block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE))
            return Items.TOOL_PICKAXE_DIAMOND.getStrVsBlock(itemstack, block);
        return this.material.getEfficiency(false);
    }

    @Override
    public DamageType getDamageType(){
        return AetherMod.HOLY;
    }
}
