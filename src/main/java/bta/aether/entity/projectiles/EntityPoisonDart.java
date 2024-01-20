package bta.aether.entity.projectiles;

import bta.aether.catalyst.effects.AetherEffects;
import bta.aether.item.AetherItems;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import sunsetsatellite.catalyst.effects.api.effect.EffectStack;
import sunsetsatellite.catalyst.effects.api.effect.IHasEffects;

public class EntityPoisonDart extends EntityProjectileModular {
    public EntityPoisonDart(World world) {
        super(world, 11);
        this.stack = new ItemStack(AetherItems.dartPoison);
        this.noPhysics = true;
    }

    public EntityPoisonDart(World world, double d, double d1, double d2) {
        super(world, d, d1, d2, 11);
        this.stack = new ItemStack(AetherItems.dartPoison);
        this.noPhysics = true;
    }

    public EntityPoisonDart(World world, EntityLiving entityliving, boolean doesArrowBelongToPlayer) {
        super(world, entityliving, doesArrowBelongToPlayer, 11);
        this.stack = new ItemStack(AetherItems.dartPoison);
        this.noPhysics = true;
    }

    @Override
    protected void init() {
        this.arrowGravity = 0.02F;
        this.arrowSpeed = 1.0F;
        this.arrowDamage = 2;
    }

    @Override
    protected Boolean hurtEntity(Entity entity) {
        IHasEffects effectEntity = (IHasEffects) entity;
        EffectStack stack = new EffectStack(effectEntity, AetherEffects.poisonEffect, 10);

        effectEntity.getContainer().add(stack);
        stack.start(effectEntity.getContainer());
        return super.hurtEntity(entity);
    }
}
