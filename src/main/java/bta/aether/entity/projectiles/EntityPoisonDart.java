package bta.aether.entity.projectiles;

import bta.aether.catalyst.effects.AetherEffects;
import bta.aether.item.AetherItems;
import net.minecraft.core.HitResult;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.projectile.EntityArrow;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import sunsetsatellite.catalyst.effects.api.effect.EffectStack;
import sunsetsatellite.catalyst.effects.api.effect.IHasEffects;

public class EntityPoisonDart extends EntityArrow {

    {
        this.stack = new ItemStack(AetherItems.dartPoison);
    }

    public EntityPoisonDart(World world) {
        super(world, 11);
    }

    public EntityPoisonDart(World world, double d, double d1, double d2) {
        super(world, d, d1, d2, 11);
    }

    public EntityPoisonDart(World world, EntityLiving entityliving, boolean doesArrowBelongToPlayer) {
        super(world, entityliving, doesArrowBelongToPlayer, 11);
    }

    @Override
    protected void init() {
        super.init();
        this.gravity = 0.02F;
        this.speed = 1.0F;
        this.damage = 2;
    }

    @Override
    public void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (hitResult.entity != null) {
            IHasEffects effectEntity = (IHasEffects) hitResult.entity;
            EffectStack stack = new EffectStack(effectEntity, AetherEffects.poisonEffect, 10);
            effectEntity.getContainer().add(stack);
            stack.start(effectEntity.getContainer());
        }
    }
}
