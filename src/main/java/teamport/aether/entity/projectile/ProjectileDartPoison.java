package teamport.aether.entity.projectile;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import sunsetsatellite.catalyst.effects.api.effect.IHasEffects;
import teamport.aether.effect.AetherEffects;
import teamport.aether.items.AetherItems;

public class ProjectileDartPoison extends ProjectileDart {
    public ProjectileDartPoison(World world) {
        super(world, 1);
        this.stack = new ItemStack(AetherItems.AMMO_DART_POISON);
    }

    public ProjectileDartPoison(World world, double x, double y, double z) {
        super(world, x, y, z, 1);
        this.stack = new ItemStack(AetherItems.AMMO_DART_POISON);
    }

    public ProjectileDartPoison(World world, Mob owner, boolean doesDartBelongToPlayer) {
        super(world, owner, doesDartBelongToPlayer, 1);
        this.stack = new ItemStack(AetherItems.AMMO_DART_POISON);
    }

    public void onHit(HitResult hitResult) {
        if (hitResult.entity != null) {
            if (hitResult.entity.hurt(this.owner, this.damage, DamageType.COMBAT)) {
                IHasEffects effectPlayer = (IHasEffects) hitResult.entity;
                AetherEffects.fixedAdd(effectPlayer, AetherEffects.poisonEffect, 10);
                if (this.isOnFire()) {
                    hitResult.entity.fireHurt();
                }

                if (!this.world.isClientSide) {
                    this.world.playSoundAtEntity(null, this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
                }

            } else {
                this.xTile = hitResult.x;
                this.yTile = hitResult.y;
                this.zTile = hitResult.z;
                this.inTile = this.world.getBlockId(this.xTile, this.yTile, this.zTile);
                this.inData = this.world.getBlockMetadata(this.xTile, this.yTile, this.zTile);
                this.xd = (float) (hitResult.location.x - this.x);
                this.yd = (float) (hitResult.location.y - this.y);
                this.zd = (float) (hitResult.location.z - this.z);
                float f1 = MathHelper.sqrt(this.xd * this.xd + this.yd * this.yd + this.zd * this.zd);
                this.x -= this.xd / (double) f1 * 0.05;
                this.y -= this.yd / (double) f1 * 0.05;
                this.z -= this.zd / (double) f1 * 0.05;
                this.inGroundAction();
            }
        }
    }

}
