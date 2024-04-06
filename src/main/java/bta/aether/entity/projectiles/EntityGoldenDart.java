package bta.aether.entity.projectiles;

import bta.aether.item.AetherItems;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.projectile.EntityArrow;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;


public class EntityGoldenDart extends EntityArrow {

    {
        this.stack = new ItemStack(AetherItems.dartGolden);
    }

    public EntityGoldenDart(World world) {
        super(world);
    }
    public EntityGoldenDart(World world, EntityLiving entityliving, boolean doesArrowBelongToPlayer) {
        super(world, entityliving, doesArrowBelongToPlayer, 11);
    }
}
