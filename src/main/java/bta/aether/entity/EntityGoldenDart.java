package bta.aether.entity;

import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.projectile.EntityArrow;
import net.minecraft.core.world.World;


public class EntityGoldenDart extends EntityArrow {

    public EntityGoldenDart(World world) {
        super(world);
    }
    public EntityGoldenDart(World world, EntityLiving entityliving, boolean doesArrowBelongToPlayer, int arrowType) {
        super(world, entityliving, doesArrowBelongToPlayer, arrowType);
    }
    public EntityGoldenDart(World world, double d, double d1, double d2, int arrowType) {
        super(world, d, d1, d2, arrowType);
    }
    public EntityGoldenDart(World world, int arrowType) {
        super(world, arrowType);
    }
}
