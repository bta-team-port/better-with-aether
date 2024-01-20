package bta.aether.entity.projectiles;

import bta.aether.item.AetherItems;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;


public class EntityGoldenDart extends EntityProjectileModular {

    public EntityGoldenDart(World world) {
        super(world);
        this.stack = new ItemStack(AetherItems.dartGolden);
    }
    public EntityGoldenDart(World world, EntityLiving entityliving, boolean doesArrowBelongToPlayer) {
        super(world, entityliving, doesArrowBelongToPlayer, 11);
        this.stack = new ItemStack(AetherItems.dartGolden);
    }
    public EntityGoldenDart(World world, double d, double d1, double d2, int arrowType) {
        super(world, d, d1, d2, arrowType);
        this.stack = new ItemStack(AetherItems.dartGolden);
    }
    public EntityGoldenDart(World world, int arrowType) {
        super(world, arrowType);
        this.stack = new ItemStack(AetherItems.dartGolden);
    }
}
