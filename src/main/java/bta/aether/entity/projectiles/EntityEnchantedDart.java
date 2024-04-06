package bta.aether.entity.projectiles;

import bta.aether.item.AetherItems;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.projectile.EntityArrow;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class EntityEnchantedDart extends EntityArrow {

    public EntityEnchantedDart(World world) {
        super(world);
    }

    @Override
    protected void init() {
        super.init();
        this.stack = new ItemStack(AetherItems.dartEnchanted);
        this.damage = 6;
    }

    public EntityEnchantedDart(World world, EntityLiving entityliving, boolean doesArrowBelongToPlayer) {
        super(world, entityliving, doesArrowBelongToPlayer, 11);
    }
}
