package teamport.aether.items.itemtool.ItemToolValkyrie;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.DamageType;
import teamport.aether.AetherMod;
import teamport.aether.blocks.AetherBlockTags;
import teamport.aether.items.itemtool.ItemToolShovelAether;

public class ItemToolShovelValkyrie extends ItemToolShovelAether {
    public int weaponDamage;
    public ItemToolShovelValkyrie(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
//        this.weaponDamage = 1;
    }

//    @Override
//    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
//        if(target.hurtTime == 10) {
//            target.hurt(attacker, 5, AetherMod.HOLY);
//        }
//        itemstack.damageItem(2, attacker);
//        return true;
//    }

    @Override
    public float getStrVsBlock(ItemStack itemstack, Block<?> block) {
        if (!block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL))
            return Items.TOOL_SHOVEL_DIAMOND.getStrVsBlock(itemstack, block);
        return this.material.getEfficiency(false);
    }

    @Override
    public DamageType getDamageType(){
        return AetherMod.HOLY;
    }
}
