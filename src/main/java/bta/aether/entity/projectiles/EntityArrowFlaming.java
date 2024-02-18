package bta.aether.entity.projectiles;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;

public class EntityArrowFlaming extends EntityProjectileModular {
    public EntityArrowFlaming(World world) {
        this(world, 4);
    }

    public EntityArrowFlaming(World world, int arrowType) {
        super(world, arrowType);
    }

    public EntityArrowFlaming(World world, double d, double d1, double d2, int arrowType) {
        super(world, d, d1, d2, arrowType);
    }

    public EntityArrowFlaming(World world, EntityLiving entityliving, boolean doesArrowBelongToPlayer, int arrowType) {
        super(world, entityliving, doesArrowBelongToPlayer, arrowType);
    }

    @Override
    protected void spawnParticles() {
        this.world.spawnParticle("flame", this.x + this.xd * 0.5, this.y + this.yd * 0.5, this.z + this.zd * 0.5, this.xd * 0.05, this.yd * 0.05 - 0.1, this.zd * 0.05);
        this.world.spawnParticle("smoke", this.x + this.xd * 0.5, this.y + this.yd * 0.5, this.z + this.zd * 0.5, this.xd * 0.05, this.yd * 0.05 - 0.1, this.zd * 0.05);
    }

    @Override
    protected Boolean hurtEntity(Entity entity) {
        entity.fireHurt();
        return entity.hurt(this.owner, this.arrowDamage, DamageType.COMBAT);
    }

    @Override
    protected void inGroundAction() {
        if (world.getBlockId(this.xTile, this.yTile + 1, this.zTile) == 0 && Block.fire.canPlaceBlockAt(this.world, this.xTile, this.yTile + 1, this.zTile))
            world.setBlockWithNotify(this.xTile, this.yTile + 1, this.zTile, Block.fire.id);
        super.inGroundAction();
    }

    @Override
    public String getEntityTexture() {
        return this.entityData.getByte(1) != 1 ? super.getEntityTexture() : "/Jar/aether/other/FlamingArrows.png";
    }
}

