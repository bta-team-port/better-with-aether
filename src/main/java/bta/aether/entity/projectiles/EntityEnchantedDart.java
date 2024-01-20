package bta.aether.entity.projectiles;

import bta.aether.item.AetherItems;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class EntityEnchantedDart extends EntityProjectileModular{

    {
        this.stack = new ItemStack(AetherItems.dartEnchanted);
        this.arrowDamage = 6;
    }

    public EntityEnchantedDart(World world) {
        super(world);
    }
    public EntityEnchantedDart(World world, EntityLiving entityliving, boolean doesArrowBelongToPlayer) {
        super(world, entityliving, doesArrowBelongToPlayer, 11);
    }
}
