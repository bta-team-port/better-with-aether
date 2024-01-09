package bta.aether.item;

import net.minecraft.core.entity.EntityLightningBolt;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;

public class ItemSwordLightning extends ItemToolSword {
    public ItemSwordLightning(String name, int id, ToolMaterial enumtoolmaterial) {
        super(name, id, enumtoolmaterial);
    }
    @Override
    public boolean hitEntity(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
        entityliving.world.entityJoinedWorld(new EntityLightningBolt(entityliving.world, entityliving.x, entityliving.y, entityliving.z));
        return super.hitEntity(itemstack, entityliving, entityliving1);
    }
}
