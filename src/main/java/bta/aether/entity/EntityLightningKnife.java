package bta.aether.entity;

import bta.aether.item.AetherItems;
import net.minecraft.core.entity.EntityLightningBolt;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.projectile.EntityArrow;
import net.minecraft.core.item.ItemStack;

import net.minecraft.core.world.World;

public class EntityLightningKnife extends EntityArrow {

    public ItemStack item = new ItemStack(AetherItems.toolKnifeLightning);

    public EntityLightningKnife(World world) {
        super(world);
    }
    public EntityLightningKnife(World world, EntityLiving entityliving, boolean doesArrowBelongToPlayer, int arrowType) {
        super(world, entityliving, doesArrowBelongToPlayer, arrowType);
    }
    public EntityLightningKnife(World world, double d, double d1, double d2, int arrowType) {
        super(world, d, d1, d2, arrowType);
    }
    public EntityLightningKnife(World world, int arrowType) {
        super(world, arrowType);
    }

    @Override
    public void tick() {
        if (this.inGround) this.remove();
        super.tick();
    }

    @Override
    public void remove() {
        this.world.entityJoinedWorld(new EntityLightningBolt(this.world, this.x, this.y, this.z));
        super.remove();
    }

}
