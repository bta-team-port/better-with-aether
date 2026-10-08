package teamport.aether.item.item_tool.phoenix;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import teamport.aether.entity.projectile.ProjectileElementFire;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.item.AetherHasCustomDamageType;
import teamport.aether.item.item_tool.ItemToolSwordAether;
import teamport.aether.mixin.accessors.EntityAccessor;

public class ItemToolSwordPhoenix extends ItemToolSwordAether implements AetherHasCustomDamageType, PhoenixAdjustBlockDropped {
	public ItemToolSwordPhoenix(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
		super(name, namespaceId, id, enumtoolmaterial);
	}

	@Override
	public boolean hitEntity(@NotNull ItemStack itemstack, @NotNull Mob target, @NotNull Mob attacker) {
        if (!(target instanceof EntityAccessor accessor)
            || accessor.isFireImmune()
            || target.hurtTime != 10
            || (target instanceof Player player && player.gamemode.hasInvulnerablePlayer())
            || !attacker.isSneaking()
        ) {
            return false;
        }
        ParticleMaker.spawnFireSwordParticles(target);
        target.maxFireTicks = 600;
        target.remainingFireTicks = 600;
        return super.hitEntity(itemstack, target, attacker);
    }

    @Override
    public @Nullable ItemStack onUse(@NotNull ItemStack selfStack, @NotNull World world, @NotNull Player player) {
        if (!world.isClientSide) {
            ProjectileElementFire elementFire = new ProjectileElementFire(world, player);
            float yaw   = player.yRot / 180.0F * (float) Math.PI;
            float pitch = player.xRot / 180.0F * (float) Math.PI;
            double dx = -MathHelper.sin(yaw) * MathHelper.cos(pitch);
            double dy = -MathHelper.sin(pitch);
            double dz =  MathHelper.cos(yaw) * MathHelper.cos(pitch);
            elementFire.setHeading(dx, dy, dz, 1.0F, 0.0F);
            world.playSoundAtEntity(player, player, "mob.ghast.fireball", 1.0F, (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F + 1.0F);
            world.entityJoinedWorld(elementFire);
            selfStack.damageItem(15, player);
            player.swingItem();
        }
        return selfStack;
    }

    @Override
	public DamageType getDamageType() {
		return DamageType.FIRE;
	}
}
